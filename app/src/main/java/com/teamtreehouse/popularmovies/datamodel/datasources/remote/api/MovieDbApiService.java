package com.teamtreehouse.popularmovies.datamodel.datasources.remote.api;

import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.discovery.MovieApiResponse;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.details.MovieDetails;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.reviews.MovieReviewsResponse;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.responses.videos.MovieVideosResponse;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class MovieDbApiService {

    private static final String TAG = "MovieDbApiService";

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";



    private final String API_KEY;

    private final Retrofit.Builder mRetrofitBuilder;

    private final MovieDbApi mMovieDbApi;

    public MovieDbApiService(Retrofit.Builder retrofitBuilder, String apiKey) {


        mRetrofitBuilder = retrofitBuilder;
        API_KEY = apiKey;

        mMovieDbApi = mRetrofitBuilder
                .baseUrl(BASE_URL)
                .build()
                .create(MovieDbApi.class);
    }

    public Single<MovieApiResponse> getMostPopularMovies() {

        return mMovieDbApi
                .getPopular(API_KEY);
    }

    public Single<MovieApiResponse> getTopRatedMovies() {

        return mMovieDbApi
                .getTopRated(API_KEY);
    }

    public Single<MovieVideosResponse> getVideos(String movieId) {
        return mMovieDbApi
                .getVideos(movieId, API_KEY);
    }

    public Single<MovieReviewsResponse> getReviews(String movieId) {
        return mMovieDbApi
                .getReviews(movieId, API_KEY);
    }

    public Single<MovieDetails> getMovieDetails(String movieId){
        return mMovieDbApi.getMovieDetails(movieId,API_KEY);
    }

}

