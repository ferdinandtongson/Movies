package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.butler.DetailButler;
import me.makeachoice.movies.controller.butler.PosterButler;
import me.makeachoice.movies.controller.housekeeper.DetailKeeper;
import me.makeachoice.movies.controller.housekeeper.MyHouseKeeper;
import me.makeachoice.movies.controller.housekeeper.SwipeKeeper;
import me.makeachoice.movies.controller.housekeeper.helper.DetailHelper;
import me.makeachoice.movies.controller.housekeeper.helper.SwipeHelper;
import me.makeachoice.movies.controller.housekeeper.maid.MyMaid;
import me.makeachoice.movies.controller.butler.valet.NetworkValet;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * Boss is the "boss", main controller of the app and interfaces with the View and Model. Boss
 * tries to adhere to the MVP (Model-View-Presenter) model so Model-View communication is
 * prevented; in MVC (Model-View-Controller) model, the Model and View can communicate
 */

public class Boss extends Application{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Context mActivityContext - current Activity Context on display
 *      PosterButler mButler - Butler class taking care of Poster data
 *      NetworkValet mNetworkValet - in charge of checking for network connectivity
 *      HashMap<Integer,MyHouseKeeper> mHouseKeeperRegistry - registered HouseKeepers
 *      HashMap<Integer,MyMaid> mMaidRegistry - registered Maids
 *
 *      boolean mOrientationChange - status flag on whether the phone orientation has changed
 */
/**************************************************************************************************/

    //mActivityContext is the current UI Activity (In this case we are only using one Activity)
    Context mActivityContext;

    //mButler will take care of preparing Movie data for consumption
    PosterButler mPosterButler;
    DetailButler mDetailButler;

    //mNetworkValet is in charge of checking for network connectivity
    NetworkValet mNetworkValet;

    //mHouseKeeperRegistry - HashMap of instantiated HouseKeeper classes being used by the Boss
    private HashMap<Integer, MyHouseKeeper> mHouseKeeperRegistry = new HashMap<>();

    //mMaidRegistry - HashMap of instantiated Maid classes being used by HouseKeepers
    private HashMap<Integer, MyMaid> mMaidRegistry = new HashMap<>();

    //mOrientationChange - status flag on whether the phone orientation has changed
    boolean mOrientationChange;


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity Context
 *      ArrayList<PosterItem> getPosters(int) - get list of poster item data
 *      MovieItem getMovie(int, int) - get movie item data
 *      MyHouseKeeper getHouseKeeper(Integer) - get houseKeeper from registry
 *      MyMaid getMaid(Integer) - get Maid from registry
 *
 * Setters:
 *      void setOnOrientationChange(boolean) - saves orientation change status of Activity
 */
/**************************************************************************************************/
/**
 * Context getActivityContext - get current Activity Context
 * @return - context of current activity
 */
    public Context getActivityContext(){
    return mActivityContext;
}

/**
 * ArrayList<PosterItem> getModel(int) - get list of poster item data. If null or is a new request,
 * Butler will start an AsyncTask to get new movie data.
 * @param request - type of movie data requested
 * @return - an array list of poster item data
 */
    public ArrayList<PosterItem> getPosters(int request){

        //return poster items
        return mPosterButler.getPosters(request);
    }

/**
 * MovieItem getMovie(int, int) - get movie item data. If the movie item data is incomplete, will
 * start an AsyncTask to get the missing data.
 * @param movieType - type of movies the movie is selected from
 * @param position - index location of the selected movie
 * @return - movie item data of the selected movie
 */
    public MovieItem getMovie(int movieType, int position){
        //get the movie model from the PosterButler
        MovieModel model = mPosterButler.getMovie(movieType, position);

        //get the movie item data from DetailButler, if incomplete will start an AsyncTask
        return mDetailButler.getMovie(model);
    }

/**
 * MyHouseKeeper getHouseKeeper(String) - request a reference to a HouseKeeper
 * @param key - HouseKeeper Name
 * @return - sends HouseKeeper reference
 */
    public MyHouseKeeper getHouseKeeper(Integer key){

        //request houseKeeper from registry
        MyHouseKeeper houseKeeper = mHouseKeeperRegistry.get(key);

        //check if houseKeeper is available
        if(houseKeeper == null){
            //if not available, wake houseKeeper
            houseKeeper = startHouseKeeper(key);
        }

        //return houseKeeper
        return houseKeeper;
    }

/**
 * MyMaid getMaid(String) - request a reference to a Maid
 * @param key - Maid name
 * @return - sends Maid reference
 */
    public MyMaid getMaid(Integer key){
        //send Maid
        return mMaidRegistry.get(key);
    }

