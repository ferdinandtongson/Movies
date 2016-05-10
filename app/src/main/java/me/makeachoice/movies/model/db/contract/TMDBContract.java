package me.makeachoice.movies.model.db.contract;

import android.provider.BaseColumns;

/**
 * TheMovieDB sqlite Contract class. A companion class which explicitly specifies the layout of
 * the table schema related to TheMovieDB data.
 *
 * Table Entries:
 *      GenreEntry - movie genres
 *      CastEntry - cast members of movies
 *      CrewEntry - crew members of movies
 *      VideoEntry - videos of movies
 *      ReviewEntry - reviews of movies
 */
public class TMDBContract extends MyContract{

    public TMDBContract(){}

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * GenreEntry - types of movie genres TheMovieDB uses
 */
/**************************************************************************************************/

    public static abstract class GenreEntry implements BaseColumns {
        public static final String TABLE_NAME = "MovieGenre";
        public static final String COLUMN_GENRE_ID = "genreId";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + GenreEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_GENRE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME + MyContract.TEXT_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

/**************************************************************************************************/
/**
 * CastEntry - movie characters and the actors who played them, movie defined by movieId
 */
/**************************************************************************************************/

    public static abstract class CastEntry implements BaseColumns{
        public static final String TABLE_NAME = "MovieCast";
        public static final String COLUMN_CAST_ID = "castId";
        public static final String COLUMN_CHARACTER = "character";
        public static final String COLUMN_CREDIT_ID = "creditId";
        public static final String COLUMN_ACTOR_ID = "actorId";
        public static final String COLUMN_ACTOR_NAME = "actorName";
        public static final String COLUMN_ORDER = "castOrder";
        public static final String COLUMN_ACTOR_PROFILE_PATH = "profilePath";
        public static final String COLUMN_MOVIE_ID = "movieId";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + CastEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_CAST_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_CHARACTER + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_CREDIT_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_ACTOR_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_ACTOR_NAME + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_ORDER + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_ACTOR_PROFILE_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

/**************************************************************************************************/
/**
 * CrewEntry - crew members and their jobs in a particular movie, movie defined by movieId
 */
/**************************************************************************************************/

    public static abstract class CrewEntry implements BaseColumns{
        public static final String TABLE_NAME = "MovieCrew";
        public static final String COLUMN_CREDIT_ID = "creditId";
        public static final String COLUMN_DEPARTMENT = "department";
        public static final String COLUMN_CREW_ID = "crewId";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_CREW_NAME = "crewName";
        public static final String COLUMN_CREW_PROFILE_PATH = "profilePath";
        public static final String COLUMN_MOVIE_ID = "movieId";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + CrewEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_CREDIT_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_DEPARTMENT + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_CREW_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_JOB + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_CREW_NAME + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_CREW_PROFILE_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

/**************************************************************************************************/
/**
 * VideoEntry - movie characters and the actors who played them, movie defined by movieId
 */
/**************************************************************************************************/

    public static abstract class VideoEntry implements BaseColumns{
        public static final String TABLE_NAME = "MovieVideo";
        public static final String COLUMN_VIDEO_ID = "videoId";
        public static final String COLUMN_VIDEO_KEY = "videoKey";
        public static final String COLUMN_VIDEO_PATH = "videoPath";
        public static final String COLUMN_VIDEO_IMAGE = "videoImage";
        public static final String COLUMN_VIDEO_NAME = "videoName";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_VIDEO_SIZE = "videoSize";
        public static final String COLUMN_VIDEO_TYPE = "videoType";
        public static final String COLUMN_MOVIE_ID = "movieId";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + VideoEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_VIDEO_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_VIDEO_KEY + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_VIDEO_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_VIDEO_IMAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_VIDEO_NAME + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_SITE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_VIDEO_SIZE + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_VIDEO_TYPE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

/**************************************************************************************************/
/**
 * ReviewEntry - movie reviews found online, movie defined by movieId
 */
/**************************************************************************************************/

    public static abstract class ReviewEntry implements BaseColumns{
        public static final String TABLE_NAME = "MovieReview";
        public static final String COLUMN_REVIEW_ID = "reviewId";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_MOVIE_ID = "movieId";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + ReviewEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_REVIEW_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_AUTHOR + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_CONTENT + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_URL + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

/**************************************************************************************************/
/**
 * MovieEntry - movie details
 */
/**************************************************************************************************/

    public static abstract class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "MovieTMDB";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_IMBD_ID = "imbdId";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_BACKDROP_PATH = "backdropPath";
        public static final String COLUMN_HAS_VIDEO = "video";
        public static final String COLUMN_HOMEPAGE = "homepage";
        public static final String COLUMN_ADULT = "adult";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + MovieEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_MOVIE_ID + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_OVERVIEW + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_RELEASE_DATE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_IMBD_ID + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_ORIGINAL_TITLE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_ORIGINAL_LANGUAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_POPULARITY + MyContract.NUMERIC_TYPE + MyContract.COMMA_SEP +
                COLUMN_VOTE_COUNT + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_VOTE_AVERAGE + MyContract.NUMERIC_TYPE + MyContract.COMMA_SEP +
                COLUMN_POSTER_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_BACKDROP_PATH + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_HAS_VIDEO + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_HOMEPAGE + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_ADULT + MyContract.TEXT_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

/**************************************************************************************************/

}
