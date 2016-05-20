package me.makeachoice.movies.view.activity;

/**************************************************************************************************/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import me.makeachoice.movies.controller.Boss;

/**
 * MyActivity abstract class extends AppCompatActivity.
 *
 * Activity is the base class of all other activities and the relationship among them is:
 *      Activity <-- FragmentActivity <-- AppCompatActivity <-- ActionBarActivity
 *
 * "<--" means inherits from; also ActionBarActivity is depreciated.
 *      Activity - is the base
 *      FragmentActivity - provides the ability to use Fragment
 *      AppComptActivity - provides features to ActionBar
 *
 */
public abstract class MyActivity extends AppCompatActivity {

/**************************************************************************************************/
/**
 * Class Variables
 *      Bridge mBridge - class implementing Bridge interface
 *      int mOrientation - holds orientation status
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mBridge - class implementing Bridge interface
    protected Bridge mBridge;

    //mOrientation - holds orientation status
    protected static int mOrientation;

    //Implemented communication line, usually implemented by a HouseKeeper class
    public interface Bridge{
        //Bridge is called when onCreate(...) is called in the activity
        void create(MyActivity activity, Bundle savedInstanceState);

        //Bridge is called when onPostResume() is called in the activity
        void postResume();

        //Bridge is called when onBackPressed() is called in the activity
        void backPressed(MyActivity activity);

        //Bridge is called when onCreateOptionsMenu(...) is called in the activity
        void createOptionsMenu(MyActivity activity, Menu menu);

        //Bridge is called when onOptionsItemSelected(...) is called in the activity
        void optionsItemSelected(MyActivity activity, MenuItem item);

        //Bridge is called when onSaveInstanceState(...) is called in the activity
        void saveInstanceState(Bundle saveState);
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Abstract Methods:
 *      finishActivity()
 */
/**************************************************************************************************/
/**
 * void finishActivity() closes the Activity
 */
    abstract public void finishActivity();

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class Methods:
 *      void setOrientationChangeFlag(Boss) - sets the orientation change flag for Boss
 */
/**************************************************************************************************/
/**
 * void setOrientationChangeFlag(Boss) - sets the orientation change flag for Boss. This flag
 * is used to handle issues with the double calling of onCreateView() method in Fragments
 * @param boss - Boss class
 */
    protected void setOrientationChangeFlag(Boss boss, int orientation){

        //check previous orientation value
        if(mOrientation == 0){
            //activity just started, orientation has not been set, save new orientation
            mOrientation = orientation;

            //orientation flag is set false, activity just started
            boss.setOnOrientationChange(false);
        }
        else if(mOrientation != orientation){
            //orientation has changed, save new orientation
            mOrientation = orientation;

            //orientation flag is set true, orientation has changed
            boss.setOnOrientationChange(true);
        }
        else{
            //orientation has Not changed, another
            boss.setOnOrientationChange(false);
        }

    }

/**************************************************************************************************/

}
