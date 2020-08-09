package com.example.finalprojectandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

/**
 * This class provides a customer adapter for the news list
 * @author Kazi Muntaha Mahdi
 */
public class NewsListAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsArticle> newsArticles;
    DatabaseUtils databaseUtils;
    SQLiteDatabase db;

    public NewsListAdapter(Context context, ArrayList<NewsArticle> newsArticles) {
        this.context = context;
        this.newsArticles = newsArticles;
        initDb();
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

        // Title of the news
        TextView titleText = (TextView) convertView.findViewById(R.id.title_text);
        // Description of the news
        TextView descriptionText = (TextView) convertView.findViewById(R.id.details_text);
        // Date of the news
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

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (context instanceof NewsList) {
                    showAlertForNewsList(title, description, date, guid, link);
                } else if (context instanceof Favorites){
                    showAlertForFavorites(title, description, date, guid, link);
                }
                return false;
            }
        });

        return convertView;
    }

    /**
     * This method shows alert for the news list items
     * @param details contains news title, description, date, guid, and link
     */
    private void showAlertForNewsList(final String... details) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Adding to Favorites")
                .setMessage("Do you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewsArticle newsArticle = new NewsArticle();
                        newsArticle.setGuid(details[3]);
                        newsArticle.setTitle(details[0]);
                        newsArticle.setDescription(details[1]);
                        newsArticle.setDate(details[2]);
                        newsArticle.setLink(details[4]);
                        try {
                            databaseUtils.storeNewsArticle(newsArticle, db);
                            Toast.makeText(context,"Added to favorites",Toast.LENGTH_SHORT).show();
                        } catch(Exception e) {
                            Toast.makeText(context,"Operation Failed",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

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

    /**
     * This method shows alert for the favorites list
     * @param details contains news title, description, date, guid, and link
     */
    private void showAlertForFavorites(final String... details) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Removing from Favorites")
                .setMessage("Do you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewsArticle newsArticle = new NewsArticle();
                        newsArticle.setGuid(details[3]);
                        newsArticle.setTitle(details[0]);
                        newsArticle.setDescription(details[1]);
                        newsArticle.setDate(details[2]);
                        newsArticle.setLink(details[4]);
                        try {
                            databaseUtils.deleteNewsArticle(newsArticle, db);
                            Toast.makeText(context,"Removed from favorites",Toast.LENGTH_SHORT).show();
                            NewsListAdapter.this.notifyDataSetChanged();
                            ((Favorites) context).recreate();
                        } catch(Exception e) {
                            Toast.makeText(context,"Operation Failed",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

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

    /**
     * Initialize database
     */
    private void initDb() {
        databaseUtils = new DatabaseUtils(context);
        db = databaseUtils.getWritableDatabase();
    }

    /**
     * Opens an article on the browser
     * @param details contains news title, description, date, guid, and link
     */
    private void openArticleOnWeb(String... details) {
        String url = details[4];
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}