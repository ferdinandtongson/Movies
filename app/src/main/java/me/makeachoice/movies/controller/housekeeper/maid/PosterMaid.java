package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.makeachoice.movies.controller.housekeeper.GridAutofitLayoutManager;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.controller.housekeeper.maid.staff.PosterStaff;
import me.makeachoice.movies.controller.housekeeper.recycler.PosterRecycler;
import me.makeachoice.movies.fragment.PosterFragment;
import me.makeachoice.movies.model.json.MovieJSON;

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
 *      PosterStaff - prepares data model for consumption for the Poster View
 *
 * Variables from MyMaid:
 *      String mName
 *      Fragment mFragment
 *
 * Methods from MyMaid:
 *      void initFragment()
 *
 * Implements PosterFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 * Implements PosterRecycler.Bridge
 *      Context getActivityContext()
 *
 * Bridge Interface:
 *      Context getActivityContext()
 *      void registerFragment(String, Fragment)
 *
 */
public class PosterMaid extends MyMaid implements PosterFragment.Bridge, PosterRecycler.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      PosterHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      Bridge mBridge - class implementing Bridge interface
 *      PosterRecycler mRecycler - manages item views for the RecyclerView used in the Fragment
 *      PosterStaff mStaff - processes data to be consumed by the Fragment
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private PosterHelper.ViewHolder mViewHolder;

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    private Bridge mBridge;

    //mRecycler - manages item views for the RecyclerView used in the Fragment
    private PosterRecycler mRecycler;

    //mStaff - processes data to be consumed by the Fragment
    private PosterStaff mStaff;


    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
        //registers the Fragment the Maid class is responsible for
        void registerFragment(String key, Fragment fragment);
        //notify HouseKeeper a poster has been selected
        void onSelectedPoster(int position);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 * @param name - name given to PosterMaid
 */
    public PosterMaid(Bridge bridge, String name){
        //class implementing Bridge interface
        mBridge = bridge;

        //service name given to PosterMaid
        mName = name;

        //initialize fragment to be maintained
        initFragment();

        //initialize Staff
        mStaff = new PosterStaff();

        //initialize RecyclerView Adapter
        mRecycler = new PosterRecycler(this);

        //initialize ViewHolder
        mViewHolder = new PosterHelper.ViewHolder();

        //ViewHolder is empty
        mViewHolder.isEmpty = true;

        //registers fragment PosterMaid is assigned to maintain
        mBridge.registerFragment(name, mFragment);

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
 * Context getActivityContext() - get current Activity context
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
    protected Fragment initFragment(){
        //create PosterFragment
        PosterFragment fragment = new PosterFragment();

        //send Maid name to fragment
        fragment.setServiceName(mName);

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

        //inflate fragment from the xml fragment layout resource file
        View v = inflater.inflate(PosterHelper.POSTER_FRAGMENT_LAYOUT_ID, container, false);

        //return fragment
        return v;
    }

/**
 * void createActivity(Bundle, View) - is called by PosterFragment when onCreateActivity(...)
 * is called in that class. Sets child views in fragment before being seen by the user
 * @param savedInstanceState - saved instance states
 * @param layout - layout where child views reside
 */
    public void createActivity(Bundle savedInstanceState, View layout){
        if(mViewHolder.isEmpty){
            //get RecyclerView
            mViewHolder.recycler = (RecyclerView)layout.findViewById(PosterHelper.POSTER_REC_ID);

            //ViewHolder is no longer empty
            mViewHolder.isEmpty = false;
        }

        //setHasFixedSize to true because 1)is true and 2)for optimization
        mViewHolder.recycler.setHasFixedSize(true);

        //set onItemTouchListener for items in RecyclerView
        //TODO - need to fix and relocate onItemClick event logic
        mViewHolder.recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(mBridge.getActivityContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.d("Movies", "PosterFragment.onItemClick: " + position);
                                mBridge.onSelectedPoster(position);
                            }
                        })
        );

        //create LayoutManager for RecyclerView, in this case a Grid type LayoutManager
        //TODO - need to change 240 to a dynamic variable
        GridAutofitLayoutManager manager =
                new GridAutofitLayoutManager(mBridge.getActivityContext(), 240);

        //set layout manager of RecyclerView
        mViewHolder.recycler.setLayoutManager(manager);

        //set RecyclerAdapter of RecyclerView
        mViewHolder.recycler.setAdapter(mRecycler);
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
 * void updatePosters(MovieJSON) - called by HouseKeep to inform Maid that changes to the data
 * being displayed in the fragment has occurred.
 * @param model - MovieJSON data model
 */
    public void updatePosters(MovieJSON model){

        mStaff.consumeModel(model);

        mRecycler.setPosters(mStaff.getPosterItems());
    }

/**************************************************************************************************/
}
