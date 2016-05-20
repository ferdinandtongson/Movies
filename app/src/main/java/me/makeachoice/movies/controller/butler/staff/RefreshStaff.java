package me.makeachoice.movies.controller.butler.staff;

import java.util.Date;
import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.model.item.RefreshItem;
import me.makeachoice.movies.util.DateManager;

/**
 * RefreshStaff maintains the buffer objects holding Refresh data
 *
 * It uses other classes to assist in the upkeep of the buffers:
 *      DateManager - handles date operations
 */
public class RefreshStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      HashMap<Integer, RefreshItem> mRefreshMap - buffer holding movie refresh item data
 */
/**************************************************************************************************/

    //mRefreshItem - buffer holding movie refresh item data
    private HashMap<Integer, RefreshItem> mRefreshMap;


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * RefreshStaff - constructor, initialize refresh buffer
 * @param boss - Boss class
 */
    public RefreshStaff(Boss boss){
        //buffer holding movie refresh item data
        mRefreshMap = new HashMap<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      - None -
 *
 * Setters:
 *      void setRefreshItem(String,RefreshItem) - save refreshItem to buffer
 */
/**************************************************************************************************/
/**
 * void setRefreshItem(RefreshItem) - save refreshItem to buffer
 * @param movieType - movie type
 */
    public void setRefreshDate(int movieType, Long refreshDate){
        RefreshItem item = new RefreshItem();
        item.movieType = movieType;
        item.dateRefresh = refreshDate;

        //save refreshItem to buffer
        mRefreshMap.put(movieType, item);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      boolean needToRefreshList(int) - returns true if the list needs to be refreshed
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * boolean needToRefreshList(int) - returns true if the list needs to be refreshed. Takes the
 * current date and compares it with the refresh date. If the current date is pass the refresh date,
 * the list needs to be updated with current data
 * @param movieType - movie type
 * @return - returns true if list needs to be refreshed, false if not
 */
    public boolean needToRefreshList(int movieType){
        //get current date
        Date currentDate = DateManager.currentDate();

        //get refresh item movie list
        RefreshItem item = mRefreshMap.get(movieType);

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
        //clear and null refresh buffer
        mRefreshMap.clear();
        mRefreshMap = null;
    }

/**************************************************************************************************/

}
