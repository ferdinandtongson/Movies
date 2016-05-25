package me.makeachoice.movies.view.activity;

import android.os.Bundle;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.helper.SwipeHelper;

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
 *      void setOrientationChangeFlag(Boss)
 *      abstract void finishActivity()
 *
 * Bridge Interface from MyActivity:
 *      void create(Bundle savedInstanceState)
 *      void onSaveInstanceState(Bundle)
 *      void backPressed()
 */

public class SwipeActivity extends MyActivity {

/**************************************************************************************************/
/**
 * Activity LifeCycle calls:
 *      void onCreate(Bundle) - start of Activity lifecycle
 *      void onSaveInstanceState(Bundle) - save instance state to bundle, if any
 *      void onBackPressed() - called when the User press the "Back" button
 */
/**************************************************************************************************/
/**
 * void onCreate(Bundle) -  start of Activity lifecycle when Activity is created or a configuration
 * change (i.e. orientation change). Saves activity context and orientation status to Boss and
 * activates the HouseKeeper for this Activity.
 * @param savedInstanceState - saved instance states.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get Boss Application
        Boss boss = (Boss)getApplicationContext();

        //register Activity context with Boss
        boss.activityCreated(this);

        try {
            //hire HouseKeeper to maintain Activity
            mBridge = (Bridge)boss.hireHouseKeeper(SwipeHelper.NAME_ID);
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

        //notify Bridge that onCreate event occurred
        mBridge.create(this, savedInstanceState);
    }

/**
 * onSaveInstanceState(Bundle) - called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 * @param saveState - bundle object used to save any instance states
 */
    @Override
    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);
        //notify bridge of onSaveInstanceState event
        mBridge.saveInstanceState(saveState);
    }

/**
 * void onBackPressed() - called when the User press the "Back" button
 */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //notify bridge of onBackPressed event
        mBridge.backPressed(this);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MyActivity abstract class:
 *      void finishActivity() - called when user finishes with App
 */
/**************************************************************************************************/
/**
 * void finishActivity - called when user finishes with App. Notifies Boss to do any final
 * clean up then finishes the activity and kills the activity processor.
 */
    public void finishActivity() {
        //get Boss Application
        Boss boss = (Boss) getApplicationContext();

        //notify Boss to do any final clean up of App
        boss.onFinish();

        //close activity
        this.finish();

        //kill activity process
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }


/**************************************************************************************************/

}