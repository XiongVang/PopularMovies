package com.teamtreehouse.popularmovies.view.moviedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxrelay2.PublishRelay;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.popularmovies.PopularMoviesApp;
import com.teamtreehouse.popularmovies.R;
import com.teamtreehouse.popularmovies.datamodel.datasource.remote.api.responses.reviews.Review;
import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsUiModel;
import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailFragment extends Fragment {
    private static final String TAG = "MovieDetailFragment";

    private static final String ARG_MOVIE_ID = "com.teamtreehouse.popularmovies.ui.MovieDetialFragment.ARG_MOVIE_ID";

    public static Fragment newInstance(String movieId) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_MOVIE_ID, movieId);
        Fragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Inject
    MovieDetailsViewModel mMovieDetailsViewModel;

    @BindView(R.id.error_message)
    LinearLayout mErrorMessage;
    @BindView(R.id.retry_button)
    Button mRetryButton;
    @BindView(R.id.movie_poster_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.movie_details)
    LinearLayout mMovieDetails;

    @BindView(R.id.movie_detail_title)
    TextView mTitle;
    @BindView(R.id.movie_detail_poster_thumbnail)
    ImageView mPosterThumbnail;
    @BindView(R.id.movie_detail_release_date)
    TextView mReleaseDate;
    @BindView(R.id.movie_detail_avg_rating)
    TextView mAvgRating;
    @BindView(R.id.favorites_button)
    ImageButton mFavoritesButton;
    @BindView(R.id.movie_detail_plot_synopsis)
    TextView mPlotSynopsis;
    @BindView(R.id.trailers)
    RecyclerView mTrailersView;
    @BindView(R.id.no_trailers_found)
    TextView mNoTrailersFound;
    @BindView(R.id.reviews)
    RecyclerView mReviewsView;
    @BindView(R.id.no_reviews_found)
    TextView mNoReviewsFound;
    private Unbinder mUnbinder;

    private String mMovieId;
    private MovieDetailsUiModel mMovieDetailsUiModel;
    private PublishRelay<List<String>> mTrailersUpdatedNotifier;
    private MovieTrailersAdapter mTrailersAdapter;
    private PublishRelay<List<Review>> mReviewsUpdatedNotifier;
    private MovieReviewsAdapter mReviewsAdapter;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getString(ARG_MOVIE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mContext = container.getContext();
        ((PopularMoviesApp) mContext.getApplicationContext())
                .getDataComponent()
                .inject(this);

        mUnbinder = ButterKnife.bind(this, view);

        showNoTrailersFound();
        setupTrailersRecyclerView();

        showNoReviewsFound();
        setupReviewsRecyclerView();
        return view;
    }

    private void setupTrailersRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mTrailersUpdatedNotifier = PublishRelay.create();
        mTrailersAdapter = new MovieTrailersAdapter(mTrailersUpdatedNotifier);

        mTrailersView.setLayoutManager(layoutManager);
        mTrailersView.setAdapter(mTrailersAdapter);
    }

    private void setupReviewsRecyclerView() {
        mReviewsUpdatedNotifier = PublishRelay.create();
        mReviewsAdapter = new MovieReviewsAdapter(mReviewsUpdatedNotifier);

        mReviewsView.setLayoutManager(new LinearLayoutManager(mContext));
        mReviewsView.setAdapter(mReviewsAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mMovieId == null) {
            showErrorMessage();
            return;
        } else {
            showProgressBar();
            bind();
            populateDetails();
            showMovieDetails();
        }
    }

    private void bind() {
        mMovieDetailsViewModel
                .getMovieDetails(mMovieId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<MovieDetailsUiModel>() {
                    @Override
                    public void onSuccess(MovieDetailsUiModel value) {
                        mMovieDetailsUiModel = value;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        showErrorMessage();
                    }
                });
    }

    private void loadTrailers(List<String> trailerIds) {
        if (trailerIds.size() == 0) {
            showNoTrailersFound();
            return;
        }

        mTrailersUpdatedNotifier.accept(trailerIds);
        showTrailers();


    }

    private void showNoTrailersFound() {
        mNoTrailersFound.setVisibility(View.VISIBLE);
        mTrailersView.setVisibility(View.GONE);
    }

    private void showTrailers() {
        mNoTrailersFound.setVisibility(View.GONE);
        mTrailersView.setVisibility(View.VISIBLE);
    }

    private void loadReviews(List<Review> reviews) {
        if (reviews.size() == 0) {
            showNoReviewsFound();
            return;
        }

        mReviewsUpdatedNotifier.accept(reviews);
        showReviews();


    }

    private void showNoReviewsFound() {
        mNoReviewsFound.setVisibility(View.VISIBLE);
        mReviewsView.setVisibility(View.GONE);
    }

    private void showReviews() {
        mNoReviewsFound.setVisibility(View.GONE);
        mReviewsView.setVisibility(View.VISIBLE);
    }


    private void populateDetails() {
        mTitle.setText(mMovie.getTitle());
        Picasso.with(mContext)
                .load(mMovie.getImageThumbnailUrl())
                .error(R.drawable.movie_poster_error)
                .placeholder(R.drawable.movie_poster_placeholder)
                .into(mPosterThumbnail);
        mReleaseDate.setText(mMovie.getReleaseDate());
        mAvgRating.setText(String.valueOf(mMovie.getUserRating()));
        mPlotSynopsis.setText(mMovie.getPlotSynopsis());
    }


    private void showErrorMessage() {
        mRetryButton.setOnClickListener(v -> onResume());
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mMovieDetails.setVisibility(View.GONE);
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
