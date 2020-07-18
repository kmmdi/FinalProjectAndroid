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

public class Favorites extends AppCompatActivity {

    final static String ACTIVITY_NAME = "Favorites";
    final static String ACTIVITY_VERSION = "1.0.0";
    DatabaseUtils databaseUtils;
    SQLiteDatabase db;
    ArrayList<NewsArticle> newsArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initDb();
        newsArticles = databaseUtils.loadNewsArticles(db);
        final ListView listView= (ListView) findViewById(R.id.fav_list_view);
        listView.setAdapter(new NewsListAdapter(Favorites.this, newsArticles));

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
                return Utils.onNavigationItemSelectedHelper(item, Favorites.this, rootView);
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView activityName = header.findViewById(R.id.activity_name);
        TextView activityVersion = header.findViewById(R.id.activity_version);
        activityName.setText(ACTIVITY_NAME);
        activityVersion.setText(ACTIVITY_VERSION);

        LinearLayout favoritesLayout = findViewById(R.id.favListLinear);

        //Toast and Snackbar
        Toast.makeText(Favorites.this,"Favorites Page Loaded",Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(favoritesLayout,"Welcome to Favorites Page",Snackbar.LENGTH_SHORT);
        snackbar.show();

        // TODO: Implement and Put the EditText and associated button in Fragment [req 5]

        // TODO: Load favorites from DB and put them in listView [req 8]
        // TODO: Long Press for option to remove from DB (and list view) [req 8]
        // TODO: Show Alert dialog - utilize showAlert below
    }

    private void initDb() {
        databaseUtils = new DatabaseUtils(Favorites.this);
        db = databaseUtils.getWritableDatabase();
    }

    // TODO: use this for delete
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Favorites.this);
        builder.setTitle("PLACE HOLDER TEXT // Maybe for deletion")
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
                        Toast.makeText(Favorites.this,"Selected Option: No",Toast.LENGTH_SHORT).show();
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
        return Utils.onOptionsItemSelectedHelpder(item, Favorites.this);
    }
}
