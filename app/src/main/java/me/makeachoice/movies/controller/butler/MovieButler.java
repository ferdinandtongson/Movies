package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.util.Log;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.worker.MovieWorker;
import me.makeachoice.movies.R;
import me.makeachoice.movies.model.MovieJSON;

/**
 * MovieButler handles the creation of the Movie model, taking resources from a flat file,
 * database or webservice and converting it into a model for consumption by the controller
 */
public class MovieButler extends MyButler{

/**************************************************************************************************/

    //mApiKey - the key used to access for TheMovieDB api
    private String mApiKey;
    //mMovieWorker - AsyncTask class that connect to the internet to get Movie details
    MovieWorker mMovieWorker;

    //MOVIE_REQUEST_MOST_POPULAR - value used when a list of most popular movies are requested
    public static int MOVIE_REQUEST_MOST_POPULAR = 0;
    //MOVIE_REQUEST_HIGHEST_RATED - value used when a list of highest rated movies are requested
    public static int MOVIE_REQUEST_HIGHEST_RATED = 1;

    //mMovieModel - a ListArray of data taken from a JSON response
    MovieJSON mMovieModel;

    //mWorking - boolean value to check if a Worker is working in the background
    Boolean mWorking;

/**************************************************************************************************/

    public MovieButler(Boss boss){
        //Application context
        mBoss = boss;

        //flag to check if work is being done in the background
        mWorking = false;

        //get TheMovieDB api key from resource file
        mApiKey = mBoss.getString(R.string.api_key_tmdb);
    }

    public Context getActivityContext(){
        return mBoss.getActivityContext();
    }

/**************************************************************************************************/

/**
 * void requestMovies(int) - used to execute and api request for a list of movies
 * @param request - type of request (most popular or highest rated)
 */
    public void requestMovies(int request){
        Log.d("Movies", "MovieButler.requestMovies");
        if(mWorking){
            Log.d("Movies", "     thread cancelled");
            mMovieWorker.cancel(true);
        }


        //initializes the AsyncTask worker
        mMovieWorker = new MovieWorker(this);

        //worker executes the url request
        if(request == MOVIE_REQUEST_MOST_POPULAR){
            //most popular movies are requested
            mMovieWorker.execute(
                    mMovieWorker.TMDB_URL_POPULAR,
                    mMovieWorker.TMDB_API_KEY + mApiKey
            );
        }
        else if(request == MOVIE_REQUEST_HIGHEST_RATED){
            //highest rated movies are requested
            mMovieWorker.execute(
                    mMovieWorker.TMDB_URL_TOP_RATED,
                    mMovieWorker.TMDB_API_KEY + mApiKey
            );
        }
        else{
            //most popular movies are requested using old DISCOVER api call
            mMovieWorker.execute(
                    mMovieWorker.TMDB_URL_DISCOVER_MOVIE,
                    mMovieWorker.TMDB_API_KEY + mApiKey,
                    mMovieWorker.TMDB_SORT,
                    mMovieWorker.SORT_POPULARITY_DESC
            );
        }

    }

/**
 * void workComplete(Boolean) - when MovieWorker completes its' work, calls this method
 * @param result - returns boolean result of success of movie download
 */
    public void workComplete(Boolean result) {
        //get modeled data from the JSON response processed by the worker
        mMovieModel = mMovieWorker.getMovies();

        if(result){
            //message the Boss that the download of movie info is complete
            mBoss.updateMainActivity();
        }
        else{
            //TODO - need to handle event of a download failure
        }

        //work has finished
        mWorking = false;
    }


/**
 * MovieJSON getModel() - allows access to the data received
 * @return - MovieJSON, an array of objects containing movie details
 */
    public MovieJSON getModel( ){
        return mMovieModel;
    }

/**************************************************************************************************/

}
