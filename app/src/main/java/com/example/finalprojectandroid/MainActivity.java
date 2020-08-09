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
 * This class is the entry point for the app
 * @author: Kazi Muntaha Mahdi
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Activity display name
     */
    final static String ACTIVITY_NAME = "Home";
    /**
     * Activity version
     */
    final static String ACTIVITY_VERSION = "1.0.0";
    /**
     * Shared preference key associated with this activity
     */
    final static String SHARED_PREF_KEY = "MainActivity_SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This initiates the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Navigation / drawer are defined here
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                View rootView = getWindow().getDecorView().getRootView();
                return Utils.onNavigationItemSelectedHelper(item, MainActivity.this, rootView);
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView activityName = header.findViewById(R.id.activity_name);
        TextView activityVersion = header.findViewById(R.id.activity_version);
        activityName.setText(ACTIVITY_NAME);
        activityVersion.setText(ACTIVITY_VERSION);

        //Fragment and Shared Pref
        Bundle dataToPass = new Bundle();
        dataToPass.putString("sharedPrefKey", SHARED_PREF_KEY);
        CustomActivityGreeting cag = new CustomActivityGreeting();
        cag.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentSpace, cag)
                .commit();

        // Snackbar
        LinearLayout mainActivityLayout = findViewById(R.id.mainActivityLayout);
        Snackbar snackbar = Snackbar.make(mainActivityLayout,"Welcome to News App",Snackbar.LENGTH_SHORT);
        snackbar.show();

        // Clicking this button will fetch news from bbc
        Button loadNews = (Button) findViewById(R.id.load_news);
        loadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });
    }

    /**
     * This is a method for that showing alert for this activity
     * @return void
     */
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Loading News from BBC")
            .setMessage("Do you want to continue?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openNewsList();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this,"Selected Option: No",Toast.LENGTH_SHORT).show();
                }
            });
        AlertDialog dialog  = builder.create();
        dialog.show();
    }

    /**
     * This method opens new activity with news list fetched from bbc
     */
    private void openNewsList() {
        Intent intent = new Intent(getApplicationContext(), NewsList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Utils.onOptionsItemSelectedHelpder(item, MainActivity.this);
    }
}
