package com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewsResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<Review> reviews = null;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

    public Integer getId() {
        return id;
    }

    public Integer getPage() {
        return page;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }
}