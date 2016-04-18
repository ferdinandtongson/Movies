package me.makeachoice.movies.controller.housekeeper.page;

import me.makeachoice.movies.R;

/**
 * PosterPage contains constant definitions for all the resources used by any of the "Poster"
 * classes underneath the housekeeper.
 */
public class PosterPage {

/**************************************************************************************************/
// Item layout ids used in a gridView, from item_poster.xml

    //POSTER_ITEM_LAYOUT_ID is the layout used for each item in the Poster gridView
    public static final int POSTER_ITEM_LAYOUT_ID = R.layout.item_poster;

    //POSTER_ITEM_IMG_ID is the imageView id used to display the poster
    public static final int POSTER_ITEM_IMG_ID = R.id.img_poster;

    //POSTER_ITEM_TXT_ID is the textView id used to display the poster title
    public static final int POSTER_ITEM_TXT_ID = R.id.txt_title;

/**************************************************************************************************/

/**************************************************************************************************/
// Fragment layout ids used to display a gridView of movie posters, from poster_fragment.xml

    //POSTER_FRAGMENT_LAYOUT_ID is the fragment layout used to display a bunch of movie posters
    public static final int POSTER_FRAGMENT_LAYOUT_ID = R.layout.poster_fragment;

    //POSTER_GRD_ID is the gridView id used poster fragment
    public static final int POSTER_GRD_ID = R.id.grd_poster;

/**************************************************************************************************/

/**************************************************************************************************/
// Drawable resource ids used as image placeholders for the movie posters

    //POSTER_PLACEHOLDER_IMG_ID is the placeholder image displayed before the poster image
    public static final int POSTER_PLACEHOLDER_IMG_ID = R.drawable.placeholder;

    //POSTER_ERROR_IMG_ID is the error image displayed if there is no movie poster image
    public static final int POSTER_ERROR_IMG_ID = R.drawable.placeholder_error;

/**************************************************************************************************/

}
