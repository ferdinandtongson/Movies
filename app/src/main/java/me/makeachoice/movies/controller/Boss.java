package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

import me.makeachoice.movies.controller.butler.MovieButler;
import me.makeachoice.movies.controller.butler.staff.HouseKeeperStaff;
import me.makeachoice.movies.controller.butler.staff.MovieStaff;
import me.makeachoice.movies.controller.butler.staff.RefreshStaff;
import me.makeachoice.movies.controller.butler.valet.PosterValet;
import me.makeachoice.movies.controller.butler.valet.RefreshValet;
import me.makeachoice.movies.controller.butler.staff.PosterStaff;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.db.MovieDB;
import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.controller.butler.DetailButler;
import me.makeachoice.movies.controller.housekeeper.DetailKeeper;
import me.makeachoice.movies.controller.housekeeper.MyHouseKeeper;
import me.makeachoice.movies.controller.housekeeper.SwipeKeeper;
import me.makeachoice.movies.controller.housekeeper.helper.DetailHelper;
import me.makeachoice.movies.controller.housekeeper.helper.SwipeHelper;
import me.makeachoice.movies.controller.housekeeper.maid.MyMaid;
import me.makeachoice.movies.util.DateManager;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;
import me.makeachoice.movies.view.dialog.WaitDialog;


/**
 * Boss is the "boss", main controller of the app and interfaces with the View and Model. Boss
 * tries to adhere to the MVP (Model-View-Presenter) model so Model-View communication is
 * prevented; in MVC (Model-View-Controller) model, the Model and View can communicate
 */

public class Boss extends Application implements PosterValet.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Context mActivityContext - current Activity Context on display
 *      MovieDB mMovieDB - in charge of maintaining the Movie app database
 *      SQLiteDatabase mDB - SQLiteDatabase object
 *
 *      MovieStaff mMovieStaff - staff in charge of maintaining the Movie buffers
 *      PosterStaff mPosterStaff - staff in charge of maintaining the Poster buffers
 *      RefreshStaff mRefreshStaff - staff in charge of maintaining the poster refresh buffers
 *      HouseKeeperStaff mKeeperStaff - staff in charge of maintain the MyHouseKeeper buffer
 *
 *      MovieButler mMovieButler - butler in charge of making API calls to get movie list data
 *      DetailButler mDetailButler - butler in charge of making API calls to get movie detail data
 *
 *      PosterValet mPosterValet - valet in charge of getting poster database data
 *      RefreshValet mRefreshValet - valet in charge of getting poster refresh database data
 *
 *      NetworkManager mNetworkValet - in charge of checking for network connectivity
 *      HashMap<Integer,MyHouseKepeper> mHouseKeeperRegistry - registered HouseKeepers
 *      HashMap<Integer,MyMaid> mMaidRegistry - registered Maids
 *
 *      boolean mOrientationChange - status flag on whether the phone orientation has changed
 */
/**************************************************************************************************/

    //mActivityContext - current Activity Context on display
    private Context mActivityContext;
    //mMovieDB - in charge of maintaining the Movie app database
    private MovieDB mMovieDB;
    //mDB - SQLiteDatabase object
    private SQLiteDatabase mDB;


    //mMovieStaff - staff in charge of maintaining the Movie buffers
    private MovieStaff mMovieStaff;
    //mPosterStaff - staff in charge of maintaining the Poster buffers
    private PosterStaff mPosterStaff;
    //mRefreshStaff - staff in charge of maintaining the poster refresh buffers
    private RefreshStaff mRefreshStaff;
    //HouseKeeperStaff mKeeperStaff - staff in charge of maintain the MyHouseKeeper buffer
    private HouseKeeperStaff mKeeperStaff;

    //mMovieButler - butler in charge of making API calls to get movie list data
    private MovieButler mMovieButler;
    //mDetailButler - butler in charge of making API calls to get movie detail data
    private DetailButler mDetailButler;

    //mPosterValet - valet in charge of getting poster database data
    private PosterValet mPosterValet;
    //mRefreshValet - valet in charge of getting poster refresh database data
    private RefreshValet mRefreshValet;

    //mPosterRetrieval - status flag on whether a poster db retrieval has been requested
    private boolean mPosterRetrieval;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Start Up Methods:
 *      void onCreate() - called when application is first created
 *      void initStaff() - initialize Staff classes, maintains ArrayList and HashMap buffers
 *      void initButlers() - initialize Butler classes, handles calls to APIs
 *      void initValets() - initialize Valet classes, handles request to DB
 *      void startUpApp() - creates/check database for any saved data to put into local buffers
 */
