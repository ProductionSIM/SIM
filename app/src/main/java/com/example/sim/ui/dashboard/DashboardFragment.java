package com.example.sim.ui.dashboard;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sim.DatabaseHelper;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment{

    private FragmentDashboardBinding binding;
    private ListView listViewItems;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> itemAdapter;
    private DatabaseHelper databaseHelper;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        listViewItems = view.findViewById(R.id.productViewItems);
        itemList = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);
        listViewItems.setAdapter(itemAdapter);

        updateItemList();

        return view;
    }

    private void updateItemList(){
        itemList.clear();

        Cursor cursor = databaseHelper.getAllDataProduct();
        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String brand = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BRAND_PRODUCT));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_PRODUCT));
                @SuppressLint("Range") String expireDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EXPIRE_DATE_PRODUCT));
                @SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNT_PRODUCT));

                itemList.add(brand + " - " + name + " - " + expireDate + " - " + count);
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