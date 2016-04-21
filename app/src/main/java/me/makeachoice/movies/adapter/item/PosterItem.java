package me.makeachoice.movies.adapter.item;

import android.graphics.Bitmap;

/**
 * PosterItem extends MyItem base class and will be used by a BaseAdapter.
 *
 * PosterItem will contain the title and source image of the poster
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

    //mTitle - holds the title of the movie
    protected String mTitle;
    //mPosterPath - holds the path to the poster image
    protected String mPosterPath;
    //mImage - holds the poster image
    protected Bitmap mImage;

/**************************************************************************************************/
/**
 * PosterItem - empty constructor
 */
    public PosterItem(){}

    /**
     * PosterItem(String, int) - constructor
     * @param title - title of poster
     * @param path - path to image of poster
     */
    public PosterItem(String title, String path){
        //title of poster
        mTitle = title;
        //path to poster image
        mPosterPath = path;
        //default image is null
        mImage = null;
    }

/**************************************************************************************************/
/**
 * Getter and Setter for the image id of the movie poster thumbnail
 * String getTitle() returns title of poster
 * int getPosterPath() returns the poster id
 *
 * void setTitle(String) sets title value of poster
 * void setPoster(int) sets the int value of the poster source id
 */
    public String getTitle() { return mTitle; }
    public String getPosterPath(){
        return mPosterPath;
    }
    public Bitmap getImage(){ return mImage; }


    public void setTitle(String title) { mTitle = title; }
    public void setPosterPath(String path){
        mPosterPath = path;
    }
    public void setImage(Bitmap img) { mImage = img; }

/**************************************************************************************************/
}
