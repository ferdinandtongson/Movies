package me.makeachoice.movies.controller.viewside.housekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.view.activity.DetailActivity;
import me.makeachoice.movies.view.activity.MyActivity;
import me.makeachoice.movies.view.activity.SwipeActivity;
import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.assistant.MaidAssistant;
import me.makeachoice.movies.controller.viewside.helper.DetailHelper;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.controller.viewside.helper.SwipeHelper;
import me.makeachoice.movies.controller.viewside.maid.PosterMaid;
import me.makeachoice.movies.controller.viewside.adapter.SwipeAdapter;

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
 *      void onPosterClicked(int, int) - movie poster selected
 *      void onPosterLongClicked(int, int) - movie poster long-clicked
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
 *      int mPageIndex - page index of current movie list being shown
 *      View.OnClickListener mFabListener - onClick listener for FloatingActionButton
 */
/**************************************************************************************************/

    //mViewHolder - holds all the child views of the fragment
    private SwipeHelper.ViewHolder mViewHolder;

    //mPageIndex - page index of current movie list being shown
    private int mPageIndex;

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
        //mBoss.registerHouseKeeper(this, SwipeHelper.NAME_ID);

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
 * SwipeActivity.Bridge implementations:
 *      void create(Bundle) - create activity layout
 *          void createViewPager(MyActivity,int) - create ViewPager and SwipeAdapter objects
 *      void createOptionsMenu(Menu menu) - does nothing
 *      void postResume() - does nothing
 *      void saveInstanceState(Bundle) - save instance state to bundle
 *      void backPressed() - back button has been pressed
 *      void optionsItemSelected(MenuItem item) - does nothing
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
            mPageIndex = savedInstanceState.getInt(
                    mBoss.getString(SwipeHelper.KEY_MOVIE_INDEX));
        }
        else{
            //default movie index = 0
            mPageIndex = 0;
        }

        //set activity layout
        activity.setContentView(SwipeHelper.SWIPE_LAYOUT_ID);

        //fragmentManager is context sensitive, need to recreate every time onCreate() is called
        mFragmentManager = activity.getSupportFragmentManager();

        //Fab is context sensitive, need to recreate every time onCreate() is called
        mFab = getFloatButton(activity, SwipeHelper.SWIPE_FAB_ID, mFabListener);

        //create ViewPager and SwipeAdapter
        createViewPager(activity, mPageIndex);
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
        inState.putInt(mBoss.getString(SwipeHelper.KEY_MOVIE_INDEX), mPageIndex);
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
 *      void registerFragment(Integer,Fragment) - implemented by MyHouseKeeper
 *      void onPosterClicked(int, int) - onPosterClicked event, show detailed info of movie
 *      void onPosterLongClicked(int,int) - onPosterLongClicked event
 */
/**************************************************************************************************/
/**
 * void onSelectedPoster(int) - event called when a poster is selected; starts DetailActivity
 * @param movieType - id number of the Maid used as movieType where the poster selection occurred
 * @param index - position of poster
 */
    public void onPosterClicked(int movieType, int index){
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

    public void onPosterLongClicked(int movieType, int index){
        //get movie type
        switch (movieType) {
            //save Most Popular poster
            case PosterHelper.NAME_ID_MOST_POPULAR:
            //get Top Rated posters
            case PosterHelper.NAME_ID_TOP_RATED:
            //get Now Playing posters
            case PosterHelper.NAME_ID_NOW_PLAYING:
            //get Upcoming posters
            case PosterHelper.NAME_ID_UPCOMING:
                mBoss.addToFavorite(movieType, index);
                break;
            //default request Favorite
            case PosterHelper.NAME_ID_FAVORITE:
                mBoss.removeFavorite(index);
                break;
        }

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
 * @param index - index of fragment being viewed by user
 */
    public void onPageSelected(int index){
        //save swipe page index
        mPageIndex = index;

        //initialize arrayList to hold movie items
        ArrayList<MovieItem> movies = new ArrayList<>();

        //get poster list from Boss, if empty will start AsyncTask to get poster data, calls
        //updatePosters() after AsyncTask completes
        switch (index){
            //get Most Popular posters
            case 0: movies = mBoss.getMovies(PosterHelper.NAME_ID_MOST_POPULAR);
                break;
            //get Top Rated posters
            case 1: movies = mBoss.getMovies(PosterHelper.NAME_ID_TOP_RATED);
                break;
            //get Now Playing posters
            case 2: movies = mBoss.getMovies(PosterHelper.NAME_ID_NOW_PLAYING);
                break;
            //get Upcoming posters
            case 3: movies = mBoss.getMovies(PosterHelper.NAME_ID_UPCOMING);
                break;
            //get Favorite posters
            case 4: movies = mBoss.getMovies(PosterHelper.NAME_ID_FAVORITE);
        }

        //check poster list size
        if(movies.size() > 0){
            //have poster in local buffer, update Poster fragment
            updateMovies(movies, convertIndexToMovieType(index));
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
 *      void updateMovies(ArrayList<MovieItem>,int) - update poster fragment with movie item data
 *      void onFABClick - floatingActionButton is clicked, refresh poster item data
 *      int convertIndexToMovieType - converts to current swipe page index into a movie type
 */
/**************************************************************************************************/
/**
 * void updatePoster(ArrayList<PosterItem>, int) - update poster fragment with movie item data.
 * @param movies - list of MovieItem data requested
 * @param request - type of movie posters requested
 */
    public void updateMovies(ArrayList<MovieItem> movies, int request){
        //get Maid responsible for displaying the type of movie posters requested
        PosterMaid maid = ((PosterMaid)mBoss.hireMaid(request));

        //update the Fragment being maintained by the Maid
        maid.updateMovies(movies);
    }

/**
 * void onFABClick() - the floatingActionButton has been clicked, user want to refresh poster
 * item data.
 */
    public void onFABClick(){
        //make API call to get fresh poster item data for current poster fragment
        mBoss.refreshPosters(convertIndexToMovieType(mPageIndex));
    }

/**
 * int convertIndexToMovieType(int) - converts the current swipe page index into the current
 * movie type being displayed.
 * @param index - current swipe page index
 * @return - movie type being displayed
 */
    private int convertIndexToMovieType(int index){
        //get movie type from index
        switch (index) {
            //get Most Popular posters
            case 0: return PosterHelper.NAME_ID_MOST_POPULAR;
            //get Top Rated posters
            case 1: return PosterHelper.NAME_ID_TOP_RATED;
            //get Now Playing posters
            case 2: return PosterHelper.NAME_ID_NOW_PLAYING;
            //get Upcoming posters
            case 3: return PosterHelper.NAME_ID_UPCOMING;
            //default request Favorite
            case 4: return PosterHelper.NAME_ID_FAVORITE;
        }

        //invalid page index
        return 0;
    }

/**************************************************************************************************/


}
