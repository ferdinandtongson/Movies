package me.makeachoice.movies.controller.viewside.housekeeper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.HashMap;

import me.makeachoice.movies.R;
import me.makeachoice.movies.model.item.VideoItem;
import me.makeachoice.movies.view.activity.DetailActivity;
import me.makeachoice.movies.view.activity.MyActivity;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.assistant.MaidAssistant;
import me.makeachoice.movies.controller.viewside.helper.DetailHelper;
import me.makeachoice.movies.controller.viewside.helper.InfoHelper;
import me.makeachoice.movies.controller.viewside.helper.ReviewHelper;
import me.makeachoice.movies.controller.viewside.helper.VideoHelper;
import me.makeachoice.movies.controller.viewside.maid.InfoMaid;
import me.makeachoice.movies.controller.viewside.adapter.DetailAdapter;
import me.makeachoice.movies.controller.viewside.maid.ReviewMaid;
import me.makeachoice.movies.controller.viewside.maid.VideoMaid;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.view.dialog.ReviewDialog;

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
 *      int getOrientation()
 *      void registerFragment(Integer key, Fragment fragment)
 *      Toolbar getToolbar(MyActivity, int)
 *      FloatingActionButton getFloatButton(MyActivity, int, View.OnClickListener)
 *
 * Implements DetailActivity.Bridge:
 *      void create(Bundle)
 *      saveInstanceState(Bundle)
 *      void backPressed()
 *
 * Implements DetailAdapter.Bridge:
 *      Context getActivityContext() - implemented by MyHouseKeeper

 * Implements Maid.Bridge:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer, Fragment) - implemented by MyHouseKeeper
 *      void onReviewClicked(int) - implemented for ReviewMaid.Bridge
 *      void onVideoClicked(int) - implemented for VideoMaid.Bridge
 *
 */
