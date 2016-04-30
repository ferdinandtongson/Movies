package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by Usuario on 4/22/2016.
 */
public class MovieModel {
    private int id;
    private String title;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("imbd_id")
    private String imbdId;
    private  String homepage;                     //called during a request for a specific movie


    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;


    private float popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("vote_average")
    private float voteAverage;


    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private Boolean video;


    private Boolean adult;
    @SerializedName("genre_ids")
    private Integer[] genreIds;                       //called during a movie list request
    private ArrayList<GenreModel> genres;      //called during a request for a specific movie

    @SerializedName("videos")
    private VideosResponse videoResult;       //called during a request for a specific movie
    @SerializedName("reviews")
    private ReviewsResponse reviewResult;     //called during a request for a specific movie
    @SerializedName("similar")
    private SimilarResponse similarResult;     //called during a request for a specific movie
    @SerializedName("credits")
    private CreditsResponse creditResult;     //called during a request for a specific movie


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

    public String getIMBDId(){ return imbdId; }

    public String getHomepage(){ return homepage; }



    public String getOriginalTitle(){
        return originalTitle;
    }

    public String getOriginalLanguage(){
        return originalLanguage;
    }




    public float getPopularity(){
        return popularity;
    }

    public Integer getVoteCount(){
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



    public Boolean getAdult(){
        return adult;
    }

    public Integer[] getGenreIds(){
        return genreIds;
    }

    public ArrayList<GenreModel> getGenres(){ return genres; }


    public ArrayList<CastModel> getCast(){ return creditResult.cast; }

    public ArrayList<CrewModel> getCrew(){ return creditResult.crew; }

    public ArrayList<ReviewModel> getReviews(){ return reviewResult.getReviews(); }

    public ArrayList<VideoModel> getVideos(){ return videoResult.getVideos(); }

}
