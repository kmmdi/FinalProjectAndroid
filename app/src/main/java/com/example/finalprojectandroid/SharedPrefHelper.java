package com.example.finalprojectandroid;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

/**
 * This is a helper class for shared preference reused across different activities
 * @author Kazi Muntaha Mahdi
 */
public class SharedPrefHelper {

    /**
     * Name for the shared preference for this app
     */
    final String SHARED_PREF_NAME = "NewsAppSharedPref";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    Context context;
    /**
     * Default value for shared preference used across activities
     */
    final public static String SHARED_PREF_DEFAULT_VALUE = "Custom Greeting";

    SharedPrefHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(this.SHARED_PREF_NAME, MODE_PRIVATE);
        this.sharedPreferencesEditor = this.sharedPreferences.edit();
        this.context = context;
    }

    /**
     * Method to store string in shared preference
     * @param stringToSave
     * @param key
     */
    public void store(String stringToSave, String key) {
        this.sharedPreferencesEditor.putString(key, stringToSave);
        this.sharedPreferencesEditor.commit();
    }

    /**
     * loads string from the shared preference key provided
     * @param key
     * @return string
     */
    public String load(String key) {
        String sharedPreferencesString = this.sharedPreferences.getString(key, this.SHARED_PREF_DEFAULT_VALUE);
        return sharedPreferencesString;
    }

    /**
     * clears all stored items from shared preference
     */
    public void clearAll() {
        this.sharedPreferencesEditor.clear();
        this.sharedPreferencesEditor.commit();
    }

}
