package me.makeachoice.movies.model.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 5/7/2016.
 */
public final class MovieListContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MovieListContract() {}

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MostPopularEntry - list of most popular movies
 */
/**************************************************************************************************/

    public static abstract class MostPopularEntry implements BaseColumns {
        public static final String TABLE_NAME = "mostPopular";
        public static final String COLUMN_NAME_ORDER = "movieOrder";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * TopRatedEntry - list of top rated movies
 */
/**************************************************************************************************/

    public static abstract class TopRatedEntry implements BaseColumns {
        public static final String TABLE_NAME = "topRated";
        public static final String COLUMN_NAME_ORDER = "movieOrder";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NowPlayingEntry - list of movies now playing
 */
/**************************************************************************************************/

    public static abstract class NowPlayingEntry implements BaseColumns {
        public static final String TABLE_NAME = "nowPlaying";
        public static final String COLUMN_NAME_ORDER = "movieOrder";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * UpcomingEntry - list of movies now playing
 */
/**************************************************************************************************/

    public static abstract class UpcomingEntry implements BaseColumns {
        public static final String TABLE_NAME = "upcoming";
        public static final String COLUMN_NAME_ORDER = "movieOrder";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * FavoriteEntry - list of favorite movies
 */
/**************************************************************************************************/

    public static abstract class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_ORDER = "movieOrder";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    }

/**************************************************************************************************/

}
