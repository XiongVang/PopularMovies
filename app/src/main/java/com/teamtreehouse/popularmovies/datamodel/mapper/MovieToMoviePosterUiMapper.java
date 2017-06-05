package com.teamtreehouse.popularmovies.datamodel.mapper;


import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.model.Movie;
import com.teamtreehouse.popularmovies.viewmodel.MoviePosterUiModel;

public class MovieToMoviePosterUiMapper extends Mapper<Movie,MoviePosterUiModel>{

    @Override
    public MoviePosterUiModel map(@NonNull Movie movie) {
        return new MoviePosterUiModel(movie.getMovieId(),movie.getImageThumbnailUrl());
    }
}
