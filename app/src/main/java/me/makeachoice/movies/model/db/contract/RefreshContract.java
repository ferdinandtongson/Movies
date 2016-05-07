package me.makeachoice.movies.model.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 5/7/2016.
 */
public final class RefreshContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public RefreshContract() {}

    /* Inner class that defines the table contents */
    public static abstract class RefreshEntry implements BaseColumns {
        public static final String TABLE_NAME = "refresh_movies";
        public static final String COLUMN_NAME_MOVIES_LIST = "movie_list";
        public static final String COLUMN_NAME_DATE_SAVED = "date_saved";
        public static final String COLUMN_NAME_DATE_REFRESH = "date_refresh";
    }

}
