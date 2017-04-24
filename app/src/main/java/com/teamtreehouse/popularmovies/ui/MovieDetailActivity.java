package com.teamtreehouse.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.teamtreehouse.popularmovies.model.Movie;

/**
 * Created by vang4999 on 4/21/17.
 */

public class MovieDetailActivity extends SingleFragmentActivity {
    private static final String EXTRA_MOVIE ="com.teamtreehouse.popularmovies.ui.MovieDetailActivity.EXTRA_MOVIE";

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context,MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE,movie);

        return intent;
    }
    @Override
    protected Fragment createFragment() {

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        return MovieDetailFragment.newInstance(movie);
    }
}
