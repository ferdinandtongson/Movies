package me.makeachoice.movies.controller.viewside.adapter;

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

import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.controller.viewside.helper.VideoHelper;
import me.makeachoice.movies.model.item.VideoItem;

/**
 * VideoRecycler extends RecyclerView.Adapter and is used to display video trailers of movies
 *
 * Methods from RecyclerView.Adapter:
 *      int getItemCount()
 *      ViewHolder onCreateViewHolder(ViewGroup, int)
 *      void onBindViewHolder(ViewHolder, int)
 *
 * Inner Class:
 *      VideoHolder extends RecyclerView.ViewHolder
 */
public class VideoRecycler extends RecyclerView.Adapter<VideoRecycler.VideoHolder> {

/**************************************************************************************************/
/**
 * Class Variables
 *      ArrayList<VideoItem> mVideos - array list of video data
 *      Bridge mBridge - class implementing Bridge interface, typically a Maid class
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mVideos - an array list of video item data consumed by the adapter
    private ArrayList<VideoItem> mVideos;

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
    public VideoRecycler(Bridge bridge){
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
 *      void setVideos(ArrayList<VideoItem>)
 */
/**************************************************************************************************/
/**
 * int getItemCount() - get number of video items in adapter
 * @return int - number of video items in adapter
 */
    @Override
    public int getItemCount(){
        if(mVideos != null){
            //return number of video items
            return mVideos.size();
        }
        return 0;
    }

/**
 * void setVideo(ArrayList<VideoItem>) - get data to be display by the RecyclerView
 * @param videos - list of video data
 */
    public void setVideos(ArrayList<VideoItem> videos){

        mVideos = videos;
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
    public VideoHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        //inflate the itemView
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(VideoHelper.CARD_VIDEO_LAYOUT_ID, viewGroup, false);


        //return ViewHolder
        return new VideoHolder(itemView);
    }

/**
 * void onBindViewHolder(ViewHolder, int) - where we bind our data to the views
 * @param holder - ViewHolder class; ReviewHolder
 * @param position - position of the itemView being bound
 */
    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        //load thumbnail image to imageView
        Picasso.with(mBridge.getActivityContext())
                .load(mVideos.get(position).thumbnailPath)
                .placeholder(PosterHelper.POSTER_PLACEHOLDER_IMG_ID)
                .error(PosterHelper.POSTER_PLACEHOLDER_IMG_ID)
                .into(holder.mImgTrailer);

        //set title of trialer
        holder.mTxtTitle.setText(mVideos.get(position).name);

    }

/**************************************************************************************************/





/**************************************************************************************************/
/**
 * VideoHolder - extends RecyclerView.ViewHolder, a design pattern to increase performance. It
 * holds the references to the UI components for each item in a ListView or GridView
 */
/**************************************************************************************************/


    public static class VideoHolder extends RecyclerView.ViewHolder{

/**************************************************************************************************/
/**
 * Child Views of the used by the ReviewRecycler
 */
/**************************************************************************************************/

        //mCrdVideo - cardView that hold child views found below
        protected CardView mCrdVideo;
        //mImgTrailer - imageView that holds thumbnail image of trailer
        protected ImageView mImgTrailer;
        //mTxtTitle - textView that holds the title of the trailer
        protected TextView mTxtTitle;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * VideoHolder - constructor
 * @param recycleView - item layout containing the child views
 */
        public VideoHolder(View recycleView){
            super(recycleView);

            //set CardView object
            mCrdVideo = (CardView)recycleView.findViewById(VideoHelper.CARD_VIDEO_CRD_VIDEO_ID);

            //set ImageView object
            mImgTrailer = (ImageView)recycleView.findViewById(VideoHelper.CARD_VIDEO_IMG_TRAILER_ID);

            //set TextView object
            mTxtTitle = (TextView)recycleView.findViewById(VideoHelper.CARD_VIDEO_TXT_TITLE_ID);

        }
    }

/**************************************************************************************************/

}
