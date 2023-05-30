package com.example.sim;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_FILE_NAME = "MyPreferences";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context){
        mySharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
    }

    public void setLoggedIn(boolean isLoggedIn){
        editor.putBoolean(KEY_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return mySharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }
}
