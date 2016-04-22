package me.makeachoice.movies.controller.housekeeper.helper;

import android.widget.FrameLayout;

import me.makeachoice.movies.R;

/**
 * MainHelper contains constant definitions for all the resources used by the Main Activity class.
 */
public class MainHelper {

    //NAME_ID - unique name of instantiated MyHouseKeeper class
    public final static int NAME_ID = R.string.keeper_main;


/**************************************************************************************************/
/**
 * Activity layout ids used to display MainActivity, from activity_main.xml
 */
/**************************************************************************************************/

    //MAIN_LAYOUT_ID is the layout used to display fragments
    public static final int MAIN_LAYOUT_ID = R.layout.activity_main;

    //MAIN_CONTAINER_ID is container that holds the fragments
    public static final int MAIN_CONTAINER_ID = R.id.main_container;

    //MAIN_TOOLBAR_ID is the toolbar used by MainFragment, found in toolbar.xml layout file
    public static final  int MAIN_TOOLBAR_ID = R.id.toolbar;

    //MAIN_FAB_ID is the floating action button, found in float_button.xml layout file
    public static final  int MAIN_FAB_ID = R.id.fab;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Menu ids used in toolbar of MainActivity
 */
/**************************************************************************************************/

    //Menu for Toolbar found in menu_main menu file
    public static final  int MAIN_MENU = R.menu.menu_main;

    //Item id from Menu
    public static final  int MENU_ITEM01 = R.id.action_bar_item01;
    public static final  int MENU_ITEM02 = R.id.action_bar_item02;
    public static final  int MENU_ITEM03 = R.id.action_bar_item03;
    public static final  int MENU_ITEM04 = R.id.action_bar_item04;


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ViewHolder class used to hold the child views of MainActivity
 */
/**************************************************************************************************/

    public static class ViewHolder{
        public boolean isEmpty;
        public FrameLayout container;
    }

/**************************************************************************************************/

}
