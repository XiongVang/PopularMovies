package com.teamtreehouse.popularmovies.datamodel.datasources.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
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

        return new Single<List<MovieModel>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<MovieModel>> observer) {

                try {

                    Cursor cursor = queryFavoriteMovies();

                    if (cursor == null) {
                        observer.onSuccess(new ArrayList<MovieModel>());
                    } else {

                        List<MovieModel> movies = new ArrayList<>();

                        while (cursor.moveToNext()) {
                            Log.d(TAG, "subscribeActual: " + cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_TITLE)));

                            movies.add(new MovieModel(
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.KEY_MOVIE_ID)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_TITLE)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_POSTER_PATH)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_RELEASE_DATE)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_USER_RATING)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_PLOT_SYNOPSIS)))
                            );
                        }

                        observer.onSuccess(movies);
                        cursor.close();
                    }

                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    private Cursor queryFavoriteMovies() {

        // uri - The URI, using the content:// scheme, for the content to retrieve.
        Uri uri = MovieContract.Movie.MOVIES_URI;

        // projection - A list of which columns to return. Passing null will
        // return all columns, which is inefficient.
        String[] projection = null;

        // selection - A filter declaring which rows to return, formatted as an
        // SQL WHERE clause (excluding the WHERE itself). Passing null will
        // return all rows for the given URI.
        String selection = null;

        // selectionArgs- You may include ?s in selection, which will be
        // replaced by the values from selectionArgs, in the order that they
        // appear in the selection. The values will be bound as Strings.
        String selectionArgs[] = null;

        // sortOrder How to order the rows, formatted as an SQL ORDER BY
        // clause (excluding the ORDER BY itself). Passing null will use the
        // default sort order, which may be unordered.
        String sortOrder = null;

        return mContentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Single<MovieModel> getMovie(String movieId) {
        return new Single<MovieModel>() {
            @Override
            protected void subscribeActual(SingleObserver<? super MovieModel> observer) {
                try {
                    Cursor cursor = queryMovie(movieId);

                    if (cursor == null) {
                        observer.onSuccess(null);
                    } else {

                        MovieModel movieModel = null;

                        while (cursor.moveToNext()) {
                            movieModel = new MovieModel(
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.KEY_MOVIE_ID)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_TITLE)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_POSTER_PATH)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_RELEASE_DATE)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_USER_RATING)),
                                    cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.Movie.COLUMN_PLOT_SYNOPSIS)));
                        }
                        cursor.close();
                        observer.onSuccess(movieModel);
                    }

                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    private Cursor queryMovie(String movieId) {

        // uri - The URI, using the content:// scheme, for the content to retrieve.
        Uri uri = MovieContract.Movie.MOVIES_URI;

        // projection - A list of which columns to return. Passing null will
        // return all columns, which is inefficient.
        String[] projection = null;

        // selection - A filter declaring which rows to return, formatted as an
        // SQL WHERE clause (excluding the WHERE itself). Passing null will
        // return all rows for the given URI.
        String selection = "'" + MovieContract.Movie.KEY_MOVIE_ID + " = " + movieId + "'";

        // selectionArgs- You may include ?s in selection, which will be
        // replaced by the values from selectionArgs, in the order that they
        // appear in the selection. The values will be bound as Strings.
        String selectionArgs[] = null;

        // sortOrder How to order the rows, formatted as an SQL ORDER BY
        // clause (excluding the ORDER BY itself). Passing null will use the
        // default sort order, which may be unordered.
        String sortOrder = null;

        return mContentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Single<List<ReviewModel>> getReviews(String movieId) {
        return new Single<List<ReviewModel>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<ReviewModel>> observer) {
                try {
                    Cursor cursor = queryReviews(movieId);

                    if (cursor == null) {
                        observer.onSuccess(new ArrayList<ReviewModel>());
                    } else {

                        List<ReviewModel> reviewModels = new ArrayList<>();

                        while (cursor.moveToNext()) {
                            reviewModels.add(new ReviewModel(
                                    cursor.getString(cursor.getColumnIndex(MovieContract.Review.KEY_MOVIE_ID)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.Review.COLUMN_AUTHOR)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.Review.COLUMN_CONTENT))
                            ));
                        }
                        cursor.close();
                        observer.onSuccess(reviewModels);
                    }

                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    private Cursor queryReviews(String movieId) {

        // uri - The URI, using the content:// scheme, for the content to retrieve.
        Uri uri = MovieContract.Review.REVIEWS_URI;

        // projection - A list of which columns to return. Passing null will
        // return all columns, which is inefficient.
        String[] projection = null;

        // selection - A filter declaring which rows to return, formatted as an
        // SQL WHERE clause (excluding the WHERE itself). Passing null will
        // return all rows for the given URI.
        String selection = "'" + MovieContract.Review.KEY_MOVIE_ID + " = " + movieId + "'";

        // selectionArgs- You may include ?s in selection, which will be
        // replaced by the values from selectionArgs, in the order that they
        // appear in the selection. The values will be bound as Strings.
        String selectionArgs[] = null;

        // sortOrder How to order the rows, formatted as an SQL ORDER BY
        // clause (excluding the ORDER BY itself). Passing null will use the
        // default sort order, which may be unordered.
        String sortOrder = null;

        return mContentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Single<List<TrailerModel>> getTrailers(String movieId) {

        return new Single<List<TrailerModel>>() {
            @Override
            protected void subscribeActual(SingleObserver<? super List<TrailerModel>> observer) {
                try {
                    Cursor cursor = queryTrailers(movieId);

                    if (cursor == null) {
                        observer.onError(new Throwable("ERROR: " + "ContentResolver.query()"));
                    } else {

                        List<TrailerModel> trailerModels = new ArrayList<>();

                        while (cursor.moveToNext()) {
                            trailerModels.add(new TrailerModel(
                                    cursor.getString(cursor.getColumnIndex(MovieContract.Trailer.KEY_MOVIE_ID)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.Trailer.COLUMN_VIDEO_KEY))
                            ));
                        }
                        cursor.close();
                        observer.onSuccess(trailerModels);
                    }

                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }

    private Cursor queryTrailers(String movieId) {

        // uri - The URI, using the content:// scheme, for the content to retrieve.
        Uri uri = MovieContract.Trailer.TRAILERS_URI;

        // projection - A list of which columns to return. Passing null will
        // return all columns, which is inefficient.
        String[] projection = null;

        // selection - A filter declaring which rows to return, formatted as an
        // SQL WHERE clause (excluding the WHERE itself). Passing null will
        // return all rows for the given URI.
        String selection = "'" + MovieContract.Trailer.KEY_MOVIE_ID + " = " + movieId + "'";

        // selectionArgs- You may include ?s in selection, which will be
        // replaced by the values from selectionArgs, in the order that they
        // appear in the selection. The values will be bound as Strings.
        String selectionArgs[] = null;

        // sortOrder How to order the rows, formatted as an SQL ORDER BY
        // clause (excluding the ORDER BY itself). Passing null will use the
        // default sort order, which may be unordered.
        String sortOrder = null;

        return mContentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
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
                    Log.e(TAG, "subscribeActual: addToFavorites()", e);
                    s.onError(e);
                } catch (Exception e) {
                    Log.e(TAG, "subscribeActual: addToFavorites()", e);
                    s.onError(e);
                }
            }
        };
    }

    private void saveMovieToDb(MovieModel movie) {

        ContentValues movieContent = new ContentValues();

        movieContent.put(MovieContract.Movie.KEY_MOVIE_ID, movie.getMovieId());
        movieContent.put(MovieContract.Movie.COLUMN_TITLE, movie.getTitle());
        movieContent.put(MovieContract.Movie.COLUMN_POSTER_PATH, movie.getPosterPath());
        movieContent.put(MovieContract.Movie.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieContent.put(MovieContract.Movie.COLUMN_USER_RATING, movie.getUserRating());
        movieContent.put(MovieContract.Movie.COLUMN_PLOT_SYNOPSIS, movie.getPlotSynopsis());

        mContentResolver.insert(MovieContract.Movie.MOVIES_URI, movieContent);
    }

    private void saveReviewsToDb(List<ReviewModel> reviews) {


        for (ReviewModel review : reviews) {

            ContentValues reviewContent = new ContentValues();

            reviewContent.put(MovieContract.Review.KEY_MOVIE_ID, review.getMovieId());
            reviewContent.put(MovieContract.Review.COLUMN_AUTHOR, review.getAuthor());
            reviewContent.put(MovieContract.Review.COLUMN_CONTENT, review.getContent());

            mContentResolver.insert(MovieContract.Review.REVIEWS_URI, reviewContent);
        }


    }

    private void saveTrailersToDb(List<TrailerModel> trailers) {

        for (TrailerModel trailer : trailers) {

            ContentValues trailerContent = new ContentValues();

            trailerContent.put(MovieContract.Trailer.KEY_MOVIE_ID, trailer.getMovieId());
            trailerContent.put(MovieContract.Trailer.COLUMN_VIDEO_KEY, trailer.getVideoKey());

            mContentResolver.insert(MovieContract.Trailer.TRAILERS_URI, trailerContent);
        }
    }
}
