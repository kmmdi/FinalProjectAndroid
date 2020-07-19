package com.example.finalprojectandroid;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

public class SharedPrefHelper {

    final String SHARED_PREF_NAME = "NewsAppSharedPref";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    Context context;
    final public static String SHARED_PREF_DEFAULT_VALUE = "Custom Greeting";

    SharedPrefHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(this.SHARED_PREF_NAME, MODE_PRIVATE);
        this.sharedPreferencesEditor = this.sharedPreferences.edit();
        this.context = context;
    }

    public void store(String stringToSave, String key) {
        this.sharedPreferencesEditor.putString(key, stringToSave);
        this.sharedPreferencesEditor.commit();
    }

    public String load(String key) {
        String sharedPreferencesString = this.sharedPreferences.getString(key, this.SHARED_PREF_DEFAULT_VALUE);
        return sharedPreferencesString;
    }

    public void clearAll() {
        this.sharedPreferencesEditor.clear();
        this.sharedPreferencesEditor.commit();
    }

}
