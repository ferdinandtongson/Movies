package me.makeachoice.movies.controller.butler;

import android.content.Context;

import me.makeachoice.movies.controller.Boss;

/**
 * MyButler abstract class - used to process and prepare data, requested by the Boss, for
 * consumption
 */
public abstract class MyButler {

    //mBoss - application context that acts as a bridge between the Model and View
    Boss mBoss;

    abstract public Context getActivityContext();

    abstract public void workComplete(Boolean result);
}
