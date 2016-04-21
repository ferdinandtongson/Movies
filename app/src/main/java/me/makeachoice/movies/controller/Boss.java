package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;

import me.makeachoice.movies.controller.butler.MovieButler;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;
import me.makeachoice.movies.controller.housekeeper.MyHouseKeeper;
import me.makeachoice.movies.controller.housekeeper.helper.MainHelper;
import me.makeachoice.movies.controller.housekeeper.maid.MyMaid;
import me.makeachoice.movies.model.json.MovieJSON;

/**
 * Boss is the "boss", main controller of the app and interfaces with the View and Model. Boss
 * tries to adhere to the MVP (Model-View-Presenter) model so Model-View communication is
 * prevented; in MVC (Model-View-Controller) model, the Model and View can communicate
 */

//TODO - stage2 Notes
/**
 * ActivityContext cannot be passed into the constructor of any class. Check all classes for any
 * references to ActivityContext. Place reference only in Boss since Boss will be updated the most
 * current ActivityContext whenever a new activity is created.
 *
 * The Butler class needs to handle more responsibility for cleaning, clearing, and checking for
 * data models; reference point and all that. Workers should not hold reference points for data. As
 * well, the Butler should be able to check on the status of any running threads so need to create
 * a registry for running threads and push and pop them when they start and finish.
 *
 * Shutdown procedure from front to back is not implemented at all. Assuming that Android will take
 * care of shutting down processes is not a very clever idea. Implement shutdown procedures for all
 * Control classes and thread first.
 *
 *
 */
public class Boss extends Application{

/**************************************************************************************************/

    //Context of the UI Activity (In this case we are only using one Activity)
    Context mActivityContext;
    //Butler class the will take care of preparing Movie data for consumption
    MovieButler mButler;
    //mMovieRequest - current list of movies requested
    private int mMovieRequest;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void setActivityContext(Context) - this method is called at in the onCreate of an Activity
 * @param ctx - Activity context
 */
    public void setActivityContext(Context ctx){
        //set Activity context - in this case there is only MainActivity
        mActivityContext = ctx;

        //check if Butler is awake
        if(mButler == null){
            //initialize MovieButler
            mButler = new MovieButler(this);
        }

        mButler.setActivityContext(ctx);
    }

/**
 * MovieJSON getModel(int) - request access to the JSON movie data
 * @param request - type of movie data requested (most popular or highest rated)
 * @return - an array list of movie data
 */
    public MovieJSON getMovies(int request){
        //check if there is data or if it is a new request
        if(mButler.getModel() == null || mMovieRequest != request){
            //record current movie request
            mMovieRequest = request;
            //request Butler to get data (will activate an AsyncTask execute
            mButler.requestMovies(request);
            //let requester know there is not any data yet
            return null;
        }

        //return movie data
        return mButler.getModel();
    }

    public void clearMovies(){
        mButler.stopWorking();
        mButler.clearModel();
    }


/**
 * void downloadMovieDataComplete() - communication channel used by Butlers to let the Boss
 * know when an AsyncTask thread has completed (in this case for MovieData)
 */
    public void downloadMovieDataComplete(){
        //instantiate HouseKeeper that will maintain the Main Activity
        MainKeeper keeper = (MainKeeper)mHouseKeeperRegistry.get(MainHelper.NAME_ID);
        //tells the HouseKeeper to prepare the fragments the Activity will use
        keeper.displayFragment();
    }


/**************************************************************************************************/


/**************************************************************************************************/

    //mHouseKeeperRegistry - HashMap of instantiated HouseKeeper classes being used by the Boss
    private HashMap<Integer, MyHouseKeeper> mHouseKeeperRegistry = new HashMap<>();

    //mMaidRegistry - HashMap of instantiated Maid classes being used by HouseKeepers
    private HashMap<Integer, MyMaid> mMaidRegistry = new HashMap<>();

/**************************************************************************************************/
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
     * MyMaid getMaid(String) - request a reference to a Maid
     * @param key - Maid name
     * @return - sends Maid reference
     */
    public MyMaid getMaid(Integer key){
        //send Maid
        return mMaidRegistry.get(key);
    }

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
     * MyHouseKeeper getHouseKeeper(String) - request a reference to a HouseKeeper
     * @param key - HouseKeeper Name
     * @return - sends HouseKeeper reference
     */
    public MyHouseKeeper getHouseKeeper(Integer key){

        MyHouseKeeper houseKeeper = mHouseKeeperRegistry.get(key);

        if(houseKeeper == null){
            houseKeeper = startHouseKeeper(key);
        }


        return houseKeeper;
    }

    private MyHouseKeeper startHouseKeeper(Integer key){
        if(key == MainHelper.NAME_ID){
            MainKeeper keeper = new MainKeeper(this);
            mHouseKeeperRegistry.put(key, keeper);
            return keeper;
        }

        return null;
    }

    public Context getActivityContext(){
        return mActivityContext;
    }

    public boolean hasConnectivity(){
        //get Connectivity Manger
        ConnectivityManager connMgr = (ConnectivityManager)
                mActivityContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get access to network information from phone
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //check if we have connection
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else{
            return false;
        }
    }

}
