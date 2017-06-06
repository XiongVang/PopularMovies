package com.teamtreehouse.popularmovies.viewmodel.mapper;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieApiResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.discovery.MovieResult;

import java.util.ArrayList;
import java.util.List;

public class MovieApiResponseToMoviesMapper extends Mapper<MovieApiResponse,List<Movie>> {

    private final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    public List<Movie> map(MovieApiResponse response) {


        List<Movie> movies = new ArrayList<>();
        List<MovieResult> movieResults = response.getMovieResults();

        for (MovieResult movieResult : movieResults) {
            movies.add(
                    new Movie(
                            movieResult.getId(),
                            movieResult.getOriginalTitle(),
                            POSTER_BASE_URL + movieResult.getPosterPath(),
                            movieResult.getOverview(),
                            movieResult.getVoteAverage(),
                            movieResult.getReleaseDate()
                    ));
        }


        return movies;
    }

}
