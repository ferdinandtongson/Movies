package me.makeachoice.movies.controller.worker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.butler.MyButler;

/**
 * HttpWorker - handles Http request and any other Http related tasks
 */
public class HttpWorker  extends MyWorker{

    public HttpWorker(MyButler butler){
        mButler = butler;
    }

    public boolean hasConnectivity(){
        Log.d("Movies", "HttpWorker.hasConnectivity");
        Context ctx = mButler.getActivityContext();
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            Log.d("Movies", "     true - has connection");
            return true;
        }
        else{
            Log.d("Movies", "     false - no connection");
            return false;
        }

    }
}
