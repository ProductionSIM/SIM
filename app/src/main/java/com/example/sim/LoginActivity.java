package com.example.sim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoginRegister, btnLogin;
    CheckBox cBstayLoggedIn;
    EditText editEmailLogIn, editPasswordLogin;
    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";
    PreferenceManager preferenceManager;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String SHARED_PREFER="MomentLogged";
    public static final String KEY_EMAIL_USER = "emailUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editEmailLogIn = (EditText) findViewById(R.id.editEmailLogIn);
        editPasswordLogin = (EditText) findViewById(R.id.editPasswordLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        cBstayLoggedIn = (CheckBox) findViewById(R.id.cBstayLoggedIn);

        btnLoginRegister = (Button) findViewById(R.id.btnLoginRegister);
        btnLoginRegister.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    public void login(String username, String password) {
        if(checkLogIn(username, password)){
            if (cBstayLoggedIn.isChecked()) {
                preferenceManager.setLoggedIn(true);
            }
            preferenceManager.setMomentLoggedIn(true);
            loadPersonalActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Benutzername oder Passwort sind falsch!", Toast.LENGTH_SHORT).show();
        }
        editPasswordLogin.setText("");
    }

    public boolean checkLogIn(String username, String password) {
        boolean okay = false;

        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorUser = databaseUser.rawQuery("SELECT password FROM user WHERE username = '" + username + "'", null);
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

    public void loadMainActivityUp(){
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    public void loadRegisterActivity(){
        Intent inten = new Intent(this, RegisterActivity.class);
        startActivity(inten);
        this.finish();
    }

    public void loadPersonalActivity(){
        Intent inten = new Intent(this, PersonalActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivityUp();
        } return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin){
            login(editEmailLogIn.getText().toString(), editPasswordLogin.getText().toString());
     //TEST
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL_USER, editEmailLogIn.getText().toString());
            editor.apply();
        } else if(view.getId() == R.id.btnLoginRegister) {
                loadRegisterActivity();
        }
    }


}
