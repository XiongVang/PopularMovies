package com.teamtreehouse.popularmovies.viewmodel;

import com.teamtreehouse.popularmovies.datamodel.DataModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MoviePosterViewModel {

    private static final String TAG = "MoviePosterViewModel";

    private final DataModel dataModel;

    @Inject
    public MoviePosterViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Single<List<MoviePosterUiModel>> getMostPopularMovies() {
        return dataModel.getMostPopularMovies();
    }

    public Single<List<MoviePosterUiModel>> getHighestRateMovies() {
        return dataModel.getTopRatedMovies();
    }
}
