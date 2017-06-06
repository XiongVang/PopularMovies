package com.teamtreehouse.popularmovies.viewmodel.uimodels;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsUiModel {

    private String mOriginalTitle;
    private String mImageThumbnailUrl;
    private String mReleaseDate;
    private String mUserRating;
    private String mPlotSynopsis;
    private List<String> mTrailerIds;
    private List<Review> mReviews;

    public MovieDetailsUiModel(String originalTitle,
                               String imageThumbnailUrl,
                               String releaseDate,
                               String userRating,
                               String plotSynopsis) {

        mOriginalTitle = originalTitle;
        mImageThumbnailUrl = imageThumbnailUrl;
        mReleaseDate = releaseDate;
        mUserRating = userRating;
        mPlotSynopsis = plotSynopsis;

        mTrailerIds = new ArrayList<>();
        mReviews = new ArrayList<>();
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getImageThumbnailUrl() {
        return mImageThumbnailUrl;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public List<String> getTrailerIds() {
        return mTrailerIds;
    }

    public void setTrailerIds(List<String> trailerIds) {
        mTrailerIds = trailerIds;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }
}
