package com.example.sim;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The ProductActivity class represents the activity for creating a new product.
 * It allows the user to enter information about the product and creates it in the database.
 */
public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editBrand, editProductTitle, editExpireDate, editPieceNumber;
    Button btnCreateProduct, dateButton;
    Date currentDate;
    SimpleDateFormat dateFormat;

    String getUsername, getProduktName, valueProductId, formattedDate;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_EMAIL_USER = "emailUser";
    public static final String KEY_PRODUCT_NAME = "produktbezeichnung";
    Cursor cursorProductId;
    SharedPreferences sharedPreferences;
    StringBuilder datarowid;
    DatabaseHelper databaseHelper;

    // Get the current date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Neues Produkt erstellen");

        editBrand = (EditText) findViewById(R.id.editBrand);
        editProductTitle = (EditText) findViewById(R.id.editProductTitle);
        editExpireDate = (EditText) findViewById(R.id.editExpireDate);
        editPieceNumber = (EditText) findViewById(R.id.editPieceNumber);

        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
        btnCreateProduct.setOnClickListener(this);
        dateButton = (Button) findViewById(R.id.dateButton);

        currentDate = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        formattedDate = dateFormat.format(currentDate);
        editExpireDate.setText(formattedDate);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Get the current selected date from the spinner
                // Create a DatePickerDialog to choose a new date
                // Set initial date values
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProductActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the selected date EditText with the chosen date
                        String formattedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, month + 1, year);
                        editExpireDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });
    }

    /**
     * Adds a new product to the database.
     *
     * @param marke        The brand of the product.
     * @param bezeichnung  The title of the product.
     * @param ablaufdatum  The expiration date of the product.
     * @param stückzahl    The number of pieces of the product.
     */
    public void addProduct(String marke, String bezeichnung, String ablaufdatum, String stückzahl) {
        if (checkProduct(marke, bezeichnung, ablaufdatum)) {
            createProduct(marke, bezeichnung, ablaufdatum, stückzahl);
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_PRODUCT_NAME, editProductTitle.getText().toString());
            editor.apply();
        } else {
            Toast.makeText(getApplicationContext(), "Dieses Produkt Exisiert bereits!", Toast.LENGTH_SHORT).show();
        }

        editBrand.setText("");
        editProductTitle.setText("");
        editExpireDate.setText("");
        editPieceNumber.setText("");
    }

    /**
     * Checks if a product with the given brand, title, and expiration date already exists in the database.
     *
     * @param marke        The brand of the product.
     * @param bezeichnung  The title of the product.
     * @param ablaufdatum  The expiration date of the product.
     * @return True if the product does not exist in the database, false otherwise.
     */
    public boolean checkProduct(String marke, String bezeichnung, String ablaufdatum) {
        boolean okay = false;
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorProduct = databaseProduct.rawQuery("SELECT COUNT (*) FROM product WHERE marke = '" + marke + "' AND produktbezeichnung ='" + bezeichnung + "' AND ablaufdatum = '" + ablaufdatum + "'", null);
        cursorProduct.moveToFirst();
        if (cursorProduct.getInt(0) == 0) {
            okay = true;
        }
        cursorProduct.close();
        databaseProduct.close();
        return okay;
    }

    /**
     * Creates a new product in the database.
     *
     * @param marke        The brand of the product.
     * @param bezeichnung  The title of the product.
     * @param ablaufdatum  The expiration date of the product.
     * @param stückzahl    The number of pieces of the product.
     */
    public void createProduct(String marke, String bezeichnung, String ablaufdatum, String stückzahl) {
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseProduct.execSQL("INSERT INTO product (marke, produktbezeichnung, ablaufdatum, stückzahl) VALUES ('" + marke + "','" + bezeichnung + "','" + ablaufdatum + "','" + stückzahl + "')");
        databaseProduct.close();
    }

    /**
     * Loads the main activity.
     */
    public void loadMainActivity() {
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    @SuppressLint("Range")
    public void getProductId(){
        SQLiteDatabase databaseList = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        getUsername =  sharedPreferences.getString(KEY_EMAIL_USER, "");
        getProduktName = sharedPreferences.getString(KEY_PRODUCT_NAME,"");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        cursorProductId = databaseList.rawQuery("SELECT rowid FROM product WHERE produktbezeichnung = '" + getProduktName +"'", null);
        datarowid = new StringBuilder();

        Toast.makeText(this, "Der Produktname lautet " + getProduktName, Toast.LENGTH_SHORT).show();

        while(cursorProductId.moveToNext()){
            valueProductId = cursorProductId.getString(cursorProductId.getColumnIndex("rowid"));

            datarowid.append(valueProductId);
        }
        String id = datarowid.toString();

        databaseList.execSQL("INSERT INTO userHasProducts (userid, productId) VALUES ('" + getUsername +"','" + id +"')");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivity();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateProduct) {
            addProduct(editBrand.getText().toString(), editProductTitle.getText().toString(), editExpireDate.getText().toString(), editPieceNumber.getText().toString());
            loadMainActivity();
            getProductId();
        }
    }
}
