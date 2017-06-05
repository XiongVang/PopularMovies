package com.teamtreehouse.popularmovies.datamodel.datasource.remote.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieDbApi {

    @GET("movie/popular")
    Single<MovieApiResponse> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Single<MovieApiResponse> getTopRated(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Single<MovieDetailsResponse> getMovieDetails(@Path("id") String movieId,
                                          @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Single<MovieVideosResponse> getVideos(@Path("id") String movieId,
                             @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Single<MovieReviewsResponse> getReviews(@Path("id") String movieId,
                              @Query("api_key") String apiKey);
}
