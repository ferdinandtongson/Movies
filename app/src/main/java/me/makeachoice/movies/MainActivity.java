package me.makeachoice.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.helper.MainHelper;

/**
 * MainActivity is the main activity of this Movie application and will display a PosterFragment of
 * movie posters to select from and and InfoFragment about a particular movie.
 *
 * MainActivity extends MyActivity which extends AppCompatActivity to allow for Fragment and
 * Toolbar use.
 *
 * Variables from MyActivity:
 *      mBridge
 *
 * Methods from MyActivity:
 *      void finishActivity()
 *
 * Bridge Interface from MyActivity:
 *      void create(Bundle savedInstanceState)
 *      void postResume()
 *      void backPressed()
 *      void createOptionsMenu(Menu menu)
 *      void optionsItemSelected(MenuItem item)
 *
 */
public class MainActivity extends MyActivity {


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

        Log.d("Movies", "MainActivity.onCreate");
        //get Boss Application
        Boss boss = (Boss)getApplicationContext();

        //register Activity context with Boss
        boss.setActivityContext(this);

        try{
            //check if HouseKeeper is implementing interface
            mBridge = (Bridge)boss.getHouseKeeper(MainHelper.NAME_ID);
        }catch(ClassCastException e){
            throw new ClassCastException(boss.toString() +
                    " must implement Bridge interface");
        }

        //use HouseKeeper class to create activity
        mBridge.create(savedInstanceState);
    }

/**
 * onSaveInstanceState(...) is called any time before onDestroy( ) and is where you can save
 * instance states by placing them into a bundle
 * @param saveState - bundle object used to save any instance states
 */
    @Override
    public void onSaveInstanceState(Bundle saveState){
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
    public void onBackPressed(){
        super.onBackPressed();
        mBridge.backPressed();
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
        //send to HouseKeeper to manage creation of toolbar
        mBridge.createOptionsMenu(menu);
        return true;
    }

/**
 * boolean onOptionItemSelected(MenuItem) is called when a menu item is clicked on by the user
 * @param item - menu item clicked on
 * @return boolean - by default returns false
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //send to HouseKeeper to manage event
        mBridge.optionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Implemented abstract methods from MyActivity:
 *      void finishActivity()
 */
/**************************************************************************************************/

    public void finishActivity(){
        //close activity
        this.finish();
    }


/**************************************************************************************************/


}
