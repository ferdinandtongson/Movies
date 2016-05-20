package me.makeachoice.movies.controller.viewside.maid;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.makeachoice.movies.controller.viewside.adapter.NameAdapter;
import me.makeachoice.movies.controller.viewside.helper.InfoHelper;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.view.dialog.CastDialog;
import me.makeachoice.movies.view.fragment.InfoFragment;
import me.makeachoice.movies.model.item.CastItem;
import me.makeachoice.movies.model.item.MovieItem;

/**
 * InfoMaid initializes and takes care of communicating with the Fragment that will display the
 * detailed information of a selected movie.
 *
 * The InfoMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * It uses other classes to assist in the upkeep of the Fragment:
 *      InfoFragment - handles the Fragment lifecycle
 *      InfoHelper - holds all static resources (layout id, view ids, etc)
 *      NameAdapter - updates the cast listView
 *
 * Variables from MyMaid:
 *      int mMaidId
 *      Bridge mBridge
 *      Fragment mFragment
 *      View mLayout
 *
 * Methods from MyMaid:
 *      void initFragment()
 *
 * Bridge Interface from MyMaid:
 *      Context getActivityContext()
 *      void registerFragment(String, Fragment)
 *      int getOrientation()
 *
 * Implements InfoFragment.Bridge
 *      View createView(LayoutInflater, ViewGroup, Bundle);
 *      void createActivity(Bundle, View);
 *
 * Implements NameAdapter.Bridge:
 *      Context getActivityContext()
 *
 * Implements AdapterView.OnItemClickListener
 *      void onItemClick(AdapterView,View,int,long)
 *
 */
public class InfoMaid extends MyMaid implements InfoFragment.Bridge, NameAdapter.Bridge,
        AdapterView.OnItemClickListener{

/**************************************************************************************************/
/**
 * Class Variables
 *      InfoHelper.ViewHolder mViewHolder - holds all the child views of the fragment
 *      NameAdapter mCastAdapter - adapter for cast listView, displays cast members in the movie
 *      MovieItem mItem - MovieItem holding all the movie data
 *      String mStarRating - string value "User Rating"
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private InfoHelper.ViewHolder mViewHolder;

    //mCastAdapter - adapter for cast listView, displays cast members in the movie
    private NameAdapter mCastAdapter;

    //mItem - MovieItem holding all the movie data
    private MovieItem mMovie;

    //mStrRating - string value "User Rating"
    private String mStrRating;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * InfoMaid - constructor
 * @param bridge - class implementing Bridge interface, typically a MyHouseKeeper class
 * @param id - id number of InfoMaid
 */
    public InfoMaid(Bridge bridge, int id){

        //class implementing Bridge interface
        mBridge = bridge;

        //get string value
        mStrRating = bridge.getActivityContext().getString(InfoHelper.STR_USER_RATING_ID);

        //initialize fragment to be maintained
        mFragment = initFragment(id);

        //initialize ViewHolder
        mViewHolder = new InfoHelper.ViewHolder();

        //registers fragment PosterMaid is assigned to maintain
        mBridge.registerFragment(id, mFragment);

        //create empty array list of names
        ArrayList<String> names = new ArrayList<>();

        //initialize adapter used to hold cast member names
        mCastAdapter = new NameAdapter(this, names);

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity context
 *
 * Setters:
 *      void setMovie(MovieItem) - set movie data used by fragment
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context
 * @return - current Activity context
 */
    public Context getActivityContext(){
    return mBridge.getActivityContext();
}

/**
 * void setMovie(MovieItem) - set movie data used by fragment
 * @param item - MovieItem data
 */
    public void setMovie(MovieItem item){
        mMovie = item;
    }

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
 * @param id - id number of maid taking care of fragment
 */
    protected Fragment initFragment(int id){
        //create InfoFragment
        InfoFragment fragment = new InfoFragment();

        //send Maid name to fragment
        fragment.setMaidId(id);

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

        return inflater.inflate(InfoHelper.INFO_FRAGMENT_LAYOUT_ID, container, false);
    }

/**
 * void createActivity(Bundle, View) - is called by PosterFragment when onCreateActivity(...)
 * is called in that class. Sets child views in fragment before being seen by the user
 * @param savedInstanceState - saved instance states
 * @param layout - layout where child views reside
 */
    public void createActivity(Bundle savedInstanceState, View layout){
        //update textView in the fragment
        updateTextViews(layout, mMovie);

        //update listView used to display the list of cast members
        updateCastList(layout, mMovie);

        //update imageView used to display the movie poster
        updatePoster(layout, mMovie);

    }

/**
 * void updateTextViews(View, MovieItem) - set all the textView children with the given movie
 * data take from MovieItem.
 * @param layout - parent view holding the textView children
 * @param item - MovieItem holding the movie data
 */
    private void updateTextViews(View layout, MovieItem item){

        //textView variables
        TextView txtTitle;
        TextView txtRelease;
        TextView txtRating;
        TextView txtOverview;

        //check orientation
        if(mBridge.getOrientation() == Configuration.ORIENTATION_PORTRAIT){
            //get textView children from viewHolder - portrait
            txtTitle = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_TITLE_ID);
            txtRelease = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_RELEASE_ID);
            txtRating = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_RATING_ID);
            txtOverview = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_OVERVIEW_ID);
        }
        else{
            //get textView children from viewHolder - landscape
            txtTitle = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_TITLE_LAND_ID);
            txtRelease = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_RELEASE_LAND_ID);
            txtRating = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_RATING_LAND_ID);
            txtOverview = (TextView)mViewHolder.getView(layout, InfoHelper.INFO_TXT_OVERVIEW_LAND_ID);
        }

        //set the title of the movie
        txtTitle.setText(item.getTitle());
        //set the release date of the movie
        txtRelease.setText(item.getReleaseDate());
        //set the user rating average
        txtRating.setText(mStrRating + ": " + item.getVoteAverage());
        //set the overview of the movie (plot)
        txtOverview.setText(item.getOverview());
    }

