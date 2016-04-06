package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.HashMap;

import me.makeachoice.movies.controller.butler.MovieButler;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;
import me.makeachoice.movies.controller.housekeeper.MyHouseKeeper;
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
 * Communication Bridges across the whole HouseKeeping staff needs to be remodeled. Base class
 * Bridges do not work but there needs to be a standard system of communication. Maybe use a
 * HashMap message registry with public static message codes or something......
 *
 * FragmentManagement is a mess. With Activity Lifecycle and Fragment Lifecycle being a bit wonky,
 * a standard way of how an Activity communicates with a Fragment needs to be establish; for example
 * isSafeToCommitFragment() method. Should be placed in a BaseClass, Interface or communication
 * Bridge.
 *
 * Did I mention that the Fragment Lifecycle is a bit wonky!! Trace the fragment lifecycle to check
 * for null ListAdapters, Container Layouts, Child Views, running threads, etc.
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
 * HouseKeeper is a mess. Create a MaidAssistant class to do all the initialization and shutdown of
 * the Maids. Look into moving more code into the MainFragmentAssistant class to deal with fragment
 * transitions; prepareFragment() should be placed there or 90% of the code at least.
 *
 * Zombie Fragment bug will be a problem going forward. Either remove fragment registry and keep
 * instantiating the fragment every time (which defeats the whole point of having a fragment in the
 * first place) or I'm not understanding something. Remember to debug around onActivityCreated in
 * the Fragment class whenever a Zombie Fragment is created!!
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
        MainKeeper keeper = (MainKeeper)mHouseKeeperRegistry.get(MainKeeper.NAME);
        //tells the HouseKeeper to prepare the fragments the Activity will use
        keeper.prepareFragment();
    }


/**************************************************************************************************/


/**************************************************************************************************/

    //mHouseKeeperRegistry - HashMap of instantiated HouseKeeper classes being used by the Boss
    private HashMap<String, MyHouseKeeper> mHouseKeeperRegistry = new HashMap<>();

    //mMaidRegistry - HashMap of instantiated Maid classes being used by HouseKeepers
    private HashMap<String, MyMaid> mMaidRegistry = new HashMap<>();

/**************************************************************************************************/
    /**
     * void registerMaid(String, MyMaid) - registers Maids being used by HouseKeepers
     * @param key - Maid name
     * @param maid - Maid class (in charge of maintaining fragments)
     */
    public void registerMaid(String key, MyMaid maid){
        //register Maid
        mMaidRegistry.put(key, maid);
    }

    /**
     * MyMaid getMaid(String) - request a reference to a Maid
     * @param key - Maid name
     * @return - sends Maid reference
     */
    public MyMaid getMaid(String key){
        //send Maid
        return mMaidRegistry.get(key);
    }

    /**
     * void registerHouseKeeper(String, MyHouseKeeper) - registers HouseKeepers used by the Boss
     * @param key - HouseKeeper name
     * @param keeper - HouseKeeper class (in charge of Activity objects)
     */
    public void registerHouseKeeper(String key, MyHouseKeeper keeper){
        //register HouseKeeper
        mHouseKeeperRegistry.put(key, keeper);
    }

    /**
     * MyHouseKeeper getHouseKeeper(String) - request a reference to a HouseKeeper
     * @param key - HouseKeeper Name
     * @return - sends HouseKeeper reference
     */
    public MyHouseKeeper getHouseKeeper(String key){ return mHouseKeeperRegistry.get(key);}

}
