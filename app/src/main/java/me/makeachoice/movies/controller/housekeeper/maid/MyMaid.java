package me.makeachoice.movies.controller.housekeeper.maid;

import android.support.v4.app.Fragment;

import me.makeachoice.movies.controller.housekeeper.MyHouseKeeper;

/**
 * MyMaid abstract class - used to directly communicate with Fragments and with the managing
 * MyHouseKeeper class
 */
public abstract class MyMaid {
    MyHouseKeeper mHouseKeeper;
    String mName;
    Fragment mFragment;

    protected abstract Fragment initFragment();
    public abstract Fragment getFragment();

}
