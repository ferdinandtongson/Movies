package me.makeachoice.movies.controller.housekeeper.recycler;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.housekeeper.page.PosterPage;

/**
 * PosterRecycler extends RecyclerView.Adapter and is used to display the image of a poster and
 * its' title
 *
 * Methods from RecyclerView.Adapter:
 *      int getItemCount()
 *      ViewHolder onCreateViewHolder(ViewGroup, int)
 *      void onBindViewHolder(ViewHolder, int)
 *
 * Inner Class:
 *      PosterHolder extends RecyclerView.ViewHolder
 */
public class PosterRecycler extends RecyclerView.Adapter<PosterRecycler.PosterHolder> {

/**************************************************************************************************/
/**
 * Class Variables
 *      ArrayList<PosterItem> mPosters - array list of poster data
 *      Bridge mBridge - class implementing Bridge interface, typically a Maid class
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mPosters - an array list of poster item data consumed by the adapter
    private ArrayList<PosterItem> mPosters;

    //mBridge - class implementing Bridge, typically a Maid class
    private Bridge mBridge;

    //Implemented communication line to a class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterRecycler - constructor
 * @param bridge - class implementing Bridge interface
 */
    public PosterRecycler(Bridge bridge){
        //set poster item data
        mBridge = bridge;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getItemCount()
 *
 * Setters:
 *      void setPosters(ArrayList<PosterItem>)
 */
/**************************************************************************************************/
/**
 * int getItemCount() - get number of poster items in adapter
 * @return int - number of poster items in adapter
 */
    @Override
    public int getItemCount(){
        //return number of poster items
        return mPosters.size();
    }

/**
 * void setPosters(ArrayList<PosterItem>) - get data to be display by the RecyclerView
 * @param posters - list of poster data
 */
    public void setPosters(ArrayList<PosterItem> posters){
        mPosters = posters;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implemented Methods from RecyclerView.Adapter:
 *      ViewHolder onCreateViewHolder(ViewGroup, int)
 *      void onBindViewHolder(ViewHolder, int)
 */
/**************************************************************************************************/
/**
 * ViewHolder onCreateViewHolder(ViewGroup, int) - inflates the layout and creates an instance
 * of the ViewHolder (PosterHolder) class. This instance is used to access the views in the
 * inflated layout and is only called when a new view must be created.
 * @param viewGroup - parent view that will hold the itemView, RecyclerView
 * @param i - position of the itemView
 * @return - ViewHolder class; PosterHolder
 */
    @Override
    public PosterHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        //inflate the itemView
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(PosterPage.POSTER_CARD_LAYOUT_ID, viewGroup, false);


        //return ViewHolder
        return new PosterHolder(itemView);
    }

/**
 * void onBindViewHolder(ViewHolder, int) - where we bind our data to the views
 * @param holder - ViewHolder class; PosterHolder
 * @param position - position of the itemView being bound
 */
    @Override
    public void onBindViewHolder(PosterHolder holder, int position) {

        //add poster title
        holder.mTxtTitle.setText(mPosters.get(position).getTitle());

        //add poster image, placeholder image and error image
        Picasso.with(mBridge.getActivityContext())
                .load(mPosters.get(position).getPosterPath())
                .placeholder(PosterPage.POSTER_PLACEHOLDER_IMG_ID)
                .error(PosterPage.POSTER_ERROR_IMG_ID)
                .into(holder.mImgPoster);
    }

/**************************************************************************************************/





/**************************************************************************************************/
/**
 * PosterHolder - extends RecyclerView.ViewHolder, a design pattern to increase performance. It
 * holds the references to the UI components for each item in a ListView or GridView
 */
/**************************************************************************************************/


    public static class PosterHolder extends RecyclerView.ViewHolder{

/**************************************************************************************************/
/**
 * Child Views of the used by the PosterRecycler
 */
/**************************************************************************************************/

        //mCrdPoster - cardView that hold child views found below
        protected CardView mCrdPoster;
        //mTxtTitle - textView that show the title of the poster
        protected TextView mTxtTitle;
        //mImgPoster - imageView that holds the image of the poster
        protected ImageView mImgPoster;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * PosterHolder - constructor
 * @param recycleView - item layout containing the child views
 */
        public PosterHolder(View recycleView){
            super(recycleView);

            //set CardView object
            mCrdPoster = (CardView)recycleView.findViewById(PosterPage.POSTER_ITEM_CRD_ID);

            //set TextView object used to display title of poster
            mTxtTitle = (TextView)recycleView.findViewById(PosterPage.POSTER_ITEM_TXT_ID);

            //set ImageView object used to display image of poster
            mImgPoster = (ImageView)recycleView.findViewById(PosterPage.POSTER_ITEM_IMG_ID);
        }
    }

/**************************************************************************************************/

}
