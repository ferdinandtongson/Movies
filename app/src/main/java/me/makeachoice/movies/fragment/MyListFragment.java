package me.makeachoice.movies.fragment;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * MyListFragment abstract class has a set of methods and an Interface class used mainly for trying
 * to keep an MVP design structure. The abstract class ensure that the communication lines between
 * the Presenter and the View are established.
 */
public abstract class MyListFragment extends ListFragment implements MyFragmentInterface{

    protected int mLayoutId;
    protected String mServiceName;
    protected Bridge mBridge;

    abstract public void setLayout(int id);
    abstract public void setServiceName(String name);

    //Upkeeping MyMaid class must implement this interface
    public interface Bridge{
        void onItemClick(ListView l, View v, int position, long id);
        ListAdapter getListAdapter();
        void setListAdapter(ListAdapter adapter);
    }
}
