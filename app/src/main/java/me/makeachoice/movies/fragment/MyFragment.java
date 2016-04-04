package me.makeachoice.movies.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListAdapter;

/**
 * Fragment abstract used for all my non-list fragments
 */
public abstract class MyFragment extends Fragment implements MyFragmentInterface{

    protected int mLayoutId;
    protected View mLayout;
    protected String mServiceName;
    protected Bridge mBridge;

    abstract public void setLayout(int id);
    abstract public void setServiceName(String name);

    //Upkeeping MyMaid class must implement this interface
    public interface Bridge{
        //TODO - need to change Bridge concept for Fragment classes
        void onItemClick(int position);
        ListAdapter getListAdapter();
    }


}

