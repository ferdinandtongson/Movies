package me.makeachoice.movies.model.response.tmdb;

import java.util.ArrayList;

/**
 * Created by Usuario on 4/23/2016.
 */
public class CreditsResponse {
    public int id;
    public ArrayList<CastModel> cast;
    public ArrayList<CrewModel> crew;

    public CreditsResponse(){
        cast = new ArrayList<>();
        crew = new ArrayList<>();
    }
}
