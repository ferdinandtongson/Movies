package me.makeachoice.movies.controller.viewside.maid;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * MyMaid abstract class used to directly communicate with Fragments and with the managing
 * MyHouseKeeper class
 */
public abstract class MyMaid {

/**************************************************************************************************/
/**
 * Class Variables
 *      int mMaidId - id number of instance Maid
 *      Bridge mBridge - class implementing Bridge, typically a HouseKeeper class
 *      Fragment mFragment - fragment being maintained by the Maid
 *      View mLayout - fragment layout view holding the child views
 *
/**************************************************************************************************/

    //mMaidId - id number of instance Maid
    protected int mMaidId;

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    protected Bridge mBridge;

    //mFragment - fragment being maintained by the Maid
    protected Fragment mFragment;

    //mLayout - fragment layout view holding the child views
    protected View mLayout;

/**************************************************************************************************/
/**
 * Abstract Methods:
 *      initFragment()
 */
/**************************************************************************************************/
/**
 * void initFragment - initialize fragment class
 */
    protected abstract Fragment initFragment(int id);

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Bridge Interface:
 *      Context getActivityContext() - get current Activity Context
 *      void registerFragment(Integer, Fragment) - register fragment Maid is maintaining
 */
/**************************************************************************************************/

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
        //Interface methods needed to be implemented by the instantiating class
        void registerFragment(Integer key, Fragment fragment);
        //get orientation value
        int getOrientation();
    }

/**************************************************************************************************/

}
