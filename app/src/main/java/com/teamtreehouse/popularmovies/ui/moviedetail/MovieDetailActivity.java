package com.teamtreehouse.popularmovies.ui.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.teamtreehouse.popularmovies.ui.SingleFragmentActivity;

/**
 * Created by vang4999 on 4/21/17.
 */

public class MovieDetailActivity extends SingleFragmentActivity {
    private static final String MOVIE_ID ="com.teamtreehouse.popularmovies.ui.moviedetail.MovieDetailActivity.MOVIE_ID";

    public static Intent newIntent(Context context, String movieId){
        Intent intent = new Intent(context,MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID,movieId);

        return intent;
    }
    @Override
    protected Fragment createFragment() {

        String movieId = getIntent().getStringExtra(MOVIE_ID);

        return MovieDetailFragment.newInstance(movieId);
    }
}
