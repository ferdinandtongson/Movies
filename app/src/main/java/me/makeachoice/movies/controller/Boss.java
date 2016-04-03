package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
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

    Context mActivityContext;

    MovieButler mButler;
    public void setActivityContext(Context ctx){
        Log.d("Movies", "Boss.setActivityContext");
        mActivityContext = ctx;

        if(mButler == null){
            //TODO - need to seperate http call
            Log.d("Movies", "     Butler null");
            mButler = new MovieButler(mActivityContext, this);
        }
    }

    public MovieJSON getModel(){

        if(mButler.getModel() == null && mButler.hasHttpConnection()){
            mButler.requestPopularMovies();
            return null;
        }

        return mButler.getModel();
    }

    public void xxxComplete(){
        Log.d("Movies", "Boss.xxxComplete");
        MainKeeper keeper = (MainKeeper)mHouseKeeperRegistry.get(MainKeeper.NAME);
        keeper.prepareFragment();
    }

/**************************************************************************************************/


/**************************************************************************************************/

    private HashMap<String, MyMaid> mMaidRegistry = new HashMap<>();
    public void registerMaid(String key, MyMaid maid){
        mMaidRegistry.put(key, maid);
    }

    public MyMaid getMaid(String key){
        return mMaidRegistry.get(key);
    }


    private HashMap<String, MyHouseKeeper> mHouseKeeperRegistry = new HashMap<>();
    public void registerHouseKeeper(String key, MyHouseKeeper keeper) {
        mHouseKeeperRegistry.put(key, keeper);
    }

    public MyHouseKeeper getHouseKeeper(String key){ return mHouseKeeperRegistry.get(key);}

}
