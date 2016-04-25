package me.makeachoice.movies.controller.housekeeper;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;

import me.makeachoice.movies.MyActivity;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.assistant.FragmentAssistant;
import me.makeachoice.movies.controller.housekeeper.assistant.MaidAssistant;

/**
 * MyHouseKeeper abstract class - used to directly communicate with Activities, MyMaid classes and
 * the Boss
 *
 */
public abstract class MyHouseKeeper {
    //mBoss - application context object
    protected Boss mBoss;

    //mFragmentManager - manages fragments for the MyHouseKeeper
    protected FragmentManager mFragmentManager;

    //mMaidAssistant - initializes and registers all Maids used by the HouseKeeper
    protected MaidAssistant mMaidAssistant;

    //mFragAssistant - assists in the transitions between fragments
    protected FragmentAssistant mFragAssistant;

    //mFragmentRegistry - registered fragments used by the HouseKeeper
    protected HashMap<Integer, Fragment> mFragmentRegistry;

    //mToolbar - activity toolbar
    protected Toolbar mToolbar;

    //mFab - floating action button
    protected FloatingActionButton mFab;


    public Context getActivityContext(){
        return mBoss.getActivityContext();
    }

    /**
     * void registerFragment(Integer, Fragment) - register Fragments maintained by Maid classes
     * @param key - id number of Maid
     * @param fragment - fragment maintained by Maid
     */
    public void registerFragment(Integer key, Fragment fragment){
        //put fragment into hash map registry
        mFragmentRegistry.put(key, fragment);
    }

/**************************************************************************************************/
/**
 * Toolbar getToolbar() inflates the toolbar from the layout and then sets it into the Activity.
 */
    public Toolbar getToolbar(MyActivity activity, int toolbarId){

        //toolbar is context sensitive, need to recreate every time Activity.onCreate is called
        Toolbar toolbar = (Toolbar)activity.findViewById(toolbarId);

        //set support for toolbar, onCreateOptionsMenu() will be called
        activity.setSupportActionBar(toolbar);

        return toolbar;
    }

/**
 * FloatingActionButton getFloatButton() inflates the floating action button layout and sets Event
 * Listener
 */
    public FloatingActionButton getFloatButton(MyActivity activity, int fabId,
                                                View.OnClickListener listener){

        FloatingActionButton fab = (FloatingActionButton)activity.findViewById(fabId);

        fab.setOnClickListener(listener);

        return fab;
    }


}
