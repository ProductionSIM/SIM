package com.example.sim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.ui.notifications.NotificationsFragment;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCreateList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnCreateList = (Button) findViewById(R.id.btnCreateList);
        btnCreateList.setOnClickListener(this);
    }

    public void loadNotificationFragment(){
        Intent inten = new Intent(this, NotificationsFragment.class);
        startActivity(inten);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateList) {
            loadNotificationFragment();
        }
    }
}