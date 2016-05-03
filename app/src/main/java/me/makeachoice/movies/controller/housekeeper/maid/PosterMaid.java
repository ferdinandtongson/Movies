package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.controller.housekeeper.adapter.RecyclerItemClickListener;
import me.makeachoice.movies.util.GridAutofitLayoutManager;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.controller.housekeeper.adapter.PosterRecycler;
import me.makeachoice.movies.view.fragment.PosterFragment;

/**
 * PosterMaid initializes and takes care of communicating with the Fragment that hold the
 * list of poster Movies the user can select from.
 *
 * Its main purpose is to upkeep and handle events and request from the Fragment and if the Maid
 * cannot handle a request or an event, it will pass it onto the HouseKeeper.
 *
 * The PosterMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * It uses other classes to assist in the upkeeping of the Fragment:
 *      PosterFragment - handles the Fragment lifecycle
 *      PosterRecycler - RecyclerView adapter used to display the list of movie posters
 *      PosterHelper - holds all static resources (layout id, view ids, etc)
 *
 * Variables from MyMaid:
 *      Bridge mBridge
 *      Fragment mFragment
 *
 * Methods from MyMaid:
 *      void initFragment()
 *
 * Bridge Interface from MyMaid:
 *      Context getActivityContext()
 *      void registerFragment(String, Fragment)
 *      int getOrientation()
 *
 * Implements PosterFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 * Implements PosterRecycler.Bridge
 *      Context getActivityContext()
 *
 */
public class PosterMaid extends MyMaid implements PosterFragment.Bridge, PosterRecycler.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      PosterHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      Bridge mBridge - class implementing Bridge interface
 *      PosterRecycler mRecycler - manages item views for the RecyclerView used in the Fragment
 *
 * Extends Bridge Interface:
 *      void onSelectedPoster(int)
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private PosterHelper.ViewHolder mViewHolder;

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    private Bridge mBridge;

    //mRecycler - manages item views for the RecyclerView used in the Fragment
    private PosterRecycler mRecycler;

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge extends MyMaid.Bridge{
        //notify HouseKeeper a poster has been selected
        void onSelectedPoster(int id, int position);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 */
    public PosterMaid(Bridge bridge, int id){
        //get id number for maid instance
        mMaidId = id;

        //class implementing Bridge interface
        mBridge = bridge;

        //initialize fragment to be maintained
        mFragment = initFragment(id);

        //initialize RecyclerView Adapter
        mRecycler = new PosterRecycler(this);

        //initialize ViewHolder
        mViewHolder = new PosterHelper.ViewHolder();

        //registers fragment PosterMaid is assigned to maintain
        mBridge.registerFragment(id, mFragment);

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - required as part of PosterRecycler.Bridge interface
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context, implemented for
 * PosterRecycler.Bridge
 * @return - current Activity context
 */
    public Context getActivityContext(){
        return mBridge.getActivityContext();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Fragment related methods. Both createView(...) and createActivity(...) are called by
 * PosterFragment when onCreateView(...) and onCreateActivity(...) are called in that class.
 *
 */
/**************************************************************************************************/
/**
 * void initFragment() - initialize Fragment, give name of Maid to fragment
 */
    protected Fragment initFragment(int id){
        //create PosterFragment
        PosterFragment fragment = new PosterFragment();

        //send Maid id number to fragment
        fragment.setMaidId(id);

        //return fragment
        return fragment;
    }

/**
 * View createView(LayoutInflater, ViewGroup, Bundle) - is called by PosterFragment when
 * onCreateView(...) is called in that class. Prepares the Fragment View to be presented.
 * @param inflater - layoutInflater to inflate the xml fragment layout resource file
 * @param container - view that will hold the fragment view
 * @param savedInstanceState - saved instance states
 * @return - view of fragment is ready
 */
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState){

        //return fragment
        return inflater.inflate(PosterHelper.POSTER_FRAGMENT_LAYOUT_ID, container, false);
    }

/**
 * void createActivity(Bundle, View) - is called by PosterFragment when onCreateActivity(...)
 * is called in that class. Sets child views in fragment before being seen by the user
 * @param savedInstanceState - saved instance states
 * @param layout - layout where child views reside
 */
    public void createActivity(Bundle savedInstanceState, View layout){

        //get RecyclerView from ViewHolder
        RecyclerView recycler = (RecyclerView)mViewHolder.getView(layout,
                PosterHelper.POSTER_REC_ID);

        //setHasFixedSize to true because 1)is true and 2)for optimization
        recycler.setHasFixedSize(true);

        //set onItemTouchListener for items in RecyclerView
        //TODO - need to fix and relocate onItemClick event logic
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(mBridge.getActivityContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.d("Movies", "PosterFragment.onItemClick: " + position);
                                mBridge.onSelectedPoster(mMaidId, position);
                            }
                        })
        );

        //create LayoutManager for RecyclerView, in this case a Grid type LayoutManager
        //TODO - need to change 240 to a dynamic variable
        GridAutofitLayoutManager manager =
                new GridAutofitLayoutManager(mBridge.getActivityContext(), 240);

        //set layout manager of RecyclerView
        recycler.setLayoutManager(manager);

        //set RecyclerAdapter of RecyclerView
        recycler.setAdapter(mRecycler);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      updatePosters(MovieJSON)
 *      getActivityContext()
 */
/**************************************************************************************************/
/**
 * void updatePosters(PosterItem) - called by HouseKeep to inform Maid that changes to the data
 * being displayed in the fragment has occurred.
 * @param posters - list of PosterItem data
 */
    public void updatePosters(ArrayList<PosterItem> posters){

        //change movie posters being displayed
        mRecycler.setPosters(posters);

        //notify adapter that data has changed
        mRecycler.notifyDataSetChanged();
    }

/**************************************************************************************************/
}
