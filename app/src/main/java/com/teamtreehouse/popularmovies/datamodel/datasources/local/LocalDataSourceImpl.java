package com.teamtreehouse.popularmovies.datamodel.datasources.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.teamtreehouse.popularmovies.datamodel.datasources.local.contentprovider.MovieContract;
import com.teamtreehouse.popularmovies.datamodel.models.MovieModel;
import com.teamtreehouse.popularmovies.datamodel.models.ReviewModel;
import com.teamtreehouse.popularmovies.datamodel.models.TrailerModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

import static android.content.ContentValues.TAG;

public class LocalDataSourceImpl implements LocalDataSource {

    private final ContentResolver mContentResolver;

    @Inject
    public LocalDataSourceImpl(ContentResolver resolver) {
        mContentResolver = resolver;
    }

    @Override
    public Single<List<MovieModel>> getFavoriteMovies() {

        return new Single<List<MovieModel>>(){
            @Override
            protected void subscribeActual(SingleObserver<? super List<MovieModel>> observer) {
                Log.d(TAG, "subscribeActual: getFavoriteMovies()");
                Cursor cursor = mContentResolver.query(MovieContract.MovieEntry.MOVIES_URI,null,null,null,null,null);

                if(cursor == null) {
                    observer.onError(new Throwable("ERROR: " + "ContentResolver.query()"));
                } else {

                    List<MovieModel> movies = new ArrayList<>();

                    while(cursor.moveToNext()){
                        Log.d(TAG, "subscribeActual: " + cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_TITLE)));

                        movies.add(new MovieModel(
                                cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.KEY_MOVIE_ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_TITLE)),
                                cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_POSTER_PATH)),
                                cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)),
                                cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_USER_RATING)),
                                cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_PLOT_SYNOPSIS))
                                ));
                    }
                    cursor.close();
                    observer.onSuccess(movies);
                }

            }
        };
    }

    @Override
    public Single<List<ReviewModel>> getReviews(String movieId) {
        // TODO
         return null;
    }

    @Override
    public Single<List<TrailerModel>> getTrailers(String movieId) {

        // TODO
        return null;
    }

    @Override
    public Completable addToFavorites(MovieModel movie, List<ReviewModel> reviews, List<TrailerModel> trailers) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                try {
                    saveMovieToDb(movie);
                    saveReviewsToDb(reviews);
                    saveTrailersToDb(trailers);

                    Log.d(TAG, "subscribeActual: addToFavorites() - completed");
                    s.onComplete();
                } catch (SQLException e) {
                    Log.e(TAG, "subscribeActual: addToFavorites()",e );
                    s.onError(e);
                } catch (Exception e){
                    Log.e(TAG, "subscribeActual: addToFavorites()", e);
                    s.onError(e);
                }
            }
        };
    }

    private void saveMovieToDb(MovieModel movie) {

        ContentValues movieContent = new ContentValues();

        movieContent.put(MovieContract.MovieEntry.KEY_MOVIE_ID, movie.getMovieId());
        movieContent.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        movieContent.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        movieContent.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieContent.put(MovieContract.MovieEntry.COLUMN_USER_RATING, movie.getUserRating());
        movieContent.put(MovieContract.MovieEntry.COLUMN_PLOT_SYNOPSIS, movie.getPlotSynopsis());

        mContentResolver.insert(MovieContract.MovieEntry.MOVIES_URI, movieContent);
    }

    private void saveReviewsToDb(List<ReviewModel> reviews) {


        for (ReviewModel review : reviews) {

            ContentValues reviewContent = new ContentValues();

            reviewContent.put(MovieContract.ReviewEntry.KEY_MOVIE_ID, review.getMovieId());
            reviewContent.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
            reviewContent.put(MovieContract.ReviewEntry.COLUMN_CONTENT, review.getContent());

            mContentResolver.insert(MovieContract.ReviewEntry.REVIEWS_URI, reviewContent);
        }


    }

    private void saveTrailersToDb(List<TrailerModel> trailers) {

        for (TrailerModel trailer : trailers) {

            ContentValues trailerContent = new ContentValues();

            trailerContent.put(MovieContract.TrailerEntry.KEY_MOVIE_ID, trailer.getMovieId());
            trailerContent.put(MovieContract.TrailerEntry.COLUMN_VIDEO_KEY, trailer.getVideoKey());

            mContentResolver.insert(MovieContract.TrailerEntry.TRAILERS_URI, trailerContent);
        }
    }
}
