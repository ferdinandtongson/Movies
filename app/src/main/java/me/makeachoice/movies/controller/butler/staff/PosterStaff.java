package me.makeachoice.movies.controller.butler.staff;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * PosterStaff maintains the buffer objects holding PosterItem data
 *
 * It uses other classes to assist in the upkeep of the buffers:
 *      TMDBUri - uri builder to make API calls to TheMovieDB
 *      PosterHelper - get static Poster resource ids
 */
public class PosterStaff{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *
 *      ArrayList<PosterItem> mEmptyPosters - Empty poster data for PosterFragment
 *      ArrayList<PosterItem> mPopularPosters - Popular poster data for PosterFragment
 *      ArrayList<PosterItem> mTopRatedPosters - Top Rated poster data for PosterFragment
 *      ArrayList<PosterItem> mNowPlayingPosters - Now Playing poster data for PosterFragment
 *      ArrayList<PosterItem> mUpcomingPosters - Upcoming poster data for PosterFragment
 *      ArrayList<PosterItem> mFavoritePosters - Favorite poster data for PosterFragment
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;
    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

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
 * PosterStaff - constructor, get TMDBUri builder and TMDBKey (TMDB = TheMovieDB) and initialize
 * data buffers.
 * @param boss - Boss class
 */
    public PosterStaff(Boss boss){
        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(boss);

        //get TheMovieDB api key from resource file
        mTMDBKey = boss.getString(R.string.api_key_tmdb);

        //initialize buffers
        initBuffers();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Initialization Methods:
 *      void initBuffers() - initialize poster Item arrayList buffers
 */
/**************************************************************************************************/
/**
 * void initBuffers() - initialize buffers to hold PosterItems
 */
    private void initBuffers(){
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
 *      PosterItem getPoster(int, int) - get a poster item from the buffer
 *      ArrayList<PosterItem> getPosters(int) - get requested poster item buffer
 */
/**************************************************************************************************/
/**
 * PosterItem getPoster(int, int) - get a poster item from the buffer, by type and index
 * @param posterType - poster item buffer type
 * @param position - index location of movie model in buffer
 * @return - poster item requested
 */
    public PosterItem getPoster(int posterType, int position){
        //check which movie buffer type
        switch (posterType) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //get poster item from Popular buffer
                return mPopularPosters.get(position);
            case PosterHelper.NAME_ID_TOP_RATED:
                //get poster item from Top Rated buffer
                return mTopRatedPosters.get(position);
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //get poster item from Now Playing buffer
                return mNowPlayingPosters.get(position);
            case PosterHelper.NAME_ID_UPCOMING:
                //get poster item from Upcoming buffer
                return mUpcomingPosters.get(position);
            case PosterHelper.NAME_ID_FAVORITE:
                //get poster item from Favorite buffer
                return mFavoritePosters.get(position);
            default:
                return null;
        }

    }

/**
 * ArrayList<PosterItem> getPosters(int) - get requested poster item buffer. If the buffer is
 * empty and empty arrayList is returned instead.
 * @param movieType - poster movie type requested
 * @return - poster item buffer, either data requested or "empty" buffer
 */
    public ArrayList<PosterItem> getPosters(int movieType){
        //check which type of posters being requested
        switch (movieType){
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //return Most Popular movie poster buffer
                return mPopularPosters;
            case PosterHelper.NAME_ID_TOP_RATED:
                //return Top Rated movie poster buffer
                return mTopRatedPosters;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //return Now Playing movie poster buffer
                return mNowPlayingPosters;
            case PosterHelper.NAME_ID_UPCOMING:
                //return Upcoming movie poster buffer
                return mUpcomingPosters;
            case PosterHelper.NAME_ID_FAVORITE:
                //return Favorite movie poster buffer
                return mFavoritePosters;
        }

        //return "empty" poster buffer
        return new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setPosters(ArrayList<PosterItem>, int) - save PosterItems to buffer
 */
/**************************************************************************************************/
/**
 * void setPosters(ArrayList<PosterItem>, int) - save PosterItems to buffer
 * @param posters - PosterItems to be saved to buffer
 * @param movieType - type of PosterItems to save
 */
    public void setPosters(ArrayList<PosterItem> posters, int movieType){
        //check type of PosterItems to save
        switch (movieType) {
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

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void preparePosters(ArrayList<MovieModel>) - convert MovieModels to posterItems
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * void preparePosters(ArrayList<MovieModel>) - convert MovieModel data to PosterItem data.
 * @param models - MovieModel data
 */
    public ArrayList<PosterItem> preparePosters(ArrayList<MovieModel> models){
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

        //return poster item list
        return itemList;
    }

/**
 * void onFinish() - nulls all of the data in the arrayList buffers
 */
    public void onFinish(){
        //clear and null Most Popular poster buffer
        mPopularPosters.clear();
        mPopularPosters = null;

        //clear and null Top Rated poster buffer
        mTopRatedPosters.clear();
        mTopRatedPosters = null;

        //clear and null Now Playing poster buffer
        mNowPlayingPosters.clear();
        mNowPlayingPosters = null;

        //clear and null Upcoming poster buffer
        mUpcomingPosters.clear();
        mUpcomingPosters = null;

        //clear and null Favorite poster buffer
        mFavoritePosters.clear();
        mFavoritePosters = null;
    }

/**************************************************************************************************/


}
