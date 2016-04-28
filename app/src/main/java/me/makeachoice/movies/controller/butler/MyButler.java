package me.makeachoice.movies.controller.butler;

import android.content.Context;

import me.makeachoice.movies.controller.Boss;

/**
 * MyButler abstract class - used to process internet and db data and prepare it for consumption
 * by View
 */
public abstract class MyButler {

/**************************************************************************************************/
/**
 * Class Variables
 *      Boss mBoss - reference to Boss class
 *      Bridge mBridge - class implementing Bridge, typically a HouseKeeper class
 *      Fragment mFragment - fragment being maintained by the Maid
 *
 /**************************************************************************************************/

    //mBoss - application context that acts as a bridge between the Model and View
    Boss mBoss;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Abstract Methods:
 *      Context getActivityContext()
 *      void workComplete(Boolean)
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity Context
 * @return - activity context
 */
    abstract public Context getActivityContext();

/**
 * workComplete(Boolean) - called when an AsyncTask job has been completed
 * @param result - job completed or failed, true or false respectively
 */
    abstract public void workComplete(Boolean result);

/**************************************************************************************************/

}
