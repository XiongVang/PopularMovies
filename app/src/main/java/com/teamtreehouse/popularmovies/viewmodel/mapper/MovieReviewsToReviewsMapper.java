package com.teamtreehouse.popularmovies.viewmodel.mapper;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.MovieReviewsResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;

import java.util.List;

public class MovieReviewsToReviewsMapper extends Mapper<MovieReviewsResponse,List<Review>>{
    @Override
    public List<Review> map(MovieReviewsResponse response) {
        return response.getReviews();
    }
}
