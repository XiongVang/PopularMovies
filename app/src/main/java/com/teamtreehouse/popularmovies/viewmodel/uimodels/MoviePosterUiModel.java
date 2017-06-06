package com.teamtreehouse.popularmovies.viewmodel.uimodels;

public class MoviePosterUiModel {

    private String mMovieId;
    private String mMoviePosterUrl;

    public MoviePosterUiModel(String movieId, String moviePosterUrl) {
        mMovieId = movieId;
        mMoviePosterUrl = moviePosterUrl;
    }

    public String getMovieId() {
        return mMovieId;
    }

    public String getMoviePosterUrl() {
        return mMoviePosterUrl;
    }
}