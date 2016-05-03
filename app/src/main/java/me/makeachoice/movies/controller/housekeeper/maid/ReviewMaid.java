package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.makeachoice.movies.controller.housekeeper.adapter.RecyclerItemClickListener;
import me.makeachoice.movies.controller.housekeeper.adapter.ReviewRecycler;
import me.makeachoice.movies.controller.housekeeper.helper.ReviewHelper;
import me.makeachoice.movies.fragment.ReviewFragment;
import me.makeachoice.movies.model.item.ReviewItem;

/**
 * ReviewMaid initializes and takes care of communicating with the Fragment that hold the list of
 * reviews for a given movie
 *
 * Its main purpose is to upkeep and handle events and request from the Fragment and if the Maid
 * cannot handle a request or an event, it will pass it onto the HouseKeeper.
 *
 * The ReviewMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * It uses other classes to assist in the upkeep of the Fragment:
 *      ReviewFragment - handles the Fragment lifecycle
 *      ReviewRecycler - RecyclerView adapter used to display the list of reviews
 *      ReviewHelper - holds all static resources (layout id, view ids, etc)
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
 * Implements ReviewFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 * Implements ReviewRecycler.Bridge
 *      Context getActivityContext()
 *
 */
public class ReviewMaid extends MyMaid implements ReviewFragment.Bridge, ReviewRecycler.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      ReviewHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      Bridge mBridge - class implementing Bridge interface
 *      ReviewRecycler mRecycler - manages item views for the RecyclerView used in the Fragment
 *
 * Extends Bridge Interface:
 *      void onSelectedReview(int)
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private ReviewHelper.ViewHolder mViewHolder;

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    private Bridge mBridge;

    //mRecycler - manages item views for the RecyclerView used in the Fragment
    private ReviewRecycler mRecycler;

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge extends MyMaid.Bridge{
        //notify HouseKeeper that a review has been selected
        void onSelectedReview(int position);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 */
    public ReviewMaid(Bridge bridge, int id){
        //get id number for maid instance
        mMaidId = id;

        //class implementing Bridge interface
        mBridge = bridge;

        //initialize fragment to be maintained
        mFragment = initFragment(id);

        //initialize RecyclerView Adapter
        mRecycler = new ReviewRecycler(this);

        //initialize ViewHolder
        mViewHolder = new ReviewHelper.ViewHolder();

        //registers fragment PosterMaid is assigned to maintain
        mBridge.registerFragment(id, mFragment);

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - required as part of ReviewRecycler.Bridge interface
 *
 * Setters:
 *      void setReviews(ArrayList<ReviewItem> - set data to be displayed by the RecyclerView
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context, implemented for
 * ReviewRecycler.Bridge
 * @return - current Activity context
 */
    public Context getActivityContext(){
        //get current Activity context
        return mBridge.getActivityContext();
    }

/**
 * void setReviews(ArrayList<ReviewItem>) - set data to be displayed by the RecyclerView
 * @param reviews - list of review data
 */
    public void setReviews(ArrayList<ReviewItem> reviews){
        //set review data for recycler
        mRecycler.setReviews(reviews);
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
        //create ReviewFragment
        ReviewFragment fragment = new ReviewFragment();

        //send Maid id number to fragment
        fragment.setMaidId(id);

        //return fragment
        return fragment;
    }

/**
 * View createView(LayoutInflater, ViewGroup, Bundle) - is called by ReviewFragment when
 * onCreateView(...) is called in that class. Prepares the Fragment View to be presented.
 * @param inflater - layoutInflater to inflate the xml fragment layout resource file
 * @param container - view that will hold the fragment view
 * @param savedInstanceState - saved instance states
 * @return - view of fragment is ready
 */
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState){

        //return fragment
        return inflater.inflate(ReviewHelper.REVIEW_FRAGMENT_LAYOUT_ID, container, false);
    }

/**
 * void createActivity(Bundle, View) - is called by ReviewFragment when onCreateActivity(...)
 * is called in that class. Sets child views in fragment before being seen by the user
 * @param savedInstanceState - saved instance states
 * @param layout - layout where child views reside
 */
    public void createActivity(Bundle savedInstanceState, View layout){

        //get RecyclerView from ViewHolder
        RecyclerView recycler = (RecyclerView)mViewHolder.getView(layout,
                ReviewHelper.REVIEW_REC_ID);

        //setHasFixedSize to true because 1)is true and 2)for optimization
        recycler.setHasFixedSize(true);

        //set onItemTouchListener for items in RecyclerView
        //TODO - need to fix and relocate onItemClick event logic
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(mBridge.getActivityContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //notify Bridge that a review has been selected
                                mBridge.onSelectedReview(position);
                            }
                        })
        );


        //create LayoutManager for RecyclerView, in this case a list type LayoutManager
        LinearLayoutManager manager =
                new LinearLayoutManager(mBridge.getActivityContext());

        //set layout manager of RecyclerView
        recycler.setLayoutManager(manager);

        //set RecyclerAdapter of RecyclerView
        recycler.setAdapter(mRecycler);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      updateReviews(ReviewItem)
 */
/**************************************************************************************************/
/**
 * void updateReviews(ReviewItem) - called by HouseKeep to inform Maid that changes to the data
 * being displayed in the fragment has occurred.
 * @param reviews - list of ReviewItem data
 */
    public void updateReviews(ArrayList<ReviewItem> reviews){

        //change movie reviews being displayed
        mRecycler.setReviews(reviews);

        //notify adapter that data has changed
        mRecycler.notifyDataSetChanged();
    }

/**************************************************************************************************/
}
