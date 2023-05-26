package com.example.sim;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.dashboard.DashboardFragment;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editBrand, editProductTitle, editExpireDate, editPieceNumber;
    Button btnCreateProduct;

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
    }

    public void addProduct(String marke, String bezeichnung, String ablaufdatum, String stückzahl){
        if(checkProduct(marke, bezeichnung, ablaufdatum)){
            createProduct(marke, bezeichnung, ablaufdatum, stückzahl);
        } else {
            Toast.makeText(getApplicationContext(), "Dieses Produkt Exisiert bereits!", Toast.LENGTH_SHORT).show();
        }

        editBrand.setText("");
        editProductTitle.setText("");
        editExpireDate.setText("");
        editPieceNumber.setText("");
    }

    public boolean checkProduct(String marke, String bezeichnung, String ablaufdatum){
        boolean okay = false;
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorProduct = databaseProduct.rawQuery("SELECT COUNT (*) FROM product WHERE marke = '"+marke+"' AND produktbezeichnung ='"+bezeichnung+"' AND ablaufdatum = '"+ablaufdatum+"'",null );
        cursorProduct.moveToFirst();
        if(cursorProduct.getInt(0) == 0){
            okay = true;
        }
        cursorProduct.close();
        databaseProduct.close();
        return okay;
    }

    public void createProduct(String marke, String bezeichnung, String ablaufdatum, String stückzahl){
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseProduct.execSQL("INSERT INTO product VALUES ('" + marke + "','" + bezeichnung + "','" + ablaufdatum + "','" + stückzahl + "')");
        databaseProduct.close();
    }

    public void loadMainActivity(){
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            loadMainActivity();
        } return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateProduct) {
            addProduct(editBrand.getText().toString(), editProductTitle.getText().toString(),editExpireDate.getText().toString(),editPieceNumber.getText().toString());
            loadMainActivity();
        }
    }
}
