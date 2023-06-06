package com.example.sim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The DatabaseHelper class handles the creation and management of the SQLite database used in the app.
 * It provides methods for creating tables, performing CRUD operations, and retrieving data from the database.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SIM.db";

    private static final int DATABASE_VERSION = 1;

    //user
    private static final String TABLE_NAME_USER = "user";
    public static final String COLUMN_ID_USER = "rowid";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";

    //listen
    public static final String TABLE_NAME_LIST = "list";
    public static final String COLUMN_ID_LIST = "rowid";
    public static final String COLUMN_NAME_LIST = "listenname";
    public static final String COLUMN_CREATION_LIST = "erstelldatum";
    public static final String COLUMN_STORAGE_LIST = "lagerort";

    //produkte
    public static final String TABLE_NAME_PRODUCT = "product";
    public static final String COLUMN_ID_PRODUCT = "rowid";
    public static final String COLUMN_BRAND_PRODUCT = "marke";
    public static final String COLUMN_NAME_PRODUCT = "produktbezeichnung";
    public static final String COLUMN_EXPIRE_DATE_PRODUCT = "ablaufdatum";
    public static final String COLUMN_COUNT_PRODUCT = "stückzahl";

    // Mengeneinheiten
    public static final String TABLE_NAME_MEASURE_UNITS = "mengeneinheiten";
    public static final String COLUMN_ID_MEASURE_UNITS = "rowid";
    public static final String COLUMN_MEASURE_UNITS = "mengeneinheiten";

    // Kategorien
    public static final String TABLE_NAME_CATEGORY = "kategorien";
    public static final String COLUMN_ID_CATEGORY = "rowid";
    public static final String COLUMN_CATEGORY_NAME = "kategoriename";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + "("
            + COLUMN_ID_USER + " INTEGER PRIMARY KEY,"
            + COLUMN_FIRSTNAME + " TEXT,"
            + COLUMN_LASTNAME + " TEXT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_LIST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LIST + "("
            + COLUMN_ID_LIST + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME_LIST + " TEXT,"
            + COLUMN_CREATION_LIST + " TEXT,"
            + COLUMN_STORAGE_LIST + " TEXT)";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT + "("
            + COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY,"
            + COLUMN_BRAND_PRODUCT + " TEXT,"
            + COLUMN_NAME_PRODUCT + " TEXT,"
            + COLUMN_EXPIRE_DATE_PRODUCT + " TEXT,"
            + COLUMN_COUNT_PRODUCT + " TEXT)";

    private static final String CREATE_TABLE_MEASURE_UNITS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_MEASURE_UNITS + "("
            // + COLUMN_ID_MEASURE_UNITS + " INTEGER PRIMARY KEY,"
            + COLUMN_MEASURE_UNITS + " TEXT)";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORY + "("
            // + COLUMN_ID_CATEGORY + " INTEGER PRIMARY KEY,"
            + COLUMN_CATEGORY_NAME + " TEXT)";

    /**
     * Constructs a new instance of the DatabaseHelper.
     *
     * @param context The context of the application.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LIST);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MEASURE_UNITS);
        db.execSQL(CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEASURE_UNITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
        onCreate(db);
    }

    /**
     * Deletes a specific row from the list table.
     *
     * @param id The ID of the row to be deleted.
     * @return The number of rows deleted.
     */
    public int deleteDataList(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME_LIST, "ID =" + id, null);
        db.close();
        return i;
    }

    /**
     * Deletes a specific row from the product table.
     *
     * @param id The ID of the row to be deleted.
     * @return The number of rows deleted.
     */
    public int deleteDataProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME_PRODUCT, "ID =" + id, null);
        db.close();
        return i;
    }

    /**
     * Retrieves all data from the list table.
     *
     * @return A Cursor containing the query result.
     */
    public Cursor getAllDataList() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_LIST, null, null, null, null, null, null);
    }

    /**
     * Retrieves all data from the product table.
     *
     * @return A Cursor containing the query result.
     */
    public Cursor getAllDataProduct() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_PRODUCT, null, null, null, null, null, null);
    }

    /**
     * Retrieves all data from the user table.
     *
     * @return A Cursor containing the query result.
     */
    public Cursor getAllDataUser() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_USER, null, null, null, null, null, null);
    }

}
