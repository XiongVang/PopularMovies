package com.teamtreehouse.popularmovies.ui;

import android.util.Log;

import com.teamtreehouse.popularmovies.api.MovieDbApiService;
import com.teamtreehouse.popularmovies.model.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MoviePosterViewModel {

    private static final String TAG = "MoviePosterViewModel";

    private final MovieDbApiService movieDbApiService;


    @Inject
    public MoviePosterViewModel(MovieDbApiService apiService) {
        Log.d(TAG, "MoviePosterViewModel: constructor");
        movieDbApiService = apiService;
    }

    public Single<List<Movie>> getMostPopularMovies() {
        return movieDbApiService.getMostPopularMovies();
    }

    public Single<List<Movie>> getHighestRateMovies() {
        return movieDbApiService.getTopRatedMovies();
    }
}
