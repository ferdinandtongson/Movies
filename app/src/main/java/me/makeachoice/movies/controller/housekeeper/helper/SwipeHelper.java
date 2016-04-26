package me.makeachoice.movies.controller.housekeeper.helper;

import me.makeachoice.movies.R;

/**
 * SimpleHelper contains constant definitions for all the resources used by SwipeActivity. It also
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

    //SWIPE_TOOLBAR_ID is the toolbar used by Activity
    public static final  int SWIPE_TOOLBAR_ID = R.id.toolbar;

    //XXX_FAB_ID is the floating action button
    //public static final  int XXX_FAB_ID = R.id.fab;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Menu ids used in toolbar of the Activity.
 *
 * Note:
 * Will only need to define these values if the Activity will contain a toolbar.
 */
/**************************************************************************************************/

    //TODO - create menu file for toolbar
    //Menu for Toolbar found in menu_main menu file
    public static final  int SWIPE_MENU = R.menu.menu_swipe;

    //TODO - define menu items
    //Item id from Menu
    public static final  int MENU_ITEM01 = R.id.bar_settings;
    //public static final  int MENU_ITEM02 = R.id.action_bar_item02;
    //public static final  int MENU_ITEM03 = R.id.action_bar_item03;
    //public static final  int MENU_ITEM04 = R.id.action_bar_item04;

/**************************************************************************************************/

}
