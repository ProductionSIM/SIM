package com.example.sim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> itemList;
    private ArrayAdapter<String> itemAdapter;
    private DatabaseHelper databaseHelper;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    SearchView searchView;
    ListView listViewSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Produktsuche");

        listViewSearch = (ListView) findViewById(R.id.listViewSearch);
        itemList = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listViewSearch.setAdapter(itemAdapter);
        databaseHelper = new DatabaseHelper(this);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                return false;
            }
        });
    }

    private void performSearch(String query){
        itemList.clear();
        boolean okay = false;
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursor = databaseProduct.rawQuery("SELECT * FROM product WHERE marke LIKE '"+'%' + query +'%'+ "' OR produktbezeichnung LIKE '"+'%' + query +'%'+ "' OR ablaufdatum LIKE '"+'%' + query +'%'+ "'OR stückzahl LIKE '"+'%' + query +'%'+ "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            if (cursor.getString(0).equals(query)) {

            }
        }

        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") Integer rowid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_PRODUCT));
                @SuppressLint("Range") String brand = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BRAND_PRODUCT));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_PRODUCT));
                @SuppressLint("Range") String expireDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EXPIRE_DATE_PRODUCT));
                @SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNT_PRODUCT));

                itemList.add(rowid + " - " + brand + " - " + name + " - " + expireDate + " - " + count);
            } while (cursor.moveToNext());
        }

        cursor.close();
        databaseProduct.close();
        itemAdapter.notifyDataSetChanged();
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
    public void onClick(View v) {

    }
}
