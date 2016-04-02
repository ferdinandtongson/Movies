package me.makeachoice.movies.controller.butler;

import android.content.Context;

import me.makeachoice.movies.controller.Boss;

/**
 * MyButler abstract class - used to process and prepare data, requested by the Boss, for
 * consumption
 */
public abstract class MyButler {
    Boss mBoss;
    Context mActivityContext;

    public Context getActivityContext(){
        return mActivityContext;
    }

    abstract public void workComplete(Boolean result);
}
