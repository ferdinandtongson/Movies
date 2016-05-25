package me.makeachoice.movies.controller.modelside.valet;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import me.makeachoice.movies.controller.modelside.worker.MovieGetWorker;
import me.makeachoice.movies.controller.modelside.worker.MovieSaveWorker;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.db.contract.MovieContract;
import me.makeachoice.movies.model.item.MovieItem;

/**
 * MovieValet handles database processing of movie data and prepares the data to be consumed by
 * the View side. It uses Worker classes to save and retrieve movie data into the database.
 *
 * It uses other classes to assist in maintaining poster data in the database:
 *      Boss - Boss application
 *      MovieContract - database contract class for Poster tables
 *      PosterHelper - holds all static resources (layout id, view ids, etc)
 *      MovieSaveWorker - Worker (AsyncTask) class, save movie data into database
 *      MovieGetWorker - Worker (AsyncTask) class, retrieves movie data from database
 *
 * Implements MovieSaveWorker.Bridge
 *      SQLiteDatabase getDatabase() - get database
 *      void movieDataSaved(Boolean) - movie data has been saved
 *
 * Implements MovieGetWorker.Bridge
 *      SQLiteDatabase getDatabase() - get database
 *      void movieRetrieved(ArrayList<MovieItem>) - movie item data has been retrieved
 */
public class MovieValet implements MovieSaveWorker.Bridge, MovieGetWorker.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Bridge mBridge - Bridge communication interface
 *      MovieContract mContract - contract class for the movie tables
 *
 *      boolean mSaveWorking - status if AsyncTask MovieSaveWorker is working
 *      boolean mGetWorking - status if AsyncTask MovieGetWorker is working
 *
 *      MovieSaveWorker mSaveWorker - AsyncTask used to save Movie Item data to database
 *      MovieGetWorker mGetWorker - AsyncTask used to get Movie Item data from the database
 *
 *      ArrayList<ArrayList<MovieItems>> mSaveBuffer- list of MovieItem lists waiting to be saved
 *          to db
 *      ArrayList<Integer> mSaveTypeBuffer - poster types waiting to be saved to db
 *      ArrayList<Integer> mGetTypeBuffer - poster types waiting to be retrieved from the database
 *      int mRetrievalType - type of posters being retrieved
 */
