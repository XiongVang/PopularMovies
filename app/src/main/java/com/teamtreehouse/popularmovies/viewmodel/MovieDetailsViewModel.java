package com.teamtreehouse.popularmovies.viewmodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.DataModel;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.Video;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.MovieDetailsUiModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MovieDetailsViewModel {
    private static final String TAG = "MovieDetailsViewModel";

    private final DataModel dataModel;

    @Inject
    public MovieDetailsViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @NonNull
    public Single getMovieDetails(@NonNull String movieId) {

        return Single.zip(dataModel.getMovieDetails(movieId),
                dataModel.getMovieVideos(movieId).onErrorReturnItem(new ArrayList<>()),
                dataModel.getMovieReviews(movieId).onErrorReturnItem(new ArrayList<>()),
                (movieDetails, videos, reviews) -> {
                    MovieDetailsUiModel movieDetailsUiModel = toMovieDetailsUiModel(movieDetails);
                    movieDetailsUiModel.setReviews(reviews);
                    movieDetailsUiModel.setTrailerIds(toTrailerIds(videos));
                    return movieDetailsUiModel;
                });
    }

    private MovieDetailsUiModel toMovieDetailsUiModel(MovieDetails movieDetails) {
        return new MovieDetailsUiModel(
                movieDetails.getOriginalTitle(),
                movieDetails.getPosterPath(),
                movieDetails.getReleaseDate(),
                movieDetails.getVoteAverage(),
                movieDetails.getOverview());
    }

    private List<String> toTrailerIds(List<Video> videos) {
        List<String> trailerIds = new ArrayList<>();
        for (Video video : videos) {
                trailerIds.add(video.getKey());
        }
        return trailerIds;
    }
}

