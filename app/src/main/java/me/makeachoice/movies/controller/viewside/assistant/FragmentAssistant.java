package me.makeachoice.movies.controller.viewside.assistant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * FragmentAssistant is to assist in managing fragment transitions for HouseKeeper
 */
public class FragmentAssistant {

/**************************************************************************************************/
/**
 * public methods:
 *      void changeFragment(FragmentManager, int, Fragment)
 *      void changeFragmentWithBackStack(FragmentManager, int, Fragment, boolean)
 */
/**************************************************************************************************/
/**
 * void changeFragment(FragmentManager, int, Fragment) - simply removes the current fragment
 * being viewed and adds the new fragment to be viewed
 * @param manager - FragmentManager to handle the fragment transaction
 * @param containerId - layout id that acts as a container for the fragment
 * @param fragment - new fragment to be viewed
 */
    public void changeFragment(FragmentManager manager, int containerId, Fragment fragment,
                               String tag){
        //begin fragment transaction
        FragmentTransaction ft = manager.beginTransaction();

        //replace fragment to the fragment container
        ft.replace(containerId, fragment, tag);

        //commit transaction
        ft.commit();
    }

/**
 * void changeFragmentWithBackStack(FragmentManager, int, Fragment, boolean) - adds a new fragment
 * to be viewed and pushes the old fragment to the back. If popBackStack is true, will pop the top
 * of the stack first before adding the new fragment.
 *
 * Example: currently viewing frag01 and add frag02, popBackStack false
 *      stack{frag01} --> stack{frag01,frag02}
 * Example: currently viewing frag02 with frag01 in back stack, add frag03, popBackStack true
 *      stack{frag01,frag02} --> (popBackStack) stack{frag01} --> stack{frag01, frag03}
 * and commit to activity
 */
    public void changeFragmentWithBackStack(FragmentManager manager, int containerId,
                                             Fragment fragment, String tag, boolean popBackStack){

        //begin fragment transaction
        FragmentTransaction ft = manager.beginTransaction();

        //check if stack needs to be popped
        if(popBackStack){
            //pop top fragment from top
            manager.popBackStackImmediate();
        }

        //replace fragment to the fragment container
        ft.replace(containerId, fragment, tag);

        //put old fragment to back stack
        ft.addToBackStack(null);

        //commit transaction
        ft.commit();
    }

/**************************************************************************************************/

}
