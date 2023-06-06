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

/**
 * The main activity of the Smart Inventory Manager app.
 */
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

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * Initializes the extended floating action button and its sub-components.
     */
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
        // below is the sample action to handle add product FAB. It initiates the loadProductActivity Method.
        mAddProductFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadProductActivity();
                    }
                });
        // below is the sample action to handle add list FAB. It initiates the loadListActivity Method.
        mAddListFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadListActivity();
                    }
                });
    }

    //@Override
    //protected void onPause() {
    //    super.onPause();
    //    preferenceManager.setMomentLoggedIn(false);
    //}

    /**
     * Loads the login activity.
     */
    public void loadLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Loads the product activity.
     */
    public void loadProductActivity(){
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Loads the list activity.
     */
    public void loadListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Loads the personal activity.
     */
    public void loadPersonalActivity(){
        Intent intent = new Intent(this, PersonalActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     *
     * @param menu The options menu in which you place your items.
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu_nav, menu);
        return true;
    }

    /**
     * This is the so to say "onClick" Method for the MenuItem action_profile button. <br>
     *
     * When Clicked: <br>
     * <p>
     *     If the user is NOT logged in, the Login Activity will be called. <br>
     *     If the user IS logged in, the Profile Activity will be called.
     * </p>
     *
     * @param item The menu item that was selected.
     *
     */
    @Override
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
