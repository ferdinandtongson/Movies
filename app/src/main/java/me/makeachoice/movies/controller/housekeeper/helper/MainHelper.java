package me.makeachoice.movies.controller.housekeeper.helper;

import me.makeachoice.movies.R;

/**
 * MainHelper contains constant definitions for all the resources used by the Main Activity class.
 *
 * Variables from MyHelper:
 *      - None -
 *
 * Methods from MyHelper:
 *      - None -
 *
 * Inner class from MyHelper:
 *      static class ViewHolder
 *          HashMap<Integer, View> mHolderMap
 *          public View getView(View layout, int id)
 *
 */
public class MainHelper extends MyHelper{

    //NAME_ID - unique name of instantiated MyHouseKeeper class
    public final static int NAME_ID = R.string.keeper_main;


/**************************************************************************************************/
/**
 * Activity layout ids used to display MainActivity
 */
/**************************************************************************************************/

    //MAIN_LAYOUT_ID is the layout used to display fragments
    public static final int MAIN_LAYOUT_ID = R.layout.activity_main;

    //MAIN_CONTAINER_ID is container that holds the fragments
    public static final int MAIN_CONTAINER_ID = R.id.main_container;

    //MAIN_TOOLBAR_ID is the toolbar used by MainActivity
    public static final  int MAIN_TOOLBAR_ID = R.id.toolbar;

    //MAIN_FAB_ID is the floating action button
    public static final  int MAIN_FAB_ID = R.id.fab;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Menu ids used in toolbar of MainActivity
 */
/**************************************************************************************************/

    //Menu for Toolbar
    //public static final  int MAIN_MENU = R.menu.menu_main;

    //Item id from Menu
    //public static final  int MENU_ITEM01 = R.id.action_bar_item01;
    //public static final  int MENU_ITEM02 = R.id.action_bar_item02;
    //public static final  int MENU_ITEM03 = R.id.action_bar_item03;
    //public static final  int MENU_ITEM04 = R.id.action_bar_item04;


/**************************************************************************************************/

}
