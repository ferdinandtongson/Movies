package me.makeachoice.movies.controller;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import me.makeachoice.movies.R;
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
 *
 * Implements MovieValet.Bridge
 *
 */

public class Boss extends Application implements MovieValet.Bridge, RefreshValet.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Context mActivityContext - current Activity Context on display
 *      SQLiteDatabase mDB - SQLiteDatabase object
 *
 *      boolean mFavoriteChanged - status if user favorite movie list has changed
 *      MovieItem mMovie - current movie item selected by user
 *      int mMovieType - current movie type (Most Popular, Top Rated, Now Playing, Upcoming,
 *          Favorite)
 *      boolean mIsTablet - is a tablet device status flag
 *
 *      int mOrientation - current orientation of phone
 *      boolean mOrientationChange - status flag on whether the phone orientation has changed

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
 */
/**************************************************************************************************/

    //mActivityContext - current Activity Context on display
    private Context mActivityContext;
    //mDB - SQLiteDatabase object
    private SQLiteDatabase mDB;

    //mFavoriteChanged - status if user favorite movie list has changed
    private boolean mFavoriteChanged;
    //mMovie - current movie item selected by user
    private MovieItem mMovie;
    //mMovieType - current movie type (Most Popular, Top Rated, Now Playing, Upcoming, Favorite)
    private int mMovieType;
    //mIsTablet - is a tablet device status flag
    private boolean mIsTablet;

    //mOrientation - current orientation of phone
    private int mOrientation;
    //mOrientationChange - status flag on whether the phone orientation has changed
    private boolean mOrientationChange;


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
        MovieDB movieDB = new MovieDB(this);

        //will create database if necessary
        mDB = movieDB.getWritableDatabase();

        //set movie type init value
        mMovieType = -1;

        //instantiate MovieItem object
        mMovie = new MovieItem();

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
        //request favorite movie list from database
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
        mRefreshStaff.setRefreshMap(mRefreshValet.getRefreshMap());

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

/**************************************************************************************************/
/**
 *  Methods:
 *      void activityCreated(Context) - stores the Activity context
 *      void backToPosters() - notified by DetailKeeper that back press event has happened
 *      void onFinish() - application is about to close, clear all local buffer from memory
 */
/**************************************************************************************************/
/**
 * void activityCreated(Context) - called when the onCreate() of an Activity is called, stores the
 * Activity Context
 * @param ctx - Activity context
 */
    public void activityCreated(Context ctx){
        //set Activity context - in this case there is only MainActivity
        mActivityContext = ctx;
    }

/**
 * void backToPosters() - notified by DetailKeeper that back press event has happened. Check
 * to see if the users' favorite movie list is going to be display and refreshes the list if so
 */
    public void backToPosters(){
        //check if favorite list is going to be displayed or if favorite list has changed
        if(mMovieType == PosterHelper.NAME_ID_FAVORITE && mFavoriteChanged){
            //refresh favorite list
            showMovieList(mMovieStaff.getMovies(PosterHelper.NAME_ID_FAVORITE),
                    PosterHelper.NAME_ID_FAVORITE);
        }
    }

/**
 * void onFinish() - application is about to close, clear all local buffer from memory
 */
