package me.makeachoice.movies.controller.housekeeper.maid.staff;

import android.content.Context;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * PosterStaff takes Poster Model data and prepares the data to be consumed by the HouseKeeping
 * staff
 */
public class PosterStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<PosterItem> mList - array list of poster data ready for comsumption by View
 */
/**************************************************************************************************/

    //mPosterList - list of PosterItems ready for consumption by View
    ArrayList<PosterItem> mList;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterStaff - constructor
 */
    public PosterStaff(){ }

/**************************************************************************************************/
/**
 * Getters:
 *      ArrayList<PosterItem> getPosterItems
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * getPosterItems - list of PosterItems ready for use by the View
 * @return - ArrayList<PosterItem>
 */
    public ArrayList<PosterItem> getPosterItems(){ return mList; }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      void consumeModel(ArrayList<MovieModel>)
 */
/**************************************************************************************************/
/**
 * void consumeModel(ArrayList<MovieModel>) - takes data from Model and prepares it for use by View
 * @param models - list of MovieModel data
 */
    public void consumeModels(Context ctx, ArrayList<MovieModel> models){
        //take and prepare Poster data items from model
        mList = preparePosterItems(ctx, models);
    }

    public void consumeModel(Context ctx, MovieModel model){
        String posterPath = ctx.getString(R.string.tmdb_image_base_request) +
                model.getPosterPath() + "?" +
                ctx.getString(R.string.tmdb_query_api_key) + "=" +
                ctx.getString(R.string.api_key_tmdb);
        //create poster item from model
        PosterItem item = new PosterItem(model.getTitle(), posterPath);

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ArrayList<PosterItem> preparePosterItems(ArrayList<MovieModel>) - prepares a list of PosterItems
 * from the MovieModel data
 * @param models - a list of MovieModel objects
 * @return ArrayList<PosterItem> - a list of PosterItems
 */
    private ArrayList<PosterItem> preparePosterItems(Context ctx, ArrayList<MovieModel> models){

        //create an ArrayList to hold the list items
        ArrayList<PosterItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = models.size();

        //loop through the data models
        for(int i = 0; i < count; i++){
            String posterPath = ctx.getString(R.string.tmdb_image_base_request) +
                    models.get(i).getPosterPath() + "?" +
                    ctx.getString(R.string.tmdb_query_api_key) + "=" +
                    ctx.getString(R.string.api_key_tmdb);
            //create poster item from model
            PosterItem item = new PosterItem(models.get(i).getTitle(), posterPath);

            //add item into array list
            itemList.add(item);
        }

        return itemList;
    }

/**************************************************************************************************/



}
