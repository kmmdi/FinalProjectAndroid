package com.example.finalprojectandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsArticle> newsArticles;

    public NewsListAdapter(Context context, ArrayList<NewsArticle> newsArticles) {
        this.context = context;
        this.newsArticles = newsArticles;
    }

    @Override
    public int getCount() {
        return newsArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArticles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.model, parent, false);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.title_text);
        TextView descriptionText = (TextView) convertView.findViewById(R.id.details_text);
        TextView dateText = (TextView) convertView.findViewById(R.id.date_text);

        NewsArticle newsArticle = (NewsArticle) this.getItem(position);

        final String title = newsArticle.getTitle();
        final String description = newsArticle.getDescription();
        final String date = newsArticle.getDate();
        final String guid = newsArticle.getGuid();
        final String link = newsArticle.getLink();

        titleText.setText(title);
        descriptionText.setText(Html.fromHtml(description));
        dateText.setText(date);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Opening News in a Browser")
                    .setMessage("Do you want to continue?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openArticleOnWeb(title, description, date, guid, link);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,"Selected Option: No",Toast.LENGTH_SHORT).show();
                        }
                    });
                AlertDialog dialog  = builder.create();
                dialog.show();
            }
        });

        return convertView;
    }

    private void openArticleOnWeb(String... details) {
        String url = details[4];
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}