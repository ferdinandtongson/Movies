package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.util.Log;

import me.makeachoice.movies.model.MovieModel;
import me.makeachoice.movies.R;

/**
 * MovieButler handles the creation of the AppDemo model, taking resources from a flat file,
 * database or webservice and converting it into a model for consumption by the controller
 */
public class MovieButler {

    Context mActivityContext;
    public MovieButler(Context ctx){
        Log.d("SimpleListFragment", "MovieButler");
        mActivityContext = ctx;
        createModel();
    }

    MovieModel mMovieModel;
    private void createModel(){
        //this is where the threads, database or resource access is called to create the model
        Log.d("SimpleListFragment", "MovieButler.createModel()");
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