public class DetailKeeper extends MyHouseKeeper implements DetailActivity.Bridge,
        DetailAdapter.Bridge, InfoMaid.Bridge, ReviewMaid.Bridge, VideoMaid.Bridge,
        ViewPager.OnPageChangeListener{

/**************************************************************************************************/
/**
 * Class Variables:
 *      MovieItem mMovie - movie item data to be displayed
 *      int mStarStatus - used to determine if the movie is a user favorite
 *      int mPageIndex - index of page being shown
 *      View.OnClickListener mFabListener - FloatingActionButton onClick listener
 *
 *      int INDEX_INFO - index number of info fragment page
 *      int INDEX_REVIEWS - index number of review fragment page
 *      int INDEX_VIDEOS - index number of video fragment page
 */
/**************************************************************************************************/

    //mMovie - movie item data to be displayed
    private MovieItem mMovie;

    //mStarStatus - used to determine if the movie is a user favorite
    private int mStarStatus;

    //mPageIndex - index of page being shown
    private int mPageIndex;

    //mStrShare - string message "Share link!"
    private String mStrShare;

    //mFabListener - onClick listener for FloatingActionButton
    private View.OnClickListener mFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onFABClick();
        }
    };

    //INDEX_INFO - index number of info fragment page
    private int INDEX_INFO = 0;
    //INDEX_REVIEWS - index number of review fragment page
    private int INDEX_REVIEWS = 1;
    //INDEX_VIDEOS - index number of video fragment page
    private int INDEX_VIDEOS = 2;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * DetailKeeper - constructor, registers to Boss, initialize Maid and Fragment Assistant classes
 * and initializes class variables if any
 * @param boss - Boss class
 */
    public DetailKeeper(Boss boss){

        //set Boss
        mBoss = boss;

        //get "Share link!" string
        mStrShare = boss.getString(DetailHelper.STR_SHARE_ID);

        //initialize fragment registry
        mFragmentRegistry = new HashMap<>();

        //initialize MaidAssistant
        mMaidAssistant = new MaidAssistant();

        //initialize all Maids used by the SwipeKeeper
        mMaidAssistant.hireDetailMaids(mBoss, this);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Maid.Bridge implementations:
 *      Context getActivityContext() - implemented by MyHouseKeeper
 *      void registerFragment(Integer key, Fragment fragment) - implemented by MyHouseKeeper
 *      void onReviewClicked(int) [ReviewMaid only] - review clicked, open dialog to see review
 *      void onVideoClicked(int) [VideoMaid only] - video clicked, start activity to watch video
 */
/**************************************************************************************************/
/**
 * void onReviewClicked(int) - review selected, open dialog to see review
 * @param position - index position of review selected
 */
    public void onReviewClicked(int position){
        //get current activity
        Activity activity = (Activity)mBoss.getActivityContext();

        //create review dialog with review item data selected
        ReviewDialog mDialog = new ReviewDialog(activity, mMovie.getReviews().get(position));

        //make dialog background transparent
        mDialog.getWindow().setBackgroundDrawable
                (new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //show dialog
        mDialog.show();
    }

/**
 * void onVideoClicked(int) - start activity to display video selected
 * @param position - index position of selected video
 */
    public void onVideoClicked(int position){
        //get url path of video selected
        String videoPath = mMovie.getVideos().get(position).videoPath;

        //create intent
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(videoPath));

        //start activity to display video selected
        mBoss.getActivityContext().startActivity(intent);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implements Bridge Interface from MainActivity:
 *      void create(Bundle) - create activity layout
 *      void postResume() - both activity and fragments have resumed
 *      void saveInstanceState(Bundle) - save instance state to bundle
 *      void backPressed() - back button has been pressed
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

        //check if tablet device is being used
        if(!mBoss.getIsTablet()){
            //not a tablet, set activity layout
            activity.setContentView(DetailHelper.DETAIL_LAYOUT_ID);
        }

        //create FloatActionButton
        mFab = getFloatButton(activity, DetailHelper.DETAIL_FAB_ID, mFabListener);

        //update movie details with movie selected by user
        updateDetails(mBoss.getMovieSelected());

        //fragmentManager is context sensitive, need to recreate every time onCreate() is called
        mFragmentManager = activity.getSupportFragmentManager();

        //create FragmentPagerAdapter for viewPager
        DetailAdapter pageAdapter = new DetailAdapter(this, mFragmentManager, mFragmentRegistry);

        //get viewPager
        ViewPager viewPager = (ViewPager)activity.findViewById(DetailHelper.DETAIL_PAGER_ID);
        //set DetailKeeper as onPageChangeListener
        viewPager.addOnPageChangeListener(this);

        //set adapter in viewPager
        viewPager.setAdapter(pageAdapter);

        //set Page Index default value
        mPageIndex = 0;

        //update Page Selected
        onPageSelected(mPageIndex);
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
        //notify Boss that onBackPress event happened in DetailActivity
        mBoss.backToPosters();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      void updateDetails(MovieItem) - called by Boss when a AsyncTask Movie Info request has
 *          finished
 *      void onFABClick() - called when the floating action button is clicked on.
 *      void setFavoriteStatus() - mark or un-mark the movie as favorite
 *      void shareVideoTrailer() - share first video trailer to a friend
 */
/**************************************************************************************************/
/**
 * void updateDetails(MovieItem) - called when a detailed Movie request AsyncTask has
 * finished.
 * @param movie - movie item data of current movie being viewed
 */
    public void updateDetails(MovieItem movie){
        //save movie item currently being shown
        mMovie = movie;

        //get InfoMaid responsible for displaying info about the movie
        InfoMaid infoMaid = (InfoMaid)mBoss.hireMaid(InfoHelper.NAME_ID);
        //update info fragment
        infoMaid.updateViews(movie);

        //get ReviewMaid responsible for displaying reviews about the movie
        ReviewMaid reviewMaid = (ReviewMaid)mBoss.hireMaid(ReviewHelper.NAME_ID);
        //update review fragment
        reviewMaid.updateReviews(movie.getReviews());

        //get VideoMaid responsible for displaying trailer videos of the movie
        VideoMaid videoMaid = (VideoMaid)mBoss.hireMaid(VideoHelper.NAME_ID);
        //update video fragment
        videoMaid.updateVideos(movie.getVideos());

        //check if movie is a user favorite
        if(mMovie.getFavorite()){
            //is favorite, give star status full start
            mStarStatus = DetailHelper.DRW_STAR_WHITE_ID;
        }
        else{
            //is Not favorite, give star status a bordered star
            mStarStatus = DetailHelper.DRW_STAR_BORDER_ID;
        }

        //update Page Selected
        onPageSelected(mPageIndex);
    }

/**
 * void onFABClick() - the floatingActionButton has been clicked, can mark or un-mark the movie as
 * favorite or share a video trailer to a friend.
 */
    public void onFABClick(){
        //check if page is the info fragment page
        if(mPageIndex == INDEX_INFO){
            //is info fragment page, make sure movie item is not empty
            if(!mMovie.getTitle().isEmpty()){
                //valid movie item, set movie favorite status
                setFavoriteStatus();
            }
        }
        //check if page is the video fragment page
        else if(mPageIndex == INDEX_VIDEOS){
            //is video fragment page, share first trailer to a friend
            shareVideoTrailer();
        }
    }

/**
 * void setFavoriteStatus() - mark or un-mark the movie as favorite. Checks the star status of
 * the movie and marks or un-marks the movie as a favorite.
 */
    private void setFavoriteStatus(){
        //check star status of movie
        if(mStarStatus == DetailHelper.DRW_STAR_WHITE_ID){
            //previously marked as favorite, un-mark movie; movie is no longer a favorite
            mStarStatus = DetailHelper.DRW_STAR_BORDER_ID;
            //notify boss to remove movie from favorite list
            mBoss.removeFavorite();
        }
        else{
            //previously un-marked, mark movie; movie is now a favorite
            mStarStatus = DetailHelper.DRW_STAR_WHITE_ID;
            //notify boss to save movie into favorite list
            mBoss.saveFavorite();
        }

        //update floating action bar icon
        mFab.setImageResource(mStarStatus);

        //refresh floating action bar to reflect icon change
        mFab.refreshDrawableState();
    }

/**
 * void shareVideoTrailer() - share first video trailer to a friend
 */
    private void shareVideoTrailer(){
        //get number of video trailer the movie has
        int count = mMovie.getVideos().size();

        //check if there is at least 1 video trailer
        if(count > 0){
            //has videos, get first video trailer for the movie
            VideoItem video = mMovie.getVideos().get(0);

            //create a share intent
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            //set intent type
            share.setType("text/plain");

            //set subject, name of video trailer
            share.putExtra(Intent.EXTRA_SUBJECT, video.name);
            //set text, url video path of trailer
            share.putExtra(Intent.EXTRA_TEXT, video.videoPath);

            //start intent
            mBoss.getActivityContext().startActivity(Intent.createChooser(share, mStrShare));
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
 * void onPageSelected(int) - updates the FloatingActionButton icon and visibility depending on the
 * fragment page being displayed
 * @param index - index of fragment being viewed by user
 */
    public void onPageSelected(int index){
        //save page index being shown
        mPageIndex = index;

        //variable to hold fab visibility, default visible
        int visibleStatus = View.VISIBLE;

        //check page index being shown
        switch (index){
            //Movie InfoFragment is being shown
            case 0:
                //display star icon, filled or bordered depending on favorite status of movie
                mFab.setImageResource(mStarStatus);
                //set status to visible
                visibleStatus = View.VISIBLE;
                break;
            //get Top Rated posters
            case 1:
                //set status to invisible
                visibleStatus = View.INVISIBLE;
                break;
            //get Now Playing posters
            case 2:
                //mFab.setBackgroundResource(R.drawable.share_white);
                mFab.setImageResource(R.drawable.share_white);

                //check if there are any videos
                if(mMovie.getVideos().size() > 0){
                    //set status to visible
                    visibleStatus = View.VISIBLE;
                }
                else{
                    //set status to invisible
                    visibleStatus = View.INVISIBLE;
                }
                break;
        }

        //set visibility status for fab
        mFab.setVisibility(visibleStatus);

        //refresh floating action bar to reflect icon change
        mFab.refreshDrawableState();

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


}
