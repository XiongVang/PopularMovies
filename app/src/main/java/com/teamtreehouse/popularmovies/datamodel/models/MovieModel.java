package com.teamtreehouse.popularmovies.datamodel.models;

public class MovieModel {

    private String movieId;
    private String title;
    private String posterPath;
    private String releaseDate;
    private String userRating;
    private String plotSynopsis;

    public MovieModel(String movieId,
                      String title,
                      String posterPath,
                      String releaseDate,
                      String userRating,
                      String plotSynopsis) {

        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.plotSynopsis = plotSynopsis;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }
}
