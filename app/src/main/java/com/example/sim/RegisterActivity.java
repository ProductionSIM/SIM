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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailRegister, pwRegister, pwConfRegister;

    Button register;

    String KEY_username, KEY_password, TABLE_Name="user";

    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailRegister = (EditText) findViewById(R.id.emailRegister);
        pwRegister = (EditText) findViewById(R.id.pwRegister);
        pwConfRegister = (EditText) findViewById(R.id.pwConfRegister);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    public void registration(String username, String password, String passwordC) {
        if (checkUserNameIsOkay(username) && passwordConfirmation(password, passwordC)) {
            createAccount(username, password);
        } else if (checkUserNameIsOkay(username)) {
            Toast.makeText(getApplicationContext(), "Der Benutzername ist schon vergeben!", Toast.LENGTH_SHORT).show();
        } else if (passwordConfirmation(password, passwordC)) {
            Toast.makeText(getApplicationContext(), "Das Passwort stimmt nicht überein!", Toast.LENGTH_SHORT).show();
        }
        emailRegister.setText("");
        pwRegister.setText("");
        pwConfRegister.setText("");
    }

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

    public boolean passwordConfirmation(String password, String passwordC) {
        boolean confirm = false;
        if (password.equals(passwordC)) {
            confirm = true;
        }
        return confirm;
    }

    public void createAccount(String username, String password) {
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseUser.execSQL("INSERT INTO user VALUES ('" + username + "','" + password + "')");
        databaseUser.close();
    }

    public void loadLoginActivity(){
        Intent inten = new Intent(this, LoginActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            loadLoginActivity();
        } return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register) {
            registration(emailRegister.getText().toString(), pwRegister.getText().toString(), pwConfRegister.getText().toString());
            loadLoginActivity();
        }
    }
}
