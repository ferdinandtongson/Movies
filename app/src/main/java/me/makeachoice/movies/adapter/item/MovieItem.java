package me.makeachoice.movies.adapter.item;

/**
 * MovieItem extends MyItem base class is used by a BaseAdapter to populate a GridView. This
 * class is associated with item_movie.xml layout resource file.
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
public class MovieItem extends MyItem {
    //mPosterId - holds the poster id image value
    protected int mPosterId;

/**************************************************************************************************/
/**
 * TitleItem(String) - constructor, accepts string value for title
 */
public MovieItem(){}

public MovieItem(int posterId){
        mPosterId = posterId;
    }

/**************************************************************************************************/
/**
 * Getter and Setter for the image id of the movie poster thumbnail
 * getPoster() returns the poster id; int
 * setPoster(int) sets the int value of the poster thumbnail image
 */
    public int getPoster(){
        return mPosterId;
    }

    public void setPoster(int posterId){
        mPosterId = posterId;
    }

/**************************************************************************************************/
}
