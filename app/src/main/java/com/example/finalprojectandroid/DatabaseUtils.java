package com.example.finalprojectandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseUtils extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "NewsDb_KM";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "NewsMeta";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_DESCRIPTION = "description";
    public final static String COL_DATE = "date";
    public final static String COL_LINK = "link";

    public DatabaseUtils(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = " CREATE TABLE " + TABLE_NAME + " ( _id TEXT PRIMARY KEY, "
                + COL_TITLE + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_LINK + " TEXT, "
                + COL_DATE + " TEXT );";
        Log.e("PRINT", createTableQuery);
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<NewsArticle> loadNewsArticles (SQLiteDatabase db) {
        ArrayList<NewsArticle> newsList = new ArrayList<>();
        String[] columns = new String[] {COL_ID, COL_TITLE, COL_DESCRIPTION, COL_LINK, COL_DATE};
        Cursor results = db.query(
                true,
                TABLE_NAME,
                columns,
                null, null , null, null, null, null);

        int idIdx = results.getColumnIndex(COL_ID);
        int titleIdx = results.getColumnIndex(COL_TITLE);
        int descriptionIdx = results.getColumnIndex(COL_DESCRIPTION);
        int dateIdx = results.getColumnIndex(COL_DATE);
        int linkIdx = results.getColumnIndex(COL_LINK);

        while(results.moveToNext()) {
            String guid = results.getString(idIdx);
            String title = results.getString(titleIdx);
            String description = results.getString(descriptionIdx);
            String date = results.getString(dateIdx);
            String link = results.getString(linkIdx);
            NewsArticle newsArticle = new NewsArticle();
            newsArticle.setGuid(guid);
            newsArticle.setTitle(title);
            newsArticle.setDescription(description);
            newsArticle.setDate(date);
            newsArticle.setLink(link);
            newsList.add(newsArticle);
        }

        return newsList;
    }

    public void storeNewsArticle(NewsArticle newsArticle, SQLiteDatabase db) {
        ContentValues cValues = new ContentValues();
        cValues.put(COL_ID, newsArticle.getGuid());
        cValues.put(COL_TITLE, newsArticle.getTitle());
        cValues.put(COL_DESCRIPTION, newsArticle.getDescription());
        cValues.put(COL_DATE, newsArticle.getDate());
        cValues.put(COL_LINK, newsArticle.getLink());
        db.insert(TABLE_NAME, null, cValues);
    }

    public void deleteMessage(NewsArticle newsArticle, SQLiteDatabase db) {
        db.delete(TABLE_NAME, "_id=?", new String[] { newsArticle.getGuid() } );
    }


}

