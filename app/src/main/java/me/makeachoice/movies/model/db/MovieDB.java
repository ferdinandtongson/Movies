package me.makeachoice.movies.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.db.contract.MovieContract;
import me.makeachoice.movies.model.db.contract.RefreshContract;
import me.makeachoice.movies.model.db.contract.TMDBContract;
import me.makeachoice.movies.model.item.RefreshItem;

/**
 * Created by Usuario on 5/8/2016.
 */
public class MovieDB extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movies.db";
    private Context mContext;

    public MovieDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        Log.d("Movies", "MovieDB - constructor");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("Movies", "MovieDB.onCreate - create tables!!!!!!!!!!!!!!!!");
        db.execSQL(RefreshContract.RefreshEntry.CREATE_TABLE);

        db.execSQL(MovieContract.MostPopularEntry.CREATE_TABLE);
        db.execSQL(MovieContract.TopRatedEntry.CREATE_TABLE);
        db.execSQL(MovieContract.NowPlayingEntry.CREATE_TABLE);
        db.execSQL(MovieContract.UpcomingEntry.CREATE_TABLE);
        db.execSQL(MovieContract.FavoriteEntry.CREATE_TABLE);

        db.execSQL(TMDBContract.MovieEntry.CREATE_TABLE);
        db.execSQL(TMDBContract.CastEntry.CREATE_TABLE);
        db.execSQL(TMDBContract.ReviewEntry.CREATE_TABLE);
        db.execSQL(TMDBContract.VideoEntry.CREATE_TABLE);

        initializeDB(db);
    }

    private void initializeDB(SQLiteDatabase db){
        Log.d("Movies", "     initializeDB");
        ArrayList<RefreshItem> refresh = new ArrayList<>();

        RefreshItem popularItem = new RefreshItem();
        popularItem.movieType = PosterHelper.NAME_ID_MOST_POPULAR;
        popularItem.dateRefresh = 0l;
        refresh.add(popularItem);

        RefreshItem topRatedItem = new RefreshItem();
        topRatedItem.movieType = PosterHelper.NAME_ID_TOP_RATED;
        topRatedItem.dateRefresh = 0l;
        refresh.add(topRatedItem);

        RefreshItem nowPlayingItem = new RefreshItem();
        nowPlayingItem.movieType = PosterHelper.NAME_ID_NOW_PLAYING;
        nowPlayingItem.dateRefresh = 0l;
        refresh.add(nowPlayingItem);

        RefreshItem upcomingItem = new RefreshItem();
        upcomingItem.movieType = PosterHelper.NAME_ID_UPCOMING;
        upcomingItem.dateRefresh = 0l;
        refresh.add(upcomingItem);

        ContentValues values = new ContentValues();

        int count = refresh.size();
        for(int i = 0; i < count; i++){
            RefreshItem item = refresh.get(i);
            values.put(RefreshContract.RefreshEntry.COLUMN_NAME_MOVIES_TYPE, item.movieType);
            values.put(RefreshContract.RefreshEntry.COLUMN_NAME_DATE_REFRESH, item.dateRefresh);
            db.insert(RefreshContract.RefreshEntry.TABLE_NAME, null, values);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
