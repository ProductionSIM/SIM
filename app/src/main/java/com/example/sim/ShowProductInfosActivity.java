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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShowProductInfosActivity extends AppCompatActivity implements View.OnClickListener{

    EditText showProductBrand, showProductName, showProductExpireDate, showProductCount;

    Button btnUpdateProduct, dateButton, btnDeleteProduct;

    Spinner editAmountUnits, editCategorySpinner;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";
    PreferenceManager preferenceManager;
    DatabaseHelper databaseHelper;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_PRODUCT_ID = "productid";
    public static final String KEY_EMAIL_USER = "emailUser";
    String valueMarke, valueProduktbezeichnung, valueAblaufdatum, valueStückzahl, valueMengeneinheit, valueKategorie, getIdFromPreference, getUsernameFromPreference;
    StringBuilder dataMarke, dataProduktbezeichnung, dataAblaufdatum, dataStückzahl, dataMengeneinheit, dataKategorie;
    Cursor cursorUserMarke, cursorUserProduktbezeichnung, cursorUserAblaufdatum, cursorUserStückzahl, cursorUserMengeneinheit, cursorUserKategorie;
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
        getSupportActionBar().setTitle(" ");

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

        showProductBrand = (EditText) findViewById(R.id.EditProductBrand);
        //showUsername();
        showProductName = (EditText) findViewById(R.id.EditProductName);
        showProductExpireDate = (EditText) findViewById(R.id.EditExpirationDate);
        showProductCount = (EditText) findViewById(R.id.EditAmount);

        preferenceManager = new PreferenceManager(getApplicationContext());

        editAmountUnits = (Spinner) findViewById(R.id.editAmountUnits);
        editCategorySpinner = (Spinner) findViewById(R.id.editCategorySpinner);

        // MeasureUnits
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getMeasureUnitsFromDatabase();
        List<String> dataList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex("mengeneinheit"));
                dataList.add(value);
            } while (cursor.moveToNext());
        }

        CustomSpinnerAdapter adapter1 = new CustomSpinnerAdapter(this, dataList);
        editAmountUnits.setAdapter(adapter1);

        // Category
        Cursor cursor1 = dbHelper.getCategoriesFromDatabase();
        List<String> dataList1 = new ArrayList<>();

        if(cursor1.moveToFirst()){
            do{
                @SuppressLint("Range") String value = cursor1.getString(cursor1.getColumnIndex("kategorie"));
                dataList1.add(value);
            } while (cursor1.moveToNext());
        }

        CustomSpinnerAdapter adapter2 = new CustomSpinnerAdapter(this, dataList1);
        editCategorySpinner.setAdapter(adapter2);

        sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        getIdFromPreference =  sharedPreferences.getString(KEY_PRODUCT_ID, "");
        getUsernameFromPreference = sharedPreferences.getString(KEY_EMAIL_USER,"");

        databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        cursorUserMarke = databaseUser.rawQuery("SELECT marke FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserProduktbezeichnung = databaseUser.rawQuery("SELECT produktbezeichnung FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserAblaufdatum = databaseUser.rawQuery("SELECT ablaufdatum FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserStückzahl = databaseUser.rawQuery("SELECT stückzahl FROM product WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserMengeneinheit = databaseUser.rawQuery("SELECT mengeneinheit FROM product WHERE rowid ='" + getIdFromPreference +"'", null);
        cursorUserKategorie = databaseUser.rawQuery("SELECT kategorie FROM product WHERE rowid = '" + getIdFromPreference +"'", null);

        dataMarke = new StringBuilder();
        dataProduktbezeichnung = new StringBuilder();
        dataAblaufdatum = new StringBuilder();
        dataStückzahl = new StringBuilder();
        dataMengeneinheit = new StringBuilder();
        dataKategorie = new StringBuilder();

        while(cursorUserMarke.moveToNext() && cursorUserProduktbezeichnung.moveToNext() && cursorUserAblaufdatum.moveToNext() && cursorUserStückzahl.moveToNext() && cursorUserMengeneinheit.moveToNext() && cursorUserKategorie.moveToNext()){
            valueMarke = cursorUserMarke.getString(cursorUserMarke.getColumnIndexOrThrow("marke"));
            valueProduktbezeichnung = cursorUserProduktbezeichnung.getString(cursorUserProduktbezeichnung.getColumnIndexOrThrow("produktbezeichnung"));
            valueAblaufdatum = cursorUserAblaufdatum.getString(cursorUserAblaufdatum.getColumnIndexOrThrow("ablaufdatum"));
            valueStückzahl = cursorUserStückzahl.getString(cursorUserStückzahl.getColumnIndexOrThrow("stückzahl"));
            valueMengeneinheit = cursorUserMengeneinheit.getString(cursorUserMengeneinheit.getColumnIndexOrThrow("mengeneinheit"));
            valueKategorie = cursorUserKategorie.getString(cursorUserKategorie.getColumnIndexOrThrow("kategorie"));

            dataMarke.append(valueMarke);
            dataProduktbezeichnung.append(valueProduktbezeichnung);
            dataAblaufdatum.append(valueAblaufdatum);
            dataStückzahl.append(valueStückzahl);
            dataMengeneinheit.append(valueMengeneinheit);
            dataKategorie.append(valueKategorie);
        }
        showProductBrand.setText(dataMarke.toString());
        showProductName.setText(dataProduktbezeichnung.toString());
        showProductExpireDate.setText(dataAblaufdatum.toString());
        showProductCount.setText(dataStückzahl.toString());

        String retrievedMeasureUnit = dataMengeneinheit.toString();
        String retrievedCategory = dataKategorie.toString();

        int position = -1;
        for (int i = 0; i < dataList.size(); i++){
            if(dataList.get(i).equals(retrievedMeasureUnit)){
                position = i;
                break;
            }
        }

        int positio = -1;
        for(int i = 0; i < dataList1.size(); i++){
            if(dataList1.get(i).equals(retrievedCategory)){
                positio = i;
                break;
            }
        }

        if(position != -1){
            editAmountUnits.setSelection(position);
        }

        if(positio != -1){
            editCategorySpinner.setSelection(positio);
        }
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
        String measure = editAmountUnits.getSelectedItem().toString();
        String category = editCategorySpinner.getSelectedItem().toString();

        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseProduct.execSQL("Update product SET marke = '"+ brand + "', produktbezeichnung = '" + name + "', ablaufdatum = '" + ablauf + "', stückzahl = '" + anzahl +"', mengeneinheit = '" + measure +"', kategorie = '" + category +"' WHERE rowid = '" + getIdFromPreference +"' AND benutzername = '" + getUsernameFromPreference +"'");
        databaseProduct.close();
    }

    public void deleteProduct(){
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseProduct.execSQL("DELETE FROM product WHERE rowid = '" + getIdFromPreference + "' AND benutzername = '" + getUsernameFromPreference +"'");
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

