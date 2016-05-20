package me.makeachoice.movies.controller.viewside.helper;

import android.view.View;

import java.util.HashMap;

import me.makeachoice.movies.view.MyActivity;

/**
 * Abstract base Helper class. Used to manage resource ids and act as a ViewHolder.
 */
public abstract class MyHelper {


/**************************************************************************************************/
/**
 * ViewHolder inner class used to hold child view being managed by Maids or HouseKeepers
 */
/**************************************************************************************************/

    public static class ViewHolder{

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

        public View getView(MyActivity activity, int id){
            //check if view has already been created
            if(mHolderMap.containsKey(id)){
                //already been created, return view
                return mHolderMap.get(id);
            }
            else {
                //view no created, create view
                View view = activity.findViewById(id);
                //add view to HashMap
                mHolderMap.put(id, view);

                //return view
                return view;
            }
        }

}

/**************************************************************************************************/


}
