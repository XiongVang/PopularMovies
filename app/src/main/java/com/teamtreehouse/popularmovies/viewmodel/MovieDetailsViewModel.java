package com.teamtreehouse.popularmovies.viewmodel;

import android.support.annotation.NonNull;

import com.teamtreehouse.popularmovies.datamodel.DataModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MovieDetailsViewModel {
    private static final String TAG = "MovieDetailsViewModel";

    private final DataModel dataModel;

    @Inject
    public MovieDetailsViewModel(DataModel dataModel){
        this.dataModel = dataModel;
    }

    @NonNull
    public Single<MovieDetailsUiModel> getMovieDetails(@NonNull String movieId){
        return Single.concat(dataModel.getMovieDetails().map())
    }

}
