package me.makeachoice.movies.controller.modelside.butler;

import android.content.Context;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.modelside.uri.TMDBUri;
import me.makeachoice.movies.controller.modelside.worker.TMDBInfoWorker;
import me.makeachoice.movies.model.item.CastItem;
import me.makeachoice.movies.model.item.GenreItem;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.model.item.ReviewItem;
import me.makeachoice.movies.model.item.VideoItem;
import me.makeachoice.movies.model.response.tmdb.CastModel;
import me.makeachoice.movies.model.response.tmdb.GenreModel;
import me.makeachoice.movies.model.response.tmdb.MovieModel;
import me.makeachoice.movies.model.response.tmdb.ReviewModel;
import me.makeachoice.movies.model.response.tmdb.VideoModel;

/**
 * TMDBInfoButler handles API calls to TheMovieDB to get movie info data.
 *
 * It uses other classes to assist in making retrieving poster data from the net:
 *      Boss - Boss application
 *      TMDBUri - uri builder that builds TheMovieDB api uri string
 *      TMDBInfoWorker - AsyncTask class that makes API calls to get Movie details
 *      NetworkManager - check for network status
 *
 * Variables from MyButler:
 *      Boss mBoss
 *      Boolean mWorking
 *      ArrayList<Integer> mRequestBuffer
 *
 * Abstract Methods from MyButler:
 *      abstract public Context getActivityContext()
 *      abstract public void workComplete(Boolean)
 */
