package com.teamtreehouse.popularmovies.datamodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.datasource.local.LocalDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.RemoteDataSource;
import com.teamtreehouse.popularmovies.datamodel.mapper.Mapper;
import com.teamtreehouse.popularmovies.datamodel.mapper.MovieToMoviePosterUiMapper;
import com.teamtreehouse.popularmovies.datamodel.model.Movie;
import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsUiModel;
import com.teamtreehouse.popularmovies.viewmodel.MoviePosterUiModel;

import java.util.List;

import io.reactivex.Single;

public class DataModelImpl implements DataModel {


    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;


    private final Mapper toMoviePosterUiMapper;

    public DataModelImpl(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {

        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.toMoviePosterUiMapper = new MovieToMoviePosterUiMapper();
    }

    @Override

    public Single<List<MoviePosterUiModel>> getMostPopularMovies() {

        return remoteDataSource.getMostPopularMovies()
                .map(this::mapToMoviePosterUiModel);
    }

    @Override
    public Single<List<MoviePosterUiModel>> getTopRatedMovies() {
        return remoteDataSource.getTopRatedMovies()
                .map(this::mapToMoviePosterUiModel);
    }

    @Override
    public Single<List<MoviePosterUiModel>> getFavoriteMovies() {
        return localDataSource.getFavoriteMovies()
                .map(this::mapToMoviePosterUiModel);
    }

    private List<MoviePosterUiModel> mapToMoviePosterUiModel(List<Movie> movies) {
        return toMoviePosterUiMapper.map(movies);
    }

    @Override
    public Single<MovieDetailsUiModel> getMovieDetails(@NonNull String movieId) {
        return null;
    }


}
