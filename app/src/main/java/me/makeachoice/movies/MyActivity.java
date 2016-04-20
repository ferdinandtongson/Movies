package me.makeachoice.movies;

/**************************************************************************************************/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
 *
 * Interface:
 *      Bridge
 */
/**************************************************************************************************/

    //mBridge - class implementing Bridge interface
    protected Bridge mBridge;


    //Implemented communication line, usually implemented by a HouseKeeper class
    public interface Bridge{
        //Bridge is called when onCreate(...) is called in the activity
        void create(Bundle savedInstanceState);

        //Bridge is called when onPostResume() is called in the activity
        void postResume();

        //Bridge is called when onBackPressed() is called in the activity
        void backPressed();

        //Bridge is called when onCreateOptionsMenu(...) is called in the activity
        void createOptionsMenu(Menu menu);

        //Bridge is called when onOptionsItemSelected(...) is called in the activity
        void optionsItemSelected(MenuItem item);

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


}
