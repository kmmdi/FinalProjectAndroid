package com.example.finalprojectandroid;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NewsList extends AppCompatActivity {

    final static String ACTIVITY_NAME = "News";
    final static String ACTIVITY_VERSION = "1.0.0";
    final static String feedUrl ="http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    final static String SHARED_PREF_KEY = "NewsList_SP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

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
                return Utils.onNavigationItemSelectedHelper(item, NewsList.this, rootView);
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

        final ListView listView= (ListView) findViewById(R.id.main_list_view);
        LinearLayout newsListLayout = findViewById(R.id.newsListLinear);
        Snackbar snackbar = Snackbar.make(newsListLayout,"News Headlines from BBC",Snackbar.LENGTH_SHORT);
        snackbar.show();

        new Retriever(NewsList.this, feedUrl,listView).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Utils.onOptionsItemSelectedHelpder(item, NewsList.this);
    }
}
