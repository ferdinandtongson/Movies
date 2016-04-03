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
 * PosterFragment is a GridView fragment that will display a grid of posters (images)
 *
 * Variables from MyFragment:
 *      String KEY_LAYOUT
 *      String KEY_SERVICE_NAME
 *
 *      int mLayoutId
 *      String mServiceName
 *      Bridge mBridge
 *
 * Methods from MyFragment:
 *      void setLayout(int)
 *      void setServiceName(String)
 */
public class PosterFragment extends MyFragment {
/*
    Fragment subclasses require an empty default constructor. If you don't provide one but
    specify a non-empty constructor, Lint will give you an error.
    Android may destroy and later re-create an activity and all its associated fragments when
    the app goes into the background. When the activity comes back, its FragmentManager starts
    re-creating fragments by using the empty default constructor. If it cannot find one, you
    get an exception
 */
    public static PosterFragment newInstance(){
        return new PosterFragment();
    }

    public PosterFragment(){}

/**************************************************************************************************/

    //mGridViewId - child view id of layout
    private int mGridViewId;
    //KEY_GRID_VIEW_ID - key used for bundle to save the child view id
    String KEY_GRID_VIEW_ID = "GridViewId";
    //mGridView - gridView of fragment
    private GridView mGridView;

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
        //empty
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
        Log.d("Movies", "PosterFragment.onCreateView");

        //check if bundle has been sent/saved
        if(savedInstanceState != null){
            //get layout id to inflate root view
            mLayoutId = savedInstanceState.getInt(KEY_LAYOUT);
            //get name of server maintaining this fragment
            mServiceName = savedInstanceState.getString(KEY_SERVICE_NAME);
            //get child view id (gridview)
            mGridViewId = savedInstanceState.getInt(KEY_GRID_VIEW_ID);
        }

        //get Application context, the Boss
        Boss boss = (Boss)getActivity().getApplicationContext();
        try{
            //check if Maid is implementing interface
            mBridge = (Bridge)boss.getMaid(mServiceName);
        }catch(ClassCastException e){
            throw new ClassCastException(boss.toString() +
                    " must implement OnSimpleListListener");
        }


        //create and return fragment layout view from file found in res/layout/xxx.xml,
        if(mLayout == null){
            mLayout = inflater.inflate(mLayoutId, container, false);
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
        Log.d("Movies", "PosterFragment.onActivityCreated");

        if(mGridView == null){
            Log.d("Movies", "     gridView is null");
            //create the child view, gridview
            mGridView = (GridView)mLayout.findViewById(mGridViewId);
        }

        //add ListAdapter to gridview
        mGridView.setAdapter(mBridge.getListAdapter());
    }

/**
 * onSaveInstanceState(...) is called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 * @param saveState - bundle object used to save any instance states
 */
    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);
        //save layout id of fragment
        saveState.putInt(KEY_LAYOUT, mLayoutId);
        //save name of server maintaining this fragment
        saveState.putString(KEY_SERVICE_NAME, mServiceName);
        //save child view id (gridView)
        saveState.putInt(KEY_GRID_VIEW_ID, mGridViewId);

    }

/**
 * onDetach( ) is called immediately prior to the fragment no longer being associated with its
 * activity.
 */
    @Override
    public void onDetach(){
        super.onDetach();
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented abstract methods from MyFragment:
 *      void setLayout(int)
 *      void setServiceName(String)
 */
/**************************************************************************************************/
/**
 * void setLayout(int) allows the layout id for the fragment to be dynamically added
 * @param id  - resource layout id
 */
    public void setLayout(int id){
        Log.d("SimpleListFragment", "PosterFragment.setLayout");

        //save layout id to an instance variable
        mLayoutId = id;
    }

/**
 * void setServiceName(String) - sets the name of the server taking care of the fragment
 */
    public void setServiceName(String name){
        mServiceName = name;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void setGridViewId(int) allows the gridview id for the fragment to be dynamically added
 * @param id - resource gridview id
 */
    public void setGridViewId(int id){
        //save gridview id to an instance variable
        mGridViewId = id;
    }

/**
 * void onListItemClick(int) is called when the user clicks on a list item
 * @param position - position of the item; position is zero based (0 - x)
 */
    public void onItemClick(int position){
        Log.d("SimpleListFragment", "PosterFragment.onListItemClick");
        //sends the click event across the bridge for the activity to handle
        mBridge.onItemClick(position);
    }
/**************************************************************************************************/



}
