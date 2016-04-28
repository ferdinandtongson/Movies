package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.butler.worker.MovieWorker;
import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * MovieButler handles the creation of the Movie model, taking resources from a flat file,
 * database or webservice and converting it into a model for consumption by the controller
 */
public class MovieButler extends MyButler{

/**************************************************************************************************/

    //mApiKey - the key used to access for TheMovieDB api
    private String mApiKey;

    //mUri - class that builds TMDB api uri strings
    private TMDBUri mUri;

    //mMovieWorker - AsyncTask class that connect to the internet to get Movie details
    MovieWorker mMovieWorker;

    //mMovieModel - a ListArray of data taken from a JSON response
    //ArrayList<MovieModel> mMovieModel;

    //mWorking - boolean value to check if a Worker is working in the background
    Boolean mWorking;

/**************************************************************************************************/

    public MovieButler(Boss boss){
        //Application context
        mBoss = boss;

        //builds TMDB api uri strings
        mUri = new TMDBUri(this);

        //flag to check if work is being done in the background
        mWorking = false;

        //get TheMovieDB api key from resource file
        mApiKey = mBoss.getString(R.string.api_key_tmdb);

        initPosterItems();
    }

    ArrayList<PosterItem> mEmptyPosters;
    ArrayList<PosterItem> mPopularPosters;
    ArrayList<PosterItem> mTopRatedPosters;
    ArrayList<PosterItem> mNowPlayingPosters;
    ArrayList<PosterItem> mUpcomingPosters;
    ArrayList<PosterItem> mFavoritePosters;

    ArrayList<MovieModel> mPopularModel;
    ArrayList<MovieModel> mTopRatedModel;
    ArrayList<MovieModel> mNowPlayingModel;
    ArrayList<MovieModel> mUpcomingModel;
    ArrayList<MovieModel> mFavoriteModel;

    private void initPosterItems(){
        mPopularModel = new ArrayList<>();
        mTopRatedModel = new ArrayList<>();
        mNowPlayingModel = new ArrayList<>();
        mUpcomingModel = new ArrayList<>();
        mFavoriteModel = new ArrayList<>();


        mPopularPosters = new ArrayList<>();
        mTopRatedPosters = new ArrayList<>();
        mNowPlayingPosters = new ArrayList<>();
        mUpcomingPosters = new ArrayList<>();
        mFavoritePosters = new ArrayList<>();

        mEmptyPosters = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            PosterItem item = new PosterItem();
            item.setTitle(mBoss.getActivityContext().getString(PosterHelper.NAME_ID_EMPTY));
            item.setPosterPath("");
            item.setImage(null);

            mEmptyPosters.add(item);
        }
    }


    public Context getActivityContext(){
        return mBoss.getActivityContext();
    }

/**************************************************************************************************/
    private int mMovieRequest;

