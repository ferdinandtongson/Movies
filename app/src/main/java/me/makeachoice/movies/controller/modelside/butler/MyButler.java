package me.makeachoice.movies.controller.modelside.butler;

import java.util.ArrayList;

import me.makeachoice.movies.controller.Boss;

/**
 * MyButler abstract class - used to process internet data model objects
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


}
