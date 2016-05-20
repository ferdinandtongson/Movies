package me.makeachoice.movies.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;


/**
 * WaitDialog is shown during the start up of the application.
 */
public class WaitDialog {

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mStrTitle - string used for AlertDialog Title
 *      String mStrMessage - string used for AlertDialog Message
 *
 *      AlertDialog mStartDialog - alert dialog object
 */
/**************************************************************************************************/

    //mStrTitle - string used for AlertDialog Title
    private String mStrTitle;

    //mStrgMessage - string used for AlertDialog Message
    private String mStrMessage;

    //mStartDialog - alert dialog object
    AlertDialog mStartDialog;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NetworkManager - constructor
 */
    public WaitDialog(){
        //string used for AlertDialog Title
        mStrTitle = "Movie App";

        //string used for AlertDialog Message
        mStrMessage = "Starting application....";

    }

    public void showStartDialog(Context context){
        //initialize AlertDialog builder to build AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //set Title of Dialog
        builder.setTitle(mStrTitle);

        //set Message of Dialog
        builder.setMessage(mStrMessage);

        //set Positive Button text and listener
        builder.setPositiveButton("test", null);

        //set Negative Button text and listener
        builder.setNegativeButton("neg", null);


        //dialog is not cancelable, user must press one of the buttons
        builder.setCancelable(false);

        //create AlertDialog
        mStartDialog = builder.create();

        mStartDialog.show();

    }

/**************************************************************************************************/
/**
 * Getters:
 *      - None -
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Private Class Methods:
 *      void showNoNetworkDialog - AlertDialog telling user there is no network connection
 */
/**************************************************************************************************/
/**
 * void showNoNetworkDialog() - show AlertDialog telling user there is no network connection
 */
    /*public void showStartDialog(){
        Log.d("Boss", "mWaitDialog.showStartDialog");

        //show dialog
        mStartDialog.show();
    }*/

    public void closeStartDialog(){
        if(mStartDialog.isShowing()){
            mStartDialog.dismiss();
        }
    }

/**************************************************************************************************/

}
