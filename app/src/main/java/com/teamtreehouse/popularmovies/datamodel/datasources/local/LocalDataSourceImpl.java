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
import io.reactivex.Single;

public class LocalDataSourceImpl implements LocalDataSource {

    private static final String TAG = "LocalDataSourceImpl";

    private final ContentResolver mContentResolver;

    @Inject
    public LocalDataSourceImpl(ContentResolver resolver) {
        mContentResolver = resolver;
    }

    @Override
    public Single<List<MovieModel>> getFavoriteMovies() {
        return Single.create(emitter -> {
            try {

                Cursor cursor = queryFavoriteMovies();

                if (cursor.getCount() == 0) {
                    emitter.onSuccess(new ArrayList<>());
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

                    emitter.onSuccess(movies);
                    cursor.close();
                }

            } catch (Exception e) {
                emitter.onError(e);
            }
        });
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
        return Single.create(emitter -> {
            try {
                Cursor cursor = queryMovie(movieId);
                Log.d(TAG, "getMovie() - cursor: " + cursor.getCount());
                if (cursor.getCount() == 0) {
                    emitter.onError(new Throwable("ERROR: " + movieId + " not found"));
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
                    Log.d(TAG, "subscribeActual: getMovie - movieModel " + (movieModel == null));
                    emitter.onSuccess(movieModel);
                    cursor.close();
                }

            } catch (Exception e) {
                emitter.onError(e);
            }
        });

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
        String selection = MovieContract.Movie.KEY_MOVIE_ID + " = " + movieId;

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

        return Single.create(emitter -> {
            try {

                Cursor cursor = queryReviews(movieId);

                Log.d(TAG, "getReviews() - cursor: " + cursor.getCount());

                if (cursor.getCount() == 0) {
                    emitter.onSuccess(new ArrayList<>());
                } else {

                    List<ReviewModel> reviewModels = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        reviewModels.add(new ReviewModel(
                                cursor.getString(cursor.getColumnIndex(MovieContract.Review.KEY_MOVIE_ID)),
                                cursor.getString(cursor.getColumnIndex(MovieContract.Review.COLUMN_AUTHOR)),
                                cursor.getString(cursor.getColumnIndex(MovieContract.Review.COLUMN_CONTENT))
                        ));
                    }

                    Log.d(TAG, "getReviews() - author: " + (reviewModels == null));
                    emitter.onSuccess(reviewModels);
                    cursor.close();
                }

            } catch (Exception e) {
                emitter.onError(e);
            }
        });

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
        String selection = MovieContract.Review.KEY_MOVIE_ID + " = " + movieId;

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

        return Single.create(emitter -> {
            try {
                Cursor cursor = queryTrailers(movieId);
                Log.d(TAG, "getTrailers() - cursor: " + cursor.getCount());

                if (cursor.getCount() == 0) {
                    emitter.onSuccess(new ArrayList<>());
                } else {

                    List<TrailerModel> trailerModels = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        trailerModels.add(new TrailerModel(
                                cursor.getString(cursor.getColumnIndex(MovieContract.Trailer.KEY_MOVIE_ID)),
                                cursor.getString(cursor.getColumnIndex(MovieContract.Trailer.COLUMN_VIDEO_KEY))
                        ));
                    }
                    Log.d(TAG, "getTrailers() - videoKey: " + (trailerModels == null));
                    emitter.onSuccess(trailerModels);
                    cursor.close();
                }

            } catch (Exception e) {
                emitter.onError(e);

            }
        });
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
        String selection = MovieContract.Trailer.KEY_MOVIE_ID + " = " + movieId;

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

        return Completable.create(emitter -> {
            try {
                saveMovieToDb(movie);
                saveReviewsToDb(reviews);
                saveTrailersToDb(trailers);
                Log.d(TAG, "subscribeActual: addToFavorites() - completed");
                emitter.onComplete();
            } catch (SQLException e) {
                Log.e(TAG, "subscribeActual: addToFavorites()", e);
                emitter.onError(e);
            } catch (Exception e) {
                Log.e(TAG, "subscribeActual: addToFavorites()", e);
                emitter.onError(e);
            }
        });
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

    @Override
    public Single<Boolean> isFavorite(String movieId) {

        return Single.create(emitter -> {
            Cursor cursor = queryMovie(movieId);
            emitter.onSuccess(cursor.getCount() > 0);
            cursor.close();
        });
    }

    @Override
    public Completable removeFromFavorites(String movieId) {
        return Completable.create(emitter -> {
            try {
                int rowsCount = mContentResolver.delete(
                        MovieContract.Movie.MOVIES_URI,
                        MovieContract.Movie.KEY_MOVIE_ID + " = " + movieId,
                        null);

                if (rowsCount != 0) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable("ERROR: not able to find movieId"));
                }

            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
