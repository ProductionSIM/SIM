package com.example.sim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener{

    TextView showInfosUsername, showInfosPassword;

    Button btnLogOut;
    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showInfosUsername = (TextView) findViewById(R.id.showInfosUsername);
        showInfosUsername.setText(getIntent().getStringExtra("email"));

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        preferenceManager = new PreferenceManager(getApplicationContext());

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogOut){
            preferenceManager.setLoggedIn(false);
            loadLoginActivity();
        }
    }
}