    /**
     * void setOnOrientationChange(boolean) - saves orientation change status of Activity
     * @param changed - boolean value on whether the orientation of phone has changed or not
     */
    public void setOnOrientationChange(boolean changed){
        //orientation change flag
        mOrientationChange = changed;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      void registerHouseKeeper(int,MyHouseKeeper) - adds HouseKeeper to registry
 *      void registerMaid(int,MyMaid) - adds Maid to registry
 *      void activityCreated(Context) - notifies Boss that onCreate() has been called in Activity
 *      boolean checkNetwork() - checks network connection of phone
 *      void updateMainActivity() - called by Butler when network download has completed
 *      boolean onOrientationChange() - checks orientation change status of phone
 */
/**************************************************************************************************/
/**
 * void registerHouseKeeper(String, MyHouseKeeper) - registers HouseKeepers used by the Boss
 * @param key - HouseKeeper name
 * @param keeper - HouseKeeper class (in charge of Activity objects)
 */
    public void registerHouseKeeper(int key, MyHouseKeeper keeper){
        //register HouseKeeper
        mHouseKeeperRegistry.put(key, keeper);
    }

/**
 * void registerMaid(String, MyMaid) - registers Maids being used by HouseKeepers
 * @param key - Maid name
 * @param maid - Maid class (in charge of maintaining fragments)
 */
    public void registerMaid(Integer key, MyMaid maid){
        //register Maid
        mMaidRegistry.put(key, maid);
    }

/**
 * void activityCreated(Context) - called when the onCreate() of an Activity is called, stores the
 * Activity Context and makes sure the Butler for activity is awake.
 * @param ctx - Activity context
 */
    public void activityCreated(Context ctx){
        //set Activity context - in this case there is only MainActivity
        mActivityContext = ctx;

        //check if Butler is awake
        if(mPosterButler == null){
            //initialize PosterButler
            mPosterButler = new PosterButler(this);
            mDetailButler = new DetailButler(this);
        }
    }

/**
 * boolean hasNetworkConnection() - checks if the phone has network connection
 * @return - true or false, will create alert dialog if false
 */
    public boolean hasNetworkConnection(){

        //check if network valet is awake
        if(mNetworkValet == null){
            //start networkValet
            mNetworkValet = new NetworkValet(this);
        }

        //return network connection status
        return mNetworkValet.hasConnection();
    }


/**
 * void downloadMovieDataComplete() - communication channel used by Butlers to let the Boss
 * know when an AsyncTask thread has completed (in this case for MovieData)
 */
    public void updateSwipeActivity(ArrayList<PosterItem> posters, int request){
        //TODO - very rigid, need to massage
        //instantiate HouseKeeper that will maintain the Main Activity
        SwipeKeeper keeper = (SwipeKeeper)mHouseKeeperRegistry.get(SwipeHelper.NAME_ID);
        //tells the HouseKeeper to prepare the fragments the Activity will use
        keeper.updatePosters(posters, request);
    }

    public void updateDetailActivity(MovieItem movie){
        DetailKeeper keeper = (DetailKeeper)mHouseKeeperRegistry.get(DetailHelper.NAME_ID);
        keeper.updateDetails(movie);
    }

/**
 * boolean onOrientationChange() - check orientation change status of phone
 * @return - boolean status of phone orientation change
 */
    public boolean onOrientationChange(){
        return mOrientationChange;
    }



/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Private Methods:
 *      MyHouseKeeper startHouseKeeper(Integer) - starts requested HouseKeeper
 */

/**************************************************************************************************/
/**
 * MyHouseKeeper startHouseKeeper(Integer) - starts requested HouseKeeper and add to registry
 * @param key - id number of HouseKeeper
 * @return - requested HouseKeeper or null
 */
    private MyHouseKeeper startHouseKeeper(Integer key){

        MyHouseKeeper keeper = mHouseKeeperRegistry.get(key);

        if(keeper == null){
            if(key == SwipeHelper.NAME_ID){
                Log.d("Movies", "Boss.startHouseKeeper: Swipe");
                SwipeKeeper swipeKeeper = new SwipeKeeper(this);

                keeper = swipeKeeper;
            }
            else if(key == DetailHelper.NAME_ID){
                DetailKeeper detailKeeper = new DetailKeeper(this);

                keeper = detailKeeper;
            }
        }

        return keeper;
    }

/**************************************************************************************************/


}
