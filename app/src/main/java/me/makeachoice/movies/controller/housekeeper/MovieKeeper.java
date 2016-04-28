package me.makeachoice.movies.controller.housekeeper;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import me.makeachoice.movies.MyActivity;
import me.makeachoice.movies._templateActivity;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.assistant.FragmentAssistant;
import me.makeachoice.movies.controller.housekeeper.assistant.MaidAssistant;
import me.makeachoice.movies.controller.housekeeper.helper.MainHelper;
import me.makeachoice.movies.controller.housekeeper.helper.MovieHelper;
import me.makeachoice.movies.controller.housekeeper.helper._templateHelper;
import me.makeachoice.movies.controller.housekeeper.maid._templateMaid;


/**
 * _templateHouseKeeper is the template for HouseKeeper Class. It is responsible for the Activity
 * and all of the Fragments contained within this activity. It communicates directly with Boss to
 * get data needed by the Activity and Fragments as well as the Activity and Fragments.
 *
 * The HouseKeeper is only aware of the Activity, its' child views, and fragments contained within
 * the Activity. It is Not aware of the details of the fragments it contains, the details are taken
 * care of by the Maid classes.
 *
 * It uses other classes to assist in the upkeep of the Activity:
 *      MaidAssistant - initializes and registers all the Maids used by this HouseKeeper
 *      SomeMaid - maintains the SomeFragment
 *
 *      FragmentAssistant - assists in the transitions between fragments
 *      _templateHelper - holds all static resources (layout id, view ids, etc)
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
public class MovieKeeper extends MyHouseKeeper implements _templateActivity.Bridge,
        _templateMaid.Bridge {

/**************************************************************************************************/
/**
 * Class Variables:
 *      - None -
 */
/**************************************************************************************************/

    //add class variables here

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * _templateKeeper - constructor, registers to Boss, initialize Maid and Fragment Assistant classes
 * and initializes class variables if any
 * @param boss - Boss class
 */
    public MovieKeeper(Boss boss){

        //set Boss
        mBoss = boss;

        //register HouseKeeper to Boss
        mBoss.registerHouseKeeper(MovieHelper.NAME_ID, this);

        mFragmentRegistry = new HashMap<>();

        //initialize MaidAssistant
        mMaidAssistant = new MaidAssistant();

        //TODO - add custom method to MaidAssistant
        //initialize all Maids used by the _templateHouseKeeper
        //mMaidAssistant.hireXXXMaids(mBoss, this);

        //initialize FragmentAssistant
        mFragAssistant = new FragmentAssistant();

        //set class variable values here
        // xxxVariable = xxxValue;
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

        //TODO - set activity layout, create Helper
        //set activity layout
        activity.setContentView(MovieHelper.MOVIE_LAYOUT_ID);

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

        Log.d("Movies", "MovieKeeper.backPressed");
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

        //identify menu selection
        /*if (id == MainHelper.MENU_ITEM01) {
            //requested most popular movies
            //movieRequest = MovieButler.MOVIE_REQUEST_MOST_POPULAR;
        }
        else if (id == MainHelper.MENU_ITEM02) {
            //requested most popular movies
            //movieRequest = MovieButler.MOVIE_REQUEST_MOST_POPULAR;
        }
        else if (id == MainHelper.MENU_ITEM03) {
            //requested now playing movies
            //movieRequest = MovieButler.MOVIE_REQUEST_NOW_PLAYING;
        }
        else{
            //requested upcoming movies
            movieRequest = MovieButler.MOVIE_REQUEST_UPCOMING;
        }*/

    }

/**************************************************************************************************/


}
