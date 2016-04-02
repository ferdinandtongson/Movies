package me.makeachoice.movies.controller.housekeeper.staff;

import android.content.Context;
import android.widget.ListAdapter;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.adapter.PosterAdapter;
import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.model.json.MovieJSON;

/**
 * MovieSelectStaff purpose to create the MoviePoster Adapter and prepare it  for consumption
 */
public class MovieSelectStaff {

    public MovieSelectStaff(){  }

/**************************************************************************************************/
/**
 * PosterAdapter - ListAdapter which displays a list of movies posters. PosterAdapter uses
 * the following resource ids:
 *      LAYOUT_ITEM_POSTER - layout id
 *      ITEM_MOVIE_CHILD_POSTER_VIEW - child view in layout, imageView
 *      ITEM_MOVIE_CHILD_TITLE_VIEW - child view in layout, textView
 */
/**************************************************************************************************/
    //LAYOUT_ITEM_MOVIE - item layout id used by PosterAdapter
    private final static int LAYOUT_ITEM_POSTER = R.layout.item_poster;

    //Child View ids from Item Layouts above
    private final static int ITEM_MOVIE_CHILD_POSTER_VIEW = R.id.img_poster;
    private final static int ITEM_MOVIE_CHILD_TITLE_VIEW = R.id.txt_title;

/**************************************************************************************************/
/**
 * ListAdapter initPosterAdapter(Context, MovieJSON) - initialize the Movie Poster adapter for
 * consumption.
 * @param model - data model for the movies
 * @return ListAdapter - will return a reference to the Poster adapter create with the model
 */
    private ListAdapter initPosterAdapter(Context ctx, MovieJSON model){

        //create an ArrayList to hold the list items to be consumed by the ListAdapter
        ArrayList<PosterItem> itemList = prepareListOfItems(model);

        //create array of layout and child id values for PosterAdapter
        int[] ids = new int[PosterAdapter.INDEX_MAX];
        ids[PosterAdapter.INDEX_LAYOUT_ID] = LAYOUT_ITEM_POSTER;
        ids[PosterAdapter.INDEX_POSTER_ID] = ITEM_MOVIE_CHILD_POSTER_VIEW;
        ids[PosterAdapter.INDEX_TITLE_ID] = ITEM_MOVIE_CHILD_TITLE_VIEW;

        //instantiate PosterAdapter with layout id found in res/layout and the child
        PosterAdapter adapter = new PosterAdapter(ctx, itemList, ids);

        return adapter;
    }

/**
 * ArrayList<PosterItem> prepareListOfItems(JSON) - prepares a list of PosterItems from the
 * MovieJSON data model
 * @param model - a list of MovieJSON objects
 * @return ArrayList<PosterItem> - a list of PosterItems
 */
    private ArrayList<PosterItem> prepareListOfItems(MovieJSON model){

        //create an ArrayList to hold the list items
        ArrayList<PosterItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = model.getMovieCount();

        //loop through the data models
        for(int i = 0; i < count; i++){
            //create poster item from model
            //TODO - hardcoded temporary poster image for testing
            PosterItem item = new PosterItem(model.getMovie(i).getTitle(),R.drawable.sample_1);

            //add item into array list
            itemList.add(item);
        }

        return itemList;
    }


}