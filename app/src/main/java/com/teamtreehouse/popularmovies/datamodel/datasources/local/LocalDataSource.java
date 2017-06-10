package com.teamtreehouse.popularmovies.datamodel.datasources.local;

import com.teamtreehouse.popularmovies.datamodel.models.MovieModel;
import com.teamtreehouse.popularmovies.datamodel.models.ReviewModel;
import com.teamtreehouse.popularmovies.datamodel.models.TrailerModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface LocalDataSource {

    Single<List<MovieModel>> getFavoriteMovies();

    Single<MovieModel> getMovie(String movieId);

    Single<List<ReviewModel>> getReviews(String movieId);

    Single<List<TrailerModel>> getTrailers(String movieId);

    Completable addToFavorites(MovieModel movie, List<ReviewModel> reviews, List<TrailerModel> trailers);

}
