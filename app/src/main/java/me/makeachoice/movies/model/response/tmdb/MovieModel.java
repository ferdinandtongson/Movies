package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.makeachoice.movies.controller.butler.uri.TMDBUri;

/**
 * Created by Usuario on 4/22/2016.
 */
public class MovieModel {
    int id;
    String title;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("imbd_id")
    String imbdId;


    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("original_language")
    String originalLanguage;


    float popularity;
    @SerializedName("vote_count")
    float voteCount;
    @SerializedName("vote_average")
    float voteAverage;


    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("backdrop_path")
    String backdropPath;
    Boolean video;
    String homepage;                    //specific movie request


    Boolean adult;
    @SerializedName("genre_ids")
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
