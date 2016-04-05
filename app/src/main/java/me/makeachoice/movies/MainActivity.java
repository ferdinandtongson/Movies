package me.makeachoice.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;

/**
 * MainActivity is the main activity of this application.
 */
public class MainActivity extends AppCompatActivity {

    //MyHouseKeeper class that handles the initialization of the MainActivity and Fragments as well
    //as the communication between UI and the Boss
    private static MainKeeper mHouseKeeper;

    //Toolbar object used by the MainActivity
    private Toolbar mToolbar;
    //Floating Action Button used by the MainActivity
    private FloatingActionButton mFloatButton;

    public interface Bridge{
        //Interface are methods the MyMaid has to implement but it is a one-way
        //communication.
        int getActivityLayoutId();
        int getToolbarId();
        int getMenuId();
        int getFloatingActionButtonId();

        void prepareFragment();
        void setFragmentManager(FragmentManager manager);

        View.OnClickListener getFABOnClickListener();
        void onOptionsItemSelected(MenuItem item);
    }


/**************************************************************************************************/
/**
 * void onCreate() is called when the Activity is first being created or during a configuration
 * change (i.e. orientation change). Creates Boss and MyHouseKeeper class, inflates the Activity
 * layout and the toolbar and floating action button (if any).
 *
 * If onCreate is being called because of a configuration change, savedInstanceState will not be
 * null.
 * @param savedInstanceState - saved instance states saved before the fragment was detached.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Movies", "MainActivity.onCreate");
        //start Boss
        mBoss = (Boss)getApplicationContext();
        //register Activity context with Boss
        mBoss.setActivityContext(this);

        if(hasConnectivity(this)){
            if(mBoss.getHouseKeeper(MainKeeper.NAME) == null){
                //start MyHouseKeeper for this Activity
                mHouseKeeper = new MainKeeper(mBoss);
            }

            //Note setContent must happen before toolbar
            setContentView(mHouseKeeper.getActivityLayoutId());

            //TODO - passing of Activity Context is brittle!!!
            //send Activity Context to HouseKeeper
            mHouseKeeper.setActivity(this);

            //send FragmentManger to HouseKeeper, a new FragmentManger is created at onCreate()
            mHouseKeeper.setFragmentManager(getSupportFragmentManager());

            //set flag so fragment waits until Activity is Resumed, onPostResume changes flag
            mHouseKeeper.isSafeToCommitFragment(false);

            //Create toolbar with creation of Activity
            initToolbar();
        }
        else{
            //TODO - need to code here
        }
    }

    public void onStart(){
        super.onStart();
        mHouseKeeper.isSafeToCommitFragment(false);
    }

    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);

    }

    @Override
    public void onPostResume(){
        super.onPostResume();
        mHouseKeeper.onActivityPostResume();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mHouseKeeper.onBackPressed();
    }

    @Override
    public void onPause(){
        super.onPause();
        mHouseKeeper.isSafeToCommitFragment(false);
    }

    private static Boss mBoss;
    public void closeApp(){
        Log.d("Movies", "MainActivity.closeApp");
        mBoss.clearMovies();
        this.finish();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * boolean onCreateOptionsMenu(Menu) is called if an action bar is present
 * @param menu - action bar menu object
 * @return boolean - by default returns true
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(mHouseKeeper.getMenuId(), menu);
        return true;
    }

/**
 * boolean onOptionItemSelected(MenuItem) is called when a menu item is clicked on by the user
 * @param item - menu item clicked on
 * @return boolean - by default returns false
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //MyHouseKeeper handles menu item click event
        mHouseKeeper.onOptionsItemSelected(item);


        return super.onOptionsItemSelected(item);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * void initToolbar() inflates the toolbar from the layout and then sets it into the Activity.
 */
    public void initToolbar(){
        //check if toolbar is already inflated
        if(mToolbar == null){
            //if null, inflate the toolbar
            mToolbar = (Toolbar)findViewById(mHouseKeeper.getToolbarId());
            //set support for toolbar, onCreateOptionsMenu() will be called
            setSupportActionBar(mToolbar);
        }
    }

/**
 * void initFloatButton() inflates the floating action button layout and sets Event Listeners
 */
    public void initFloatButton(){

        if(mFloatButton == null){
            mFloatButton = (FloatingActionButton)findViewById
                    (mHouseKeeper.getFloatingActionButtonId());
            mFloatButton.setOnClickListener(mHouseKeeper.getFABOnClickListener());
        }
    }
/**************************************************************************************************/

    private boolean hasConnectivity(Context ctx){
        //get Connectivity Manger
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get access to network information from phone
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //check if we have connection
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else{
            return false;
        }
    }


}
