package com.teamtreehouse.popularmovies.di;

import com.teamtreehouse.popularmovies.ui.MoviePosterFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MovieDbApiModule.class,RetrofitBuilderModule.class})
public interface MovieDbApiComponent {
    void inject(MoviePosterFragment fragment);
}
