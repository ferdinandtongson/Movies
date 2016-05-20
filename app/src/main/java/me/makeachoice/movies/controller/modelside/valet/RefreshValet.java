package me.makeachoice.movies.controller.modelside.valet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

import me.makeachoice.movies.model.db.contract.RefreshContract;
import me.makeachoice.movies.model.item.RefreshItem;

/**
 * RefreshValet takes care of all the database request to the movie refresh table.
 *
 * It uses other classes to assist in maintaining poster data in the database:
 *      Bridge mBridge - Bridge communication interface
 *      RefreshContract - database contract class for refresh table
 *
 */
public class RefreshValet{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Bridge mBridge - Bridge communication interface
 *      RefreshContract mContract - contract class for the movie refresh table
 */
/**************************************************************************************************/

    //mBridge - Bridge communication interface
    Bridge mBridge;

    //mContract - contract class for the movie refresh table
    private RefreshContract mContract;

    //Implemented communication line to a Valet class
    public interface Bridge{
        //get Database from Bridge
        SQLiteDatabase getDatabase();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * RefreshValet - constructor, save bridge communication and create contract class.
 * @param bridge - class implementing Bridge interface
 */
    public RefreshValet(Bridge bridge){
        //Class implementing Bridge interface
        mBridge = bridge;

        //refresh contract class for refresh tables in database
        mContract = new RefreshContract();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      HashMap<String, RefreshItem> getRefreshMap() - get refreshMap from database
 *
 * Setters:
 *      void setRefresh(SQLiteDatabase,RefreshItem) - save refresh item to database
 */
/**************************************************************************************************/
/**
 * HashMap<String, RefreshItem> getRefreshMap() - get refreshMap from database
 * @return - refreshMap of RefreshItems
 */
    public HashMap<Integer, RefreshItem> getRefreshMap(){
        //get Select All query for the movie refresh table
        String query = RefreshContract.RefreshEntry.SELECT_ALL;

        //run query, get cursor
        Cursor cursor = mBridge.getDatabase().rawQuery(query, null);

        //check if cursor is null or cursor count is zero
        if(cursor != null && cursor.getCount() != 0){
            //cursor is valid, prepare refresh hashMap
            return prepareRefreshMap(cursor);
        }

        //invalid cursor, return empty hashMap
        return new HashMap<>();
    }

/**
 * void setRefresh(int,Long) - save refresh data into database
 * @param movieType - movie type being refreshed
 * @param refreshDate - date when list needs to be refreshed
 */
    public void setRefresh(int movieType, Long refreshDate){
        //create whereClause
        String whereClause = RefreshContract.RefreshEntry.COLUMN_NAME_MOVIES_TYPE +
                " = " + movieType;

        //update refresh table with new refresh date
        mBridge.getDatabase().update(RefreshContract.RefreshEntry.TABLE_NAME,
                mContract.getContentValues(movieType, refreshDate), whereClause, null);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      HashMap<String, RefreshItem> prepareRefreshMap(Cursor) - get refresh data from the database
 */
/**************************************************************************************************/
/**
 * HashMap<String, RefreshItem> prepareRefreshMap(Cursor) - get refresh data from the database and
 * put them in a hashMap
 * @param cursor - cursor containing refresh data from database
 * @return - refresh item hashMap
 */
    private HashMap<Integer, RefreshItem> prepareRefreshMap(Cursor cursor){
        //initialize refresh buffer
        HashMap<Integer, RefreshItem> refreshMap = new HashMap<>();

        //get number of movie lists to refresh
        int count = cursor.getCount();

        //valid query, move to first item in cursor
        cursor.moveToFirst();

        //loop through list
        for(int i = 0; i < count; i++){

            //create refresh item object
            RefreshItem item = new RefreshItem();
            //get movie list name
            item.movieType = cursor.getInt(RefreshContract.RefreshEntry.INDEX_MOVIE_TYPE);
            //get refresh date
            item.dateRefresh = cursor.getLong(RefreshContract.RefreshEntry.INDEX_DATE_REFRESH);

            //add refreshItem to hashMap with movieList name as key
            refreshMap.put(item.movieType, item);

            //move to next cursor
            cursor.moveToNext();
        }

        //close cursor
        cursor.close();

        //return hashMap
        return refreshMap;
    }

/**************************************************************************************************/


}
