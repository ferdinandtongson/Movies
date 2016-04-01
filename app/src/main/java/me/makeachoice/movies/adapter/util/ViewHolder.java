package me.makeachoice.movies.adapter.util;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder class is used to limit the number of calls to findViewById(). The idea is that you
 * call it once, then store the child view references in a ViewHolder instance that will be
 * associated with the convertView thanks to View.setTag()
 */
public class ViewHolder {

    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        //gets the SparseArray<View> object stored in the requesting view
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        if (viewHolder == null) {
            //if the ViewHolder is null, create the ViewHolder
            viewHolder = new SparseArray<View>();
            //save ViewHolder object to view.setTag()
            view.setTag(viewHolder);
        }

        //get childView by using the key - id
        View childView = viewHolder.get(id);

        if (childView == null) {
            //if view is null, get view in resource by using findViewById(id)
            childView = view.findViewById(id);
            //put into ViewHolder key - id, value - childView
            viewHolder.put(id, childView);
        }

        //return View
        return (T) childView;
    }
}
