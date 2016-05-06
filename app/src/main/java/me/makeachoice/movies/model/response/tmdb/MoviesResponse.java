package me.makeachoice.movies.model.response.tmdb;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Usuario on 4/22/2016.
 */
public class MoviesResponse {

    public int page;

    @SerializedName("results")
    public ArrayList<MovieModel> movies;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("total_pages")
    public int totalPages;


    public MoviesResponse(){
        movies = new ArrayList<>();
    }





}
