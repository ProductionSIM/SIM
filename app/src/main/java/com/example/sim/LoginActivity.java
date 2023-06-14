package com.example.sim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The LoginActivity class handles the user login functionality.
 * It allows users to log in with their credentials and provides options for staying logged in.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoginRegister, btnLogin;
    EditText editEmailLogIn, editPasswordLogin;
    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";
    PreferenceManager preferenceManager;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_EMAIL_USER = "emailUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize UI elements
        editEmailLogIn = findViewById(R.id.editEmailLogIn);
        editPasswordLogin = findViewById(R.id.editPasswordLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        btnLoginRegister.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    /**
     * Handles the login process.<br>
     * It sets flags using shared preferences to know if you logged in.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    public void login(String username, String password) {
        if (checkLogIn(username, password)) {
            preferenceManager.setLoggedIn(true);
            loadPersonalActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Benutzername oder Passwort sind falsch!", Toast.LENGTH_SHORT).show();
        }
        editPasswordLogin.setText("");
    }

    /**
     * Checks if the entered login credentials are valid.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the login credentials are valid, false otherwise.
     */
    public boolean checkLogIn(String username, String password) {
        boolean okay = false;

        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorUser = databaseUser.rawQuery("SELECT password FROM user WHERE email = '" + username + "'", null);
        cursorUser.moveToFirst();

        if (cursorUser.getCount() > 0) {
            if (cursorUser.getString(0).equals(password)) {
                okay = true;
            }
        }
        cursorUser.close();
        databaseUser.close();
        return okay;
    }

    /**
     * Loads the MainActivity when navigating up from the LoginActivity.
     */
    public void loadMainActivityUp(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Loads the RegisterActivity to allow users to register a new account.
     */
    public void loadRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Loads the PersonalActivity for the logged-in user.
     */
    public void loadPersonalActivity(){
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Gives the Up Button in the App Bar its functionality.<br>
     * Because of this button being built into the AppBar it does not need its own menu to appear on it.<br>
     * By adding getSupportActionBar().setDisplayHomeAsUpEnabled(true); into {@link #onCreate}, it automatically appears on the AppBar.
     *
     * @param item The menu item that was selected.
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivityUp();
        }
        return true;
    }

    /**
     * If the Login Button is pressed, it will call {@link #login} to handle the states of being logged in.<br>
     * The Email (user.username in database) gets saved into SharedPreferences for use in the Personal Activity.<br>
     * <br>
     * If the Register button is pressed, the Register Activity will be called.
     * @see PersonalActivity
     * @see RegisterActivity
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin){
            login(editEmailLogIn.getText().toString(), editPasswordLogin.getText().toString());
            // Save Username using shared Preferences
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL_USER, editEmailLogIn.getText().toString());
            editor.apply();
        } else if(view.getId() == R.id.btnLoginRegister) {
            loadRegisterActivity();
        }
    }
}
