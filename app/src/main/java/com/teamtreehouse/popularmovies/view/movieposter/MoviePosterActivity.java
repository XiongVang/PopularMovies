package com.teamtreehouse.popularmovies.view.movieposter;

import android.support.v4.app.Fragment;

import com.teamtreehouse.popularmovies.view.SingleFragmentActivity;

public class MoviePosterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MoviePosterFragment();
    }
}
