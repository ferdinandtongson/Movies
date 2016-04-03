package me.makeachoice.movies.controller.worker;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.makeachoice.movies.controller.butler.MyButler;
import me.makeachoice.movies.model.json.MovieJSON;

/**************************************************************************************************/
/**
 * MovieWorker - handles The Movie DB Http api request, processes the JSON response generated by
 * the request for consumption and passes the processed data up to the Butler.
 *
 * Variable from MyWorker:
 *      mButler;
 *
 * Methods from MyWorker:
 *      Boolean doInBackground(String... params)
 *      void onProgressUpdate(Integer... values)
 *      Boolean onPostExecute(Boolean result)
 *
 * Permissions needed in AndroidManifest file:
 *      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *      <uses-permission android:name="android.permission.INTERNET" />
 */
public class MovieWorker extends MyWorker{

    private ProgressDialog mDialog;
    public MovieWorker(MyButler butler){
        //Butler in charge of MovieWorker
        mButler = butler;
        mDialog = new ProgressDialog(mButler.getActivityContext());
    }

/**************************************************************************************************/
/**
 * URL string variables needed to communicate with The Movie DB api. The api calls can be
 * dynamically generated by taking pieces of variables and appending them together.
 *
 * TMDB_URL_XXX - beginning variable that has the api address and type of api call.
 * TMDB_API_KEY - add next, after TMDB_URL, so we have access to the api
 * TMDB_XXX - various parameters for the api call
 */
    //URL to API Discover/Movie
    public final String TMDB_URL_DISCOVER_MOVIE = "http://api.themoviedb.org/3/discover/movie?";

    //The Movie DB API key
    //TODO - need to move API Key, create one location to hold all keys
    public final String TMDB_API_KEY = "api_key=ec1c9e77ea098584409c2b2309c4f287";

    //The api sort command and sort options
    public final String TMDB_SORT = "&sort_by=";
        public final String SORT_POPULARITY_ASC = "popularity.asc";
        public final String SORT_POPULARITY_DESC = "popularity.desc";
        public final String SORT_DATE_ASC = "release_date.asc";
        public final String SORT_DATE_DESC = "release_date.desc";
        public final String SORT_TITLE_ASC = "original_title.asc";
        public final String SORT_TITLE_DESC = "original_title.desc";
        public final String SORT_RATE_ASC = "vote_average.asc";
        public final String SORT_RATE_DESC = "vote_average.desc";

    private final String TMBD_URL_POSTER = "https://image.tmdb.org/t/p/w500/";

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * boolean hasConnectivity(Context) - check to see if the phone has network connection
 * @return boolean - true if we have network connect, false if not
 *
 * NOTE - need to have <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *
 *
 */
    public boolean hasConnectivity(Context ctx){
        //get Connectivity Manger
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get access to network information from phone
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //check if we have connection
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else{
            return false;
        }
    }

    public MovieJSON getMovies(){
        return mMovies;
    }

/**************************************************************************************************/

    @Override
    protected void onPreExecute(){
        mDialog.setMessage("Doing something, please wait.");
        mDialog.show();
    }

/**
 * Boolean doInBackground(String... params) is called after the instantiated Worker class calls
 * XWorker.execute(String...). doInBackground runs on a background thread but is only meant for
 * short processing work, a couple seconds or so.
 *
 * Communicates with The Movie DB api, receives a JSON reponse and processes the response for
 * consumption by the Butler
 * @param params - a dynamic array of string varaible used to construct the url api call
 * @return - returns boolean value to onPostExecute
 */
    protected Boolean doInBackground(String... params){

        //url used
        String api_call = "";
        int count = params.length;

        //construct api call url
        for(int i = 0; i < count; i++){
            api_call = api_call + params[i];
        }

        //result of url call
        Boolean result;
        try {
            Log.d("Movies", "     at url");
            Log.d("Movies", "     " + api_call);
            //create url object with string variable
            URL url = new URL(api_call);
            //open http connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //receive input stream from url
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            //create bufferedReader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            //create a string reader to read buffer
            StringBuilder builder = new StringBuilder();

            //string variable to read each line in the buffer
            String inputString;
            //loop through all lines in buffer
            while ((inputString = bufferedReader.readLine()) != null) {
                //append lines into one string
                builder.append(inputString);
            }

            Log.d("Movies", builder.toString());
            //process JSON response
            result = processJSON(builder);

            //disconnect url connection
            urlConnection.disconnect();
        }
        catch (IOException e) {
            Log.d("Movies", "***** IOException *****");
            //IO exception, url call failed
            e.printStackTrace();
            return false;
        }

        return result;
    }

/**
 * void onProgressUpdate(Integer...) - runs on UI thread, can post updates to UI
 * @param values - a dynamic array of integers
 */
    protected void onProgressUpdate(Integer... values){
        //does nothing
    }

