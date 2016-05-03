package me.makeachoice.movies.controller.housekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.view.DetailActivity;
import me.makeachoice.movies.view.MyActivity;
import me.makeachoice.movies.view.SwipeActivity;
import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.assistant.MaidAssistant;
import me.makeachoice.movies.controller.housekeeper.helper.DetailHelper;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.controller.housekeeper.helper.SwipeHelper;
import me.makeachoice.movies.controller.housekeeper.maid.PosterMaid;
import me.makeachoice.movies.controller.housekeeper.adapter.SwipeAdapter;

/**
 * SwipeKeeper is responsible for SwipeActivity and all the Fragments contained with the activity.
 * It communicates directly with Boss, SwipeActivity and the Maids maintaining the fragments.
 *
 * The HouseKeeper is only aware of the Activity, its' child views, and fragments contained within
 * the Activity. It is Not aware of the details of the fragments it contains, the details are taken
 * care of by the Maid classes.
 *
 * It uses other classes to assist in the upkeep of the Activity:
 *      MaidAssistant - initializes and registers all the Maids used by this HouseKeeper
 *      PosterMaid (Popular) - maintains the PosterFragment that displays Most Popular movies
 *      PosterMaid (Top Rated) - maintains the PosterFragment that displays Top Rated movies
 *      PosterMaid (Now Playing) - maintains the PosterFragment that displays Now Playing movies
 *      PosterMaid (Upcoming) - maintains the PosterFragment that displays Upcoming movies
 *      PosterMaid (Favorite) - maintains the PosterFragment that displays User Favorite movies
 *
 *      SwipeHelper - holds all static resources (layout id, view ids, etc)
 *      SwipeAdapter - FragmentStatePagerAdapter that handles the PosterFragment swipe updates
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
 *      int getOrientation()
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
 * Implements SwipeAdapter.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void onFragmentChange(int) - called when a fragment change occurs because of a "swipe" event
 *
 * Implements Maid.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      void onSelectedPoster(int, int) - movie poster selected [PosterMaid.Bridge]
 *
 */
public class SwipeKeeper extends MyHouseKeeper implements SwipeActivity.Bridge, SwipeAdapter.Bridge,
        PosterMaid.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      SwipeHelper.ViewHolder mViewHolder - holds all the child view of the Activity
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private SwipeHelper.ViewHolder mViewHolder;

/**************************************************************************************************/

/**************************************************************************************************/
    /**
     * SwipeKeeper - constructor, registers to Boss and initialize Maids
     * @param boss - Boss class
     */
    public SwipeKeeper(Boss boss){

        //set Boss
        mBoss = boss;

        //register HouseKeeper to Boss
        mBoss.registerHouseKeeper(SwipeHelper.NAME_ID, this);

        //initialize fragment registry
        mFragmentRegistry = new HashMap<>();

        //initialize ViewHolder
        mViewHolder = new SwipeHelper.ViewHolder();

        //initialize MaidAssistant
        mMaidAssistant = new MaidAssistant();

        //initialize all Maids used by the SwipeKeeper
        mMaidAssistant.hireSwipeMaids(mBoss, this);

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

    //- NONE -

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Maid.Bridge implementations:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      xxx onSomeCustomMaidMethod() [SomeMaid only]
 */
/**************************************************************************************************/

    //- NONE -

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implements Bridge Interface from MainActivity:
 *      void create(Bundle savedInstanceState) - create activity layout
 *      void createOptionsMenu(Menu menu) - create menu for toolbar
 *      void postResume() - both activity and fragments have resumed
 *      void backPressed() - back button has been pressed
 *      void optionsItemSelected(MenuItem item) - menu item has been selected
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
        Log.d("Movies", "SwipeKeeper.create");
        if(savedInstanceState != null){
            //TODO - need to save instances. Movie request type maybe?
        }

        //set activity layout
        activity.setContentView(SwipeHelper.SWIPE_LAYOUT_ID);

        //fragmentManager is context sensitive, need to recreate every time onCreate() is called
        mFragmentManager = activity.getSupportFragmentManager();

        //TODO - set toolbar layout, create Helper
        //Create toolbar with creation of Activity
        //mToolbar = getToolbar(activity, SwipeHelper.SWIPE_TOOLBAR_ID);

        //check for connectivity
        if(!mBoss.hasNetworkConnection()){
            //do something if no network found
        }

        //create FragmentPagerAdapter for viewPager
        SwipeAdapter adapter = new SwipeAdapter(this, mFragmentManager, mFragmentRegistry);

        //get viewPager
        ViewPager viewPager = (ViewPager)activity.findViewById(SwipeHelper.SWIPE_PAGER_ID);

        //set adapter in viewPager
        viewPager.setAdapter(adapter);

    }

