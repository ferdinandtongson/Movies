package me.makeachoice.movies.controller.housekeeper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.assistant.FragmentAssistant;
import me.makeachoice.movies.controller.housekeeper.assistant.MaidAssistant;

/**
 * MyHouseKeeper abstract class - used to directly communicate with Activities, MyMaid classes and
 * the Boss
 *
 */
public abstract class MyHouseKeeper {
    //mBoss - application context object
    protected Boss mBoss;

    //mFragmentManager - manages fragments for the MyHouseKeeper
    protected FragmentManager mFragmentManager;

    //mMaidAssistant - initializes and registers all Maids used by the HouseKeeper
    protected MaidAssistant mMaidAssistant;

    //mFragAssistant - assists in the transitions between fragments
    protected FragmentAssistant mFragAssistant;

    //mFragmentRegistry - registered fragments used by the HouseKeeper
    protected HashMap<Integer, Fragment> mFragmentRegistry;

}
