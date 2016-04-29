package me.makeachoice.movies.model.item;

/**
 * Created by Usuario on 4/29/2016.
 */
public class GenreItem {
    private int tmdbId;
    private String name;

    public int getTMDBId(){ return tmdbId; }
    public void setTMDBId(int id){ tmdbId = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
}
