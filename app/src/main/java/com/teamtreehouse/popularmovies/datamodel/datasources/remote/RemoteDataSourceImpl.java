package com.teamtreehouse.popularmovies.datamodel.datasources.remote;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.MovieDbApiService;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.videos.Video;

import java.util.List;

import io.reactivex.Single;

public class RemoteDataSourceImpl implements RemoteDataSource {


    private final MovieDbApiService apiService;


    public RemoteDataSourceImpl(MovieDbApiService apiService){
        this.apiService = apiService;
    }
    @Override
    public Single<List<MovieResult>> getMostPopularMovies() {
        return apiService
                .getMostPopularMovies()
                .map(movieApiResponse -> movieApiResponse.getMovieResults());
    }

    @Override
    public Single<List<MovieResult>> getTopRatedMovies() {
        return apiService
                .getTopRatedMovies()
                .map(movieApiResponse -> movieApiResponse.getMovieResults());
    }


    @Override
    public Single<MovieDetails> getMovieDetails(@NonNull String movieId) {
        return apiService.getMovieDetails(movieId);
    }

    @Override
    public Single<List<Review>> getMovieReviews(@NonNull String movieId) {
        return apiService
                .getReviews(movieId)
                .map(movieReviewsResponse -> movieReviewsResponse.getReviews());
    }


    @Override
    public Single<List<Video>> getMovieTrailers(@NonNull String movieId) {
        return apiService
                .getVideos(movieId)
                .map(movieVideosResponse -> movieVideosResponse.getVideos());
    }

}
