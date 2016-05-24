package me.makeachoice.movies.controller.modelside.staff;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.modelside.uri.TMDBUri;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.model.response.tmdb.MovieModel;

/**
 * MovieStaff maintains the buffer objects holding MovieItem data
 *
 * It uses other classes to assist in the upkeep of the buffers:
 *      PosterHelper - get static Poster resource ids
 */
public class MovieStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mTMDBKey - the key used to access TheMovieDB api
 *      TMDBUri mTMDBUri - uri builder that builds TheMovieDB api uri string
 *
 *      ArrayList<MovieItem> mPopularMovies - Popular Movie item data from TMDB
 *      ArrayList<MovieItem> mTopRatedMovies - Top Rated Movie item data from TMDB
 *      ArrayList<MovieItem> mNowPlayingMovies - Now Playing Movie item data from TMDB
 *      ArrayList<MovieItem> mUpcomingMovies - Upcoming Movie item data from TMDB
 *      ArrayList<MovieItem> mFavoriteMovies - Favorite Movie item data selected by user
 */
/**************************************************************************************************/

    //mTMDBKey - the key used to access TheMovieDB api
    private String mTMDBKey;
    //mUri - class that builds TheMovieDB api uri strings
    private TMDBUri mTMDBUri;

    //mPopularMovies - array list of movie item data of Popular Movies from TMDB
    private ArrayList<MovieItem> mPopularMovies;
    //mTopRatedMovies - array list of movie item data of Top Rated Movies from TMDB
    private ArrayList<MovieItem> mTopRatedMovies;
    //mNowPlayingMovies - array list of movie item data of Now Playing Movies from TMDB
    private ArrayList<MovieItem> mNowPlayingMovies;
    //mUpcomingMovies - array list of movie item data of Upcoming Movies from TMDB
    private ArrayList<MovieItem> mUpcomingMovies;
    //mFavoriteMovies - Favorite Movie item data selected by user
    private ArrayList<MovieItem> mFavoriteMovies;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MovieStaff - constructor, get TMDBUri builder and TMDBKey (TMDB = TheMovieDB) and initialize
 * data buffers.
 * @param boss - Boss class
 */
    public MovieStaff(Boss boss){
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
 *      void initBuffers() - initialize movie item arrayList buffers
 */
/**************************************************************************************************/
/**
 * void initBuffers() - initialize buffers to hold MovieItem data
 */
    private void initBuffers(){
        //buffer for Most Popular MovieItems
        mPopularMovies = new ArrayList<>();
        //buffer for Top Rated MovieItems
        mTopRatedMovies = new ArrayList<>();
        //buffer for Now Playing MovieItems
        mNowPlayingMovies = new ArrayList<>();
        //buffer for Upcoming MovieItems
        mUpcomingMovies = new ArrayList<>();
        //buffer for Favorite MovieItems
        mFavoriteMovies = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      MovieItem getMovie(int, int) - get a movie item from the buffer
 *      ArrayList<MovieModel> getMovieModels(int) - get the list of movie models requested
 */
/**************************************************************************************************/
/**
 * MovieItem getMovie(int, int) - get a movie item from the buffer, by type and index
 * @param movieType - movie type buffer being request
 * @param position - index location of movie item in buffer
 * @return - movie item
 */
    public MovieItem getMovie(int movieType, int position){
        //check which movie buffer type
        switch (movieType) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //get movie item from Popular buffer
                return mPopularMovies.get(position);
            case PosterHelper.NAME_ID_TOP_RATED:
                //get movie item from Top Rated buffer
                return mTopRatedMovies.get(position);
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //get movie item from Now Playing buffer
                return mNowPlayingMovies.get(position);
            case PosterHelper.NAME_ID_UPCOMING:
                //get movie item from Upcoming buffer
                return mUpcomingMovies.get(position);
            case PosterHelper.NAME_ID_FAVORITE:
                //get movie item from Favorite buffer;
                return mFavoriteMovies.get(position);
        }
        //invalid request, return null
        return null;
    }

/**
 * ArrayList<MovieItem> getMovies(int) - get the list of movie items requested
 * @param movieType - type of MovieItem
 * @return - list of movie items requested
 */
    public ArrayList<MovieItem> getMovies(int movieType){
        //check type of MovieItems to get
        switch (movieType) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //return Most Popular movie items
                return mPopularMovies;
            case PosterHelper.NAME_ID_TOP_RATED:
                //return Top Rated movie items
                return mTopRatedMovies;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //return Now Playing movie items
                return mNowPlayingMovies;
            case PosterHelper.NAME_ID_UPCOMING:
                //return Upcoming movie items
                return mUpcomingMovies;
            case PosterHelper.NAME_ID_FAVORITE:
                //return Favorite movie items
                return mFavoriteMovies;
        }

        //invalid request, return empty list
        return new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setMovies(ArrayList<MovieItem>, int) - set MovieItems to buffer
 */
/**************************************************************************************************/
/**
 * void setMovies(ArrayList<MovieItem>, int) - set MovieItems to buffer
 * @param movieType - type of MovieItems to save
 * @param movies - MovieItems to be saved to buffer
 */
    public void setMovies(ArrayList<MovieItem> movies, int movieType){
        //check type of MovieModels to save
        switch (movieType) {
            case PosterHelper.NAME_ID_MOST_POPULAR:
                //save movie items to Popular buffer
                mPopularMovies = new ArrayList<>(movies);
                break;
            case PosterHelper.NAME_ID_TOP_RATED:
                //save movie items to Top Rated buffer
                mTopRatedMovies = new ArrayList<>(movies);
                break;
            case PosterHelper.NAME_ID_NOW_PLAYING:
                //save movie items to Now Playing buffer
                mNowPlayingMovies = new ArrayList<>(movies);
                break;
            case PosterHelper.NAME_ID_UPCOMING:
                //save movie items to Upcoming buffer
                mUpcomingMovies = new ArrayList<>(movies);
                break;
            case PosterHelper.NAME_ID_FAVORITE:
                //save movie items to Favorite buffer
                mFavoriteMovies = new ArrayList<>(movies);
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
 * void prepareMovies(ArrayList<MovieModel>) - convert MovieModel data to MovieItem data.
 * @param models - MovieModel data
 */
    public ArrayList<MovieItem> prepareMovies(ArrayList<MovieModel> models){
        //create an ArrayList to hold the list of movie items
        ArrayList<MovieItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = models.size();

        //loop through the data models
        for(int i = 0; i < count; i++){
            //get MovieModel
            MovieModel mod = models.get(i);

            //create movie item from movie model
            MovieItem item = new MovieItem();
            item.setTMDBId(mod.getId());
            item.setTitle(mod.getTitle());
            item.setOverview(mod.getOverview());
            item.setReleaseDate(mod.getReleaseDate());
            item.setIMDBId(mod.getIMDBId());

            item.setOriginalTitle(mod.getOriginalTitle());
            item.setOriginalLanguage(mod.getOriginalLanguage());

            item.setPopularity(mod.getPopularity());
            item.setVoteCount(mod.getVoteCount());
            item.setVoteAverage(mod.getVoteAverage());

            //get uri poster path
            String posterPath = mTMDBUri.getImagePath(mod.getPosterPath(), mTMDBKey);

            item.setPosterPath(posterPath);
            item.setPoster(null);

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
        //clear and null Most Popular buffer
        mPopularMovies.clear();
        mPopularMovies = null;

        //clear and null Top Rated buffer
        mTopRatedMovies.clear();
        mTopRatedMovies = null;

        //clear and null Now Playing buffer
        mNowPlayingMovies.clear();
        mNowPlayingMovies = null;

        //clear and null Upcoming buffer
        mUpcomingMovies.clear();
        mUpcomingMovies = null;

        //clear and null Favorite buffer
        mFavoriteMovies.clear();
        mFavoriteMovies = null;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Favorites:
 *      void addFavorite(MovieItem) - adds movie to Favorite Movie list
 *      void removeFavorite(MovieItem) - removes movie from Favorite Movie list
 *      boolean alreadyFavorite(MovieItem) - check if Movie is already in the Favorite Movie list
 */
/**************************************************************************************************/
/**
 * void addFavorite(MovieItem) - adds movie to Favorite Movie list
 * @param movie - movie to be added to list
 */
    public void addFavorite(MovieItem movie){
        mFavoriteMovies.add(movie);
    }

/**
 * void removeFavorite(MovieItem) - removes movie from Favorite Movie list
 * @param movie - movie to be removed from list
 */
    public void removeFavorite(MovieItem movie){
        mFavoriteMovies.remove(movie);
    }

/**
 * boolean alreadyFavorite(MovieItem) - check if Movie is already in the Favorite Movie list
 * @param movie - check if movie is already in the list
 * @return - true if already in list, false otherwise
 */
    public boolean alreadyFavorite(MovieItem movie){
        return mFavoriteMovies.contains(movie);
    }

/**************************************************************************************************/

}