/**************************************************************************************************/
/**
 * void onCreate() - called when application is first created. Initializes the Staff and Butler
 * classes. Checks the database if there is any saved data to put into local buffers; creates
 * the database if it does not exist
 */
    @Override
    public void onCreate(){
        super.onCreate();
        //initialize Staff classes
        initStaff();

        //initialize Butler classes
        initButlers();

        //initialize Valet classes
        initValets();

        //initialize database manager
        mMovieDB = new MovieDB(this);

        //will create database if necessary
        mDB = mMovieDB.getWritableDatabase();

        mWaitDialog = new WaitDialog();
    }

/**
 * void initStaff() - initializes Staff classes; they maintain ArrayList and HashMap buffers
 */
    private void initStaff(){
        //wake movie staff, maintains ArrayList MovieModel and MovieItem buffers
        mMovieStaff = new MovieStaff(this);

        //wake poster staff, maintains ArrayList PosterItem buffers
        mPosterStaff = new PosterStaff(this);

        //wake refresh staff, maintains HashMap RefreshItem buffers
        mRefreshStaff = new RefreshStaff(this);

        //wake house keeper staff, maintains HashMap MyHouseKeeper buffer
        mKeeperStaff = new HouseKeeperStaff(this);
    }

/**
 * void initButlers() - initializes Butler classes; they handle making and processing API calls
 */
    private void initButlers(){
        //initialize MovieButler, makes API calls for a list of movies in a given category
        mMovieButler = new MovieButler(this);

        //initialize DetailButler, makes API calls to get more detailed info about a given movie
        mDetailButler = new DetailButler(this);

    }

/**
 * void initValets() - initialize Valet classes; they handle database requests
 */
    private void initValets(){
        mRefreshValet = new RefreshValet(this);
        mPosterValet = new PosterValet(this);
    }

/**************************************************************************************************/


    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        Log.d("Movies", "Boss.onConfigurationChanged: ");
    }

    public void onFinish(){

        //savePosters();

        mMovieStaff.onFinish();
        mPosterStaff.onFinish();
        mRefreshStaff.onFinish();
        mKeeperStaff.onFinish();

        mDB.close();
    }



    private WaitDialog mWaitDialog;


    //mMaidRegistry - HashMap of instantiated Maid classes being used by HouseKeepers
    private HashMap<Integer, MyMaid> mMaidRegistry = new HashMap<>();

    //mOrientation - current orientation of phone
    private int mOrientation;

    //mOrientationChange - status flag on whether the phone orientation has changed
    private boolean mOrientationChange;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity Context
 *      int getOrientation() - get current orientation of phone
 *      boolean getOrientationChanged() - get status flag for phone orientation changed
 *      ArrayList<PosterItem> getPosters(int) - get list of poster item data
 *      MovieItem getMovie(int, int) - get movie item data
 *      MyHouseKeeper getHouseKeeper(Integer) - get houseKeeper from registry
 *      MyMaid getMaid(Integer) - get Maid from registry
 */
/**************************************************************************************************/
/**
 * Context getActivityContext - get current Activity Context
 * @return - context of current activity
 */
    public Context getActivityContext(){
        return mActivityContext;
    }

    public SQLiteDatabase getDatabase(){
        return mDB;
    }

/**
 * int getOrientation() - get current orientation of phone
 * @return - orientation of phone
 */
    public int getOrientation(){ return mOrientation; }

/**
 * boolean getOrientationChanged() - get status flag for phone orientation changed
 * @return - status flag for phone orientation changed
 */
    public boolean getOrientationChanged(){
        return mOrientationChange;
    }

