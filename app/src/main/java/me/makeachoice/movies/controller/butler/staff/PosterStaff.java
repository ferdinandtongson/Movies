package me.makeachoice.movies.controller.butler.staff;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 *It also buffers the data so it
 * does not need to make repeated calls to the API or database.
 */
public class PosterStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      Boss mBoss - Boss class
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *
 *      ArrayList<MovieModel> mPopularModels - Popular Movie raw data from TMDB
 *      ArrayList<MovieModel> mTopRatedModels - Top Rated Movie raw data from TMDB
 *      ArrayList<MovieModel> mNowPlayingModels - Now Playing Movie raw data from TMDB
 *      ArrayList<MovieModel> mUpcomingModels - Upcoming Movie raw data from TMDB
 *
 *      ArrayList<PosterItem> mEmptyPosters - Empty poster data for PosterFragment
 *      ArrayList<PosterItem> mPopularPosters - Popular poster data for PosterFragment
 *      ArrayList<PosterItem> mTopRatedPosters - Top Rated poster data for PosterFragment
 *      ArrayList<PosterItem> mNowPlayingPosters - Now Playing poster data for PosterFragment
 *      ArrayList<PosterItem> mUpcomingPosters - Upcoming poster data for PosterFragment
 *      ArrayList<PosterItem> mFavoritePosters - Favorite poster data for PosterFragment
 */
/**************************************************************************************************/

    //mBoss - Boss class
    private Boss mBoss;
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

    //mPopularPosters - Popular poster data for PosterFragment
    private ArrayList<PosterItem> mPopularPosters;
    //mTopRatedPosters - Top Rated poster data for PosterFragment
    private ArrayList<PosterItem> mTopRatedPosters;
    //mNowPlayingPosters - Now Playing poster data for PosterFragment
    private ArrayList<PosterItem> mNowPlayingPosters;
    //mUpcomingPosters - Upcoming poster data for PosterFragment
    private ArrayList<PosterItem> mUpcomingPosters;
    //mFavoritePosters - Favorite poster data for PosterFragment
    private ArrayList<PosterItem> mFavoritePosters;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterStaff - constructor, registers to Boss
 * data buffers.
 * @param boss - Boss class
 */
    public PosterStaff(Boss boss){
        //Application context
        mBoss = boss;

        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(mBoss);

        //get TheMovieDB api key from resource file
        mTMDBKey = mBoss.getString(R.string.api_key_tmdb);

        //initialize buffers
        initBuffers();
    }

/**
 * void initBuffers() - initialize buffers to hold MovieModels, PosterItems and movie requests
 */
    private void initBuffers(){

        //initialize buffer to hold MovieModels
        initModelBuffers();

        //initialize buffer to hold PosterItems
        initPosterBuffers();
    }

    /**
     * void initModelBuffers - initialize buffers to hold MovieModels
     */
    private void initModelBuffers(){
        //buffer for MovieModels for Popular movies
        mPopularModels = new ArrayList<>();
        //buffer for MovieModels for Top Rated movies
        mTopRatedModels = new ArrayList<>();
        //buffer for MovieModels for NowPlaying movies
        mNowPlayingModels = new ArrayList<>();
        //buffer for MovieModels for Upcoming movies
        mUpcomingModels = new ArrayList<>();
    }

    /**
     * initPosterBuffers - initialize buffers to hold PosterItems
     */
    private void initPosterBuffers(){
        //buffer for PosterItems for Popular movies
        mPopularPosters = new ArrayList<>();
        //buffer for PosterItems for Top Rated movies
        mTopRatedPosters = new ArrayList<>();
        //buffer for PosterItems for Now Playing movies
        mNowPlayingPosters = new ArrayList<>();
        //buffer for PosterItems for Upcoming movies
        mUpcomingPosters = new ArrayList<>();
        //buffer for PosterItems for Favorite movies
        mFavoritePosters = new ArrayList<>();

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
    public MovieModel getMovie(int movieType, int position){
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

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      ArrayList<PosterItem> checkPosterBuffer(int) - return poster buffer if not empty
 *      ArrayList<PosterItem> preparePosterItems(ArrayList<MovieModel>) - convert MovieModels to
 *          PosterItems used by View
 *      void saveMovieModels(int, ArrayList<MovieModel>) - save MovieModels to buffer
 *      void savePosterItems(int, ArrayList<PosterItem>) - save PosterItems to buffer
 */
/**************************************************************************************************/
/**
 * ArrayList<PosterItem> checkPosterBuffer(int) - checks poster item buffer. If the buffer is
 * empty, a request is made to get the movie data and populate the buffer.
 * @param request - poster buffer request
 * @return - poster item buffer, either data requested or "empty" buffer
 */
    public ArrayList<PosterItem> checkPosterBuffer(int request){
        //check which type of posters being requested
        switch (request){
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //check Popular movie poster buffer if not empty
                if(mPopularPosters.size() > 0){
                    //return Popular movie poster buffer
                    return mPopularPosters;
                }
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //check Top Rated movie poster buffer if not empty
                if(mTopRatedPosters.size() > 0){
                    //return Top Rated movie poster buffer
                    return mTopRatedPosters;
                }
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //check Now Playing movie poster buffer if not empty
                if(mNowPlayingPosters.size() > 0){
                    //return Now Playing movie poster buffer
                    return mNowPlayingPosters;
                }
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //check Upcoming movie poster buffer if not empty
                if(mUpcomingPosters.size() > 0){
                    //return Upcoming movie poster buffer
                    return mUpcomingPosters;
                }
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                //check Favorite movie poster buffer if not empty
                if(mFavoritePosters.size() > 0){
                    //return Favorite movie poster buffer
                    return mFavoritePosters;
                }
                break;
        }

        //return "empty" poster buffer
        return new ArrayList<>();
    }



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
 * ArrayList<PosterItem> preparePosterItems(int, ArrayList<MovieModel>) - convert MovieModel
 * data to PosterItem data.
 * @param models - MovieModel data
 * @return - array list of PosterItem
 */
    public ArrayList<PosterItem> preparePosterItems(ArrayList<MovieModel> models, int request){

        //create an ArrayList to hold the list of poster items
        ArrayList<PosterItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = models.size();

        //loop through the data models
        for(int i = 0; i < count; i++){
            //get MovieModel
            MovieModel mod = models.get(i);

            //get uri poster path
            String posterPath = mTMDBUri.getImagePath(mod.getPosterPath(), mTMDBKey);

            //create poster item from movie model
            PosterItem item = new PosterItem(mod.getId(), mod.getTitle(), posterPath);

            //add item into array list
            itemList.add(item);
        }

        savePosterItems(itemList, request);

        //return poster item list
        return itemList;
    }

/**
 * void savePosterItems(int,ArrayList<PosterItem>) - save PosterItems to buffer
 * @param request - type of PosterItems to save
 * @param posters - PosterItems to be saved to buffer
 */
    private void savePosterItems(ArrayList<PosterItem> posters, int request){
        //check type of PosterItems to save
        switch (request) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //save poster items to Popular buffer
                mPopularPosters = new ArrayList<>(posters);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //save poster items to Top Rated buffer
                mTopRatedPosters = new ArrayList<>(posters);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //save poster items to Now Playing buffer
                mNowPlayingPosters = new ArrayList<>(posters);
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //save poster items to Upcoming buffer
                mUpcomingPosters = new ArrayList<>(posters);
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                //save poster items to Favorite buffer
                mFavoritePosters = new ArrayList<>(posters);
                break;
        }

    }



}
