package com.teamtreehouse.popularmovies.datamodel.datasource.local;

import com.teamtreehouse.popularmovies.datamodel.model.Movie;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class LocalDataSourceImpl implements LocalDataSource {

    @Override
    public Single<List<Movie>> getFavoriteMovies() {
        return Single.just(Collections.EMPTY_LIST);

    }
}
