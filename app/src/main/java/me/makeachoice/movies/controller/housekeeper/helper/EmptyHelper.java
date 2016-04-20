package me.makeachoice.movies.controller.housekeeper.helper;

import android.widget.TextView;

import me.makeachoice.movies.R;

/**
 * EmptyHelper contains constant definitions for all the resources used by any of the "Empty"
 * classes underneath the housekeeper.
 */
public class EmptyHelper {

/**************************************************************************************************/
/**
 * Fragment layout ids used to display an empty fragment, from empty_fragment.xml
 */
/**************************************************************************************************/

    //EMPTY_FRAGMENT_LAYOUT_ID is the fragment layout used to display an error message
    public static final int EMPTY_FRAGMENT_LAYOUT_ID = R.layout.empty_fragment;

    //EMPTY_TXT_ID is the textView displaying the "empty" message"
    public static final int EMPTY_TXT_ID = R.id.txt_message;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * String resource ids used by EmptyFragment to display a message
 */
/**************************************************************************************************/

    //EMPTY_MSG_NO_NETWORK_ID is a "no network" error message
    public static final int EMPTY_MSG_NO_NETWORK_ID = R.string.msg_no_network;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ViewHolder class used to hold the child views of EmptyFragment
 */
/**************************************************************************************************/

    public static class ViewHolder{
        public boolean isEmpty;
        public TextView message;
    }

/**************************************************************************************************/

}
