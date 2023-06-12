package com.example.sim;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The ListActivity class represents the activity for creating a new list.
 * It allows the user to enter information about the list and creates it in the database.
 */
public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editListName, editCreationDate, editStorageLocation;
    Button btnCreateList, dateButton;

    Date currentDate;
    String formattedDate;
    SimpleDateFormat dateFormat;

    // Get the current date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Neue Liste erstellen");

        editListName = (EditText) findViewById(R.id.editListName);
        editCreationDate = (EditText) findViewById(R.id.editCreationDate);
        editStorageLocation = (EditText) findViewById(R.id.editStorageLocation);

        btnCreateList = (Button) findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(this);
        dateButton = (Button) findViewById(R.id.dateButton);

        currentDate = new Date();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        formattedDate = dateFormat.format(currentDate);
        editCreationDate.setText(formattedDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Get the current selected date from the spinner
                // Create a DatePickerDialog to choose a new date
                // Set initial date values
                DatePickerDialog datePickerDialog = new DatePickerDialog(ListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the selected date EditText with the chosen date
                        String formattedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, month + 1, year);
                        editCreationDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });
    }

    /**
     * Adds a new list to the database.
     *
     * @param listenname    The name of the list.
     * @param erstelldatum  The creation date of the list.
     * @param lagerort      The storage location of the list.
     */
    public void addList(String listenname, String erstelldatum, String lagerort) {
        if (checkList(listenname, lagerort)) {
            createList(listenname, erstelldatum, lagerort);
        } else {
            Toast.makeText(getApplicationContext(), "Diese Liste existiert bereits!", Toast.LENGTH_SHORT).show();
        }

        editListName.setText("");
        editCreationDate.setText("");
        editStorageLocation.setText("");
    }

    /**
     * Checks if a list with the given name and storage location already exists in the database.
     *
     * @param listenname  The name of the list.
     * @param lagerort    The storage location of the list.
     * @return True if the list does not exist in the database, false otherwise.
     */
    public boolean checkList(String listenname, String lagerort) {
        boolean okay = false;
        SQLiteDatabase databaseList = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorList = databaseList.rawQuery("SELECT COUNT (*) FROM list WHERE listenname= '" + listenname + "' AND lagerort='" + lagerort + "'", null);
        cursorList.moveToFirst();
        if (cursorList.getInt(0) == 0) {
            okay = true;
        }
        cursorList.close();
        databaseList.close();
        return okay;
    }

    /**
     * Creates a new list in the database.
     *
     * @param listenname    The name of the list.
     * @param erstelldatum  The creation date of the list.
     * @param lagerort      The storage location of the list.
     */
    public void createList(String listenname, String erstelldatum, String lagerort) {
        SQLiteDatabase databaseList = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseList.execSQL("INSERT INTO list (listenname, erstelldatum, lagerort) VALUES ('" + listenname + "','" + erstelldatum + "','" + lagerort + "')");
        databaseList.close();
    }

    /**
     * Loads the main activity.
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
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateList) {
            addList(editListName.getText().toString(), editCreationDate.getText().toString(), editStorageLocation.getText().toString());
            loadMainActivity();
        }
    }
}