public void onFinish(){

        mMovieStaff.onFinish();
        mRefreshStaff.onFinish();
        mKeeperStaff.onFinish();
        mMaidStaff.onFinish();

        mDB.close();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity Context
 *      SQLiteDatabase getDatabase() - get SQLite database
 *      Executor getExecutor() - get AsyncTask thread pool executor
 *      boolean getIsTablet() - get if device is a tablet status flag
 * Setters:
 *      void setIsTablet(boolean) - set tablet status flag
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

    public boolean getIsTablet(){ return mIsTablet; }

    public void setIsTablet(boolean isTablet){ mIsTablet = isTablet; }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Movie Methods:
 *      void onMovieClicked(int,int) - onMovieClick event occurred
 *      void tabletMovieSelected(MovieItem) - a movie item was selected by a tablet device
 *      void emptyMovieSelected() - empty movie item selected for display
 *      MovieItem getMovieSelected() - get movie selected by user
 *      void showMovieInfo(MovieItem) - show movie item info in View
 */
/**************************************************************************************************/
/**
 * void onMovieClicked(int,int) - onMovieClick event occurred. Get movie list type and list
 * position of movie that was clicked.
 * @param movieType - movie list type being shown
 * @param position - position of movie in the list
 */
    public void onMovieClicked(int movieType, int position){
        //get the movie item from the local buffer
        mMovie = mMovieStaff.getMovie(movieType, position);

        //check local buffer if movie is a user favorite, set user favorite status
        mMovie.setFavorite(mMovieStaff.alreadyFavorite(mMovie.getTMDBId()));

        //check if movie item has detailed movie data
        if(mMovie.getCast() == null || mMovie.getCast().size() == 0){
            //data is incomplete, start AsyncTask to request movie data
            mInfoButler.requestMovieInfo(mMovie.getTMDBId());
        }
    }

/**
 * void tabletMovieSelected(MovieItem) - a movie item was selected by a tablet device
 * @param movie - movie item selected
 */
    public void tabletMovieSelected(MovieItem movie){
        //check if movie item is null
        if(movie != null){
            //save movie item to buffer
            mMovie = movie;

            //check local buffer if movie is a user favorite, set user favorite status
            mMovie.setFavorite(mMovieStaff.alreadyFavorite(mMovie.getTMDBId()));

            //check if movie item has detailed movie data
            if(mMovie.getCast() == null || mMovie.getCast().size() == 0){
                //data is incomplete, start AsyncTask to request movie data
                mInfoButler.requestMovieInfo(mMovie.getTMDBId());
            }
        }
        else{
            //show movie item
            showMovieInfo(mMovie);
        }
    }

/**
 * void movieRequestCompleted(MovieModel) - AsyncTask API call for movie info has completed. Convert
 * movie model to movie item object, prepare data for View consumption. Called by TMDBInfoButler
 * @param model - movie model object
 */
    public void movieRequestCompleted(MovieModel model){
        //convert movie model to movie item data
        mMovieStaff.prepareMovieItem(model, mMovie);

        //show movie item data
        showMovieInfo(mMovie);
    }

/**
 * void emptyMovieSelected() - empty movie item selected for display; used when user favorite
 * movie list is empty
 */
    public void emptyMovieSelected(){
        //get empty movie item
        mMovie = mMovieStaff.getEmptyMovie();

        //show movie item
        showMovieInfo(mMovie);
    }

/**
 * MovieItem getMovieSelected() - get movie selected by user
 * @return - movie item selected
 */
    public MovieItem getMovieSelected(){
        //return movie item data
        return mMovie;
    }

/**
 * void showMovieInfo(MovieItem) - show movie item info in View
 * @param movie - movie item to show
 */
    public void showMovieInfo(MovieItem movie){
        //get houseKeeper in charge of displaying movie item info
        DetailKeeper keeper = (DetailKeeper)mKeeperStaff.getHouseKeeper(DetailHelper.NAME_ID);
        //update View with movie item data
        keeper.updateDetails(movie);
    }


/**************************************************************************************************/
/**
 * Movie List Methods:
 *      ArrayList<MovieItem> getMovieList(int) - get list of movies.
 *      ArrayList<MovieItem> requestedMovieList(int) - request movie list from db or api call
 *      void listRequestCompleted(ArrayList<MovieModel>,int) - AsyncTask API request for movies has
 *          completed
 *      void listRetrievalCompleted(ArrayList<MovieItem>,int) - AsyncTask database retrieval request
 *          for movie list data has completed
 *      void showMovieList(ArrayList<MovieItem>,int) - show movie list
 *      void refreshMovieList(int) - refresh the movie list type
 *      void updateRefreshListDate(int) - update the date of when the movie list type needs to be
 *          by TheMovieDB data
 */
/**************************************************************************************************/
/**
 * ArrayList<MovieItem> getMovieList(int) - get list of movies. If list of favorite movies are
 * being requested, get favorite movie list. If not make a movie request to get the list
 * @param movieType - type of movie list being requested
 * @return - an array list of movie item data
 */
    public ArrayList<MovieItem> getMovieList(int movieType){
        //save movie type
        mMovieType = movieType;

        //check movie type being requested
        if(movieType == PosterHelper.NAME_ID_FAVORITE){
            //mark favorite movie list has changed as true
            mFavoriteChanged = true;

            //if favorite, return list of favorite movies
            return getFavoriteMovies();
        }
        else{
            //if any other movie type, request list of movies
            return requestMovieList(movieType);
        }
    }

/**
 * ArrayList<MovieItem> requestedMovieList(int) - request movie list from buffer, db or api call.
 * Check if movie list needs to be refreshed. If yes, make an API call to TheMovieDB. If does NOT
 * need to refresh, check if list is in local buffer. If not, retrieve movie list from database
 * @param movieType - type of movie list
 * @return - list of movies
 */
    private ArrayList<MovieItem> requestMovieList(int movieType){

        //create array list buffer
        ArrayList<MovieItem> movies = new ArrayList<>();

        //check if the movie list type needs to be refreshed
        if(mRefreshStaff.needToRefresh(movieType)){
            //need to refresh movie list, make an API call to TheMovieDB API
            mMoviesButler.requestMovies(movieType);

            //send toast message to user
            Toast.makeText(mActivityContext, getString(R.string.str_api_call),
                    Toast.LENGTH_SHORT).show();
        }
        else{
            //do NOT need to refresh list, check if movie list is in buffer
            movies = mMovieStaff.getMovies(movieType);

            //check size of list from buffer
            if (movies.size() == 0) {
                //list is zero, retrieve movie list from database
                mMovieValet.requestMovies(movieType);

                //send toast message to user
                Toast.makeText(mActivityContext, getString(R.string.str_retrieve_from_db),
                        Toast.LENGTH_SHORT).show();
            }
        }

        //return poster items
        return movies;

    }

/**
 * void listRequestCompleted(ArrayList<MovieModel>,int) - AsyncTask API request for movies has
 * completed; called by TMDBMoviesButler. Convert MovieModel objects to MovieItem objects and show
 * the new movie list. Finally save the new list to the local buffer and to the database and update
 * the refresh date of the movie list.
 * @param models - list of movie models created by an API call to TheMovieDB
 * @param movieType - type of movie list being requested
 */
    public void listRequestCompleted(ArrayList<MovieModel> models, int movieType){
        //convert MovieModels to MovieItems
        ArrayList<MovieItem> movies = mMovieStaff.prepareMovies(models);

        //show the new list of movie items
        showMovieList(movies, movieType);

        //save the movie list to the local buffer
        mMovieStaff.setMovies(movies, movieType);

        //save the movie list to the database
        mMovieValet.saveMovies(movies, movieType);

        //update refresh data
        updateRefreshListDate(movieType);
    }

/**
 * void listRetrievalCompleted(ArrayList<MovieItem>,int) - AsyncTask database retrieval request
 * for movie list data has completed; called by MovieValet. If movie list is the user favorite
 * list, determine what to do with list. If any other list, check size of list. If the list is
 * NOT empty, save and show list. If the list is empty, make an API call to get the movie list
 * @param movies - list of movie items retrieved from the database
 * @param movieType - type of movie list being retrieved
 */
    public void listRetrievalCompleted(ArrayList<MovieItem> movies, int movieType){
        //check if movie type is a the favorite list of the user
        if(movieType == PosterHelper.NAME_ID_FAVORITE){
            //favorite list, process favorite list
            retrieveFavoritesCompleted(movies);
        }
        else{
            //movie type is NOT the favorite list, check movie list size
            if(movies.size() > 0){
                //movie list is not zero, show new movie list
                showMovieList(movies, movieType);

                //save movie list to local buffer
                mMovieStaff.setMovies(movies, movieType);
            }
            else{
                //movie list is zero, make an API call to TheMovieDB to get the movie list
                mMoviesButler.requestMovies(movieType);
            }
        }

    }

/**
 * void showMovieList(ArrayList<MovieItem>,int) - show movie list
 * @param movies - list of movies to be shown
 * @param request - type of movies in list
 */
    private void showMovieList(ArrayList<MovieItem> movies, int request){
        //instantiate HouseKeeper that is in charge of displaying the movie list
        SwipeKeeper swipe = (SwipeKeeper)mKeeperStaff.getHouseKeeper(SwipeHelper.NAME_ID);

        //notify houseKeeper to update movie list
        swipe.updateMovieList(movies, request);
    }

/**
 * void refreshMovieList(int) - refresh the movie list type
 * @param movieType - type of movie list
 */
    public void refreshMovieList(int movieType){
        //make an API call to TheMovieDB to refresh the movie list type
        mMoviesButler.requestMovies(movieType);
    }

/**
 * void updateRefreshListDate(int) - update the date of when the movie list type needs to be
 * refreshed by TheMovieDB data
 * @param movieType - type of movie list
 */
    private void updateRefreshListDate(int movieType){
        //set the date when the list needs to be refreshed, tomorrow
        Long dateRefresh = DateManager.addDaysToDate(1).getTime();

        //save refresh date into the database
        mRefreshValet.setRefresh(movieType, dateRefresh);

        //save refresh date into local buffer
        mRefreshStaff.setRefreshDate(movieType, dateRefresh);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Movie Favorite Methods:
 *      ArrayList<MovieItem> getFavoriteMovies(int) - get favorite movie list from buffer
 *      void retrieveFavoritesComplete(ArrayList<MovieItem>) - database retrieval of favorite movies
 *          completed.
 *      void saveFavorite() - save movie to user favorite list
 *      void removeFavorite() - remove movie from user favorite list
 */
/**************************************************************************************************/
/**
 * ArrayList<MovieItem> getFavoriteMovies(int) - get favorite movies from buffer
 * @return - array list of favorite movies
 */
    private ArrayList<MovieItem> getFavoriteMovies(){
        //get list of favorite movies from buffer
        ArrayList<MovieItem> movies = mMovieStaff.getMovies(PosterHelper.NAME_ID_FAVORITE);

        //check if favorite movie list has changed
        if(mFavoriteChanged){
            //list has changed, show new movie list
            showMovieList(movies, PosterHelper.NAME_ID_FAVORITE);
        }

        //return movie list
        return movies;
    }

/**
 * void retrieveFavoritesCompleted(ArrayList<MovieItem>) - database retrieval of favorite movies
 * completed; AsyncTask completed
 * @param movies - list of favorite movies by the user
 */
    private void retrieveFavoritesCompleted(ArrayList<MovieItem> movies){
        //get number of favorite movies in the list
        int count = movies.size();

        //check if there are any movies
        if(count > 0){
            //save movies to buffer
            mMovieStaff.setMovies(movies, PosterHelper.NAME_ID_FAVORITE);
        }

        //check if the current poster fragment is displaying the user favorite list
        if(mMovieType == PosterHelper.NAME_ID_FAVORITE){
            //yes, show new movie list
            showMovieList(movies, PosterHelper.NAME_ID_FAVORITE);
        }
    }

/**
 * void saveFavorite() - save movie to user favorite list. Check to make sure the movie is NOT
 * already a favorite. If not, mark that the user favorite list has changed, update the movie item
 * as favorite and save movie item to favorite buffer and to favorite database table,
 *
 */
    public void saveFavorite(){
        //make sure movie is NOT already a favorite
        if(!mMovieStaff.alreadyFavorite(mMovie.getTMDBId())){
            //is a new favorite, favorite movie list status has changed
            mFavoriteChanged = true;

            //set movie item favorite status as true
            mMovie.setFavorite(true);

            //save movie to favorite database table
            mMovieValet.saveFavorite(mMovie);

            //add movie to favorite movie list buffer
            mMovieStaff.addFavorite(mMovie);
        }

        //check if movie list type is display a favorite list and if the device is a tablet
        if(mMovieType == PosterHelper.NAME_ID_FAVORITE && mIsTablet){
            //both yes, get favorite movies to update poster fragment
            getMovieList(PosterHelper.NAME_ID_FAVORITE);
        }
    }

/**
 * void removeFavorite() - remove movie from user favorite list. First check if the movie is in
 * the favorite list. If yes, mark that the user favorite list has changed, update the movie
 * item as no longer favorite and save movie item to favorite buffer and to favorite database
 * table
 */
    public void removeFavorite(){
        //check if movie is in the user favorite list
        if(mMovieStaff.alreadyFavorite(mMovie.getTMDBId())){
            //is in the favorite list, mark that list has changed
            mFavoriteChanged = true;

            //update movie item favorite status
            mMovie.setFavorite(false);

            //delete movie item from favorite database table
            mMovieValet.deleteFavorite(mMovie);

            //remove movie item from favorite movie list buffer
            mMovieStaff.removeFavorite(mMovie);
        }

        //check if movie list type is display a favorite list and if the device is a tablet
        if(mMovieType == PosterHelper.NAME_ID_FAVORITE && mIsTablet){
            //both yes, get favorite movies to update poster fragment
            getMovieList(PosterHelper.NAME_ID_FAVORITE);
        }
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getter Orientation:
 *      int getOrientation() - get orientation of device
 *      boolean getOrientationChanged() - get if an orientation change has occurred
 * Setter Orientation:
 *      void setOrientation(int) - save current device orientation
 *      void setOnOrientationChange(boolean) - saves orientation change status of Activity
 */
/**************************************************************************************************/
/**
 * int getOrientation() - get current orientation of phone
 * @return - orientation of phone
 */
    public int getOrientation(){ return mOrientation; }

/**
 * boolean getOrientationChanged() - get status flag for phone orientation changed
 * @return - status flag for phone orientation changed
 */
    public boolean getOrientationChanged(){ return mOrientationChange; }

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