/**
 * void updateCastList(View, MovieItem) - updates the cast listView with the cast adapter data
 * @param layout - parent view holding the listView child
 * @param item - MovieItem holding the movie data
 */
    private void updateCastList(View layout, MovieItem item){
        //listView variable
        ListView listView;

        //check orientation
        if(mBridge.getOrientation() == Configuration.ORIENTATION_PORTRAIT){
            //get listView children from viewHolder - portrait
            listView = (ListView)mViewHolder.getView(layout, InfoHelper.INFO_LST_CAST_ID);
        }
        else{
            //get listView children from viewHolder - landscape
            listView = (ListView)mViewHolder.getView(layout, InfoHelper.INFO_LST_CAST_LAND_ID);
        }

        //check if MovieItem has cast information
        if(item.getCast() != null){
            //add cast names to cast adapter
            mCastAdapter.setNames(prepareNames(item.getCast()));
        }

        //set onItemClick listener for listview
        listView.setOnItemClickListener(this);

        // Assign adapter to ListView
        listView.setAdapter(mCastAdapter);
    }

/**
 * void updatePoster(View, MovieItem) - updates the imageView with the poster of the movie
 * @param layout - parent view holding the listView child
 * @param item - MovieItem holding the movie data
 */
    private void updatePoster(View layout, MovieItem item){

        //imageView variable
        ImageView imgPoster;

        //check orientation
        if(mBridge.getOrientation() == Configuration.ORIENTATION_PORTRAIT){
            //get imageView children from viewHolder - portrait
            imgPoster = (ImageView)mViewHolder.getView(layout, InfoHelper.INFO_IMG_POSTER_ID);
        }
        else{
            //get imageView children from viewHolder - landscape
            imgPoster = (ImageView)mViewHolder.getView(layout, InfoHelper.INFO_IMG_POSTER_LAND_ID);
        }

        //add poster image, placeholder image and error image
        Picasso.with(mBridge.getActivityContext())
                .load(item.getPosterPath())
                .placeholder(PosterHelper.POSTER_PLACEHOLDER_IMG_ID)
                .error(PosterHelper.POSTER_PLACEHOLDER_IMG_ID)
                .into(imgPoster);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Class Methods
 *      void updateViews(MovieItem) - update fragment views with new movie data
 *      ArrayList<String> prepareNames(ArrayList<CastItem> - get cast member names and put them in
 *          an ArrayList<String>
 */
/**************************************************************************************************/
/**
 * void updateViews(MovieItem) - update fragment views with new movie data
 * @param item - MovieItem containing movie data
 */
    public void updateViews(MovieItem item){
        //save movie data to class variable
        mMovie = item;

        //check if cast member data is available
        if(item.getCast() != null){
            //has cast membr data, get names of cast members and update cast adapter
            mCastAdapter.setNames(prepareNames(item.getCast()));
        }
        else{
            //no cast member data, send empty list of names to cast adapter
            mCastAdapter.setNames(new ArrayList<String>());
        }
        //notify cast adapter that data has changed
        mCastAdapter.notifyDataSetChanged();

    }

/**
 * ArrayList<String> prepareNames(ArrayList<CastItem> - get cast member names and put them in
 * an ArrayList<String>.
 * @param castList - list of CastItem data
 * @return - return simple list of string names
 */
    private ArrayList<String> prepareNames(ArrayList<CastItem> castList){
        //create simple array list of names
        ArrayList<String> names = new ArrayList<>();

        //get number of cast members
        int count = castList.size();

        //populate simple array list of names
        for(int i = 0; i < count; i++){
            //add name to list
            names.add(castList.get(i).name);
        }

        //return list of names
        return names;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implement AdapterView.OnItemClickListener
 */
/**************************************************************************************************/
/**
 * void onItemClick(AdapterView,View,int,long) - onItemClick event when user clicks on cast
 * member name. When clicked, will display a dialog giving more information about the actor.
 * @param parent - adapterView holding list of names
 * @param v - child view displaying the cast member name
 * @param position - position of the item clicked in the list
 * @param id - id number of the item clicked in the list
 */
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        //get current activity
        Activity activity = (Activity)mBridge.getActivityContext();

        //create review dialog with review item data selected
        CastDialog mDialog = new CastDialog(activity, mMovie.getCast().get(position));

        //make dialog background transparent
        mDialog.getWindow().setBackgroundDrawable
                (new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //show dialog
        mDialog.show();
    }

/**************************************************************************************************/

}
