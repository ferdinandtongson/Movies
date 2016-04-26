package me.makeachoice.movies.controller.butler;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.butler.worker.MovieWorker;
import me.makeachoice.movies.R;
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

    //MOVIE_REQUEST_MOST_POPULAR - value used when a list of most popular movies are requested
    public static int MOVIE_REQUEST_MOST_POPULAR = 0;
    //MOVIE_REQUEST_HIGHEST_RATED - value used when a list of highest rated movies are requested
    public static int MOVIE_REQUEST_TOP_RATED = 1;

    public final static int MOVIE_REQUEST_NOW_PLAYING = 2;
    public final static int MOVIE_REQUEST_UPCOMING = 3;

    //mMovieModel - a ListArray of data taken from a JSON response
    ArrayList<MovieModel> mMovieModel;

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

    private void initPosterItems(){
        mPopularPosters = new ArrayList<>();
        mTopRatedPosters = new ArrayList<>();
        mNowPlayingPosters = new ArrayList<>();
        mUpcomingPosters = new ArrayList<>();
        mFavoritePosters = new ArrayList<>();

        mEmptyPosters = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            PosterItem item = new PosterItem();
            item.setTitle("Empty");
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
        //get modeled data from the JSON response processed by the worker
        mMovieModel = mMovieWorker.getMovies();

        if(result){
            preparePosterItems(mBoss.getActivityContext(), mMovieRequest, mMovieModel);
            //message the Boss that the download of movie info is complete
            mBoss.updateMainActivity();
        }
        else{
            //TODO - need to handle event of a download failure
        }

        //work has finished
        mWorking = false;
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

        //worker executes the url request
        if(request == MOVIE_REQUEST_MOST_POPULAR){
            mPopularPosters = itemList;
        }
        else if(request == MOVIE_REQUEST_TOP_RATED){
            mTopRatedPosters = itemList;
        }
        else if(request == MOVIE_REQUEST_NOW_PLAYING){
            mNowPlayingPosters = itemList;
        }
        else if(request == MOVIE_REQUEST_UPCOMING){
            mUpcomingPosters = itemList;
        }
    }



/**
 * MovieJSON getModel() - allows access to the data received
 * @return - MovieJSON, an array of objects containing movie details
 */
    public ArrayList<PosterItem> getPosters(int request){
        Log.d("Movies", "MovieButler.getPosters: " + request);
        mMovieRequest = request;
        if(mWorking){
            mMovieWorker.cancel(true);
        }

        //initializes the AsyncTask worker
        mMovieWorker = new MovieWorker(this);

        //worker executes the url request
        if(request == MOVIE_REQUEST_MOST_POPULAR){
            if(mPopularPosters.size() == 0){
                //most popular movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_POPULAR, mApiKey));
            }
            else{
                return mPopularPosters;
            }
        }
        else if(request == MOVIE_REQUEST_TOP_RATED){
            if(mTopRatedPosters.size() == 0){
                //highest rated movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_TOP_RATED, mApiKey));
            }
            else{
                return mTopRatedPosters;
            }
        }
        else if(request == MOVIE_REQUEST_NOW_PLAYING){
            if(mNowPlayingPosters.size() == 0){
                //now playing movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_NOW_PLAYING, mApiKey));
            }
            else{
                return mNowPlayingPosters;
            }
        }
        else if(request == MOVIE_REQUEST_UPCOMING){
            if(mUpcomingPosters.size() == 0){
                //upcoming movies are requested
                mMovieWorker.execute(mUri.getMovieList(TMDBUri.PATH_UPCOMING, mApiKey));
            }
            else{
                return mUpcomingPosters;
            }
        }

        return mEmptyPosters;
    }


/**************************************************************************************************/

}
