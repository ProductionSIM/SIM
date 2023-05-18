package com.example.sim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.home.HomeFragment;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCreateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        btnCreateList = (Button) findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(this);
    }

    public void loadHomeFragment(){
        Intent inten = new Intent(this, HomeFragment.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateList){
            loadHomeFragment();
        }
    }
}
