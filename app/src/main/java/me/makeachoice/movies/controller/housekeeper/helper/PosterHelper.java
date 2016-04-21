package me.makeachoice.movies.controller.housekeeper.helper;

import android.support.v7.widget.RecyclerView;

import me.makeachoice.movies.R;

/**
 * PosterHelper contains constant definitions for all the resources used by any of the "Poster"
 * classes underneath the housekeeper.
 */
public class PosterHelper {

    //NAME_ID - unique name of instantiated Maid class
    public final static int NAME_ID = R.string.maid_poster;

/**************************************************************************************************/
// Card Item layout ids used in a RecyclerView, from poster_card.xml

    //POSTER_CARD_LAYOUT_ID is the layout used for each item in the Poster RecyclerView
    public static final int POSTER_CARD_LAYOUT_ID = R.layout.card_poster;

    //POSTER_ITEM_CRD_ID is the cardView id used to display the cardView
    public static final int POSTER_ITEM_CRD_ID = R.id.crd_poster;

    //POSTER_ITEM_IMG_ID is the imageView id used to display the poster
    public static final int POSTER_ITEM_IMG_ID = R.id.img_poster;

    //POSTER_ITEM_TXT_ID is the textView id used to display the poster title
    public static final int POSTER_ITEM_TXT_ID = R.id.txt_title;

/**************************************************************************************************/

/**************************************************************************************************/
// Fragment layout ids used to display a grid of movie posters, from poster_fragment.xml

    //POSTER_FRAGMENT_LAYOUT_ID is the fragment layout used to display a bunch of movie posters
    public static final int POSTER_FRAGMENT_LAYOUT_ID = R.layout.poster_fragment;

    //POSTER_REC_ID is the RecyclerView id used poster fragment
    public static final int POSTER_REC_ID = R.id.rec_poster;

/**************************************************************************************************/

/**************************************************************************************************/
// Drawable resource ids used as image placeholders for the movie posters

    //POSTER_PLACEHOLDER_IMG_ID is the placeholder image displayed before the poster image
    public static final int POSTER_PLACEHOLDER_IMG_ID = R.drawable.placeholder;

    //POSTER_ERROR_IMG_ID is the error image displayed if there is no movie poster image
    public static final int POSTER_ERROR_IMG_ID = R.drawable.placeholder_error;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ViewHolder class used to hold the child views of PosterFragment
 */
/**************************************************************************************************/

    public static class ViewHolder{
        public boolean isEmpty;
        public RecyclerView recycler;
    }

/**************************************************************************************************/

}
