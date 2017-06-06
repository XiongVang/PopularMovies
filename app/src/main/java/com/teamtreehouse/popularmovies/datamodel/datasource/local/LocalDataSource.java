package com.teamtreehouse.popularmovies.datamodel.datasource.local;

import android.graphics.Movie;

import java.util.List;

import io.reactivex.Single;


public interface LocalDataSource {

    Single<List<Movie>> getFavoriteMovies();

}