/**
 * ArrayList<PosterItem> getModel(int) - get list of poster item data. If null or is a new request,
 * Butler will start an AsyncTask to get new movie data.
 * @param movieType - type of movie data requested
 * @return - an array list of poster item data
 */
    public ArrayList<PosterItem> getPosters(int movieType){
        Log.d("Boss", "Boss.getPosters: " + getString(movieType));
        mPosterRetrieval = false;

        Log.d("Boss", "     checkBuffer:");
        ArrayList<PosterItem> posters = new ArrayList<>();
        if(mRefreshStaff.needToRefreshList(movieType)){
            //refresh posters, access internet data
            mMovieButler.requestMovies(movieType);
            mWaitDialog.showStartDialog(mActivityContext);
        }
        else{
            posters = mPosterStaff.getPosters(movieType);
            Log.d("Boss", "     list: " + posters.size());

            if (posters.size() == 0) {
                Log.d("Boss", "          retrieve posters from DB");
                //retrieve posters from database
                mPosterValet.requestPosters(movieType);
                mWaitDialog.showStartDialog(mActivityContext);
            }
        }

        //return poster items
        return posters;
    }

    public void refreshPosters(int movieType){
        //refresh posters, access internet data
        mMovieButler.requestMovies(movieType);
    }

    //public void dbRequestComplete

