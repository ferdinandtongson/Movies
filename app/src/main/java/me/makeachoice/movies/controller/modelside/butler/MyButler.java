package me.makeachoice.movies.controller.modelside.butler;

import android.content.Context;

import java.util.ArrayList;

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
 *      Boolean mWorking - boolean value to check if a Worker is working in the background
 *      ArrayList<Integer> mRequestBuffer - pending AsyncTask Movie request
 *
 /**************************************************************************************************/

    //mBoss - application context that acts as a bridge between the Model and View
    protected Boss mBoss;

    //mWorking - boolean value to check if a Worker is working in the background
    protected Boolean mWorking;

    //mBufferRequest - pending AsyncTask Movie requests
    protected ArrayList<Integer> mRequestBuffer;

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
