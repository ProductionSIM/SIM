package com.example.sim;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOkay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infos);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnOkay){

        }
    }
}
