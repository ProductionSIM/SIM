package com.example.sim.ui.home;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sim.DatabaseHelper;
import com.example.sim.R;
import com.example.sim.databinding.FragmentHomeBinding;

import java.util.ArrayList;

/**
 * The HomeFragment class represents the fragment for the Home screen.
 * It displays a list of user data retrieved from the database.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listviewHome;

    private ArrayList<String> itemListHome;
    private ArrayAdapter<String> itemAdapterHome;

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listviewHome = view.findViewById(R.id.listviewHome);
        itemListHome = new ArrayList<>();
        itemAdapterHome = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemListHome);
        listviewHome.setAdapter(itemAdapterHome);

        databaseHelper = new DatabaseHelper(requireContext());

        updateItemList();
        return root;
    }

    /**
     * Updates the list of items displayed in the Home Fragment.
     */
    private void updateItemList() {
        itemListHome.clear();

        Cursor cursor = databaseHelper.getAllDataUser();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));

                itemListHome.add(name + " - " + password);
            } while (cursor.moveToNext());
        }

        cursor.close();
        itemAdapterHome.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
