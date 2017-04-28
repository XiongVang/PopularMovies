package com.teamtreehouse.popularmovies.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideosResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("results")
    @Expose
    public List<Video> videos = null;

    public Integer getId() {
        return id;
    }

    public List<Video> getVideos() {
        return videos;
    }
}