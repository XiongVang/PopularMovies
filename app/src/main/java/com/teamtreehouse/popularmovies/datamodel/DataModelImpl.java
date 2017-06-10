package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasources.local.LocalDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.RemoteDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.discovery.MovieResult;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.videos.Video;
import com.teamtreehouse.popularmovies.datamodel.models.MovieModel;
import com.teamtreehouse.popularmovies.datamodel.models.ReviewModel;
import com.teamtreehouse.popularmovies.datamodel.models.TrailerModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DataModelImpl implements DataModel {


    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;


    public DataModelImpl(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }


    @Override
    public Single<List<MovieModel>> getMostPopularMovies() {
        return remoteDataSource.getMostPopularMovies()
                .map(this::mapMovieResultToMovieModels);
    }

    @Override
    public Single<List<MovieModel>> getTopRatedMovies() {
        return remoteDataSource.getTopRatedMovies()
                .map(this::mapMovieResultToMovieModels);
    }

    private List<MovieModel> mapMovieResultToMovieModels(List<MovieResult> movieResults){

        return movieResults.stream().map(movieResult -> new MovieModel(
                    movieResult.getId(),
                    movieResult.getOriginalTitle(),
                    movieResult.getPosterPath(),
                    movieResult.getReleaseDate(),
                    movieResult.getVoteAverage().toString(),
                    movieResult.getOverview()))
                .collect(Collectors.toList());
    }

    @Override
    public Single<MovieModel> getMovieModel(@NonNull String movieId) {
        return remoteDataSource.getMovieDetails(movieId)
                .map(this::mapMovieDetailsToMovieModel);
    }

    private MovieModel mapMovieDetailsToMovieModel(MovieDetails movieDetails){
        return new MovieModel(
                    movieDetails.getId(),
                    movieDetails.getOriginalTitle(),
                    movieDetails.getPosterPath(),
                    movieDetails.getReleaseDate(),
                    movieDetails.getVoteAverage().toString(),
                    movieDetails.getOverview());
    }

    @Override
    public Single<List<ReviewModel>> getReviewModels(@NonNull String movieId) {
        return remoteDataSource.getMovieReviews(movieId)
                .map(this::mapReviewsToReviewModels);
    }

    private List<ReviewModel> mapReviewsToReviewModels(List<Review> reviews){
        return reviews.stream()
                .map(review -> new ReviewModel(
                        review.getId(),
                        review.getAuthor(),
                        review.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public Single<List<TrailerModel>> getTrailerModels(@NonNull String movieId) {
        return remoteDataSource.getMovieTrailers(movieId)
                .map(this::mapVideosToTrailerModels);
    }

    private List<TrailerModel> mapVideosToTrailerModels(List<Video> videos) {
        return videos.stream()
                .filter(video -> video.getType().equals("Trailer"))
                .map(video -> new TrailerModel(video.getId(),video.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public Completable addToFavorites(MovieModel movie,
                                      List<ReviewModel> reviews,
                                      List<TrailerModel> trailers) {
        return localDataSource.addToFavorites(movie,reviews,trailers);
    }

    @Override
    public Single<List<MovieModel>> getFavoriteMovies() {
        return localDataSource.getFavoriteMovies();
    }

    @Override
    public Single<MovieModel> getMovieModelFromFavorites(@NonNull String movieId) {
        return localDataSource.getMovie(movieId);
    }

    @Override
    public Single<List<ReviewModel>> getReviewModelsFromFavorites(@NonNull String movieId) {
        return localDataSource.getReviews(movieId);
    }

    @Override
    public Single<List<TrailerModel>> getTrailerModelsFromFavorites(@NonNull String movieId) {
        return localDataSource.getTrailers(movieId);
    }

    @Override
    public Single<Boolean> isInFavorites(String movieId) {
        return localDataSource.isFavorite(movieId);
    }

}
