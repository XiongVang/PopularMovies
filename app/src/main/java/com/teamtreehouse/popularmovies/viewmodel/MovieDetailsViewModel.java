package com.teamtreehouse.popularmovies.viewmodel;

import android.support.annotation.NonNull;

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
    private MovieDetailsUiModel movie;

    @Inject
    public MovieDetailsViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @NonNull
    public Single getMovieDetails(@NonNull String movieId) {
        this.movieId = movieId;
        return Single.zip(dataModel.getMovieDetails(movieId),
                dataModel.getMovieTrailers(movieId).onErrorReturnItem(new ArrayList<>()),
                dataModel.getMovieReviews(movieId).onErrorReturnItem(new ArrayList<>()),
                (movieDetails, videos, reviews) -> {
                    MovieDetailsUiModel movieDetailsUiModel = toMovieDetailsUiModel(movieDetails);
                    movieDetailsUiModel.setReviews(toReviewUiModels(reviews));
                    movieDetailsUiModel.setTrailers(toTrailerUiModels(videos));

                    movie = movieDetailsUiModel;
                    return movie;
                });
    }

    public Completable addToFavorites() {

        MovieModel movieModel = new MovieModel(
                movieId,
                movie.getOriginalTitle(),
                movie.getImageThumbnailUrl(),
                movie.getReleaseDate(),
                movie.getUserRating(),
                movie.getPlotSynopsis()
        );

        List<ReviewModel> reviewModels = new ArrayList<>();
        for (ReviewUiModel review : movie.getReviews()) {
            reviewModels.add(new ReviewModel(movieId, review.getAuthor(), review.getContent()));
        }

        List<TrailerModel> trailerModels = new ArrayList<>();
        for (TrailerUiModel trailer : movie.getTrailers()) {
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
}

