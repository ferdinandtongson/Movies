package me.makeachoice.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.helper.DetailHelper;

/**
 * DetailActivity allows the user to swipe through Detailed information about a selected movie. It
 * allows the user to see general information, credits, reviews, and trailers about the movie.
 *
 * If HouseKeeper initializes Toolbar, onCreateOptionsMenu(Menu) will be called and an Options
 * Menu listener will be automatically created which will call onOptionsItemSelected(MenuItem) if
 * a menu in the toolbar is selected.
 *
 * Fragment Notes:
 * onPostResume() is called when the activity and fragments have all resumed. Bridge.postResume()
 * is used to signal to the HouseKeeper that it is now save to update the fragment with new data.
 *
 * onBackPressed() is called when the activity receives a backPress event from the phone.
 * Bridge.backPressed() is used to signal to the HouseKeeper that this event has happened and to
 * take appropriate action if necessary. Also, onBackPressed() automatically handles popping the
 * back stack of the fragment stack and HouseKeeper is signalled to be aware of that event.
 *
 * DetailActivity extends MyActivity which extends AppCompatActivity to allow for Fragment and
 * Toolbar use.
 *
 * Variables from MyActivity:
 *      static int mOrientation
 *      Bridge mBridge
 *
 * Methods from MyActivity:
 *      setOrientationChangeFlag(Boss)
 *      abstract void finishActivity()
 *
 * Bridge Interface from MyActivity:
 *      void create(Bundle savedInstanceState)
 *      void postResume()
 *      void backPressed()
 *      void createOptionsMenu(Menu menu)
 *      void optionsItemSelected(MenuItem item)
 *
 */

public class DetailActivity extends MyActivity {

/**************************************************************************************************/
/**
 * void onCreate() is called when the Activity is first being created or during a configuration
 * change (i.e. orientation change). Creates Boss and Bridge class, inflates the Activity
 * layout and the toolbar and floating action button (if any).
 *
 * @param savedInstanceState - saved instance states saved before the fragment was detached.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Movies", "DetailActivity.onCreate");

        //get Boss Application
        Boss boss = (Boss)getApplicationContext();

        //register Activity context with Boss
        boss.activityCreated(this);

        try {
            //check if HouseKeeper is implementing interface
            mBridge = (Bridge) boss.getHouseKeeper(DetailHelper.NAME_ID);
        } catch (ClassCastException e) {
            throw new ClassCastException(boss.toString() +
                    " must implement Bridge interface");
        }

        //get current orientation of phone (portrait = 1, landscape = 2)
        int orientation = getResources().getConfiguration().orientation;

        //send orientation status to boss
        boss.setOrientation(orientation);

        //set orientation flag in Boss class and mOrientation
        setOrientationChangeFlag(boss, orientation);

        //use HouseKeeper class to create activity
        mBridge.create(this, savedInstanceState);
    }

/**
 * onSaveInstanceState(...) is called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 *
 * @param saveState - bundle object used to save any instance states
 */
    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        mBridge.saveInstanceState(saveState);
    }

/**
 * void onPostResume() is called after the activity and fragments have all resumed. Fragments
 * are resumed with the activity's onResume() method but they are not guaranteed to have
 * been resumed. Another approach is to use onResumeFragment()
 */
    @Override
    public void onPostResume(){
        super.onPostResume();
        //signal to HouseKeeper that Activity and Fragments have resumed
        mBridge.postResume();
    }

/**
 * void onBackPressed() is called when the User press the "Back" button
 */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBridge.backPressed(this);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * boolean onCreateOptionsMenu(Menu) is called if an action bar is present
 *
 * @param menu - action bar menu object
 * @return boolean - by default returns true
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //send to HouseKeeper to manage creation of toolbar
        mBridge.createOptionsMenu(this, menu);
        return true;
    }

/**
 * boolean onOptionItemSelected(MenuItem) is called when a menu item is clicked on by the user
 *
 * @param item - menu item clicked on
 * @return boolean - by default returns false
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //send to HouseKeeper to manage event
        mBridge.optionsItemSelected(this, item);

        return super.onOptionsItemSelected(item);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Implemented abstract methods from MyActivity:
 *      void finishActivity()
 */
/**************************************************************************************************/

    public void finishActivity() {
        //close activity
        this.finish();
    }


/**************************************************************************************************/

}
