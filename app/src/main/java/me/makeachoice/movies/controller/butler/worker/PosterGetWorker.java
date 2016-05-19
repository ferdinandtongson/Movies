package me.makeachoice.movies.controller.butler.worker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.util.ArrayList;

import me.makeachoice.movies.model.db.contract.PosterContract;
import me.makeachoice.movies.model.item.PosterItem;

/**
 * PosterGetWorker - gets poster item data from the database, extends AsyncTask<>
 *
 * Methods from AsyncTask
 *      Boolean doInBackground(String...)
 *      void onProgressUpdate(Void...)
 *      Boolean onPostExecute(ArrayList<PosterItem>)
 *      void onCancelled()
 *
 */
public class PosterGetWorker extends AsyncTask<String, Void, ArrayList<PosterItem>> {

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
        //notify Bridge that poster item data has been retrieved
        void postersRetrieved(ArrayList<PosterItem> posters);
    }



/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterGetWorker - constructor
 * @param bridge - bridge communication used by Valet class
 */
    public PosterGetWorker(Bridge bridge){
        //Class implementing Bridge interface
        mBridge = bridge;

    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      - None -
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/

    //- None -

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented AsyncTask methods
 *      void onPreExecute() - method inherited from AsyncTask; runs on UI Thread. Does nothing.
 *      Boolean doInBackground(String...) - method inherited from AsyncTask; runs on a background
 *          Thread. Access the database to get poster Item data
 *      void onProgressUpdate(Integer...) - method inherited from AsyncTask; runs on UI Thread.
 *          Does nothing.
 *      void onPostExecute(Boolean) - method inherited from AsyncTask; runs on UI Thread. Informs
 *          Bridge that the work has completed.
 *      void onCancelled() - method inherited from AsyncTask; runs on UI Thread. Does nothing.
 */
/**************************************************************************************************/
/**
 * void onPreExecute() - does nothing.
 */
    @Override
    protected void onPreExecute(){
        //does nothing
   }

/**
 * ArrayList<PosterItem> doInBackground(String... params) - retrieves poster item data from the db.
 * @param param - dynamic array of Strings, param[0] = query string to retrieve poster data
 * @return - arrayList of poster item data
 */
    protected ArrayList<PosterItem> doInBackground(String... param){
        //retrieve poster data from the database, param[0] is a select all query
        return retrievePostersFromDB(param[0]);
    }

/**
 * void onProgressUpdate() - does nothing
 */
    protected void onProgressUpdate(){
        //does nothing
    }

/**
 * void onPostExecute(ArrayList<PosterItem>) - runs on UI thread, called when doInBackground is
 * completed. Lets Bridge know that the poster item data requested has been retrieved.
 */
    protected void onPostExecute(ArrayList<PosterItem> posters){
        //inform Butler class that the database is ready
        mBridge.postersRetrieved(posters);
    }

/**
 * void onCancelled() - called if the Task is canceled. Does nothing.
 */
    @Override
    protected void onCancelled(){
        //does nothing
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      ArrayList<PosterItem> retrievePostersFromDB(String) - retrieves poster data from database
 *      ArrayList<PosterItem> preparePosterList(Cursor) - prepares poster data for View consumption
 */
/**************************************************************************************************/
/**
 * ArrayList<PosterItem> retrievePostersFromDB(String) - retrieves poster data from database.
 * Makes a database query call and if there is poster data in the database, prepares the data
 * for View consumption or return an empty arrayList, if not.
 * @param query - query call to get poster data from the database
 * @return - posterItem arrayList or an empty list
 */
    public ArrayList<PosterItem> retrievePostersFromDB(String query){

        //run query to retrieve poster data in the database
        Cursor cursor = mBridge.getDatabase().rawQuery(query, null);

        //check if there is poster data present in the database
        if(cursor != null && cursor.getCount() != 0){
            //has poster data, prepare database data for View consumption
            return preparePosterList(cursor);
        }

        //no poster data in database, return empty arrayList
        return new ArrayList<>();
    }

    /**
     * ArrayList<PosterItem> preparePosterList(Cursor) - prepares poster data for View consumption.
     * Takes cursor data and puts them in PosterItem objects. As well, decodes byte[] to bitmaps
     * @param cursor - cursor to poster data in database
     * @return - poster item arrayList
     */
    private ArrayList<PosterItem> preparePosterList(Cursor cursor){
        //initialize arrayList
        ArrayList<PosterItem> posters = new ArrayList<>();

        //create Bitmap object reference
        Bitmap bitmap;

        //move cursor to first item in cursor
        cursor.moveToFirst();

        //get number of poster rows in cursor
        int count = cursor.getCount();

        //loop through cursor
        for(int i = 0; i < count; i++){
            //create PosterItem object with the movie id, movie title, and poster path (url)
            PosterItem item = new PosterItem(cursor.getInt(PosterContract.INDEX_MOVIE_ID),
                    cursor.getString(PosterContract.INDEX_TITLE),
                    cursor.getString(PosterContract.INDEX_POSTER_PATH));

            //get byte[] from database
            byte[] image = cursor.getBlob(PosterContract.INDEX_BITMAP);
            //decode byte[] array to bitmap
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            //set byte[] to poster item
            item.setPosterBytes(image);

            //set bitmap to poster item
            item.setPoster(bitmap);

            //add posterItem to arrayList
            posters.add(item);

            //move to next row in cursor
            cursor.moveToNext();
        }

        //close cursor
        cursor.close();

        //return poster item arrayList
        return posters;
    }

/**************************************************************************************************/
}