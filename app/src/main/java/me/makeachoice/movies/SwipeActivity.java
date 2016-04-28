package me.makeachoice.movies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.helper.SwipeHelper;

/**
 * SwipeActivity allows users to swipe through a various list of Posters set in a GridView fragment.
 * If a user click on an poster, it will activate a new activity that will display the details of
 * the movie selected.
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
 * SwipeActivity extends MyActivity which extends AppCompatActivity to allow for Fragment and
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
 */

public class SwipeActivity extends MyActivity {

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

        //get Boss Application
        Boss boss = (Boss) getApplicationContext();

        //register Activity context with Boss
        boss.activityCreated(this);

        try {
            //check if HouseKeeper is implementing interface
            mBridge = (Bridge) boss.getHouseKeeper(SwipeHelper.NAME_ID);
        } catch (ClassCastException e) {
            throw new ClassCastException(boss.toString() +
                    " must implement Bridge interface");
        }

        //set orientation flag in Boss class
        setOrientationChangeFlag(boss);

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
    public void onPostResume() {
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
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }


/**************************************************************************************************/

}