package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Usuario on 4/23/2016.
 */
public class VideosResponse {
    public int id;

    @SerializedName("results")
    public ArrayList<VideoModel> videos;

    public VideosResponse(){
        videos = new ArrayList<>();
    }

}
