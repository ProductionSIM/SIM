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

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener{

    TextView showInfosUsername, showInfosPassword, showInfosFirstname, showInfosLastname;

    Button btnLogOut;
    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    PreferenceManager preferenceManager;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String KEY_EMAIL_USER = "emailUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        showInfosUsername = (TextView) findViewById(R.id.showInfosUsername);
        showUsername();
        showInfosPassword = (TextView) findViewById(R.id.showInfosPassword);
        showInfosPassword.setText(getIntent().getStringExtra("password"));

        showInfosFirstname = (TextView) findViewById(R.id.showInfosFirstName);


        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());

        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        Cursor cursorUser = databaseUser.rawQuery("SELECT password FROM user WHERE username = '" + showInfosUsername.getText().toString() + "'", null);
        Cursor cursorUserFirstname = databaseUser.rawQuery("SELECT firstname FROM user WHERE username = '" + showInfosUsername.getText().toString() + "'", null);

        showInfosPassword.setText(cursorUser.toString());
        showInfosFirstname.setText(cursorUserFirstname.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            loadMainActivity();
        } return true;
    }

    public void loadMainActivity(){
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
    }

    public void loadLoginActivity(){
        Intent inten = new Intent(this, LoginActivity.class);
        startActivity(inten);
        this.finish();
    }

    private void showUsername(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        showInfosUsername.setText(sharedPreferences.getString(KEY_EMAIL_USER, ""));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogOut){
            preferenceManager.setLoggedIn(false);
            preferenceManager.setMomentLoggedIn(false);
            loadLoginActivity();
        }
    }
}
