package project.io.goeffective.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

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
}
