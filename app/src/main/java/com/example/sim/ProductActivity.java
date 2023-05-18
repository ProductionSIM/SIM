package com.example.sim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.dashboard.DashboardFragment;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCreateProduct;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
        btnCreateProduct.setOnClickListener(this);
    }

    public void loadDashboardFragment(){
        Intent inten = new Intent(this, DashboardFragment.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateProduct) {
            loadDashboardFragment();
        }
    }
}
