package me.makeachoice.movies.controller.housekeeper.maid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.makeachoice.movies.controller.housekeeper.helper.EmptyHelper;
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
 * It uses other classes to assist in the upkeeping of the Fragment:
 *      EmptyFragment - handles the Fragment lifecycle
 *
 * Variables from MyMaid:
 *      Bridge mBridge
 *      Fragment mFragment
 *
 * Methods from MyMaid:
 *      abstract void initFragment()
 *
 * Bridge Interface from MyMaid:
 *      Context getActivityContext()
 *      void registerFragment(String, Fragment)
 *
 * Implements EmptyFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 */
public class EmptyMaid extends MyMaid implements EmptyFragment.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      EmptyHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      Bridge mBridge - class implementing Bridge interface
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private EmptyHelper.ViewHolder mViewHolder;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * EmptyMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 */
    public EmptyMaid(Bridge bridge, int id){

        //class implementing Bridge interface
        mBridge = bridge;

        //initialize fragment to be maintained
        mFragment = initFragment(id);

        //initialize ViewHolder
        mViewHolder = new EmptyHelper.ViewHolder();

        //registers fragment EmptyMaid is assigned to maintain
        mBridge.registerFragment(id, mFragment);

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
    protected Fragment initFragment(int id){
        //create PosterFragment
        EmptyFragment fragment = new EmptyFragment();

        //send Maid name to fragment
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

        //TODO - see if this can be put into ViewHolder
        //TODO - see if layoutID changes, for example when on portrait or landscape (if two files)
        //inflate fragment from the xml fragment layout resource file
        View v = inflater.inflate(EmptyHelper.EMPTY_FRAGMENT_LAYOUT_ID, container, false);

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

        //get RecyclerView from ViewHolder
        TextView txtMessage = (TextView)mViewHolder.getView(layout,
                EmptyHelper.EMPTY_TXT_ID);

        //set textView message
        txtMessage.setText(layout.getResources().
                getString(EmptyHelper.EMPTY_MSG_NO_NETWORK_ID));
    }

/**************************************************************************************************/

}
