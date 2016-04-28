package me.makeachoice.movies.controller.butler;

import android.content.Context;

import java.util.ArrayList;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.butler.worker.MovieWorker;
import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * PosterButler handles the creation of a list of PosterItems to be consumed by the View. It takes
 * data from API calls or from the database and process the data. It also buffers the data so it
 * does not need to make repeated calls to the API or database.
 *
 * Variables from MyButler:
 *      Boss mBoss
 *
 * Abstract Methods from MyButler:
 *      abstract public Context getActivityContext()
 *      abstract public void workComplete(Boolean)
 */
public class MovieButler extends MyButler{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *      MovieWorker mMovieWorker - AsyncTask class that makes API calls to get Movie details
 *      Boolean mWorking - boolean value to check if a Worker is working in the background
 *      ArrayList<Integer> mRequestBuffer - pending AsyncTask Movie request
 *      int mMovieRequest - current list of Movies being requested
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
 *
 *      int EMPTY_POSTERS_COUNT - number of "empty" poster items to create
 *      int EMPTY_ID - id number of "empty" poster item
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;

    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mMovieWorker - AsyncTask class that makes API calls to get Movie details
    private MovieWorker mMovieWorker;

    //mWorking - boolean value to check if a Worker is working in the background
    private Boolean mWorking;

    //mBufferRequest - pending AsyncTask Movie requests
    private ArrayList<Integer> mRequestBuffer;

    //mMovieRequest - current list of Movies being requested
    private int mMovieRequest;

    //mPopularModels - array list of raw data of Popular Movies from TMDB
    private ArrayList<MovieModel> mPopularModels;
    //mTopRatedModels - array list of raw data of Top Rated Movies from TMDB
    private ArrayList<MovieModel> mTopRatedModels;
    //mNowPlayingModels - array list of raw data of Now Playing Movies from TMDB
    private ArrayList<MovieModel> mNowPlayingModels;
    //mUpcomingModles - array list of raw data of Upcoming Movies from TMDB
    private ArrayList<MovieModel> mUpcomingModels;

    //mEmptyPosters - EmptyPoster data for PosterFragment
    private ArrayList<PosterItem> mEmptyPosters;
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

    //EMPTY_POSTERS_COUNT - number of "empty" poster items to create
    private int EMPTY_POSTERS_COUNT = 20;

