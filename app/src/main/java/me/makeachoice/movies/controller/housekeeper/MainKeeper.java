package me.makeachoice.movies.controller.housekeeper;

import android.os.Bundle;
import android.support.design.widget.Snackbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.MainActivity;

import me.makeachoice.movies.MyActivity;
import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.housekeeper.assistant.FragmentAssistant;
import me.makeachoice.movies.controller.housekeeper.assistant.MaidAssistant;
import me.makeachoice.movies.controller.housekeeper.helper.EmptyHelper;
import me.makeachoice.movies.controller.housekeeper.helper.InfoHelper;
import me.makeachoice.movies.controller.housekeeper.helper.MainHelper;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.controller.housekeeper.maid.InfoMaid;
import me.makeachoice.movies.controller.housekeeper.maid.MyMaid;
import me.makeachoice.movies.controller.housekeeper.maid.PosterMaid;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * MainKeeper is the HouseKeeper for MainActivity. It is responsible for the Activity and all of
 * the Fragments contained within this activity. It communicates directly with Boss to get data
 * needed by the Activity and Fragments as well as the Activity and Fragments.
 *
 * The HouseKeeper is only aware of the Activity, its' child views, and fragments contained within
 * the Activity. It is Not aware of the details of the fragments it contains, the details are taken
 * care of by the Maid classes.
 *
 * It uses other classes to assist in the upkeep of the Activity:
 *      MaidAssistant - initializes and registers all the Maids used by this HouseKeeper
 *      PosterMaid - maintains the PosterFragment
 *      EmptyMaid - maintains the EmptyFragment
 *      InfoMaid - maintains the InfoFragment
 *
 *      FragmentAssistant - assists in the transitions between fragments
 *      MainHelper - holds all static resources (layout id, view ids, etc)
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
 *      void registerFragment(Integer, Fragment)
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
 *      void onSelectedPoster(int position) [PosterMaid only]
 *
 */
public class MainKeeper extends MyHouseKeeper implements MainActivity.Bridge,
        PosterMaid.Bridge, MyMaid.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      int mCurrentFragId - current fragment id being seen by the user
 *      int mMovieRequest - current list of movies requested
 *      MovieModel mMovie - current movie selected
 */
