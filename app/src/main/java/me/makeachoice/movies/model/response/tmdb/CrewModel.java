package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Usuario on 4/23/2016.
 */
public class CrewModel {
    @SerializedName("credit_id")
    public String creditId;
    public String department;

    public int id;
    public String job;
    public String name;

    @SerializedName("profile_path")
    public String profilePath;
}
