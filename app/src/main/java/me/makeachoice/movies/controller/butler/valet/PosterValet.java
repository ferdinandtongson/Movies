package me.makeachoice.movies.controller.butler.valet;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import me.makeachoice.movies.controller.butler.worker.PosterGetWorker;
import me.makeachoice.movies.controller.butler.worker.PosterSaveWorker;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.db.contract.PosterContract;
import me.makeachoice.movies.model.item.PosterItem;

/**
 * PosterValet handles database processing of poster data and prepares the data to be consumed by
 * the View side. It uses Worker classes to save and retrieve poster data into the database.
 *
 * It uses other classes to assist in maintaining poster data in the database:
 *      Boss - Boss application
 *      PosterContract - database contract class for Poster tables
 *      PosterHelper - holds all static resources (layout id, view ids, etc)
 *      PosterSaveWorker - Worker (AsyncTask) class, save poster data into database
 *      PosterGetWorker - Worker (AsyncTask) class, retrieves poster data from database
 *
 * Implements PosterSaveWorker.Bridge
 *      SQLiteDatabase getDatabase() - get database
 *      void posterDataSaved(Boolean) - poster data has been saved
 *
 * Implements PosterGetWorker.Bridge
 *      SQLiteDatabase getDatabase() - get database
 *      void postersRetrieved(ArrayList<PosterItem>) - poster item data has been retrieved
 */
public class PosterValet implements PosterSaveWorker.Bridge, PosterGetWorker.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      Bridge mBridge - Bridge communication interface
 *      PosterContract mContract - contract class for the poster tables
 *
 *      boolean mSaveWorking - status if AsyncTask PosterSaveWorker is working
 *      boolean mGetWorking - status if AsyncTask PosterGetWorker is working
 *
 *      PosterSaveWorker mSaveWorker - AsyncTask used to save Poster Item data to database
 *      PosterGetWorker mGetWorker - AsyncTask used to get Poster Item data from the database
 *
 *      ArrayList<ArrayList<PosterItems>> mSaveBuffer- list of PosterItem lists waiting to be saved
 *          to db
 *      ArrayList<Integer> mSaveTypeBuffer - poster types waiting to be saved to db
 *      ArrayList<Integer> mGetTypeBuffer - poster types waiting to be retrieved from the database
 *      int mRetrievalType - type of posters being retrieved
 */
