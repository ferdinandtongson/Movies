package me.makeachoice.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.R;
import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.adapter.util.ViewHolder;
import me.makeachoice.movies.model.json.MovieJSON;

/**
* PosterAdapter extends BaseAdapter class and is used as a List Adapter to display an image
* (movie poster) and a title text (movie title)
*
* Variables from MyAdapter:
*      Context mContext
*      Inflater mInflator
*      View.OnClickListener mOnClickListener
*
* Methods from MyAdapter
*       void updateView(int, view)
*      setOnClickListener(Listener)
*
*/
public class PosterAdapter extends MyAdapter {

/**************************************************************************************************/
    //Default layout has been modifed from TitleSimpleAdapter to use item_titleicon instead
    protected int DEFAULT_LAYOUT_ID = R.layout.item_poster;
    //Default ImageView id found in the default layout
    protected int DEFAULT_POSTER_VIEW_ID = R.id.img_poster;
    //Defaul TextView id found in the default layout
    protected int DEFAULT_TITLE_VIEW_ID = R.id.txt_title;

    //mItemLayoutId - id of the layout to be inflated
    protected int mItemLayoutId;
    //mPosterViewId - id of ImageView to display Poster
    protected int mPosterViewId;
    //mTitleViewId - id of TextView to display title
    protected int mTitleViewId;

    //public static variable to be able to index properly the layout and child ids
    public final static int INDEX_LAYOUT_ID = 0;
    public final static int INDEX_POSTER_ID = 1;
    public final static int INDEX_TITLE_ID = 2;
    public final static int INDEX_MAX = 3;

    //an array list of movie items for the list adapter to consume
    private ArrayList<PosterItem> mItems;

/**************************************************************************************************/
/**
 * PosterAdapter - constructor, uses default values for Layout, ImageView
 * @param c - activity context
 * @param items - array list of PosterItem objects; data used to populate view objects
 */
    public PosterAdapter(Context c, ArrayList<PosterItem> items) {
        //initialize class variables
        mContext = c;
        mInflater = LayoutInflater.from(c);
        mItems = items;

        //use default values for Layout and TextView to display the Title
        mItemLayoutId = DEFAULT_LAYOUT_ID;
        mPosterViewId = DEFAULT_POSTER_VIEW_ID;
        mTitleViewId = DEFAULT_TITLE_VIEW_ID;
    }

    /**
     * PosterAdapter - constructor, accept Keeper values for Layout and ImageView (for Poster)
     * @param c - activity context
     * @param items - array list of PosterItem objects; data used to populate view objects
     * @param ids - an int array holding layout and child id values
     */
    public PosterAdapter(Context c, ArrayList<PosterItem> items, int[] ids){
        //initialize class variable
        mContext = c;
        mInflater = LayoutInflater.from(c);
        mItems = items;

        //use Presenter values for Layout and TextView to display the Title
        mItemLayoutId = ids[INDEX_LAYOUT_ID];
        mPosterViewId = ids[INDEX_POSTER_ID];
        mTitleViewId = ids[INDEX_TITLE_ID];
    }

    View.OnClickListener mListener;

    public void setListener(View.OnClickListener listener){
        mListener = listener;
    }

    public void clearAdapter(){
        mItems.clear();
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
 * getItem(int) - returns the object in the ArrayList<T> position; PosterItem Object
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

    HashMap<Bitmap,Integer> mMyMap = new HashMap<>();
    public HashMap<Bitmap, Integer> getMap(){
        return mMyMap;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void updateView(int, View) - get object from ArrayList<T> and update ImageView with thumbnail
 * @param position - list item position
 * @param convertView - layout view holding the child views
 */
    protected void updateView(int position, View convertView){
        //get poster item object
        PosterItem item = mItems.get(position);

        //update view with poster image
        updatePosterView(item, convertView, position);
        //update view with movie title
        updateTitleView(item, convertView);
    }

/**
 * void updatePosterView(PosterItem, View) - update the imageView with a bitmap poster
 * @param item - Poster Item object holding movie info
 * @param convertView - layout view holding the child views
 */
    private void updatePosterView(PosterItem item, View convertView, int position){
        //TODO - removed ViewHolder pattern to handle OnClick events in GridView, researh further
        //get child view using ViewHolder class
        /*ImageView imgPoster = ViewHolder.get(convertView, mPosterViewId);
        if(imgPoster == null){
            //if imageView is null, get imageView using id
            imgPoster = (ImageView)convertView.findViewById(mPosterViewId);
        }*/

        //get ImageView
        ImageView imgPoster = (ImageView)convertView.findViewById(mPosterViewId);
        //set onClick listener
        imgPoster.setOnClickListener(mListener);
        //save adapter position to Tag
        imgPoster.setTag(position);

        Picasso.with(mContext).load(item.getPosterPath()).into(imgPoster);
    }


/**
 * void updateTitleView(PosterItem, View) - update textView with title of movie
 * @param item - Poster Item object hodling movie info
 * @param convertView - layout view holding child views
 */
    private void updateTitleView(PosterItem item, View convertView){
        //get child view using ViewHolder class
        TextView txtTitle = ViewHolder.get(convertView, mTitleViewId);
        if(txtTitle == null){
            //if textView isn't created, create textView using id
            txtTitle = (TextView) convertView.findViewById(mTitleViewId);
        }

        //update child view data - title
        txtTitle.setText(item.getTitle());
    }

/**************************************************************************************************/

}