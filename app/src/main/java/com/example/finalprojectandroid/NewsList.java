package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

public class NewsList extends AppCompatActivity {

    final static String feedUrl ="http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        LinearLayout newsListLayout = findViewById(R.id.newsListLinear);
        final EditText et = (EditText)findViewById(R.id.editTextNewsList);
        // TODO: Check if SharedPref present, if so, then set it to SharedPref [req: 10]
        Button saveNote = (Button)findViewById(R.id.saveNoteNewsList);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement sharing to SharedPref [req: 10]
                et.setText(et.getText());
                et.setFocusable(false);
                et.setFocusableInTouchMode(false);
                et.setClickable(false);
            }
        });
        final ListView listView= (ListView) findViewById(R.id.main_list_view);
        Snackbar snackbar = Snackbar.make(newsListLayout,"News Headlines from BBC",Snackbar.LENGTH_SHORT);
        snackbar.show();
        new Retriever(NewsList.this, feedUrl,listView).execute();
    }
}
