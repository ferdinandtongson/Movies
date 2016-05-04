package me.makeachoice.movies.controller.housekeeper.helper;

import me.makeachoice.movies.R;

/**
 * DetailHelper contains constant definitions for all the resources used by DetailActivity. It also
 * has a ViewHolder pattern implementation contained in the inner class inherited from MyHelper
 * abstract class
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
public class DetailHelper extends MyHelper{

    //NAME_ID - unique name of instantiated MyHouseKeeper class
    public final static int NAME_ID = R.string.keeper_detail;


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

    //DETAIL_LAYOUT_ID is the layout used to display fragments
    public static final int DETAIL_LAYOUT_ID = R.layout.activity_detail;

    //DETAIL_PAGER_ID is the swipe viewpager that holds the fragments
    public static final int DETAIL_PAGER_ID = R.id.detail_pager;

    //DETAIL_TITLE_STRIP_ID is the viewpager title strip
    public static final int DETAIL_TITLE_STRIP_ID = R.id.detail_title_strip;

    //XXX_TOOLBAR_ID is the toolbar used by Activity
    //public static final  int XXX_TOOLBAR_ID = R.id.toolbar;

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
    //public static final  int MAIN_MENU = R.menu.menu_main;

    //TODO - define menu items
    //Item id from Menu
    //public static final  int MENU_ITEM01 = R.id.action_bar_item01;
    //public static final  int MENU_ITEM02 = R.id.action_bar_item02;
    //public static final  int MENU_ITEM03 = R.id.action_bar_item03;
    //public static final  int MENU_ITEM04 = R.id.action_bar_item04;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * String resource ids used as KEY values
 */
/**************************************************************************************************/

    //KEY_MOVIE_TYPE_ID is key value used for storing the movie type
    public static final int KEY_MOVIE_TYPE_ID = R.string.key_movie_type;

    //KEY_MOVIE_INDEX_ID is key value used for storing the movie index
    public static final int KEY_MOVIE_INDEX_ID = R.string.key_movie_index;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewDialog layout and child view ids
 */
/**************************************************************************************************/

    //DIA_REVIEW_LAYOUT_ID - layout id of DialogReview
    public static final int DIA_REVIEW_LAYOUT_ID = R.layout.review_dialog;

    //DIA_REVIEW_TXT_AUTHOR - child view id of DialogReview used to display author of review
    public static final int DIA_REVIEW_TXT_AUTHOR = R.id.dia_txt_review_author;

    //DIA_REVIEW_TXT_REVIEW - child view id of DialogReview used to display review of movie
    public static final int DIA_REVIEW_TXT_REVIEW = R.id.dia_txt_review;

/**************************************************************************************************/

}
