package me.makeachoice.movies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.maid.InfoMaid;
import me.makeachoice.movies.model.json.MovieJSON;

/**************************************************************************************************/
/**
 * InfoFragment is a Fragment that displays detailed information about a selected movie
 *
 * Variables from MyFragment:
 *      String KEY_LAYOUT
 *      String KEY_SERVICE_NAME
 *
 *      int mLayoutId
 *      View mLayout
 *      String mServiceName
 *      Bridge mBridge
 *
 * Methods from MyFragment:
 *      void setLayout(int)
 *      void setServiceName(String)
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

    //textView ids for layout
    private int INDEX_TITLE = 0;
    private int INDEX_RELEASE = 1;
    private int INDEX_CAST = 2;
    private int INDEX_RATING = 3;
    private int INDEX_OVERVIEW = 4;
    private int INDEX_MAX = 5;

    private int[] mTxtIds = new int[INDEX_MAX];

    //txtView objects
    private TextView mTxtTitle;
    private TextView mTxtRelease;
    private TextView mTxtCast;
    private TextView mTxtRating;
    private TextView mTxtOverview;

    private int mImgPosterId;
    private ImageView mImgPoster;

    private int mRtbRatingId;
    private RatingBar mRtbRating;

    //Upkeeping MyMaid class must implement this interface
    public interface Bridge extends MyFragment.Bridge{
        String[] getTxtValues();
    }


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
        mContext = context;
    }
    Context mContext;

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
        Log.d("Movies", "InfoFragment.onCreateView");

        //check if bundle has been sent/saved
        if(savedInstanceState != null){
            //get layout id to inflate root view
            mLayoutId = savedInstanceState.getInt(KEY_LAYOUT);
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

        //get child view for fragment
        if(mTxtTitle == null){
            //get textView children
            mTxtTitle = (TextView)mLayout.findViewById(mTxtIds[INDEX_TITLE]);
            mTxtRelease = (TextView)mLayout.findViewById(mTxtIds[INDEX_RELEASE]);
            mTxtCast = (TextView)mLayout.findViewById(mTxtIds[INDEX_CAST]);
            mTxtRating = (TextView)mLayout.findViewById(mTxtIds[INDEX_RATING]);
            mTxtOverview = (TextView)mLayout.findViewById(mTxtIds[INDEX_OVERVIEW]);

            //get imageView and ratingBar child
            mImgPoster = (ImageView)mLayout.findViewById(mImgPosterId);
            mRtbRating = (RatingBar)mLayout.findViewById(mRtbRatingId);
        }

        mMovieItem = ((InfoMaid) mBridge).getMovie();
        updateTextViews();
        updatePoster();
        //updateRatingBar();
    }

    private void updateTextViews(){
        //TODO - need to change Bridge concept for MyFragment
        setTxtValues(((InfoMaid) mBridge).getTxtValues());

        mTxtTitle.setText(mTxtValues[INDEX_TITLE]);
        mTxtRelease.setText(mTxtValues[INDEX_RELEASE]);
        mTxtCast.setText(mTxtValues[INDEX_CAST]);
        mTxtRating.setText(mTxtValues[INDEX_RATING]);
        mTxtOverview.setText(mTxtValues[INDEX_OVERVIEW]);
    }

    private void updatePoster(){
        Picasso.with(mContext).load(mMovieItem.getPosterPath()).into(mImgPoster);
    }

    private void updateRatingBar(){
        Double rating = mMovieItem.getVoteAverage();
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
 * void setTextViewId(int) - child view id of layout, a textView that will display a message
 */
    public void setTxtViewIds(Integer[] ids){
        mTxtIds[INDEX_TITLE] = ids[INDEX_TITLE];
        mTxtIds[INDEX_RELEASE] = ids[INDEX_RELEASE];
        mTxtIds[INDEX_CAST] = ids[INDEX_CAST];
        mTxtIds[INDEX_RATING] = ids[INDEX_RATING];
        mTxtIds[INDEX_OVERVIEW] = ids[INDEX_OVERVIEW];
    }

    public void setChildIds(int imgId, int rtbId){
        mImgPosterId = imgId;
        mRtbRatingId = rtbId;
    }

    private String[] mTxtValues = new String[INDEX_MAX];
    private void setTxtValues(String[] values){
        mTxtValues[INDEX_TITLE] = values[INDEX_TITLE];
        mTxtValues[INDEX_RELEASE] = values[INDEX_RELEASE];
        mTxtValues[INDEX_CAST] = values[INDEX_CAST];
        mTxtValues[INDEX_RATING] = values[INDEX_RATING];
        mTxtValues[INDEX_OVERVIEW] = values[INDEX_OVERVIEW];
    }

    //TODO - review logic, look at using PosterItem instead of MovieJSON.MovieDetail, breaking MVP
    MovieJSON.MovieDetail mMovieItem;

/**************************************************************************************************/


}