public class TMDBInfoButler extends MyButler{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *      TMDBInfoWorker mInfoWorker - AsyncTask class that makes API calls to get Movie details
 *
 *      HashMap<Integer, MovieItem> mMovieMap - movie buffer holding movie items
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;

    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mInfoWorker - AsyncTask class that makes API calls to get Movie details
    private TMDBInfoWorker mInfoWorker;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * TMDBInfoButler - constructor, registers to Boss, initialize URI builder, get API key and
 * initialize data buffers.
 * @param boss - Boss class
 */
    public TMDBInfoButler(Boss boss){
        //Application context
        mBoss = boss;

        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(mBoss);

        //flag to check if work is being done in the background
        mWorking = false;

        //get TheMovieDB api key from resource file
        mTMDBKey = mBoss.getString(R.string.api_key_tmdb);

        //initialize buffers
        initBuffers();
    }

/**
 * void initBuffers() - initialize buffers to hold MovieItems that have been requested
 */
    private void initBuffers(){
        //initialize buffer to hold pending movie requests
        mRequestBuffer = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      Context getActivityContext() - get current Activity context
 *      MovieItem getMovie(int, int) - get a movie model from the buffer
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
 * void requestMovie(MovieItem) - get the MovieItem of the requested movie
 * @param item - movie model being requested
 * @return - movie item processed for consumption by View
 */
    public void requestMovie(MovieItem item){
        mItem = item;
        //check if movie is in buffer
        //return checkMovieBuffer(item);
        makeDetailRequest(item.getTMDBId());
    }

    private MovieItem mItem;


/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Class methods
 *      MovieItem checkMovieBuffer(int) - check if movie is in buffer
 *      void makeDetailRequest(int) - start or buffer movie detail request task
 *      void startDetailRequest(int) - start AsyncTask worker to get movie data requested
 *      void workComplete(boolean) - called when AsyncTask has completed
 *      ArrayList<PosterItem> preparePosterItems(ArrayList<MovieModel>) - convert MovieModels to
 *          PosterItems used by View
 *      void saveMovieModels(int, ArrayList<MovieModel>) - save MovieModels to buffer
 *      void savePosterItems(int, ArrayList<PosterItem>) - save PosterItems to buffer
 *      void checkRequestBuffer() - check if there are any pending movie data requests
 */
/**************************************************************************************************/
/**
 * void makeMovieDetailRequest(int) - make a request to get details of a movie. If the MovieWorker
 * is working, save movie id into request buffer. If not working, start request
 * @param id - id number of Movie from TheMovieDB
 */
    private void makeDetailRequest(int id){
        //check if MovieWorker is already working on another request
        if(mWorking){
            //save request to buffer
            mRequestBuffer.add(id);
        }
        else{
            //start working on the movie detail request
            startDetailRequest(id);
        }
    }

/**
 * void startMovieRequest(int) - start AsyncTask worker to get details of movie being requested.
 * @param id - id number of movie from TheMovieDB
 */
    private void startDetailRequest(int id){
        //initializes the AsyncTask worker
        mInfoWorker = new TMDBInfoWorker(this);

        //set working flag, AsyncTask is working in the background
        mWorking = true;

        mInfoWorker.executeOnExecutor(mBoss.getExecutor(),
                mTMDBUri.getMovieDetailAll(String.valueOf(id), mTMDBKey));
    }

/**
 * void workComplete(Boolean) - called when MovieWorker completes its' work, updates buffers,
 * notifies Boss and, finally checks if there are more movie requests
 * @param result - returns boolean result of success of movie download
 */
    public void workComplete(Boolean result) {
        //work has finished
        mWorking = false;

        //check if results were successful
        if(result){
            mItem = prepareMovieDetails(mInfoWorker.getMovie(), mItem);
            mBoss.updateDetailActivity(mItem);
        }
        else{
            //TODO - need to handle event of a download failure
        }

        checkRequestBuffer();

    }

    private MovieItem prepareMovieDetails(MovieModel model, MovieItem item){

        item.setIMDBId(model.getIMDBId());
        item.setHomepage(model.getHomepage());
        item.setGenres(prepareGenreItems(model.getGenres()));
        item.setCast(prepareCastItems(model.getCast()));
        item.setReviews(prepareReviewItems(model.getReviews()));
        item.setVideos(prepareVideoItems(model.getVideos()));

        return item;
    }

    private ArrayList<GenreItem> prepareGenreItems(ArrayList<GenreModel> models){
        ArrayList<GenreItem> genres = new ArrayList<>();

        int count = models.size();
        for(int i = 0; i < count; i++){
            GenreModel mod = models.get(i);

            GenreItem item = new GenreItem();
            item.setTMDBId(mod.id);
            item.setName(mod.name);

            genres.add(item);
        }

        return genres;
    }

    private ArrayList<CastItem> prepareCastItems(ArrayList<CastModel> models){
        ArrayList<CastItem> cast = new ArrayList<>();

        int count = models.size();
        for(int i = 0; i < count; i++){
            CastModel mod = models.get(i);


            CastItem item = new CastItem();
            item.character = mod.character;
            item.name = mod.name;
            item.profilePath = processImagePath(mod.profilePath);

            cast.add(item);
        }

        return cast;
    }

    private ArrayList<ReviewItem> prepareReviewItems(ArrayList<ReviewModel> models){
        ArrayList<ReviewItem> reviews = new ArrayList<>();

        int count = models.size();
        for(int i = 0; i < count; i++){
            ReviewModel mod = models.get(i);

            ReviewItem item = new ReviewItem();
            item.author = mod.author;
            item.review = mod.content;
            item.reviewPath = mod.url;

            reviews.add(item);
        }

        return reviews;
    }

    private ArrayList<VideoItem> prepareVideoItems(ArrayList<VideoModel> models){
        ArrayList<VideoItem> reviews = new ArrayList<>();

        int count = models.size();
        for(int i = 0; i < count; i++){
            VideoModel mod = models.get(i);

            VideoItem item = new VideoItem();
            item.key = mod.key;
            item.site = mod.site;
            item.name = mod.name;
            item.size = mod.size;

            if(item.site.equalsIgnoreCase(mBoss.getString(R.string.tmdb_youtube))){

                item.thumbnailPath = mTMDBUri.getYouTubeThumbnailPath(item.key);
                item.videoPath = mTMDBUri.getYouTubeVideoPath(item.key);
            }

            reviews.add(item);
        }

        return reviews;
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

            //make a movie request
            makeDetailRequest(request);
        }
    }

    private String processImagePath(String path){

        //create valid poster path uri for TheMovieDB api
        return mTMDBUri.getImagePath(path, mTMDBKey);
    }

/**************************************************************************************************/

}
