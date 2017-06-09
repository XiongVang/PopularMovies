package com.teamtreehouse.popularmovies.datamodel.datasources.remote;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.videos.Video;

import java.util.List;

import io.reactivex.Single;

public interface RemoteDataSource {


    Single<List<MovieResult>> getMostPopularMovies();

    Single<List<MovieResult>> getTopRatedMovies();

    Single<MovieDetails> getMovieDetails(@NonNull String movieId);

    Single<List<Review>> getMovieReviews(@NonNull String movieId);

    Single<List<Video>> getMovieTrailers(@NonNull String movieId);

}
