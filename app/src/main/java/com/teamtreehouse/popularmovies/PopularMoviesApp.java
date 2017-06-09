package com.teamtreehouse.popularmovies;

import android.app.Application;

import com.teamtreehouse.popularmovies.di.ApiModule;
import com.teamtreehouse.popularmovies.di.ApplicationModule;
import com.teamtreehouse.popularmovies.di.DaggerDataComponent;
import com.teamtreehouse.popularmovies.di.DataComponent;
import com.teamtreehouse.popularmovies.di.DataModelModule;

public class PopularMoviesApp extends Application {

    private final String API_KEY = "63ba90194ff5cc0e2f703ed4f25413f1";

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