/**
 * void workComplete(Boolean) - when MovieWorker completes its' work, calls this method
 * @param result - returns boolean result of success of movie download
 */
    public void workComplete(Boolean result) {
        //work has finished
        mWorking = false;

        //get modeled data from the JSON response processed by the worker
        ArrayList<MovieModel> movieModel = mMovieWorker.getMovies();

        if(result){
            preparePosterItems(mBoss.getActivityContext(), mMovieRequest, movieModel);

            ArrayList<PosterItem> itemList;
            switch (mMovieRequest) {
                case PosterHelper.NAME_ID_MOST_POPULAR:
                    mPopularModel = new ArrayList<>(movieModel);
                    itemList = mPopularPosters;
                    break;
                case PosterHelper.NAME_ID_TOP_RATED:
                    mTopRatedModel = new ArrayList<>(movieModel);
                    itemList = mTopRatedPosters;
                    break;
                case PosterHelper.NAME_ID_NOW_PLAYING:
                    mNowPlayingModel = new ArrayList<>(movieModel);
                    itemList = mNowPlayingPosters;
                    break;
                case PosterHelper.NAME_ID_UPCOMING:
                    mUpcomingModel = new ArrayList<>(movieModel);
                    itemList = mUpcomingPosters;
                    break;
                case PosterHelper.NAME_ID_FAVORITE:
                    itemList = mEmptyPosters;
                    break;
                default:
                    itemList = mEmptyPosters;
                    break;
            }

            //message the Boss that the download of movie info is complete
            mBoss.updateSwipeActivity(itemList, mMovieRequest);
            //mBoss.updateMainActivity();
            checkBufferedRequest();
        }
        else{
            //TODO - need to handle event of a download failure
        }

    }



    private void checkBufferedRequest(){
        Log.d("Movies", "MovieButler.checkBufferedRequest: " + mBufferRequest.size());
        if (mBufferRequest.size() > 0) {
            Log.d("Movies", "     have buffered request");
            int request = mBufferRequest.get(0);
            mBufferRequest.remove(0);
            getPosters(request);
        }
        Log.d("Movies", "          new buffer: " + mBufferRequest.size());
    }

    private void preparePosterItems(Context ctx, int request,
                                                     ArrayList<MovieModel> models){

        //create an ArrayList to hold the list items
        ArrayList<PosterItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = models.size();

        //loop through the data models
        for(int i = 0; i < count; i++){
            String posterPath = ctx.getString(R.string.tmdb_image_base_request) +
                    models.get(i).getPosterPath() + "?" +
                    ctx.getString(R.string.tmdb_query_api_key) + "=" +
                    ctx.getString(R.string.api_key_tmdb);
            //create poster item from model
            PosterItem item = new PosterItem(models.get(i).getTitle(), posterPath);

            //add item into array list
            itemList.add(item);
        }

        switch (request) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                mPopularPosters = itemList;
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                mTopRatedPosters = itemList;
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                mNowPlayingPosters = itemList;
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                mUpcomingPosters = itemList;
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                mFavoritePosters = itemList;
                break;
        }
    }


    private ArrayList<Integer> mBufferRequest = new ArrayList<>();

/**
 * MovieJSON getModel() - allows access to the data received
 * @return - MovieJSON, an array of objects containing movie details
 */
    public ArrayList<PosterItem> getPosters(int request){
        Log.d("Movies", "MovieButler.getPosters: " + request);
        if(mWorking){
            Log.d("Movies", "     buffer request");
            mBufferRequest.add(request);
            return mEmptyPosters;
        }
        else{
            Log.d("Movies", "     execute request!!!!");
            mMovieRequest = request;
        }

        //initializes the AsyncTask worker
        mMovieWorker = new MovieWorker(this);

        //worker executes the url request
        if(request == PosterHelper.NAME_ID_MOST_POPULAR){
            if(mPopularPosters.size() == 0){
                //most popular movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_POPULAR, mApiKey));
                mWorking = true;
            }
            else{
                return mPopularPosters;
            }
        }
        else if(request == PosterHelper.NAME_ID_TOP_RATED){
            if(mTopRatedPosters.size() == 0){
                //highest rated movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_TOP_RATED, mApiKey));
                mWorking = true;
            }
            else{
                return mTopRatedPosters;
            }
        }
        else if(request == PosterHelper.NAME_ID_NOW_PLAYING){
            if(mNowPlayingPosters.size() == 0){
                //now playing movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_NOW_PLAYING, mApiKey));
                mWorking = true;
            }
            else{
                return mNowPlayingPosters;
            }
        }
        else if(request == PosterHelper.NAME_ID_UPCOMING){
            if(mUpcomingPosters.size() == 0){
                //upcoming movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_UPCOMING, mApiKey));
                mWorking = true;
            }
            else{
                return mUpcomingPosters;
            }
        }

        return mEmptyPosters;
    }

    public MovieModel getMovie(int movieType, int position){
        //TODO - need to prepare MovieItem data instead of passing MovieModel
        MovieModel movie;
        switch (movieType) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                movie = mPopularModel.get(position);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                movie = mTopRatedModel.get(position);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                movie = mNowPlayingModel.get(position);
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                movie = mUpcomingModel.get(position);
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                movie = null;
                break;
            default:
                movie = null;
        }

        String posterPath = mBoss.getActivityContext().getString(R.string.tmdb_image_base_request) +
                movie.getPosterPath() + "?" +
                mBoss.getActivityContext().getString(R.string.tmdb_query_api_key) + "=" +
                mBoss.getActivityContext().getString(R.string.api_key_tmdb);


        return movie;

    }


/**************************************************************************************************/

}
