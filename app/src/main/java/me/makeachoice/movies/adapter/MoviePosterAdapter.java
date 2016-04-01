package me.makeachoice.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import me.makeachoice.movies.R;
import me.makeachoice.movies.adapter.item.MovieItem;
import me.makeachoice.movies.adapter.util.ViewHolder;

/**
* MoviePosterAdapter extends BaseAdapter class and is used as a List Adapter for the Android
* GridView widget.
*
* Variables from MyBaseAdapter:
*      Context mContext
*      Inflater mInflator
*      View.OnClickListener mOnClickListener
*
* Methods from MyBaseAdapter
*       void updateView(int, view)
*      setOnClickListener(Listener)
*
*/
public class MoviePosterAdapter extends MyBaseAdapter {
/**************************************************************************************************/
//Default layout has been modifed from TitleSimpleAdapter to use item_titleicon instead
protected int DEFAULT_LAYOUT_ID = R.layout.item_movie;
//Default TextView id found in the default layout
protected int DEFAULT_POSTER_VIEW_ID = R.id.item_poster;

//mItemLayoutId - id of the layout to be inflated
protected int mItemLayoutId;
//mPosterViewId - id of ImageView to display Poster
protected int mPosterViewId;

private ArrayList<MovieItem> mItems;

/**************************************************************************************************/
/**
 * MoviePosterAdapter - constructor, uses default values for Layout, ImageView
 * @param c - activity context
 * @param items - array list of MovieItem objects; data used to populate view objects
 */
    public MoviePosterAdapter(Context c, ArrayList<MovieItem> items) {
        //initialize class variables
        mContext = c;
        mInflater = LayoutInflater.from(c);
        mItems = items;

        //use default values for Layout and TextView to display the Title
        mItemLayoutId = DEFAULT_LAYOUT_ID;
        mPosterViewId = DEFAULT_POSTER_VIEW_ID;
    }

    /**
     * MoviePosterAdapter - constructor, accept Keeper values for Layout and ImageView (for Poster)
     * @param c - activity context
     * @param items - array list of MovieItem objects; data used to populate view objects
     * @param layoutId - list item layout id
     * @param posterViewId - child view of list item layout, imageView for thumbnail images
     */
    public MoviePosterAdapter(Context c, ArrayList<MovieItem> items, int layoutId, int posterViewId){
        //initialize class varialbes
        mContext = c;
        mInflater = LayoutInflater.from(c);
        mItems = items;

        //use Presenter values for Layout and TextView to display the Title
        mItemLayoutId = layoutId;
        mPosterViewId = posterViewId;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * getCount() - returns the size of the ArrayList<T>
 * @return int - returns number of items in list
 */
    public int getCount() {
        return mItems.size();
    }

/**
 * getItem(int) - returns the object in the ArrayList<T> position; MovieItem Object
 * @param position - position of list item
 * @return Object - item object at that given position
 */
    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

/**
 * getItemId(int) - returns index of the object in the ArrayList<T>
 * @param position - position of list item
 * @return int - id of list item
 */
    @Override
    public long getItemId(int position) {
        return mItems.indexOf(getItem(position));
    }

/**
 * getView(int, View, ViewGroup) - create or recylce View object then update and return View
 * @param position - position of view
 * @param convertView - view object
 * @param parent - parent of view, list view object
 * @return View - return updated item view of list
 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Check if we can recycle old view object
        if (convertView == null) {
            // if not recycled, inflate layout of new view object
            convertView = mInflater.inflate(mItemLayoutId, null);
        }

        //updateView with data
        updateView(position, convertView);

        return convertView;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * updateView(int, View) - get object from ArrayList<T> and update ImageView with thumbnail
 * @param position - list item position
 * @param convertView - layout of list item view
 */
    protected void updateView(int position, View convertView){
        MovieItem item = mItems.get(position);

        updatePosterView(item, convertView);
    }

    private void updatePosterView(MovieItem item, View convertView){

        //get child view using ViewHolder class
        ImageView imgIcon = ViewHolder.get(convertView, mPosterViewId);
        if(imgIcon == null){
            imgIcon = (ImageView) convertView.findViewById(mPosterViewId);
        }

        //update child view data - title
        imgIcon.setImageResource(item.getPoster());
    }
/**************************************************************************************************/

    // create a new ImageView for each item referenced by the Adapter
    /*public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }*/

}