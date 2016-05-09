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

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createTaskTable(SQLiteDatabase db){
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTaskTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
