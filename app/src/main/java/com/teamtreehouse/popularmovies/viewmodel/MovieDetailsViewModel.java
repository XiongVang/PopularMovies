package com.teamtreehouse.popularmovies.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.teamtreehouse.popularmovies.datamodel.DataModel;
import com.teamtreehouse.popularmovies.datamodel.models.MovieModel;
import com.teamtreehouse.popularmovies.datamodel.models.ReviewModel;
import com.teamtreehouse.popularmovies.datamodel.models.TrailerModel;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.MovieDetailsUiModel;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.ReviewUiModel;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.TrailerUiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class MovieDetailsViewModel {
    private static final String TAG = "MovieDetailsViewModel";

    private final DataModel dataModel;

    private String movieId;
    private MovieDetailsUiModel movieDetailsUiModel;
    private boolean isFavorite;

    @Inject
    public MovieDetailsViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @NonNull
    public Single<MovieDetailsUiModel> getMovieDetailsUiModel(@NonNull String movieId) {
        this.movieId = movieId;

        return dataModel.isInFavorites(movieId)
                .flatMap(isFavorite -> {
                    if (!isFavorite) {
                        return fetchMovieDetailsFromApi(movieId);
                    } else {
                        return fetchMovieDetailsFromFavorites(movieId);
                    }
                });
}

    private Single<MovieDetailsUiModel> fetchMovieDetailsFromApi(String movieId) {
        Log.d(TAG, "fetchMovieDetailsFromApi: ");
        return Single.zip(
                dataModel.getMovieModel(movieId),
                dataModel.getReviewModels(movieId),
                dataModel.getTrailerModels(movieId),
                (movieModel, reviewModels, trailerModels) -> {
                    Log.d(TAG, "fetchMovieDetailsFromApi: mapper");
                    MovieDetailsUiModel movie = toMovieDetailsUiModel(movieModel);
                    movie.setReviews(toReviewUiModels(reviewModels));
                    movie.setTrailers(toTrailerUiModels(trailerModels));

                    movieDetailsUiModel = movie;
                    return movieDetailsUiModel;
                });
    }

    private Single<MovieDetailsUiModel> fetchMovieDetailsFromFavorites(String movieId) {
        Log.d(TAG, "fetchMovieDetailsFromFavorites: ");
        return Single.zip(
                dataModel.getMovieModelFromFavorites(movieId),
                dataModel.getReviewModelsFromFavorites(movieId),
                dataModel.getTrailerModelsFromFavorites(movieId),

                (movieModel, reviewModels, trailerModels) -> {

                    Log.d(TAG, "fetchMovieDetailsFromFavorites: mapper");
                    MovieDetailsUiModel movie = toMovieDetailsUiModel(movieModel);
                    movie.setReviews(toReviewUiModels(reviewModels));
                    movie.setTrailers(toTrailerUiModels(trailerModels));

                    movieDetailsUiModel = movie;
                    return movieDetailsUiModel;
                });
    }


    public Completable addToFavorites() {

        MovieModel movieModel = new MovieModel(
                movieId,
                movieDetailsUiModel.getOriginalTitle(),
                movieDetailsUiModel.getImageThumbnailUrl(),
                movieDetailsUiModel.getReleaseDate(),
                movieDetailsUiModel.getUserRating(),
                movieDetailsUiModel.getPlotSynopsis()
        );

        List<ReviewModel> reviewModels = new ArrayList<>();
        for (ReviewUiModel review : movieDetailsUiModel.getReviews()) {
            reviewModels.add(new ReviewModel(movieId, review.getAuthor(), review.getContent()));
        }

        List<TrailerModel> trailerModels = new ArrayList<>();
        for (TrailerUiModel trailer : movieDetailsUiModel.getTrailers()) {
            trailerModels.add(new TrailerModel(movieId, trailer.getTrailerId()));
        }

        return dataModel.addToFavorites(movieModel, reviewModels, trailerModels);
    }

    private MovieDetailsUiModel toMovieDetailsUiModel(MovieModel movieModel) {
        return new MovieDetailsUiModel(
                movieModel.getTitle(),
                movieModel.getPosterPath(),
                movieModel.getReleaseDate(),
                movieModel.getUserRating(),
                movieModel.getPlotSynopsis());
    }

    private List<ReviewUiModel> toReviewUiModels(List<ReviewModel> reviews) {
        return reviews.stream()
                .map(review -> new ReviewUiModel(review.getAuthor(), review.getContent()))
                .collect(Collectors.toList());
    }


    private List<TrailerUiModel> toTrailerUiModels(List<TrailerModel> trailers) {
        return trailers.stream()
                .map(trailer -> new TrailerUiModel(trailer.getVideoKey()))
                .collect(Collectors.toList());
    }

    public Completable removeFromFavorites(String movieId){
        return dataModel.removeFromFavorites(movieId);
    }

    public Single<Boolean> isFavorite(String movieId) {
        return dataModel.isInFavorites(movieId);
    }
}

