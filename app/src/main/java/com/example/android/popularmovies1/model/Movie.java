package com.example.android.popularmovies1.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO representing a Movie Object
 */

public class Movie implements Parcelable {

    private String title;
    private String poster;
    private String synopsis;
    private String rating;
    private String releaseDate;

    public Movie(String title, String poster, String synopsis, String rating, String releaseDate) {
        this.title = title;
        this.poster = poster;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Title: " + this.getTitle() + "\n" +
                "Poster: " + this.getPoster() + "\n" +
                "Synopsis: " + this.getSynopsis() + "\n" +
                "Rating: " + this.getRating() + "\n" +
                "Release date: " + this.getReleaseDate() + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.synopsis);
        dest.writeString(this.rating);
        dest.writeString(this.releaseDate);
    }

    private Movie(Parcel in) {
        this.title = in.readString();
        this.poster = in.readString();
        this.synopsis = in.readString();
        this.rating = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
