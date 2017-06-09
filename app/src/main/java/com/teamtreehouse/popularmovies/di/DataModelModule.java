package com.teamtreehouse.popularmovies.di;

import android.app.Application;

import com.teamtreehouse.popularmovies.datamodel.DataModel;
import com.teamtreehouse.popularmovies.datamodel.DataModelImpl;
import com.teamtreehouse.popularmovies.datamodel.datasources.local.LocalDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasources.local.LocalDataSourceImpl;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.RemoteDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.RemoteDataSourceImpl;
import com.teamtreehouse.popularmovies.datamodel.datasources.remote.api.MovieDbApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class DataModelModule {

    @Singleton
    @Provides
    LocalDataSource provideLocalDataSource(Application application){
        return new LocalDataSourceImpl(application.getContentResolver());
    }

    @Singleton
    @Provides
    RemoteDataSource provideRemoteDataSource(MovieDbApiService movieDbApiService){
        return new RemoteDataSourceImpl(movieDbApiService);
    }

    @Singleton
    @Provides
    DataModel provideDataModel(RemoteDataSource remoteDataSource, LocalDataSource localDataSource){
        return new DataModelImpl(remoteDataSource,localDataSource);

    }
}
