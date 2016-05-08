package me.makeachoice.movies.model.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 5/7/2016.
 */
public final class RefreshContract extends MyContract{
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public RefreshContract() {}

    /* Inner class that defines the table contents */
    public static abstract class RefreshEntry implements BaseColumns {
        public static final String TABLE_NAME = "refresh_movies";
        public static final String COLUMN_NAME_MOVIES_LIST = "movie_list";
        public static final String COLUMN_NAME_DATE_SAVED = "date_saved";
        public static final String COLUMN_NAME_DATE_REFRESH = "date_refresh";

        public static final String CREATE_TABLE = MyContract.CREATE_TABLE + TABLE_NAME +
                MyContract.PAREN_OPEN + RefreshEntry._ID + MyContract.PRIMARY_KEY +
                COLUMN_NAME_MOVIES_LIST + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_DATE_SAVED + MyContract.TEXT_TYPE + MyContract.COMMA_SEP +
                COLUMN_NAME_DATE_REFRESH + MyContract.TEXT_TYPE + MyContract.PAREN_CLOSE;

        public static final String DROP_TABLE = MyContract.DROP_TABLE + TABLE_NAME;
    }

}
