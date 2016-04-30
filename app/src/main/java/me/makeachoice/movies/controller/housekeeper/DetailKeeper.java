package me.makeachoice.movies.controller.housekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import me.makeachoice.movies.DetailActivity;
import me.makeachoice.movies.MyActivity;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.assistant.MaidAssistant;
import me.makeachoice.movies.controller.housekeeper.helper.DetailHelper;
import me.makeachoice.movies.controller.housekeeper.helper.InfoHelper;
import me.makeachoice.movies.controller.housekeeper.maid.InfoMaid;
import me.makeachoice.movies.controller.housekeeper.recycler.DetailAdapter;
import me.makeachoice.movies.model.item.MovieItem;


/**
 * DetailKeeper is responsible for DetailActivity and all the Fragments contained with the activity.
 * It communicates directly with Boss, DetailActivity and the Maids maintaining the fragments.
 *
 * The HouseKeeper is only aware of the Activity, its' child views, and fragments contained within
 * the Activity. It is Not aware of the details of the fragments it contains, the details are taken
 * care of by the Maid classes.
 *
 * It uses other classes to assist in the upkeep of the Activity:
 *      MaidAssistant - initializes and registers all the Maids used by this HouseKeeper
 *      InfoMaid - maintains the InfoFragment that displays general information about the movie
 *
 *      DetailHelper - holds all static resources (layout id, view ids, etc)
 *      DetailAdapter - FragmentStatePagerAdapter that handles the Fragment swipe updates
 *
 * Variables from MyHouseKeeper:
 *      Boss mBoss
 *      FragmentManager mFragmentManager
 *      MaidAssistant mMaidAssistant
 *      FragmentAssistant mFragAssistant
 *      HashMap<Integer, Fragment> mFragmentRegistry
 *      Toolbar mToolbar
 *      FloatingActionButton mFab
 *
 * Methods from MyHouseKeeper
 *      Context getActivityContext()
 *      void registerFragment(Integer key, Fragment fragment)
 *      Toolbar getToolbar(MyActivity, int)
 *      FloatingActionButton getFloatButton(MyActivity, int, View.OnClickListener)
 *
 * Implements MainActivity.Bridge
 *      void create(Bundle savedInstanceState)
 *      void postResume()
 *      void backPressed()
 *      void createOptionsMenu(Menu menu)
 *      void optionsItemSelected(MenuItem item)
 *
 * Implements Maid.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      xxx onSomeCustomMaidMethod() [SomeMaid only]
 *
 */
