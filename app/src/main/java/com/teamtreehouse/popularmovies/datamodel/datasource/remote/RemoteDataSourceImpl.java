package com.teamtreehouse.popularmovies.datamodel.datasource.remote;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.MovieApiResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.MovieDbApiService;
import com.teamtreehouse.popularmovies.datamodel.mapper.MovieApiResponseToMoviesMapper;
import com.teamtreehouse.popularmovies.datamodel.model.Movie;

import java.util.List;

import io.reactivex.Single;

public class RemoteDataSourceImpl implements RemoteDataSource {


    private final MovieDbApiService apiService;
    private final MovieApiResponseToMoviesMapper toMoviesMapper;

    public RemoteDataSourceImpl(MovieDbApiService apiService){
        this.apiService = apiService;
        this.toMoviesMapper = new MovieApiResponseToMoviesMapper();

    }
    @Override
    public Single<List<Movie>> getMostPopularMovies() {
        return apiService
                .getMostPopularMovies()
                .map(this::mapResponseToMovies);
    }

    @Override
    public Single<List<Movie>> getTopRatedMovies() {
        return apiService
                .getTopRatedMovies()
                .map(this::mapResponseToMovies);
    }

    private List<Movie> mapResponseToMovies(MovieApiResponse response){
        return toMoviesMapper.map(response);
    }
}
