package me.makeachoice.movies.model.item;


import android.graphics.Bitmap;

/**
 * PosterItem extends MyItem base class and will be used by a BaseAdapter.
 *
 * PosterItem will contain the poster data used by View
 *
 * In your Presenter, adding data to your adapter before calling setListAdapter():
 *      ArrayList<T> rowItems = new ArrayList<>();
 *      for(int i = 0; i < count; i++){
 *          XItem item = new XItem(x_array[i]);
 *          rowItems.add(item);
 *      }
 *
 *      [X]Adapter adapter = new [X]Adapter(context, rowItems);
 *      setListAdapter(adapter);
 *
 *
 */
public class PosterItem extends MyItem {

/**************************************************************************************************/

    //mTMDBId - holds the TheMovieDB id number of the movie
    protected int mTMDBId;
    //mTitle - holds the title of the movie
    protected String mTitle;
    //mPosterPath - holds the path to the poster image
    protected String mPosterPath;
    //mPoster - holds the poster image
    protected Bitmap mPoster;
    //mPosterByte - holds the byte array for the poster image
    protected byte[] mPosterByte;

/**************************************************************************************************/
/**
 * PosterItem - empty constructor
 */
    public PosterItem(){}

    /**
     * PosterItem(String, int) - constructor
     * @param id - TheMovieDB id of the movie
     * @param title - title of poster
     * @param path - path to image of poster
     */
    public PosterItem(int id, String title, String path){
        //TheMovieDB id of the movie
        mTMDBId = id;
        //title of poster
        mTitle = title;
        //path to poster image
        mPosterPath = path;
        //default image is null
        mPoster = null;
        //default image byte array
        mPosterByte = null;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getTMDBId() - gets TheMovieDB id number of the movie
 *      String getTitle() - gets title of poster
 *      String getPosterPath() - gets the full API path to the poster image in TMDB
 *      Bitmap getPoster() - gets the poster bitmap
 *      byte[] getPosterBytes() - gets the byte array of the poster bitmap
 */
/**************************************************************************************************/
/**
 * int getTMDBId() - gets TheMovieDB id number of the movie
 * @return - movie id number
 */
    public int getTMDBId(){ return mTMDBId; }

/**
 * String getTitle() - gets title of poster
 * @return - title of poster
 */
    public String getTitle() { return mTitle; }

/**
 * String getPosterPath() - gets the full API path to the poster image in TMDB
 * @return - poster path API call
 */
    public String getPosterPath(){
        return mPosterPath;
    }

/**
 * Bitmap getPoster() - gets the poster bitmap
 * @return - bitmap of poster
 */
    public Bitmap getPoster(){ return mPoster; }

/**
 * byte[] getPosterBytes() - gets the byte array of the poster bitmap
 * @return - byte array of poster bitmap
 */
    public byte[] getPosterBytes(){ return mPosterByte; }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setTMDBId(int) - sets TheMovieDB id number of the movie
 *      void setTitle(String) - sets title value of the poster
 *      void setPosterPath(String) - sets the full poster path API call to TMDB
 *      void setPoster(Bitmap) - sets the bitmap value of the poster
 *      void setPosterBytes(byte[]) - set the byte array value of the poster bitmap
 */
/**************************************************************************************************/
/**
 * void setTMDBId(int) - sets TheMovieDB id number of the movie
 * @param id - TMDB id number of movie
 */
    public void setTMDBId(int id){ mTMDBId = id; }

/**
 * void setTitle(String) - sets the title value of the poster
 * @param title - title of poster
 */
    public void setTitle(String title) { mTitle = title; }

/**
 * void setPosterPath(String) - sets the full poster path API call to TMDB
 * @param path - full poster path API call
 */
    public void setPosterPath(String path){
        mPosterPath = path;
    }

/**
 * void setPoster(Bitmap) - sets the bitmap value of the poster
 * @param bmp - bitmap of the poster
 */
    public void setPoster(Bitmap bmp) { mPoster = bmp; }

/**
 * void setPosterBytes(byte[]) - set the byte array value of the poster bitmap
 * @param bmpByte - byte array of the poster bitmap
 */
    public void setPosterBytes(byte[] bmpByte){ mPosterByte = bmpByte;}

/**************************************************************************************************/
}
