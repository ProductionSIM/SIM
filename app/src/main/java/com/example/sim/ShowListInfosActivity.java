package com.example.sim;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Calendar;
import java.util.Locale;

/**
 * This activity displays and allows the user to edit the information of a specific list.
 */
public class ShowListInfosActivity extends AppCompatActivity implements View.OnClickListener {

    EditText EditListName, listCreationDate, EditStorageLocation;

    Button btnUpdateList, dateButton, btnDeleteList;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";
    PreferenceManager preferenceManager;
    DatabaseHelper databaseHelper;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_LIST_ID = "listid";
    public static final String KEY_EMAIL_USER = "emailUser";
    String valueListname, valueCreationDate, valueStorage, getIdFromPreference, getUsernameFromPreference;
    StringBuilder dataListname, dataCreationDate, dataStorage;
    Cursor cursorUserListname, cursorUserCreationDate, cursorUserStorage;
    SQLiteDatabase databaseUser;
    SharedPreferences sharedPreferences;

    // Get the current date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_infos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");

        btnUpdateList = (Button) findViewById(R.id.btnUpdateList);
        btnUpdateList.setOnClickListener(this);

        btnDeleteList = (Button) findViewById(R.id.btnDeleteList);
        btnDeleteList.setOnClickListener(this);

        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current selected date from the spinner
                // Create a DatePickerDialog to choose a new date
                // Set initial date values
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShowListInfosActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the selected date EditText with the chosen date
                        String formattedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, month + 1, year);
                        listCreationDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        EditListName = (EditText) findViewById(R.id.EditListname);
        listCreationDate = (EditText) findViewById(R.id.listCreationDate);
        EditStorageLocation = (EditText) findViewById(R.id.EditStorageLocation);

        preferenceManager = new PreferenceManager(getApplicationContext());

        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        getIdFromPreference = sharedPreferences.getString(KEY_LIST_ID, "");
        getUsernameFromPreference = sharedPreferences.getString(KEY_EMAIL_USER, "");

        databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        cursorUserListname = databaseUser.rawQuery("SELECT listenname FROM list WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserCreationDate = databaseUser.rawQuery("SELECT erstelldatum FROM list WHERE rowid = '" + getIdFromPreference + "'", null);
        cursorUserStorage = databaseUser.rawQuery("SELECT lagerort FROM list WHERE rowid = '" + getIdFromPreference + "'", null);

        dataListname = new StringBuilder();
        dataCreationDate = new StringBuilder();
        dataStorage = new StringBuilder();

        while (cursorUserListname.moveToNext() && cursorUserCreationDate.moveToNext() && cursorUserStorage.moveToNext()) {
            valueListname = cursorUserListname.getString(cursorUserListname.getColumnIndexOrThrow("listenname"));
            valueCreationDate = cursorUserCreationDate.getString(cursorUserCreationDate.getColumnIndexOrThrow("erstelldatum"));
            valueStorage = cursorUserStorage.getString(cursorUserStorage.getColumnIndexOrThrow("lagerort"));

            dataListname.append(valueListname);
            dataCreationDate.append(valueCreationDate);
            dataStorage.append(valueStorage);
        }
        EditListName.setText(dataListname.toString());
        listCreationDate.setText(dataCreationDate.toString());
        EditStorageLocation.setText(dataStorage.toString());
    }

    /**
     * Loads the MainActivity.
     */
    public void loadMainActivityUp() {
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivityUp();
        }
        return true;
    }

    /**
     * Updates the list in the database with the entered information.
     */
    public void updateList() {
        String listName = EditListName.getText().toString();
        String creationDate = listCreationDate.getText().toString();
        String storageLocation = EditStorageLocation.getText().toString();

        SQLiteDatabase databaseList = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseList.execSQL("Update list SET listenname = '" + listName + "', erstelldatum = '" + creationDate + "', lagerort = '" + storageLocation + "' WHERE rowid = '" + getIdFromPreference + "' AND benutzername = '" + getUsernameFromPreference + "'");
        databaseList.close();
        Toast.makeText(this, "Liste aktualisiert!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Deletes the list from the database.
     */
    public void deleteList() {
        SQLiteDatabase databaseList = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseList.execSQL("DELETE FROM list WHERE rowid = '" + getIdFromPreference + "'AND benutzername = '" + getUsernameFromPreference + "'");
        databaseList.close();
        Toast.makeText(this, "Liste gelöscht!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnUpdateList) {
            updateList();
            loadMainActivityUp();
        } else if (view.getId() == R.id.btnDeleteList) {
            deleteList();
            loadMainActivityUp();
        }
    }
}
