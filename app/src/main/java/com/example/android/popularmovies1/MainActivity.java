package com.example.android.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies1.model.Movie;
import com.example.android.popularmovies1.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterListener {

    private static final String JSON_TITLE_KEY = "title";
    private static final String JSON_POSTER_KEY = "poster_path";
    private static final String JSON_OVERVIEW_KEY = "overview";
    private static final String JSON_VOTE_AVERAGE_KEY = "vote_average";
    private static final String JSON_RELEASE_DATE_KEY = "release_date";

    private final ArrayList<Movie> mMovies = new ArrayList<>();
    private MoviesAdapter mMoviesAdapter;
    public static final String MOVIE_INTENT_KEY = "movie_key";

    @BindView(R.id.loading_indicator_pb) ProgressBar mLoadingIndicator;
    @BindView(R.id.movies_gv) GridView gridView;
    @BindView(R.id.no_network_tv) TextView mNoNetworkTextView;
    @BindView(R.id.error_laoding_tv) TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Create and set the adapter to our gridview
        mMoviesAdapter = new MoviesAdapter(MainActivity.this, mMovies, this);
        gridView.setAdapter(mMoviesAdapter);

        // Check network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            showLoadingIndicator();
            // Execute AsyncTask to fetch data from TMDB server
            new FetchMovieDataTask(new FetchMovieDataTaskCompleteListener())
                    .execute(NetworkUtils.buildPopularUrl());
        } else {
            showNetworkError();
        }
    }

    /**
     * Callback to handle selected movie and display its details
     *
     * @param movie object
     */
    @Override
    public void onMovieSelected(Movie movie) {
        Intent movieIntent = new Intent(MainActivity.this, DetailActivity.class);
        movieIntent.putExtra(MOVIE_INTENT_KEY, movie);
        startActivity(movieIntent);
    }

    /**
     * Create an interface to be used as a callback from AsyncTask
     */
    public class FetchMovieDataTaskCompleteListener implements AsyncTaskCompleteListener<String> {

        @Override
        public void onTaskComplete(String result) {
            if (result != null && !TextUtils.isEmpty(result)) {
                try {
                    // Convert JSON data to movie objects
                    convertJsonToObject(result);
                    showGridview();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Convert the response from TMDB server to Movie objects and add them to an ArrayList
     *
     * @param s String which is the response from TMDB server
     * @throws JSONException
     */
    private void convertJsonToObject(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        JSONArray results = jsonObject.optJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.optJSONObject(i);
            mMovies.add(new Movie(
                    result.optString(JSON_TITLE_KEY),
                    result.optString(JSON_POSTER_KEY),
                    result.optString(JSON_OVERVIEW_KEY),
                    result.optString(JSON_VOTE_AVERAGE_KEY),
                    result.optString(JSON_RELEASE_DATE_KEY)
            ));
        }
        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate main menu options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popular:
                // Clear current list of movies
                mMovies.clear();
                // Execute a new task to fetch data from the server
                Log.d("MainActicity", NetworkUtils.buildPopularUrl().toString());
                // new moviesDbQueryTask().execute(NetworkUtils.buildPopularUrl());
                showLoadingIndicator();
                new FetchMovieDataTask(new FetchMovieDataTaskCompleteListener())
                        .execute(NetworkUtils.buildPopularUrl());
                return true;
            case R.id.action_sort_by_highest_rate:
                // Clear current list of movies
                mMovies.clear();
                // Execute a new task to fetch data from the server
                Log.d("MainActicity", NetworkUtils.buildHighestRatedUrl().toString());
                // new moviesDbQueryTask().execute(NetworkUtils.buildHighestRatedUrl());
                showLoadingIndicator();
                new FetchMovieDataTask(new FetchMovieDataTaskCompleteListener())
                        .execute(NetworkUtils.buildHighestRatedUrl());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.INVISIBLE);
        mNoNetworkTextView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    private void showGridview() {
        gridView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mNoNetworkTextView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    private void showNetworkError() {
        mNoNetworkTextView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }
}
