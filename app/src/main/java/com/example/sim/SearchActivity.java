package com.example.sim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * The SearchActivity class represents the activity for performing product searches.
 * It allows users to search for products based on various criteria and displays the search results.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> itemList;
    private ArrayAdapter<String> itemAdapter;
    private DatabaseHelper databaseHelper;

    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_PRODUCT_ID = "productid";


    private ArrayList<Integer> integerList;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";
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
        integerList = retrieveIntegerValuesFromDatabase();
        databaseHelper = new DatabaseHelper(this);

        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedValue = integerList.set(position, integerList.size());

                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_PRODUCT_ID, String.valueOf(selectedValue));
                editor.apply();

                Intent intent = new Intent(getBaseContext(), ShowProductInfosActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Initializes the Search Button in the AppBar as a SearchView which is used to search for products
     * @param menu The options menu in which you place your items.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu_search, menu);
        MenuItem searchViewitem = menu.findItem(R.id.appbar_search);
        SearchView searchView = (SearchView) searchViewitem.getActionView();
        searchView.setQueryHint("Suchen");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<Integer> retrieveIntegerValuesFromDatabase() {
        ArrayList<Integer> values = new ArrayList<>();

        // Replace with your own code to retrieve values from the database
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT rowid FROM product ", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int value = cursor.getInt(cursor.getColumnIndex("rowid"));
                values.add(value);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return values;
    }

    /**
     * Performs a search for products based on the specified query.
     * Displays the search results in the ListView.
     *
     * @param query The search query entered by the user.
     */
    public void performSearch(String query) {
        itemList.clear();
        boolean okay = false;
        SQLiteDatabase databaseProduct = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursor = databaseProduct.rawQuery("SELECT * FROM product WHERE marke LIKE '" + '%' + query + '%' + "' OR produktbezeichnung LIKE '" + '%' + query + '%' + "' OR ablaufdatum LIKE '" + '%' + query + '%' + "'OR stückzahl LIKE '" + '%' + query + '%' + "'OR mengeneinheit LIKE '" + '%' + query + '%' + "'OR kategorie LIKE '" + '%' + query + '%' + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            if (cursor.getString(0).equals(query)) {

            }
        }

        if (cursor.moveToFirst()) {
            do {
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

    /**
     * Loads the MainActivity.
     * This method is called when the home button is clicked.
     */
    public void loadMainActivity() {
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
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
    public void onClick(View v) {

    }
}
