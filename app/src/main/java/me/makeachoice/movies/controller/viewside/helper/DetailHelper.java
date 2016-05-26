package me.makeachoice.movies.controller.viewside.helper;

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

    //DETAIL_FAB_ID is the floating action button
    public static final  int DETAIL_FAB_ID = R.id.detail_fab;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * String resource string ids used as KEY values and drawable ids used for favorite status
 */
/**************************************************************************************************/

    //KEY_MOVIE_TYPE_ID - key value used for storing the movie type
    public static final int KEY_MOVIE_TYPE_ID = R.string.key_movie_type;

    //KEY_MOVIE_INDEX_ID - key value used for storing the movie index
    public static final int KEY_MOVIE_INDEX_ID = R.string.key_movie_index;

    //STR_SHARE_ID - string id of "Share link!" message
    public static final int STR_SHARE_ID = R.string.str_share_link;

    //DRW_STAR_WHITE_ID - drawable used when a movie is marked as favorite by the user
    public static final int DRW_STAR_WHITE_ID = R.drawable.star_white;

    //DRW_STAR_BORDER_ID - drawable used when a movie is not a favorite
    public static final int DRW_STAR_BORDER_ID = R.drawable.star_border_white;


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewDialog layout and child view ids
 */
/**************************************************************************************************/

    //DIA_REVIEW_LAYOUT_ID - layout id of ReviewDialog
    public static final int DIA_REVIEW_LAYOUT_ID = R.layout.review_dialog;

    //DIA_REVIEW_TXT_AUTHOR - child view id of ReviewDialog used to display author of review
    public static final int DIA_REVIEW_TXT_AUTHOR = R.id.dia_txt_review_author;

    //DIA_REVIEW_TXT_REVIEW - child view id of ReviewDialog used to display review of movie
    public static final int DIA_REVIEW_TXT_REVIEW = R.id.dia_txt_review;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * CastDialog layout and child view ids
 */
/**************************************************************************************************/

    //DIA_CAST_LAYOUT_ID - layout id of CastDialog
    public static final int DIA_CAST_LAYOUT_ID = R.layout.cast_dialog;

    //DIA_CAST_IMG_ACTOR - child view id of CastDialog used to display image of actor
    public static final int DIA_CAST_IMG_ACTOR = R.id.dia_cast_img_actor;

    //DIA_CAST_TXT_ACTOR - child view id of CastDialog used to display name of actor
    public static final int DIA_CAST_TXT_ACTOR = R.id.dia_cast_txt_actor;

    //DIA_CAST_TXT_CHARACTER - child view id of CastDialog used to display character played by actor
    public static final int DIA_CAST_TXT_CHARACTER = R.id.dia_cast_txt_character;

/**************************************************************************************************/


}
