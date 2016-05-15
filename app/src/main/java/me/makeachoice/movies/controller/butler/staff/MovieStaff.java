package me.makeachoice.movies.controller.butler.staff;

import android.util.Log;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * MovieStaff maintains the buffer objects holding MovieModel data
 */
public class MovieStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *
 *      ArrayList<MovieModel> mPopularModels - Popular Movie raw data from TMDB
 *      ArrayList<MovieModel> mTopRatedModels - Top Rated Movie raw data from TMDB
 *      ArrayList<MovieModel> mNowPlayingModels - Now Playing Movie raw data from TMDB
 *      ArrayList<MovieModel> mUpcomingModels - Upcoming Movie raw data from TMDB
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;
    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mPopularModels - array list of raw data of Popular Movies from TMDB
    private ArrayList<MovieModel> mPopularModels;
    //mTopRatedModels - array list of raw data of Top Rated Movies from TMDB
    private ArrayList<MovieModel> mTopRatedModels;
    //mNowPlayingModels - array list of raw data of Now Playing Movies from TMDB
    private ArrayList<MovieModel> mNowPlayingModels;
    //mUpcomingModles - array list of raw data of Upcoming Movies from TMDB
    private ArrayList<MovieModel> mUpcomingModels;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterStaff - constructor, registers to Boss
 * data buffers.
 * @param boss - Boss class
 */
    public MovieStaff(Boss boss){
        Log.d("Start", "     MovieStaff - constructor");

        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(boss);

        //get TheMovieDB api key from resource file
        mTMDBKey = boss.getString(R.string.api_key_tmdb);

        //initialize buffers
        initBuffers();
    }

/**
 * void initBuffers() - initialize buffers to hold MovieModel data
 */
    private void initBuffers(){
        //buffer for MovieModels for Popular movies
        mPopularModels = new ArrayList<>();
        //buffer for MovieModels for Top Rated movies
        mTopRatedModels = new ArrayList<>();
        //buffer for MovieModels for NowPlaying movies
        mNowPlayingModels = new ArrayList<>();
        //buffer for MovieModels for Upcoming movies
        mUpcomingModels = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      MovieModel getMovie(int, int) - get a movie model from the buffer
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * MovieModel getMovie(int, int) - get a movie model from the buffer, by type and index
 * @param movieType - movie model buffer type
 * @param position - index location of movie model in buffer
 * @return - movie model
 */
    public MovieModel getModel(int movieType, int position){
        //check which movie buffer type
        switch (movieType) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //get movie model from Popular buffer
                return mPopularModels.get(position);
            case PosterHelper.NAME_ID_TOP_RATED:
                //get movie model from Top Rated buffer
                return mTopRatedModels.get(position);
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //get movie model from Now Playing buffer
                return mNowPlayingModels.get(position);
            case PosterHelper.NAME_ID_UPCOMING:
                //get movie model from Upcoming buffer
                return mUpcomingModels.get(position);
            case PosterHelper.NAME_ID_FAVORITE:
                //TODO - need to handle Favorite movie request
                return null;
            default:
                return null;
        }

    }

/**
 * ArrayList<MovieModel> getMovieModels(int) - get the list of movie models requested
 * @param request - type of MovieModels
 * @return - list of movie models requested
 */
    public ArrayList<MovieModel> getModels(int request){
        //check type of MovieModels to save
        switch (request) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //return Most Popular movie models
                return mPopularModels;
            case PosterHelper.NAME_ID_TOP_RATED:
                //return Top Rated movie models
                return mTopRatedModels;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //return Now Playing movie models
                return mNowPlayingModels;
            case PosterHelper.NAME_ID_UPCOMING:
                //return Upcoming movie models
                return mUpcomingModels;
        }

        return new ArrayList<MovieModel>();
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void saveMovieModels(int, ArrayList<MovieModel>) - save MovieModels to buffer
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * void saveMovieModels(int,ArrayList<MovieModel>) - save MovieModels to buffer
 * @param request - type of MovieModels to save
 * @param movieModels - MovieModels to be saved to buffer
 */
    public void saveMovieModels(ArrayList<MovieModel> movieModels, int request){
        //check type of MovieModels to save
        switch (request) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //save movie models to Popular buffer
                mPopularModels = new ArrayList<>(movieModels);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //save movie models to Top Rated buffer
                mTopRatedModels = new ArrayList<>(movieModels);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //save movie models to Now Playing buffer
                mNowPlayingModels = new ArrayList<>(movieModels);
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //save movie models to Upcoming buffer
                mUpcomingModels = new ArrayList<>(movieModels);
                break;
        }
    }

/**
 * void onFinish() - nulls all of the data in the arrayList buffers
 */
    public void onFinish(){
        mPopularModels.clear();
        mTopRatedModels.clear();
        mNowPlayingModels.clear();
        mUpcomingModels.clear();
    }

/**************************************************************************************************/

}
