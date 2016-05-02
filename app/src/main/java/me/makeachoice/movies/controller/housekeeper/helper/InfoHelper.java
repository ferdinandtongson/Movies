package me.makeachoice.movies.controller.housekeeper.helper;

import me.makeachoice.movies.R;

/**
 * InfoHelper contains constant definitions for all the resources used by any of the "Info"
 * classes underneath the housekeeper.
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
public class InfoHelper extends MyHelper{

    //NAME_ID - unique name of instantiated Maid class
    public final static int NAME_ID = R.string.maid_detail_info;

/**************************************************************************************************/
/**
 * Fragment layout ids used to display InfoFragment, from info_fragment.xml
 */
/**************************************************************************************************/

    //INFO_FRAGMENT_LAYOUT_ID - layout used by Info Fragment
    public final static int INFO_FRAGMENT_LAYOUT_ID = R.layout.info_fragment;

    //INFO_TXT_TITLE - textView child for title of movie
    public final static int INFO_TXT_TITLE_ID = R.id.txt_title;
    //INFO_IMG_POSTER - imageView child for poster image of movie
    public final static int INFO_IMG_POSTER_ID = R.id.img_poster;
    //INFO_TXT_RELEASE - textView child for release date of movie
    public final static int INFO_TXT_RELEASE_ID = R.id.txt_release;
    //INFO_LST_CAST - listView child for cast members of movie
    public final static int INFO_LST_CAST_ID = R.id.lst_cast;
    //INFO_TXT_RATING - textView child for rating percentage of movie
    public final static int INFO_TXT_RATING_ID = R.id.txt_rating;
    //INFO_TXT_OVERVIEW - textView child for overview, plot, of movie
    public final static int INFO_TXT_OVERVIEW_ID = R.id.txt_overview;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ListView item child view ids
 */
/**************************************************************************************************/

    //ITEM_NAME_LAYOUT_ID - item layout used in the cast listview
    public final static int ITEM_NAME_LAYOUT_ID = R.layout.item_name;

    //ITEM_NAME_TXT_NAME_ID - textView child for name of cast members in the movie
    public final static int ITEM_NAME_TXT_NAME_ID = R.id.txt_name;

/**************************************************************************************************/
/**
 * Drawable resource ids used as image placeholders for the movie poster in info_fragment.xml
 */
/**************************************************************************************************/

    //INFO_PLACEHOLDER_IMG_ID is the placeholder image displayed before the poster image
    public static final int INFO_PLACEHOLDER_IMG_ID = R.drawable.placeholder;

    //INFO_ERROR_IMG_ID is the error image displayed if there is no movie poster image
    public static final int INFO_ERROR_IMG_ID = R.drawable.placeholder_error;

    //STR_USER_RATING_ID is the "User Rating" string id
    public static final int STR_USER_RATING_ID = R.string.str_user_rating;

/**************************************************************************************************/

}
