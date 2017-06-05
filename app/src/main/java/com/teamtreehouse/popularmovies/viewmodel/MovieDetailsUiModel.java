package com.teamtreehouse.popularmovies.viewmodel;

public class MovieDetailsUiModel {

    private String mOriginalTitle;
    private String mImageThumbnailUrl;
    private String mReleaseDate;
    private double userRating;
    private String PlotSynopsis;

    public MovieDetailsUiModel(String originalTitle, String imageThumbnailUrl, String releaseDate, double userRating, String plotSynopsis) {
        mOriginalTitle = originalTitle;
        mImageThumbnailUrl = imageThumbnailUrl;
        mReleaseDate = releaseDate;
        this.userRating = userRating;
        PlotSynopsis = plotSynopsis;
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

    public double getUserRating() {
        return userRating;
    }

    public String getPlotSynopsis() {
        return PlotSynopsis;
    }
}
