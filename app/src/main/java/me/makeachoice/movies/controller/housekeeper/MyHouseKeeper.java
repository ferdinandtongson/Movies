package me.makeachoice.movies.controller.housekeeper;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;

/**
 * MyHouseKeeper abstract class - used to directly communicate with Activities and Maid classes that
 * maintain Fragment classes and the Boss
 *
 */
public abstract class MyHouseKeeper {
    //mBoss - application context object
    Boss mBoss;

    //mActivityContext - context object that follows the Activity lifecycle
    Context mActivityContext;

    //mFragmentManager - manages fragments for the MyHouseKeeper
    FragmentManager mFragmentManager;

    //mMapFragmentType - hashmap used to hold different fragments types and their type variations
    HashMap<String, Integer> mMapFragmentType;

}
