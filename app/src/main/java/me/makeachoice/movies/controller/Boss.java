package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import me.makeachoice.movies.controller.modelside.butler.TMDBMoviesButler;
import me.makeachoice.movies.controller.modelside.staff.HouseKeeperStaff;
import me.makeachoice.movies.controller.modelside.staff.MaidStaff;
import me.makeachoice.movies.controller.modelside.staff.MovieStaff;
import me.makeachoice.movies.controller.modelside.staff.RefreshStaff;
import me.makeachoice.movies.controller.modelside.valet.MovieValet;
import me.makeachoice.movies.controller.modelside.valet.RefreshValet;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.db.MovieDB;
import me.makeachoice.movies.controller.modelside.butler.TMDBInfoButler;
import me.makeachoice.movies.controller.viewside.housekeeper.DetailKeeper;
import me.makeachoice.movies.controller.viewside.housekeeper.MyHouseKeeper;
import me.makeachoice.movies.controller.viewside.housekeeper.SwipeKeeper;
import me.makeachoice.movies.controller.viewside.helper.DetailHelper;
import me.makeachoice.movies.controller.viewside.helper.SwipeHelper;
import me.makeachoice.movies.controller.viewside.maid.MyMaid;
import me.makeachoice.movies.util.DateManager;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;


/**
 * Boss is the "boss", main controller of the app and interfaces with the View and Model. Boss
 * tries to adhere to the MVP (Model-View-Presenter) model so Model-View communication is
 * prevented; in MVC (Model-View-Controller) model, the Model and View can communicate
 */

public class Boss extends Application implements MovieValet.Bridge, RefreshValet.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Context mActivityContext - current Activity Context on display
 *      MovieDB mMovieDB - in charge of maintaining the Movie app database
 *      SQLiteDatabase mDB - SQLiteDatabase object
 *
 *      MovieStaff mMovieStaff - staff in charge of maintaining the Movie buffers
 *      RefreshStaff mRefreshStaff - staff in charge of maintaining the poster refresh buffers
 *      HouseKeeperStaff mKeeperStaff - staff in charge of maintain the MyHouseKeeper buffer
 *
 *      TMDBMovieButler mMoviesButler - butler in charge of making API calls to get movie list data
 *      TMDBInfoButler mInfoButler - butler in charge of making API calls to get movie detail data
 *
 *      MovieValet mMovieValet - valet in charge of getting movie database data
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
    //mRefreshStaff - staff in charge of maintaining the poster refresh buffers
    private RefreshStaff mRefreshStaff;
    //mKeeperStaff - staff in charge of maintaining the MyHouseKeeper buffer
    private HouseKeeperStaff mKeeperStaff;
    //mMaidStaff - staff in charge of maintaining the MyMaid buffer
    private MaidStaff mMaidStaff;

    //mMoviesButler - butler in charge of making API calls to get movie list data
    private TMDBMoviesButler mMoviesButler;
    //mInfoButler - butler in charge of making API calls to get movie detail data
    private TMDBInfoButler mInfoButler;

    //mMovieValet - valet in charge of getting movie database data
    private MovieValet mMovieValet;
    //mRefreshValet - valet in charge of getting poster refresh database data
    private RefreshValet mRefreshValet;

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
        //initialize database manager
        mMovieDB = new MovieDB(this);

        //will create database if necessary
        mDB = mMovieDB.getWritableDatabase();

        //initialize Valet classes
        initValets();

        //initialize Staff classes
        initStaff();

        //initialize Butler classes
        initButlers();

    }

/**
 * void initValets() - initialize Valet classes; they handle database requests
 */
    private void initValets(){
        //valet in charge of getting movie database data
        mMovieValet = new MovieValet(this);
        mMovieValet.requestMovies(PosterHelper.NAME_ID_FAVORITE);

        //valet in charge of getting poster refresh database data
        mRefreshValet = new RefreshValet(this);
    }

    /**
 * void initStaff() - initializes Staff classes; they maintain ArrayList and HashMap buffers
 */
    private void initStaff(){
        //wake movie staff, maintains ArrayList MovieModel and MovieItem buffers
        mMovieStaff = new MovieStaff(this);

        //wake refresh staff, maintains HashMap RefreshItem buffers
        mRefreshStaff = new RefreshStaff(this);

        //wake house keeper staff, maintains HashMap MyHouseKeeper buffer
        mKeeperStaff = new HouseKeeperStaff(this);

        //wake maid staff, maintains HashMap MyMaid buffer
        mMaidStaff = new MaidStaff(this);
    }

