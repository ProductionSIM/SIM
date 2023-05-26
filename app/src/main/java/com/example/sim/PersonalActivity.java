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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalActivity extends AppCompatActivity {

    TextView showInfosUsername, showInfosPassword;
    final String databaseName = "/data/data/com.example.sim/databases/SIM.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showInfosUsername = (TextView) findViewById(R.id.showInfosUsername);
        showInfosPassword = (TextView) findViewById(R.id.showInfosPassword);
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
}
