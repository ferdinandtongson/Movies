package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.worker.DBWorker;
import me.makeachoice.movies.model.db.MovieDB;
import me.makeachoice.movies.model.db.contract.RefreshContract;
import me.makeachoice.movies.model.item.RefreshItem;
import me.makeachoice.movies.util.DateManager;

/**
 * RefreshButler takes care of all the database request.
 *
 * Variables from MyButler:
 *      Boss mBoss
 *      Boolean mWorking
 *      ArrayList<Integer> mRequestBuffer
 *
 * Abstract Methods from MyButler:
 *      abstract public Context getActivityContext()
 *      abstract public void workComplete(Boolean)
 */
public class RefreshButler extends MyButler implements DBWorker.Bridge {

/**************************************************************************************************/
/**
 * Class Variables:
 */
/**************************************************************************************************/

    //class variables

/**************************************************************************************************/

    private final static int DB_INIT = 0;
    private final static int DB_OPEN = 1;

    private int mRequest;
    private MovieDB mMovieDB;
    private RefreshContract mRefreshContract;

    private DBWorker mWorker;
    private SQLiteDatabase mDB;

/**************************************************************************************************/
/**
 * _templateButler - constructor, registers to Boss, initialize URI builder, get API key and
 * initialize data buffers, if any
 * @param boss - Boss class
 */
    public RefreshButler(Boss boss){
        //Application context
        mBoss = boss;

        //initialize access to db
        mMovieDB = new MovieDB(mBoss);

        mWorker = new DBWorker(this, mMovieDB);

        mRefreshContract = new RefreshContract();

        mRefreshMap = new HashMap<>();
        mRefreshStatus = new HashMap<>();

        //flag to check if work is being done in the background
        mWorking = false;

        //initialize buffers
        initBuffers();
    }

    public void requestDB(){
        Log.d("Movies", "RefreshButler.requestDB");
        if(!mWorking){
            Log.d("Movies", "     start worker");
            mRequest = DB_OPEN;
            mWorker.execute();
        }
    }

    public void initDB(){
        if(!mWorking){
            mRequest = DB_INIT;
            mWorker.execute();
        }
    }

/**
 * void initBuffers() - initialize buffers if any
 */
    private void initBuffers(){
        //initialize buffer to hold pending movie requests
        mRequestBuffer = new ArrayList<>();

        //initialize any other buffers here
        //initSomeOtherBuffer();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity context
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context
 * @return - activity context
 */
    public Context getActivityContext() {
        return mBoss.getActivityContext();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void workComplete(boolean) - called when AsyncTask has completed
 */
/**************************************************************************************************/
/**
 * void workComplete(Boolean) - called when an AsyncTask completes its' work
 * @param result - returns boolean result of task
 */
    public void workComplete(Boolean result) {
        //work has finished
        mWorking = false;

        //do something with data

    }

    public void databaseReady(SQLiteDatabase db){
        Log.d("Movies", "RefreshButler.databaseReady: " + db.toString());
        //work has finished
        mWorking = false;
        mDB = db;
        closeDatabase();
    }

    public void closeDatabase(){
        if(mDB.isOpen()){
            mDB.close();
        }
    }

    public boolean refreshList(String movieList){
        return mRefreshStatus.get(movieList);
    }

    private HashMap<String, Boolean> mRefreshStatus;
    private HashMap<String, RefreshItem> mRefreshMap;

    public boolean refreshPosters(int request){
        String movieList = mBoss.getString(request);
        Log.d("Movies", "RefreshButler.checkRefresh: " + movieList);
        //get refreshItem from HashMap buffer
        RefreshItem item = mRefreshMap.get(movieList);

        if(item == null){
            //no data in HashMap, check database for refresh data
            return checkRefreshInDB(movieList);
        }
        else{

            return true;
        }
    }

    private boolean checkRefreshInDB(String movieList){
        //create query to check database for refresh data
        String query = mRefreshContract.getQueryMovieRefresh(movieList);

        //get database
        mDB = mMovieDB.getReadableDatabase();

        //run query, get cursor
        Cursor cursor = mDB.rawQuery(query, null);

        if(cursor != null && cursor.getCount() != 0){
            //valid query, move to first item in cursor
            cursor.moveToFirst();

            //get refresh time
            Long refreshMillis = cursor
                    .getLong(RefreshContract.RefreshEntry.INDEX_DATE_REFRESH);

            return validRefreshDate(movieList, refreshMillis);

        }
        else{
            //TODO - need to handle case where movieList is invalid (??)
            //cursor equals null, return true;
            return true;
        }
    }

    private boolean validRefreshDate(String movieList, long refreshMillis){
        //get date of refresh time
        Date refreshDate = new Date(refreshMillis);
        //get current date
        Date currentDate = DateManager.currentDate();

        //check if current date is pass refresh date
        if(currentDate.after(refreshDate)){
            //need to refresh, create new refresh date
            refreshDate = DateManager.addDaysToDate(currentDate, 1);

            //create sql update string for database
            String updateRefresh = mRefreshContract.getUpdateMovieRefresh(movieList,
                    currentDate.getTime(), refreshDate.getTime());

            //execute sql, update refresh table with new data
            mDB.execSQL(updateRefresh);

            //close database
            closeDatabase();

            //add new refresh data to HashMap buffer
            addToRefreshMap(movieList, refreshDate.getTime());

            //update refresh status buffer
            mRefreshStatus.put(movieList, true);

            return true;
        }
        else{
            //no need to refresh database only need to update HashMap buffer
            addToRefreshMap(movieList, refreshMillis);

            //update refresh status buffer
            mRefreshStatus.put(movieList, false);
            return false;
        }

    }

    private void addToRefreshMap(String movieList, long refreshTime){
        RefreshItem item = new RefreshItem();
        item.movieList = movieList;
        item.dateRefresh = refreshTime;

        mRefreshMap.put(movieList, item);

    }
/**************************************************************************************************/

}
