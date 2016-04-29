package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Usuario on 4/23/2016.
 */
public class SimilarResponse {

    @SerializedName("results")
    public ArrayList<MovieModel> movies;

    public SimilarResponse(){
        movies = new ArrayList<>();
    }

}
