package me.makeachoice.movies.controller.worker;

import android.os.AsyncTask;

import me.makeachoice.movies.controller.butler.MyButler;

/**
 * MyWorker abstract class - used to run threads, network calls or background work to process data
 * before passing it onto the Butler and then general consumption by the View
 *
 * MyWorker which extends AsyncTask<Params, Progress, Result>. When extending AsyncTask, you can
 * modify the type of variables that are received in doInBackground(), onProgressUpdate() and
 * onPostExecute(). For example:
 *      AsynTask<String, Integer, Boolean> will result in method calls
 *      doInBackground(String... params), onProgressUpdate(Integer... values) and
 *      onPostExecute(Result result)
 *
 * doInBackground(String... params); String... means that the doInBackground method can receive
 * an undefined number of string variable and it will place those variables into a dynamic String
 * array. So we can call doInBackground("x1", "x2", "x3") and process the variables by using
 * params[0], params[1] and params[2].
 *
 * For access to Network information or using the internet, we need to modify the AndroidManifest
 * file to contain permissions, this is placed outside of <application></application>:
 *      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *      <uses-permission android:name="android.permission.INTERNET" />

 *
 */
public abstract class MyWorker extends AsyncTask<String, Integer, Boolean> {

    //mButler - MyWorker talks directly to the Butler
    MyButler mButler;

    //abstract method from AsyncTask, see notes above about AsyncTask<Params, Progress, Result>
    //doInBackground - runs in background thread
    protected abstract Boolean doInBackground(String... params);
    //onProgressUpdate - runs in UI thread
    protected abstract void onProgressUpdate(Integer... values);
    //onPostExecute - runs in UI thread
    protected abstract void onPostExecute(Boolean result);
}
