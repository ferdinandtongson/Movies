package me.makeachoice.movies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**************************************************************************************************/
/**
 * MyFragment abstract class extends Fragments
 */
public abstract class MyFragment extends Fragment{

/**************************************************************************************************/
/**
 * Class Variables
 *      String KEY_SERVICE_NAME - key value used to store service name into Bundle
 *
 *      View mLayout - View layout of the fragment
 *      Bridge mBridge - class implementing Bridge interface
 *      String mServiceName - name of the Maid class taking care of the fragment
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //KEY_SERVICE_NAME - key value used to store the service into Bundle
    protected String KEY_SERVICE_NAME = "ServiceName";

    //mLayout - View layout of the fragment
    protected View mLayout;

    //mBridge - class implementing Bridge interface
    protected Bridge mBridge;

    //mMaidId - id number of the Maid class taking care of the fragment
    protected Integer mMaidId;


    //Implemented communication line, usually implemented by a Maid class
    public interface Bridge{
        //Bridge is called when onCreateView is called in the Fragment
        View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
        //Bridge is called when onCreateActivity is called in the Fragment
        void createActivity(Bundle savedInstanceState, View layout);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Abstract Methods:
 *      setServiceName
 */
/**************************************************************************************************/
    /**
     * void setMaidId(Integer) sets the id number of the Maid taking care of this Fragment
     * @param id - name of server, usually a Maid class
     */
    abstract public void setMaidId(Integer id);

/**************************************************************************************************/

}

