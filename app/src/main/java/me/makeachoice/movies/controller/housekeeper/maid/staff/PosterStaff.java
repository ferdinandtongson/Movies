package me.makeachoice.movies.controller.housekeeper.maid.staff;

import java.util.ArrayList;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.model.MovieJSON;

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
 *      void consumeModel(MovieJSON)
 */
/**************************************************************************************************/
/**
 * void consumeModel(MovieJSON) - takes data from Model and prepares it for use by View
 * @param model - JSON type data model
 */
    public void consumeModel(MovieJSON model){
        //take and prepare Poster data items from JSON model
        mList = preparePosterItems(model);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ArrayList<PosterItem> preparePosterItems(JSON) - prepares a list of PosterItems from the
 * MovieJSON data model
 * @param model - a list of MovieJSON objects
 * @return ArrayList<PosterItem> - a list of PosterItems
 */
    private ArrayList<PosterItem> preparePosterItems(MovieJSON model){

        //create an ArrayList to hold the list items
        ArrayList<PosterItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = model.getMovieCount();

        //loop through the data models
        for(int i = 0; i < count; i++){
            //create poster item from model
            PosterItem item = new PosterItem(model.getMovie(i).getTitle(),
                    model.getMovie(i).getPosterPath());

            //add item into array list
            itemList.add(item);
        }

        return itemList;
    }

/**************************************************************************************************/


}
