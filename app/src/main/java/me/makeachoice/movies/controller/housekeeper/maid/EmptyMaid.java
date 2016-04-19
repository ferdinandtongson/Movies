package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.makeachoice.movies.controller.housekeeper.page.EmptyPage;
import me.makeachoice.movies.fragment.EmptyFragment;

/**
 * EmptyMaid initializes and takes care of communicating with the Fragment that hold the
 * "Empty" message.
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
 * Methods from MyMaid:
 *      void initFragment()
 *      Fragment getFragment()
 *
 * Implements EmptyFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 * Bridge Interface:
 *      void registerFragment(String, Fragment)
 *
 */
public class EmptyMaid extends MyMaid implements EmptyFragment.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      Bridge mBridge - class implementing Bridge interface
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    private Bridge mBridge;

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
        //Interface methods needed to be implemented by the instantiating class
        void registerFragment(String key, Fragment fragment);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * EmptyMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 * @param name - name given to PosterMaid
 */
    public EmptyMaid(Bridge bridge, String name){

        //class implementing Bridge interface
        mBridge = bridge;

        //service name given to PosterMaid
        mName = name;

        //registers fragment PosterMaid is assigned to maintain
        mBridge.registerFragment(name, getFragment());

        //initialize fragment to be maintained
        initFragment();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      - None -
 *
 * Setters:
 *      - None -
 */
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
    protected void initFragment(){
        //create PosterFragment
        EmptyFragment fragment = new EmptyFragment();

        //send Maid name to fragment
        fragment.setServiceName(mName);

        //save fragment as class variable
        //TODO - check if needed
        mFragment = fragment;
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
        View v = inflater.inflate(EmptyPage.EMPTY_FRAGMENT_LAYOUT_ID, container, false);

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

        //create the textView that will display a message
        TextView txtMessage = (TextView)layout.findViewById(EmptyPage.EMPTY_TXT_ID);

        //set textView message
        txtMessage.setText("Empty Message");
    }



    //TODO - check if can be removed
    public Fragment getFragment(){
        if(mFragment == null){
            initFragment();
        }

        return mFragment;
    }

/**************************************************************************************************/

}
