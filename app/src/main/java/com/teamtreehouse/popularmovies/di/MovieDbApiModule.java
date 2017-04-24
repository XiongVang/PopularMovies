package com.teamtreehouse.popularmovies.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class MovieDbApiModule {

    private final String API_KEY;

    public MovieDbApiModule(String apiKey){
        API_KEY = apiKey;
    }

    @Singleton
    @Provides
    @Named("API_KEY")
    String provideApiKey(){
        return API_KEY;
    }

}
