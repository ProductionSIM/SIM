package com.example.sim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SIM.db";
    private static final int DATABASE_VERSION = 1;

    //user
     private static final String TABLE_NAME_USER = "user";
     public static final String COLUMN_ID_USER = "idUser";
     public static final String COLUMN_USERNAME = "username";
     private static final String COLUMN_PASSWORD = "password";

    //listen
    public static final String TABLE_NAME_LIST = "list";
    public static final String COLUMN_ID_LIST = "idList";
    public static final String COLUMN_NAME_LIST = "listenname";
    public static final String COLUMN_CREATION_LIST = "erstelldatum";
    public static final String COLUMN_STORAGE_LIST = "lagerort";

    //produkte
    public static final String TABLE_NAME_PRODUCT = "product";
    public static final String COLUMN_ID_PRODUCT = "idProduct";
    public static final String COLUMN_BRAND_PRODUCT = "marke";
    public static final String COLUMN_NAME_PRODUCT = "produktbezeichnung";
    public static final String COLUMN_EXPIRE_DATE_PRODUCT = "ablaufdatum";
    public static final String COLUMN_COUNT_PRODUCT = "stückzahl";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + "("
            + COLUMN_ID_USER + "INTEGER PRIMARY KEY,"
            + COLUMN_USERNAME + "TEXT,"
            + COLUMN_PASSWORD + "TEXT)";

    private static final String CREATE_TABLE_LIST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LIST + "("
            + COLUMN_ID_LIST + "INTEGER PRIMARY KEY,"
            + COLUMN_NAME_LIST + "TEXT,"
            + COLUMN_CREATION_LIST + "TEXT,"
            + COLUMN_STORAGE_LIST + "TEXT)";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT + "("
            + COLUMN_ID_PRODUCT + "INTEGER PRIMARY KEY,"
            + COLUMN_BRAND_PRODUCT + "TEXT,"
            + COLUMN_NAME_PRODUCT + "TEXT,"
            + COLUMN_EXPIRE_DATE_PRODUCT + "TEXT,"
            + COLUMN_COUNT_PRODUCT + "TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LIST);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);
    }

    public Cursor getAllDataList(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_LIST, null, null, null, null, null, null);
    }

    public Cursor getAllDataProduct(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_PRODUCT, null, null, null, null, null, null);
    }

    public Cursor getAllDataUser(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_USER, null,null,null,null,null,null);
    }
}
