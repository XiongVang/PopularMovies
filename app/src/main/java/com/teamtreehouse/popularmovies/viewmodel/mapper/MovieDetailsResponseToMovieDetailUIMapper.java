package com.teamtreehouse.popularmovies.viewmodel.mapper;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsUiModel;

public class MovieDetailsResponseToMovieDetailUIMapper extends Mapper<MovieDetails,MovieDetailsUiModel> {
    @Override
    public MovieDetailsUiModel map(@NonNull MovieDetails response) {
        return new MovieDetailsUiModel(response.originalTitle,
                response.getPosterPath(),
                response.getReleaseDate(),
                response.getVoteAverage(),
                response.getOverview());
    }
}
