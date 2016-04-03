package me.makeachoice.movies.controller.housekeeper.maid;

import android.support.v4.app.Fragment;
import android.widget.ListAdapter;

import me.makeachoice.movies.R;
import me.makeachoice.movies.fragment.EmptyFragment;
import me.makeachoice.movies.fragment.PosterFragment;

/**
 * EmptyMaid initializes and takes care of communicating with the Fragment that hold the
 * "Empty" message when a simple fragment containing only a ListView or GridView is empty.
 *
 * Its main purpose is to display a simple "Empty" message.
 *
 * The EmptyMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * Variables from MyMaid:
 *      MyHouseKeeper mHouseKeeper
 *      String mName
 *      Fragment mFragment
 *
 * Implements EmptyFragment.Bridge - NONE used
 *      ListAdapter getListAdapter() - not used
 *      void setListAdapter(ListAdapter) - not used
 *      void onItemClick(...) - not used
 */
public class EmptyMaid extends MyMaid implements EmptyFragment.Bridge{

    private ListAdapter mListAdapter;
    public interface Bridge{
        //Interface methods needed to be implemented by the instantiating class
        void registerFragment(String key, Fragment fragment);
    }

    private Bridge mBridge;
    public EmptyMaid(Bridge bridge, String name){

        mBridge = bridge;
        mName = name;

        mBridge.registerFragment(name, getFragment());
    }

/**************************************************************************************************/
/**
 * Variables used for initializing Fragments
 */
/**************************************************************************************************/
    //LAYOUT_EMPTY_FRAGMENT - layout id used by Empty Fragment
    private final static int LAYOUT_EMPTY_FRAGMENT = R.layout.empty_fragment;
    //EMPTY_CHILD_TEXT_VIEW - child view in empty fragment layout, textView child
    private final static int EMPTY_CHILD_TEXT_VIEW = R.id.txt_empty;
/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void initFragment() - initialize Fragment; set layout and child view ids and maid name
 */
    protected Fragment initFragment(){
        //create EmptyFragment
        EmptyFragment fragment = new EmptyFragment();

        //send layout id to EmptyFragment
        fragment.setLayout(LAYOUT_EMPTY_FRAGMENT);

        //send child view id, gridView
        fragment.setTextViewId(EMPTY_CHILD_TEXT_VIEW);

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
        return null;
    }

/**
 * void setListAdapter(ListAdapter) - set the ListAdapter the fragment is going to use, if any.
 * @param adapter - ListAdapter to be consumed by the fragment
 */
    public void setListAdapter(ListAdapter adapter){
        //is empty
    }

/**
 * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
 * @param position - list position of item clicked
 */
    public void onItemClick(int position){
        //is empty
    }

/**************************************************************************************************/


}
