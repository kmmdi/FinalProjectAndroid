package com.example.finalprojectandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class Retriever extends AsyncTask<Void,Void,Object> {

    Context context;
    String feedUrl;
    ListView listView;

    ProgressDialog progressDialog;

    public Retriever(Context context, String feedUrl, ListView listView) {
        this.context = context;
        this.feedUrl = feedUrl;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =new ProgressDialog(context);
        progressDialog.setTitle("Fetch News");
        progressDialog.setMessage("Fetching News... Please wait");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Void... params) {
        return this.retrieveNewsData();
    }

    @Override
    protected void onPostExecute(Object data) {
        super.onPostExecute(data);
        progressDialog.dismiss();

        if(data.toString().startsWith("Error")) {
            Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show();
        } else {
            new FeedParser(context, (InputStream) data, listView).execute();
        }
    }

    private Object retrieveNewsData() {
        Object connection= HttpConnector.connect(feedUrl);
        if(connection.toString().startsWith("Error")) {
            return connection.toString();
        }

        try {
            HttpURLConnection con= (HttpURLConnection) connection;
            InputStream is=new BufferedInputStream(con.getInputStream());
            return is;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IO_ERROR", e.getMessage());
            return CustomError.IO_ERROR;
        }
    }
}