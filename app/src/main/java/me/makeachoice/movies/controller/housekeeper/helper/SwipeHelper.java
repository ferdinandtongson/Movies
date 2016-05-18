package me.makeachoice.movies.controller.housekeeper.helper;

import me.makeachoice.movies.R;

/**
 * SwipeHelper contains constant definitions for all the resources used by SwipeActivity. It also
 * has a ViewHolder pattern implementation contained in the inner class inherited from MyHelper
 * abstract class
 *
 * Variables from MyHelper:
 * - None -
 *
 * Methods from MyHelper:
 * - None -
 *
 * Inner class from MyHelper:
 * static class ViewHolder
 * HashMap<Integer, View> mHolderMap
 * public View getView(View layout, int id)
 */
public class SwipeHelper extends MyHelper{

    //NAME_ID - unique name of instantiated MyHouseKeeper class
    public final static int NAME_ID = R.string.keeper_swipe;


/**************************************************************************************************/
/**
 * Activity layout ids used by the Activity.
 *
 * NOTE:
 * With Activity layouts, there are typically not many child views. Usually Activity layouts hold
 * container views that will display fragments. Also, toolbar and floating action button layout
 * ids should be put here. These are all found in res/layout
 */
/**************************************************************************************************/

    //SWIPE_LAYOUT_ID is the layout used to display fragments
    public static final int SWIPE_LAYOUT_ID = R.layout.activity_swipe;

    //SWIPE_PAGER_ID is the swipe viewpager that holds the fragments
    public static final int SWIPE_PAGER_ID = R.id.swipe_pager;

    //SWIPE_TITLE_STRIP_ID is the viewpager title strip
    public static final int SWIPE_TITLE_STRIP_ID = R.id.swipe_title_strip;

    //SWIPE_FAB_ID is the floating action button
    public static final  int SWIPE_FAB_ID = R.id.swipe_fab;

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * String ids used by the Activity
 */
/**************************************************************************************************/

    public static final int KEY_MOVIE_INDEX = R.string.key_movie_index;


/**************************************************************************************************/

}
