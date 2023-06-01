package com.example.sim;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_FILE_NAME = "MyPreferences";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_FIRSTNAME = "firstnameUser";
    private static final String KEY_USER_LASTNAME = "lastnameUser";
    private static final String KEY_USER_EMAIL = "emailUser";
    private static final String KEY_USER_PASSWORD = "passwordUser";

    public SharedPreferences mySharedPreferences;
    public SharedPreferences.Editor editor;

    public PreferenceManager(Context context){
        mySharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
    }

    //SharedPreference for UserStayLoggedIn TRUE OR FALSE
    public void setLoggedIn(boolean isLoggedIn){
        editor.putBoolean(KEY_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return mySharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    //SharedPreference for Firstname
    public void setFirstname(String firstnameSave){
        editor.putString(KEY_USER_FIRSTNAME, firstnameSave);
        editor.apply();
    }
    public String isFirstnameUser(){ return mySharedPreferences.getString(KEY_USER_FIRSTNAME, "");}

    //SharedPreference for Lastname
    public void setLastname(String lastnameSave){
        editor.putString(KEY_USER_LASTNAME, lastnameSave);
        editor.apply();
    }
    public String isLastnameUser(){ return mySharedPreferences.getString(KEY_USER_LASTNAME, "");}

    //SharedPreference for Email
    public void setEmail(String emailSave){
        editor.putString(KEY_USER_EMAIL, emailSave);
        editor.apply();
    }
    public String isEmailUser(){ return mySharedPreferences.getString(KEY_USER_EMAIL, "");}

    //SharedPreference for Password
    public void setPassword(String passwordSave){
        editor.putString(KEY_USER_PASSWORD, passwordSave);
        editor.apply();
    }
    public String isPasswordUser(){ return mySharedPreferences.getString(KEY_USER_PASSWORD, "");}
}
