package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.support.v4.app.Fragment;

import me.makeachoice.movies.controller.housekeeper.helper.EmptyHelper;


/**
 * MyMaid abstract class used to directly communicate with Fragments and with the managing
 * MyHouseKeeper class
 */
public abstract class MyMaid {

/**************************************************************************************************/
/**
 * Class Variables
 *      Fragment mFragment - fragment being maintained by the Maid
 *
/**************************************************************************************************/

    //mFragment - fragment being maintained by the Maid
    Fragment mFragment;

/**************************************************************************************************/
/**
 * Abstract Methods:
 *      initFragment()
 */
/**************************************************************************************************/
/**
 * void initFragment - initialize fragment class
 */
    protected abstract Fragment initFragment();

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Bridge Interface:
 *      Context getActivityContext() - get current Activity Context
 *      void registerFragment(Integer, Fragment) - register fragment Maid is maintaining
 */
/**************************************************************************************************/

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    protected Bridge mBridge;

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
        //Interface methods needed to be implemented by the instantiating class
        void registerFragment(Integer key, Fragment fragment);
    }

/**************************************************************************************************/

}
