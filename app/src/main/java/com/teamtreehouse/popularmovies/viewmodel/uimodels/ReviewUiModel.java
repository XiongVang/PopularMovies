package com.teamtreehouse.popularmovies.viewmodel.uimodels;

public class ReviewUiModel {

    private String author;
    private String content;

    public ReviewUiModel(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
