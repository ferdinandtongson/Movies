package me.makeachoice.movies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import me.makeachoice.movies.controller.Boss;


/**************************************************************************************************/

/**
 * SimpleGridFragment is a Fragment with only a GridView as a child
 */
public class SimpleGridFragment extends MyFragment {
/*
    Fragment subclasses require an empty default constructor. If you don't provide one but
    specify a non-empty constructor, Lint will give you an error.
    Android may destroy and later re-create an activity and all its associated fragments when
    the app goes into the background. When the activity comes back, its FragmentManager starts
    re-creating fragments by using the empty default constructor. If it cannot find one, you
    get an exception
 */
    public static SimpleGridFragment newInstance(){
        return new SimpleGridFragment();
    }

    public SimpleGridFragment(){}

/**************************************************************************************************/

    private View mView;
    private int mGridViewId;
    String KEY_GRIDVIEW_ID = "GridViewId";

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * onAttach(...) called once the fragment is associated with its activity. Fragments are usually
 * created in the Activities onCreate( ) method.
 *
 * This is where you can check if the container activity has implemented a bridge interface. If
 * not, it throws an exception. The bridge interface acts as a bridge to communicate with the
 * implementing activity.
 *
 * The reference to the bridge needs to be removed when onDetach is called to prevent a leak.
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
        Log.d("Movies", "SimpleGridFragment.onCreateView");

        //check if bundle has been sent/saved
        if(savedInstanceState != null){
            //get layout id to inflate root view
            mLayoutId = savedInstanceState.getInt(KEY_LAYOUT);
            mServiceName = savedInstanceState.getString(KEY_SERVICE_NAME);
            mGridViewId = savedInstanceState.getInt(KEY_GRIDVIEW_ID);

        }

        Boss boss = (Boss)getActivity().getApplicationContext();
        try{
            mBridge = (Bridge)boss.getMaid(mServiceName);
        }catch(ClassCastException e){
            throw new ClassCastException(boss.toString() +
                    " must implement OnSimpleListListener");
        }


        //create and return fragment layout view from file found in res/layout/xxx.xml,
        // use R.layout.xxx (mLayoutId)
        mView = inflater.inflate(mLayoutId, container, false);

        return mView;
    }

/**
 * onActivityCreated(...) is called when the activity the fragment is attached to completed its
 * own Activity.onCreate( )
 * @param savedInstanceState - bundle object containing saved instance states
 */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Movies", "SimpleGridFragment.onActivityCreated");

        //create the list by setting the list adapter
        GridView gridView = (GridView)mView.findViewById(mGridViewId);
        gridView.setAdapter(mBridge.getListAdapter());

    }

/**
 * onSaveInstanceState(...) is called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 * @param saveState - bundle object used to save any instance states
 */
    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);
        Log.d("Movies", "SimpleGridFragment.onSaveInstanceState");
        saveState.putInt(KEY_LAYOUT, mLayoutId);
        saveState.putString(KEY_SERVICE_NAME, mServiceName);
        saveState.putInt(KEY_GRIDVIEW_ID, mGridViewId);

    }

/**
 * onDetach( ) is called immediately prior to the fragment no longer being associated with its
 * activity. This is where you remove any bridge references created with the activity.
 */
    @Override
    public void onDetach(){
        super.onDetach();
    }

/**************************************************************************************************/
/**
 * void setLayout(int) allows the layout id for the fragment to be dynamically added
 * @param id  - resource layout id
 */
    public void setLayout(int id){
        Log.d("SimpleListFragment", "SimpleGridFragment.setLayout");

        //save layout id to an instance variable
        mLayoutId = id;
    }

/**
 * void setGridViewId(int) allows the gridview id for the fragment to be dynamically added
 * @param id - resource gridview id
 */
    public void setGridViewId(int id){
        Log.d("SimpleListFragment", "SimpleGridFragment.setGridViewId");

        //save gridview id to an instance variable
        mGridViewId = id;
    }

/**
 * void setServiceName(String) - sets the name of the server taking care of the fragment
 */
    public void setServiceName(String name){
        mServiceName = name;
    }


    /**
 * void onListItemClick(int) is called when the user clicks on a list item
 * @param position - position of the item; position is zero based (0 - x)
 */
    public void onItemClick(int position){
        Log.d("SimpleListFragment", "SimpleGridFragment.onListItemClick");
        //sends the click event across the bridge for the activity to handle
        mBridge.onItemClick(position);
    }
/**************************************************************************************************/



}
