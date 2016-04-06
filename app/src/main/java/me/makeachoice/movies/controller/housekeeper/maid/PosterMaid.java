package me.makeachoice.movies.controller.housekeeper.maid;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;
import me.makeachoice.movies.fragment.PosterFragment;

/**
 * PosterMaid initializes and takes care of communicating with the Fragment that hold the
 * list of poster Movies the user can select from.
 *
 * Its main purpose is to handle events and request from the Fragment and if the Maid cannot handle
 * a request or an event, it will pass it onto the HouseKeeper.
 *
 * The PosterMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment) or below it (the View Items in
 * a ListView or GridView, for example).
 *
 * Variables from MyMaid:
 *      MyHouseKeeper mHouseKeeper
 *      String mName
 *      Fragment mFragment
 *
 * Implements PosterFragment.Bridge
 * //TODO - need to review implementations
 *      ListAdapter getListAdapter() - Fragments' access to the ListAdapter
 *      void setListAdapter(ListAdapter) - receives the ListAdapter from Boss to be displayed
 *      void onItemClick(...) - list item click event
 */
public class PosterMaid extends MyMaid implements PosterFragment.Bridge{

    private ListAdapter mListAdapter;
    public interface Bridge{
        //Interface methods needed to be implemented by the instantiating class
        ListAdapter requestPosterAdapter();
        void onItemClick(ListView l, View v, int position, long id);
        void registerFragment(String key, Fragment fragment);
    }

    private Bridge mBridge;
    public PosterMaid(Bridge bridge, String name){
        mBridge = bridge;
        mName = name;

        mBridge.registerFragment(name, getFragment());
    }

/**************************************************************************************************/
/**
 * Variables used for initializing Fragments
 */
/**************************************************************************************************/
    //LAYOUT_POSTER_FRAGMENT - layout id used by Poster Fragment
    private final static int LAYOUT_POSTER_FRAGMENT = R.layout.poster_fragment;
    //POSTER_CHILD_GRID_VIEW - child view in poster fragment layout, gridView child
    private final static int POSTER_CHILD_GRID_VIEW = R.id.gridview;
/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void initFragment() - initialize Fragment; set layout and child view ids and maid name
 */
    protected Fragment initFragment(){
        //create PosterFragment
        PosterFragment fragment = new PosterFragment();

        //send layout id to PosterFragment
        fragment.setLayout(LAYOUT_POSTER_FRAGMENT);

        //send child view id, gridView
        fragment.setGridViewId(POSTER_CHILD_GRID_VIEW);

        //send Maid name to fragment
        fragment.setServiceName(mName);

        return fragment;
    }

    public Fragment getFragment(){
        if(mFragment == null){
            mFragment = initFragment();
        }

        return mFragment;
    }

/**************************************************************************************************/


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * SimpleListFragment.Bridge interface implementation.
 *      ListAdapter getListAdapter() - Fragments' access to the ListAdapter
 *      void setListAdapter() - used to ensure that the Maid class uses the setListAdapter method
 *      void onItemClick() - list item click event
 */
/**************************************************************************************************/
/**
 * ListAdapter getListAdapter() - Fragment can get access to the ListAdapter
 * @return ListAdapter - returns a list adapter created by the Boss
 */
    public ListAdapter getListAdapter(){
        //checks if ListAdapter is null
        if(mListAdapter == null){
            //if null, request ListAdapter from Boss
            mListAdapter = mBridge.requestPosterAdapter();
        }

        //return list adapter to calling fragment
        return mListAdapter;
    }

/**
 * void setListAdapter(ListAdapter) - set the ListAdapter the fragment is going to use, if any.
 * @param adapter - ListAdapter to be consumed by the fragment
 */
    public void setListAdapter(ListAdapter adapter){
        mListAdapter = adapter;
    }


/**
 * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
 * @param position - list position of item clicked
 */
    public void onItemClick(ListView l, View v, int position, long id){
        //do not use
    }

/**************************************************************************************************/


    /**
     * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
     * @param position - list position of item clicked
     */
    public void onItemClick(int position){
        //TODO - temporary workaround for PosterFragment bug
        //temporary use of Bridge method as a work around for Empty PosterFragment bug
        mFragment = initFragment();
        ((MainKeeper)mBridge).onItemClick();
    }


}
