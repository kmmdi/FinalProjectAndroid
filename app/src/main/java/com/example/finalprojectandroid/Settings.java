package com.example.finalprojectandroid;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/**
 * This activity provides some app-wide settings
 * @author Kazi Muntaha Mahdi
 */
public class Settings extends AppCompatActivity {

    /**
     * Activity display name
     */
    final static String ACTIVITY_NAME = "Settings";
    /**
     * Activity version
     */
    final static String ACTIVITY_VERSION = "1.0.0";
    /**
     * Shared preference key for the activity
     */
    final static String SHARED_PREF_KEY = "Settings_SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                View rootView = getWindow().getDecorView().getRootView();
                return Utils.onNavigationItemSelectedHelper(item, Settings.this, rootView);
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView activityName = header.findViewById(R.id.activity_name);
        TextView activityVersion = header.findViewById(R.id.activity_version);
        activityName.setText(ACTIVITY_NAME);
        activityVersion.setText(ACTIVITY_VERSION);

        LinearLayout settingLayout = findViewById(R.id.settingLayout);

        // Snackbar
        Snackbar snackbar = Snackbar.make(settingLayout,"Welcome to Settings Page",Snackbar.LENGTH_SHORT);
        snackbar.show();

        //Fragment and Shared Pref
        Bundle dataToPass = new Bundle();
        dataToPass.putString("sharedPrefKey", SHARED_PREF_KEY);
        CustomActivityGreeting cag = new CustomActivityGreeting();
        cag.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentSpace, cag)
                .commit();

        // Button to delete all custom greetings
        Button deleteGreetings = (Button) findViewById(R.id.delCustomGreetings);
        deleteGreetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    /**
     * Method for showing alert for this activity
     */
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("Deleting Custom Greetings")
                .setMessage("Do you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(Settings.this);
                        sharedPrefHelper.clearAll();
                        Toast.makeText(Settings.this,"Custom Greetings Cleared",Toast.LENGTH_SHORT).show();
                        Settings.this.recreate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Settings.this,"Selected Option: No",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog  = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Utils.onOptionsItemSelectedHelpder(item, Settings.this);
    }
}
