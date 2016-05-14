package project.io.goeffective.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabase {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "GoEffective";

    private static final String TABLE_TASK = "tasks";
    private static final String TABLE_TASK_START = "task_starts";
    private static final String TABLE_TASK_DONE = "task_done";

    private static final String KEY_ID = "id";
    private static final String KEY_TASK_ID = "task_id";

    //Table tasks
    private static final String KEY_NAME = "name";

    //Table task_starts
    private static final String KEY_START = "start";
    private static final String KEY_DELAY = "delay";

    //Table task_done
    private static final String KEY_DATE = "date_done";
    private static final String KEY_COMMENT = "comment";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createTaskTable(SQLiteDatabase db){
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT"
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
        return values;
    }

    private String createContentValueTaskStart(Integer taskId, TaskStart taskStart, String tableName){

        return String.format("INSERT INTO %s (%s, %s, %s) VALUES(%d, julianday('%s'), %d);",
                tableName, KEY_TASK_ID, KEY_START, KEY_DELAY,
                taskId, taskStart.getStart().toString(), taskStart.getDelay());
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

    private void deleteTaskStart(SQLiteDatabase db, Task task){
        for (TaskStart taskStart: task.getTaskStartList()) {
            db.delete(TABLE_TASK_START, KEY_ID + " = ?", new String[]{String.valueOf(taskStart.getId())});
        }
    }

    private void deleteTask(SQLiteDatabase db, Task task){
        deleteTaskStart(db, task);
        db.delete(TABLE_TASK, KEY_ID + " = ?", new String[]{ String.valueOf(task.getId())});
    }

    private void deleteTaskDone(SQLiteDatabase db, Task task){
        db.delete(TABLE_TASK_DONE, KEY_TASK_ID + " = ?", new String[]{String.valueOf(task.getId())});
    }

    @Override
    public void removeTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteTask(db, task);
        deleteTaskDone(db, task);
    }

    @Override
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

    }

    private Boolean checkTaskStatusAtDate(SQLiteDatabase db, Integer taskId, Date date){
        String query = String.format("Select %s from %s where %s = %s and cast(%s as int) = cast(julianday('%s') as int);",
                KEY_TASK_ID, TABLE_TASK_DONE,
                KEY_TASK_ID, String.valueOf(taskId), KEY_DATE, date.toString());
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
        String taskStartQuery = String.format("Select %s, date(%s), %s from %s where %s = %s;",
                KEY_ID, KEY_START, KEY_DELAY, TABLE_TASK_START, KEY_TASK_ID, String.valueOf(id));
        Cursor cursor = db.rawQuery(taskStartQuery, null);
        List<TaskStart> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Integer taskStartId = cursor.getInt(0);
                Date start = Date.valueOf(cursor.getString(1));
                Integer delay = cursor.getInt(2);
                TaskStart taskStart = new TaskStart(taskStartId, start, delay);
                list.add(taskStart);
            } while (cursor.moveToNext());
        }
        return list;
    }

    private Task getTaskFromDatabase(SQLiteDatabase db, Integer id){
        String query = String.format("Select %s from %s where %s = %s;", KEY_NAME, TABLE_TASK,
                KEY_ID, String.valueOf(id));
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            List<TaskStart> taskStarts = getTaskStartFromDatabase(db, id);
            return new Task(id, name, taskStarts);
        }
        return null;
    }

    @Override
    public List<Task> getTasksAtDate(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> list = new ArrayList<>();
        String query = String.format("Select %s from %s where cast(julianday('%s') as int) - cast(%s as int) = 0;",
                KEY_ID, TABLE_TASK_START, date.toString(), KEY_START);
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
                    id, date.toString(), "commant");
            db.execSQL(query);
        } else {
            String query = String.format("Delete from %s where %s = %s and cast(%s as int) = cast(julianday('%s') as int)",
                    TABLE_TASK_DONE, KEY_TASK_ID, id, KEY_DATE, date.toString());
            db.execSQL(query);
        }
    }
}
