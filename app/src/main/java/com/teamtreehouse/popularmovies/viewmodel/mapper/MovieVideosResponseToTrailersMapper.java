package com.teamtreehouse.popularmovies.viewmodel.mapper;

import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.MovieVideosResponse;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.videos.Video;

import java.util.ArrayList;
import java.util.List;

public class MovieVideosResponseToTrailersMapper extends Mapper<MovieVideosResponse,List<String>> {
    @Override
    public List<String> map(MovieVideosResponse response) {

        List<Video> results = response.getVideos();

        List<String> trailers = new ArrayList<>();

        for(Video result: results){
            if(result.getType().equalsIgnoreCase("trailer"))
                trailers.add(result.getKey());
        }

        return trailers;

    }
}
