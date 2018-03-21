package com.example.android.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies1.model.Movie;
import com.example.android.popularmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popularmovies1.MainActivity.MOVIE_INTENT_KEY;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.detail_poster_iv) ImageView posterImageView;
    @BindView(R.id.synopsis_tv) TextView synopsisTextView;
    @BindView(R.id.title_tv) TextView titleTextView;
    @BindView(R.id.release_tv) TextView releaseTextView;
    @BindView(R.id.rating_tv) TextView ratingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Get intent that has started the activity
        Intent intent = getIntent();
        Movie selectedMovie = null;
        if (intent.hasExtra(MOVIE_INTENT_KEY)) {
            selectedMovie = intent.getParcelableExtra(MOVIE_INTENT_KEY);
            Log.d(LOG_TAG, selectedMovie.toString());
        }

        // Populate UI with intent data
        if (selectedMovie != null) {
            populateUI(selectedMovie);
        } else  {
            Toast.makeText(DetailActivity.this, R.string.no_movie_selected, Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    private void populateUI(Movie selectedMovie) {
        titleTextView.setText(selectedMovie.getTitle());
        releaseTextView.setText(selectedMovie.getReleaseDate());
        ratingTextView.setText(selectedMovie.getRating());
        synopsisTextView.setText(selectedMovie.getSynopsis());
        Picasso
                .with(DetailActivity.this)
                .load(String.valueOf(NetworkUtils.buildPosterUrl(selectedMovie.getPoster())))
                .into(posterImageView);
    }
}
