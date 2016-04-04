package me.makeachoice.movies.controller.housekeeper.maid;

import android.support.v4.app.Fragment;
import android.widget.ListAdapter;

import me.makeachoice.movies.R;
import me.makeachoice.movies.fragment.InfoFragment;
import me.makeachoice.movies.model.json.MovieJSON;

/**
 * InfoMaid initializes and takes care of communicating with the Fragment that will display the
 * detailed information of a selected movie.
 *
 * The InfoMaid is only aware of the Fragment and the views at the fragment level. It is NOT
 * aware of the view above it (the Activity containing the Fragment).
 *
 * Variables from MyMaid:
 *      MyHouseKeeper mHouseKeeper
 *      String mName
 *      Fragment mFragment
 *
 * Implements InfoFragment.Bridge - NONE used
 *      ListAdapter getListAdapter() - not used
 *      void setListAdapter(ListAdapter) - not used
 *      void onItemClick(...) - not used
 */
public class InfoMaid extends MyMaid implements InfoFragment.Bridge{

    private ListAdapter mListAdapter;
    public interface Bridge{
        //Interface methods needed to be implemented by the instantiating class
        void registerFragment(String key, Fragment fragment);
    }

    private Bridge mBridge;
    public InfoMaid(Bridge bridge, String name){

        mBridge = bridge;
        mName = name;

        mBridge.registerFragment(name, getFragment());
    }

/**************************************************************************************************/
/**
 * Variables used for initializing Fragments
 */
/**************************************************************************************************/
    //LAYOUT_INFO_FRAGMENT - layout used by Info Fragment
    private final static int LAYOUT_INFO_FRAGMENT = R.layout.info_fragment;

    //CHILD_TXT_TITLE - textView child for title of movie
    private final static int CHILD_TXT_TITLE = R.id.txt_title;
    //CHILD_IMG_POSTER - imageView child for poster image of movie
    private final static int CHILD_IMG_POSTER = R.id.img_poster;
    //CHILD_TXT_RELEASE - textView child for release date of movie
    private final static int CHILD_TXT_RELEASE = R.id.txt_release;
    //CHILD_TXT_CAST - textView child for cast members of movie
    private final static int CHILD_TXT_CAST = R.id.txt_cast;
    //CHILD_RTB_RATING - ratingBarView child for visual display of rating
    private final static int CHILD_RTB_RATING = R.id.rtb_rating;
    //CHILD_TXT_RATING - textView child for rating percentage of movie
    private final static int CHILD_TXT_RATING = R.id.txt_rating;
    //CHILD_TXT_OVERVIEW - textView child for overview, plot, of movie
    private final static int CHILD_TXT_OVERVIEW = R.id.txt_overview;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void initFragment() - initialize Fragment; set layout and child view ids and maid name
 */
    protected Fragment initFragment(){
        //create InfoFragment
        InfoFragment fragment = new InfoFragment();

        //send layout id to InfoFragment
        fragment.setLayout(LAYOUT_INFO_FRAGMENT);

        //send Maid Name to fragment
        fragment.setServiceName(mName);

        Integer[] ids = {CHILD_TXT_TITLE, CHILD_TXT_RELEASE, CHILD_TXT_CAST,
                CHILD_TXT_RATING, CHILD_TXT_OVERVIEW};

        fragment.setTxtViewIds(ids);
        fragment.setChildIds(CHILD_IMG_POSTER, CHILD_RTB_RATING);

        return fragment;
    }

    public Fragment getFragment(){
        if(mFragment == null){
            mFragment = initFragment();
        }

        return mFragment;
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
/**
 * ListAdapter getListAdapter() - Fragment can get access to the ListAdapter
 * @return ListAdapter - returns a list adapter created by the Boss
 */
    public ListAdapter getListAdapter(){
        return null;
    }

/**
 * void setListAdapter(ListAdapter) - set the ListAdapter the fragment is going to use, if any.
 * @param adapter - ListAdapter to be consumed by the fragment
 */
    public void setListAdapter(ListAdapter adapter){
        //is empty
    }

/**
 * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
 * @param position - list position of item clicked
 */
    public void onItemClick(int position){
        //is empty
    }

    public String[] getTxtValues(){
        return mValues;
    }

//TODO - methods in InfoMaid are a bit rough
    public MovieJSON.MovieDetail getMovie(){
        return mItem;
    }

//TODO - using MovieJSON.MovieDetail is breaking MVP design!!
//TODO - String values are hardcode here - move to resource file
    private String[] mValues;
    private MovieJSON.MovieDetail mItem;
    public void setMovie(MovieJSON.MovieDetail item){
        mItem = item;
        String [] values = {item.getOriginalTitle(), item.getReleaseDate(),
                "Placeholder for casting info",
                "User Rating: " + item.getVoteAverage().toString(),
                item.getOverview()};

        mValues = values;
        mRating = item.getVoteAverage();
    }


    private Double mRating;
/**************************************************************************************************/


}
