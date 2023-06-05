package com.example.sim;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sim.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FloatingActionButton mAddListFab, mAddProductFab;
    ExtendedFloatingActionButton mAddFab;
    TextView addListActionText, addProductActionText;
    // to check whether sub FABs are visible or not
    Boolean isAllFabsVisible;

    PreferenceManager preferenceManager;
    public static final String SHARED_PREF = "MyPreferences";
    public static final String SHARED_PREFER = "MomentLogged";
    // for checkbox (set false if button Abmelden was clicked)
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    // while using app (set false when app was closed)
    private static final String KEY_STAY_LOGGED_IN = "momentLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        CreateExtendedFAB();
    }

    public void CreateExtendedFAB(){
        mAddFab = findViewById(R.id.add_fab);
        mAddListFab = findViewById(R.id.add_list_fab);
        mAddProductFab = findViewById(R.id.add_product_fab);
        addListActionText = findViewById(R.id.add_list_action_text);
        addProductActionText = findViewById(R.id.add_product_action_text);
        // Now set all the FABs and all the action name texts as GONE
        mAddListFab.setVisibility(View.GONE);
        mAddProductFab.setVisibility(View.GONE);
        addListActionText.setVisibility(View.GONE);
        addProductActionText.setVisibility(View.GONE);
        // make the boolean variable as false, as all the action name texts and all the sub FABs are invisible
        isAllFabsVisible = false;
        // Set the Extended floating action button to shrinked state initially
        mAddFab.shrink();
        // We will make all the FABs and action name texts visible only when Parent FAB button is clicked So we have to handle the Parent FAB button first, by using setOnClickListener you can see below
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {
                            // when isAllFabsVisible becomes true make all the action name texts and FABs VISIBLE.
                            mAddListFab.show();
                            mAddProductFab.show();
                            addListActionText.setVisibility(View.VISIBLE);
                            addProductActionText.setVisibility(View.VISIBLE);
                            // Now extend the parent FAB, as user clicks on the shrinked parent FAB
                            mAddFab.extend();
                            // make the boolean variable true as we have set the sub FABs visibility to GONE
                            isAllFabsVisible = true;
                        } else {
                            // when isAllFabsVisible becomes true make all the action name texts and FABs GONE.
                            mAddListFab.hide();
                            mAddProductFab.hide();
                            addListActionText.setVisibility(View.GONE);
                            addProductActionText.setVisibility(View.GONE);
                            // Set the FAB to shrink after user closes all the sub FABs
                            mAddFab.shrink();
                            // make the boolean variable false as we have set the sub FABs visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });
        // below is the sample action to handle add product FAB. Here it shows simple Toast msg. The Toast will be shown only when they are visible and only when user clicks on them
        mAddProductFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadProductActivity();
                    }
                });
        // below is the sample action to handle add list FAB. Here it shows simple Toast msg The Toast will be shown only when they are visible and only when user clicks on them
        mAddListFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadListActivity();
                    }
                });
    }

    //@Override
    //protected void onDestroy(){
    //    super.onDestroy();
    //    preferenceManager.setMomentLoggedIn(false);
    //}

    // Methode für den Aufruf der LoginActivity
    public void loadLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    // Methode für den Aufruf der ProductActivity
    public void loadProductActivity(){
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
        this.finish();
    }

    // Methode für den Aufruf der ListActivity
    public void loadListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        this.finish();
    }

    // Methode für den Aufruf der PersonalActivity
    public void loadPersonalActivity(){
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu_nav, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_profile) {
           if(preferenceManager.isLoggedIn() == false && preferenceManager.isMomentLoggedIn() == false){
               // User is already logged in, display user data
               loadLoginActivity();
           } else if(preferenceManager.isLoggedIn() == true && preferenceManager.isMomentLoggedIn() == true){
               // simulate login process
               loadPersonalActivity();
           } else if(preferenceManager.isMomentLoggedIn() == false && preferenceManager.isLoggedIn() == true){
               loadPersonalActivity();
           } else if(preferenceManager.isMomentLoggedIn() == true && preferenceManager.isLoggedIn() == false){
               loadPersonalActivity();
           }
        }
        return true;
    }

}