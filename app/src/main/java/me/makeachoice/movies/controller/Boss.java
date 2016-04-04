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
            mButler = new MovieButler(mActivityContext, this);
        }
    }

/**
 * MovieJSON getModel(int) - request access to the JSON movie data
 * @param request - type of movie data requested (most popular or highest rated)
 * @return - an array list of movie data
 */
    public MovieJSON getModel(int request){
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

    public boolean hasConnectivity(Context ctx){
        //get Connectivity Manger
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

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
