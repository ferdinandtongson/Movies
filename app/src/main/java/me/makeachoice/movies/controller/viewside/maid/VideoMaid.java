package me.makeachoice.movies.controller.viewside.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.viewside.adapter.RecyclerItemClickListener;
import me.makeachoice.movies.controller.viewside.adapter.VideoRecycler;
import me.makeachoice.movies.controller.viewside.helper.VideoHelper;
import me.makeachoice.movies.util.GridAutofitLayoutManager;
import me.makeachoice.movies.util.NetworkManager;
import me.makeachoice.movies.view.fragment.ReviewFragment;
import me.makeachoice.movies.model.item.VideoItem;

/**
 * VideoMaid initializes and takes care of communicating with the Fragment that hold the list of
 * trailer videos for a given movie
 *
 * Its main purpose is to upkeep and handle events and request from the Fragment and if the Maid
 * cannot handle a request or an event, it will pass it onto the HouseKeeper.
 *
 * The VideoMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * It uses other classes to assist in the upkeep of the Fragment:
 *      ReviewFragment - handles the Fragment lifecycle
 *      VideoRecycler - RecyclerView adapter used to display the list of videos
 *      VideoHelper - holds all static resources (layout id, view ids, etc)
 *
 * Variables from MyMaid:
 *      int mMaidId
 *      Bridge mBridge
 *      Fragment mFragment
 *      View mLayout
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
public class VideoMaid extends MyMaid implements ReviewFragment.Bridge, VideoRecycler.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      VideoHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      Bridge mBridge - extends MyMaid.Bridge interface
 *      ReviewRecycler mRecycler - manages item views for the RecyclerView used in the Fragment
 *
 * Extends Bridge Interface:
 *      void onVideoClicked(int)
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private VideoHelper.ViewHolder mViewHolder;

    //mBridge - extends MyMaid.Bridge, typically a MyHouseKeeper class
    private Bridge mBridge;

    //mRecycler - manages item views for the RecyclerView used in the Fragment
    private VideoRecycler mRecycler;

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge extends MyMaid.Bridge{
        //notifies HouseKeeper a video has been clicked
        void onVideoClicked(int position);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 */
    public VideoMaid(Bridge bridge, int id){
        //get id number for maid instance
        mMaidId = id;

        //class implementing Bridge interface
        mBridge = bridge;

        //initialize fragment to be maintained
        mFragment = initFragment(id);

        //initialize RecyclerView Adapter
        mRecycler = new VideoRecycler(this);

        //initialize ViewHolder
        mViewHolder = new VideoHelper.ViewHolder();

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
 *      void setVideos(ArrayList<VideoItem>) - set data to be displayed by the RecyclerView
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context, implemented for
 * ReviewRecycler.Bridge
 * @return - current Activity context
 */
    public Context getActivityContext(){
        return mBridge.getActivityContext();
    }

/**
 * void setVideos(ArrayList<VideoItem>) - set data to be displayed by the RecyclerView
 * @param videos - list of video data
 */
    public void setVideos(ArrayList<VideoItem> videos){
        mRecycler.setVideos(videos);
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
        return inflater.inflate(VideoHelper.VIDEO_FRAGMENT_LAYOUT_ID, container, false);
    }

/**
 * void createActivity(Bundle, View) - is called by ReviewFragment when onCreateActivity(...)
 * is called in that class. Sets child views in fragment before being seen by the user
 * @param savedInstanceState - saved instance states
 * @param layout - layout where child views reside
 */
    public void createActivity(Bundle savedInstanceState, View layout){

        //get fragment layout
        mLayout = layout;

        //prepare "No Data" TextView
        prepareNoDataTextView(mLayout);

        //check if there is data to display
        displayNoData(mRecycler.getItemCount());

        //prepare RecyclerView
        prepareRecycler(mLayout);
    }

/**
 * void prepareNoDataTextView(View) - prepares the textView to display "No Data"
 * @param layout - layout view holding the textView as a child view
 */
    private void prepareNoDataTextView(View layout){
        //get "No Data" TextView from ViewHolder
        TextView txtNoData = (TextView)mViewHolder
                .getView(layout, VideoHelper.VIDEO_TXT_NO_DATA_ID);

        //set "No Data" text in textView
        txtNoData.setText(mBridge.getActivityContext().getString(VideoHelper.STR_NO_DATA_ID));
    }

/**
 * void prepareRecycler - prepares the recyclerView to display Video data
 * @param layout - layout view holding the recyclerView as a child view
 */
    private void prepareRecycler(View layout){
        //get RecyclerView from ViewHolder
        RecyclerView recVideo = (RecyclerView)mViewHolder.getView(layout,
                VideoHelper.VIDEO_REC_ID);

        //setHasFixedSize to true because 1)is true and 2)for optimization
        recVideo.setHasFixedSize(true);

        //set onItemTouchListener for items in RecyclerView
        recVideo.addOnItemTouchListener(
                new RecyclerItemClickListener(mBridge.getActivityContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //notify Bridge that a video has been selected
                                mBridge.onVideoClicked(position);
                            }
                        })
        );


        //create LayoutManager for RecyclerView, in this case a list type LayoutManager
        GridAutofitLayoutManager manager =
                new GridAutofitLayoutManager(mBridge.getActivityContext(), 240);

        //set layout manager of RecyclerView
        recVideo.setLayoutManager(manager);

        //set RecyclerAdapter of RecyclerView
        recVideo.setAdapter(mRecycler);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      void updateVideos(ArrayList<VideoItem>)
 *      void displayNoData(int)
 */
/**************************************************************************************************/
/**
 * void updateVideos(ReviewItem) - called by HouseKeep to inform Maid that changes to the data
 * being displayed in the fragment has occurred.
 * @param videos - list of VideoItem data
 */
    public void updateVideos(ArrayList<VideoItem> videos){

        if(mLayout != null){
            //if layout not null, check if there is data to display
            displayNoData(videos.size());
        }

        //change movie trailers being displayed
        mRecycler.setVideos(videos);

        //notify adapter that data has changed
        mRecycler.notifyDataSetChanged();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      void displayNoData(int) - displays "No Data" message to user if there are no videos
 *      String noDataMessage() - type of "No Data" message to display
 */
/**************************************************************************************************/
/**
 * void displayNoData(int) - displays "No Data" message to user if there are no videos and hid
 * "No Data" display if there are videos to display
 * @param count - number of videos to display
 */
    private void displayNoData(int count){
        //get "No Data" TextView from ViewHolder
        TextView txtNoData = (TextView)mViewHolder
                .getView(mLayout, VideoHelper.VIDEO_TXT_NO_DATA_ID);

        //check if there are any reviews
        if(count == 0){
            //no posters, set message "No Data" message
            txtNoData.setText(noDataMessage());
            //display "No Data" text
            txtNoData.setVisibility(View.VISIBLE);
        }
        else{
            //have posters, hid "No Data" text
            txtNoData.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * String noDataMessage() - type of "no Data" message to display. If there is no network, will
     *  display "No Network Connection" and if it is simple no data will display "No Data to Display"
     * @return - type of string message to display
     */
    private String noDataMessage(){

        //check if we have connection
        if(NetworkManager.hasConnection(mBridge.getActivityContext())) {
            //we have connection just no data
            return mBridge.getActivityContext().getString(R.string.str_no_data);
        }
        else{
            //we have no network connection
            return mBridge.getActivityContext().getString(R.string.str_no_network);
        }

    }

/**************************************************************************************************/

}
