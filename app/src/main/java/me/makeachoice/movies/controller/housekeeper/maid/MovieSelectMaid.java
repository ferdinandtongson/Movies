package me.makeachoice.movies.controller.housekeeper.maid;

import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import me.makeachoice.movies.fragment.SimpleGridFragment;

/**
 * MovieSelectMaid initializes and takes care of communicating with the Fragment that hold the
 * list of Movies the user can select from.
 *
 * Its main purpose is to handle events and request from the Fragment and if the Maid cannot handle
 * a request or an event, it will pass it onto the HouseKeeper.
 *
 * The MovieSelectMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment) or below it (the View Items in
 * a ListView or GridView, for example).
 *
 * Variables from MyMaid:
 *      MyHouseKeeper mHouseKeeper
 *
 * Implements SimpleGridFragment.Bridge
 * //TODO - need to review implementations
 *      ListAdapter getListAdapter() - Fragments' access to the ListAdapter
 *      void setListAdapter(ListAdapter) - receives the ListAdapter from Boss to be displayed
 *      void onItemClick(...) - list item click event
 */
public class MovieSelectMaid extends MyMaid implements SimpleGridFragment.Bridge{

    private ListAdapter mListAdapter;

    private Bridge mBridge;
    public MovieSelectMaid(Bridge bridge){
        mBridge = bridge;

    }

    public interface Bridge{
        //Interface methods needed to be implemented by the instantiating class
        ListAdapter getListAdapter();
        void onItemClick(ListView l, View v, int position, long id);
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
        //checks if ListAdapter is null
        if(mListAdapter == null){
            //if null, request ListAdapter from Boss
            mListAdapter = mBridge.getListAdapter();
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
        Log.d("SimpleListFragment", "Maid.onListItemClick");
        mBridge.onItemClick(l, v, position, id);
        //TODO - need to connect onItemClick event to Boss
    }

/**************************************************************************************************/


    /**
     * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
     * @param position - list position of item clicked
     */
    public void onItemClick(int position){
        Log.d("SimpleListFragment", "Maid.onListItemClick");

        //TODO - need to connect onItemClick event to Boss
    }


}
