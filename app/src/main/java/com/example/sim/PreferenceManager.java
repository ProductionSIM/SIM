package com.example.sim;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The PreferenceManager class provides methods for managing shared preferences in the app.
 * It allows storing and retrieving boolean values related to user login status.
 */
public class PreferenceManager {
    private static final String PREF_FILE_NAME = "MyPreferences";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    public SharedPreferences mySharedPreferences;
    public SharedPreferences.Editor editor;

    /**
     * Constructs a new instance of the PreferenceManager.
     *
     * @param context The context of the application.
     */
    public PreferenceManager(Context context) {
        mySharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
    }

    /**
     * Sets the logged-in status of the user.
     *
     * @param isLoggedIn A boolean indicating whether the user is logged in.
     */
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    /**
     * Checks if the user is currently logged in.
     *
     * @return A boolean indicating the login status.
     */
    public boolean isLoggedIn() {
        return mySharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }
}
