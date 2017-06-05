package com.teamtreehouse.popularmovies.di;

import com.teamtreehouse.popularmovies.datamodel.DataModel;
import com.teamtreehouse.popularmovies.datamodel.DataModelImpl;
import com.teamtreehouse.popularmovies.datamodel.datasource.local.LocalDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasource.local.LocalDataSourceImpl;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.RemoteDataSource;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.RemoteDataSourceImpl;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.MovieDbApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class DataModelModule {

    @Singleton
    @Provides
    LocalDataSource provideLocalDataSource(){
        return new LocalDataSourceImpl();
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
