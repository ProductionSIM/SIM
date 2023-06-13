package com.example.sim;

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

import java.util.Calendar;
import java.util.Locale;

public class ShowProductInfosActivity extends AppCompatActivity implements View.OnClickListener{

    EditText showProductBrand, showProductName, showProductExpireDate, showProductCount;

    Button btnUpdateProduct, dateButton, btnDeleteProduct;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";
    PreferenceManager preferenceManager;
    DatabaseHelper databaseHelper;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_PRODUCT_ID = "productid";
    String valueMarke, valueProduktbezeichnung, valueAblaufdatum, valueStückzahl, getIdFromPreference;
    StringBuilder dataMarke, dataProduktbezeichnung, dataAblaufdatum, dataStückzahl;
    Cursor cursorUserMarke, cursorUserProduktbezeichnung, cursorUserAblaufdatum, cursorUserStückzahl;
    SQLiteDatabase databaseUser;
    SharedPreferences sharedPreferences;

    // Get the current date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_infos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        btnUpdateProduct = (Button) findViewById(R.id.btnUpdateProduct);
        btnUpdateProduct.setOnClickListener(this);

        btnDeleteProduct = (Button) findViewById(R.id.btnDeleteProduct);
        btnDeleteProduct.setOnClickListener(this);

        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Get the current selected date from the spinner
                // Create a DatePickerDialog to choose a new date
                // Set initial date values
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShowProductInfosActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the selected date EditText with the chosen date
                        String formattedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, month + 1, year);
                        showProductExpireDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        showProductBrand = (EditText) findViewById(R.id.EditListname);
        //showUsername();
        showProductName = (EditText) findViewById(R.id.productName);
        showProductExpireDate = (EditText) findViewById(R.id.listCreationDate);
        showProductCount = (EditText) findViewById(R.id.listStorageLocation);

        preferenceManager = new PreferenceManager(getApplicationContext());

        sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        getIdFromPreference =  sharedPreferences.getString(KEY_PRODUCT_ID, "");

        databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        cursorUserMarke = databaseUser.rawQuery("SELECT marke FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserProduktbezeichnung = databaseUser.rawQuery("SELECT produktbezeichnung FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserAblaufdatum = databaseUser.rawQuery("SELECT ablaufdatum FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserStückzahl = databaseUser.rawQuery("SELECT stückzahl FROM product WHERE rowid = '" + getIdFromPreference + "'", null);

        dataMarke = new StringBuilder();
        dataProduktbezeichnung = new StringBuilder();
        dataAblaufdatum = new StringBuilder();
        dataStückzahl = new StringBuilder();

        while(cursorUserMarke.moveToNext() && cursorUserProduktbezeichnung.moveToNext() && cursorUserAblaufdatum.moveToNext() && cursorUserStückzahl.moveToNext()){
            valueMarke = cursorUserMarke.getString(cursorUserMarke.getColumnIndexOrThrow("marke"));
            valueProduktbezeichnung = cursorUserProduktbezeichnung.getString(cursorUserProduktbezeichnung.getColumnIndexOrThrow("produktbezeichnung"));
            valueAblaufdatum = cursorUserAblaufdatum.getString(cursorUserAblaufdatum.getColumnIndexOrThrow("ablaufdatum"));
            valueStückzahl = cursorUserStückzahl.getString(cursorUserStückzahl.getColumnIndexOrThrow("stückzahl"));


            dataMarke.append(valueMarke);
            dataProduktbezeichnung.append(valueProduktbezeichnung);
            dataAblaufdatum.append(valueAblaufdatum);
            dataStückzahl.append(valueStückzahl);
        }
        showProductBrand.setText(dataMarke.toString());
        showProductName.setText(dataProduktbezeichnung.toString());
        showProductExpireDate.setText(dataAblaufdatum.toString());
        showProductCount.setText(dataStückzahl.toString());
    }

    public void loadMainActivityUp(){
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivityUp();
        } return true;
    }

    public void updateProduct(){
        String brand = showProductBrand.getText().toString();
        String name = showProductName.getText().toString();
        String ablauf = showProductExpireDate.getText().toString();
        String anzahl = showProductCount.getText().toString();

        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseProduct.execSQL("Update product SET marke = '"+ brand + "', produktbezeichnung = '" + name + "', ablaufdatum = '" + ablauf + "', stückzahl = '" + anzahl +"' WHERE rowid = '" + getIdFromPreference +"' ");
        databaseProduct.close();
    }

    public void deleteProduct(){
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseProduct.execSQL("DELETE FROM product WHERE rowid = '" + getIdFromPreference + "'");
        databaseProduct.close();
        Toast.makeText(this, "Produkt gelöscht!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUpdateProduct){
            updateProduct();
            loadMainActivityUp();
        } else if(view.getId() == R.id.btnDeleteProduct){
            deleteProduct();
            loadMainActivityUp();
        }
    }
}

