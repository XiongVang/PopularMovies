package com.teamtreehouse.popularmovies.datamodel.datasources.local.contentprovider;

/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    // Immutable
    private MovieContract(){}


    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.teamtreehouse.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "movies";

    public static final String PATH_REVIEWS = "reviews";

    public static final String PATH_TRAILERS = "trailers";

    /* Inner class that defines the contents of the movie table */
    public static final class Movie implements BaseColumns {

        // Movie content URI = base content URI + path
        public static final Uri MOVIES_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        // Movie table and column names
        public static final String TABLE_NAME = "movies";

        public static final String KEY_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_PLOT_SYNOPSIS = "plot_synopsis";

    }

    public static final class Trailer implements BaseColumns {

        // Trailer content URI = base content URI + path
        public static final Uri TRAILERS_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();


        // Movie table and column names
        public static final String TABLE_NAME = "trailers";
        public static final String KEY_MOVIE_ID = "movie_id";
        public static final String COLUMN_VIDEO_KEY = "video_key";

    }

    public static final class Review implements BaseColumns {

        // Review content URI = base content URI + path
        public static final Uri REVIEWS_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();


        // Movie table and column names
        public static final String TABLE_NAME = "reviews";
        public static final String KEY_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";


    }
}
