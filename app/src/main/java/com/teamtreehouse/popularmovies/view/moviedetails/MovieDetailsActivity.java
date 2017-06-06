package com.teamtreehouse.popularmovies.view.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.teamtreehouse.popularmovies.view.SingleFragmentActivity;

public class MovieDetailsActivity extends SingleFragmentActivity {
    private static final String MOVIE_ID ="com.teamtreehouse.popularmovies.ui.moviedetail.MovieDetailsActivity.MOVIE_ID";

    public static Intent newIntent(Context context, String movieId){
        Intent intent = new Intent(context,MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID,movieId);

        return intent;
    }
    @Override
    protected Fragment createFragment() {

        String movieId = getIntent().getStringExtra(MOVIE_ID);

        return MovieDetailsFragment.newInstance(movieId);
    }
}
