package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.models.MovieModel;
import com.teamtreehouse.popularmovies.datamodel.models.ReviewModel;
import com.teamtreehouse.popularmovies.datamodel.models.TrailerModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataModel {

    Single<List<MovieModel>> getMostPopularMovies();

    Single<List<MovieModel>> getTopRatedMovies();

    Single<List<MovieModel>> getFavoriteMovies();

    Single<MovieModel> getMovieModel(@NonNull String movieId);

    Single<List<ReviewModel>> getReviewModels(@NonNull String movieId);

    Single<List<TrailerModel>> getTrailerModels(@NonNull String movieId);

    Completable addToFavorites(MovieModel movie,
                               List<ReviewModel> reviews,
                               List<TrailerModel> trailers);

    Single<MovieModel> getMovieModelFromFavorites(@NonNull String movieId);

    Single<List<ReviewModel>> getReviewModelsFromFavorites(@NonNull String movieId);

    Single<List<TrailerModel>> getTrailerModelsFromFavorites(@NonNull String movieId);

    Single<Boolean> isInFavorites(String movieId);
}
