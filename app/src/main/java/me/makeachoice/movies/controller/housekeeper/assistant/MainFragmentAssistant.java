package me.makeachoice.movies.controller.housekeeper.assistant;

import android.util.Log;

import me.makeachoice.movies.R;
import me.makeachoice.movies.fragment.EmptyFragment;
import me.makeachoice.movies.fragment.PosterFragment;

/**
 * MainFragmentAssistant is to assist in managing Fragments for HouseKeeper class
 */
public class MainFragmentAssistant {

    public MainFragmentAssistant(){}

/**************************************************************************************************/
/**
 * Variables used for initializing Fragments
 */
    //LAYOUT_POSTER_FRAGMENT - layout id used by Poster Fragment
    private final static int LAYOUT_POSTER_FRAGMENT = R.layout.poster_fragment;
    //POSTER_CHILD_GRID_VIEW - child view in poster fragment layout, gridView child
    private final static int POSTER_CHILD_GRID_VIEW = R.id.gridview;
    //mPosterFragment - fragment used to display a gridView of poster images
    private PosterFragment mPosterFragment;

    //LAYOUT_EMPTY_FRAGMENT - layout id used by Empty Fragment
    private final static int LAYOUT_EMPTY_FRAGMENT = R.layout.empty_fragment;
    //EMPTY_CHILD_TEXT_VIEW - child view in empty fragment layout, textView child
    private final static int EMPTY_CHILD_TEXT_VIEW = R.id.txt_empty;
    //mEmptyFragment - fragment used to display an "Empty" message if PosterFragment is empty
    private EmptyFragment mEmptyFragment;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void initPosterFragment(String) - initialize PosterFragment; set layout and child view ids and
 * send the Maid name to fragment
 */
    public void initPosterFragment(String name){
        Log.d("Movies", "     simple grid fragment");
        //create PosterFragment
        mPosterFragment = new PosterFragment();

        //send layout id to PosterFragment
        mPosterFragment.setLayout(LAYOUT_POSTER_FRAGMENT);

        //send child view id, gridView
        mPosterFragment.setGridViewId(POSTER_CHILD_GRID_VIEW);

        //send Maid name to fragment
        mPosterFragment.setServiceName(name);
    }

/**
 * void initEmptyFragment(String) - initialize EmptyFragment; set layout and child view ids and send
 * the Maid name to fragment
 */
    public void initEmptyFragment(String name){
        //create EmptyFragmentt
        mEmptyFragment = new EmptyFragment();

        //send layout id to EmptyFragment
        mEmptyFragment.setLayout(LAYOUT_EMPTY_FRAGMENT);

        //send child view id, textView
        mEmptyFragment.setTexttViewId(EMPTY_CHILD_TEXT_VIEW);

        //send Maid name to fragment
        mEmptyFragment.setServiceName(name);

        mEmptyFragment.setMessage("Hey Guys!!");
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterFragment getPosterFragment() - getter for PosterFragment
 */
    public PosterFragment getPosterFragment(){
        return mPosterFragment;
    }

/**
 * EmptyFragment getEmptyFragment() - getter for EmptyFragment
 */
    public EmptyFragment getEmptyFragment(){
        return mEmptyFragment;
    }

/**************************************************************************************************/

}
