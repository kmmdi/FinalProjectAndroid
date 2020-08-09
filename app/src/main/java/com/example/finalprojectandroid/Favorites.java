package com.example.finalprojectandroid;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the activity for favorite news items
 * @author Kazi Muntaha Mahdi
 */
public class Favorites extends AppCompatActivity {

    /**
     * Activity display name
     */
    final static String ACTIVITY_NAME = "Favorites";
    /**
     * Activity version
     */
    final static String ACTIVITY_VERSION = "1.0.0";
    /**
     * Activity shared preference
     */
    final static String SHARED_PREF_KEY = "Favorites_SP";
    DatabaseUtils databaseUtils;
    SQLiteDatabase db;
    ArrayList<NewsArticle> newsArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize database and get news from db
        initDb();
        newsArticles = databaseUtils.loadNewsArticles(db);

        // List view for favorite news items
        final ListView listView= (ListView) findViewById(R.id.fav_list_view);
        listView.setAdapter(new NewsListAdapter(Favorites.this, newsArticles));

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Navigation
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
                return Utils.onNavigationItemSelectedHelper(item, Favorites.this, rootView);
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView activityName = header.findViewById(R.id.activity_name);
        TextView activityVersion = header.findViewById(R.id.activity_version);
        activityName.setText(ACTIVITY_NAME);
        activityVersion.setText(ACTIVITY_VERSION);

        LinearLayout favoritesLayout = findViewById(R.id.favListLinear);

        // Snackbar
        Snackbar snackbar = Snackbar.make(favoritesLayout,"Welcome to Favorites Page",Snackbar.LENGTH_SHORT);
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
    }

    /**
     * Initializes database
     */
    private void initDb() {
        databaseUtils = new DatabaseUtils(Favorites.this);
        db = databaseUtils.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Utils.onOptionsItemSelectedHelpder(item, Favorites.this);
    }
}
