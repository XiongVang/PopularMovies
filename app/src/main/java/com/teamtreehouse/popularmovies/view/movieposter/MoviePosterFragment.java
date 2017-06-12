package com.teamtreehouse.popularmovies.view.movieposter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jakewharton.rxrelay2.PublishRelay;
import com.teamtreehouse.popularmovies.PopularMoviesApp;
import com.teamtreehouse.popularmovies.R;
import com.teamtreehouse.popularmovies.view.moviedetails.MovieDetailsActivity;
import com.teamtreehouse.popularmovies.viewmodel.MovieDetailsViewModel;
import com.teamtreehouse.popularmovies.viewmodel.MoviePosterViewModel;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.MoviePosterUiModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviePosterFragment extends Fragment {

    private static final String TAG = "MoviePosterFragment";

    private static final String SORT_PREF_STATE = "sort_preference_state";
    private static final String LAYOUT_MANAGER_STATE = "layout_manager_state";


    private static final int SORT_BY_MOST_POPULAR = 1;
    private static final int SORT_BY_HIGHEST_RATED = 2;
    private static final int FAVORITE_MOVIES = 3;

    @Inject
    MoviePosterViewModel mMoviePosterViewModel;
    @Inject
    MovieDetailsViewModel mMovieDetailsViewModel;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.error_message)
    LinearLayout mNetworkError;
    @BindView(R.id.retry_button)
    Button mRetryButton;
    @BindView(R.id.movie_poster_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.movie_poster_recyclerview)
    RecyclerView mMoviePosterGrid;
    private Unbinder mUnbinder;

    private CompositeDisposable mCompositeDisposable;


    private Context mContext;
    private PublishRelay<List<MoviePosterUiModel>> mMovieListUpdateNotifier;
    private MoviePosterAdapter mMoviePosterAdapter;
    private GridLayoutManager mGridLayoutManager;

    private int mSortPref = SORT_BY_MOST_POPULAR;
    private Parcelable mLayoutManagerState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieListUpdateNotifier = PublishRelay.create();
        mSortPref = SORT_BY_MOST_POPULAR;
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_poster, container, false);

        mContext = container.getContext();
        ((PopularMoviesApp) mContext.getApplicationContext())
                .getDataComponent()
                .inject(this);

        mUnbinder = ButterKnife.bind(this, view);
        mCompositeDisposable = new CompositeDisposable();

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mMoviePosterAdapter = new MoviePosterAdapter(mMovieListUpdateNotifier);
        mGridLayoutManager = new GridLayoutManager(mContext, 2);
        mMoviePosterGrid.setAdapter(mMoviePosterAdapter);
        mMoviePosterGrid.setLayoutManager(mGridLayoutManager);

        subscribeToListItemClickListener();

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewStateRestored: ");
        if (savedInstanceState != null) {
            mSortPref = savedInstanceState.getInt(SORT_PREF_STATE);
            mLayoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
        }

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onResume: ");

        if (!isConnectedToNetwork() && (mSortPref != FAVORITE_MOVIES)) {
            showNetworkError();
            return;
        }

        fetchMoviePosters();

        if(mLayoutManagerState!=null) {
            mGridLayoutManager.onRestoreInstanceState(mLayoutManagerState);
        }
    }


    // Checks for network connection
    private boolean isConnectedToNetwork() {

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    private void subscribeToListItemClickListener() {

        mMoviePosterAdapter.getListItemClickNotifier()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String movieId) {
                        startActivity(MovieDetailsActivity.newIntent(mContext, movieId));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: getListItemClickNotifier()", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void fetchMoviePosters() {
        showProgressBar();

        Single<List<MoviePosterUiModel>> moviesData;

        switch (mSortPref) {
            case SORT_BY_HIGHEST_RATED:
                moviesData = mMoviePosterViewModel.getHighestRateMovies();
                break;
            case FAVORITE_MOVIES:
                moviesData = mMoviePosterViewModel.getFavoriteMovies();
                break;
            default:
                moviesData = mMoviePosterViewModel.getMostPopularMovies();
        }

        moviesData
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<MoviePosterUiModel>>() {
                    @Override
                    public void onSuccess(List<MoviePosterUiModel> movies) {
                        mMovieListUpdateNotifier.accept(movies);
                        showMoviePosterGrid();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: fetchMoviePosters()", e);
                        showNetworkError();
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_PREF_STATE, mSortPref);
        outState.putParcelable(LAYOUT_MANAGER_STATE,mGridLayoutManager.onSaveInstanceState());
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();

    }

    private void showNetworkError() {
        mRetryButton.setOnClickListener(v -> onResume());
        mNetworkError.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mMoviePosterGrid.setVisibility(View.GONE);
        ;
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMoviePosterGrid.setVisibility(View.GONE);
        mNetworkError.setVisibility(View.GONE);

    }

    private void showMoviePosterGrid() {
        mMoviePosterGrid.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mNetworkError.setVisibility(View.GONE);
    }


    // --- Options Menu ---

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sort_options, menu);

        if (mSortPref == SORT_BY_HIGHEST_RATED) {
            menu.findItem(R.id.sort_by_highest_rated).setEnabled(false);
            menu.findItem(R.id.sort_by_most_popular).setEnabled(true);
            menu.findItem(R.id.favorite_movies).setEnabled(true);
        } else if (mSortPref == FAVORITE_MOVIES) {
            menu.findItem(R.id.sort_by_highest_rated).setEnabled(true);
            menu.findItem(R.id.sort_by_most_popular).setEnabled(true);
            menu.findItem(R.id.favorite_movies).setEnabled(false);
        } else {
            menu.findItem(R.id.sort_by_highest_rated).setEnabled(true);
            menu.findItem(R.id.sort_by_most_popular).setEnabled(false);
            menu.findItem(R.id.favorite_movies).setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort_by_highest_rated:
                mSortPref = SORT_BY_HIGHEST_RATED;
                break;
            case R.id.sort_by_most_popular:
                mSortPref = SORT_BY_MOST_POPULAR;
                break;
            case R.id.favorite_movies:
                mSortPref = FAVORITE_MOVIES;
                break;
        }

        mMoviePosterGrid.scrollToPosition(0);
        fetchMoviePosters();
        getActivity().invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

}
