package me.makeachoice.movies.controller.housekeeper.assistant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;
import me.makeachoice.movies.fragment.EmptyFragment;
import me.makeachoice.movies.fragment.InfoFragment;
import me.makeachoice.movies.fragment.PosterFragment;

/**
 * MainFragmentAssistant is to assist in managing Fragments for HouseKeeper class
 */
public class MainFragmentAssistant {

    private int mContainerId;
    private boolean mHasFragment;
    private int mCurrentFragment;

    public MainFragmentAssistant(int containerId){
        Log.d("Movies", "MainFragmentAssistant");
        mContainerId = containerId;
        mHasFragment = false;
    }


/**************************************************************************************************/

/**************************************************************************************************/

    public void requestDetailFragment(FragmentManager manager, Fragment fragment,
                                      String oldFrag, String newFrag){
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(oldFrag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(mContainerId, fragment, newFrag);
        ft.commit();
    }


    public void requestFragment(FragmentManager manager, Fragment fragment, String name){
        if(mHasFragment){
            replaceFragment(manager, fragment, name);
        }
        else{
            mHasFragment = true;
            addFragment(manager, fragment, name);
        }
    }

/**
 * void addFragmentToManager(Fragment) - adds fragment to FragmentManager and commit to activity
 * @param fragment - fragment object to be added
 */
    private void addFragment(FragmentManager manager, Fragment fragment, String name){
        //begin fragment transaction
        FragmentTransaction ft = manager.beginTransaction();

        //add fragment to the fragment container
        ft.add(mContainerId, fragment, name);

        //commit fragment to activity
        ft.commit();
    }

/**
 * void replaceFragmentInManager(int) - replaces a fragment object held by the FragmentManager
 * and commit to activity
 */
    private void replaceFragment(FragmentManager manager, Fragment fragment, String name){

        //begin fragment transaction
        FragmentTransaction ft = manager.beginTransaction();

        //replace fragment held by the FragmentManager
        ft.replace(mContainerId, fragment, name);

        //commit fragment to activity
        ft.commit();
    }

/**************************************************************************************************/


}
