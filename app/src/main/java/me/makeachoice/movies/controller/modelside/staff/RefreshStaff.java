package me.makeachoice.movies.controller.modelside.staff;

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
 *      int getMapSize() - size of refresh HashMap
 */
/**************************************************************************************************/
/**
 * int getMapSize() - size of refresh HashMap
 * @return - size of HashMap
 */
    public int getMapSize(){
        return mRefreshMap.size();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setRefreshItem(String,RefreshItem) - set refreshItem into buffer
 *      void setRefreshMap(HashMap<Integer,RefreshItem> - set buffer HashMap
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

/**
 * void setRefreshMap(HashMap<Integer,RefreshItem>) - set buffer HashMap
 * @param refresh - HashMap
 */
    public void setRefreshMap(HashMap<Integer,RefreshItem> refresh){
        mRefreshMap = new HashMap<>(refresh);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      boolean needToRefreshList(int) - returns true if the list needs to be refreshed
 *      boolean checkDates(RefreshItem) - compares dates and determines if refresh date is passed
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * boolean needToRefreshList(int) - gets RefreshItem from buffer. If in buffer, check refresh date.
 * @param movieType - movie type
 * @return - returns true if list needs to be refreshed, false if not
 */
    public boolean needToRefresh(int movieType){
        //get refresh item movie list
        RefreshItem item = mRefreshMap.get(movieType);

        if(item != null){
            return checkDates(item);
        }

        //not in buffer, refresh
        return true;
    }

/**
 * boolean checkDates(RefreshItem) - compares dates and determines if refresh date is passed
 * @param item - refresh item
 * @return - boolean value, true = need to refresh
 */
    private boolean checkDates(RefreshItem item){
        //valid item, get refresh date
        Date refreshDate = new Date(item.dateRefresh);

        //get current date
        Date currentDate = DateManager.currentDate();

        //compare current date with refresh date, return true if need to refresh
        return currentDate.after(refreshDate);
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
