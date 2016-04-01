package me.makeachoice.movies.controller.housekeeper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import me.makeachoice.movies.MainActivity;
import me.makeachoice.movies.R;
import me.makeachoice.movies.adapter.MoviePosterAdapter;
import me.makeachoice.movies.adapter.item.MovieItem;
import me.makeachoice.movies.model.MovieModel;
import me.makeachoice.movies.controller.Boss;

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
public class MainKeeper extends MyHouseKeeper implements MainActivity.Bridge{
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
 *      int getFragmentType(String);
 *      void setFragmentType(String, int);
 *
 *      void createFragment(Boolean);
 *
 *      void onOptionsItemSelected(MenuItem item);
 *      void setFABOnClickListener();
 *
 */
/**************************************************************************************************/
    //NAME - unique name of the MyHouseKeeper
    public final static String NAME = "MainKeeper";

    //mMovieSelectFrag - application movie select fragment, where user can select movie to look at
    private Fragment mMovieSelectFrag;
    //mMovieInfoFrag - application demo information fragment, where user can see info about movie
    private Fragment mMovieInfoFrag;

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

    public MainKeeper(Boss boss, Context ctx, FragmentManager manager){
        mBoss = boss;
        mActivityContext = ctx;
        mFragmentManager = manager;

        mBoss.registerHouseKeeper(NAME, this);
        //TODO - need to be able to save user default fragment select type
        mMapFragmentType = new HashMap<>();

        initHouseKeeping();
    }

/**************************************************************************************************/
/**
 * MovieSelectMaid is in charge of taking care of displaying thumbnail icon images of movies in a
 * grid fragment. It will maintain all events or requests called by the fragment and will push
 * these events or requests up to the MyHouseKeeper if the MyMaid cannot handle it.
 *
 * MovieInfoMaid is in charge of displaying information about a particular movie selected by the
 * user. It will maintain all events or requests called by the fragment and will push these events
 * or requests up to the MyHouseKeeper if the MyMaid cannot handle it.
 */
/**************************************************************************************************/
    //TODO - mMovieSelectMaid - maid in charge of the grid fragment displaying a selection of movies
    //private MovieSelectMaid mMovieSelectMaid;
    //TODO - mMovieInfoMaid - maid in charge of the fragment displaying info about a particular movie
    //private MovieInfoMaid mMovieInfoMaid

    //NAME_APP_SELECT_MAID - name registered to the Boss for AppSelectMaid
    private final static String NAME_MOVIE_SELECT_MAID = "MovieSelectMaid";
    //NAME_APP_INFO_MAID - name registered to the Boss for AppInfoMaid
    private final static String NAME_MOVIE_INFO_MAID = "MovieInfoMaid";

    //mMovieSelectAdapter - list adapter of of movie thumbnail icons
    ListAdapter mMovieSelectAdapter;

/**************************************************************************************************/
/**
 * void initHouseKeeping() - initializes the Maids that will take care of the fragments that
 * will be added to MainKeepers' Activity.
 *
 * HouseKeeping:
 *      MovieSelectMaid
 *      MovieInfoMaid
 */
    private void initHouseKeeping(){
        //initialize MyMaid in charge of the movie selection fragment (grid fragment)
        //initMovieSelectMaid();

        //initialize MyMaid in charge of displaying info about a movie (fragment)
        initMovieInfoMaid();
    }

/**
 * void initMovieSelectMaid() - initialize MovieSelectMaid and register MyMaid to Boss
 */
    private void initMovieSelectMaid(){
        //TODO - initialize and register MovieSelectMaid
        //mMovieSelectMaid = new MovieSelectMaid(this);
        //mBoss.registerMaid(NAME_MOVIE_SELECT_MAID, mMovieSelectMaid);
    }

