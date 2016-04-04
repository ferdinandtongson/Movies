package me.makeachoice.movies.controller.housekeeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.HashMap;

import me.makeachoice.movies.MainActivity;
import me.makeachoice.movies.R;
import me.makeachoice.movies.adapter.PosterAdapter;
import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.butler.MovieButler;
import me.makeachoice.movies.controller.housekeeper.assistant.MainFragmentAssistant;
import me.makeachoice.movies.controller.housekeeper.maid.EmptyMaid;
import me.makeachoice.movies.controller.housekeeper.maid.InfoMaid;
import me.makeachoice.movies.controller.housekeeper.maid.MyMaid;
import me.makeachoice.movies.controller.housekeeper.maid.PosterMaid;
import me.makeachoice.movies.controller.housekeeper.maid.staff.PosterStaff;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.model.json.MovieJSON;

/**
 * MainKeeper is the MyHouseKeeper class for MainActivity. It's primary responsibility is to
 * initializes and takes care of the MainActivity resource details.
 *
 * //TODO - need to dynamically set the Menus in the ActionBar, now currently hardcoded in xml file
 *
 * It also handles the loading and unloading of Fragments and manages the MyMaid classes responsible
 * for the upkeep of the Fragments and the communication between the Maids, Activity and Boss.
 *
 * Finally, it directly communicates with the Boss to get all the necessary data for the Views.
 */
public class MainKeeper extends MyHouseKeeper implements MainActivity.Bridge,
        PosterMaid.Bridge, EmptyMaid.Bridge, InfoMaid.Bridge{
/**
 * MainKeeper will be able to display the following fragments:
 *      AppList
 *      AppListIcon
 *      AppGrid
 *      AppGridInfo
 *      AppInfo
 *
 * Variables from MyHouseKeeper:
 *      Boss mBoss
 *
 * Implements Boss.xxxBridge Methods:
 *
 *
 * Implements MainActivity.Bridge Methods:
 *      int getActivityLayoutId();
 *      int getFragmentContainerId();
 *      int getToolbarId();
 *      int getFloatingActionButtonId();
 *
 *      void createFragment(Boolean);
 *      void setFragmentManager(FragmentManager manager)
 *
 *      void onOptionsItemSelected(MenuItem item);
 *      void setFABOnClickListener();
 *
 */
/**************************************************************************************************/
    //NAME - unique name of the MyHouseKeeper
    public final static String NAME = "MainKeeper";

    //mMovieSelectType - type of movie selection fragment
    public final static String FRAG_MOVIE_SELECT = "MovieSelectFragment";

    public final static int SELECT_TYPE_GRID = 0;
   // public final static int SELECT_TYPE_LIST = 1;
    public final static int DEFAULT_SELECT_TYPE = SELECT_TYPE_GRID;

    //mMovieInfoType - type of information fragments
    private int mMovieInfoType;
    public final static String FRAG_MOVIE_INFO = "MovieInfoFragment";

    public final static int INFO_TYPE_SIMPLE = 0;


/**************************************************************************************************/

    private MainFragmentAssistant mFragAssistant;
    private String mCurrentFragName;
    public MainKeeper(Boss boss, Context ctx){
        Log.d("Movies", "MainKeeper constructor");
        mBoss = boss;
        mActivityContext = ctx;

        mBoss.registerHouseKeeper(NAME, this);

        mCurrentFragName = NAME_POSTER;
        mSortBy = MovieButler.MOVIE_REQUEST_HIGHEST_RATED;

        initHouseKeeping();

        mFragAssistant = new MainFragmentAssistant(LAYOUT_MAIN_CONTAINER);

    }

    public void setFragmentManager(FragmentManager manager){
        mFragmentManager = manager;
    }


/**************************************************************************************************/
/**
 * PosterMaid is in charge of taking care of displaying thumbnail icon images of movies in a
 * grid fragment. It will maintain all events or requests called by the fragment and will push
 * these events or requests up to the MyHouseKeeper if the MyMaid cannot handle it.
 *
 * EmptyMaid is in charge of displaying and "Empty" message if the gridView in the PosterFragment
 * is empty.
 *
 * MovieInfoMaid is in charge of displaying information about a particular movie selected by the
 * user. It will maintain all events or requests called by the fragment and will push these events
 * or requests up to the MyHouseKeeper if the MyMaid cannot handle it.
 */
/**************************************************************************************************/

    //NAME_POSTER_MAID - name registered to the Boss for PosterMaid
    private final static String NAME_POSTER = "PosterName";
    //mPosterMaid - maid in charge of the PosterFragment
    private PosterMaid mPosterMaid;
    //mPosterStaff - needed by PosterMaid, handles creating the PosterAdapter
    private PosterStaff mPosterStaff;

    //NAME_EMPTY_MAID - name registered to the Boss for EmptyMaid
    private final static String NAME_EMPTY = "EmptyName";
    //mEmptyMaid - maid in charge of the EmptyFragment
    private EmptyMaid mEmptyMaid;

    //NAME_INFO_MAID - name registered to the Boss for InfoMaid
    private final static String NAME_INFO = "InfoName";
    //mInfoMaid - maid in charge of the InfoFragment
    private InfoMaid mInfoMaid;

/**************************************************************************************************/
/**
 * void initHouseKeeping() - initializes the Maids that will take care of the fragments that
 * will be added to MainKeepers' Activity.
 *
 * HouseKeeping:
 *      PosterMaid
 *      EmptyMaid
 */
    private void initHouseKeeping(){
        //initialize Maid and Staff in charge of the poster fragment (grid fragment)
        initPosterMaid();
        initEmptyMaid();
        initInfoMaid();
    }

/**************************************************************************************************/
/**
 * void initPosterMaid() - initialize PosterMaid and Staff needed by PosterMaid and register
 * Maid to Boss.
 *
 * Staff:
 *      PosterStaff
 *
 * Implements PosterMaid.Bridge methods:
 *      ListAdapter requestPosterAdapter();
 *      void onItemClick(ListView l, View v, int position, long id);
 */
    private void initPosterMaid(){
        //initialize and register PosterMaid
        mPosterMaid = new PosterMaid(this, NAME_POSTER);
        mBoss.registerMaid(NAME_POSTER, mPosterMaid);

    }

    private void initEmptyMaid(){
        //initialize and register EmptyMaid
        mEmptyMaid = new EmptyMaid(this, NAME_EMPTY);
        mBoss.registerMaid(NAME_EMPTY, mEmptyMaid);
    }

    private void initInfoMaid(){
        //initialize and register InfoMaid
        mInfoMaid = new InfoMaid(this, NAME_INFO);
        mBoss.registerMaid(NAME_INFO, mInfoMaid);
    }

    private HashMap<String, Fragment> mFragmentRegistry = new HashMap<>();
    public void registerFragment(String key, Fragment fragment){
        mFragmentRegistry.put(key, fragment);
    }



/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Variables used for MainActivity
 */
    //Activity Layout Id
    private int LAYOUT_MAIN = R.layout.activity_main;
    //Fragment Container Id found in activity_main.xml layout file
    private int LAYOUT_MAIN_CONTAINER = R.id.fragment_container;

    //Toolbar Id found in toolbar.xml layout file
    private int TOOLBAR_MAIN = R.id.toolbar;
    //Menu for Toolbar found in menu_main menu file
    private int MENU_MAIN = R.menu.menu_main;
    //Item id from Menu
    private int MENU_ITEM01 = R.id.action_bar_item01;
    private int MENU_ITEM02 = R.id.action_bar_item02;


    //mSortType - holds sort type
    private int mSortBy;

    //FloatingActionButton Id found in float_button.xml layout file
    private int FLOATING_ACTION_BUTTON = R.id.fab;

    //OnClickListener used with FloatingActionButton
    private View.OnClickListener mFABOnClickListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onFABOnClickListener(view);
        }
    };
