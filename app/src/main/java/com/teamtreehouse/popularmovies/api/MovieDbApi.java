package com.teamtreehouse.popularmovies.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MovieDbApi {

    @GET("movie/popular")
    Single<MovieApiResponse> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Single<MovieApiResponse> getTopRated(@Query("api_key") String apiKey);

}
