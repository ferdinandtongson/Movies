package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Usuario on 4/23/2016.
 */
public class ReviewsResponse {
    public int id;

    @SerializedName("results")
    public ArrayList<ReviewModel> reviews;

    public ReviewsResponse(){
        reviews = new ArrayList<>();
    }
}