/**
 * void movieRequestComplete() - communication channel used by Butlers to let the Boss
 * know when an AsyncTask thread has completed (in this case for MovieData)
 */
    public void movieRequestComplete(ArrayList<MovieModel> models, int movieType){
        mWaitDialog.closeStartDialog();
        Log.d("Boss", "Boss.movieRequestComplete: " + getString(movieType));
        //convert MovieModels to PosterItems
        ArrayList<PosterItem> posters = mPosterStaff.preparePosters(models);

        Log.d("Boss", "     showPosters");
        //show list of poster items
        showPosters(posters, movieType);

        Log.d("Boss", "     processMovieData");
        processMovieData(models, posters, movieType);
   }

    public void posterRetrievalComplete(ArrayList<PosterItem> posters, int movieType){
        Log.d("Boss", ".");
        Log.d("Boss", ".");
        Log.d("Boss", "Here!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d("Boss", ".");
        Log.d("Boss", ".");
        if(posters.size() >= 20){
            mWaitDialog.closeStartDialog();
            mPosterStaff.setPosters(posters, movieType);

            showPosters(posters, movieType);
        }
        else{
            if(movieType != PosterHelper.NAME_ID_FAVORITE){
                mMovieButler.requestMovies(movieType);
            }
        }
    }

    public void showPosters(ArrayList<PosterItem> posters, int request){
        //instantiate HouseKeeper that will maintain the Main Activity
        SwipeKeeper keeper = (SwipeKeeper)mKeeperStaff.getHouseKeeper(SwipeHelper.NAME_ID);
        //tells the HouseKeeper to prepare the fragments the Activity will use
        keeper.updatePosters(posters, request);
    }

    private void processMovieData(ArrayList<MovieModel> models,
                                  ArrayList<PosterItem> posters, int movieType) {
        Log.d("Boss", "          save movie models to buffer");
        //save movie models to buffer
        mMovieStaff.setMovieModels(models, movieType);

        Log.d("Boss", "          save poster items to buffer");
        //update refresh data
        updateRefreshData(movieType);

        //save poster data to database
        mPosterValet.savePosters(posters, movieType);

        //save poster items to buffer
        mPosterStaff.setPosters(posters, movieType);
    }

    private void updateRefreshData(int movieType){
        Log.d("Boss", "Boss.updateRefreshData: " + getString(movieType));
        String strType = getString(movieType);
        Long dateRefresh = DateManager.addDaysToDate(1).getTime();


        mRefreshValet.setRefresh(movieType, dateRefresh);


        mRefreshStaff.setRefreshDate(movieType, dateRefresh);
    }



    /**
 * MovieItem getMovie(int, int) - get movie item data. If the movie item data is incomplete, will
 * start an AsyncTask to get the missing data.
 * @param movieType - type of movies the movie is selected from
 * @param position - index location of the selected movie
 * @return - movie item data of the selected movie
 */
    public MovieItem getMovie(int movieType, int position){
        //get the movie model from the MovieButler
        MovieModel model = mMovieStaff.getModel(movieType, position);

        //get the movie item data from DetailButler, if incomplete will start an AsyncTask
        return mDetailButler.getMovie(model);
    }

/**
 * MyMaid getMaid(String) - request a reference to a Maid
 * @param key - Maid name
 * @return - sends Maid reference
 */
    public MyMaid getMaid(Integer key){
        //send Maid
        return mMaidRegistry.get(key);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setOrientation(int) - save current phone orientation
 *      void setOnOrientationChange(boolean) - saves orientation change status of Activity
 */
/**************************************************************************************************/
/**
 * void setOrientation(int) - save current phone orientation
 * @param orientation - orientation status of phone
 */
    public void setOrientation(int orientation){
        //save orientation of phone
        mOrientation = orientation;
    }

/**
 * void setOnOrientationChange(boolean) - saves orientation change status of Activity
 * @param changed - boolean value on whether the orientation of phone has changed or not
 */
    public void setOnOrientationChange(boolean changed){
        //orientation change flag
        mOrientationChange = changed;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      void registerMaid(int,MyMaid) - adds Maid to registry
 *      void activityCreated(Context) - notifies Boss that onCreate() has been called in Activity
 *      boolean checkNetwork() - checks network connection of phone
 *      void updateMainActivity() - called by Butler when network download has completed
 */
/**************************************************************************************************/
/**
 * void registerMaid(String, MyMaid) - registers Maids being used by HouseKeepers
 * @param key - Maid name
 * @param maid - Maid class (in charge of maintaining fragments)
 */
    public void registerMaid(Integer key, MyMaid maid){
        //register Maid
        mMaidRegistry.put(key, maid);
    }

/**
 * void activityCreated(Context) - called when the onCreate() of an Activity is called, stores the
 * Activity Context and makes sure the Butler for activity is awake.
 * @param ctx - Activity context
 */
    public void activityCreated(Context ctx){
        Log.d("Movies", "Boss.activityCreated");
        //set Activity context - in this case there is only MainActivity
        mActivityContext = ctx;
    }


    public void updateDetailActivity(MovieItem movie){
        DetailKeeper keeper = (DetailKeeper)mKeeperStaff.getHouseKeeper(DetailHelper.NAME_ID);
        keeper.updateDetails(movie);
    }


/**************************************************************************************************/


/**************************************************************************************************/
/**
 * HouseKeeper Methods:
 *      MyHouseKeeper getHouseKeeper(int) - get HouseKeeper object
 *      MyHouseKeeper hireHouseKeeper(int) - start HouseKeeper object, requested by Activity
 *      void registerHouseKeeper(MyHouseKeeper,int) - register HouseKeeper
 */
/**************************************************************************************************/
/**
 * MyHouseKeeper getHouseKeeper(int) - get HouseKeeper object
 * @param id - id number of HouseKeeper
 * @return - HouseKeeper object
 */
    private MyHouseKeeper getHouseKeeper(int id){
        //return houseKeeper
        return mKeeperStaff.getHouseKeeper(id);
    }

/**
 * MyHouseKeeper hireHouseKeeper(int) - start HouseKeeper object, requested by Activity
 * @param id - id number of HouseKeeper
 * @return - HouseKeeper object
 */
    public MyHouseKeeper hireHouseKeeper(int id){
        return mKeeperStaff.startHouseKeeper(id);
    }

/**
 * void registerHouseKeeper(MyHouseKeeper,int) - register HouseKeeper
 * @param keeper - HouseKeeper class (in charge of Activity objects)
 * @param id - id number of HouseKeeper
 */
    public void registerHouseKeeper(MyHouseKeeper keeper, int id){
        //register HouseKeeper
        mKeeperStaff.setHouseKeeper(keeper, id);
    }



/**************************************************************************************************/


    public Executor getExecutor(){
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

}
