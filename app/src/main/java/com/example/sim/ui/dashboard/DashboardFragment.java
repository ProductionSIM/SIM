package com.example.sim.ui.dashboard;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.sim.DatabaseHelper;
import com.example.sim.R;
import com.example.sim.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

/**
 * The DashboardFragment class represents the fragment for the Products screen.
 * It displays a list of items from the database.
 */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ListView listViewItems;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> itemAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        listViewItems = view.findViewById(R.id.productViewItems);
        itemList = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);
        listViewItems.setAdapter(itemAdapter);
        databaseHelper = new DatabaseHelper(requireContext());
        updateItemList();

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

        return view;
    }

    /**
     * Updates the list of items displayed in the Product Fragment.
     */
    private void updateItemList() {
        itemList.clear();

        Cursor cursor = databaseHelper.getAllDataProduct();
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
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
