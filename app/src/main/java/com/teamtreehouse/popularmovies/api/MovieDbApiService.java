package com.teamtreehouse.popularmovies.api;

import com.teamtreehouse.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Retrofit;

@Singleton
public class MovieDbApiService {

    private static final String TAG = "MovieDbApiService";

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private final String API_KEY;

    private final Retrofit.Builder mRetrofitBuilder;

    private final MovieDbApi mMovieDbApi;

    @Inject
    public MovieDbApiService(Retrofit.Builder retrofitBuilder, @Named("API_KEY") String apiKey) {

        mRetrofitBuilder = retrofitBuilder;
        API_KEY = apiKey;

        mMovieDbApi = mRetrofitBuilder
                .baseUrl(BASE_URL)
                .build()
                .create(MovieDbApi.class);
    }

    public Single<List<Movie>> getMostPopularMovies() {

        return mMovieDbApi
                .getPopular(API_KEY)
                .map(this::mapResponseToMovie);
    }

    public Single<List<Movie>> getTopRatedMovies() {

        return mMovieDbApi
                .getTopRated(API_KEY)
                .map(this::mapResponseToMovie);
    }

    private List<Movie> mapResponseToMovie(MovieApiResponse response) {


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

