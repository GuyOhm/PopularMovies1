package com.example.android.popularmovies1.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utilities class to be used to communicate with the moviedb API
 *
 * Credit: inspired from Sunshine NetworkUtils class
 */

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p";
    private static final String MOVIE_PATH = "movie";
    private static final String IMAGE_SIZE_PATH = "w185";

    // Please enter your own TheMovieDB API key
    private static final String API_KEY = BuildConfig.API_KEY;

    // Queries name
    private static final String API_KEY_QUERY = "api_key";
    private static final String POPULAR_QUERY = "popular";
    private static final String TOP_RATED_QUERY = "top_rated";

    // Timeouts for URLConnection in millisec
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    /**
     * Build URL for popular movies to TLDB server
     *
     * @return URL
     */
    public static URL buildPopularUrl(){
        Uri builtUri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(POPULAR_QUERY)
                .appendQueryParameter(API_KEY_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("NetworkUtils", url.toString());
        return url;
    }

    /**
     * Build URL for highest rated movies to TLDB server
     *
     * @return URL
     */
    public static URL buildHighestRatedUrl(){
        Uri builtUri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(TOP_RATED_QUERY)
                .appendQueryParameter(API_KEY_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("NetworkUtils", url.toString());
        return url;
    }

    /**
     * Build URL to the movie poster
     *
     * @param string picture path
     * @return String which is the URL to the poster
     */
    public static String buildPosterUrl(String string) {
        Uri builtUri = Uri
                .parse(IMAGE_URL)
                .buildUpon()
                .appendPath(IMAGE_SIZE_PATH)
                .build();

        return builtUri.toString() + string;
    }


    /**
     * Handle HTTP request and get response from it
     *
     * @param url to request
     * @return String as a response
     * @throws IOException if an error occurred
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // Set connecting and readind timeouts as per:
        // https://eventuallyconsistent.net/2011/08/02/working-with-urlconnection-and-timeouts/
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIMEOUT);

        // Scan response from the server
        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
