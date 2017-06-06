package com.teamtreehouse.popularmovies.view.moviedetails;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jakewharton.rxrelay2.PublishRelay;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ThumbnailViewHolder> {

    private static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/VIDEO_ID/default.jpg";

    private Context mContext;

    List<String> mTrailerIds;

    public MovieTrailersAdapter(PublishRelay<List<String>> onTrailersUpdatedNotifier){
        mTrailerIds = new ArrayList<>();

        onTrailersUpdatedNotifier
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((trailerIds) -> updateUI(trailerIds));

    }

    private void updateUI(List<String> trailerIds){
        mTrailerIds = trailerIds;
        notifyDataSetChanged();
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_trailer_thumbnail, parent, false);

        return new ThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(YOUTUBE_THUMBNAIL_URL.replace("VIDEO_ID",mTrailerIds.get(position)))
                .error(R.drawable.movie_poster_error)
                .placeholder(R.drawable.movie_poster_placeholder)
                .into(holder.mTrailerThumbnail);

        holder.mTrailerThumbnail
                .setOnClickListener(v -> watchYoutubeVideo(mTrailerIds.get(position)));

    }

    private void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            mContext.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            mContext.startActivity(webIntent);
        }
    }


    @Override
    public int getItemCount() {
        return mTrailerIds.size();
    }

    public class ThumbnailViewHolder  extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_trailer_thumbnail)
        ImageView mTrailerThumbnail;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
