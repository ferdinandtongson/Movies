package me.makeachoice.movies.controller.housekeeper.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;

/**
 * SwipeAdapter extends FragmentPagerAdapter and is used to display fragments as pages where users
 * can swipe between the different pages.
 *
 * FragmentPageAdapter is used because there is a fixed number of pages to be displayed.
 *
 * Methods from FragmentPagerAdapter:
 *      Fragment getItem(int)
 *      int getCount()
 *      CharSequence getPageTitle(int)
 *
 */
public class SwipeAdapter extends FragmentPagerAdapter {

/**************************************************************************************************/
/**
 * Class Variables
 *      int NUM_ITEMS - set number of fragments to be viewed
 *      ArrayList<Fragment> mFragments - list of fragments to be viewed
 *      ArrayList<String> mTitles - list of titles used for each fragment
 *      Bridge mBridge - class implementing Bridge interface, typically a Maid or HouseKeeper class
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //set number of fragments to be viewed
    static final int NUM_FRAGMENT = 5;

    //list of fragments to be viewed
    private ArrayList<Fragment> mFragments;

    //list of titles used for each fragment
    private ArrayList<String> mTitles;

    //mBridge - class implementing Bridge, typically a Maid or HouseKeeper class
    private Bridge mBridge;

    //Implemented communication line to a class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * SwipeAdapter - constructor
 * @param bridge - class implementing Bridge interface
 * @param fm - fragment manager
 * @param fragmentMap - hashMap containing fragments to be displayed
 */
    public SwipeAdapter(Bridge bridge, FragmentManager fm,
                        HashMap<Integer, Fragment> fragmentMap) {
        super(fm);

        //get Bridge
        mBridge = bridge;

        //add fragments to fragment list
        initializeFragmentList(fragmentMap);

        //add titles to title list
        initializeTitleList();

    }

/**************************************************************************************************/
/**
 * Initialization Methods:
 *      void initializeFragmentList(HashMap<Integer,Fragment>) - add fragments to fragment list
 *      void initializeTitleList() - add titles to title list
 */
/**************************************************************************************************/
/**
 * void initializeFragmentList(HashMap<Integer,Fragment>) - add fragments to fragment list
 * @param fragmentMap - hashMap containing fragments for the list
 */
    private void initializeFragmentList(HashMap<Integer, Fragment> fragmentMap){
        //initialize fragment array list
        mFragments = new ArrayList<>();

        //add Most Popular poster fragment
        mFragments.add(fragmentMap.get(PosterHelper.NAME_ID_MOST_POPULAR));
        //add Top Rated poster fragment
        mFragments.add(fragmentMap.get(PosterHelper.NAME_ID_TOP_RATED));
        //add Now Playing poster fragment
        mFragments.add(fragmentMap.get(PosterHelper.NAME_ID_NOW_PLAYING));
        //add Upcoming poster fragment
        mFragments.add(fragmentMap.get(PosterHelper.NAME_ID_UPCOMING));
        //add Favorite poster fragment
        mFragments.add(fragmentMap.get(PosterHelper.NAME_ID_FAVORITE));
    }

/**
 * void initializeTitleList() - add titles to title list
 */
    private void initializeTitleList(){
        //get context
        Context ctx = mBridge.getActivityContext();

        //initialize title array list
        mTitles = new ArrayList<>();

        //add Most Popular title
        mTitles.add(ctx.getString(PosterHelper.NAME_ID_MOST_POPULAR));
        //add Top Rated title
        mTitles.add(ctx.getString(PosterHelper.NAME_ID_TOP_RATED));
        //add Now Playing title
        mTitles.add(ctx.getString(PosterHelper.NAME_ID_NOW_PLAYING));
        //add Upcoming title
        mTitles.add(ctx.getString(PosterHelper.NAME_ID_UPCOMING));
        //add Favorite title
        mTitles.add(ctx.getString(PosterHelper.NAME_ID_FAVORITE));
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getItem() - get fragment currently being shown
 *      int getCount() - get number of items (fragments) in adapter
 *      CharSequence getPageTitle() - get page title of fragment being shown
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * Fragment getItem(int) - called when the user does a "swipe" action causing the next fragment
 * to be shown. Depending on how setOffscreenPageLimit() is defined (default 1), getItem will call
 * the current fragment and the fragments before (if any) and after the show fragment
 * @param i - current adapter position being shown
 * @return Fragment - fragment being called
 */
    @Override
    public Fragment getItem(int i) {
        //return fragment
        return mFragments.get(i);
    }

/**
 * int getCount() - get number of items (fragments) stored in the adapter
 * @return - int, number of items (fragments) stored in the adapter
 */
    @Override
    public int getCount() {
        return NUM_FRAGMENT;
    }

/**
 * CharSequence getPageTitle(int) - get the title of the current fragment being shown
 * @param position - index position of the fragment being shown
 * @return - CharSequence, title of the current fragment being shown
 */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

/**************************************************************************************************/

}