package me.makeachoice.movies.adapter.item;

/**
 * MoviePosterItem extends MyItem base class and will be used by a BaseAdapter. This class is
 * associated with item_poster.xml layout resource file.
 *
 * MoviePosterItem will contain the title and source image of the poster
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
public class MoviePosterItem extends MyItem {

/**************************************************************************************************/

    //mPosterId - holds the source image id of the poster to be displayed
    protected int mPosterId;
    //mTitle - holds the title of the movie
    protected String mTitle;

/**************************************************************************************************/
/**
 * MoviePosterItem - empty constructor
 */
    public MoviePosterItem(){}

    /**
     * MoviePosterItem(String, int) - constructor
     * @param title - title of poster
     * @param id - source image id of poster
     */
    public MoviePosterItem(String title, int id){
        //title of poster
        mTitle = title;
        //source image id of poster
        mPosterId = id;
    }

/**************************************************************************************************/
/**
 * Getter and Setter for the image id of the movie poster thumbnail
 * String getTitle() returns title of poster
 * int getPoster() returns the poster id
 *
 * void setTitle(String) sets title value of poster
 * void setPoster(int) sets the int value of the poster source id
 */
    public String getTitle() { return mTitle; }
    public int getPoster(){
        return mPosterId;
    }

    public void setTitle(String title) { mTitle = title; }
    public void setPoster(int posterId){
        mPosterId = posterId;
    }

/**************************************************************************************************/
}
