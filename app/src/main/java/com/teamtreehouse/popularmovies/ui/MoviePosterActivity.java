package com.teamtreehouse.popularmovies.ui;

import android.support.v4.app.Fragment;

public class MoviePosterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MoviePosterFragment();
    }
}
