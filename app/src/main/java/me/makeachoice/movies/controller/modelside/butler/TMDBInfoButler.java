package me.makeachoice.movies.controller.modelside.butler;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.modelside.uri.TMDBUri;
import me.makeachoice.movies.controller.modelside.worker.TMDBInfoWorker;
import me.makeachoice.movies.model.response.tmdb.MovieModel;
/**
 * TMDBInfoButler handles API calls to TheMovieDB to get movie info data.
 *
 * It uses other classes to assist in making retrieving poster data from the net:
 *      Boss - Boss application
 *      TMDBUri - uri builder that builds TheMovieDB api uri string
 *      TMDBInfoWorker - AsyncTask class that makes API calls to get Movie details
 *      NetworkManager - check for network status
 *
 * Variables from MyButler:
 *      Boss mBoss
 *      Boolean mWorking
 *      ArrayList<Integer> mRequestBuffer
 */
public class TMDBInfoButler extends MyButler implements TMDBInfoWorker.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;

    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * TMDBInfoButler - constructor, registers to Boss, initialize URI builder, get API key and
 * initialize data buffers.
 * @param boss - Boss class
 */
    public TMDBInfoButler(Boss boss){
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
 * Movie Request related methods:
 *      void requestMovieInfo(int) - get info on the requested movie
 *      void makeInfoRequest(int) - make a request to get details of a movie
 *      void startInfoRequest(int) - start AsyncTask worker to get info of movie being requested
 *      void workComplete(boolean) - called when AsyncTask has completed
 *      void checkRequestBuffer() - check if there are any pending requests
 */
/**************************************************************************************************/

/**
 * void requestMovieInfo(MovieItem) - get info on the requested movie
 * @param id - requesting more info on movie with given id number
 */
    public void requestMovieInfo(int id){
        //make request for info
        makeInfoRequest(id);
    }

 /**
 * void makeInfoRequest(int) - make a request to get details of a movie. If the MovieWorker
 * is working, save movie id into request buffer. If not working, start request
 * @param id - id number of Movie from TheMovieDB
 */
    private void makeInfoRequest(int id){
        //check if MovieWorker is already working on another request
        if(mWorking){
            //save request to buffer
            mRequestBuffer.add(id);
        }
        else{
            //start working on the movie info request
            startInfoRequest(id);
        }
    }

/**
 * void startInfoRequest(int) - start AsyncTask worker to get info of movie being requested.
 * @param id - id number of movie from TheMovieDB
 */
    private void startInfoRequest(int id){
        //initializes the AsyncTask worker
        TMDBInfoWorker infoWorker = new TMDBInfoWorker(this);

        //set working flag, AsyncTask is working in the background
        mWorking = true;

        //start AsyncTask in background thread
        infoWorker.executeOnExecutor(mBoss.getExecutor(),
                mTMDBUri.getMovieDetailAll(String.valueOf(id), mTMDBKey));
    }

/**
 * void workComplete(Boolean) - called when AsyncTask has completed, prepares the data model to
 * movie item for consumption by the View then notifies boss to update the Activity
 * @param model - movie mode contain movie info data
 */
    public void movieInfoDownloaded(MovieModel model) {
        //work has finished
        mWorking = false;

        //notify Boss that the movie info request is complete
        mBoss.movieRequestCompleted(model);

        //check Request buffer
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

            //make a movie request
            startInfoRequest(request);
        }
    }


/**************************************************************************************************/

}
