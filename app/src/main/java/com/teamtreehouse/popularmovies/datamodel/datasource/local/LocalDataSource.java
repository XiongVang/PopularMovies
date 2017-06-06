package com.teamtreehouse.popularmovies.datamodel.datasource.local;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieResult;

import java.util.List;

import io.reactivex.Single;


public interface LocalDataSource {

    Single<List<MovieResult>> getFavoriteMovies();

}
