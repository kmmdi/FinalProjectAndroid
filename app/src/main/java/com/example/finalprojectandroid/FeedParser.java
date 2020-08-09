package com.example.finalprojectandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class parse RSS news feed provided by bbc
 * @author Kazi Muntaha Mahdi
 */
public class FeedParser extends AsyncTask<Void,Void,Boolean> {

    Context context;
    InputStream inputStream;
    ListView listView;

    ProgressDialog progressDialog;
    ArrayList<NewsArticle> newsNewsArticles = new ArrayList<>();

    public FeedParser(Context context, InputStream inputStream, ListView listView) {
        this.context = context;
        this.inputStream = inputStream;
        this.listView = listView;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =new ProgressDialog(context);
        progressDialog.setTitle("Parse news articles");
        progressDialog.setMessage("Parsing news articles...Please wait");
        progressDialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseFeed();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        progressDialog.dismiss();
        if (isParsed) {
            listView.setAdapter(new NewsListAdapter(context, newsNewsArticles));
        } else {
            Toast.makeText(context,"Could Not Parse",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method parse retrieved rss news feed from bbc
     * @return boolean
     */
    private Boolean parseFeed() {
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = parserFactory.newPullParser();
            pullParser.setInput(inputStream,null);
            int parserEventType = pullParser.getEventType();
            String tag = null;
            Boolean isMeta = true;

            newsNewsArticles.clear();
            NewsArticle newsArticle = new NewsArticle();

            do {
                String tagName=pullParser.getName();
                switch (parserEventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            newsArticle =new NewsArticle();
                            isMeta=false;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        tag=pullParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (!isMeta) {
                            if (tagName.equalsIgnoreCase("title")) {
                                newsArticle.setTitle(tag);
                            } else if (tagName.equalsIgnoreCase("description")) {
                                newsArticle.setDescription(tag);
                            } else if (tagName.equalsIgnoreCase("pubDate")) {
                                newsArticle.setDate(tag);
                            } else if (tagName.equalsIgnoreCase("guid")) {
                                newsArticle.setGuid(tag);
                            } else if (tagName.equalsIgnoreCase("link")) {
                                newsArticle.setLink(tag);
                            }
                        }
                        if(tagName.equalsIgnoreCase("item")) {
                            newsNewsArticles.add(newsArticle);
                            isMeta = true;
                        }
                        break;
                }
                parserEventType=pullParser.next();
            } while (parserEventType != XmlPullParser.END_DOCUMENT);
            return true;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
