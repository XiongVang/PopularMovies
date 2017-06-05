package com.teamtreehouse.popularmovies.datamodel.datasource.remote;

import com.teamtreehouse.popularmovies.datamodel.model.Movie;

import java.util.List;

import io.reactivex.Single;

public interface RemoteDataSource {


    Single<List<Movie>> getMostPopularMovies();

    Single<List<Movie>> getTopRatedMovies();

}