    private void createMovieSelectAdapter(){
        //TODO - initialize and send ListAdapter to MyMaid
        mMovieSelectAdapter = initializeAdapter(mBoss.getModel());
        //mMovieSelectMaid.setListAdapter(mMovieSelectAdapter);
    }

/**
 * void intitAppInfoMaid() - initialize AppInfoMaid and register MyMaid to Boss
 */
    private void initMovieInfoMaid(){
        //TODO - initialize and register MovieInfoMaid
        //mMovieInfoMaid = new MovieInfoMaid(this);
        //mBoss.registerMaid(NAME_MOVIE_INFO_MAID, mMovieInfoMaid);
    }

/**************************************************************************************************/
/**
 * MoviePosterAdapter - ListAdapter which displays a list of movies posters. MovieListAdapter uses
 * the following resource ids:
 *      LAYOUT_ITEM_MOVIE_
 *      ITEM_MOVIE_CHILD_POSTER_VIEW
 *
 */
/**************************************************************************************************/
    //LAYOUT_ITEM_MOVIE - item layout id used by MoviePosterAdapter
    private final static int LAYOUT_ITEM_MOVIE = R.layout.item_movie;

    //Child View ids from Item Layouts above
    private final static int ITEM_MOVIE_CHILD_POSTER_VIEW = R.id.item_poster;

/**************************************************************************************************/
/**
 * ListAdapter initializeAdapter() initializes the ListAdapter(s) that can be used by the
 * MovieSelect fragment.
 * @return ListAdapter - will return a reference to the ListAdapter to be consumed.
 */
    public ListAdapter initializeAdapter(MovieModel model){

        //ListAdapter variable to be return
        ListAdapter adapter;

        //GridView fragment, initialize an Icon type adapter using the data model for the apps
        adapter = initMoviePosterAdapter(model, LAYOUT_ITEM_MOVIE, ITEM_MOVIE_CHILD_POSTER_VIEW);

        return adapter;
    }

/**
 * ListAdapter initMoviePosterAdapter(model, int, int) - initialize the Movie Poster adapter that
 * will be used by the GridView fragment. Uses MovieModel.
 * @param model - data model for the movies
 * @return ListAdapter - will return a reference to the MoviePoster adapter create with the model
 */
    private ListAdapter initMoviePosterAdapter(MovieModel model, int layoutId, int posterViewId){
        //create an ArrayList to hold the list items to be consumed by the ListAdapter
        ArrayList<MovieItem> itemList = new ArrayList<>();

        //number of Movie data models
        int count = model.getAppCount();

        //loop through the data models
        for(int i = 0; i < count; i++){
            //add items to ArrayList<T>
            MovieItem item = new MovieItem(model.getMovie(i).getPoster());

            //add item into array list
            itemList.add(item);
        }

        //instantiate MoviePosterAdapter with layout id found in res/layout and the child
        MoviePosterAdapter adapter = new MoviePosterAdapter(mActivityContext, itemList,
                layoutId, posterViewId);
        //TODO - setOnClickListener
        //adapter.setOnClickListener(mAppListOnClickListener);

        return adapter;
    }

/**
 * ListAdapter getListAdapter() - returns a reference of the ListAdapter used by the MovieSelect
 * Fragment
 * @return ListAdapter - adapter consumed by MovieSelect Fragment
 */
    public ListAdapter getListAdapter(){
        //check if ListAdapter for MovieSelect Fragment is null
        if(mMovieSelectAdapter == null){
            //initialize adapter for MovieSelect Fragment
            mMovieSelectAdapter = initializeAdapter(mBoss.getModel());
        }

        //return MovieSelect Fragment
        return mMovieSelectAdapter;
    }


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

    //Type of sorting order used to display movies
    private int SORT_TYPE_POPULAR = 0;
    private int SORT_TYPE_HIGHEST_RATED = 1;

