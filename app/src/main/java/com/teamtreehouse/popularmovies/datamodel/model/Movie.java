package com.teamtreehouse.popularmovies.datamodel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String movieId;
    private String title;
    private String imageThumbnailUrl;
    private String plotSynopsis;
    private double userRating;
    private String releaseDate;

    public Movie(String movieId, String title, String imageThumbnailUrl, String plotSynopsis, double userRating, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.imageThumbnailUrl = imageThumbnailUrl;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    protected Movie(Parcel in) {
        movieId = in.readString();
        title = in.readString();
        imageThumbnailUrl = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(title);
        dest.writeString(imageThumbnailUrl);
        dest.writeString(plotSynopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}