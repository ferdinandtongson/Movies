package me.makeachoice.movies.controller.butler;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.butler.worker.DetailWorker;
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
 * DetailButler handles the creation of a MovieItems to be consumed by the View. It takes data from
 * API calls or from the database and processes the data. It also buffers the data so it
 * does not need to make repeated calls to the API or database.
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
public class DetailButler extends MyButler{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *      DetailWorker mDetailWorker - AsyncTask class that makes API calls to get Movie details
 *
 *      HashMap<Integer, MovieItem> mMovieMap - movie buffer holding movie items
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;

    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mMovieWorker - AsyncTask class that makes API calls to get Movie details
    private DetailWorker mDetailWorker;

    //mMovieMap - movie buffer holding movie items
    private HashMap<Integer, MovieItem> mMovieMap;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterButler - constructor, registers to Boss, initialize URI builder, get API key and initialize
 * data buffers.
 * @param boss - Boss class
 */
    public DetailButler(Boss boss){
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

        //initialize buffer to hold movie items that have been requested
        mMovieMap = new HashMap<>();
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
 * MovieItem getMovie(MovieModel) - get the MovieItem of the request movie
 * @param model - movie model being requested
 * @return - movie item processed for consumption by View
 */
    public MovieItem getMovie(MovieModel model){
        //check if movie is in buffer
        return checkMovieBuffer(model);
    }

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
 * MovieItem checkMovieBuffer(MovieModel) - check if movie is in buffer. If not, a request is made
 * to get the movie data.
 * @param model - movie being requested
 * @return - movie item ready for View consumption
 */
    private MovieItem checkMovieBuffer(MovieModel model){
        //get movie id
        int id = model.getId();

        //check if movie item is in buffer
        if(mMovieMap.containsKey(id)){
            //movie in buffer, return movie item
            return mMovieMap.get(id);
        }
        else{
            //requested to get missing movie data
            makeDetailRequest(id);

            //prepare movie data we already have for View consumption
            return prepareMovieItem(model);
        }
    }

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
        mDetailWorker = new DetailWorker(this);

        //set working flag, AsyncTask is working in the background
        mWorking = true;

        mDetailWorker.execute(mTMDBUri.getMovieDetailAll(String.valueOf(id), mTMDBKey));
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
            mBoss.updateDetailActivity(prepareMovieDetails(mDetailWorker.getMovie()));
        }
        else{
            //TODO - need to handle event of a download failure
        }

        checkRequestBuffer();

    }

/**
 * MovieItem prepareMovieItem(MovieModel) - convert MovieModel to MovieItem
 * @param model - MovieModel data
 * @return - MovieItem
 */
    private MovieItem prepareMovieItem(MovieModel model){

        //create an empty MovieItem object
        MovieItem movieItem = new MovieItem();

        //populate MovieItem with MovieModel data
        movieItem.setId(model.getId());
        movieItem.setTitle(model.getTitle());
        movieItem.setOverview(model.getOverview());
        movieItem.setReleaseDate(model.getReleaseDate());

        movieItem.setOriginalTitle(model.getOriginalTitle());
        movieItem.setOriginalLanguage(model.getOriginalLanguage());

        movieItem.setPopularity(model.getPopularity());
        movieItem.setVoteCount(model.getVoteCount());
        movieItem.setVoteAverage(model.getVoteAverage());

        movieItem.setPosterPath(processImagePath(model.getPosterPath()));
        movieItem.setBackdropPath(processImagePath(model.getBackdropPath()));
        movieItem.setVideo(model.getVideo());

        movieItem.setAdult(model.getAdult());
        movieItem.setGenreIds(model.getGenreIds());

        prepareEmptyDetails(movieItem);

        //save movie item to buffer
        mMovieMap.put(model.getId(), movieItem);

        //return movie item
        return movieItem;
    }

    private void prepareEmptyDetails(MovieItem movie){
        String strEmpty = mBoss.getString(R.string.str_empty);


        ArrayList<GenreItem> emptyGenre = new ArrayList<>();
        GenreItem genreItem = new GenreItem();
        genreItem.setTMDBId(-1);
        genreItem.setName(strEmpty);

        emptyGenre.add(genreItem);
        movie.setGenres(emptyGenre);

        ArrayList<CastItem> emptyCast = new ArrayList<>();

        CastItem castItem = new CastItem();
        castItem.character = strEmpty;
        castItem.name = strEmpty;
        castItem.profilePath = strEmpty;

        emptyCast.add(castItem);
        movie.setCast(emptyCast);

        ArrayList<ReviewItem> emptyReview = new ArrayList<>();

        ReviewItem reviewItem = new ReviewItem();
        reviewItem.author = strEmpty;
        reviewItem.review = strEmpty;
        reviewItem.reviewPath = strEmpty;

        emptyReview.add(reviewItem);
        movie.setReviews(emptyReview);

        ArrayList<VideoItem> emptyVideo = new ArrayList<>();

        VideoItem videoItem = new VideoItem();
        videoItem.key = strEmpty;
        videoItem.name = strEmpty;
        videoItem.site = strEmpty;
        videoItem.size = -1;

        emptyVideo.add(videoItem);
        movie.setVideos(emptyVideo);

    }

    private MovieItem prepareMovieDetails(MovieModel model){
        MovieItem movieItem = mMovieMap.get(model.getId());

        movieItem.setImbdId(model.getIMBDId());
        movieItem.setHomepage(model.getHomepage());
        movieItem.setGenres(prepareGenreItems(model.getGenres()));
        movieItem.setCast(prepareCastItems(model.getCast()));
        movieItem.setReviews(prepareReviewItems(model.getReviews()));
        movieItem.setVideos(prepareVideoItems(model.getVideos()));

        return movieItem;
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
