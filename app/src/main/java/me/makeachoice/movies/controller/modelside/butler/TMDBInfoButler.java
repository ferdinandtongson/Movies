package me.makeachoice.movies.controller.modelside.butler;


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
 */
public class TMDBInfoButler extends MyButler implements TMDBInfoWorker.Bridge{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;

    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mMovie - movie item requesting further movie info about the movie
    private MovieItem mMovie;

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

        //initialize buffer to hold pending movie requests
        mRequestBuffer = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Movie Request related methods:
 *      void requestMovieInfo(MovieItem) - get info on the requested movie
 *      void makeInfoRequest(int) - make a request to get details of a movie
 *      void startInfoRequest(int) - start AsyncTask worker to get info of movie being requested
 *      void workComplete(boolean) - called when AsyncTask has completed
 *      void checkRequestBuffer() - check if there are any pending requests
 */
/**************************************************************************************************/

/**
 * void requestMovieInfo(MovieItem) - get info on the requested movie
 * @param movie - movie item requesting more info
 */
    public void requestMovieInfo(MovieItem movie){
        //save movie item to buffer
        mMovie = movie;

        //make request for info
        makeInfoRequest(movie.getTMDBId());
    }

 /**
 * void makeInfoRequest(int) - make a request to get details of a movie. If the MovieWorker
 * is working, save movie id into request buffer. If not working, start request
 * @param id - id number of Movie from TheMovieDB
 */
    private void makeInfoRequest(int id){
        //check if MovieWorker is already working on another request
        if(mWorking){
            //save request to buffer
            mRequestBuffer.add(id);
        }
        else{
            //start working on the movie info request
            startInfoRequest(id);
        }
    }

/**
 * void startInfoRequest(int) - start AsyncTask worker to get info of movie being requested.
 * @param id - id number of movie from TheMovieDB
 */
    private void startInfoRequest(int id){
        //initializes the AsyncTask worker
        TMDBInfoWorker infoWorker = new TMDBInfoWorker(this);

        //set working flag, AsyncTask is working in the background
        mWorking = true;

        //start AsyncTask in background thread
        infoWorker.executeOnExecutor(mBoss.getExecutor(),
                mTMDBUri.getMovieDetailAll(String.valueOf(id), mTMDBKey));
    }

/**
 * void workComplete(Boolean) - called when AsyncTask has completed, prepares the data model to
 * movie item for consumption by the View then notifies boss to update the Activity
 * @param model - movie mode contain movie info data
 */
    public void movieInfoDownloaded(MovieModel model) {
        //work has finished
        mWorking = false;

        //prepare Movie model data for consumption by the View, add to Movie item object
        mMovie = prepareMovieItem(model, mMovie);
        //notify Boss that the movie info request is complete
        mBoss.infoRequestComplete(mMovie);

        //check Request buffer
        checkRequestBuffer();
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
            startInfoRequest(request);
        }
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Prepare Item methods
 *      MovieItem prepareMovieItem(MovieModel,MovieItem) - convert MovieModels to MovieItem
 *      ArrayList<GenreItem> prepareGenreItems(ArrayList<GenreModel>) - convert Genre Model to Genre
 *          Item data
 *      ArrayList<CastItem> prepareCastItems(ArrayList<CastModel>) - convert Cast Model to Cast Item
 *          data
 *      ArrayList<ReviewItem> prepareReviewItems(ArrayList<ReviewModel>) - convert Review Model to
 *          Review Item data
 *      ArrayList<VideoItem> prepareVideoItems(ArrayList<VideoModel>) - convert Video Model to Video
 *          data
 */
/**************************************************************************************************/
/**
 * MovieItem prepareMovieInfo(MovieModel,MovieItem) - convert MovieModels to MovieItem
 * @param model - movie model downloaded from TMDB API call
 * @param item - movie item to be updated
 * @return - movie item with updated movie info
 */
    private MovieItem prepareMovieItem(MovieModel model, MovieItem item){

        //get InternetMovieDB id of movie
        item.setIMDBId(model.getIMDBId());
        //get homepage url of movie
        item.setHomepage(model.getHomepage());

        //get array list of genre data related to movie
        item.setGenres(prepareGenreItems(model.getGenres()));
        //get array list of cast member data
        item.setCast(prepareCastItems(model.getCast()));
        //get array list of movie review data
        item.setReviews(prepareReviewItems(model.getReviews()));
        //get array list of movie trailer video data
        item.setVideos(prepareVideoItems(model.getVideos()));

        return item;
    }

/**
 * ArrayList<GenreItem> prepareGenreItems(ArrayList<GenreModel>) - convert Genre Model to Genre
 * Item data
 * @param models - list of genre model data
 * @return - list of genre item data ready for View consumption
 */
    private ArrayList<GenreItem> prepareGenreItems(ArrayList<GenreModel> models){
        //create array list buffer for genre items
        ArrayList<GenreItem> genres = new ArrayList<>();

        //get number of genre models to process
        int count = models.size();

        //loop through models
        for(int i = 0; i < count; i++){
            //get model from model list
            GenreModel mod = models.get(i);

            //create genre item object
            GenreItem item = new GenreItem();
            //set TMDB genre id number
            item.setTMDBId(mod.id);
            //set name of genre
            item.setName(mod.name);

            //add item to list
            genres.add(item);
        }

        //return list of genre items
        return genres;
    }

/**
 * ArrayList<CastItem> prepareCastItems(ArrayList<CastModel>) - convert Cast Model to Cast Item
 * data
 * @param models - list of cast model data
 * @return - list of cast item data ready for View consumption
 */
    private ArrayList<CastItem> prepareCastItems(ArrayList<CastModel> models){
        //create array list buffer for cast item data
        ArrayList<CastItem> cast = new ArrayList<>();

        //get number of cast models to process
        int count = models.size();

        //loop through models
        for(int i = 0; i < count; i++){
            //get model from model list
            CastModel mod = models.get(i);

            //create cast item object
            CastItem item = new CastItem();
            //set name of character in the movie
            item.character = mod.character;
            //set name of actor who played the character in the movie
            item.name = mod.name;
            //set full portfolio picture uri path of the actor
            item.profilePath = mTMDBUri.getImagePath(mod.profilePath, mTMDBKey);

            //add cast item to list
            cast.add(item);
        }

        //return list of cast item data
        return cast;
    }

/**
 * ArrayList<ReviewItem> prepareReviewItems(ArrayList<ReviewModel>) - convert Review Model to
 * Review Item data
 * @param models - list of review model data
 * @return - list of review item data ready for View consumption
 */
    private ArrayList<ReviewItem> prepareReviewItems(ArrayList<ReviewModel> models){
        //create array list buffer for review item data
        ArrayList<ReviewItem> reviews = new ArrayList<>();

        //get number of review models to process
        int count = models.size();

        //loop through models
        for(int i = 0; i < count; i++){
            //get model from model list
            ReviewModel mod = models.get(i);

            //create review item object
            ReviewItem item = new ReviewItem();
            //set name of author who created the movie review
            item.author = mod.author;
            //set review of the movie
            item.review = mod.content;
            //set the url path to the original review
            item.reviewPath = mod.url;

            //add review item to list
            reviews.add(item);
        }

        //return list of review item data
        return reviews;
    }

/**
 * ArrayList<VideoItem> prepareVideoItems(ArrayList<VideoModel>) - convert Video Model to Video
 * data
 * @param models - list of video model data
 * @return - list of video item data ready for View consumption
 */
    private ArrayList<VideoItem> prepareVideoItems(ArrayList<VideoModel> models){
        //create array list buffer for video item data
        ArrayList<VideoItem> videos = new ArrayList<>();

        //get number of video models to process
        int count = models.size();

        //loop through models
        for(int i = 0; i < count; i++){
            //get model from model list
            VideoModel mod = models.get(i);

            //create video item object
            VideoItem item = new VideoItem();
            //set video key to access video
            item.key = mod.key;
            //set website where the video is located; normally YouTube
            item.site = mod.site;
            //set name of video trailer
            item.name = mod.name;
            //set size of video trailer
            item.size = mod.size;

            //check if YouTube is website source
            if(item.site.equalsIgnoreCase(mBoss.getString(R.string.tmdb_youtube))){
                //set video thumbnail image of trailer
                item.thumbnailPath = mTMDBUri.getYouTubeThumbnailPath(item.key);
                //set video url path of trailer
                item.videoPath = mTMDBUri.getYouTubeVideoPath(item.key);
            }

            //add videos item to list
            videos.add(item);
        }

        //return list video item data
        return videos;
    }

/**************************************************************************************************/

}
