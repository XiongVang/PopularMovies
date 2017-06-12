package com.teamtreehouse.popularmovies.view.moviedetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamtreehouse.popularmovies.R;
import com.teamtreehouse.popularmovies.viewmodel.uimodels.ReviewUiModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;


public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewHolder>{

    private static final String TAG = "MovieReviewsAdapter";

    private Context mContext;

    List<ReviewUiModel> mReviews;

    public MovieReviewsAdapter() {
        mReviews = new ArrayList<>();
    }

    public Completable updateUI(List<ReviewUiModel> reviews) {
        return Completable.create(emitter -> {
            mReviews = reviews;
            notifyDataSetChanged();
            emitter.onComplete();
        });
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie_review, parent, false);

        return new ReviewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ReviewUiModel review = mReviews.get(position);

        holder.mAuthor.setText(review.getAuthor().trim());
        holder.mContent.setText(review.getContent().trim());

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.review_author)
        TextView mAuthor;
        @BindView(R.id.review_content)
        TextView mContent;

        public ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
