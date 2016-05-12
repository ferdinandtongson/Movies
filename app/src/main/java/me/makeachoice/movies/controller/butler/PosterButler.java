package me.makeachoice.movies.controller.butler;

import android.content.Context;

import java.util.ArrayList;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.butler.worker.MovieWorker;
import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * PosterButler handles the creation of a list of PosterItems to be consumed by the View. It takes
 * data from API calls or from the database and processes the data.
 *
 * Variables from MyButler:
 *      Boss mBoss
 *      Boolean mWorking
 *      ArrayList<Integer> mRequestBuffer
 *
 * Abstract Methods from MyButler:
 *      abstract public Context getActivityContext()
 *      abstract public void workComplete(Boolean)
 */
public class PosterButler extends MyButler{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *      MovieWorker mMovieWorker - AsyncTask class that makes API calls to get Movie details
 *      int mMovieRequest - current list of Movies being requested
 *
 *      int EMPTY_POSTERS_COUNT - number of "empty" poster items to create
 *      int EMPTY_ID - id number of "empty" poster item
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;

    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mMovieWorker - AsyncTask class that makes API calls to get Movie details
    private MovieWorker mMovieWorker;

    //mMovieRequest - current list of Movies being requested
    private int mMovieRequest;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterButler - constructor, registers to Boss, initialize URI builder, get API key and initialize
 * data buffers.
 * @param boss - Boss class
 */
    public PosterButler(Boss boss){
        //Application context
        mBoss = boss;

        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(mBoss);

        //flag to check if work is being done in the background
        mWorking = false;

        //get TheMovieDB api key from resource file
        mTMDBKey = mBoss.getString(R.string.api_key_tmdb);

        //initialize buffer to hold pending movie requests
        mRequestBuffer = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity context
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context
 * @return - activity context
 */
    public Context getActivityContext(){
        return mBoss.getActivityContext();
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Class methods
 *      void makePosterRequest(int) - start or buffer movie request task
 *      void startMovieRequest(int) - start AsyncTask worker to get movie data requested
 *      void workComplete(boolean) - called when AsyncTask has completed
 *      void checkRequestBuffer() - check if there are any pending movie data requests
 */
/**************************************************************************************************/
/**
 * void makePosterRequest(int) - make a request to get poster data. If MovieWork is already
 * working, the request will be saved into a buffer.
 * @param request - poster buffer request
 */
    public void makePosterRequest(int request){
        //check if MovieWorker is already working on another request
        if(mWorking){
            //save request to buffer
            mRequestBuffer.add(request);
        }
        else{
            //save type of movies being requested
            mMovieRequest = request;

            //start working on the movie request
            startMovieRequest(request);
        }
    }

/**
 * void startMovieRequest(int) - start AsyncTask worker to get movie data requested.
 * @param request - movie data requested
 */
    private void startMovieRequest(int request){
        //initializes the AsyncTask worker
        mMovieWorker = new MovieWorker(this);

        //set working flag, AsyncTask is working in the background
        mWorking = true;

        //check type of movie request
        switch(request) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //start AsyncTask, get Popular movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_POPULAR, mTMDBKey));
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //start AsyncTask, get Top Rated movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_TOP_RATED, mTMDBKey));
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //start AsyncTask, get Now Playing movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_NOW_PLAYING, mTMDBKey));
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //start AsyncTask, get Upcoming movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_UPCOMING, mTMDBKey));
                break;
            default:
                mWorking = false;
        }
    }

/**
 * void workComplete(Boolean) - called when MovieWorker completes its' work, updates buffers,
 * notifies Boss and, finally checks if there are more movie requests
 * @param result - returns boolean result of success of movie download
 */
    public void workComplete(Boolean result) {
        //work has finished
        mWorking = false;

        //get movie data from MovieWorker
        ArrayList<MovieModel> movieModel = mMovieWorker.getMovies();

        //check if results were successful
        if(result){
            //message the Boss that the download of movie info is complete
            mBoss.movieRequestComplete(movieModel, mMovieRequest);
        }
        else{
            //TODO - need to handle event of a download failure
        }

        //check if there are any pending request
        checkRequestBuffer();

    }



/**
 * void checkRequestBuffer() - checks the request buffer if there are any pending movie requests
 */
    private void checkRequestBuffer(){
        //check request buffer
        if(mRequestBuffer.size() > 0) {
            //there are pending requests, get request
            int request = mRequestBuffer.get(0);

            //remove request from buffer
            mRequestBuffer.remove(0);

            //make a poster request
            makePosterRequest(request);
        }
    }

/**************************************************************************************************/

}
