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
 * void setRefreshItem(String,RefreshItem) - save refreshItem to buffer
 * @param movieList - which movieList has been refreshed
 * @param item - refreshItem to save to buffer
 */
    public void updateRefreshItem(String movieList, RefreshItem item){
        //save refreshItem to buffer
        mRefreshMap.put(movieList, item);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/

    public boolean needToRefreshList(String movieList){
        //get current date
        Date currentDate = DateManager.currentDate();

        RefreshItem item = mRefreshMap.get(movieList);
        if(item != null){
            Date refreshDate = new Date(item.dateRefresh);

            if(currentDate.after(refreshDate)){
                return true;
            }
        }

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
