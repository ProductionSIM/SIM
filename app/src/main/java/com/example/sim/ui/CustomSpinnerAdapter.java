package com.example.sim.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * CustomSpinnerAdapter is a custom ArrayAdapter for populating a Spinner with data.
 */
public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> dataList;

    /**
     * Constructs a new CustomSpinnerAdapter.
     *
     * @param context  The current context.
     * @param dataList The List of data to be displayed in the Spinner.
     */
    public CustomSpinnerAdapter(Context context, List<String> dataList) {
        super(context, android.R.layout.simple_spinner_item, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    /**
     * Returns the custom view for the Spinner item in the closed state (not expanded).
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The custom view for the Spinner item in the closed state.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextSize(20);
        view.setTextColor(Color.WHITE); // Set the desired text color
        return view;
    }

    /**
     * Returns the custom view for the Spinner item in the expanded state (drop-down view).
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The custom view for the Spinner item in the expanded state.
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextSize(22);
        view.setTextColor(Color.WHITE); // Set the desired text color
        return view;
    }
}
