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


//This Class will create an Account if the inserted Email isn't already in use
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailRegister, pwRegister, pwConfRegister, editFirstname, editLastname;

    Button register;

    PreferenceManager preferenceManager;

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    // Method tells what is happening while creating this Class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailRegister = (EditText) findViewById(R.id.emailRegister);
        pwRegister = (EditText) findViewById(R.id.pwRegister);
        pwConfRegister = (EditText) findViewById(R.id.pwConfRegister);
        editFirstname = (EditText) findViewById(R.id.editFirstname);
        editLastname = (EditText) findViewById(R.id.editLastname);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    public void registration(String firstname, String lastname, String username, String password, String passwordC) {
        if (checkUserNameIsOkay(username) && passwordConfirmation(password, passwordC)) {
            createAccount(firstname, lastname, username, password);
            //preferenceManager.setFirstname(editFirstname.getText().toString());
            //preferenceManager.setLastname(editLastname.getText().toString());
            //preferenceManager.setEmail(emailRegister.getText().toString());
            //preferenceManager.setPassword(pwRegister.getText().toString());
            //createAccount(firstname, lastname, username, hashPassword());
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

    // Method for hashing the Password
    public String hashPassword (){
        String hashedPassword = BCrypt.hashpw(pwRegister.getText().toString(), BCrypt.gensalt());
        return hashedPassword;
    }

    // Method for checking if username is already registered
    public boolean checkUserNameIsOkay(String username) {
        boolean okay = false;
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorUser = databaseUser.rawQuery("SELECT COUNT (*) FROM user WHERE username = '" + username +"'", null);
        cursorUser.moveToFirst();
        if (cursorUser.getInt(0) == 0) {
            okay = true;
        } else{
            return false;
        }
        cursorUser.close();
        databaseUser.close();
        return okay;
    }

    // Method for checking if password is equal to password confirmation
    public boolean passwordConfirmation(String password, String passwordC) {
        boolean confirm = false;
        if (password.equals(passwordC)) {
            confirm = true;
        }
        return confirm;
    }

    // Method for creating account and Inserting Values into database
    public void createAccount(String firstname, String lastname, String username, String password) {
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseUser.execSQL("INSERT INTO user (firstname, lastname, username, password) VALUES ('" + firstname + "','" + lastname + "','" + username + "','" + password + "')");
        databaseUser.close();
    }

    // Method for loading Login Activity
    public void loadLoginActivity(){
        Intent inten = new Intent(this, LoginActivity.class);
        startActivity(inten);
        this.finish();
    }

    // Method for AppBar Buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadLoginActivity();
        } return true;
    }

    // Method for clicking on Buttons
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            registration(editFirstname.getText().toString(), editLastname.getText().toString(), emailRegister.getText().toString(), pwRegister.getText().toString(), pwConfRegister.getText().toString());
            loadLoginActivity();
        }
    }
}
