package com.example.sim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoginRegister, btnLogin;

    final String databaseName = "/data/data/de.codeyourapp.myapplication/databases/meinedb.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    public void loadHomeFragment(){
        Intent inten = new Intent(this, HomeFragment.class);
        startActivity(inten);
        this.finish();
    }

    public void loadRegisterActivity(){
        Intent inten = new Intent(this, RegisterActivity.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin){
            setStayLoggedIn();
            loadHomeFragment();
        } else if(view.getId() == R.id.btnLoginRegister){
            loadRegisterActivity();
        }
    }
}