/**
 * void initButlers() - initializes Butler classes; they handle making and processing API calls
 */
    private void initButlers(){
        //initialize TMDBMoviesButler, makes API calls for a list of movies in a given category
        mMoviesButler = new TMDBMoviesButler(this);

        //initialize TMDBInfoButler, makes API calls to get more detailed info about a given movie
        mInfoButler = new TMDBInfoButler(this);

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
        mRefreshStaff.onFinish();
        mKeeperStaff.onFinish();
        mMaidStaff.onFinish();

        mDB.close();
    }



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

    public Executor getExecutor(){
        return AsyncTask.THREAD_POOL_EXECUTOR;
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
 * ArrayList<PosterItem> getPosters(int) - get list of poster item data. If null or is a new request,
 * Butler will start an AsyncTask to get new movie data.
 * @param movieType - type of movie data requested
 * @return - an array list of poster item data
 */
    public ArrayList<MovieItem> getMovies(int movieType){
        Log.d("Boss", "Boss.getPosters: " + getString(movieType));
        if(movieType == PosterHelper.NAME_ID_FAVORITE){
            return getFavoriteMovies(movieType);
        }
        else{
            return getRequestedMovies(movieType);
        }
    }


    private ArrayList<MovieItem> getFavoriteMovies(int movieType){
        Log.d("Boss", "     get Favorite");
        Log.d("Boss", "          checkBuffer:");
        ArrayList<MovieItem> movies = mMovieStaff.getMovies(movieType);

        if(movies.size() == 0){
            Log.d("Boss", "          check database");
            mMovieValet.requestMovies(movieType);
        }
        return movies;
    }

    private ArrayList<MovieItem> getRequestedMovies(int movieType){

        if(mRefreshStaff.getMapSize() == 0){
            mRefreshStaff.setRefreshMap(mRefreshValet.getRefreshMap());
        }

        ArrayList<MovieItem> movies = new ArrayList<>();
        if(mRefreshStaff.needToRefresh(movieType)){
            //refresh movies, access internet data
            mMoviesButler.requestMovies(movieType);
            Toast.makeText(mActivityContext,"API call to TheMovieDB", Toast.LENGTH_SHORT).show();
        }
        else{
            movies = mMovieStaff.getMovies(movieType);

            if (movies.size() == 0) {
                //retrieve movies from database
                mMovieValet.requestMovies(movieType);
                Toast.makeText(mActivityContext,"Retrieving posters from DB", Toast.LENGTH_SHORT).show();
            }
        }

        //return poster items
        return movies;

    }

    public void refreshPosters(int movieType){
        //refresh posters, access internet data
        mMoviesButler.requestMovies(movieType);
    }

    //public void dbRequestComplete

/**
 * void movieRequestComplete() - communication channel used by Butlers to let the Boss
 * know when an AsyncTask thread has completed (in this case for MovieData)
 */
    public void movieRequestComplete(ArrayList<MovieModel> models, int movieType){
        Log.d("Boss", "Boss.movieRequestComplete: " + getString(movieType));
        //convert MovieModels to PosterItems
        ArrayList<MovieItem> movies = mMovieStaff.prepareMovies(models);

        Log.d("Boss", "     showPosters");
        //show list of poster items
        showMovies(movies, movieType);

        Log.d("Boss", "     processMovieData");
        processMovieData(movies, movieType);
   }

    public void movieRetrievalComplete(ArrayList<MovieItem> movies, int movieType){
        if(movieType == PosterHelper.NAME_ID_FAVORITE){
            processRetrievalFavorite(movies);
        }
        else{
            if(movies.size() > 0){
                showMovies(movies, movieType);
                mMovieStaff.setMovies(movies, movieType);
            }
            else{
                mMoviesButler.requestMovies(movieType);
            }
        }

    }

    private void processRetrievalFavorite(ArrayList<MovieItem> movies){
        int count = movies.size();

        if(count > 0){
            showMovies(movies, PosterHelper.NAME_ID_FAVORITE);
            mMovieStaff.setMovies(movies, PosterHelper.NAME_ID_FAVORITE);

            for(int i = 0; i < count; i++){
                mMovieStaff.addFavoriteId(movies.get(i).getTMDBId());
            }
        }
    }

    public void showMovies(ArrayList<MovieItem> movies, int request){
        //instantiate HouseKeeper that will maintain the Main Activity
        SwipeKeeper keeper = (SwipeKeeper)mKeeperStaff.getHouseKeeper(SwipeHelper.NAME_ID);
        //tells the HouseKeeper to prepare the fragments the Activity will use
        keeper.updateMovies(movies, request);
    }

    private void processMovieData(ArrayList<MovieItem> movies, int movieType) {
        Log.d("Boss", "          save movie models to buffer");
        //
        mMovieStaff.setMovies(movies, movieType);

        mMovieValet.saveMovies(movies, movieType);

        //update refresh data
        updateRefreshData(movieType);

    }

    private void updateRefreshData(int movieType){
        Log.d("Boss", "Boss.updateRefreshData: " + getString(movieType));
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
        Log.d("Boss", "Boss.getMovie: " );
        //get the movie item from the MovieStaff
        mMovie = mMovieStaff.getMovie(movieType, position);
        Log.d("Boss", "     title: " + mMovie.getTitle());

        mMovie.setFavorite(mMovieStaff.alreadyFavorite(mMovie.getTMDBId()));


        //check if movie item has complete data
        if(mMovie.getCast() == null){
            //data is incomplete, start AsyncTask to request movie data
            mInfoButler.requestMovie(mMovie);
        }

        //return movie item data
        return mMovie;
    }

    private MovieItem mMovie;

    public void saveFavorite(){
        ArrayList<MovieItem> movies = mMovieStaff.getMovies(PosterHelper.NAME_ID_FAVORITE);

        if(!movies.contains(mMovie)){
            mMovieValet.saveFavorite(mMovie);
            mMovieStaff.addFavorite(mMovie);
        }
    }

    public void addToFavorite(int movieType, int position){
        mMovie = mMovieStaff.getMovie(movieType, position);

        Toast.makeText(mActivityContext,"Saving to Favorites", Toast.LENGTH_SHORT).show();
        saveFavorite();
    }

    public void removeFavorite(int position){
        Log.d("Boss", "Boss.removeFavorite");
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
 *      MyHouseKeeper hireHouseKeeper(int) - get requested HouseKeeper, called by Activity
 */
/**************************************************************************************************/
/**
 * MyHouseKeeper hireHouseKeeper(int) - get requested HouseKeeper, called by Activity
 * @param id - id number of HouseKeeper
 * @return - HouseKeeper object
 */
    public MyHouseKeeper hireHouseKeeper(int id){
        return mKeeperStaff.startHouseKeeper(id);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Maid Methods:
 *      MyMaid hireMaid(int) - get requested Maid, called by HouseKeeper
 *      void registerMaid(MyMaid,int) - register maid
 */
/**************************************************************************************************/
/**
 * MyMaid hireMaid(int) - get requested Maid, called by HouseKeeper
 * @param id - id number of maid
 * @return - sends Maid reference
 */
    public MyMaid hireMaid(Integer id){
        //return requested Maid
        return mMaidStaff.getMaid(id);
    }

/**
 * void registerMaid(String, MyMaid) - registers Maids being used by HouseKeepers
 * @param key - Maid name
 * @param maid - Maid class (in charge of maintaining fragments)
 */
    public void registerMaid(Integer key, MyMaid maid){
        //register Maid
        mMaidStaff.setMaid(maid, key);
    }

/**************************************************************************************************/

}
