package me.makeachoice.movies.controller.butler.staff;

import android.util.Log;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.uri.TMDBUri;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.model.item.PosterItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * PosterStaff maintains the buffer objects holding PosterItem data
 */
public class PosterStaff {

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
 * PosterStaff - constructor, registers to Boss
 * data buffers.
 * @param boss - Boss class
 */
    public PosterStaff(Boss boss){
        Log.d("Start", "     PosterStaff - constructor");

        //builds TheMovieDB api uri strings
        mTMDBUri = new TMDBUri(boss);

        //get TheMovieDB api key from resource file
        mTMDBKey = boss.getString(R.string.api_key_tmdb);

        //initialize buffers
        initBuffers();
    }

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
 * Setters:
 *      - None -
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
 * @param request - poster buffer request
 * @return - poster item buffer, either data requested or "empty" buffer
 */
    public ArrayList<PosterItem> getPosters(int request){
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



/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods
 *      void prepareAndSavePosters(ArrayList<MovieModel>) - convert MovieModels to posterItems
 *      void savePosters(ArrayList<PosterItem>, int) - save PosterItems to buffer
 *      void onFinish() - nulls all of the data in the arrayList buffers
 */
/**************************************************************************************************/
/**
 * void preparePosters(int, ArrayList<MovieModel>) - convert MovieModel data to PosterItem data.
 * @param models - MovieModel data
 * @param movieType - type of movie model
 */
    public void prepareAndSavePosters(ArrayList<MovieModel> models, int movieType){

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

        //save poster item data to appropriate buffer
        savePosters(itemList, movieType);
    }

/**
 * void savePosters(ArrayList<PosterItem>, int) - save PosterItems to buffer
 * @param posters - PosterItems to be saved to buffer
 * @param movieType - type of PosterItems to save
 */
    public void savePosters(ArrayList<PosterItem> posters, int movieType){
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

/**
 * void onFinish() - nulls all of the data in the arrayList buffers
 */
    public void onFinish(){
        mPopularPosters.clear();
        mTopRatedPosters.clear();
        mNowPlayingPosters.clear();
        mUpcomingPosters.clear();
        mFavoritePosters.clear();
    }

/**************************************************************************************************/


}
