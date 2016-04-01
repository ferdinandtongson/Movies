package me.makeachoice.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * MyBaseAdapter - abstract class to act as my Base class for all my custom Adapters
 */
public abstract class MyBaseAdapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;

    abstract void updateView(int position, View convertView);

    View.OnClickListener mOnClickListener;
    public void setOnClickListener(View.OnClickListener listener){
        mOnClickListener = listener;
    }

}
