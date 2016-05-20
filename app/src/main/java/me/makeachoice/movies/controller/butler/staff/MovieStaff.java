package me.makeachoice.movies.controller.butler.staff;

import java.util.ArrayList;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * MovieStaff maintains the buffer objects holding MovieModel data
 *
 * It uses other classes to assist in the upkeep of the buffers:
 *      PosterHelper - get static Poster resource ids
 */
public class MovieStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<MovieModel> mPopularModels - Popular Movie raw data from TMDB
 *      ArrayList<MovieModel> mTopRatedModels - Top Rated Movie raw data from TMDB
 *      ArrayList<MovieModel> mNowPlayingModels - Now Playing Movie raw data from TMDB
 *      ArrayList<MovieModel> mUpcomingModels - Upcoming Movie raw data from TMDB
 */
/**************************************************************************************************/

    //mPopularModels - array list of raw data of Popular Movies from TMDB
    private ArrayList<MovieModel> mPopularModels;
    //mTopRatedModels - array list of raw data of Top Rated Movies from TMDB
    private ArrayList<MovieModel> mTopRatedModels;
    //mNowPlayingModels - array list of raw data of Now Playing Movies from TMDB
    private ArrayList<MovieModel> mNowPlayingModels;
    //mUpcomingModels - array list of raw data of Upcoming Movies from TMDB
    private ArrayList<MovieModel> mUpcomingModels;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterStaff - constructor, initialize movie model buffers
 * @param boss - Boss class
 */
    public MovieStaff(Boss boss){
        //initialize buffers
        initBuffers();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Initialization Methods:
 *      void initBuffers() - initialize movie model arrayList buffers
 */
/**************************************************************************************************/
/**
 * void initBuffers() - initialize buffers to hold MovieModel data
 */
    private void initBuffers(){
        //buffer for MovieModels for Most Popular movies
        mPopularModels = new ArrayList<>();
        //buffer for MovieModels for Top Rated movies
        mTopRatedModels = new ArrayList<>();
        //buffer for MovieModels for Now Playing movies
        mNowPlayingModels = new ArrayList<>();
        //buffer for MovieModels for Upcoming movies
        mUpcomingModels = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      MovieModel getMovie(int, int) - get a movie model from the buffer
 *      ArrayList<MovieModel> getMovieModels(int) - get the list of movie models requested
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
            default:
                return null;
        }

    }

/**
 * ArrayList<MovieModel> getMovieModels(int) - get the list of movie models requested
 * @param movieType - type of MovieModels
 * @return - list of movie models requested
 */
    public ArrayList<MovieModel> getModels(int movieType){
        //check type of MovieModels to get
        switch (movieType) {
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

        //invalid request, return empty list
        return new ArrayList<MovieModel>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setMovieModels(ArrayList<MovieModel>, int) - set MovieModels to buffer
 */
/**************************************************************************************************/
/**
 * void setMovieModels(ArrayList<MovieModel>, int) - set MovieModels to buffer
 * @param movieType - type of MovieModels to save
 * @param movieModels - MovieModels to be saved to buffer
 */
    public void setMovieModels(ArrayList<MovieModel> movieModels, int movieType){
        //check type of MovieModels to save
        switch (movieType) {
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

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * void onFinish() - nulls all of the data in the arrayList buffers
 */
    public void onFinish(){
        //clear and null Most Popular buffer
        mPopularModels.clear();
        mPopularModels = null;

        //clear and null Top Rated buffer
        mTopRatedModels.clear();
        mTopRatedModels = null;

        //clear and null Now Playing buffer
        mNowPlayingModels.clear();
        mNowPlayingModels = null;

        //clear and null Upcoming buffer
        mUpcomingModels.clear();
        mUpcomingModels = null;
    }

/**************************************************************************************************/

}
