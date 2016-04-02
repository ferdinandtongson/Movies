package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;

import me.makeachoice.movies.controller.butler.MovieButler;
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
        mActivityContext = ctx;
        mButler = new MovieButler(mActivityContext);
        mButler.establishHttpConnection();
    }

    public MovieJSON getModel(){
        return mButler.getModel();
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

}
