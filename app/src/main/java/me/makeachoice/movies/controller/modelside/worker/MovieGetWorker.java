package me.makeachoice.movies.controller.modelside.worker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

import me.makeachoice.movies.model.db.contract.MovieContract;
import me.makeachoice.movies.model.item.CastItem;
import me.makeachoice.movies.model.item.GenreItem;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.model.item.ReviewItem;
import me.makeachoice.movies.model.item.VideoItem;

/**
 * MovieGetWorker - gets movie item data from the database, extends AsyncTask<>
 *
 * Methods from AsyncTask
 *      Boolean doInBackground(String...)
 *      Boolean onPostExecute(ArrayList<PosterItem>)
 *
 */
public class MovieGetWorker extends AsyncTask<String, Void, ArrayList<MovieItem>> {

/**************************************************************************************************/
/**
 * Class Variables:
 *      Bridge mBridge - Bridge communication interface
 */
/**************************************************************************************************/

    //mBridge - Bridge communication interface
    Bridge mBridge;

    //Implemented communication line to a Valet class
    public interface Bridge{
        //get Database from Bridge
        SQLiteDatabase getDatabase();
        //notify Bridge that movie item data has been retrieved
        void moviesRetrieved(ArrayList<MovieItem> movies);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieGetWorker - constructor
 * @param bridge - bridge communication used by Valet class
 */
    public MovieGetWorker(Bridge bridge){
        //Class implementing Bridge interface
        mBridge = bridge;

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented AsyncTask methods
 *      Boolean doInBackground(String...) - method inherited from AsyncTask; runs on a background
 *          Thread. Access the database to get movie Item data
 *      void onPostExecute(Boolean) - method inherited from AsyncTask; runs on UI Thread. Informs
 *          Bridge that the work has completed.
 */
/**************************************************************************************************/
/**
 * ArrayList<MovieItem> doInBackground(String... params) - retrieves movie item data from the db.
 * @param param - dynamic array of Strings, param[0] = query string to retrieve movie data
 * @return - arrayList of movie item data
 */
    protected ArrayList<MovieItem> doInBackground(String... param){
        //retrieve movie data from the database, param[0] is a select all query
        return retrieveMoviesFromDB(param[0]);
    }

/**
 * void onPostExecute(ArrayList<MovieItem>) - runs on UI thread, called when doInBackground is
 * completed. Lets Bridge know that the movie item data requested has been retrieved.
 */
    protected void onPostExecute(ArrayList<MovieItem> movies){
        //inform Bridge class that the database is ready
        mBridge.moviesRetrieved(movies);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      ArrayList<MovieItem> retrieveMoviesFromDB(String) - retrieves movie data from database
 *      ArrayList<MovieItem> prepareMovieList(Cursor) - prepares movie data for View consumption
 */
/**************************************************************************************************/
/**
 * ArrayList<MovieItem> retrieveMoviesFromDB(String) - retrieves movie data from database.
 * Makes a database query call and if there is movie data in the database, prepares the data
 * for View consumption or return an empty arrayList, if not.
 * @param query - query call to get movie data from the database
 * @return - movieItem arrayList or an empty list
 */
    public ArrayList<MovieItem> retrieveMoviesFromDB(String query){

        //run query to retrieve movie data in the database
        Cursor cursor = mBridge.getDatabase().rawQuery(query, null);

        //check if there is movie data present in the database
        if(cursor != null && cursor.getCount() != 0){
            //has movie data, prepare database data for View consumption
            return prepareMovieList(cursor);
        }

        //no movie data in database, return empty arrayList
        return new ArrayList<>();
    }

/**
 * ArrayList<MovieItem> prepareMovieList(Cursor) - prepares movie data for View consumption.
 * Takes cursor data and puts them in MovieItem objects.
 * @param cursor - cursor to movie data in database
 * @return - movie item arrayList
 */
    private ArrayList<MovieItem> prepareMovieList(Cursor cursor){
        //initialize arrayList
        ArrayList<MovieItem> movies = new ArrayList<>();

        //move cursor to first item in cursor
        cursor.moveToFirst();

        //get number of poster rows in cursor
        int count = cursor.getCount();

        //loop through cursor
        for(int i = 0; i < count; i++){
            //create MovieItem object
            MovieItem item = new MovieItem();
            item.setTMDBId(cursor.getInt(MovieContract.INDEX_MOVIE_ID));
            item.setTitle(cursor.getString(MovieContract.INDEX_TITLE));
            item.setOverview(cursor.getString(MovieContract.INDEX_OVERVIEW));
            item.setReleaseDate(cursor.getString(MovieContract.INDEX_RELEASE_DATE));
            item.setIMDBId(cursor.getString(MovieContract.INDEX_IMDB_ID));
            item.setHomepage(cursor.getString(MovieContract.INDEX_HOME_PAGE));

            item.setOriginalTitle(cursor.getString(MovieContract.INDEX_ORIGINAL_TITLE));
            item.setOriginalLanguage(cursor.getString(MovieContract.INDEX_ORIGINAL_LANGUAGE));

            item.setPopularity(cursor.getFloat(MovieContract.INDEX_POPULARITY));
            item.setVoteCount(cursor.getInt(MovieContract.INDEX_VOTE_COUNT));
            item.setVoteAverage(cursor.getFloat(MovieContract.INDEX_VOTE_AVERAGE));

            item.setPosterPath(cursor.getString(MovieContract.INDEX_POSTER_PATH));
            item.setPosterBytes(cursor.getBlob(MovieContract.INDEX_POSTER_BYTES));

            //set default values for Movie Info detail
            item.setIMDBId("");
            item.setHomepage("");

            item.setGenres(new ArrayList<GenreItem>());
            item.setCast(new ArrayList<CastItem>());
            item.setReviews(new ArrayList<ReviewItem>());
            item.setVideos(new ArrayList<VideoItem>());


            //add movieItem to arrayList
            movies.add(item);

            //move to next row in cursor
            cursor.moveToNext();
        }

        //close cursor
        cursor.close();

        //return poster item arrayList
        return movies;
    }

/**************************************************************************************************/
}