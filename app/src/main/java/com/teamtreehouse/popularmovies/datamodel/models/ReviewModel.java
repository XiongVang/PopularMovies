package com.teamtreehouse.popularmovies.datamodel.models;

public class ReviewModel {

    private String movieId;
    private String author;
    private String content;

    public ReviewModel(String movieId, String author, String content) {
        this.movieId = movieId;
        this.author = author;
        this.content = content;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