    //EMPTY_ID - id number of "empty" poster item
    private int EMPTY_ID = -1;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieButler - constructor, registers to Boss, initialize URI builder, get API key and initialize
 * data buffers.
 * @param boss - Boss class
 */
    public MovieButler(Boss boss){
        //Application context
        mBoss = boss;

        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(this);

        //flag to check if work is being done in the background
        mWorking = false;

        //get TheMovieDB api key from resource file
        mTMDBKey = mBoss.getString(R.string.api_key_tmdb);

        //initialize buffers
        initBuffers();
    }

/**
 * void initBuffers() - initialize buffers to hold MovieModels, PosterItems and movie requests
 */
    private void initBuffers(){
        //initialize buffer to hold pending movie requests
        mRequestBuffer = new ArrayList<>();

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

        //buffer for PosterItems for "empty" movies, used when requested movie data fails or is empty
        mEmptyPosters = new ArrayList<>();

        //create "empty" poster items for mEmptyPosters buffer
        for(int i = 0; i < EMPTY_POSTERS_COUNT; i++){
            //create PosterItem
            PosterItem item = new PosterItem();

            //set Empty Id
            item.setTMDBId(EMPTY_ID);
            //set Empty title
            item.setTitle(mBoss.getActivityContext().getString(PosterHelper.NAME_ID_EMPTY));
            //set Poster path
            item.setPosterPath("");
            //set image
            item.setImage(null);

            //add PosterItem to buffer
            mEmptyPosters.add(item);
        }
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity context
 *      ArrayList<PosterItem> getPosters(int) - get poster item data of movies being requested
 *      MovieModel getMovie(int, int) - get a movie model from the buffer
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * Context getActivityContext() - get current Activity context
 * @return - activity context
 */
    public Context getActivityContext(){
        return mBoss.getActivityContext();
    }

/**
 * ArrayList<PosterItem> getPosters(int) - get Poster Items from buffer. If empty, will return
 * a list of "empty" Poster Items.
 * @param request - movie posters being requested
 * @return - list of PosterItem data being requested
 */
    public ArrayList<PosterItem> getPosters(int request){
        //return list of PosterItems saved in a Poster buffer
        return checkPosterBuffer(request);
    }

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
 *      void makePosterRequest(int) - start or buffer movie request task
 *      void startMovieRequest(int) - start AsyncTask worker to get movie data requested
 *      void workComplete(boolean) - called when AsyncTask has completed
 *      ArrayList<PosterItem> preparePosterItems(ArrayList<MovieModel>) - convert MovieModels to
 *          PosterItems used by View
 *      void saveMovieModels(int, ArrayList<MovieModel>) - save MovieModels to buffer
 *      void savePosterItems(int, ArrayList<PosterItem>) - save PosterItems to buffer
 *      void checkRequestBuffer() - check if there are any pending movie data requests
 */
/**************************************************************************************************/
/**
 * ArrayList<PosterItem> checkPosterBuffer(int) - checks poster item buffer. If the buffer is
 * empty, a request is made to get the movie data and populate the buffer.
 * @param request - poster buffer request
 * @return - poster item buffer, either data requested or "empty" buffer
 */
    private ArrayList<PosterItem> checkPosterBuffer(int request){
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

        //requested movie poster buffer was empty, make request to get data
        makePosterRequest(request);

        //return "empty" poster buffer
        return mEmptyPosters;
    }

/**
 * void makePosterRequest(int) - make a request to get poster data. If MovieWork is already
 * working, the request will be saved into a buffer.
 * @param request - poster buffer request
 */
    private void makePosterRequest(int request){
        //check if MovieWorker is already working on another request
        if(mWorking){
            //save request to buffer
            mRequestBuffer.add(request);
        }
        else{
            //save type of movies being requested
            mMovieRequest = request;

            //start working on the movie request
            startMovieRequest(request);
        }
    }

/**
 * void startMovieRequest(int) - start AsyncTask worker to get movie data requested.
 * @param request - movie data requested
 */
    private void startMovieRequest(int request){
        //initializes the AsyncTask worker
        mMovieWorker = new MovieWorker(this);

        //set working flag, AsyncTask is working in the background
        mWorking = true;

        //check type of movie request
        switch (request) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //start AsyncTask, get Popular movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_POPULAR, mTMDBKey));
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //start AsyncTask, get Top Rated movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_TOP_RATED, mTMDBKey));
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //start AsyncTask, get Now Playing movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_NOW_PLAYING, mTMDBKey));
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //start AsyncTask, get Upcoming movies from TheMovieDB api
                mMovieWorker.execute(mTMDBUri.getMovieList(TMDBUri.PATH_UPCOMING, mTMDBKey));
                break;
        }
    }

/**
 * void workComplete(Boolean) - called when MovieWorker completes its' work, updates buffers,
 * notifies Boss and, finally checks if there are more movie requests
 * @param result - returns boolean result of success of movie download
 */
    public void workComplete(Boolean result) {
        //work has finished
        mWorking = false;

        //get movie data from MovieWorker
        ArrayList<MovieModel> movieModel = mMovieWorker.getMovies();

        //check if results were successful
        if(result){
            //save movie models to buffer
            saveMovieModels(mMovieRequest, movieModel);

            //convert MovieModels to PosterItems, prepare for View consumption
            ArrayList<PosterItem> itemList = preparePosterItems(movieModel);

            //save poster items to buffer
            savePosterItems(mMovieRequest, itemList);

            //TODO - communication to Boss is to brittle here
            //message the Boss that the download of movie info is complete
            mBoss.updateSwipeActivity(itemList, mMovieRequest);

        }
        else{
            //TODO - need to handle event of a download failure
        }

        checkRequestBuffer();

    }

/**
 * ArrayList<PosterItem> preparePosterItems(int, ArrayList<MovieModel>) - convert MovieModel
 * data to PosterItem data.
 * @param models - MovieModel data
 * @return - array list of PosterItem
 */
    private ArrayList<PosterItem> preparePosterItems(ArrayList<MovieModel> models){
        Context ctx = mBoss.getActivityContext();

        //create an ArrayList to hold the list of poster items
        ArrayList<PosterItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = models.size();

        //loop through the data models
        for(int i = 0; i < count; i++){
            //create valid poster path uri for TheMovieDB api
            String posterPath = ctx.getString(R.string.tmdb_image_base_request) +
                    models.get(i).getPosterPath() + "?" +
                    ctx.getString(R.string.tmdb_query_api_key) + "=" +
                    ctx.getString(R.string.api_key_tmdb);

            //create poster item from movie model
            PosterItem item = new PosterItem(models.get(i).getId(),
                    models.get(i).getTitle(),
                    posterPath);

            //add item into array list
            itemList.add(item);
        }

        //return poster item list
        return itemList;
    }

/**
 * void saveMovieModels(int,ArrayList<MovieModel>) - save MovieModels to buffer
 * @param request - type of MovieModels to save
 * @param movieModels - MovieModels to be saved to buffer
 */
    private void saveMovieModels(int request, ArrayList<MovieModel> movieModels){
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
 * void savePosterItems(int,ArrayList<PosterItem>) - save PosterItems to buffer
 * @param request - type of PosterItems to save
 * @param posters - PosterItems to be saved to buffer
 */
    private void savePosterItems(int request, ArrayList<PosterItem> posters){
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

/**
 * void checkRequestBuffer() - checks the request buffer if there are any pending movie requests
 */
    private void checkRequestBuffer(){
        //check request buffer
        if(mRequestBuffer.size() > 0) {
            //there are pending requests, get request
            int request = mRequestBuffer.get(0);

            //remove request from buffer
            mRequestBuffer.remove(0);

            //make a poster request
            makePosterRequest(request);
        }
    }

/**************************************************************************************************/

}