/**************************************************************************************************/

    //mBridge - Bridge communication interface
    Bridge mBridge;

    //mContract - contract class for the poster tables
    MovieContract mContract;

    //mSaveWorking - status if AsyncTask MovieSaveWorker is working
    boolean mSaveWorking;

    //mGetWorking - status if AsyncTask MovieGetWorker is working
    boolean mGetWorking;

    //mSaveWorker - AsyncTask used to save Movie Item data to database
    MovieSaveWorker mSaveWorker;

    //mGetWorker - AsyncTask used to get Movie Item data from the database
    MovieGetWorker mGetWorker;

    //mSaveBuffer - list of MovieItem lists waiting to be saved to database
    private ArrayList<ArrayList<MovieItem>> mSaveBuffer;

    //mSaveTypeBuffer - movie types waiting to be saved to database
    private ArrayList<Integer> mSaveTypeBuffer;

    //mGetTypeBuffer - movie types waiting to be retrieved from the database
    private ArrayList<Integer> mRetrieveBuffer;

    //mRetrievalType - type of posters being retrieved
    private int mRetrievalType;

    //Implemented communication line to a Valet class
    public interface Bridge{
        //Executor to allow for AsyncTask to run concurrently
        Executor getExecutor();
        //get Database from Bridge
        SQLiteDatabase getDatabase();
        //notify Bridge that movie item data has been retrieved
        void movieRetrievalComplete(ArrayList<MovieItem> movies, int movieType);
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieValet - constructor, save bridge communication, creates contract class and initializes
 * data buffers.
 * @param bridge - class implementing Bridge interface
 */
    public MovieValet(Bridge bridge){
        //Class implementing Bridge interface
        mBridge = bridge;

        //contract class for movie tables in database
        mContract = new MovieContract();

        //buffer holding list of movie item data to be saved into the database
        mSaveBuffer = new ArrayList<>();
        //buffer holding the type of movies to be saved into the database
        mSaveTypeBuffer = new ArrayList<>();

        //buffer holding the type of movies to be retrieved from the database
        mRetrieveBuffer = new ArrayList<>();
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      SQLiteDatabase getDatabase() - get SQLiteDatabase object
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * SQLiteDatabase getDatabase() - get SQLiteDatabase object
 * @return - SQLiteDatabase object
 */
    public SQLiteDatabase getDatabase(){
        return mBridge.getDatabase();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieSaveWorker.Bridge implementations:
 *      SQLiteDatabase getDatabase() - get database (see Getter section)
 *      void movieDataSaved(Boolean) - movie data has been saved
 */
/**************************************************************************************************/
/**
 * void movieDataSaved(Boolean) - movie data has been saved. Change "save working" flag and check
 * if there are any other save requests pending.
 * @param result - default value = true
 */
    public void movieDataSaved(Boolean result){
        //change "save working" flag to false
        mSaveWorking = false;

        //check "save" buffers if there are any other save requests pending
        checkSaveBuffer();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieGetWorker.Bridge implementations:
 *      SQLiteDatabase getDatabase() - get database (see Getter section)
 *      void moviesRetrieved(ArrayList<MovieItem>) - movie item data has been retrieved
 */
/**************************************************************************************************/
/**
 * void moviesRetrieved(ArrayList<MovieItem>) - movie item data has been retrieved. Change
 * "retrieve working" flag to false and check "retrieve" buffer
 * @param movies - list of movie item data retrieved from database
 */
    public void moviesRetrieved(ArrayList<MovieItem> movies){
        //notify Bridge that poster data has been retrieved
        mBridge.movieRetrievalComplete(movies, mRetrievalType);

        //change "retrieve working" flag to false
        mGetWorking = false;

        //check "retrieve" buffer if there are any other retrieve requests pending
        checkRetrieveBuffer();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      void requestMovies(int) - request movie data from database, if any
 *      void saveMovies(ArrayList<MovieItem>,int) - save movie data to database.
 *      void saveFavorite(MovieItem) - save movie into Favorite table
 *      void deleteFavorite(MovieItem) - delete movie from Favorite table
 */
/**************************************************************************************************/
/**
 * void requestMovies(int) - get movie data from database, if any. Check to see if a retrieval
 * request is already running. If running, save request to buffer. If not, start AsyncTask to get
 * data from the database
 * @param movieType - type of movies being requested
 */
    public void requestMovies(int movieType){
        //check if a retrieval request is already running
        if(mGetWorking){
            //a request is running, save movie type request to buffer
            mRetrieveBuffer.add(movieType);
        }
        else{
            //no request running, retrieve poster data from database
            retrieveMoviesFromDB(movieType);
        }
    }

/**
 * void saveMovies(ArrayList<MovieItem>,int) - save movie data to database. If a save request
 * is already working, the request will be saved into a buffer. If not, start AsyncTask to save
 * movie data into database
 * @param movies - list of movie items to be saved to database
 * @param movieType - type of movies to be saved
 */
    public void saveMovies(ArrayList<MovieItem> movies, int movieType){
        ArrayList<MovieItem> tmpList = new ArrayList<>(movies);

        //check if MovieWorker is already working on another request
        if(mSaveWorking){
            //save request to buffer
            mSaveBuffer.add(tmpList);
            mSaveTypeBuffer.add(movieType);
        }
        else{
            //save posters to database
            saveMoviesToDB(movies, movieType);
        }
    }

/**
 * void saveFavorite(MovieItem) - save movie into Favorite table
 * @param item - movie item to save
 */
    public void saveFavorite(MovieItem item){
        //initializes the AsyncTask worker
        mSaveWorker = new MovieSaveWorker(this, null);

        //insert new movie item data into poster table
        mBridge.getDatabase().insert(MovieContract.FavoriteEntry.TABLE_NAME, null,
                mContract.getContentValues(item, 0));
    }

/**
 * void deleteFavorite(MovieItem) - delete movie from Favorite table
 * @param item - movie item to be deleted
 */
    public void deleteFavorite(MovieItem item){
        //create where clause to remove movie item row
        String whereClause = MovieContract.COLUMN_NAME_MOVIE_ID + " = " + item.getTMDBId();

        //delete movie from database
        mBridge.getDatabase().delete(MovieContract.FavoriteEntry.TABLE_NAME, whereClause, null);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void retrieveMoviesFromDB(int) - retrieve requested movie data from database.
 *      void saveMoviesToDB(ArrayList<PosterItem>, int) - save movie data in database.
 *      void checkRetrieveBuffer() - check retrieve buffer for any pending retrieval requests
 *      void checkSaveBuffer() - check save buffer for any pending save requests
 */
/**************************************************************************************************/
/**
 * void retrieveMoviesFromDB(int) - retrieve requested movie data from database. Start
 * AsyncTask background Thread to retrieve movie data from the database. When task is complete,
 * will call moviesRetrieved() method
 * @param movieType - movie poster type data being requested for
     */
    private void retrieveMoviesFromDB(int movieType){
        //create AsyncTask Worker class
        mGetWorker = new MovieGetWorker(this);

        //set "retrieve working" flag to true
        mGetWorking = true;

        //store movie type being requested, used in moviesRetrieved() method
        mRetrievalType = movieType;

        //determine which movie data to retrieve
        switch (movieType){
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //get select all query from MostPopular
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.MostPopularEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //get select all query from TopRated
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.TopRatedEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //get select all query from NowPlaying
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.NowPlayingEntry.SELECT_ALL);
            break;
            case PosterHelper.NAME_ID_UPCOMING:
                //get select all query from Upcoming
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.UpcomingEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                //get select all query from Favorite
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.FavoriteEntry.SELECT_ALL);
                break;
            default:
                //invalid request, Worker not started; change "retrieve working" flag status
                mGetWorking = false;

                //check buffer for other retrieval requests
                checkRetrieveBuffer();
        }
    }

/**
 * void saveMoviesToDB(ArrayList<MovieItem>,int) - save movie data in database. Starts
 * AsyncTask  background Thread to save poster data in database. When task is complete, will
 * call saveMovies().
 * @param movies - list of movie item data to be saved to database
 */
    private void saveMoviesToDB(ArrayList<MovieItem> movies, int movieType){
        //initializes the AsyncTask worker
        mSaveWorker = new MovieSaveWorker(this, movies);

        //set working flag, AsyncTask is working in the background
        mSaveWorking = true;

        //check type of movie data being saved
        switch(movieType){
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //start AsyncTask, save to Most Popular table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.MostPopularEntry.TABLE_NAME,
                        MovieContract.MostPopularEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //start AsyncTask, save to Top Rated table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.TopRatedEntry.TABLE_NAME,
                        MovieContract.TopRatedEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //start AsyncTask, save to Now Playing table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.NowPlayingEntry.TABLE_NAME,
                        MovieContract.NowPlayingEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //start AsyncTask, save to Upcoming table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        MovieContract.UpcomingEntry.TABLE_NAME,
                        MovieContract.UpcomingEntry.SELECT_ALL);
                break;
            default:
                //invalid request, change working status flag
                mSaveWorking = false;

                //check buffer if there are any other save requests
                checkSaveBuffer();
        }
    }

/**
 * void checkRetrieveBuffer() - check retrieve buffer for any pending retrieval requests.
 */
    private void checkRetrieveBuffer(){

        //check size of buffer
        if(mRetrieveBuffer.size() > 0){
            //get requested poster type
            int movieType = mRetrieveBuffer.get(0);

            //remove request from buffer
            mRetrieveBuffer.remove(0);

            //buffer is not empty, make poster request from first item in the buffer
            requestMovies(movieType);
        }
    }

/**
 * void checkSaveBuffer() - check save buffer for any pending save requests. First removes
 * previous request from buffer that just completed then checks the first item in the buffer
 */
    private void checkSaveBuffer(){
        //check request buffer
        if (mSaveTypeBuffer.size() > 0) {
            //get list from save buffer
            ArrayList<MovieItem> tmpList = new ArrayList<>(mSaveBuffer.get(0));

            //get movie typ from save type buffer
            int movieType = mSaveTypeBuffer.get(0);

            //remove list from save request from buffer
            mSaveBuffer.remove(0);

            //remove type from save type buffer
            mSaveTypeBuffer.remove(0);

            //has pending save request, save data to database
            saveMoviesToDB(tmpList, movieType);
        }
    }

/**************************************************************************************************/

}
