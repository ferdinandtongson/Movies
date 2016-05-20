package me.makeachoice.movies.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;

/**
 * NetworkManager is in charge of checking for network connectivity and alerting the user if there
 * is Not any.
 */
public class NetworkManager {

/**************************************************************************************************/
/**
 * Class Variables:
 *      Boss mBoss - instance of Boss, Application context
 *      String mStrRetry - string used for AlertDialog Button
 *      String mStrClose - string used for AlertDialog Button
 *      String mStrTitle - string used for AlertDialog Title
 *      String mStrMessage - string used for AlertDialog Message
 *      DialogInterface.OnClickListener mPositiveListener - positive response in AlertDialog
 *      DialogInterface.OnClickListener mNegativeListener - negative response in AlertDialog
 */
/**************************************************************************************************/

    //mBoss - Application context
    private Boss mBoss;

    //mStrRetry - string used for AlertDialog Button
    private String mStrRetry;

    //mStrClose - string used for AlertDialog Button
    private String mStrClose;

    //mStrTitle - string used for AlertDialog Title
    private String mStrTitle;

    //mStrgMessage - string used for AlertDialog Message
    private String mStrMessage;

    //mPositiveListener - positive response in AlertDialog
    DialogInterface.OnClickListener mPositiveListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            if(hasConnection(mBoss.getActivityContext())){
                //TODO - need to handle network reconnected
                //mBoss.updateMainActivity();
            }
        }
    };

    //mNegativeListener - negative response in AlertDialog
    DialogInterface.OnClickListener mNegativeListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            ((Activity)mBoss.getActivityContext()).finish();
        }
    };


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * NetworkManager - constructor
 * @param boss - Boss class
 */
    public NetworkManager(Boss boss){
        //set Boss
        mBoss = boss;

        //string used for AlertDialog button
        mStrRetry = mBoss.getString(R.string.dia_btn_retry);

        //string used for AlertDialog Button
        mStrClose = mBoss.getString(R.string.dia_btn_close);

        //string used for AlertDialog Title
        mStrTitle = mBoss.getString(R.string.msg_no_network);

        //string used for AlertDialog Message
        mStrMessage = mBoss.getString(R.string.msg_enable_network);

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
 * Public Class Methods:
 *      boolean hasConnection() - check if phone has network connection
 */
/**************************************************************************************************/
/**
 * boolean hasConnection() - check if phone has network connection. If not it will notify the
 * user of the situation.
 * @return - status of network connection, true or false
 */
    public static boolean hasConnection(Context context){

        //get Connectivity Manger
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get access to network information from phone
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //check if we have connection
        if(networkInfo != null && networkInfo.isConnected()) {
            //we have connection, return true
            return true;
        }
        else{
            //we have no network connection, return false
            return false;
        }

    }

/**************************************************************************************************/
/**
 * Private Class Methods:
 *      void showNoNetworkDialog - AlertDialog telling user there is no network connection
 */
/**************************************************************************************************/
/**
 * void showNoNetworkDialog() - show AlertDialog telling user there is no network connection
 */
    public void showNoNetworkDialog(){

        //initialize AlertDialog builder to build AlertDialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(mBoss.getActivityContext());

        //set Title of Dialog
        builder.setTitle(mStrTitle);

        //set Message of Dialog
        builder.setMessage(mStrMessage);

        //set Positive Button text and listener
        builder.setPositiveButton(mStrRetry, mPositiveListener);

        //set Negative Button text and listener
        builder.setNegativeButton(mStrClose, mNegativeListener);

        //dialog is not cancelable, user must press one of the buttons
        builder.setCancelable(true);

        //create AlertDialog
        AlertDialog alertDialog = builder.create();

        //show dialog
        alertDialog.show();
    }

    private AlertDialog mDialog;

/**************************************************************************************************/

}
