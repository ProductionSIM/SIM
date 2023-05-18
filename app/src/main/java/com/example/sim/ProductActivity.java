package com.example.sim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.home.HomeFragment;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnProductSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnProductSubmit = (Button) findViewById(R.id.btnProductSubmit);
        btnProductSubmit.setOnClickListener(this);
    }

    public void loadHomeFragment(){
        Intent inten = new Intent(this, HomeFragment.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin){
            loadHomeFragment();
        }

    }
}