/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Implements MainActivity.Bridge Methods:
 *      int getActivityLayoutId();
 *      int getToolbarId();
 *      int getMenuId();
 *      int getFloatingActionButtonId();
 *
 *      int getFragmentType(String);
 *      void setFragmentType(String, int);
 *
 *      OnClickListener getFABOnClickListener();
 *      void onOptionsItemSelected(MenuItem item)
 *
 */
/**************************************************************************************************/
/**
 * int getActivityLayoutId() - get layout id used for Main Activity
 * @return int - layout id value
 */
    public int getActivityLayoutId(){
        return LAYOUT_MAIN;
    }


/**
 * int getToolbarId() - get the toolbar id, a toolbar object found inside the xml layout file used
 * by the Activity.
 * @return int - toolbar id
 */
    public int getToolbarId(){
        return TOOLBAR_MAIN;
    }

/**
 * int getMenuId - get Id of menu file from res/menu to be used in the Toolbar
 * @return int - xml menu id
 */
    public int getMenuId(){
        return MENU_MAIN;
    }

/**
 * int getFloatingActionButtonId() - get the floating action button id found inside the xml
 * layoutfile used by the Activity
 * @return int - floating action button id
 */
    public int getFloatingActionButtonId(){
        return FLOATING_ACTION_BUTTON;
    }

