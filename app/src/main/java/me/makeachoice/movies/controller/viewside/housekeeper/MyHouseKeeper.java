package me.makeachoice.movies.controller.viewside.housekeeper;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;

import me.makeachoice.movies.view.activity.MyActivity;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.assistant.MaidAssistant;

/**
 * MyHouseKeeper abstract class - used to directly communicate with Activities, MyMaid classes and
 * the Boss
 *
 */
public abstract class MyHouseKeeper {

/**************************************************************************************************/
/**
 * Class Variables:
 *      Boss mBoss - application context object
 *      FragmentManager mFragmentManager - manages fragments for the MyHouseKeeper
 *      MaidAssistant mMaidAssistant - initializes and registers all Maids used by the HouseKeeper
 *      HashMap<Integer, Fragment> mFragmentRegistry - registered fragments used by the HouseKeeper
 *      Toolbar mToolbar - activity toolbar
 *      FloatingActionButton mFab - floating action button
 */
/**************************************************************************************************/

    //mBoss - application context object
    protected Boss mBoss;

    //mFragmentManager - manages fragments for the MyHouseKeeper
    protected FragmentManager mFragmentManager;

    //mMaidAssistant - initializes and registers all Maids used by the HouseKeeper
    protected MaidAssistant mMaidAssistant;

    //mFragmentRegistry - registered fragments used by the HouseKeeper
    protected HashMap<Integer, Fragment> mFragmentRegistry;

    //mToolbar - activity toolbar
    protected Toolbar mToolbar;

    //mFab - floating action button
    protected FloatingActionButton mFab;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - return current Activity Context
 *      int getOrientation() - return current orientation (portrait = 1, landscape = 2)
 *      Toolbar getToolbar(MyActivity,int) - inflates and return toolBar object
 *      FloatingActionButton getFloatButton(MyActivity,int,View.OnClickListener) - inflates and
 *          return FloatingActionButton object
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - return current Activity Context
 * @return - activity context
 */
    public Context getActivityContext(){
        return mBoss.getActivityContext();
    }

/**
 * int getOrientation() - return current phone orientation (portrait = 1, landscape = 2)
 * @return - current phone orientation
 */
    public int getOrientation(){ return mBoss.getOrientation(); }

/**
 * Toolbar getToolbar(MyActivity,int) - inflates and return toolBar object
 * @param activity - current activity being shown to user
 * @param toolbarId - layout resource id of toolbar object
 * @return - toolBar object
 */
    public Toolbar getToolbar(MyActivity activity, int toolbarId){

        //toolbar is context sensitive, need to recreate every time Activity.onCreate is called
        Toolbar toolbar = (Toolbar)activity.findViewById(toolbarId);

        //set support for toolbar, onCreateOptionsMenu() will be called
        activity.setSupportActionBar(toolbar);

        return toolbar;
    }

/**
 * FloatingActionButton getFloatButton(MyActivity,int,View.OnClickListener) - inflates and return
 * FloatingActionButton object
 * @param activity - current activity being show to user
 * @param fabId - layout resource id of floatingActionButton object
 * @param listener - View.OnClickListener to listen for onClick events from fab
 * @return - floatingActionButton object
 */
    public FloatingActionButton getFloatButton(MyActivity activity, int fabId,
                                                View.OnClickListener listener){

        FloatingActionButton fab = (FloatingActionButton)activity.findViewById(fabId);

        fab.setOnClickListener(listener);

        return fab;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Method:
 *      void registerFragment(Integer,Fragment) - registers fragment to hashMap buffer
 */
/**************************************************************************************************/
/**
 * void registerFragment(Integer,Fragment) - registers fragment to hashMap buffer
 * @param id - id number used as key
 * @param fragment - fragment put into buffer
 */
    public void registerFragment(Integer id, Fragment fragment){
        if(mFragmentRegistry == null){
            mFragmentRegistry = new HashMap<>();
        }

        mFragmentRegistry.put(id, fragment);
    }

/**************************************************************************************************/

}
