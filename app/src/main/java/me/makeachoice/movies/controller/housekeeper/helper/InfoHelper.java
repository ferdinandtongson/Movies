package me.makeachoice.movies.controller.housekeeper.helper;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import me.makeachoice.movies.R;

/**
 * InfoHelper contains constant definitions for all the resources used by any of the "Info"
 * classes underneath the housekeeper.
 */
public class InfoHelper {

    //NAME_ID - unique name of instantiated Maid class
    public final static int NAME_ID = R.string.maid_info;

/**************************************************************************************************/
/**
 * Fragment layout ids used to display InfoFragment, from info_fragment.xml
 */
/**************************************************************************************************/

    //INFO_FRAGMENT_LAYOUT_ID - layout used by Info Fragment
    public final static int INFO_FRAGMENT_LAYOUT_ID = R.layout.info_fragment;

    //INFO_TXT_TITLE - textView child for title of movie
    public final static int INFO_TXT_TITLE = R.id.txt_title;
    //INFO_IMG_POSTER - imageView child for poster image of movie
    public final static int INFO_IMG_POSTER = R.id.img_poster;
    //INFO_TXT_RELEASE - textView child for release date of movie
    public final static int INFO_TXT_RELEASE = R.id.txt_release;
    //INFO_TXT_CAST - textView child for cast members of movie
    public final static int INFO_TXT_CAST = R.id.txt_cast;
    //INFO_RTB_RATING - ratingBarView child for visual display of rating
    public final static int INFO_RTB_RATING = R.id.rtb_rating;
    //INFO_TXT_RATING - textView child for rating percentage of movie
    public final static int INFO_TXT_RATING = R.id.txt_rating;
    //INFO_TXT_OVERVIEW - textView child for overview, plot, of movie
    public final static int INFO_TXT_OVERVIEW = R.id.txt_overview;

/**************************************************************************************************/

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

/**************************************************************************************************/
/**
 * ViewHolder class used to hold the child views of InfoFragment
 */
/**************************************************************************************************/

    public static class ViewHolder{
        public boolean isEmpty;
        public TextView title;
        public ImageView poster;
        public TextView release;
        public TextView cast;
        public TextView rating;
        public TextView overview;
        public RatingBar stars;
    }

/**************************************************************************************************/

}
