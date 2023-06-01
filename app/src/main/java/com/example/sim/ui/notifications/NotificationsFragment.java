package com.example.sim.ui.notifications;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.sim.DatabaseHelper;
import com.example.sim.R;
import com.example.sim.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private ListView listViewItems;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> itemAdapter;
    private DatabaseHelper databaseHelper;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        listViewItems = view.findViewById(R.id.productViewItems);
        itemList = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);
        listViewItems.setAdapter(itemAdapter);

        databaseHelper = new DatabaseHelper(requireContext());
        updateItemList();

        return view;
    }

    private void updateItemList(){
        itemList.clear();

        Cursor cursor = databaseHelper.getAllDataList();
        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") Integer rowid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_LIST));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_LIST));
                @SuppressLint("Range") String creation = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREATION_LIST));
                @SuppressLint("Range") String storage = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STORAGE_LIST));

                itemList.add(rowid + " - " + name + " - " + creation + " - " + storage);
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