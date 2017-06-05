package com.teamtreehouse.popularmovies.datamodel.mapper;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.MovieDetailsResponse;
import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsUiModel;

public class MovieDetailsResponseToMovieDetailUIMapper extends Mapper<MovieDetailsResponse,MovieDetailsUiModel> {
    @Override
    public MovieDetailsUiModel map(@NonNull MovieDetailsResponse response) {
        return null;
    }
}
