package me.makeachoice.movies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;


/**************************************************************************************************/
/**
 * EmptyFragment is a Fragment with a TextView child and is used when a simple fragment with either
 * a GridView or ListView child ONLY is empty.
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
public class EmptyFragment extends MyFragment {
/*
    Fragment subclasses require an empty default constructor. If you don't provide one but
    specify a non-empty constructor, Lint will give you an error.
    Android may destroy and later re-create an activity and all its associated fragments when
    the app goes into the background. When the activity comes back, its FragmentManager starts
    re-creating fragments by using the empty default constructor. If it cannot find one, you
    get an exception
 */
    public static EmptyFragment newInstance(){
        return new EmptyFragment();
    }

    public EmptyFragment(){}

/**************************************************************************************************/

    //mView - view inflated from resource layout id
    private View mView;
    //mTextViewId - child view of layout, contains "Empty" message
    private int mTextViewId;

    //KEY_TEXT_VIEW_ID - key used for bundle to save id of the textView child
    String KEY_TEXT_VIEW_ID = "TextViewId";
    //KEY_MESSAGE - key used for bundle to save message used in the textView child
    String KEY_MESSAGE = "Message";

    //mMessage - message when fragment is displayed
    String mMessage;

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
        Log.d("Movies", "EmptyFragment.onCreateView");

        //check if bundle has been sent/saved
        if(savedInstanceState != null){
            //get layout id to inflate root view
            mLayoutId = savedInstanceState.getInt(KEY_LAYOUT);
            //get name of servers up-keeping this fragment
            mServiceName = savedInstanceState.getString(KEY_SERVICE_NAME);
            //get id of textView containing the "Empty" message
            mTextViewId = savedInstanceState.getInt(KEY_TEXT_VIEW_ID);
            //get message for textView child
            mMessage = savedInstanceState.getString(KEY_MESSAGE);

        }

        //get application context, the Boss
        Boss boss = (Boss)getActivity().getApplicationContext();
        try{
            //make sure Maid is implementing the Bridge interface
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
        Log.d("Movies", "EmptyFragment.onActivityCreated");

        //create the textView that will display a message
        TextView txtView = (TextView)mView.findViewById(mTextViewId);
        //set textView message
        txtView.setText(mMessage);
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
        //save id of child view (textview) that displays the message
        saveState.putInt(KEY_TEXT_VIEW_ID, mTextViewId);
        //save message to be displayed
        saveState.putString(KEY_MESSAGE, mMessage);
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
 * void setServiceName(String) - sets the name of the server taking care of the fragment
 */
    public void setServiceName(String name){
        mServiceName = name;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void setTextViewId(int) - child view id of layout, a textview that will display a message
 * @param id - resource textview id
 */
    public void setTexttViewId(int id){
        //save textview id to an instance variable
        mTextViewId = id;
    }
/**************************************************************************************************/

}
