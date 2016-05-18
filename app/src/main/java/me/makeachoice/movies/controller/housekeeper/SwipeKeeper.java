package me.makeachoice.movies.controller.housekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
 * Implements SwipeActivity.Bridge
 *      void create(Bundle savedInstanceState)
 *      void postResume()
 *      void backPressed()
 *      void createOptionsMenu(Menu menu)
 *      void optionsItemSelected(MenuItem item)
 *
 * Implements SwipeAdapter.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *
 * Implements PosterMaid.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      void onSelectedPoster(int, int) - movie poster selected [PosterMaid.Bridge]
 *
 * Implements ViewPager.OnPageChangeListener
 *      void onPageScrollStateChanged(int)
 *      void onPageScrolled(int,float,int)
 *      onPageSelected(int)
 */
public class SwipeKeeper extends MyHouseKeeper implements SwipeActivity.Bridge, SwipeAdapter.Bridge,
        PosterMaid.Bridge, ViewPager.OnPageChangeListener{

/**************************************************************************************************/
/**
 * Class Variables:
 *      SwipeHelper.ViewHolder mViewHolder - holds all the child view of the Activity
 *      int mMovieIndex - index of current movie list being shown
 *      View.OnClickListener mFabListener - onClick listener for FloatingActionButton
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private SwipeHelper.ViewHolder mViewHolder;

    //mMovieIndex - index of current movie list being shown
    private int mMovieIndex;

    //mFabListener - onClick listener for FloatingActionButton
    private View.OnClickListener mFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onFABClick();
        }
    };


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

        //initialize movie index
        mMovieIndex = 0;
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
 * SwipeActivity.Bridge implementations:
 *      void create(Bundle savedInstanceState) - create activity layout
 *          void createViewPager(MyActivity,int) - create ViewPager and SwipeAdapter objects
 *      void createOptionsMenu(Menu menu) - create menu for toolbar
 *      void postResume() - both activity and fragments have resumed
 *      void backPressed() - back button has been pressed
 *      void optionsItemSelected(MenuItem item) - menu item has been selected
 */
/**************************************************************************************************/
/**
 * void create(MyActivity, Bundle) - called when onCreate is called in the activity. Sets the
 * activity layout, fragmentManager and other child views of the activity
 *
 * NOTE: Both FragmentManager and FAB are context sensitive and need to be recreated every time
 * onCreate() is called in the Activity
 * @param activity - current activity being shown
 * @param savedInstanceState - instant state values
 */
    public void create(MyActivity activity, Bundle savedInstanceState){
        if(savedInstanceState != null){
            //get movieIndex of last shown movieList
            mMovieIndex = savedInstanceState.getInt(
                    mBoss.getString(SwipeHelper.KEY_MOVIE_INDEX));
        }
        else{
            //default movie index = 0
            mMovieIndex = 0;
        }

        //set activity layout
        activity.setContentView(SwipeHelper.SWIPE_LAYOUT_ID);

        //fragmentManager is context sensitive, need to recreate every time onCreate() is called
        mFragmentManager = activity.getSupportFragmentManager();

        //Fab is context sensitive, need to recreate everytime onCreate() is called
        mFab = getFloatButton(activity, SwipeHelper.SWIPE_FAB_ID, mFabListener);

        //create ViewPager and SwipeAdapter
        createViewPager(activity, mMovieIndex);
    }

/**
 * void createViewPager(MyActivity,int) - create ViewPager and SwipeAdapter objects
 * @param activity - current Activity
 * @param pageIndex - index of page to be displayed in the ViewPager
 */
    private void createViewPager(MyActivity activity, int pageIndex){
        //create FragmentPagerAdapter for viewPager
        SwipeAdapter adapter = new SwipeAdapter(this, mFragmentManager, mFragmentRegistry);

        //get viewPager
        ViewPager viewPager = (ViewPager)activity.findViewById(SwipeHelper.SWIPE_PAGER_ID);

        //SwipeKeeper implements ViewPager.OnPageChangeListener interface
        viewPager.addOnPageChangeListener(this);

        //set adapter in viewPager
        viewPager.setAdapter(adapter);

        //set current movie fragment to view
        viewPager.setCurrentItem(pageIndex);

        //check if movie fragment index is 0
        if(pageIndex == 0){
            //if index = 0, call onPageSelected to update fragment
            onPageSelected(pageIndex);
        }
    }

/**
 * void createOptionsMenu(Menu) - does nothing
 * @param activity - current activity being shown
 * @param menu - will hold menu items
 */
    public void createOptionsMenu(MyActivity activity, Menu menu){
        //does nothing
    }

/**
 * void postResume() - does nothing
 */
    public void postResume(){
        //does nothing
    }

/**
 * void saveInstanceState(Bundle) - called when onSaveInstanceState is called in the Activity.
 * @param inState - bundle to save instance states
 */
    public void saveInstanceState(Bundle inState){
        //save current movieIndex
        inState.putInt(mBoss.getString(SwipeHelper.KEY_MOVIE_INDEX), mMovieIndex);
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
 * void onOptionsItemSelected(MenuItem) - does nothing
 * @param activity - current activity being shown
 * @param item - menu item selected in the toolbar
 */
    public void optionsItemSelected(MyActivity activity, MenuItem item){
        //does nothing
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * SwipeAdapter.Bridge implementations:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 */
/**************************************************************************************************/

    //Context getActivityContext() - implemented by MyHouseKeeper

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterMaid.Bridge implementations:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
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
 * ViewPager.OnPageChangeListener implementations:
 *      void onPageSelected(int) - current fragment being viewed by ViewPager adapter
 *      void onPageScrollStateChanged(int) - does nothing
 *      void onPageScrolled(int,float,int) - does nothing
 */
/**************************************************************************************************/
/**
 * void onPageSelected(int) - update fragment being viewed by ViewPager adapter. Checks with Boss
 * if poster data is in local buffer, if not will start an AsyncTask to get it from the database or
 * make an API call. After AsyncTask is complete, will call updatePosters() method
 * @param position - index of fragment being viewed by user
 */
    public void onPageSelected(int position){
        //initialize arrayList to hold poster items
        ArrayList<PosterItem> posters;

        //get type of posters being displayed
        int posterType;

        //get poster list from Boss, if empty will start AsyncTask to get poster data, calls
        //updatePosters() after AsyncTask completes
        switch (position) {
            //get Most Popular posters
            case 0: posters = mBoss.getPosters(PosterHelper.NAME_ID_MOST_POPULAR);
                posterType = PosterHelper.NAME_ID_MOST_POPULAR;
                break;
            //get Top Rated posters
            case 1: posters = mBoss.getPosters(PosterHelper.NAME_ID_TOP_RATED);
                posterType = PosterHelper.NAME_ID_TOP_RATED;
                break;
            //get Now Playing posters
            case 2: posters = mBoss.getPosters(PosterHelper.NAME_ID_NOW_PLAYING);
                posterType = PosterHelper.NAME_ID_NOW_PLAYING;
                break;
            //get Upcoming posters
            case 3: posters = mBoss.getPosters(PosterHelper.NAME_ID_UPCOMING);
                posterType = PosterHelper.NAME_ID_UPCOMING;
                break;
            //default request Favorite
            default: posters = mBoss.getPosters(PosterHelper.NAME_ID_FAVORITE);
                posterType = PosterHelper.NAME_ID_FAVORITE;
                break;
        }

        //check poster list size
        if(posters.size() > 0){
            //have poster in local buffer, update Poster fragment
            updatePosters(posters, posterType);
        }
    }

/**
 * void onPageScrollStateChanged(int) - does nothing
 * @param state - state of page scroll
 */
    public void onPageScrollStateChanged(int state){
        //does nothing
    }

/**
 * void onPageScrolled(int,float,int) - does nothing
 * @param position - initial position
 * @param positionOffset - change of position
 * @param positionOffsetPixels - change of pixel position
 */
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
        //does nothing
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      void updatePoster(ArrayList<PosterItem>,int) - update poster fragment with poster item data
 *      void onFABClick - floatingActionButton is clicked, refresh poster item data
 */
/**************************************************************************************************/
/**
 * void updatePoster(ArrayList<PosterItem>, int) - update poster fragment with poster item data. If
 * poster item data is buffered will be called onPageSelected(). If there is no poster item data
 * buffered, Boss will start an AsyncTask to get the data either from the database or through an
 * API call. Once that task is done, this method will be called.
 * @param posters - list of PosterItem data requested
 * @param request - type of movie posters requested
 */
    public void updatePosters(ArrayList<PosterItem> posters, int request){
        //get Maid responsible for displaying the type of movie posters requested
        PosterMaid maid = ((PosterMaid)mBoss.getMaid(request));

        //update the Fragment being maintained by the Maid
        maid.updatePosters(posters);
    }

/**
 * void onFABClick() - the floatingActionButton has been clicked, user want to refresh poster
 * item data.
 */
    public void onFABClick(){
        //make API call to get fresh poster item data for current poster fragment
        mBoss.refreshPosters(mMovieIndex);
    }

/**************************************************************************************************/

}
