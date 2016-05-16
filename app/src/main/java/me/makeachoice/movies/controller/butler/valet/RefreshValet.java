package me.makeachoice.movies.controller.butler.valet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.model.db.contract.RefreshContract;
import me.makeachoice.movies.model.item.RefreshItem;

/**
 * RefreshValet takes care of all the database request to the movie refresh table.
 *
 */
public class RefreshValet{

/**************************************************************************************************/
/**
 * Class Variables:
 *      RefreshContract mRefreshContract - contract class for the movie refresh table
 */
/**************************************************************************************************/

    //mRefreshContract - contract class for the movie refresh table
    private RefreshContract mRefreshContract;


/**************************************************************************************************/


/**************************************************************************************************/
/**
 * RefreshVale - constructor
 * @param boss - Boss class
 */
    public RefreshValet(Boss boss){

        mRefreshContract = new RefreshContract();

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
 */
    public HashMap<String, RefreshItem> getRefreshMap(SQLiteDatabase db){
        //get Select All query for the movie refresh table
        String query = RefreshContract.RefreshEntry.SELECT_ALL;

        //run query, get cursor
        Cursor cursor = db.rawQuery(query, null);

        //check if cursor is null or cursor count is zero
        if(cursor != null && cursor.getCount() != 0){
            //cursor is valid, prepare refresh hashMap
            return prepareRefreshMap(cursor);
        }

        //invalid cursor, return empty hashMap
        return new HashMap<>();
    }

/**
 * void setRefresh(SQLiteDatabase,RefreshItem) - save refresh item into database
 * @param db - SQLiteDatabase object
 * @param item - refresh item to save
 */
    public void setRefresh(SQLiteDatabase db, RefreshItem item){
        String whereClause = RefreshContract.RefreshEntry.COLUMN_NAME_MOVIES_LIST + " = '" +
                item.movieList + "'";

        db.update(RefreshContract.RefreshEntry.TABLE_NAME,
                mRefreshContract.getContentValues(item), whereClause, null);
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
    private HashMap<String, RefreshItem> prepareRefreshMap(Cursor cursor){
        //initialize refresh buffer
        HashMap<String, RefreshItem> refreshMap = new HashMap<>();

        //get number of movie lists to refresh
        int count = cursor.getCount();

        //loop through list
        for(int i = 0; i < count; i++){
            //valid query, move to first item in cursor
            cursor.moveToFirst();

            //create refresh item object
            RefreshItem item = new RefreshItem();
            //get movie list name
            item.movieList = cursor.getString(RefreshContract.RefreshEntry.INDEX_MOVIE_LIST);
            //get refresh date
            item.dateRefresh = cursor.getLong(RefreshContract.RefreshEntry.INDEX_DATE_REFRESH);

            Log.d("Refresh", "     index 0: " + cursor.getInt(0));
            Log.d("Refresh", "     index 1: " + cursor.getString(1));
            Log.d("Refresh", "     index 2: " + cursor.getLong(2));

            //add refreshItem to hashMap with movieList name as key
            refreshMap.put(item.movieList, item);

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
