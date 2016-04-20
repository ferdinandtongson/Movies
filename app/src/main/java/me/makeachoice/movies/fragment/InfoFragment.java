package me.makeachoice.movies.fragment;

import android.content.Context;
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
 *      String KEY_SERVICE_NAME
 *
 *      Bridge mBridge
 *      View mLayout
 *      String mServiceName
 *
 * Methods from MyFragment:
 *      void setServiceName(String)
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
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //check if bundle has been sent/saved
        if(savedInstanceState != null){
            //get name of servers up-keeping this fragment
            mServiceName = savedInstanceState.getString(KEY_SERVICE_NAME);
        }

        //get application context, the Boss
        Boss boss = (Boss)getActivity().getApplicationContext();

        try{
            //make sure Maid is implementing the Bridge interface
            mBridge = (Bridge)boss.getMaid(mServiceName);
        }catch(ClassCastException e){
            throw new ClassCastException(boss.toString() +
                    " must implement Bridge interface");
        }

        //create and return fragment layout view from file found in res/layout/xxx.xml,
        if(mLayout == null){
            //mLayout = inflater.inflate(mLayoutId, container, false);
            mLayout = mBridge.createView(inflater, container, savedInstanceState);
        }

        return mLayout;
    }

/**
 * onActivityCreated(...) is called when the activity the fragment is attached to completed its
 * own Activity.onCreate( )
 * @param savedInstanceState - bundle object containing saved instance states
 */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBridge.createActivity(savedInstanceState, mLayout);

    }

/**
 * onSaveInstanceState(...) is called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 * @param saveState - bundle object used to save any instance states
 */
    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);
        //save name of server maintaining this fragment
        saveState.putString(KEY_SERVICE_NAME, mServiceName);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented abstract methods from MyFragment:
 *      void setServiceName(String)
 */
/**************************************************************************************************/
    /**
     * void setServiceName(String) - sets the name of the server taking care of the fragment
     */
    public void setServiceName(String name){
        mServiceName = name;
    }

/**************************************************************************************************/

}