    /**
 * void onPostExecute(Boolean) - runs on UI thread, called when doInBackground is completed
 * @param result - boolean value on whether the background task completed successfully or not
 */
    protected void onPostExecute(Boolean result){
        //TODO - need to confirm success of background task
        if(mDialog.isShowing()){
            mDialog.dismiss();
        }

        mButler.workComplete(result);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * JSON response variables received from TheMovie DB api call.
 * {
 *     "page":X
 *     "result":[{....}],
 * }
 */
    private final String FIRST_KEY = "page";
        private int mPage;

    private final String SECOND_KEY = "results";
        private JSONArray mJSONArray;
            private final String ITEM_ORIGINAL_TITLE = "original_title";
            private final String ITEM_TITLE = "title";
            private final String ITEM_OVERVIEW = "overview";

            private final String ITEM_POSTER_PATH = "poster_path";
            private final String ITEM_ADULT = "adult";
            private final String ITEM_RELEASE_DATE = "release_date";
            private final String ITEM_GENRE_IDS = "genere_id";
            private final String ITEM_ORIGINAL_LANGUAGE = "original_language";
            private final String ITEM_BACKDROP_PATH = "backdrop_path";
            private final String ITEM_POPULARITY = "popularity";
            private final String ITEM_VOTE_COUNT = "vote_count";
            private final String ITEM_VIDEO = "video";
            private final String ITEM_VOTE_AVERAGE = "vote_average";

/**************************************************************************************************/

/**************************************************************************************************/

    MovieJSON mMovies;
    private Boolean processJSON(StringBuilder builder){
        Log.d("Movies", "MovieWorker.processJSON");
        mMovies = new MovieJSON();
        try{
            //convert string to JSON Object
            JSONObject topLevel = new JSONObject(builder.toString());

            //skip "page" results
            //int page = topLevel.getInt("page");

            Log.d("Movies", "     get json array");
            //get results from api call
            JSONArray jsonArray = topLevel.optJSONArray(SECOND_KEY);
            Log.d("Movies", "          here");

            //data model for Movie results from api response
            MovieJSON.MovieDetail modelObj;

            //get number of movies results received
            int count = jsonArray.length();

            Log.d("Movies", "     count: " + count);
            //loop through movies and model data for consumption
            for(int i = 0; i < count; i++){
                //process JSON object into MovieJSON.MovieDetail object
                modelObj = modelResponse(jsonArray.getJSONObject(i));

                //check if model processed okay, if not discard model
                if(modelObj != null){
                    //add model to movies
                    mMovies.addMovie(modelObj);
                    Log.d("Movies", "     title: " + modelObj.getTitle());
                }

            }

        }
        catch(JSONException e){
            Log.d("Movies", "***** JSONException - processJSON *****");
            e.printStackTrace();
            return false;
        }
        return true;

    }


    private MovieJSON.MovieDetail modelResponse(JSONObject obj){
        MovieJSON.MovieDetail detailObj;
        detailObj = new MovieJSON().new MovieDetail();

        try{
            detailObj.setOriginalLanguage(obj.getString(ITEM_ORIGINAL_TITLE));
            detailObj.setTitle(obj.getString(ITEM_TITLE));
            detailObj.setOverview(obj.getString(ITEM_OVERVIEW));

            detailObj.setReleaseDate(obj.getString(ITEM_RELEASE_DATE));
            detailObj.setOriginalLanguage(obj.getString(ITEM_ORIGINAL_LANGUAGE));

            detailObj.setPopularity(obj.getDouble(ITEM_POPULARITY));
            detailObj.setVoteCount(obj.getDouble(ITEM_VOTE_COUNT));
            detailObj.setVoteAverage(obj.getDouble(ITEM_VOTE_AVERAGE));

            //detailObj.setGenreId(obj.getJSONArray(ITEM_GENRE_IDS));
            detailObj.setAdult(obj.getBoolean(ITEM_ADULT));

            detailObj.setPosterPath(TMBD_URL_POSTER + obj.getString(ITEM_POSTER_PATH));
            detailObj.setBackdropPath(obj.getString(ITEM_BACKDROP_PATH));
            detailObj.setVideo(obj.getBoolean(ITEM_VIDEO));
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return detailObj;
    }
}
