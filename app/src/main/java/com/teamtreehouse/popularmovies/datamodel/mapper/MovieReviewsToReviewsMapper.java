package com.teamtreehouse.popularmovies.datamodel.mapper;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.MovieReviewsResponse;
import com.teamtreehouse.popularmovies.datamodel.model.Review;

import java.util.List;

public class MovieReviewsToReviewsMapper extends Mapper<MovieReviewsResponse,List<Review>>{
    @Override
    public List<Review> map(MovieReviewsResponse response) {
        return response.getReviews();
    }
}
