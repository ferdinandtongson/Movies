package me.makeachoice.movies.controller.worker;

import android.os.AsyncTask;

import me.makeachoice.movies.controller.butler.MyButler;

/**
 * MyWorker abstract class - used to run threads, network calls or background work to process data
 * before passing it onto the Butler and then general consumption by the View
 */
public abstract class MyWorker extends AsyncTask {
    MyButler mButler;

    protected abstract Object doInBackground(Object[] params);
}