/**************************************************************************************************/

    //mCurrentFragId - current fragment id being seen by the user
    private int mCurrentFragId;

    //mMovieRequest - type of movies requested
    private int mMovieRequest;

    //mMovie - current movie selected
    private MovieModel mMovie;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MainKeeper - constructor, registers to Boss, initialize Maid and Fragment Assistant classes and
 * sets default values for current fragment and movie request
 * @param boss - Boss class
 */
    public MainKeeper(Boss boss){

        //set Boss
        mBoss = boss;

        //register HouseKeeper to Boss
        mBoss.registerHouseKeeper(MainHelper.NAME_ID, this);

        mFragmentRegistry = new HashMap<>();

        //initialize MaidAssistant
        mMaidAssistant = new MaidAssistant();

        //initialize all Maids used by the HouseKeeper
        mMaidAssistant.hireMainMaids(mBoss, this);

        //initialize FragmentAssistant
        mFragAssistant = new FragmentAssistant();

        //set fragment id
        mCurrentFragId = PosterHelper.NAME_ID;

        //set movie request type
        mMovieRequest = PosterHelper.NAME_ID_MOST_POPULAR;
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

/**************************************************************************************************/
/**
 * PosterMaid.Bridge implementations:
 *      void onSelectedPoster(int position) [PosterMaid] - onSelectPoster event
 */
/**************************************************************************************************/
/**
 * void onSelectedPoster(int) - event called when a poster is selected in PosterFragment
 * @param position - position of poster
 */
    public void onSelectedPoster(int id, int position){

        //get movie data from movie list
        mMovie = mBoss.getMovie(mMovieRequest, position);

        //get InfoMaid
        InfoMaid maid = ((InfoMaid)mBoss.getMaid(InfoHelper.NAME_ID));

        //send movie data to InfoFragment
        maid.setMovie(mMovie);

        //update current fragment to InfoFragment
        mCurrentFragId = InfoHelper.NAME_ID;

        //get InfoFragment and display, save back stack, do NOT pop stack
        mFragAssistant.changeFragmentWithBackStack(mFragmentManager, MainHelper.MAIN_CONTAINER_ID,
                mFragmentRegistry.get(InfoHelper.NAME_ID),
                mBoss.getActivityContext().getString(InfoHelper.NAME_ID),
                false);

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

        //set activity layout
        activity.setContentView(MainHelper.MAIN_LAYOUT_ID);

        //fragmentManager is context sensitive, need to recreate every time onCreate() is called
        mFragmentManager = activity.getSupportFragmentManager();

        //Create toolbar with creation of Activity
        mToolbar = getToolbar(activity, MainHelper.MAIN_TOOLBAR_ID);

        //check for connectivity
        if(!mBoss.hasNetworkConnection()){
            //no network, update current fragment to EmptyFragment
            mCurrentFragId = EmptyHelper.NAME_ID;

            //get EmptyFragment and display
            mFragAssistant.changeFragment(mFragmentManager, MainHelper.MAIN_CONTAINER_ID,
                    mFragmentRegistry.get(EmptyHelper.NAME_ID),
                    mBoss.getActivityContext().getString(EmptyHelper.NAME_ID));
        }

    }

    /**
     * void createOptionsMenu(Menu) - called if a toolbar is present in the activity.
     * @param menu - will hold menu items
     */
    public void createOptionsMenu(MyActivity activity, Menu menu){
        // Inflate the menu; this adds items to the toolbar if it is present.
        activity.getMenuInflater().inflate(MainHelper.MAIN_MENU, menu);
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
            displayFragment();
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

        //check current fragment being displayed
        if(mCurrentFragId == PosterHelper.NAME_ID){
            //if PosterFragment, shutdown app
            activity.finishActivity();
        }
        else{
            //if other than PosterFragment (InfoFragment), change to PosterFragment
            mCurrentFragId = PosterHelper.NAME_ID;

            //display PosterFragment
            displayFragment();
        }
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
        if (id == MainHelper.MENU_ITEM01) {
            //requested most popular movies
            movieRequest = PosterHelper.NAME_ID_MOST_POPULAR;
        }
        else if (id == MainHelper.MENU_ITEM02) {
            //requested most popular movies
            movieRequest = PosterHelper.NAME_ID_TOP_RATED;
        }
        else if (id == MainHelper.MENU_ITEM03) {
            //requested now playing movies
            movieRequest = PosterHelper.NAME_ID_NOW_PLAYING;
        }
        else{
            //requested upcoming movies
            movieRequest = PosterHelper.NAME_ID_UPCOMING;
        }

        //check movie request type and where it occurred
        checkMovieRequest(activity, movieRequest);

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      void checkMovieRequest(int) - checks the movie request initiated by a menu selection
 *      void displayFragment() - display fragment being requested
 */
/**************************************************************************************************/
/**
 * void checkMovieRequest(int) - checks the movie request initiated by a menu selection.
 *
 * If the menu selection occurred while InfoFragment is being displayed, an onBackPressed() will
 * be called to go back to the PosterFragment.
 *
 * If the menu selection occurred while PosterFragment is being displayed, will check if the
 * selection is a new selection. If it is, the fragment will be prepared to display then new
 * list of movie posters.
 *
 * @param movieRequest - type of movies requested
 */
    private void checkMovieRequest(MyActivity activity, int movieRequest){
        //check if movie request happened while displaying InfoFragment
        if(mCurrentFragId == InfoHelper.NAME_ID){
            //movie request happened in InfoFragment, update movie request
            mMovieRequest = movieRequest;

            //call Activity.onBackPressed(),
            activity.onBackPressed();
        }
        else{
            //movie request happened in PosterFragment
            if(mMovieRequest != movieRequest){
                //is a new movie request
                mMovieRequest = movieRequest;
                //update fragment with new data
                displayFragment();
            }
        }

    }

    /**
     * void displayFragment - createFragment to be displayed. If current fragment is set to
     * PosterFragment or EmptyFragment, check Boss if we have movie data to present. If we have
     * data, present PosterFragment. If not, present EmptyFragment.
     *
     * If current fragment is set to InfoFragment, display InfoFragment. In this case, this method
     * was called due to an orientation change so mCurrentFragId does NOT need to be updated but
     * the back stack needs to be popped.
     */
    public void displayFragment(){
        //get layout container for fragments
        int containerId = MainHelper.MAIN_CONTAINER_ID;

        //get movie data from Boss, if null will start AsyncTask to get data
        ArrayList<PosterItem> posters = mBoss.getPosters(mMovieRequest);

        if(mCurrentFragId == PosterHelper.NAME_ID || mCurrentFragId == EmptyHelper.NAME_ID){
            if(posters != null){
                //movie data is available, get PosterMaid
                PosterMaid maid = ((PosterMaid)mBoss.getMaid(PosterHelper.NAME_ID));

                //update posters
                maid.updatePosters(posters);

                //update current fragment to PosterFragment
                mCurrentFragId = PosterHelper.NAME_ID;

                //get PosterFragment and display
                mFragAssistant.changeFragment(mFragmentManager,
                        containerId, mFragmentRegistry.get(PosterHelper.NAME_ID),
                        mBoss.getActivityContext().getString(PosterHelper.NAME_ID));
            }
            else{
                //update current fragment to EmptyFragment
                mCurrentFragId = EmptyHelper.NAME_ID;

                //get EmptyFragment and display
                mFragAssistant.changeFragment(mFragmentManager,
                        containerId, mFragmentRegistry.get(EmptyHelper.NAME_ID),
                        mBoss.getActivityContext().getString(EmptyHelper.NAME_ID));
            }
        }
        else if(mCurrentFragId == InfoHelper.NAME_ID){
            //do NOT need to set current fragment

            //get InfoMaid
            InfoMaid maid = ((InfoMaid)mBoss.getMaid(InfoHelper.NAME_ID));

            //send movie details to fragment
            maid.setMovie(mMovie);

            //get InfoFragment and display, pop back stack
            mFragAssistant.changeFragmentWithBackStack(mFragmentManager, containerId,
                    mFragmentRegistry.get(InfoHelper.NAME_ID),
                    mBoss.getActivityContext().getString(InfoHelper.NAME_ID),
                    true);
        }
    }

/**************************************************************************************************/

    /**
     * void onFABOnClickListener(View) - listens for an onClick event happening with the Floating
     * Action Button object.
     * @param view - floating action button view that registered the onClick event
     */
    private void onFABOnClickListener(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
    //OnClickListener used with FloatingActionButton
    private View.OnClickListener mFABOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    onFABOnClickListener(view);
                }
            };




/**************************************************************************************************/



}
