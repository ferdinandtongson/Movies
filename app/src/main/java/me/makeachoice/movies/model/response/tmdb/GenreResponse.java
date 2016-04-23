package me.makeachoice.movies.model.response.tmdb;

import android.util.Log;

import java.util.ArrayList;

import me.makeachoice.movies.model.response.tmdb.GenreModel;

/**
 * Created by Usuario on 4/22/2016.
 */
public class GenreResponse {
    public ArrayList<GenreModel> genres;

    public GenreResponse(){
        Log.d("Movies", "tmdbArray - constructor");
        genres = new ArrayList<>();
    }
}
