package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Usuario on 4/23/2016.
 */
public class CastModel {
    @SerializedName("cast_id")
    public int castId;

    public String character;

    @SerializedName("credit_id")
    public String creditId;

    public int id;
    public String name;

    public int order;

    @SerializedName("profile_path")
    public String profilePath;
}
