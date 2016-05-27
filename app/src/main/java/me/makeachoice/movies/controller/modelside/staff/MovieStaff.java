package me.makeachoice.movies.controller.modelside.staff;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.modelside.uri.TMDBUri;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
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
    //mTMDBYouTube - YouTube string
    private String mTMDYouTube;
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

        //get TheMovieDB YouTube string
        mTMDYouTube = boss.getString(R.string.tmdb_youtube);

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
 *      MovieItem getEmptyMovie() - get empty movie item object
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
 * MovieItem getEmptyMovie() - get empty movie item object
 * @return - empty movie item object
 */
    public MovieItem getEmptyMovie(){
        //create movie item from movie model
        MovieItem item = new MovieItem();
        item.setTMDBId(0);
        item.setTitle("");
        item.setOverview("");
        item.setReleaseDate("");
        item.setIMDBId("");

        item.setOriginalTitle("");
        item.setOriginalLanguage("");

        item.setPopularity(0);
        item.setVoteCount(0);
        item.setVoteAverage(0);

        item.setPosterPath("empty");
        item.setPoster(null);

        //set default values for Movie Info detail
        item.setIMDBId("");
        item.setHomepage("");
        item.setFavorite(false);

        item.setGenres(new ArrayList<GenreItem>());
        item.setCast(new ArrayList<CastItem>());
        item.setReviews(new ArrayList<ReviewItem>());
        item.setVideos(new ArrayList<VideoItem>());

        return item;
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
 *      ArrayList<MovieItem> prepareMovies(ArrayList<MovieModel>) - convert Movie models to items
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

            //set default values for Movie Info detail
            item.setIMDBId("");
            item.setHomepage("");
            item.setFavorite(false);

            item.setGenres(new ArrayList<GenreItem>());
            item.setCast(new ArrayList<CastItem>());
            item.setReviews(new ArrayList<ReviewItem>());
            item.setVideos(new ArrayList<VideoItem>());

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
    public MovieItem prepareMovieItem(MovieModel model, MovieItem item){

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
            if(item.site.equalsIgnoreCase(mTMDYouTube)){
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
        //not in list, add movie to
        mFavoriteMovies.add(movie);
    }

/**
 * void removeFavorite(MovieItem) - removes movie from Favorite Movie list
 * @param movie - movie to be removed from list
 */
    public void removeFavorite(MovieItem movie){
        //default index to -1
        int index = -1;

        //get number of favorite movies in list
        int count = mFavoriteMovies.size();

        //loop through movies in list
        for(int i = 0; i < count; i++){
            //find index of favorite movie to remove
            if(mFavoriteMovies.get(i).getTMDBId() == movie.getTMDBId()){
                //movie id number match, save index
                index = i;
            }
        }

        //check movie index value
        if(index != -1){
            //not default value, remove movie found at the index
            mFavoriteMovies.remove(index);
        }
    }

/**
 * boolean alreadyFavorite(int) - check if Movie id is already in the Favorite Movie list
 * @param id - check if movie id is already in the list
 * @return - true if already in list, false otherwise
 */
    public boolean alreadyFavorite(int id){
        int count = mFavoriteMovies.size();
        for(int i = 0; i < count; i++){
            if(mFavoriteMovies.get(i).getTMDBId() == id){
                return true;
            }
        }
        return false;
    }

/**************************************************************************************************/

}