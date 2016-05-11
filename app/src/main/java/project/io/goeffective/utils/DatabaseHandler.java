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

    private static final int DATABASE_VERSION = 1;
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
    private static final String KEY_DATE = "date";
    private static final String KEY_COMMENT = "comment";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createTaskTable(SQLiteDatabase db){
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    private void createTaskStartTable(SQLiteDatabase db){
        String CREATE_TASK_START_TABLE = "CREATE TABLE " + TABLE_TASK_START + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_TASK_ID + " INTEGER, "
                + KEY_START + " REAL,"
                + KEY_DELAY + " INTEGER,"
                + "FOREIGN KEY(" + KEY_TASK_ID + ")"
                + "REFERENCES "+ TABLE_TASK + "(" + KEY_ID + ")";
        db.execSQL(CREATE_TASK_START_TABLE);
    }

    private void createTaskDoneTable(SQLiteDatabase db){
        String CREATE_TASK_DONE_TABLE = "CREATE TABLE " + TABLE_TASK_DONE + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY, "
                + KEY_DATE + " REAL PRIMARY KEY,"
                + KEY_COMMENT + " TEXT,"
                + "FOREIGN KEY(" + KEY_TASK_ID + ")"
                + "REFERENCES "+ TABLE_TASK + "(" + KEY_ID + ")";
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

    private ContentValues createContentValueTaskStart(Long taskId, TaskStart taskStart){
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_ID, taskId);
        values.put(KEY_START, "julianday(" + taskStart.getStart().toString() + ")");
        values.put(KEY_DELAY, taskStart.getDelay());
        return values;
    }

    @Override
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        Long taskId = db.insert(TABLE_TASK, null, createContentValueTask(task));

        for (TaskStart taskStart: task.getTaskStartList()) {
            db.insert(TABLE_TASK_START, null, createContentValueTaskStart(taskId, taskStart));
        }
    }

    private void deleteTask(SQLiteDatabase db, Task task){
        for (TaskStart taskStart: task.getTaskStartList()) {
            db.delete(TABLE_TASK_START, KEY_ID + " = ?", new String[]{String.valueOf(taskStart.getId())});
        }
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
    public void changeTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteTask(db, task);
        addTask(task);
    }

    @Override
    public List<Pair<Task, Boolean>> getTasksStatusAtDate(Date date) {
        return null;
    }


    private List<TaskStart> getTaskStartFromDatabase(SQLiteDatabase db, Integer id){
        String taskStartQuery = "Select ?, date(?), ? from ? where ? = ?";
        Cursor cursor = db.rawQuery(taskStartQuery, new String[]{KEY_ID, KEY_START, KEY_DELAY, TABLE_TASK_START, KEY_TASK_ID, String.valueOf(id)});
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
        Cursor cursor = db.query(TABLE_TASK, new String[] {KEY_NAME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor == null) return null;

        cursor.moveToFirst();
        String name = cursor.getString(0);
        List<TaskStart> taskStarts = getTaskStartFromDatabase(db, id);

        return new Task(id, name, taskStarts);
    }

    @Override
    public List<Task> getTasksAtDate(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> list = new ArrayList<>();
        String query = "Select " + KEY_ID + " from "+ TABLE_TASK_START +" julianday('now') - " + KEY_START + " = 0;";
        Cursor cursor = db.rawQuery(query, null);

        Set<Integer> task_id = new HashSet<>();

        if( cursor.moveToFirst()){
            do {
                task_id.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        for (Integer id: task_id) {
            list.add(getTaskFromDatabase(db, id));
        }

        return list;
    }

    @Override
    public void setTaskStatusAtDate(Date date, Task task, Boolean flag) {

    }
}
