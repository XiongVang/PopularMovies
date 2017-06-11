package com.teamtreehouse.popularmovies;

import android.app.Application;

import com.teamtreehouse.popularmovies.di.ApiModule;
import com.teamtreehouse.popularmovies.di.ApplicationModule;
import com.teamtreehouse.popularmovies.di.DaggerDataComponent;
import com.teamtreehouse.popularmovies.di.DataComponent;
import com.teamtreehouse.popularmovies.di.DataModelModule;

public class PopularMoviesApp extends Application {


    // Enter api key here to run app
    private final String API_KEY = "";

    DataComponent mDataComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        mDataComponent = DaggerDataComponent.builder()
                .apiModule(new ApiModule(API_KEY))
                .dataModelModule(new DataModelModule())
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public DataComponent getDataComponent() {
        return mDataComponent;
    }

}