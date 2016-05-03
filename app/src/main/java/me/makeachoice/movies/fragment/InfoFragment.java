package me.makeachoice.movies.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.makeachoice.movies.controller.Boss;

/**************************************************************************************************/
/**
 * InfoFragment is a Fragment that displays detailed information about a selected movie
 *
 * Variables from MyFragment:
 *      String KEY_MAID_ID
 *
 *      Bridge mBridge
 *      View mLayout
 *      Integer mMaidId
 *
 * Methods from MyFragment:
 *      void setMaidId(Integer)
 *
 * Bridge Interface from MyFragment:
 *      View createView(LayoutInflater, ViewGroup, Bundle)
 *      void createActivity(Bundle, View)
 *
 */
public class InfoFragment extends MyFragment {
/*
    Fragment subclasses require an empty default constructor. If you don't provide one but
    specify a non-empty constructor, Lint will give you an error.
    Android may destroy and later re-create an activity and all its associated fragments when
    the app goes into the background. When the activity comes back, its FragmentManager starts
    re-creating fragments by using the empty default constructor. If it cannot find one, you
    get an exception
 */
    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    public InfoFragment(){}

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * onAttach(...) called once the fragment is associated with its activity. Fragments are usually
 * created in the Activities onCreate( ) method.
 *
 * @param context - activity context
 */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

/** onCreateView(...) is called when it's time for the fragment to draw its UI for the first
 * time. To draw a UI for your fragment, you must return a View from this method that is the
 * root of your fragment's layout. You can return null if the fragment does not provide a UI.
 *
 * This is called between onCreate(...) and onActivityCreated(...). If you return a View from
 * here, you will later be called in onDestroyView() when the view is being released.
 *
 * Remember setRetainInstance(true) if you want to retain fragment values during an orientation
 * change event.
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //check if bundle has been sent/saved
        if(savedInstanceState != null){
            //get id number of Maid maintaining this fragment
            mMaidId = savedInstanceState.getInt(KEY_MAID_ID);
        }

        //get application context, the Boss
        Boss boss = (Boss)getActivity().getApplicationContext();

        try{
            //make sure Maid is implementing the Bridge interface
            mBridge = (Bridge)boss.getMaid(mMaidId);
        }catch(ClassCastException e){
            throw new ClassCastException(boss.toString() +
                    " must implement Bridge interface");
        }

        //create and return fragment layout view from file found in res/layout/xxx.xml,
        if(boss.getOrientation() == Configuration.ORIENTATION_PORTRAIT){
            if(mLayout == null){
                //inflate portrait layout if null
                mLayout = mBridge.createView(inflater, container, savedInstanceState);
            }

            //set current layout to portrait layout
            mCurrentLayout = mLayout;
        }
        else{
            if(mLayoutLand == null){
                //inflate landscape layout if null
                mLayoutLand = mBridge.createView(inflater, container, savedInstanceState);
            }

            //set current layout to landscape layout
            mCurrentLayout = mLayoutLand;
        }

        //fragment if some kind of configuration change occurs (like an orientation change)
        setRetainInstance(true);

        return mCurrentLayout;
    }

    View mLayoutLand;
    View mCurrentLayout;

/**
 * onActivityCreated(...) is called when the activity the fragment is attached to completed its
 * own Activity.onCreate( )
 * @param savedInstanceState - bundle object containing saved instance states
 */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBridge.createActivity(savedInstanceState, mCurrentLayout);

    }

/**
 * onSaveInstanceState(...) is called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 * @param saveState - bundle object used to save any instance states
 */
    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);
        //save id number of Maid maintaining this fragment
        saveState.putInt(KEY_MAID_ID, mMaidId);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented abstract methods from MyFragment:
 *      void setMaidId(Integer)
 */
/**************************************************************************************************/
    /**
     * void setMaidId(Integer) - sets the id number of the Maid taking care of the fragment
     */
    public void setMaidId(Integer id){
        mMaidId = id;
    }

/**************************************************************************************************/

}
