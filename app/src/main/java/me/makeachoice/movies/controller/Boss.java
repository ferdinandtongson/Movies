package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;

import me.makeachoice.movies.controller.butler.MovieButler;
import me.makeachoice.movies.controller.housekeeper.HouseKeeper;
import me.makeachoice.movies.controller.maid.Maid;
import me.makeachoice.movies.model.MovieModel;

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
    }

    public MovieModel getModel(){
        return mButler.getModel();
    }

/**************************************************************************************************/


/**************************************************************************************************/




    private HashMap<String, Maid> mMaidRegistry = new HashMap<>();
    public void registerMaid(String key, Maid maid){
        mMaidRegistry.put(key, maid);
    }

    public Maid getMaid(String key){
        return mMaidRegistry.get(key);
    }


    private HashMap<String, HouseKeeper> mHouseKeeperRegistry = new HashMap<>();
    public void registerHouseKeeper(String key, HouseKeeper keeper) {
        mHouseKeeperRegistry.put(key, keeper);
    }

}
