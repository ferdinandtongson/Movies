package me.makeachoice.movies.controller.housekeeper.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.controller.housekeeper.helper.InfoHelper;

/**
 * NamAdapter extends BaseAdapter and is used to display a simple textViw child.
 *
 * Methods from BaseAdapter
 *      int getCount()
 *      String getItem(int)
 *      long getItemId(int)
 *      ViewHolder onCreateViewHolder(int,View,ViewGroup)
 *
 * Inner Class:
 *      ViewHolder
 *
 */
public class NameAdapter extends BaseAdapter {

/**************************************************************************************************/
/**
 * Class Variables
 *      Context mContext - current context
 *      ArrayList<String> mNames - array list of names
 */
/**************************************************************************************************/

    //mContext - current context
    private Context mContext;

    //mNames - array list of names
    private ArrayList<String> mNames = new ArrayList<>();

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NamAdapter - constructor
 * @param context - current activity context
 * @param names - list of string names
 */
    public NameAdapter(Context context, ArrayList<String> names){
        //current activity context
        mContext = context;

        //clear array list
        mNames.clear();

        //add names to array list
        mNames.addAll(names);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getItemCount() - get number of names in the list
 *      String getItem(int) - get name in the given list position
 *      long getItemId(int) - return 0
 *      View getView(int,View,ViewGroup) - update views displayed in listView
 */
/**************************************************************************************************/
/**
 * int getCount() - get number of names in the list
 * @return - return number of names in the list
 */
    @Override
    public int getCount(){
        //return size of list
        return mNames.size();
    }

/**
 * String getItem(int) - get name in the given list position
 * @param position - position in the list
 * @return - String, name in the given position of the list
 */
    @Override
    public String getItem(int position){
        //get name in the given position of the list
        return mNames.get(position);
    }

    /**
     * long getItemId(int) - return 0
     * @param position - position in the list
     * @return - 0, no id number for names
     */
    @Override
    public long getItemId(int position){
        //no unique id number for names
        return 0;
    }

    /**
     * View getView(int,View,ViewGroup)- update the items in the listView
     * @param position - position of item in list
     * @param convertView - item view contained in the listview
     * @param parent - listView
     * @return - updated item view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Movie", "NameAdapter.getView");
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(InfoHelper.ITEM_NAME_LAYOUT_ID,
                    parent, false);
            ViewHolder viewHolder = new ViewHolder();

            convertView.setTag(viewHolder);
        }

        updateName(convertView, mNames, position);

        // Return the completed view to render on screen
        return convertView;
    }

    private void updateName(View convertView, ArrayList<String> nameList, int position){
        //get the data item for this position
        String name = nameList.get(position);

        //get viewHolder
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        //get textView to display cast members' name
        TextView txtName = (TextView)viewHolder.getView(
                convertView, InfoHelper.ITEM_NAME_TXT_NAME_ID);

        //set name to textView
        txtName.setText(name);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setNames(ArrayList<String>) - set names to be displayed in the listView
 */
/**************************************************************************************************/
/**
 * void setNames(ArrayList<String>) - set the list of names to be displayed by the listview
 * @param names - lst of names to be displayed
 */
    public void setNames(ArrayList<String> names){
        Log.d("Movies", "NameAdapter.setNames");
        mNames.clear();
        Log.d("Movies", "     clear");
        mNames.addAll(names);
        Log.d("Movies", "     add names");
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * ViewHolder - a design pattern to increase performance. It holds the references to the UI
 * components for each item in a ListView or GridView
 */
/**************************************************************************************************/

    public static class ViewHolder{

/**************************************************************************************************/
/**
 * Class Variables
 *      HashMap<Integer,View> mHolderMap - contains views with their resource id as a key
 */
/**************************************************************************************************/

        //mHolderMap contains views with their resource id as a key
        HashMap<Integer, View> mHolderMap = new HashMap<>();



        public View getView(View layout, int id){
            //check if view has already been created
            if(mHolderMap.containsKey(id)){
                //already been created, return view
                return mHolderMap.get(id);
            }
            else {
                //view no created, create view
                View view = layout.findViewById(id);

                //add view to HashMap
                mHolderMap.put(id, view);

                //return view
                return view;
            }
        }

    }
}