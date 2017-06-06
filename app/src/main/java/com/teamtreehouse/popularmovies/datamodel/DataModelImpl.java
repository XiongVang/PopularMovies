package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.local.LocalDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.RemoteDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.Video;

import java.util.List;

import io.reactivex.Single;

public class DataModelImpl implements DataModel {


    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;


    public DataModelImpl(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }


    @Override
    public Single<List<MovieResult>> getMostPopularMovies() {
        return remoteDataSource.getMostPopularMovies();
    }

    @Override
    public Single<List<MovieResult>> getTopRatedMovies() {
        return remoteDataSource.getTopRatedMovies();
    }

    @Override
    public Single<MovieDetails> getMovieDetails(@NonNull String movieId) {
        return remoteDataSource.getMovieDetails(movieId);
    }

    @Override
    public Single<List<Review>> getMovieReviews(@NonNull String movieId) {
        return remoteDataSource.getMovieReviews(movieId);
    }

    @Override
    public Single<List<Video>> getMovieVideos(@NonNull String movieId) {
        return remoteDataSource.getMovieTrailers(movieId);
    }
}
