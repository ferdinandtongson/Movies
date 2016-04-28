package me.makeachoice.movies.model.item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.makeachoice.movies.model.response.tmdb.GenreModel;

/**
 * Created by Usuario on 4/22/2016.
 */
public class MovieItem {
    int id;
    String title;
    String overview;
    String releaseDate;
    String imbdId;


    String originalTitle;
    String originalLanguage;


    float popularity;
    float voteCount;
    float voteAverage;


    String posterPath;
    String backdropPath;
    Boolean video;
    String homepage;                    //specific movie request


    Boolean adult;
    Integer[] genreIds;                 //called during a movie list request
    ArrayList<GenreModel> genres;       //called during a request for a specific movie


    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getOverview(){
        return overview;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getImbdId() {return imbdId; }



    public String getOriginalTitle(){
        return originalTitle;
    }

    public String getOriginalLanguage(){
        return originalLanguage;
    }




    public float getPopularity(){
        return popularity;
    }

    public float getVoteCount(){
        return voteCount;
    }

    public float getVoteAverage(){
        return voteAverage;
    }



    public String getPosterPath(){ return posterPath; }

    public String getBackdropPath(){
        return backdropPath;
    }

    public Boolean getVideo(){
        return video;
    }

    public String getHomepage() { return homepage; }



    public Boolean getAdult(){
        return adult;
    }

    public Integer[] getGenreIds(){
        return genreIds;
    }

    public ArrayList<GenreModel> getGenres(){
        return genres;
    }
}
