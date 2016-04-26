package me.makeachoice.movies.controller.housekeeper.helper;

import me.makeachoice.movies.R;

/**
 * PosterHelper contains constant definitions for all the resources used by any of the "Poster"
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
public class PosterHelper extends MyHelper {

    //NAME_ID - unique name of instantiated Maid class
    public final static int NAME_ID = R.string.maid_poster;
    public final static int NAME_ID_POPULAR = R.string.maid_poster_popular;
    public final static int NAME_ID_HIGHEST_RATED = R.string.maid_poster_highest_rated;
    public final static int NAME_ID_PLAYING = R.string.maid_poster_now_playing;
    public final static int NAME_ID_UPCOMING = R.string.maid_poster_upcoming;
    public final static int NAME_ID_FAVORITE = R.string.maid_poster_favorite;


/**************************************************************************************************/
// Fragment layout ids used to display a grid of movie posters, from poster_fragment.xml

    //POSTER_FRAGMENT_LAYOUT_ID is the fragment layout used to display a bunch of movie posters
    public static final int POSTER_FRAGMENT_LAYOUT_ID = R.layout.poster_fragment;

    //POSTER_REC_ID is the RecyclerView id used poster fragment
    public static final int POSTER_REC_ID = R.id.rec_poster;

/**************************************************************************************************/

/**************************************************************************************************/
// Card Item layout ids used in a RecyclerView, from poster_card.xml

    //POSTER_CARD_LAYOUT_ID is the layout used for each item in the Poster RecyclerView
    public static final int POSTER_CARD_LAYOUT_ID = R.layout.poster_card;

    //POSTER_ITEM_CRD_ID is the cardView id used to display the cardView
    public static final int POSTER_ITEM_CRD_ID = R.id.crd_poster;

    //POSTER_ITEM_IMG_ID is the imageView id used to display the poster
    public static final int POSTER_ITEM_IMG_ID = R.id.img_poster;

    //POSTER_ITEM_TXT_ID is the textView id used to display the poster title
    public static final int POSTER_ITEM_TXT_ID = R.id.txt_title;

/**************************************************************************************************/

/**************************************************************************************************/
// Drawable resource ids used as image placeholders for the movie posters

    //POSTER_PLACEHOLDER_IMG_ID is the placeholder image displayed before the poster image
    public static final int POSTER_PLACEHOLDER_IMG_ID = R.drawable.placeholder;

    //POSTER_ERROR_IMG_ID is the error image displayed if there is no movie poster image
    public static final int POSTER_ERROR_IMG_ID = R.drawable.placeholder_error;

/**************************************************************************************************/

}
