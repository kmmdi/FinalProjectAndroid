package com.example.finalprojectandroid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnector {

    public static Object connect(String urlAddress) {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setDoInput(true);

            return httpURLConnection;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return CustomError.INVALID_URL_FORMAT;

        } catch (IOException e) {
            e.printStackTrace();
            return CustomError.CONNECTION_ERROR;
        }
    }

}
