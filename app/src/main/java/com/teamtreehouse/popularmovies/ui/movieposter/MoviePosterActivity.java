package com.teamtreehouse.popularmovies.ui.movieposter;

import android.support.v4.app.Fragment;

import com.teamtreehouse.popularmovies.ui.SingleFragmentActivity;

public class MoviePosterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MoviePosterFragment();
    }
}
