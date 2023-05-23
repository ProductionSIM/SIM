package com.example.sim;

import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnLoginRegister = (Button) findViewById(R.id.btnLoginRegister);
        btnLoginRegister.setOnClickListener(this);


    }

    public void setStayLoggedIn(){
        SharedPreferences prefStayLoggedIn = getSharedPreferences("loggedin", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefStayLoggedIn.edit();
        editor.putBoolean("loggedin", false);
        editor.commit();
    }

    public void loadMainActivity(){
        Toast.makeText(getApplicationContext(), "Erfolgreich Eingeloggt", Toast.LENGTH_SHORT).show();
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        this.finish();
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
            setStayLoggedIn();
            loadMainActivity();
        } else if(view.getId() == R.id.btnLoginRegister){
            loadRegisterActivity();
        }
    }
}
