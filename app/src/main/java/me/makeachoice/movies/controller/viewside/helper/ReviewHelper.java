package me.makeachoice.movies.controller.viewside.helper;

import me.makeachoice.movies.R;

/**
 * ReviewHelper contains constant definitions for all the resources used by the review fragment. It
 * also has a ViewHolder pattern implementation contained in the inner class inherited from
 * MyHelper abstract class
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
public class ReviewHelper extends MyHelper{

    //NAME_ID - unique name of instantiated MyHouseKeeper class
    public final static int NAME_ID = R.string.maid_detail_reviews;


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

    //REVIEW_LAYOUT_ID is the layout used to display fragments
    public static final int REVIEW_FRAGMENT_LAYOUT_ID = R.layout.detail_fragment;

    //REVIEW_REC_ID is container that holds the fragments
    public static final int REVIEW_REC_ID = R.id.rec_detail;

    //REVIEW_TXT_NO_DATA_ID is the textView displayed when there is no data to display
    public static final int REVIEW_TXT_NO_DATA_ID = R.id.txt_detail_no_data;

    //XXX_TOOLBAR_ID is the toolbar used by Activity
    //public static final  int XXX_TOOLBAR_ID = R.id.toolbar;

    //XXX_FAB_ID is the floating action button
    //public static final  int XXX_FAB_ID = R.id.fab;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * CardView item child view ids
 */
/**************************************************************************************************/

    //CARD_REVIEW_LAYOUT_ID - cardView layout used in the review recycler
    public final static int CARD_REVIEW_LAYOUT_ID = R.layout.review_card;

    //CARD_REVIEW_CRD_REVIEW_ID - cardView that holds the other views
    public final static int CARD_REVIEW_CRD_REVIEW_ID = R.id.crd_review;

    //CARD_REVIEW_TXT_AUTHOR_ID - textView child for the author of the review
    public final static int CARD_REVIEW_TXT_AUTHOR_ID = R.id.txt_review_author;

    //CARD_REVIEW_TXT_REVIEW_ID - textView child for the author of the review
    public final static int CARD_REVIEW_TXT_REVIEW_ID = R.id.txt_review;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * String resource ids used
 */
/**************************************************************************************************/

    //STR_NO_DATA_ID is the "No Data to Display" string id
    public static final int STR_NO_DATA_ID = R.string.str_no_data;

/**************************************************************************************************/


}
