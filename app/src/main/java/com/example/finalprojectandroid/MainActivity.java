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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    final static String ACTIVITY_NAME = "Home";
    final static String ACTIVITY_VERSION = "1.0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        LinearLayout mainActivityLayout = findViewById(R.id.mainActivityLayout);
        // TODO: Put the EditText and associated button in Fragment [req 5]
        final EditText et = (EditText)findViewById(R.id.editTextMainActivity);
        // TODO: Check if SharedPref present, if so, then set it to SharedPref [req: 10]
        Button setGreeting = (Button)findViewById(R.id.setGreetingMainActivity);
        setGreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement sharing to SharedPref [req: 10]
                et.setText(et.getText());
                et.setFocusable(false);
                et.setFocusableInTouchMode(false);
                et.setClickable(false);
            }
        });
        // Toast and Snackbar
        Toast.makeText(MainActivity.this,"Welcome Page Loaded",Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(mainActivityLayout,"Welcome to News App",Snackbar.LENGTH_SHORT);
        snackbar.show();

        Button loadNews = (Button) findViewById(R.id.load_news);
        loadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });
    }

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
