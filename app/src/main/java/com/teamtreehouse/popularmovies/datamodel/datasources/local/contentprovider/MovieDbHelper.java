package com.teamtreehouse.popularmovies.datamodel.datasources.local.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MovieDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "moviesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;



    // Constructor
    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Enable foreign key support
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {


        final String CREATE_MOVIES_TABLE = "CREATE TABLE "  + MovieContract.Movie.TABLE_NAME +
                " (" +
                MovieContract.Movie.KEY_MOVIE_ID           + " TEXT PRIMARY KEY, " +
                MovieContract.Movie.COLUMN_TITLE           + " TEXT NOT NULL, " +
                MovieContract.Movie.COLUMN_POSTER_PATH     + " TEXT NOT NULL, " +
                MovieContract.Movie.COLUMN_RELEASE_DATE    + " TEXT NOT NULL, " +
                MovieContract.Movie.COLUMN_USER_RATING     + " TEXT NOT NULL, " +
                MovieContract.Movie.COLUMN_PLOT_SYNOPSIS   + " TEXT NOT NULL " +
                ");";

        Log.d(TAG, "onCreate: " + CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_MOVIES_TABLE);


        final String CREATE_REVIEWS_TABLE = "CREATE TABLE "  + MovieContract.Review.TABLE_NAME +
                " (" +
                MovieContract.Review.KEY_MOVIE_ID           + " TEXT NOT NULL, " +
                MovieContract.Review.COLUMN_AUTHOR          + " TEXT NOT NULL, " +
                MovieContract.Review.COLUMN_CONTENT         + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + MovieContract.Review.KEY_MOVIE_ID + ") " +
                "REFERENCES " + MovieContract.Movie.TABLE_NAME + "(" + MovieContract.Movie.KEY_MOVIE_ID + ")" +
                ");";
        Log.d(TAG, "onCreate: " + CREATE_REVIEWS_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);

        final String CREATE_TRAILERS_TABLE = "CREATE TABLE "  + MovieContract.Trailer.TABLE_NAME +
                " (" +
                MovieContract.Trailer.KEY_MOVIE_ID           + " TEXT NOT NULL, " +
                MovieContract.Trailer.COLUMN_VIDEO_KEY       + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + MovieContract.Trailer.KEY_MOVIE_ID + ") " +
                "REFERENCES " + MovieContract.Movie.TABLE_NAME + "(" + MovieContract.Movie.KEY_MOVIE_ID + ")" +
                ");";
        Log.d(TAG, "onCreate: " + CREATE_TRAILERS_TABLE);
        db.execSQL(CREATE_TRAILERS_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
