package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsUiModel;
import com.teamtreehouse.popularmovies.viewmodel.MoviePosterUiModel;

import java.util.List;

import io.reactivex.Single;

public interface DataModel {

    Single<List<MoviePosterUiModel>> getMostPopularMovies();

    Single<List<MoviePosterUiModel>> getTopRatedMovies();

    Single<List<MoviePosterUiModel>> getFavoriteMovies();

    Single<MovieDetailsUiModel> getMovieDetails(@NonNull String movieId);


}