/**
 * void createOptionsMenu(Menu) - called if a toolbar is present in the activity.
 * @param menu - will hold menu items
 */
    public void createOptionsMenu(MyActivity activity, Menu menu){
        //TODO - need to enable toolbar
        // Inflate the menu; this adds items to the toolbar if it is present.
        //activity.getMenuInflater().inflate(SwipeHelper.SWIPE_MENU, menu);
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
        if(!mBoss.getOrientationChanged()){
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
        //finish activity
        activity.finishActivity();
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
 * PosterMaid.Bridge implementations:
 *      void onSelectedPoster(int position) [PosterMaid] - onSelectPoster event
 */
/**************************************************************************************************/
/**
 * void onSelectedPoster(int) - event called when a poster is selected; starts DetailActivity
 * @param movieType - id number of the Maid used as movieType where the poster selection occurred
 * @param index - position of poster
 */
    public void onSelectedPoster(int movieType, int index){
        //get key for saving movie type to Extra
        String keyType = mBoss.getActivityContext().getString(DetailHelper.KEY_MOVIE_TYPE_ID);
        //get key for saving movie index to Extra
        String keyIndex = mBoss.getActivityContext().getString(DetailHelper.KEY_MOVIE_INDEX_ID);

        //create intent to start DetailActivity
        Intent intent = new Intent(mBoss.getActivityContext(), DetailActivity.class);

        //save movieType for DetailActivity to use
        intent.putExtra(keyType, String.valueOf(movieType));
        //save movieIndex for DetailActivity to use
        intent.putExtra(keyIndex, String.valueOf(index));

        //start DetailActivity
        mBoss.getActivityContext().startActivity(intent);


    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * SwipeAdapter.Bridge implementations:
 *      void onFragmentChange(int position) - fragment being viewed changed, swipe event happened
 */
/**************************************************************************************************/
/**
 * void onFragmentChange(int) - SwipeAdapter Bridge implementation, called when a swipe event
 * happens causing the Fragment being viewed to be changed. Gets poster data from Boss, if
 * poster data is not available, an AsyncTask will start and updatePoster() will be called
 * when the task is done.
 * @param position - position of fragment being displayed
 */
    public void onFragmentChange(int position){
        //get movie data from Boss, if null will start AsyncTask to get data, calls updatePoster
        switch (position) {
            case 0: mBoss.getPosters(PosterHelper.NAME_ID_MOST_POPULAR);
                break;
            case 1: mBoss.getPosters(PosterHelper.NAME_ID_TOP_RATED);
                break;
            case 2: mBoss.getPosters(PosterHelper.NAME_ID_NOW_PLAYING);
                break;
            case 3: mBoss.getPosters(PosterHelper.NAME_ID_UPCOMING);
                break;
            case 4: mBoss.getPosters(PosterHelper.NAME_ID_EMPTY);
                break;
            default: mBoss.getPosters(PosterHelper.NAME_ID_MOST_POPULAR);
                break;
        }

    }

/**
 * void updatePoster(ArrayList<PosterItem>, int) - called when a Movie request AsyncTask has
 * finished. The Maid handling the type of movie posters request will be notified and the Maid
 * will update the posters in the Fragment the Maid is maintaining.
 * @param posters - list of PosterItem data requested
 * @param request - type of Movies requested
 */
    public void updatePosters(ArrayList<PosterItem> posters, int request){
        //get Maid responsible for displaying the type of movies requested
        PosterMaid maid = ((PosterMaid)mBoss.getMaid(request));

        //update posters being displayed by the Fragment being maintained by the Maid
        maid.updatePosters(posters);
    }

/**************************************************************************************************/

}
