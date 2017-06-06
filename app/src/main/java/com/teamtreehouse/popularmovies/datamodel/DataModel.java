package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.Video;

import java.util.List;

import io.reactivex.Single;

public interface DataModel {

    Single<List<MovieResult>> getMostPopularMovies();

    Single<List<MovieResult>> getTopRatedMovies();

    Single<MovieDetails> getMovieDetails(@NonNull String movieId);

    Single<List<Review>> getMovieReviews(@NonNull String movieId);

    Single<List<Video>> getMovieVideos(@NonNull String movieId);
}
