package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.Video;

import java.util.List;

import io.reactivex.Single;

public interface DataModel {

    Single<List<Movie>> getMostPopularMovies();

    Single<List<Movie>> getTopRatedMovies();

    Single<Movie> getMovieDetails(@NonNull String movieId);

    Single<List<Review>> getMovieReviews(@NonNull String movieId);

    Single<List<Video>> getMovieVideos(@NonNull String movieId);
}
