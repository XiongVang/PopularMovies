package com.teamtreehouse.popularmovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Singleton
@Module
public class RetrofitBuilderModule {
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }
    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }
    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }
    @Singleton
    @Provides
    ScalarsConverterFactory provideScalarConverterFactory(){
        return ScalarsConverterFactory.create();
    }
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient,
                                            RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                                            GsonConverterFactory gsonConverterFactory,
                                            ScalarsConverterFactory scalarConverterFactory){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .addConverterFactory(scalarConverterFactory);

    }
}