/**
 * void prepareFragment - createFragment to be displayed
 */
    public void prepareFragment(){
        //TODO - MovieSelect Fragment is hard-coded in this method
        Log.d("Movies", "MainKeeper.prepareFragment: " + mSortBy);

        if(mBoss.getModel(mSortBy) != null){

            if(mPosterStaff == null){
                mPosterStaff = new PosterStaff(mActivityContext, mBoss.getModel(mSortBy));
            }

            //set list
            mPosterMaid.setListAdapter(mPosterStaff.getPosterAdapter(mPosterListener));

            //get PosterFragment and display
            mFragAssistant.requestFragment(mFragmentManager,
                    mFragmentRegistry.get(NAME_POSTER), NAME_POSTER);
        }
        else{
            //get EmptyFragment and display
            mFragAssistant.requestFragment(mFragmentManager,
                    mFragmentRegistry.get(NAME_EMPTY), NAME_EMPTY);
        }
        /*mFragAssistant.requestFragment(mFragmentManager,
                mFragmentRegistry.get(NAME_INFO), NAME_INFO);*/

    }

/**************************************************************************************************/
    /**
     * ListAdapter requestPosterAdapter() - returns a reference of the ListAdapter used by the Poster
     * Fragment
     * @return ListAdapter - PosterAdapter
     */
    public ListAdapter requestPosterAdapter(){
        //return PosterAdapter for consumption from PosterStaff
        //mPosterAdapter =
        return mPosterStaff.getPosterAdapter(mPosterListener);
    }

    private View.OnClickListener mPosterListener = new View.OnClickListener(){

        private int mPosition;
        public void setPosition(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v) {

            Log.d("Movies", "MainKeeper.mPosterListener: " + v.toString());
            //HashMap<String, Integer> myMap = mPosterStaff.getRealPosterAdapter().getMap();

            //Log.d("Movies", "     map: " + myMap.toString());
            //Integer index = myMap.get(v.toString());
            //Log.d("Movies", "     index: " + index);
            Log.d("Movies", "     tag: " + v.getTag());
            int index = (int)v.getTag();
            MovieJSON.MovieDetail item = mBoss.getModel(mSortBy).getMovie(index);


            Log.d("Movies", "     movie: " + item.getTitle());
            mInfoMaid.setMovie(item);

            //PosterItem posterItem = (PosterItem)mPosterStaff.getRealPosterAdapter().getItem(index);
            //mInfoMaid.setPoster(v.getBackground());

            mFragAssistant.requestDetailFragment(mFragmentManager,
                    mFragmentRegistry.get(NAME_INFO), NAME_POSTER, NAME_INFO);

        }
    };

/**
 * OnClickListener getFABOnClickListener() - get the OnClick Listener for the Floating Action
 * Button object.
 * @return - View.OnClickListener object
 */
    public View.OnClickListener getFABOnClickListener(){
        return mFABOnClickListener;
    }

/**
 * void onOptionsItemSelected(MenuItem) - listens for an onOptionsItemSelected event from the
 * menu list contained in the toolbar view
 *
 * Menu will sort movies by most popular or by highest rated
 *
 * @param item - menu item selected in the toolbar
 */
    public void onOptionsItemSelected(MenuItem item){
        //get id of item selected from ActionBar
        int id = item.getItemId();

        //create variable to hold sort type
        int sortBy;

        //get fragment type selected
        if (id == MENU_ITEM01) {
            //simple fragment list display
            sortBy = MovieButler.MOVIE_REQUEST_MOST_POPULAR;
        }
        else if (id == MENU_ITEM02) {
            //fragment list display with icons
            sortBy = MovieButler.MOVIE_REQUEST_HIGHEST_RATED;
        }
        else {
            sortBy = MovieButler.MOVIE_REQUEST_MOST_POPULAR;
        }

        if(mSortBy != sortBy){
            mSortBy = sortBy;
            mPosterStaff = null;
            prepareFragment();
        }

    }

/**
 * void onFABOnClickListener(View) - listens for an onClick event happening with the Floating
 * Action Button object.
 * @param view - floating action button view that registered the onClick event
 */
    private void onFABOnClickListener(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

/**************************************************************************************************/



    /**
     * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
     * @param position - list position of item clicked
     */
    public void onItemClick(ListView l, View v, int position, long id){
        Log.d("Movies", "MainKeeper.onItemClick");
        MovieJSON model = mBoss.getModel(mSortBy);
        String msg = model.getMovie(position).getTitle();

        //display in long period of time
        Toast.makeText(mActivityContext, msg, Toast.LENGTH_SHORT).show();
    }

    public String getPosterMaidName(){
        return NAME_POSTER;
    }

    public String getEmptyMaidName(){
        return NAME_EMPTY;
    }

}
