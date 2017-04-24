package com.teamtreehouse.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.teamtreehouse.popularmovies.PopularMoviesApp;
import com.teamtreehouse.popularmovies.R;
import com.teamtreehouse.popularmovies.api.MovieDbApiService;
import com.teamtreehouse.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviePosterFragment extends Fragment {

    private static final String TAG = "MoviePosterFragment";

    private static final int SORT_BY_MOST_POPULAR = 1;
    private static final int SORT_BY_HIGHEST_RATED = 2;

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
    private Disposable mMovieApiSubcription;


    private Context mContext;
    MoviePosterAdapter mMoviePosterAdapter;

    @Inject
    MovieDbApiService mMovieDbApiService;

    private int mSortPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSortPref = SORT_BY_MOST_POPULAR;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_poster, container, false);

        mContext = container.getContext();
        ((PopularMoviesApp) mContext.getApplicationContext())
                .getMovieDbApiComponent()
                .inject(this);

        mUnbinder = ButterKnife.bind(this, view);
        mCompositeDisposable = new CompositeDisposable();


        mMoviePosterAdapter = new MoviePosterAdapter(new ArrayList<>());
        mMoviePosterGrid.setLayoutManager(new GridLayoutManager(mContext, 2));
        mMoviePosterGrid.setAdapter(mMoviePosterAdapter);

        mRetryButton.setOnClickListener(v -> onResume());

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isConnectedToNetwork()) {
            showNetworkError();
            return;
        }

        fetchMoviesFromApiService();

        subscribeToListItemClickListener();

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

        mMoviePosterAdapter.getListItemClickObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Movie clickedMovie) {
                        Intent intent = MovieDetailActivity.newIntent(mContext, clickedMovie);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void fetchMoviesFromApiService() {
        showProgressBar();

        Single<List<Movie>> apiService;

        if (mSortPref == SORT_BY_HIGHEST_RATED) {
            apiService = mMovieDbApiService.getTopRatedMovies();
        } else {
            apiService = mMovieDbApiService.getMostPopularMovies();
        }

        apiService
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mMovieApiSubcription = d;
                    }

                    @Override
                    public void onSuccess(List<Movie> movies) {
                        mMoviePosterAdapter.updateMovies(movies);
                        showMoviePosterGrid();
                        mMovieApiSubcription.dispose();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        showNetworkError();
                    }
                });
    }


    @Override
    public void onPause() {
        super.onPause();
        mCompositeDisposable.clear();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();

    }

    private void showNetworkError() {
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

        if(mSortPref == SORT_BY_HIGHEST_RATED){
            menu.findItem(R.id.sort_by_highest_rated).setEnabled(false);
            menu.findItem(R.id.sort_by_most_popular).setEnabled(true);
        } else {
            menu.findItem(R.id.sort_by_highest_rated).setEnabled(true);
            menu.findItem(R.id.sort_by_most_popular).setEnabled(false);
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
        }

        mMoviePosterGrid.scrollToPosition(0);
        fetchMoviesFromApiService();
        getActivity().invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

}
