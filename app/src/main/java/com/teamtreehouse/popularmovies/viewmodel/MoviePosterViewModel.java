package com.teamtreehouse.popularmovies.viewmodel;

import com.teamtreehouse.popularmovies.datamodel.DataModel;
import com.teamtreehouse.popularmovies.datamodel.models.MovieModel;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.MoviePosterUiModel;

import java.util.List;
import java.util.stream.Collectors;

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
                .map(this::movieResultsToMoviePosterUiModel);
    }

    public Single<List<MoviePosterUiModel>> getHighestRateMovies() {
        return dataModel.getTopRatedMovies()
                .map(this::movieResultsToMoviePosterUiModel);
    }

    public Single<List<MoviePosterUiModel>> getFavoriteMovies(){

        return dataModel.getFavoriteMovies()
                .map(this::movieResultsToMoviePosterUiModel);
    }

    private List<MoviePosterUiModel> movieResultsToMoviePosterUiModel(List<MovieModel> movieModels){
        return movieModels.stream()
                .map(movieModel -> new MoviePosterUiModel(movieModel.getMovieId(),movieModel.getPosterPath()))
                .collect(Collectors.toList());
    }

}
