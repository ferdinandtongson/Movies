package me.makeachoice.movies.controller.housekeeper.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.makeachoice.movies.controller.housekeeper.helper.ReviewHelper;
import me.makeachoice.movies.model.item.ReviewItem;

/**
 * ReviewRecycler extends RecyclerView.Adapter and is used to display the reviews made for a movie
 *
 * Methods from RecyclerView.Adapter:
 *      int getItemCount()
 *      ViewHolder onCreateViewHolder(ViewGroup, int)
 *      void onBindViewHolder(ViewHolder, int)
 *
 * Inner Class:
 *      ReviewHolder extends RecyclerView.ViewHolder
 */
public class ReviewRecycler extends RecyclerView.Adapter<ReviewRecycler.ReviewHolder> {

/**************************************************************************************************/
/**
 * Class Variables
 *      ArrayList<ReviewItem> mReviews - array list of review data
 *      Bridge mBridge - class implementing Bridge interface, typically a Maid class
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mReviews - an array list of review item data consumed by the adapter
    private ArrayList<ReviewItem> mReviews;

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
    public ReviewRecycler(Bridge bridge){
        //set bridge communication
        mBridge = bridge;
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getItemCount()
 *
 * Setters:
 *      void setReviews(ArrayList<ReviewItem>)
 */
/**************************************************************************************************/
/**
 * int getItemCount() - get number of review items in adapter
 * @return int - number of review items in adapter
 */
    @Override
    public int getItemCount(){
        if(mReviews != null){
            //return number of review items
            return mReviews.size();
        }
        return 0;
    }

/**
 * void setReviews(ArrayList<ReviewItem>) - get data to be display by the RecyclerView
 * @param reviews - list of review data
 */
    public void setReviews(ArrayList<ReviewItem> reviews){
        mReviews = reviews;
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
 * @return - ViewHolder class; ReviewHolder
 */
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        //inflate the itemView
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(ReviewHelper.CARD_REVIEW_LAYOUT_ID, viewGroup, false);


        //return ViewHolder
        return new ReviewHolder(itemView);
    }

/**
 * void onBindViewHolder(ViewHolder, int) - where we bind our data to the views
 * @param holder - ViewHolder class; ReviewHolder
 * @param position - position of the itemView being bound
 */
    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        //set author
        holder.mTxtAuthor.setText(mReviews.get(position).author);

        //set review
        holder.mTxtReview.setText(mReviews.get(position).review);
    }

/**************************************************************************************************/





/**************************************************************************************************/
/**
 * ReviewHolder - extends RecyclerView.ViewHolder, a design pattern to increase performance. It
 * holds the references to the UI components for each item in a ListView or GridView
 */
/**************************************************************************************************/


    public static class ReviewHolder extends RecyclerView.ViewHolder{

/**************************************************************************************************/
/**
 * Child Views of the used by the ReviewRecycler
 */
/**************************************************************************************************/

        //mCrdReview - cardView that hold child views found below
        protected CardView mCrdReview;
        //mTxtAuthor - textView that show the author of the review
        protected TextView mTxtAuthor;
        //mTxtReview - textView that show the review of the movie
        protected TextView mTxtReview;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewHolder - constructor
 * @param recycleView - item layout containing the child views
 */
        public ReviewHolder(View recycleView){
            super(recycleView);

            //set CardView object
            mCrdReview = (CardView)recycleView.findViewById(ReviewHelper.CARD_REVIEW_CRD_REVIEW_ID);

            //set TextView object used to display author of the review
            mTxtAuthor = (TextView)recycleView.findViewById(ReviewHelper.CARD_REVIEW_TXT_AUTHOR_ID);

            //set TextView object used to display review of the movie
            mTxtReview = (TextView)recycleView.findViewById(ReviewHelper.CARD_REVIEW_TXT_REVIEW_ID);

        }
    }

/**************************************************************************************************/

}
