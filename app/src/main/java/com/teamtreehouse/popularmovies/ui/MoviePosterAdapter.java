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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterHolder> {

    private static final String TAG = "MoviePosterAdapter";

    private Context mContext;
    private List<Movie> mMovies;
    private PublishRelay<Movie> mListItemClickNotifier;

    public MoviePosterAdapter(List<Movie> movies){
        mMovies = movies;
        mListItemClickNotifier = PublishRelay.create();
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

    // -- client methods --

    public void updateMovies(List<Movie> movies){
        mMovies = movies;
        notifyDataSetChanged();
    }

    PublishRelay<Movie> getListItemClickObservable() {
        return mListItemClickNotifier;
    }
}
