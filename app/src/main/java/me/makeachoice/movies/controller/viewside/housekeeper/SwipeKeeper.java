package me.makeachoice.movies.controller.viewside.housekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.R;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.util.NetworkManager;
import me.makeachoice.movies.view.activity.DetailActivity;
import me.makeachoice.movies.view.activity.MyActivity;
import me.makeachoice.movies.view.activity.SwipeActivity;
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
 *      void create(Bundle)
 *      void saveInstanceState(Bundle)
 *      void backPressed()
 *
 * Implements SwipeAdapter.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *
 * Implements PosterMaid.Bridge
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      void onMovieClicked(int, int) - movie poster clicked on
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
 *      int mPageIndex - page index of current movie list being shown
 *      MyActivity.Bridge mDetailBridge - communication Bridge to DetailKeeper
 *      View.OnClickListener mFabListener - onClick listener for FloatingActionButton
 */
/**************************************************************************************************/

    //mPageIndex - page index of current movie list being shown
    private int mPageIndex;

    //mDetailBridge - communication Bridge to DetailKeeper
    private DetailKeeper mDetailBridge;

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

        //initialize fragment registry
        mFragmentRegistry = new HashMap<>();

        //initialize MaidAssistant
        mMaidAssistant = new MaidAssistant();

        //initialize all Maids used by the SwipeKeeper
        mMaidAssistant.hireSwipeMaids(mBoss, this);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * SwipeActivity.Bridge implementations:
 *      void create(Bundle) - create activity layout
 *          void createViewPager(MyActivity,int) - create ViewPager and SwipeAdapter objects
 *      void saveInstanceState(Bundle) - save instance state to bundle
 *      void backPressed() - back button has been pressed
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

        //get tablet view layout
        View tabletView = activity.findViewById(SwipeHelper.TABLET_LAYOUT_ID);

        //check if tablet view is valid
        if(tabletView != null){
            //tablet view is valid, device is a tablet, set isTablet = true
            mBoss.setIsTablet(true);

            //create communication Bridge with DetailKeeper
            mDetailBridge = (DetailKeeper)mBoss.hireHouseKeeper(DetailHelper.NAME_ID);
        }
        else{
            //tablet view is null, device is a phone, set isTablet = false
            mBoss.setIsTablet(false);
        }

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

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterMaid.Bridge implementations:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer,Fragment) - implemented by MyHouseKeeper
 *      void onMovieClicked(int, int) - movie has been clicked on.
 */
/**************************************************************************************************/
/**
 * void onMovieClicked(int) - movie has been clicked on. Inform Boss of the event then, if the
 * device is a tablet, will get the Movie Item selected and let Boss know that a movie has been
 * selected for a tablet. If the device is a phone, will start DetailActivity
 * @param movieType - id number of the Maid used as movieType where the poster selection occurred
 * @param index - position of poster
 */
    public void onMovieClicked(int movieType, int index){
        //inform Boss of Movie selection
        mBoss.onMovieClicked(movieType, index);

        //check if device is tablet or phone
        if(mBoss.getIsTablet()){
            //get movie item of movie selected
            MovieItem movie = mBoss.getSelectedMovie();

            //inform Boss that an onSelectTabletMovie has occurred
            mBoss.onSelectTabletMovie(movie);
        }
        else{

            //create intent to start DetailActivity
            Intent intent = new Intent(mBoss.getActivityContext(), DetailActivity.class);

            //start DetailActivity
            mBoss.getActivityContext().startActivity(intent);
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
                //set fab as visible
                mFab.setVisibility(View.VISIBLE);
                break;
            //get Top Rated posters
            case 1: movies = mBoss.getMovies(PosterHelper.NAME_ID_TOP_RATED);
                //set fab as visible
                mFab.setVisibility(View.VISIBLE);
                break;
            //get Now Playing posters
            case 2: movies = mBoss.getMovies(PosterHelper.NAME_ID_NOW_PLAYING);
                //set fab as visible
                mFab.setVisibility(View.VISIBLE);
                break;
            //get Upcoming posters
            case 3: movies = mBoss.getMovies(PosterHelper.NAME_ID_UPCOMING);
                //set fab as visible
                mFab.setVisibility(View.VISIBLE);
                break;
            //get Favorite posters
            case 4: movies = mBoss.getMovies(PosterHelper.NAME_ID_FAVORITE);
                //set fab as visible
                mFab.setVisibility(View.INVISIBLE);
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
 *      void updateTabletDetail(ArrayList<MovieItem>) - update Tablet detail fragment
 *      void onFABClick - floatingActionButton is clicked, refresh poster item data
 *      int convertIndexToMovieType - converts to current swipe page index into a movie type
 */
/**************************************************************************************************/
/**
 * void updatePoster(ArrayList<PosterItem>, int) - update poster fragment with new movie item data.
 * If the device is a tablet, it will also update the Detail fragment.
 * @param movies - list of MovieItem data requested
 * @param request - type of movie posters requested
 */
    public void updateMovies(ArrayList<MovieItem> movies, int request){
        //get Maid responsible for displaying the type of movie posters requested
        PosterMaid maid = ((PosterMaid)mBoss.hireMaid(request));

        //update the movie posters of the fragment being maintained by the Maid
        maid.updateMovies(movies);

        //check if the device is a tablet
        if(mBoss.getIsTablet()){
            //device is a tablet, update movie detail fragment
            updateTabletDetail(movies);
        }
    }

/**
 * void updateTabletDetail(ArrayList<MovieItem>) - update Tablet detail fragment. Check the new
 * list of movies sent. If list is not empty, select the 1st movie in the list to be displayed
 * in the detail fragment. If the list is empty, select an empty movie item to be displayed
 * @param movies - list of new movies to be displayed in swipe fragment
 */
    private void updateTabletDetail(ArrayList<MovieItem> movies){
        //check movie list size
        if(movies.size() > 0){
            //if there is a list of new movies, select the 1st movie to display, inform Boss
            mBoss.onSelectTabletMovie(movies.get(0));
        }
        else{
            //list is empty, set selected movie as empty
            mBoss.setEmptySelectedMovie();
        }

        //update detail fragment
        mDetailBridge.create((MyActivity)mBoss.getActivityContext(), null);
    }

/**
 * void onFABClick() - the floatingActionButton has been clicked, user wants to refresh movie
 * item data with new internet data.
 */
    public void onFABClick(){
        //check if network connection is available
        if(NetworkManager.hasConnection(mBoss.getActivityContext())){
            //make API call to get fresh poster item data for current poster fragment
            mBoss.refreshPosters(convertIndexToMovieType(mPageIndex));
        }
        else{
            //if network is not available, check if it's not favorite fragment
            if(mPageIndex != 4){
                //display "No Network" message
                Toast.makeText(mBoss.getActivityContext(),
                        mBoss.getString(R.string.str_no_network), Toast.LENGTH_LONG).show();
            }
        }
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
