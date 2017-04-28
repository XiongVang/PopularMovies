package com.teamtreehouse.popularmovies;

import android.app.Application;

import com.teamtreehouse.popularmovies.di.DaggerMovieDbApiComponent;
import com.teamtreehouse.popularmovies.di.MovieDbApiComponent;
import com.teamtreehouse.popularmovies.di.MovieDbApiModule;
import com.teamtreehouse.popularmovies.di.RetrofitBuilderModule;

public class PopularMoviesApp extends Application {

    private final String API_KEY = "63ba90194ff5cc0e2f703ed4f25413f1"; // INSERT YOUR MOVIE API KEY HERE;


    MovieDbApiComponent mMovieDbApiComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        mMovieDbApiComponent = DaggerMovieDbApiComponent.builder()
                .retrofitBuilderModule(new RetrofitBuilderModule())
                .movieDbApiModule(new MovieDbApiModule(API_KEY))
                .build();

    }

    public MovieDbApiComponent getMovieDbApiComponent() {
        return mMovieDbApiComponent;
    }

}