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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Settings extends AppCompatActivity {

    final static String ACTIVITY_NAME = "Settings";
    final static String ACTIVITY_VERSION = "1.0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
                return NavigationUtils.onNavigationItemSelectedHelper(item, Settings.this, rootView);
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView activityName = header.findViewById(R.id.activity_name);
        TextView activityVersion = header.findViewById(R.id.activity_version);
        activityName.setText(ACTIVITY_NAME);
        activityVersion.setText(ACTIVITY_VERSION);

        LinearLayout settingLayout = findViewById(R.id.settingLayout);
        Toast.makeText(Settings.this,"Settings Page Loaded",Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(settingLayout,"Welcome to Settings Page",Snackbar.LENGTH_SHORT);
        snackbar.show();
        // TODO: Implement and Put the EditText and associated button in Fragment [req 5]

        /**
         * TODO: Provide option to clear all SharedPref [this is an enhancement as we need 4 activities.
         * It's related to req 10.
         */

        // TODO: Show Alert dialog - utilize showAlert below
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setTitle("PLACE HOLDER TEXT // maybe for confirming clearing of SharedPref")
                .setMessage("Do you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: Do my action here
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
        Intent intent;
        switch(item.getItemId()) {
            case R.id.myHome:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.myNews:
                intent = new Intent(getApplicationContext(), NewsList.class);
                startActivity(intent);
                break;
            case R.id.myFav:
                intent = new Intent(getApplicationContext(), Favorites.class);
                startActivity(intent);
                break;
            case R.id.mySettings:
                intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
