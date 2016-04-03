package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.util.Log;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.worker.MovieWorker;
import me.makeachoice.movies.model.MovieModel;
import me.makeachoice.movies.R;
import me.makeachoice.movies.model.json.MovieJSON;

/**
 * MovieButler handles the creation of the AppDemo model, taking resources from a flat file,
 * database or webservice and converting it into a model for consumption by the controller
 */
public class MovieButler extends MyButler{

    private Boss mBoss;
    public MovieButler(Context ctx, Boss boss){
        Log.d("Movies", "MovieButler: " + ctx.toString());
        mActivityContext = ctx;
        mBoss = boss;

    }


    MovieWorker mMovieWorker;
    public boolean hasHttpConnection(){
        Log.d("Movies", "MovieButler.establishHttpConnection");
        mMovieWorker = new MovieWorker(this);

        if(mMovieWorker.hasConnectivity(mActivityContext)){
            Log.d("Movies", "     connection true");
            return true;
        }
        else{
            Log.d("Movies", "     no connection");
            return false;
        }
    }

    public void requestPopularMovies(){
        mMovieWorker.execute(
                mMovieWorker.TMDB_URL_DISCOVER_MOVIE,
                mMovieWorker.TMDB_API_KEY,
                mMovieWorker.TMDB_SORT,
                mMovieWorker.SORT_POPULARITY_DESC
        );

    }

    public void workComplete(Boolean result) {
        Log.d("Movies", "MovieButler.movieWorkerDone: " + result);
        mMovieModel = mMovieWorker.getMovies();
        mBoss.xxxComplete();
    }

    MovieJSON mMovieModel;
    /*private void createModel(){
        //this is where the threads, database or resource access is called to create the model
        Log.d("Movies", "MovieButler.createModel()");
        mMovieModel = new MovieModel();

        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
    }*/



    public MovieJSON getModel( ){
        return mMovieModel;
    }

}
