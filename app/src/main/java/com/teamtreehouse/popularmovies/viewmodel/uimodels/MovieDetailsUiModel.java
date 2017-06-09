package com.teamtreehouse.popularmovies.viewmodel.uimodels;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsUiModel {

    private String mOriginalTitle;
    private String mImageThumbnailUrl;
    private String mReleaseDate;
    private String mUserRating;
    private String mPlotSynopsis;
    private List<TrailerUiModel> mTrailers;
    private List<ReviewUiModel> mReviews;

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

        mTrailers = new ArrayList<>();
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

    public List<TrailerUiModel> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(List<TrailerUiModel> trailers) {
        mTrailers = trailers;
    }

    public List<ReviewUiModel> getReviews() {
        return mReviews;
    }

    public void setReviews(List<ReviewUiModel> reviews) {
        mReviews = reviews;
    }
}
