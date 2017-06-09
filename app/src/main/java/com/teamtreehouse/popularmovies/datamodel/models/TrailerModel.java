package com.teamtreehouse.popularmovies.datamodel.models;

public class TrailerModel {

    private String movieId;
    private String videoKey;

    public TrailerModel(String movieId, String videoKey) {
        this.movieId = movieId;
        this.videoKey = videoKey;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getVideoKey() {
        return videoKey;
    }
}
