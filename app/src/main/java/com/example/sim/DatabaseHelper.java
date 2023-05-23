package com.example.sim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // private static final String DATABASE_NAME = "meinedb.db";
    // private static final int DATABASE_VERSION = 1;
    // private static final String TABLE_NAME = "list";
    // public static final String COLUMN_LISTENNAME = "listenname";
    // private static final String COLUMN_ERSTELLDATUM = "erstelldatum";
    // private static final String COLUMN_LAGERORT = "lagerort";
    // //user
    // private static final String TABLE_NAME_USER = "user";
    // public static final String COLUMN_USERNAME = "username";
    // private static final String COLUMN_PASSWORD = "password";

    private static final String DATABASE_NAME = "SIM.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_LIST = "list";
    public static final String COLUMN_ID_LIST = "idList";
    public static final String COLUMN_NAME_LIST = "listenname";
    public static final String COLUMN_CREATION_LIST = "erstelldatum";
    public static final String COLUMN_STORAGE_LIST = "lagerort";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LIST + "("
            + COLUMN_ID_LIST + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_LIST + "TEXT,"
            + COLUMN_CREATION_LIST + "TEXT,"
            + COLUMN_STORAGE_LIST + "TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST);
        onCreate(db);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_LIST, null, null, null, null, null, null);
    }
}
