package me.makeachoice.movies.model.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 5/7/2016.
 */
public final class MovieContract extends MyContract{
    public static final int INDEX_ID = 0;
    public static final int INDEX_ORDER = 1;
    public static final int INDEX_MOVIE_ID = 2;
    public static final int INDEX_TITLE = 3;
    public static final int INDEX_OVERVIEW = 4;
    public static final int INDEX_RELEASE_DATE = 5;
    public static final int INDEX_IMDB_ID = 6;
    public static final int INDEX_HOME_PAGE = 7;

    public static final int INDEX_ORIGINAL_TITLE = 8;
    public static final int INDEX_ORIGINAL_LANGUAGE = 9;

    public static final int INDEX_POPULARITY = 10;
    public static final int INDEX_VOTE_COUNT = 11;
    public static final int INDEX_VOTE_AVERAGE = 12;

    public static final int INDEX_POSTER_PATH = 13;
    public static final int INDEX_BITMAP = 14;

    public static final String COLUMN_NAME_ORDER = "movieOrder";
    public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    public static final String COLUMN_NAME_TITLE = "movieTitle";
    public static final String COLUMN_NAME_OVERVIEW = "overview";
    public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
    public static final String COLUMN_NAME_IMBD_ID = "imbdId";
    public static final String COLUMN_NAME_HOME_PAGE = "homePage";

    public static final String COLUMN_NAME_ORIGINAL_TITLE = "originalTitle";
    public static final String COLUMN_NAME_ORIGINAL_LANGUAGE = "originalLanguage";

    public static final String COLUMN_NAME_POPULARITY = "popularity";
    public static final String COLUMN_NAME_VOTE_COUNT = "voteCount";
    public static final String COLUMN_NAME_VOTE_AVERAGE = "voteAverage";

    public static final String COLUMN_NAME_POSTER_PATH = "posterPath";
    public static final String COLUMN_NAME_BITMAP = "posterBitmap";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MovieContract() {}



/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MostPopularEntry - list of most popular movies
 */
/**************************************************************************************************/

    public static abstract class MostPopularEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblMostPopular";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + MostPopularEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_ORDER + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_OVERVIEW + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_RELEASE_DATE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_IMBD_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_HOME_PAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_LANGUAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POPULARITY + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_COUNT + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_AVERAGE + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_BITMAP + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * TopRatedEntry - list of top rated movies
 */
/**************************************************************************************************/

    public static abstract class TopRatedEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblTopRated";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + TopRatedEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_ORDER + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_OVERVIEW + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_RELEASE_DATE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_IMBD_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_HOME_PAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_LANGUAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POPULARITY + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_COUNT + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_AVERAGE + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_BITMAP + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NowPlayingEntry - list of movies now playing
 */
/**************************************************************************************************/

    public static abstract class NowPlayingEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblNowPlaying";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + NowPlayingEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_ORDER + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_OVERVIEW + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_RELEASE_DATE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_IMBD_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_HOME_PAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_LANGUAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POPULARITY + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_COUNT + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_AVERAGE + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_BITMAP + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * UpcomingEntry - list of movies now playing
 */
/**************************************************************************************************/

    public static abstract class UpcomingEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblUpcoming";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + UpcomingEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_ORDER + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_OVERVIEW + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_RELEASE_DATE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_IMBD_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_HOME_PAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_LANGUAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POPULARITY + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_COUNT + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_AVERAGE + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_BITMAP + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * FavoriteEntry - list of favorite movies
 */
/**************************************************************************************************/

    public static abstract class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblFavorite";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + FavoriteEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_ORDER + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_OVERVIEW + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_RELEASE_DATE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_IMBD_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_HOME_PAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_ORIGINAL_LANGUAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POPULARITY + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_COUNT + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_VOTE_AVERAGE + MyContract.REAL_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_BITMAP + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

}
