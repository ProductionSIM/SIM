package com.example.sim.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> dataList;

    public CustomSpinnerAdapter(Context context, List<String> dataList) {
        super(context, android.R.layout.simple_spinner_item, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextSize(20);
        view.setTextColor(Color.WHITE); // Set the desired text color
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextSize(22);
        view.setTextColor(Color.WHITE); // Set the desired text color
        return view;
    }
}
