package com.example.finalprojectandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rl = findViewById(R.id.rl);
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
        Snackbar snackbar = Snackbar.make(rl,"Welcome to News App",Snackbar.LENGTH_SHORT);
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

}
