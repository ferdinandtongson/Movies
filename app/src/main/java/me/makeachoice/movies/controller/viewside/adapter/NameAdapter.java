package me.makeachoice.movies.controller.viewside.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.controller.viewside.helper.InfoHelper;

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
public class NameAdapter extends BaseAdapter{

/**************************************************************************************************/
/**
 * Class Variables
 *      Context mContext - current context
 *      ArrayList<String> mNames - array list of names
 */
/**************************************************************************************************/

    //mBridge - class implementing Bridge interface
    private Bridge mBridge;

    //mNames - array list of names
    private ArrayList<String> mNames = new ArrayList<>();

    //Implemented communication line to any MyHouseKeeper class
    public interface Bridge{
        //get Context of current Activity
        Context getActivityContext();
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NamAdapter - constructor
 * @param bridge - class implementing Bridge interface
 * @param names - list of string names
 */
    public NameAdapter(Bridge bridge, ArrayList<String> names){
        //current activity context
        mBridge = bridge;

        //clear array list
        mNames.clear();

        //add names to array list
        mNames.addAll(names);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getCount() - get number of names in the list
 *      String getItem(int) - get name in the given list position
 *      long getItemId(int) - return 0
 *      View getView(int,View,ViewGroup) - get views to be displayed in listView
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
 * View getView(int,View,ViewGroup)- get views to be displayed in listview
 * @param position - position of item in list
 * @param convertView - item view contained in the listview
 * @param parent - listView
 * @return - updated item view
 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //view is null, inflate view
            convertView = LayoutInflater.from(mBridge.getActivityContext())
                    .inflate(InfoHelper.ITEM_NAME_LAYOUT_ID, parent, false);

            //create viewHolder for new convertView
            ViewHolder viewHolder = new ViewHolder();

            //attach viewHolder to convertView via tag
            convertView.setTag(viewHolder);
        }

        //update child views
        updateName(convertView, mNames.get(position));

        // Return the completed view to render on screen
        return convertView;
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
        //clear list array
        mNames.clear();

        //add names to list array
        mNames.addAll(names);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods
 *      void updateName(View,ArrayList<String>,int) - update textView child to display name
 */
/**************************************************************************************************/

/**
 * void updateName(View,ArrayList<String>,int) - update textView child to display name
 * @param convertView - view holding the child views
 * @param name - name to display in textView
 */
    private void updateName(View convertView, String name){

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

/**************************************************************************************************/

}