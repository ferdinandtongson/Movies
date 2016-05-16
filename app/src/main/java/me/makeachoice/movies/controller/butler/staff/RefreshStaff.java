package me.makeachoice.movies.controller.butler.staff;

import android.util.Log;

import java.util.Date;
import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.model.item.RefreshItem;
import me.makeachoice.movies.util.DateManager;

/**
 * RefreshStaff maintains the buffer objects holding Refresh data
 */
public class RefreshStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      HashMap<String, RefreshItem> mRefreshMap - buffer holding movie refresh item data
 */
/**************************************************************************************************/

    //mRefreshItem - buffer holding movie refresh item data
    private HashMap<String, RefreshItem> mRefreshMap;


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterStaff - constructor, registers to Boss
 * data buffers.
 * @param boss - Boss class
 */
    public RefreshStaff(Boss boss){
        Log.d("Start", "     RefreshStaff - constructor");
        //buffer holding movie refresh item data
        mRefreshMap = new HashMap<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      RefreshItem getRefreshItem(String) - get refreshItem for a given movie list
 *
 * Setters:
 *      void setRefreshMap(HashMap<String,RefreshItem) - set refreshMap to given map
 *      void setRefreshItem(String,RefreshItem) - save refreshItem to buffer
 */
/**************************************************************************************************/
/**
 * RefreshItem getRefreshItem(String) - get refreshItem for a given movie list
 * @param movieList - movie list
 * @return - refreshItem of movie list request
 */
    public RefreshItem getRefreshItem(String movieList){
        return mRefreshMap.get(movieList);
    }

/**
 * void setRefreshMap(HashMap<String,RefreshItem>) - set refreshMap to given map
 * @param refreshMap - refreshMap to save to buffer
 */
    public void setRefreshMap(HashMap<String, RefreshItem> refreshMap){
        mRefreshMap = new HashMap<>(refreshMap);
    }

/**
 * void setRefreshItem(String,RefreshItem) - save refreshItem to buffer
 * @param movieList - which movieList has been refreshed
 * @param item - refreshItem to save to buffer
 */
    public void setRefreshItem(String movieList, RefreshItem item){
        //save refreshItem to buffer
        mRefreshMap.put(movieList, item);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      boolean needToRefreshList(String) - returns true if the list needs to be refreshed
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * boolean needToRefreshList(String) - returns true if the list needs to be refreshed. Takes
 * the current date and compares it with the refresh date. If the current date is pass the
 * refresh date, the list needs to be updated with current data
 * @param movieList - movie list name
 * @return - returns true if list needs to be refreshed, false if not
 */
    public boolean needToRefreshList(String movieList){
        //get current date
        Date currentDate = DateManager.currentDate();

        //get refresh item movie list
        RefreshItem item = mRefreshMap.get(movieList);

        //check if item is not null
        if(item != null){
            //valid item, get refresh date
            Date refreshDate = new Date(item.dateRefresh);

            //compare current date with refresh date
            if(currentDate.after(refreshDate)){
                //refresh date has already passed, need to refresh
                return true;
            }
        }

        //refresh date is still in the future, NO need to refresh
        return false;
    }


/**
 * void onFinish() - nulls all of the data in the arrayList buffers
 */
    public void onFinish(){
        mRefreshMap.clear();
    }

/**************************************************************************************************/

}