public class DetailKeeper extends MyHouseKeeper implements DetailActivity.Bridge,
        DetailAdapter.Bridge,
        InfoMaid.Bridge {

/**************************************************************************************************/
/**
 * Class Variables:
 *      DetailHelper.ViewHolder mViewHolder - holds all the child view of the Activity
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private DetailHelper.ViewHolder mViewHolder;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * _templateKeeper - constructor, registers to Boss, initialize Maid and Fragment Assistant classes
 * and initializes class variables if any
 * @param boss - Boss class
 */
    public DetailKeeper(Boss boss){

        //set Boss
        mBoss = boss;

        //register HouseKeeper to Boss
        mBoss.registerHouseKeeper(DetailHelper.NAME_ID, this);

        //initialize fragment registry
        mFragmentRegistry = new HashMap<>();

        //initialize ViewHolder
        mViewHolder = new DetailHelper.ViewHolder();

        //initialize MaidAssistant
        mMaidAssistant = new MaidAssistant();

        //initialize all Maids used by the SwipeKeeper
        mMaidAssistant.hireDetailMaids(mBoss, this);
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

    //getters and setters, if any

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Maid.Bridge implementations:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      xxx onSomeCustomMaidMethod() [SomeMaid only]
 */
/**************************************************************************************************/

    public void onSomeCustomMaidMethod(){
        //communication method used by some Maid class to request something from the HouseKeeper
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implements Bridge Interface from MainActivity:
 *      void create(Bundle savedInstanceState) - create activity layout
 *      void createOptionsMenu(Menu menu) - create menu for toolbar
 *      void postResume() - both activity and fragments have resumed
 *      void backPressed() - back button has been pressed
 *      void optionsItemSelected(MenuItem item) - menu item has been selected
 *
 */
/**************************************************************************************************/
/**
 * void create(Bundle) - called when onCreate is called in the activity. Sets the activity layout,
 * fragmentManager and toolbar for the activity as well as checks for network connectivity.
 *
 * NOTE: Both FragmentManager and Toolbar are context sensitive and need to be recreated every time
 * onCreate() is called in the Activity
 * @param savedInstanceState - instant state values
 */
    public void create(MyActivity activity, Bundle savedInstanceState){
        if(savedInstanceState != null){
            //TODO - need to save instances. Movie request type maybe?
        }

        String keyType = activity.getString(DetailHelper.KEY_MOVIE_TYPE_ID);
        String keyIndex = activity.getString(DetailHelper.KEY_MOVIE_INDEX_ID);

        Intent intent = activity.getIntent();
        int movieType = Integer.valueOf(intent.getStringExtra(keyType));
        int index = Integer.valueOf(intent.getStringExtra(keyIndex));

        MovieItem movie = mBoss.getMovie(movieType, index);

        InfoMaid maid = (InfoMaid)mBoss.getMaid(InfoHelper.NAME_ID);
        maid.setMovie(movie);

        Log.d("Movies", "     overview: " + movie.getOverview());

        //set activity layout
        activity.setContentView(DetailHelper.DETAIL_LAYOUT_ID);

        //fragmentManager is context sensitive, need to recreate every time onCreate() is called
        mFragmentManager = activity.getSupportFragmentManager();

        //TODO - set toolbar layout, create Helper
        //Create toolbar with creation of Activity
        //mToolbar = getToolbar(activity, _templateHelper.MAIN_TOOLBAR_ID);

        //TODO - set floatbar layout, create Helper
        //mFab = getFloatButton(activity, _templateHelper.MAIN_FAB_ID), xxOnClickListener)

        //check for connectivity
        if(!mBoss.hasNetworkConnection()){
            //do something if no network found
        }

        //create FragmentPagerAdapter for viewPager
        DetailAdapter adapter = new DetailAdapter(this, mFragmentManager, mFragmentRegistry);
        Log.d("Movies", "     adapter: " + adapter.toString());

        //get viewPager
        ViewPager viewPager = (ViewPager)activity.findViewById(DetailHelper.DETAIL_PAGER_ID);

        //set adapter in viewPager
        viewPager.setAdapter(adapter);
    }

/**
 * void createOptionsMenu(Menu) - called if a toolbar is present in the activity.
 * @param menu - will hold menu items
 */
    public void createOptionsMenu(MyActivity activity, Menu menu){
        //TODO - create toolbar menues, create Helper
        // Inflate the menu; this adds items to the toolbar if it is present.
        //activity.getMenuInflater().inflate(MainHelper.MAIN_MENU, menu);
    }

/**
 * void postResume() - called when onPostResume() is called in the Activity signalling that
 * both the Activity and Fragments have resumed and can now be manipulated.
 *
 * If orientation change has occurred, old fragment will automatically be added. No need to commit
 * the fragment again. This stops the double calling of onCreateView() method in Fragment classes.
 * Remember to setRetainInstance(true) in Fragment to retain data!!
 */
    public void postResume(){

        //check orientation change status
        if(!mBoss.onOrientationChange()){
            //NOT an orientation change event, update fragment view
            //displayFragment();
        }
    }

/**
 * void saveInstanceState(Bundle) - called when onSaveInstanceState is called in the Activity.
 * @param inState - bundle to save instance states
 */
    public void saveInstanceState(Bundle inState){
        //TODO - need to save instance state. Movie request type maybe?
    }

/**
 * void backPressed() - called when onBackPressed() is called in the Activity. MainActivity has
 * two Fragments (PosterFragment and InfoFragment). If InfoFragment is currently active, back
 * press will send PosterFragment to the front. If PosterFragment is active, will close the
 * application.
 */
    public void backPressed(MyActivity activity){

        Log.d("Movies", "DetailKeeper.backPressed");
        //check current fragment being displayed
        /*if(mCurrentFragId == PosterHelper.NAME_ID){
            //if PosterFragment, shutdown app
            activity.finishActivity();
        }
        else{
            //if other than PosterFragment (InfoFragment), change to PosterFragment
            mCurrentFragId = PosterHelper.NAME_ID;

            //display PosterFragment
            displayFragment();
        }*/
    }

    /**
     * void onOptionsItemSelected(MenuItem) - listens for an onOptionsItemSelected event from the
     * menu list contained in the toolbar view
     *
     * Menu will list movies by most popular, highest rated, now playing, upcoming
     *
     * @param item - menu item selected in the toolbar
     */
    public void optionsItemSelected(MyActivity activity, MenuItem item){
        //get id of item selected from ActionBar
        int id = item.getItemId();

        //type of movie requested
        int movieRequest;

        switch(id){
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(activity);
                //return true;
        }

        //identify menu selection
        /*if (id == MainHelper.MENU_ITEM01) {
            //requested most popular movies
            //movieRequest = PosterButler.MOVIE_REQUEST_MOST_POPULAR;
        }
        else if (id == MainHelper.MENU_ITEM02) {
            //requested most popular movies
            //movieRequest = PosterButler.MOVIE_REQUEST_MOST_POPULAR;
        }
        else if (id == MainHelper.MENU_ITEM03) {
            //requested now playing movies
            //movieRequest = PosterButler.MOVIE_REQUEST_NOW_PLAYING;
        }
        else{
            //requested upcoming movies
            movieRequest = PosterButler.MOVIE_REQUEST_UPCOMING;
        }*/

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * DetailAdapter.Bridge implementations:
 *      void onFragmentChange(int position) - fragment being viewed changed, swipe event happened
 */
/**************************************************************************************************/
/**
 * void onFragmentChange(int) - DetailAdapter Bridge implementation, called when a swipe event
 * happens causing the Fragment being viewed to be changed. Gets poster data from Boss, if
 * poster data is not available, an AsyncTask will start and updatePoster() will be called
 * when the task is done.
 * @param position - position of fragment being displayed
 */
    public void onFragmentChange(int position){
        //get movie data from Boss, if null will start AsyncTask to get data, calls updatePoster
        switch (position) {
            case 0:
                Log.d("Movies", "DetailKeeper.onFragmentChange: " + position);
                //mBoss.getPosters(PosterHelper.NAME_ID_MOST_POPULAR);
                break;
            case 1:
                //mBoss.getPosters(PosterHelper.NAME_ID_TOP_RATED);
                break;
            case 2:
                //mBoss.getPosters(PosterHelper.NAME_ID_NOW_PLAYING);
                break;
            case 3:
                //mBoss.getPosters(PosterHelper.NAME_ID_UPCOMING);
                break;
            case 4:
                //mBoss.getPosters(PosterHelper.NAME_ID_EMPTY);
                break;
            default:
                //mBoss.getPosters(PosterHelper.NAME_ID_MOST_POPULAR);
                break;
        }

    }

/**
 * void updateDetails(MovieItem) - called when a detailed Movie request AsyncTask has
 * finished.
 * @param item - movie item data of current movie being viewed
 */
    public void updateDetails(MovieItem item){
        Log.d("Movies", "DetailKeeper.updateDetails");
        //get Maid responsible for displaying the type of movies requested
        InfoMaid maid = ((InfoMaid)mBoss.getMaid(InfoHelper.NAME_ID));

        maid.updateViews(item);
        //update posters being displayed by the Fragment being maintained by the Maid
        //maid.updatePosters(posters);
    }

/**************************************************************************************************/


}