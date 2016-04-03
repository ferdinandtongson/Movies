package me.makeachoice.movies.controller.housekeeper.maid;

import android.widget.ListAdapter;

import me.makeachoice.movies.fragment.EmptyFragment;

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
 *
 * Implements EmptyFragment.Bridge - NONE used
 *      ListAdapter getListAdapter() - not used
 *      void setListAdapter(ListAdapter) - not used
 *      void onItemClick(...) - not used
 */
public class EmptyMaid extends MyMaid implements EmptyFragment.Bridge{

    private ListAdapter mListAdapter;

    private Bridge mBridge;
    public EmptyMaid(Bridge bridge){
        mBridge = bridge;
    }

    public interface Bridge{
        //Interface methods needed to be implemented by the instantiating class
        //is empty
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
