package com.example.sim.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sim.DatabaseHelper;
import com.example.sim.R;
import com.example.sim.ShowProductInfosActivity;
import com.example.sim.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

/**
 * The DashboardFragment class represents the fragment for the Products screen.
 * It displays a list of items from the database.
 */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ListView listViewItems;
    private TextView noProducts;

    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_PRODUCT_ID = "productid";
    public static final String KEY_EMAIL_USER = "emailUser";

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    private String getUsername;
    private SharedPreferences sharedPreferences;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> itemAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<Integer> integerList;
    private ArrayAdapter<Integer> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        listViewItems = view.findViewById(R.id.productViewItems);
        noProducts = view.findViewById(R.id.noProducts);
        integerList = retrieveIntegerValuesFromDatabase();
        listViewItems.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    private int lastFirstVisibleItem = 0;
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }
                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        // Update the first visible item
                        lastFirstVisibleItem = firstVisibleItem;
                    }
                }
        );

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedValue = integerList.set(position, integerList.size());

                SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_PRODUCT_ID, String.valueOf(selectedValue));
                editor.apply();

                Intent intent = new Intent(getActivity(), ShowProductInfosActivity.class);
                startActivity(intent);
            }
        });

        itemList = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, integerList);
        listViewItems.setAdapter(adapter);
        listViewItems.setAdapter(itemAdapter);
        databaseHelper = new DatabaseHelper(requireContext());
        updateItemList();

        return view;
    }

    /**
     * Retrieves the integer values from the database.
     *
     * @return The list of integer values retrieved from the database.
     */
    private ArrayList<Integer> retrieveIntegerValuesFromDatabase() {
        ArrayList<Integer> values = new ArrayList<>();
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        getUsername =  sharedPreferences.getString(KEY_EMAIL_USER, "");

        // Replace with your own code to retrieve values from the database
        SQLiteDatabase db = requireContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT rowid FROM product WHERE benutzername = '" + getUsername +"'", null);

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
     * Updates the list of items displayed in the Product Fragment.
     */
    private void updateItemList() {
        itemList.clear();

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        getUsername =  sharedPreferences.getString(KEY_EMAIL_USER, "");

        SQLiteDatabase databaseProduct = getContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursor = databaseProduct.rawQuery("SELECT marke, produktbezeichnung, ablaufdatum, stückzahl, mengeneinheit FROM product WHERE benutzername = '" + getUsername + "'", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String brand = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BRAND_PRODUCT));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_PRODUCT));
                @SuppressLint("Range") String expireDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EXPIRE_DATE_PRODUCT));
                @SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNT_PRODUCT));
                @SuppressLint("Range") String measureunit = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MEASURE_UNIT_PRODUCT));

                itemList.add(brand + " - " + name + " - " + expireDate + " - " + count + " " + measureunit);
            } while (cursor.moveToNext());
        }

        if(integerList.size() == 0){
            listViewItems.setVisibility(View.GONE);
            noProducts.setVisibility(View.VISIBLE);
        } else if(integerList.size() >= 1){
            listViewItems.setVisibility(View.VISIBLE);
            noProducts.setVisibility(View.GONE);
        }

        cursor.close();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
