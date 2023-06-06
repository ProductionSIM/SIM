package com.example.sim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The PersonalActivity class represents the activity that displays personal information of the user.
 * It retrieves and displays the user's username, password, first name, and last name from the database.
 * It also provides an option to log out and navigate back to the main activity.
 */
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    TextView showInfosUsername, showInfosPassword, showInfosFirstname, showInfosLastname;

    Button btnLogOut;
    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    PreferenceManager preferenceManager;
    DatabaseHelper databaseHelper;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_EMAIL_USER = "emailUser";
    String valueFirstname, valueLastname, valuePassword, getUsername;
    StringBuilder dataFirstname, dataLastname, dataPassword;
    Cursor cursorUserFirstname, cursorUserLastname, cursorUserPassword;
    SQLiteDatabase databaseUser;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        showInfosUsername = (TextView) findViewById(R.id.showInfosUsername);
        showUsername();
        showInfosPassword = (TextView) findViewById(R.id.showInfosPassword);
        showInfosFirstname = (TextView) findViewById(R.id.showInfosFirstName);
        showInfosLastname = (TextView) findViewById(R.id.showInfosLastName);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());

        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        getUsername = sharedPreferences.getString(KEY_EMAIL_USER, "");

        databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        cursorUserFirstname = databaseUser.rawQuery("SELECT firstname FROM user WHERE username = '" + getUsername + "'", null);
        cursorUserLastname = databaseUser.rawQuery("SELECT lastname FROM user WHERE username = '" + getUsername + "'", null);
        cursorUserPassword = databaseUser.rawQuery("SELECT password FROM user WHERE username = '" + getUsername + "'", null);

        dataFirstname = new StringBuilder();
        dataLastname = new StringBuilder();
        dataPassword = new StringBuilder();

        while (cursorUserFirstname.moveToNext() && cursorUserLastname.moveToNext() && cursorUserPassword.moveToNext()) {
            valueFirstname = cursorUserFirstname.getString(cursorUserFirstname.getColumnIndexOrThrow("firstname"));
            valueLastname = cursorUserLastname.getString(cursorUserLastname.getColumnIndexOrThrow("lastname"));
            valuePassword = cursorUserPassword.getString(cursorUserPassword.getColumnIndexOrThrow("password"));

            dataFirstname.append(valueFirstname);
            dataLastname.append(valueLastname);
            dataPassword.append(valuePassword);
        }
        showInfosFirstname.setText(dataFirstname.toString());
        showInfosLastname.setText(dataLastname.toString());
        showInfosPassword.setText(dataPassword.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivity();
        }
        return true;
    }

    /**
     * Loads the main activity.
     */
    public void loadMainActivity() {
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    /**
     * Loads the login activity.
     */
    public void loadLoginActivity() {
        Intent inten = new Intent(this, LoginActivity.class);
        startActivity(inten);
        this.finish();
    }

    /**
     * Retrieves and displays the user's username.
     */
    private void showUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        showInfosUsername.setText(sharedPreferences.getString(KEY_EMAIL_USER, ""));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogOut) {
            preferenceManager.setLoggedIn(false);
            preferenceManager.setMomentLoggedIn(false);
            loadLoginActivity();
        }
    }
}
