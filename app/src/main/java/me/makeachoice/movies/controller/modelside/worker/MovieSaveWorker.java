package me.makeachoice.movies.controller.modelside.worker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

import me.makeachoice.movies.model.db.contract.MovieContract;
import me.makeachoice.movies.model.item.MovieItem;

/**
 * MovieSaveWorker - gets movie item data from the database, extends AsyncTask<>
 *
 * It uses other classes to assist saving data to the database:
 *      MovieContract - converts MovieItem to ContentValues
 *
 * Methods from AsyncTask
 *      Boolean doInBackground(String...)
 *      Boolean onPostExecute(Boolean)
 *
 */
public class MovieSaveWorker extends AsyncTask<String, Void, Boolean> {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<MovieItem> - list of poster item data
 *      MovieContract mContract - contract class for movies
 *      Bridge mBridge - bridge communication interface
 */
/**************************************************************************************************/

    //mMovies - list of movie item data to be saved
    private ArrayList<MovieItem> mMovies;

    //mContract - contract class for movies
    private MovieContract mContract;

    //mBridge - bridge communication used by Butler class
    Bridge mBridge;

    //Implemented communication line to a Valet class
    public interface Bridge{
        //get Database from Bridge
        SQLiteDatabase getDatabase();
        //notify Bridge that poster data has been saved
        void movieDataSaved(Boolean result);
    }



/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterSaveWorker - constructor
 * @param bridge - bridge communication used by Valet class
 * @param movies - list of movie item data to be saved
 */
    public MovieSaveWorker(Bridge bridge, ArrayList<MovieItem> movies){
        //Class implementing Bridge interface
        mBridge = bridge;

        //movie item arrayList to be saved
        mMovies = movies;

        //instantiate Contract class
        mContract = new MovieContract();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented AsyncTask methods
 *      Boolean doInBackground(String...) - method inherited from AsyncTask; runs on a background
 *          Thread. Save poster Item data to database
 *      void onPostExecute(Boolean) - method inherited from AsyncTask; runs on UI Thread. Informs
 *          Bridge that the work has completed.
 */
/**************************************************************************************************/
/**
 * Boolean doInBackground(String... params) - saves poster item data to database
 * @param param - dynamic array of Strings, param[0] = name of table, param[1] = query to database
 * @return - boolean default = true
 */
    protected Boolean doInBackground(String... param){
        //save poster item data to database, param[0] = name of talbe, param[1] = query to database
        saveMoviesToDB(mMovies, param[0], param[1]);

        //return true
        return true;
    }

/**
 * void onPostExecute(Boolean) - runs on UI thread, called when doInBackground is completed. Lets
 * Bridge know that the poster item data has been saved.
 */
    protected void onPostExecute(Boolean result){
        //inform Bridge class that the database is ready
        mBridge.movieDataSaved(result);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void saveMoviesToDB(ArrayList<MovieItem>,String,String) - save movie data to database
 *      void updateMovies(ArrayList<MovieItem>,String) - update movie table with new data
 *      void insertMovies(ArrayList<MovieItem>,String) - insert new data into movie table
 */
/**************************************************************************************************/
/**
 * void saveMoviesToDB(ArrayList<MovieItem>,String,String) - save movie data to database.
 * Checks poster table if it already has poster data or not.
 * @param movies - array list of movie item data
 * @param tableName - name of movie table to save data into
 * @param query - query call to check if poster table has movie data already
 */
    public void saveMoviesToDB(ArrayList<MovieItem> movies, String tableName, String query){
        //query movie table for data
        Cursor cursor = mBridge.getDatabase().rawQuery(query, null);

        //check if there is already data in the movie table
        if(cursor.getCount() > 0){
            //there is already data, update movie table with new data
            updateMovies(movies, tableName);
        }
        else{
            //no data, insert new data into movie table
            insertMovies(movies, tableName);
        }

        //close cursor
        cursor.close();
    }

/**
 * void updateMovies(ArrayList<MovieItem>,String) - update movie table with new data.
 * @param movies - arrayList of movie item data to save
 * @param tableName - name of movie table
 */
    private void updateMovies(ArrayList<MovieItem> movies, String tableName){
        //whereClause string object
        String whereClause;

        //get number of movies to save into database
        int count = movies.size();

        //loop through movies
        for(int i = 0; i < count; i++){
            //get movie item in list
            MovieItem item = movies.get(i);

            //create whereClause to update row in movie table
            whereClause = MovieContract.COLUMN_NAME_ORDER + " = " + i;

            //update movie table row
            int rowChanged = mBridge.getDatabase().update(tableName,
                    mContract.getContentValues(item, i), whereClause, null);

            //check if row has changed, if zero means missing row
            if(rowChanged == 0){
                //row has not changed, insert new movie item data into poster table
                mBridge.getDatabase().insert(tableName, null, mContract.getContentValues(item, i));
            }
        }
    }

/**
 * void insertMovies(ArrayList<MovieItem>,String) - insert new data into movie table.
 * @param movies - movie item arrayList
 * @param tableName - name of movie table
 */
    private void insertMovies(ArrayList<MovieItem> movies, String tableName){
        //get number of movies to save
        int count = movies.size();

        //loop through movies
        for(int i = 0; i < count; i++){
            //get movie item in list
            MovieItem item = movies.get(i);

            //insert new movie item data into poster table
            mBridge.getDatabase().insert(tableName, null, mContract.getContentValues(item, i));
        }
    }

/**************************************************************************************************/

}