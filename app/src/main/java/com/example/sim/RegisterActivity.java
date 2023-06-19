package com.example.sim;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The RegisterActivity class handles the user registration process.
 * It allows users to create a new account by providing their personal details and credentials.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailRegister, pwRegister, pwConfRegister, editFirstname, editLastname;

    Button register;

    PreferenceManager preferenceManager;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrieren");

        // Initialize UI elements
        emailRegister = findViewById(R.id.emailRegister);
        pwRegister = findViewById(R.id.pwRegister);
        pwConfRegister = findViewById(R.id.pwConfRegister);
        editFirstname = findViewById(R.id.editFirstname);
        editLastname = findViewById(R.id.editLastname);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    /**
     * Handles the registration process.
     *
     * @param firstname The user's first name.
     * @param lastname  The user's last name.
     * @param username  The chosen username for the account.
     * @param password  The chosen password for the account.
     * @param passwordC The password confirmation entered by the user.
     */
    public void registration(String firstname, String lastname, String username, String password, String passwordC) {
        if (checkUserNameIsOkay(username) && passwordConfirmation(password, passwordC)) {
            createAccount(firstname, lastname, username, password);
        } else if (checkUserNameIsOkay(username)) {
            Toast.makeText(getApplicationContext(), "Der Benutzername ist schon vergeben!", Toast.LENGTH_SHORT).show();
        } else if (passwordConfirmation(password, passwordC)) {
            Toast.makeText(getApplicationContext(), "Das Passwort stimmt nicht überein!", Toast.LENGTH_SHORT).show();
        }
        emailRegister.setText("");
        pwRegister.setText("");
        pwConfRegister.setText("");
        editFirstname.setText("");
        editLastname.setText("");
    }

    /**
     * Hashes the password using BCrypt.
     *
     * @return The hashed password.
     */
    public String hashPassword() {
        String hashedPassword = BCrypt.hashpw(pwRegister.getText().toString(), BCrypt.gensalt());
        return hashedPassword;
    }

    /**
     * Checks if the chosen username is available.
     *
     * @param username The username to check.
     * @return True if the username is available, false otherwise.
     */
    public boolean checkUserNameIsOkay(String username) {
        boolean okay = false;
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorUser = databaseUser.rawQuery("SELECT COUNT (*) FROM user WHERE email = '" + username + "'", null);
        cursorUser.moveToFirst();
        if (cursorUser.getInt(0) == 0) {
            okay = true;
        } else {
            return false;
        }
        cursorUser.close();
        databaseUser.close();
        return okay;
    }

    /**
     * Checks if the password matches the password confirmation.
     *
     * @param password  The password entered by the user.
     * @param passwordC The password confirmation entered by the user.
     * @return True if the passwords match, false otherwise.
     */
    public boolean passwordConfirmation(String password, String passwordC) {
        boolean confirm = false;
        if (password.equals(passwordC)) {
            confirm = true;
        }
        return confirm;
    }

    /**
     * Creates a new account and inserts the user's details into the database.
     *
     * @param firstname The user's first name.
     * @param lastname  The user's last name.
     * @param username  The chosen username for the account.
     * @param password  The chosen password for the account.
     */
    public void createAccount(String firstname, String lastname, String username, String password) {
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseUser.execSQL("INSERT INTO user (firstname, lastname, email, password) VALUES ('" + firstname + "','" + lastname + "','" + username + "','" + password + "')");
        databaseUser.close();
    }

    /**
     * Loads the Login Activity.
     */
    public void loadLoginActivity() {
        Intent inten = new Intent(this, LoginActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadLoginActivity();
        }
        return true;
    }

    /**
     * Calls {@link #registration} and returns to the Login Activity.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            registration(editFirstname.getText().toString(), editLastname.getText().toString(), emailRegister.getText().toString(), pwRegister.getText().toString(), pwConfRegister.getText().toString());
            loadLoginActivity();
        }
    }
}
