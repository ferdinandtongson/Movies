package me.makeachoice.movies.model.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 5/7/2016.
 */
public final class PosterContract extends MyContract{
    public static final int INDEX_ID = 0;
    public static final int INDEX_ORDER = 1;
    public static final int INDEX_MOVIE_ID = 2;
    public static final int INDEX_TITLE = 3;
    public static final int INDEX_POSTER_PATH = 4;
    public static final int INDEX_BITMAP = 5;

    public static final String COLUMN_NAME_ORDER = "movieOrder";
    public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    public static final String COLUMN_NAME_TITLE = "movieTitle";
    public static final String COLUMN_NAME_POSTER_PATH = "posterPath";
    public static final String COLUMN_NAME_BITMAP = "posterBitmap";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public PosterContract() {}



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
                COLUMN_NAME_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_BITMAP + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

}
