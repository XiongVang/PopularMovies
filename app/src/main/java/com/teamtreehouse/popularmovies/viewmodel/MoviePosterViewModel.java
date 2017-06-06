package com.teamtreehouse.popularmovies.viewmodel;

import com.teamtreehouse.popularmovies.datamodel.DataModel;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.MoviePosterUiModel;

import java.util.ArrayList;
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
        return dataModel.getMostPopularMovies()
                .map(this::toMoviePosterUiModel);
    }

    public Single<List<MoviePosterUiModel>> getHighestRateMovies() {
        return dataModel.getTopRatedMovies()
                .map(this::toMoviePosterUiModel);
    }

    private List<MoviePosterUiModel> toMoviePosterUiModel(List<MovieResult> results){
        List<MoviePosterUiModel> posters = new ArrayList<>();

        for(MovieResult movie: results){
            posters.add(new MoviePosterUiModel(movie.getId(),movie.getPosterPath()));
        }

        return posters;
    }
}