/**************************************************************************************************/

    //mBridge - Bridge communication interface
    Bridge mBridge;

    //mContract - contract class for the poster tables
    PosterContract mContract;

    //mSaveWorking - status if AsyncTask PosterSaveWorker is working
    boolean mSaveWorking;

    //mGetWorking - status if AsyncTask PosterGetWorker is working
    boolean mGetWorking;

    //mSaveWorker - AsyncTask used to save Poster Item data to database
    PosterSaveWorker mSaveWorker;

    //mGetWorker - AsyncTask used to get Poster Item data from the database
    PosterGetWorker mGetWorker;

    //mSaveBuffer - list of PosterItem lists waiting to be saved to database
    private ArrayList<ArrayList<PosterItem>> mSaveBuffer;

    //mSaveTypeBuffer - poster types waiting to be saved to database
    private ArrayList<Integer> mSaveTypeBuffer;

    //mGetTypeBuffer - poster types waiting to be retrieved from the database
    private ArrayList<Integer> mRetrieveBuffer;

    //mRetrievalType - type of posters being retrieved
    private int mRetrievalType;

    //Implemented communication line to a Valet class
    public interface Bridge{
        //Executor to allow for AsyncTask to run concurrently
        Executor getExecutor();
        //get Database from Bridge
        SQLiteDatabase getDatabase();
        //notify Bridge that poster item data has been retrieved
        void posterRetrievalComplete(ArrayList<PosterItem> posters, int posterType);
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterValet - constructor, save bridge communication, creates contract class and initializes
 * data buffers.
 * @param bridge - class implementing Bridge interface
 */
    public PosterValet(Bridge bridge){
        //Class implementing Bridge interface
        mBridge = bridge;

        //poster contract class for poster tables in database
        mContract = new PosterContract();

        //buffer holding list of poster item data to be saved into the database
        mSaveBuffer = new ArrayList<>();
        //buffer holding the type of movie posters to be saved into the database
        mSaveTypeBuffer = new ArrayList<>();

        //buffer holding the type of movie posters to be retrieved from the database
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
 * PosterSaveWorker.Bridge implementations:
 *      SQLiteDatabase getDatabase() - get database (see Getter section)
 *      void posterDataSaved(Boolean) - poster data has been saved
 */
/**************************************************************************************************/
/**
 * void posterDataSaved(Boolean) - poster data has been saved. Change "save working" flag and check
 * if there are any other save requests pending.
 * @param result - default value = true
 */
    public void posterDataSaved(Boolean result){
        //change "save working" flag to false
        mSaveWorking = false;

        //check "save" buffers if there are any other save requests pending
        checkSaveBuffer();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterGetWorker.Bridge implementations:
 *      SQLiteDatabase getDatabase() - get database (see Getter section)
 *      void postersRetrieved(ArrayList<PosterItem>) - poster item data has been retrieved
 */
/**************************************************************************************************/
/**
 * void postersRetrieved(ArrayList<PosterItem>) - poster item data has been retrieved. Change
 * "retrieve working" flag to false and check "retrieve" buffer
 * @param posters - list of poster item data retrieved from database
 */
    public void postersRetrieved(ArrayList<PosterItem> posters){
        //notify Bridge that poster data has been retrieved
        mBridge.posterRetrievalComplete(posters, mRetrievalType);

        //change "retrieve working" flag to false
        mGetWorking = false;

        //check "retrieve" buffer if there are any other retrieve requests pending
        checkRetrieveBuffer();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Public Methods:
 *      void requestPosters(int) - request poster data from database, if any
 *      void savePosters(ArrayList<PosterItem>,int) - save poster data to database.
 */
/**************************************************************************************************/
/**
 * void requestPosters(int) - get poster data from database, if any. Check to see if a retrieval
 * request is already running. If running, save request to buffer. If not, start AsyncTask to get
 * data from the database
 * @param movieType - type of movie posters being requested
 */
    public void requestPosters(int movieType){
        //check if a retrieval request is already running
        if(mGetWorking){
            //a request is running, save movie type request to buffer
            mRetrieveBuffer.add(movieType);
        }
        else{
            //no request running, retrieve poster data from database
            retrievePostersFromDB(movieType);
        }
    }

/**
 * void savePosters(ArrayList<PosterItem>,int) - save poster data to database. If a save request
 * is already working, the request will be saved into a buffer. If not, start AsyncTask to save
 * poster data into database
 * @param posters - list of poster items to be saved to database
 * @param movieType - type of posters to be saved
 */
    public void savePosters(ArrayList<PosterItem> posters, int movieType){
        ArrayList<PosterItem> tmpList = new ArrayList<>(posters);

        //check if MovieWorker is already working on another request
        if(mSaveWorking){
            //save request to buffer
            mSaveBuffer.add(tmpList);
            mSaveTypeBuffer.add(movieType);
        }
        else{
            //save posters to database
            savePostersToDB(posters, movieType);
        }
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void retrievePostersFromDB(int) - retrieve requested poster data from database.
 *      void savePostersToDB(ArrayList<PosterItem>, int) - save poster data in database.
 *      void checkRetrieveBuffer() - check retrieve buffer for any pending retrieval requests
 *      void checkSaveBuffer() - check save buffer for any pending save requests
 */
/**************************************************************************************************/
/**
 * void retrievePostersFromDB(int) - retrieve requested poster data from database. Start
 * AsyncTask background Thread to retrieve poster data from the database. When task is complete,
 * will call postersRetrieved() method
 * @param movieType - movie poster type data being requested for
     */
    private void retrievePostersFromDB(int movieType){
        //create AsyncTask Worker class
        mGetWorker = new PosterGetWorker(this);

        //set "retrieve working" flag to true
        mGetWorking = true;

        //store movie poster type being requested, used in postersRetrieved() method
        mRetrievalType = movieType;

        //determine which poster data to retrieve
        switch (movieType){
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //get select all query from MostPopular
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.MostPopularEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //get select all query from TopRated
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.TopRatedEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //get select all query from NowPlaying
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.NowPlayingEntry.SELECT_ALL);
            break;
            case PosterHelper.NAME_ID_UPCOMING:
                //get select all query from Upcoming
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.UpcomingEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                //get select all query from Favorite
                mGetWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.FavoriteEntry.SELECT_ALL);
                break;
            default:
                //invalid request, Worker not started; change "retrieve working" flag status
                mGetWorking = false;

                //check buffer for other retrieval requests
                checkRetrieveBuffer();
        }
    }

/**
 * void savePostersToDB(ArrayList<PosterItem>,int) - save poster data in database. Starts
 * AsyncTask  background Thread to save poster data in database. When task is complete, will
 * call savePosters().
 * @param posters - list of poster item data to be saved to database
 */
    private void savePostersToDB(ArrayList<PosterItem> posters, int movieType){
        //initializes the AsyncTask worker
        mSaveWorker = new PosterSaveWorker(this, posters);

        //set working flag, AsyncTask is working in the background
        mSaveWorking = true;

        //check type of movie poster data being saved
        switch(movieType){
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //start AsyncTask, save to Most Popular table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.MostPopularEntry.TABLE_NAME,
                        PosterContract.MostPopularEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //start AsyncTask, save to Top Rated table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.TopRatedEntry.TABLE_NAME,
                        PosterContract.TopRatedEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //start AsyncTask, save to Now Playing table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.NowPlayingEntry.TABLE_NAME,
                        PosterContract.NowPlayingEntry.SELECT_ALL);
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //start AsyncTask, save to Upcoming table in db
                mSaveWorker.executeOnExecutor(mBridge.getExecutor(),
                        PosterContract.UpcomingEntry.TABLE_NAME,
                        PosterContract.UpcomingEntry.SELECT_ALL);
                break;
            default:
                //invalid request, change working status flag
                mSaveWorking = false;

                //check buffer if there are any other save requests
                checkSaveBuffer();
        }
    }

/**
 * void checkRetrieveBuffer() - check retrieve buffer for any pending retrieval requests. First
 * removes previous request from buffer that just completed then checks the first item in the buffer
 */
    private void checkRetrieveBuffer(){
        //checks if buffer is not empty
        if(!mRetrieveBuffer.isEmpty()){
            //not empty, remove request from buffer that has just completed
            mRetrieveBuffer.remove(0);
        }

        //check size of buffer
        if(mRetrieveBuffer.size() > 0){
            //buffer is not empty, make poster request from first item in the buffer
            requestPosters(mRetrieveBuffer.get(0));
        }
    }

/**
 * void checkSaveBuffer() - check save buffer for any pending save requests. First removes
 * previous request from buffer that just completed then checks the first item in the buffer
 */
    private void checkSaveBuffer(){
        //check if "save" buffer is empty
        if(!mSaveBuffer.isEmpty()){
            //is not empty, remove just completed save request from buffer
            mSaveBuffer.remove(0);

            //remove just completed save request from buffer
            mSaveTypeBuffer.remove(0);
        }

        //check request buffer
        if (mSaveTypeBuffer.size() > 0) {
            //has pending save request, save data to database
            savePostersToDB(mSaveBuffer.get(0), mSaveTypeBuffer.get(0));
        }
    }

/**************************************************************************************************/

}
