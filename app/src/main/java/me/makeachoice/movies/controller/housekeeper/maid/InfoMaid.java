package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.housekeeper.helper.InfoHelper;
import me.makeachoice.movies.fragment.InfoFragment;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * InfoMaid initializes and takes care of communicating with the Fragment that will display the
 * detailed information of a selected movie.
 *
 * The InfoMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * It uses other classes to assist in the upkeeping of the Fragment:
 *      InfoFragment - handles the Fragment lifecycle
 *      InfoHelper - holds all static resources (layout id, view ids, etc)
 *
 * Variables from MyMaid:
 *      Bridge mBridge
 *      Fragment mFragment
 *
 * Methods from MyMaid:
 *      void initFragment()
 *
 * Bridge Interface from MyMaid:
 *      Context getActivityContext()
 *      void registerFragment(String, Fragment)
 *
 * Implements InfoFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 */
public class InfoMaid extends MyMaid implements InfoFragment.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      InfoHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      String mStarRating
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private InfoHelper.ViewHolder mViewHolder;

    //TODO - need to comment
    private String mStrRating;

/**************************************************************************************************/

/**************************************************************************************************/
    /**
     * InfoMaid - constructor
     * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
     */
    public InfoMaid(Bridge bridge){

        //class implementing Bridge interface
        mBridge = bridge;

        //get string value
        mStrRating = bridge.getActivityContext().getString(InfoHelper.STR_USER_RATING_ID);

        //initialize fragment to be maintained
        mFragment = initFragment();

        //initialize ViewHolder
        mViewHolder = new InfoHelper.ViewHolder();

        //registers fragment PosterMaid is assigned to maintain
        mBridge.registerFragment(InfoHelper.NAME_ID, mFragment);

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
 * void initFragment() - initialize Fragment; set layout and child view ids and maid name
 */
    protected Fragment initFragment(){
        //create InfoFragment
        InfoFragment fragment = new InfoFragment();

        //send Maid name to fragment
        fragment.setMaidId(InfoHelper.NAME_ID);

        //return fragment
        return fragment;
    }

/**
 * View createView(LayoutInflater, ViewGroup, Bundle) - is called by InfoFragment when
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
        View v = inflater.inflate(InfoHelper.INFO_FRAGMENT_LAYOUT_ID, container, false);

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

        updateTextViews(layout);
        updatePoster(layout);
    }

    private void updateTextViews(View layout){
        //get textView children
        TextView txtTitle = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_TITLE);
        TextView txtRelease = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_RELEASE);
        TextView txtCast = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_CAST);
        TextView txtRating = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_RATING);
        TextView txtOverview = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_OVERVIEW);

        txtTitle.setText(mItem.getOriginalTitle());
        txtRelease.setText(mItem.getReleaseDate());
        txtCast.setText("Placeholder for cast");
        txtRating.setText(mStrRating + ": " + mItem.getVoteAverage());
        txtOverview.setText(mItem.getOverview());
    }


    private void updatePoster(View layout){

        ImageView imgPoster = (ImageView)mViewHolder.getView(layout, InfoHelper.INFO_IMG_POSTER);

        //TODO - need to move to resource file
        String posterPath = mBridge.getActivityContext().getString(R.string.tmdb_image_base_request) +
                mItem.getPosterPath() + "?" +
                mBridge.getActivityContext().getString(R.string.tmdb_query_api_key) + "=" +
                mBridge.getActivityContext().getString(R.string.api_key_tmdb);


        //add poster image, placeholder image and error image
        Picasso.with(mBridge.getActivityContext())
                .load(posterPath)
                .placeholder(InfoHelper.INFO_PLACEHOLDER_IMG_ID)
                .error(InfoHelper.INFO_ERROR_IMG_ID)
                .into(imgPoster);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * SimpleListFragment.Bridge interface implementation.
 *      ListAdapter getListAdapter() - Fragments' access to the ListAdapter
 *      void setListAdapter() - used to ensure that the Maid class uses the setListAdapter method
 *      void onItemClick() - list item click event
 */
/**************************************************************************************************/

//TODO - methods in InfoMaid are a bit rough
    public MovieModel getMovie(){
        return mItem;
    }

//TODO - using MovieJSON.MovieDetail is breaking MVP design!!
//TODO - String values are hardcode here - move to resource file
    private MovieModel mItem;
    public void setMovie(MovieModel item){
        mItem = item;
    }

/**************************************************************************************************/

}
