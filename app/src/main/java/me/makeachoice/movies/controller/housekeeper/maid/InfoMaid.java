package me.makeachoice.movies.controller.housekeeper.maid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
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
 *      Fragment mFragment
 *
 * Methods from MyMaid:
 *      void initFragment()
 *
 * Implements InfoFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 * Bridge Interface:
 *      Context getActivityContext()
 *      void registerFragment(String, Fragment)
 *
 */
public class InfoMaid extends MyMaid implements InfoFragment.Bridge{

/**************************************************************************************************/
/**
 * Class Variables
 *      InfoHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      Bridge mBridge - class implementing Bridge interface
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private InfoHelper.ViewHolder mViewHolder;

    private String mStrRating;

    //mBridge - class implementing Bridge, typically a MyHouseKeeper class
    private Bridge mBridge;

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
        //register fragment to the HouseKeeper
        void registerFragment(Integer key, Fragment fragment);
    }

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

        //ViewHolder is empty
        mViewHolder.isEmpty = true;

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

        //get child view for fragment
        if(mViewHolder.isEmpty){
            //get textView children
            mViewHolder.title = (TextView)layout.findViewById(InfoHelper.INFO_TXT_TITLE);
            mViewHolder.release = (TextView)layout.findViewById(InfoHelper.INFO_TXT_RELEASE);
            mViewHolder.cast = (TextView)layout.findViewById(InfoHelper.INFO_TXT_CAST);
            mViewHolder.rating = (TextView)layout.findViewById(InfoHelper.INFO_TXT_RATING);
            mViewHolder.overview = (TextView)layout.findViewById(InfoHelper.INFO_TXT_OVERVIEW);

            //get imageView and ratingBar child
            mViewHolder.poster = (ImageView)layout.findViewById(InfoHelper.INFO_IMG_POSTER);
            mViewHolder.stars = (RatingBar)layout.findViewById(InfoHelper.INFO_RTB_RATING);

            //ViewHolder no longer empty
            mViewHolder.isEmpty = false;
        }

        updateTextViews();
        updatePoster();
    }

    private void updateTextViews(){

        mViewHolder.title.setText(mItem.getOriginalTitle());
        mViewHolder.release.setText(mItem.getReleaseDate());
        mViewHolder.cast.setText("Placeholder for cast");
        mViewHolder.rating.setText(mStrRating + ": " + mItem.getVoteAverage());
        mViewHolder.overview.setText(mItem.getOverview());
    }


    private void updatePoster(){
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
                .into(mViewHolder.poster);
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
