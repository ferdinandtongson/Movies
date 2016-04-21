package me.makeachoice.movies.controller.housekeeper.maid;

import android.support.v4.app.Fragment;


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

}