    //mSortType - holds sort type
    private int mSortType;

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
 *      int getFragmentContainerId();
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
 * int getFragmentContainerId() - get fragment container id, a framelayout found inside the xml
 * layout file used by the Activity to be used as a container to load fragments
 * @return int - framelayout id,
 */
    public int getFragmentContainerId(){
        return LAYOUT_MAIN_CONTAINER;
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
 * void setFragmentType(String, int) - put the key and fragment type into a hashmap
 * @param fragmentType - variation of the fragment type to display
 */
    public void setFragmentType(String key, int fragmentType){
        Log.d("Simple", "MainKeeper.setFragmentType: " + fragmentType);
        //put fragment type and the variation of that time to a hashmap
        mMapFragmentType.put(key, fragmentType);
    }

/**
 * int getFragmentType(String) - returns the variation of a particular fragment type
 * @param key - fragment type (list or info)
 * @return int - variation of the fragment tpe
 */
    public int getFragmentType(String key){
        return mMapFragmentType.get(key);
    }

/**
 * void prepareFragment - createFragment to be displayed
 * @param shouldAdd - used to determine if it should be added to the Fragment manager
 */
    public void prepareFragment(Boolean shouldAdd){
        //TODO - MovieSelect Fragment is hard-coded in this method
        Log.d("Simple", "Keeper.createFragment");
        //create the ListAdapter to be displayed by the MovieSelect Fragment
        createMovieSelectAdapter();

        //initialize the MovieSelectFragment
        mMovieSelectFrag = initFragment(mMapFragmentType.get(SELECT_TYPE_GRID));

        //check if Fragment needs to be added to the Fragment manager
        if(shouldAdd){
            //add fragment to manager
            addFragmentToManager(mMovieSelectFrag);
        }

    }

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
        int sortType;

        //get fragment type selected
        if (id == MENU_ITEM01) {
            //simple fragment list display
            sortType = SORT_TYPE_POPULAR;
        }
        else if (id == MENU_ITEM02) {
            //fragment list display with icons
            sortType = SORT_TYPE_HIGHEST_RATED;
        }
        else {
            sortType = SORT_TYPE_POPULAR;
        }

        if(mSortType != sortType){
            mSortType = sortType;
            //TODO - need to send message up to boss about new sort order
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

/**************************************************************************************************/
/**
 * Variables used for initializing Fragments
 */
    //simple grid fragment layout
    private final static int LAYOUT_GRID_SIMPLE = R.layout.grid_fragment;
    //child view in grid fragment layout, gridView child
    private final static int GRID_CHILD_GRID_VIEW = R.id.gridview;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Fragment initFragment(int) - initialize the type of Fragment being requested.
 * @param fragmentType - fragment type
 * @return Fragment - returns an initialized and ready fragment
 */
    private Fragment initFragment(int fragmentType){
        Fragment fragment;

        //check fragment type being requested
        if(fragmentType == LAYOUT_GRID_SIMPLE){
            //TODO - create Fragment with GridView, class extends Fragment
            //fragment = new SimpleGridFragment();

            //TODO - set layout id to use to inflate fragment
            //((SimpleGridFragment)fragment).setLayout(LAYOUT_GRID_SIMPLE);

            //TODO - set gridView id to be used in fragment
            //((SimpleGridFragment)fragment).setGridViewId(GRID_CHILD_GRID_VIEW);

            //TODO - setMaid name to fragment
            //((SimpleGridFragment)fragment).setServiceName(NAME_APP_SELECT_MAID);

        }
        else{
            //TODO - handle other fragment type
            fragment = new Fragment();
        }

        //TODO - return fragment
        return null;
    }

/**
 * void addFragmentToManager(Fragment) - adds fragment to FragmentManager and commit to activity
 * @param fragment - fragment object to be added
 */
    private void addFragmentToManager(Fragment fragment){
        //begin fragment transaction
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        //TODO - need to add animation for Fragment transistion

        //add fragment to the fragment container
        ft.add(LAYOUT_MAIN_CONTAINER, fragment);

        //commit fragment to activity
        ft.commit();
    }

/**
 * void replaceFragmentInManager(int) - replaces a fragment object held by the FragmentManager
 * and commit to activity
 */
    private void replaceFragmentInManager(Fragment fragment){
        Log.d("Simple", "MainKeeper.changeFragment");
        //prepare AppSelect fragment, do NOT add fragment to fragment manager, shouldAdd = false
        prepareFragment(false);

        //begin fragment transaction
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        //replace fragment held by the FragmentManager
        ft.replace(LAYOUT_MAIN_CONTAINER, fragment);

        //commit fragment to activity
        ft.commit();
    }

/**************************************************************************************************/


    /**
     * void onItemClick(int) - event listener call by the fragment when an app item has been clicked
     * @param position - list position of item clicked
     */
    public void onItemClick(ListView l, View v, int position, long id){
        MovieModel model = mBoss.getModel();
        String msg = model.getMovie(position).getTitle();

        //display in long period of time
        Toast.makeText(mActivityContext, msg, Toast.LENGTH_SHORT).show();
    }

}
