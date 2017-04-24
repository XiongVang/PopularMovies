package com.teamtreehouse.popularmovies.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamtreehouse.popularmovies.R;
import com.teamtreehouse.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailFragment extends Fragment {
    private static final String TAG = "MovieDetailFragment";

    private static final String ARG_MOVIE = "com.teamtreehouse.popularmovies.ui.MovieDetialFragment.ARG_MOVIE";

    public static Fragment newInstance(Movie movie){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE,movie);
        Fragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.error_message)
    LinearLayout mErrorMessage;
    @BindView(R.id.retry_button)
    Button mRetryButton;
    @BindView(R.id.movie_poster_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.movie_details)
    GridLayout mMovieDetails;

    @BindView(R.id.movie_detail_title)
    TextView mTitle;
    @BindView(R.id.movie_detail_poster_thumbnail)
    ImageView mPosterThumbnail;
    @BindView(R.id.movie_detail_release_date)
    TextView mReleaseDate;
    @BindView(R.id.movie_detail_avg_rating)
    TextView mAvgRating;
    @BindView(R.id.movie_detail_plot_synopsis)
    TextView mPlotSynopsis;
    private Unbinder mUnbinder;

    private Movie mMovie;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovie = getArguments().getParcelable(ARG_MOVIE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail,container,false);
        mContext = container.getContext();
        mUnbinder = ButterKnife.bind(this,view);

        mRetryButton.setOnClickListener(v -> onResume());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMovie == null) {
            showErrorMessage();
        } else {
            showProgressBar();
            populateDetails();
            showMovieDetails();
        }
    }

    private void populateDetails() {
        mTitle.setText(mMovie.getTitle());
        Picasso.with(mContext)
                .load(mMovie.getImageThumbnailUrl())
                .into(mPosterThumbnail);
        mReleaseDate.setText(mMovie.getReleaseDate());
        mAvgRating.setText(String.valueOf(mMovie.getUserRating()));
        mPlotSynopsis.setText(mMovie.getPlotSynopsis());
    }


    private void showErrorMessage() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mMovieDetails.setVisibility(View.GONE);
        ;
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMovieDetails.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);

    }

    private void showMovieDetails() {
        mMovieDetails.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }
}
