package com.teamtreehouse.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jakewharton.rxrelay2.PublishRelay;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.popularmovies.R;
import com.teamtreehouse.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterHolder> {

    private static final String TAG = "MoviePosterAdapter";

    private final PublishRelay<List<Movie>> mMovieListChangedNotifier;

    private Context mContext;
    private List<Movie> mMovies = new ArrayList<>();
    private PublishRelay<Movie> mListItemClickNotifier;

    public MoviePosterAdapter(PublishRelay<List<Movie>> movieListNotifier){

        mMovieListChangedNotifier = movieListNotifier;
        bind();

        mListItemClickNotifier = PublishRelay.create();
    }

    private void bind(){
        mMovieListChangedNotifier
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    mMovies = movies;
                    notifyDataSetChanged();
                });
    }


    @Override
    public MoviePosterAdapter.MoviePosterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie_poster,parent,false);
        return new MoviePosterHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviePosterAdapter.MoviePosterHolder holder, int position) {
        Picasso.with(mContext)
                .load(mMovies.get(position).getImageThumbnailUrl())
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public class MoviePosterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster_holder)
        ImageView mMoviePoster;

        Unbinder mUnbinder;

        public MoviePosterHolder(View itemView) {
            super(itemView);
            mUnbinder = ButterKnife.bind(this,itemView);
            mMoviePoster.setOnClickListener(v -> mListItemClickNotifier
                    .accept(mMovies.get(getAdapterPosition())));
        }
    }

    // Client Methods
    public PublishRelay<Movie> getListItemClickNotifier(){
        return mListItemClickNotifier;
    }

}
