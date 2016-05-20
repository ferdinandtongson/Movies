package me.makeachoice.movies.controller.modelside.worker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import me.makeachoice.movies.model.db.contract.PosterContract;
import me.makeachoice.movies.model.item.PosterItem;

/**
 * PosterSaveWorker - gets poster item data from the database, extends AsyncTask<>
 *
 * Methods from AsyncTask
 *      Boolean doInBackground(String...)
 *      void onProgressUpdate(Void...)
 *      Boolean onPostExecute(Boolean)
 *      void onCancelled()
 *
 */
public class PosterSaveWorker extends AsyncTask<String, Void, Boolean> {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<PosterItem> - list of poster item data
 *      Bridge mBridge - bridge communication interface
 */
/**************************************************************************************************/

    //mBridge - bridge communication used by Butler class
    Bridge mBridge;

    //mPosters - list of posters item data to be saved
    private ArrayList<PosterItem> mPosters;

    //Implemented communication line to a Valet class
    public interface Bridge{
        //get Database from Bridge
        SQLiteDatabase getDatabase();
        //notify Bridge that poster data has been saved
        void posterDataSaved(Boolean result);
    }



/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterSaveWorker - constructor
 * @param bridge - bridge communication used by Valet class
 */
    public PosterSaveWorker(Bridge bridge, ArrayList<PosterItem> posters){
        //Class implementing Bridge interface
        mBridge = bridge;

        //poster item arrayList
        mPosters = posters;
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
 *          Thread. Save poster Item data to database
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
 * Boolean doInBackground(String... params) - saves poster item data to database
 * @param param - dynamic array of Strings, param[0] = name of table, param[1] = query to database
 * @return - boolean default = true
 */
    protected Boolean doInBackground(String... param){
        //save poster item data to database, param[0] = name of talbe, param[1] = query to database
        savePostersToDB(mPosters, param[0], param[1]);

        //return true
        return true;
    }

/**
 * void onProgressUpdate() - does nothing
 */
    protected void onProgressUpdate(){
        //does nothing
    }

/**
 * void onPostExecute(Boolean) - runs on UI thread, called when doInBackground is completed. Lets
 * Bridge know that the poster item data has been saved.
 */
    protected void onPostExecute(Boolean result){
        //inform Butler class that the database is ready
        mBridge.posterDataSaved(result);
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
 *      void savePostersToDB(ArrayList<PosterItem>,String,String) - save poster data to database
 *      void updatePosters(ArrayList<PosterItem>,String) - update poster table with new data
 *      void insertPosters(ArrayList<PosterItem>,String) - insert new data into poster table
 *      ContentValues getContentValues(PosterItem,int) - convert PosterItem into ContentValues
 *      byte[] getBitmapFromURL(PosterItem) - gets poster image from API call
 *      byte[] convertBitmap(Bitmap) - convert bitmap image into byte[]
 */
/**************************************************************************************************/
/**
 * void savePostersToDB(ArrayList<PosterItem>,String,String) - save poster data to database.
 * Checks poster table if it already has poster data or not.
 * @param posters - array list of poster item data
 * @param tableName - name of poster table to save data into
 * @param query - query call to check if poster table has poster data already
 */
    public void savePostersToDB(ArrayList<PosterItem> posters, String tableName, String query){
        //query poster table for data
        Cursor cursor = mBridge.getDatabase().rawQuery(query, null);

        //check if there is already data in the poster table
        if(cursor.getCount() > 0){
            //there is already data, update poster table with new data
            updatePosters(posters, tableName);
        }
        else{
            //no data, insert new data into poster table
            insertPosters(posters, tableName);
        }

        //close cursor
        cursor.close();
    }

/**
 * void updatePosters(ArrayList<PosterItem>,String) - update poster table with new data.
 * @param posters - arrayList of poster item data to save
 * @param tableName - name of poster table
 */
    private void updatePosters(ArrayList<PosterItem> posters, String tableName){
        //whereClause string object
        String whereClause;

        //get number of posters to save into database
        int count = posters.size();

        //loop through posters
        for(int i = 0; i < count; i++){
            //get poster item in list
            PosterItem item = posters.get(i);

            //create whereClause to update row in poster table
            whereClause = PosterContract.COLUMN_NAME_ORDER + " = " + i;

            //update poster table row
            mBridge.getDatabase().update(tableName, getContentValues(item, i), whereClause, null);
        }
    }

/**
 * void insertPosters(ArrayList<PosterItem>,String) - insert new data into poster table.
 * @param posters - poster item arrayList
 * @param tableName - name of poster table
 */
    private void insertPosters(ArrayList<PosterItem> posters, String tableName){
        //get number of posters to save
        int count = posters.size();

        //loop through posters
        for(int i = 0; i < count; i++){
            //get poster item in list
            PosterItem item = posters.get(i);

            //insert new poster item data into poster table
            mBridge.getDatabase().insert(tableName, null, getContentValues(item, i));
        }
    }

/**
 * ontentValues getContentValues(PosterItem,int) - convert PosterItem into ContentValues.
 * ContentValues allows for easy consumption of data into the database.
 * @param item - poster item data
 * @param index - position of list item, used in ordering the posters
 * @return - ContentValues object ready for database consumption
 */
    private ContentValues getContentValues(PosterItem item, int index){
        ContentValues values = new ContentValues();
        values.put(PosterContract.COLUMN_NAME_MOVIE_ID, item.getTMDBId());
        values.put(PosterContract.COLUMN_NAME_ORDER, index);
        values.put(PosterContract.COLUMN_NAME_TITLE, item.getTitle());
        values.put(PosterContract.COLUMN_NAME_POSTER_PATH, item.getPosterPath());
        values.put(PosterContract.COLUMN_NAME_BITMAP, getBitmapFromURL(item));

        return values;
    }

/**
 * byte[] getBitmapFromURL(PosterItem) - gets poster image from API call. Uses the poster path
 * url to download the poster image. Adds the byte[] and bitmap to the poster item and returns
 * the byte[] to be saved into the database
 * @param item - poster item data to save to database
 * @return - byte[] of poster image to be saved into database
 */
    private byte[] getBitmapFromURL(PosterItem item){
        try {
            //create url object with poster path string (complete API call to TMDB)
            java.net.URL url = new java.net.URL(item.getPosterPath());

            //open connection to url
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //allow for DoInput
            connection.setDoInput(true);

            //attempt url connection
            connection.connect();

            //get InputStream from url connection
            InputStream input = connection.getInputStream();

            //convert InputStream into bitmap
            Bitmap bmpPoster = BitmapFactory.decodeStream(input);

            //save bitmap into poster item
            item.setPoster(bmpPoster);

            //save byte[] into poster item
            item.setPosterBytes(convertBitmap(bmpPoster));

            //return byte[]
            return item.getPosterBytes();
        } catch (IOException e) {
            //IOException event happened
            e.printStackTrace();
            return null;
        }
    }

/**
 * byte[] convertBitmap(Bitmap) - convert bitmap image into byte[].
 * @param image - bitmap image to convert into byte[]
 * @return - byte[] of image
 */
    private byte[] convertBitmap(Bitmap image){
        //create byt[] array output stream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //compress bitmap image into byte[]
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);

        //return byte[]
        return bos.toByteArray();
    }

/**************************************************************************************************/

}