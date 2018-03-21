package com.example.android.popularmovies1.utilities;

import android.net.Uri;

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
    private static final String DISCOVER_PATH = "discover";
    private static final String MOVIE_PATH = "movie";
    private static final String IMAGE_SIZE_PATH = "w185";

    // Please enter your own TheMovieDB API key
    private static final String API_KEY = "";

    // Query parameter names
    private static final String API_KEY_QUERY = "api_key";
    private static final String SORT_BY_QUERY = "sort_by";

    // Query parameter values
    private static final String POPULARITY_DESC_PARAM = "popularity.desc";
    private static final String HIGHEST_RATED_PARAM = "vote_average";

    /**
     * Build URL for movies to TLDB server
     *
     * @return URL
     */
    public static URL buildMovieUrl(){
        Uri builtUri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath(DISCOVER_PATH)
                .appendPath(MOVIE_PATH)
                .appendQueryParameter(API_KEY_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Build URL for popular movies to TLDB server
     *
     * @return URL
     */
    public static URL buildPopularUrl(){
        Uri builtUri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath(DISCOVER_PATH)
                .appendPath(MOVIE_PATH)
                .appendQueryParameter(API_KEY_QUERY, API_KEY)
                .appendQueryParameter(SORT_BY_QUERY, POPULARITY_DESC_PARAM)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
                .appendPath(DISCOVER_PATH)
                .appendPath(MOVIE_PATH)
                .appendQueryParameter(API_KEY_QUERY, API_KEY)
                .appendQueryParameter(SORT_BY_QUERY, HIGHEST_RATED_PARAM)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
