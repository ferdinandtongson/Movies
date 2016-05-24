package me.makeachoice.movies.model.db.contract;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import me.makeachoice.movies.model.item.MovieItem;

/**
 * MovieContract - is the database contract class for Movie tables
 */
public final class MovieContract extends MyContract{

/**************************************************************************************************/
/**
 * Static INDEX Variables:
 *      int INDEX_ID - column index of primary key
 *      int INDEX_ORDER - column index of movie order
 *      int INDEX_MOVIE_ID - column index of the id number of TheMovieDB
 *      int INDEX_TITLE - column index of the title of movie
 *      int INDEX_OVERVIEW - column index of the overview of the movie
 *      int INDEX_RELEASE_DATE - column index of the release date of the movie
 *      int INDEX_IMDB_ID - column index of the id number of InternetMovieDB
 *      int INDEX_HOME_PAGE - column index of the homepage of the movie
 *
 *      int INDEX_ORIGINAL_TITLE - column index of the original title of the movie
 *      int INDEX_ORIGINAL_LANGUAGE - column index of the original language of the movie
 *
 *      int INDEX_POPULARITY - column index of the popularity ranking of the movie
 *      int INDEX_VOTE_COUNT - column index of the number of votes the movie has
 *      int INDEX_VOTE_AVERAGE - column index of the average rating the movie has
 *
 *      int INDEX_POSTER_PATH - column index of the full url path to poster of the movie
 *      int INDEX_POSTER_BYTES - column index of the byte array of the poster image
 */
/**************************************************************************************************/

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
    public static final int INDEX_POSTER_BYTES = 14;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Static COLUMN_NAME Variables:
 *      int COLUMN_NAME_ORDER - column name for movie order
 *      int COLUMN_NAME_MOVIE_ID - column name for the id number of TheMovieDB
 *      int COLUMN_NAME_TITLE - column name for the title of movie
 *      int COLUMN_NAME_OVERVIEW - column name for the overview of the movie
 *      int COLUMN_NAME_RELEASE_DATE - column name for the release date of the movie
 *      int COLUMN_NAMEX_IMDB_ID - column name for the id number of InternetMovieDB
 *      int COLUMN_NAME_HOME_PAGE - column name for the homepage of the movie
 *
 *      int COLUMN_NAME_ORIGINAL_TITLE - column name for the original title of the movie
 *      int COLUMN_NAME_ORIGINAL_LANGUAGE - column name for the original language of the movie
 *
 *      int COLUMN_NAME_POPULARITY - column name for the popularity ranking of the movie
 *      int COLUMN_NAME_VOTE_COUNT - column name for the number of votes the movie has
 *      int COLUMN_NAME_VOTE_AVERAGE - column name for the average rating the movie has
 *
 *      int COLUMN_NAME_POSTER_PATH - column name for the full url path to poster of the movie
 *      int COLUMN_NAME_POSTER_BYTES - column name for the byte array of the poster image
 */
/**************************************************************************************************/

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
    public static final String COLUMN_NAME_POSTER_BYTES = "posterByte";

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieContract - construct
 */
    public MovieContract() {}

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      ContentValues getContentValues(MovieItem,int) - convert MovieItem data to ContentValues
 *      Bitmap getBitmapFromURL(String) - gets poster image from API call
 *      byte[] convertBitmap(Bitmap) - convert bitmap image into byte[]
 */
/**************************************************************************************************/
/**
 * ContentValues getContentValues(MovieItem,int) - convert MovieItem data to ContentValues. If
 * there is no byte[] data, will make an API call to download the bitmap poster and convert it
 * to a byte[] to store in the database
 * @param item - movie item data
 * @param index - order position of movie item in list
 * @return - ContentValues of movie item
 */
    public ContentValues getContentValues(MovieItem item, int index){
        //create ContentValues object
        ContentValues values = new ContentValues();

        //add movie item data to ContentValues
        values.put(MovieContract.COLUMN_NAME_MOVIE_ID, item.getTMDBId());
        values.put(MovieContract.COLUMN_NAME_ORDER, index);
        values.put(MovieContract.COLUMN_NAME_TITLE, item.getTitle());
        values.put(MovieContract.COLUMN_NAME_OVERVIEW, item.getOverview());
        values.put(MovieContract.COLUMN_NAME_RELEASE_DATE, item.getReleaseDate());
        values.put(MovieContract.COLUMN_NAME_IMBD_ID, item.getIMDBId());
        values.put(MovieContract.COLUMN_NAME_HOME_PAGE, item.getHomepage());

        values.put(MovieContract.COLUMN_NAME_ORIGINAL_TITLE, item.getOriginalTitle());
        values.put(MovieContract.COLUMN_NAME_ORIGINAL_LANGUAGE, item.getOriginalLanguage());

        values.put(MovieContract.COLUMN_NAME_POPULARITY, item.getPopularity());
        values.put(MovieContract.COLUMN_NAME_VOTE_COUNT, item.getVoteCount());
        values.put(MovieContract.COLUMN_NAME_VOTE_AVERAGE, item.getVoteAverage());

        values.put(MovieContract.COLUMN_NAME_POSTER_PATH, item.getPosterPath());

        //check if movie item has a byte[] data
        if(item.getPosterBytes() != null){
            //has byte[] data, save to ContentValues
            values.put(MovieContract.COLUMN_NAME_POSTER_BYTES, item.getPosterBytes());
        }
        else{
            //no byte[] data, get bitmap poster from internet
            Bitmap poster = getBitmapFromURL(item.getPosterPath());

            //check if poster is null
            if(poster != null){
                //not null, save bitmap poster to movie item
                item.setPoster(poster);

                //convert bitmap to byte[] data
                byte[] bytePoster = convertBitmap(poster);

                //save byte[] to movie item
                item.setPosterBytes(bytePoster);

                //save byte[] to ContentValues
                values.put(MovieContract.COLUMN_NAME_POSTER_BYTES, bytePoster);
            }
        }

        //return ContentValues
        return values;
    }

/**
 * byte[] getBitmapFromURL(PosterItem) - gets poster image from API call. Uses the poster path
 * url to download the poster image.
 * @param posterPath - url poster path to poster image
 * @return - bitmap of poster image
 */
    private Bitmap getBitmapFromURL(String posterPath){
        try {
            //create url object with poster path string (complete API call to TMDB)
            java.net.URL url = new java.net.URL(posterPath);

            //open connection to url
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //allow for DoInput
            connection.setDoInput(true);

            //attempt url connection
            connection.connect();

            //get InputStream from url connection
            InputStream input = connection.getInputStream();

            //convert InputStream into bitmap
            return BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            //IOException event happened
            e.printStackTrace();
            return null;
        }
    }

/**
 * byte[] convertBitmap(Bitmap) - convert bitmap image into byte[].
 * @param image - bitmap image to convert into byte[]
 * @return - byte[] of image
 */
    private byte[] convertBitmap(Bitmap image){
        //create byt[] array output stream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //compress bitmap image into byte[]
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);

        //return byte[]
        return bos.toByteArray();
    }


/**************************************************************************************************/


/**************************************************************************************************/
/**
 * MostPopularEntry - table implementation for Most Popular movies
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
                COLUMN_NAME_POSTER_BYTES + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * TopRatedEntry - table implementation for Top Ratedd movies
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
                COLUMN_NAME_POSTER_BYTES + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NowPlayingEntry - table implementation for Now Playing movies
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
                COLUMN_NAME_POSTER_BYTES + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * UpcomingEntry - table implementation for Upcoming movies
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
                COLUMN_NAME_POSTER_BYTES + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * FavoriteEntry - table implementation for Favorite movies
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
                COLUMN_NAME_POSTER_BYTES + MyContract.BLOB_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

/**************************************************************************************************/

}
