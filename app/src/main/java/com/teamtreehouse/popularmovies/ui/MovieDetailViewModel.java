package com.teamtreehouse.popularmovies.ui;

import android.util.Log;

import com.teamtreehouse.popularmovies.api.MovieDbApiService;
import com.teamtreehouse.popularmovies.api.Review;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MovieDetailViewModel {
    private static final String TAG = "MovieDetailViewModel";

    private final MovieDbApiService movieDbApiService;

    @Inject
    public MovieDetailViewModel(MovieDbApiService apiService){
        Log.d(TAG, "MovieDetailViewModel: constructor");
        movieDbApiService = apiService;
    }

    public Single<List<String>> getVideos(String movieId){
        return movieDbApiService.getVideos(movieId);
    }

    public Single<List<Review>> getReviews(String movieId){
        return movieDbApiService.getReviews(movieId);
    }
}
