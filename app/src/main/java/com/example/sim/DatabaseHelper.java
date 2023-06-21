package com.example.sim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

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
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_PASSWORD = "password";

    //lists
    public static final String TABLE_NAME_LIST = "list";
    public static final String COLUMN_ID_LIST = "rowid";
    public static final String COLUMN_NAME_LIST = "listenname";
    public static final String COLUMN_CREATION_LIST = "erstelldatum";
    public static final String COLUMN_STORAGE_LIST = "lagerort";
    public static final String COLUMN_LIST_USER_ID = "benutzername";

    //listen has products
    private static final String TABLE_NAME_LIST_HAS_PRODUCTS = "listenHasProducts";
    public static final String COLUMN_ID_LIST_PRODUCTS = "listId";
    public static final String COLUMN_ID_PRODUCTS_LIST = "productId";

    //products
    public static final String TABLE_NAME_PRODUCT = "product";
    public static final String COLUMN_ID_PRODUCT = "rowid";
    public static final String COLUMN_BRAND_PRODUCT = "marke";
    public static final String COLUMN_NAME_PRODUCT = "produktbezeichnung";
    public static final String COLUMN_EXPIRE_DATE_PRODUCT = "ablaufdatum";
    public static final String COLUMN_COUNT_PRODUCT = "stückzahl";
    public static final String COLUMN_PRODUCT_USER_ID = "benutzername";


    // Mengeneinheiten
    public static final String TABLE_NAME_MEASURE_UNITS = "mengeneinheiten";
    public static final String COLUMN_ID_MEASURE_UNITS = "rowid";
    public static final String COLUMN_MEASURE_UNITS = "mengeneinheit";

    // Kategorien
    public static final String TABLE_NAME_CATEGORY = "kategorien";
    public static final String COLUMN_ID_CATEGORY = "rowid";
    public static final String COLUMN_CATEGORY_NAME = "kategorie";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + "("
            + COLUMN_ID_USER + " INTEGER PRIMARY KEY,"
            + COLUMN_FIRSTNAME + " TEXT,"
            + COLUMN_LASTNAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_LIST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LIST + "("
            + COLUMN_ID_LIST + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME_LIST + " TEXT,"
            + COLUMN_CREATION_LIST + " TEXT,"
            + COLUMN_STORAGE_LIST + " TEXT,"
            + COLUMN_PRODUCT_USER_ID + " TEXT)";

    private static final String CREATE_TABLE_NAME_LISTS_HAVE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LIST_HAS_PRODUCTS + "("
            + COLUMN_ID_LIST_PRODUCTS + " INTEGER,"
            + COLUMN_ID_PRODUCTS_LIST + " INTEGER)";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT + "("
            + COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY,"
            + COLUMN_BRAND_PRODUCT + " TEXT,"
            + COLUMN_NAME_PRODUCT + " TEXT,"
            + COLUMN_EXPIRE_DATE_PRODUCT + " TEXT,"
            + COLUMN_COUNT_PRODUCT + " TEXT,"
            + COLUMN_PRODUCT_USER_ID + " TEXT)";

    private static final String CREATE_TABLE_MEASURE_UNITS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_MEASURE_UNITS + "("
            + COLUMN_ID_MEASURE_UNITS + " INTEGER PRIMARY KEY,"
            + COLUMN_MEASURE_UNITS + " TEXT)";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORY + "("
            + COLUMN_ID_CATEGORY + " INTEGER PRIMARY KEY,"
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
        db.execSQL(CREATE_TABLE_NAME_LISTS_HAVE_PRODUCT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEASURE_UNITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LIST_HAS_PRODUCTS);
        onCreate(db);
    }

    public void insertMeasureUnits(List<String> measureUnits){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        for (String measure : measureUnits){
            values.clear();
            values.put("mengeneinheit", measure);
            db.insert("mengeneinheiten", null, values);
        }
        db.close();
    }

    public void insertCategory(List<String> categories){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues Categoryvalues = new ContentValues();

        for (String category : categories){
            Categoryvalues.clear();
            Categoryvalues.put("kategorie", category);
            db.insert("kategorien", null, Categoryvalues);
        }
        db.close();
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

    public Cursor getAllMeasureUnits(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_MEASURE_UNITS, null, null, null, null, null, null);
    }

    public Cursor getAllCategories(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME_CATEGORY, null, null, null, null, null, null);
    }
}
