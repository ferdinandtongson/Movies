package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.controller.butler.MovieButler;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;
import me.makeachoice.movies.controller.housekeeper.MyHouseKeeper;
import me.makeachoice.movies.controller.housekeeper.helper.MainHelper;
import me.makeachoice.movies.controller.housekeeper.maid.MyMaid;
import me.makeachoice.movies.controller.butler.valet.NetworkValet;
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
 *      MovieButler mButler - Butler class taking care of Movie data
 *      NetworkValet mNetworkValet - in charge of checking for network connectivity
 *      int mMovieRequest - current list of movies requested
 *      HashMap<Integer,MyHouseKeeper> mHouseKeeperRegistry - registered HouseKeepers
 *      HashMap<Integer,MyMaid> mMaidRegistry - registered Maids
 *
 *      boolean mOrientationChange - status flag on whether the phone orientation has changed
 */
/**************************************************************************************************/

    //mActivityContext is the current UI Activity (In this case we are only using one Activity)
    Context mActivityContext;

    //mButler will take care of preparing Movie data for consumption
    MovieButler mButler;

    //mNetworkValet is in charge of checking for network connectivity
    NetworkValet mNetworkValet;

    //mMovieRequest - current list of movies requested
    private int mMovieRequest;

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
 *      MovieJson getMovies(int) - get list of JSON movie data
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
 * MovieJSON getModel(int) - request access to the JSON movie data. If null or is a new request,
 * Butler will start an AsyncTask to get new movie data.
 * @param request - type of movie data requested (most popular or highest rated)
 * @return - an array list of movie data
 */
    public ArrayList<MovieModel> getMovies(int request){

        //check if there is data or if it is a new request
        if(mButler.getModel() == null || mMovieRequest != request){

            //record current movie request
            mMovieRequest = request;
            //request Butler to get data (will activate an AsyncTask execute
            mButler.requestMovies(request);
            //let requester know there is not any data yet
            return null;
        }

        //return movie data list
        return mButler.getModel();
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
        if(mButler == null){
            //initialize MovieButler
            mButler = new MovieButler(this);
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
    public void updateMainActivity(){
        //TODO - very rigid, need to massage
        //instantiate HouseKeeper that will maintain the Main Activity
        MainKeeper keeper = (MainKeeper)mHouseKeeperRegistry.get(MainHelper.NAME_ID);
        //tells the HouseKeeper to prepare the fragments the Activity will use
        keeper.displayFragment();
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
        if(key == MainHelper.NAME_ID){
            MainKeeper keeper = new MainKeeper(this);
            mHouseKeeperRegistry.put(key, keeper);
            return keeper;
        }

        return null;
    }

/**************************************************************************************************/


}
