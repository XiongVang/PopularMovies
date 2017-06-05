package com.teamtreehouse.popularmovies.datamodel.datasource.local;

import com.teamtreehouse.popularmovies.datamodel.model.Movie;

import java.util.List;

import io.reactivex.Single;


public interface LocalDataSource {

    Single<List<Movie>> getFavoriteMovies();

}
