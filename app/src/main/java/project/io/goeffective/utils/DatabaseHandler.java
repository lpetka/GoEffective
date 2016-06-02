package project.io.goeffective.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabase {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "GoEffective";

    private static final String TABLE_TASK = "tasks";
    private static final String TABLE_TASK_START = "task_starts";
    private static final String TABLE_TASK_DONE = "task_done";

    private static final String KEY_ID = "id";
    private static final String KEY_TASK_ID = "task_id";

    //Table tasks
    private static final String KEY_NAME = "name";
    private static final String KEY_NOTIFICATION = "notification";

    //Table task_starts
    private static final String KEY_START = "start";
    private static final String KEY_DELAY = "delay";

    //Table task_done
    private static final String KEY_DATE = "date_done";
    private static final String KEY_COMMENT = "comment";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createTaskTable(SQLiteDatabase db){
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT,"
                + KEY_COMMENT + " TEXT,"
                + KEY_NOTIFICATION + " INTEGER"
                + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    private void createTaskStartTable(SQLiteDatabase db){
        String CREATE_TASK_START_TABLE = "CREATE TABLE " + TABLE_TASK_START + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_TASK_ID + " INTEGER, "
                + KEY_START + " REAL,"
                + KEY_DELAY + " INTEGER,"
                + "FOREIGN KEY(" + KEY_TASK_ID + ")"
                + "REFERENCES "+ TABLE_TASK + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_TASK_START_TABLE);
    }

    private void createTaskDoneTable(SQLiteDatabase db){
        String CREATE_TASK_DONE_TABLE = "CREATE TABLE " + TABLE_TASK_DONE + "("
                + KEY_TASK_ID + " INTEGER, "
                + KEY_DATE + " REAL,"
                + KEY_COMMENT + " TEXT,"
                + "PRIMARY KEY (" + KEY_TASK_ID + ", " + KEY_DATE + "),"
                + "FOREIGN KEY(" + KEY_TASK_ID + ")"
                + "REFERENCES "+ TABLE_TASK + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_TASK_DONE_TABLE);
    }

    @Override
    public void clearDatabase() {
        onUpgrade(this.getWritableDatabase(), 0, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTaskTable(sqLiteDatabase);
        createTaskStartTable(sqLiteDatabase);
        createTaskDoneTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_START);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_DONE);
        onCreate(sqLiteDatabase);
    }

    private ContentValues createContentValueTask(Task task){
        ContentValues values = new ContentValues();
        if(task.getId() != -1) {
            values.put(KEY_ID, task.getId());
        }
        values.put(KEY_NAME, task.getName());
        values.put(KEY_COMMENT, task.getNote());
        values.put(KEY_NOTIFICATION, task.isNotification()?1:0);
        return values;
    }

    private String createContentValueTaskStart(Integer taskId, TaskStart taskStart, String tableName){
        return String.format("INSERT INTO %s (%s, %s, %s) VALUES(%d, julianday('%s'), %d);",
                tableName, KEY_TASK_ID, KEY_START, KEY_DELAY,
                taskId, dateFormat.format(taskStart.getStart()), taskStart.getDelay());
    }

    private void addTaskStart(SQLiteDatabase db, Task task, Integer taskId){
        for (TaskStart taskStart: task.getTaskStartList()) {
            String query = createContentValueTaskStart(taskId, taskStart, TABLE_TASK_START);
            db.execSQL(query);
        }
    }

    @Override
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        Long taskId = db.insert(TABLE_TASK, null, createContentValueTask(task));
        addTaskStart(db, task, taskId.intValue());
    }

    private String removeQuery(String tableName, String idName, Integer id){
        return String.format("Delete from %s where %s = %d", tableName, idName, id);
    }

    private void deleteTaskStart(SQLiteDatabase db, Task task){
        db.execSQL(removeQuery(TABLE_TASK_START, KEY_TASK_ID, task.getId()));
    }

    private void deleteTask(SQLiteDatabase db, Task task){
        deleteTaskStart(db, task);
        db.execSQL(removeQuery(TABLE_TASK, KEY_ID, task.getId()));
    }

    private void deleteTaskDone(SQLiteDatabase db, Task task){
        db.execSQL(removeQuery(TABLE_TASK_DONE, KEY_TASK_ID, task.getId()));
    }

    @Override
    public void removeTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteTaskDone(db, task);
        deleteTask(db, task);
    }

    @Override
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteTaskStart(db, task);
        String query = String.format("Update %s set %s = '%s', %s = '%s', %s = %s where %s = %d",
                TABLE_TASK, KEY_NAME, task.getName(), KEY_COMMENT, task.getNote(), KEY_NOTIFICATION, task.isNotification()?1:0, KEY_ID, task.getId());
        db.execSQL(query);
        addTaskStart(db, task, task.getId());
    }

    private Boolean checkTaskStatusAtDate(SQLiteDatabase db, Integer taskId, Date date){
        String query = String.format("Select %s from %s where %s = %s and cast(%s as int) = cast(julianday('%s') as int);",
                KEY_TASK_ID, TABLE_TASK_DONE,
                KEY_TASK_ID, String.valueOf(taskId), KEY_DATE, dateFormat.format(date));
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    @Override
    public List<Pair<Task, Boolean>> getTasksStatusAtDate(Date date) {
        List<Task> tasks = getTasksAtDate(date);
        SQLiteDatabase db = this.getReadableDatabase();
        List<Pair<Task, Boolean>> pairList = new ArrayList<>();
        for (Task task: tasks) {
            Pair<Task, Boolean> pair= new Pair<>(task, checkTaskStatusAtDate(db, task.getId(), date));
            pairList.add(pair);
        }
        return pairList;
    }


    private List<TaskStart> getTaskStartFromDatabase(SQLiteDatabase db, Integer id){
        String taskStartQuery = String.format("Select date(%s), %s from %s where %s = %s;",
                KEY_START, KEY_DELAY, TABLE_TASK_START, KEY_TASK_ID, String.valueOf(id));
        Cursor cursor = db.rawQuery(taskStartQuery, null);
        List<TaskStart> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                try{
                Date start = dateFormat.parse(cursor.getString(0));
                Integer delay = cursor.getInt(1);
                TaskStart taskStart = new TaskStart(start, delay);
                list.add(taskStart);
                } catch (ParseException e) {}
            } while (cursor.moveToNext());
        }
        return list;
    }

    private Task getTaskFromDatabase(SQLiteDatabase db, Integer id){
        String query = String.format("Select %s, %s, %s from %s where %s = %s;",
                KEY_NAME, KEY_COMMENT,
                KEY_NOTIFICATION, TABLE_TASK,
                KEY_ID, String.valueOf(id));
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String comment = cursor.getString(1);
            boolean notification = cursor.getInt(2) == 1;
            List<TaskStart> taskStarts = getTaskStartFromDatabase(db, id);
            return new Task(id, name, taskStarts, comment, notification);
        }
        return null;
    }

    @Override
    public List<Task> getTasksAtDate(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> list = new ArrayList<>();
        String query = String.format("Select %s from %s where " +
                "((cast(julianday('%s') as int) - cast(%s as int) + 1) %% %s = 0) and " +
                "(cast(julianday('%s') as int) > cast(%s as int));",
                KEY_TASK_ID, TABLE_TASK_START,
                dateFormat.format(date), KEY_START, KEY_DELAY,
                dateFormat.format(date), KEY_START);
        Cursor cursor = db.rawQuery(query, null);

        Set<Integer> task_id = new HashSet<>();

        if( cursor.moveToFirst()){
            do {
                task_id.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        for (Integer id: task_id) {
            Task task = getTaskFromDatabase(db, id);
            if (task != null){
                list.add(task);
            }
        }

        return list;
    }

    @Override
    public void setTaskStatusAtDate(Date date, Task task, Boolean flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(task.getId());
        if(flag){
            String query = String.format("Insert into %s (%s, %s, %s) values (%s, julianday('%s'), '%s')",
                    TABLE_TASK_DONE, KEY_TASK_ID, KEY_DATE, KEY_COMMENT,
                    id, dateFormat.format(date), "commant");
            db.execSQL(query);
        } else {
            String query = String.format("Delete from %s where %s = %s and cast(%s as int) = cast(julianday('%s') as int)",
                    TABLE_TASK_DONE, KEY_TASK_ID, id, KEY_DATE, dateFormat.format(date));
            db.execSQL(query);
        }
    }

    @Override
    public List<Task> getTasksList() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> list = new ArrayList<>();
        String query = String.format("Select %s from %s;", KEY_ID, TABLE_TASK);
        Cursor cursor = db.rawQuery(query, null);
        Set<Integer> task_id = new HashSet<>();
        if (cursor.moveToFirst()) {
            do {
                task_id.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        for (Integer id : task_id) {
            Task task = getTaskFromDatabase(db, id);
            if (task != null) {
                list.add(task);
            }
        }
        return list;
    }
    
    private class TaskDate {
        private List<TaskStart> taskStartList;
        private List<Date> lastTaskDateList;
        private Date date;
        public TaskDate(List<TaskStart> tlist, Date today){
            taskStartList = tlist;
            date = (Date)today.clone();
            lastTaskDateList = new ArrayList<>(tlist.size());
            for (TaskStart taskStart: taskStartList) {
                lastTaskDateList.add(getLastDate(date, taskStart));
            }
        }

        private Date getLastDate(Date date, TaskStart taskStart){
            long diff = taskStart.getStart().getTime() - date.getTime();
            long dayDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, -((int)dayDiff%taskStart.getDelay()));
            return cal.getTime();
        }

        private boolean isSameDay(Date first, Date second){
            Long firstDays = TimeUnit.DAYS.convert(first.getTime(), TimeUnit.MILLISECONDS);
            Long secondDays = TimeUnit.DAYS.convert(second.getTime(), TimeUnit.MILLISECONDS);
            return firstDays == secondDays;
        }

        private Date getPrevDate(Date date, TaskStart taskStart){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            return getLastDate(cal.getTime(), taskStart);
        }

        public Date getPrevTaskDate(){
            Date lastDate = lastTaskDateList.get(0);
            for(int i = 0; i<lastTaskDateList.size(); i++){
                if(lastDate.before(lastTaskDateList.get(i))){
                    lastDate = lastTaskDateList.get(i);
                }
            }
            for(int i = 0; i<lastTaskDateList.size(); i++){
                if(isSameDay(lastDate, lastTaskDateList.get(i))){
                    lastTaskDateList.set(i, getPrevDate(lastTaskDateList.get(i), taskStartList.get(i)));
                }
            }
            return lastDate;
        }

        public boolean hasPrevDay(){
            if(lastTaskDateList.isEmpty()){
                return false;
            }
            boolean taskAfterStart = true;
            for(int i = 0; i<lastTaskDateList.size(); i++){
                taskAfterStart &= taskStartList.get(i).getStart().before(lastTaskDateList.get(i));
            }
            return taskAfterStart;
        }
    }

    public List<Boolean> getTaskHistory(Task task, Date date, int days) {
        SQLiteDatabase db = this.getReadableDatabase();
        TaskDate taskDate = new TaskDate(task.getTaskStartList(), date);
        List<Boolean> history = new LinkedList<>();
        for(int i = 0; i<days; i++){
            if(taskDate.hasPrevDay()){
                Date prev = taskDate.getPrevTaskDate();
                history.add(checkTaskStatusAtDate(db, task.getId(), prev));
            }
        }
        return history;
    }

    public List<Boolean> getTaskHistoryUntilFalse(Task task, Date date){
        SQLiteDatabase db = this.getReadableDatabase();
        TaskDate taskDate = new TaskDate(task.getTaskStartList(), date);
        List<Boolean> history = new LinkedList<>();
        boolean prevDateStatus = true;
        while (taskDate.hasPrevDay() && prevDateStatus){
            Date prev = taskDate.getPrevTaskDate();
            prevDateStatus = checkTaskStatusAtDate(db, task.getId(), prev);
            history.add(prevDateStatus);
        }
        return history;
    }

    public List<Boolean> getTaskHistoryUntilFalse(Task task, Date date, int minDays){
        SQLiteDatabase db = this.getReadableDatabase();
        TaskDate taskDate = new TaskDate(task.getTaskStartList(), date);
        List<Boolean> history = new LinkedList<>();
        boolean prevDateGlobalStatus = true;
        int day = 0;
        while (taskDate.hasPrevDay() && (prevDateGlobalStatus || minDays>day++)){
            Date prev = taskDate.getPrevTaskDate();
            boolean prevDateStatus = checkTaskStatusAtDate(db, task.getId(), prev);
            history.add(prevDateStatus);
            prevDateGlobalStatus &= prevDateStatus;
        }
        return history;
    }
}
