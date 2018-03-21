package com.example.android.popularmovies1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies1.model.Movie;
import com.example.android.popularmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Movies adapter to feed data to gridview
 */

class MoviesAdapter extends ArrayAdapter<Movie> {

    // Create interface to handle selected movie
    public interface MoviesAdapterListener {
        void onMovieSelected(Movie movie);
    }

    private final MoviesAdapterListener mMoviesAdapterListener;

    public MoviesAdapter(@NonNull Context context, @NonNull ArrayList<Movie> movies,
                         MoviesAdapterListener listener) {
        super(context, 0, movies);
        mMoviesAdapterListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_item, parent, false);
        }

        // Get reference to the poster view
        ImageView poster = convertView.findViewById(R.id.movie_poster_iv);

        // Display picture using Picasso library
        if (movie != null) {
            Picasso
                    .with(getContext())
                    .load(String.valueOf(NetworkUtils.buildPosterUrl(movie.getPoster())))
                    .into(poster);
        }

        // Set a click handler to the poster to pass movie's info to the interface
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoviesAdapterListener.onMovieSelected(movie);
            }
        });

        return convertView;
    }


}
