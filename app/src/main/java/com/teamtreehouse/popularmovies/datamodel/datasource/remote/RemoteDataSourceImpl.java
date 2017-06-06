package com.teamtreehouse.popularmovies.datamodel.datasource.remote;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.MovieDbApiService;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieApiResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.MovieReviewsResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.MovieVideosResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.Video;

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
                .map(this::toPostersMapper);
    }

    @Override
    public Single<List<MovieResult>> getTopRatedMovies() {
        return apiService
                .getTopRatedMovies()
                .map(this::toPostersMapper);
    }

    private List<MovieResult> toPostersMapper(MovieApiResponse response){
        return response.getMovieResults();
    }

    @Override
    public Single<MovieDetails> getMovieDetails(@NonNull String movieId) {
        return apiService.getMovieDetails(movieId);
    }

    @Override
    public Single<List<Review>> getMovieReviews(@NonNull String movieId) {
        return apiService
                .getReviews(movieId)
                .map(this::toReviews);
    }

    private List<Review> toReviews(MovieReviewsResponse response){
        return response.getReviews();
    }

    @Override
    public Single<List<Video>> getMovieTrailers(@NonNull String movieId) {
        return apiService
                .getVideos(movieId)
                .map(this::toVideos);
    }

    private List<Video> toVideos(MovieVideosResponse response){
        return response.getVideos();
    }

}
