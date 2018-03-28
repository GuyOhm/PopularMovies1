package com.example.android.popularmovies1;

import android.os.AsyncTask;

import com.example.android.popularmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * AsyncTask to fetch movie data from TMDB server
 */

public class FetchMovieDataTask extends AsyncTask<URL, Void, String> {

    private AsyncTaskCompleteListener<String> listener;

    FetchMovieDataTask(AsyncTaskCompleteListener<String> listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... urls) {
        // Get the request url
        URL url = urls[0];
        // Declare String variable for response
        String JSONString = null;
        try {
            JSONString = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onTaskComplete(s);
    }
}
