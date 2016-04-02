package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.util.Log;

import me.makeachoice.movies.controller.worker.MovieWorker;
import me.makeachoice.movies.model.MovieModel;
import me.makeachoice.movies.R;

/**
 * MovieButler handles the creation of the AppDemo model, taking resources from a flat file,
 * database or webservice and converting it into a model for consumption by the controller
 */
public class MovieButler extends MyButler{

    public MovieButler(Context ctx){
        Log.d("Movies", "MovieButler: " + ctx.toString());
        mActivityContext = ctx;
        createModel();

    }


    MovieWorker mMovieWorker;
    public void establishHttpConnection(){
        Log.d("Movies", "MovieButler.establishHttpConnection");
        mMovieWorker = new MovieWorker(this);

        if(mMovieWorker.hasConnectivity(mActivityContext)){
            Log.d("Movies", "     connection true");
            mMovieWorker.execute(
                    mMovieWorker.TMDB_URL_DISCOVER_MOVIE,
                    mMovieWorker.TMDB_API_KEY,
                    mMovieWorker.TMDB_SORT,
                    mMovieWorker.SORT_POPULARITY_DESC
            );
        }
        else{
            Log.d("Movies", "     no connection");
        }
    }

    public void workComplete(Boolean result) {
        Log.d("Movies", "MovieButler.movieWorkerDone: " + result);
    }

    MovieModel mMovieModel;
    private void createModel(){
        //this is where the threads, database or resource access is called to create the model
        Log.d("Movies", "MovieButler.createModel()");
        mMovieModel = new MovieModel();

        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
        mMovieModel.addMovie("Title1", "Plot1", R.drawable.sample_1, "Rating1", "Date1");
    }



    public MovieModel getModel( ){
        return mMovieModel;
    }

}
