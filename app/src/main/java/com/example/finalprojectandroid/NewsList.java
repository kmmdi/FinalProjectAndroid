package com.example.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class NewsList extends AppCompatActivity {

    final static String feedUrl ="http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        final ListView listView= (ListView) findViewById(R.id.main_list_view);
        new Retriever(NewsList.this, feedUrl,listView).execute();
    }
}
