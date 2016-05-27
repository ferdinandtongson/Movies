package me.makeachoice.movies.model.db.contract;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * RefreshContract holds table and column name variables for refresh_movie table. This table is
 * used to determine whether the data of a particular movie list needs to be refreshed or not. If
 * it needs to be refreshed, the movies of a particular list will be downloaded from the internet
 * and stored in the database.
 */
public final class RefreshContract extends MyContract{
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public RefreshContract() {}

    public String getQueryMovieRefresh(int movieType){
        return RefreshEntry.SELECT_ALL + MyContract.WHERE +
                RefreshEntry.COLUMN_NAME_MOVIES_TYPE + " = " + movieType;
    }

    public String getUpdateMovieRefresh(int movieType, Long dateSaved, Long dateRefresh){
        return MyContract.UPDATE + RefreshEntry.TABLE_NAME +
                " SET " + RefreshEntry.COLUMN_NAME_DATE_REFRESH + " = " + dateRefresh +
                MyContract.WHERE +
                RefreshEntry.COLUMN_NAME_MOVIES_TYPE + " = " + movieType;
    }

    public ContentValues getContentValues(int movieType, Long refreshDate){
        ContentValues values = new ContentValues();
        values.put(RefreshContract.RefreshEntry.COLUMN_NAME_MOVIES_TYPE, movieType);
        values.put(RefreshContract.RefreshEntry.COLUMN_NAME_DATE_REFRESH, refreshDate);

        return values;
    }







    /* Inner class that defines the table contents */
    public static abstract class RefreshEntry implements BaseColumns {
        public static final int INDEX_ID = 0;
        public static final int INDEX_MOVIE_TYPE = 1;
        public static final int INDEX_DATE_REFRESH = 2;

        public static final String TABLE_NAME = "refresh_movies";
        public static final String COLUMN_NAME_MOVIES_TYPE = "movie_type";
        public static final String COLUMN_NAME_DATE_REFRESH = "date_refresh";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + RefreshEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_MOVIES_TYPE + MyContract.INTEGER_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_DATE_REFRESH + MyContract.INTEGER_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;

        public static final String SELECT_ALL = MyContract.SELECT_ALL + TABLE_NAME;
    }

}
