package me.makeachoice.movies.model.item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.makeachoice.movies.model.response.tmdb.GenreModel;

/**
 * Created by Usuario on 4/22/2016.
 */
public class MovieItem {
    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private String imbdId;
    private String homepage;                    //specific movie request

    private String originalTitle;
    private String originalLanguage;

    private float popularity;
    private Integer voteCount;
    private float voteAverage;

    private String posterPath;
    private String backdropPath;
    private Boolean video;


    private Boolean adult;
    private Integer[] genreIds;                 //called during a movie list request
    private ArrayList<GenreItem> genres;       //called during a request for a specific movie
    private ArrayList<CastItem> cast;


    public int getId(){
        return id;
    }
    public void setId(int id){ this.id = id; }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){ this.title = title; }

    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview){ this.overview = overview; }

    public String getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(String date){ this.releaseDate = date; }

    public String getImbdId() {return imbdId; }
    public void setImbdId(String id){ this.imbdId = id; }

    public String getHomepage() { return homepage; }
    public void setHomepage(String url){ this.homepage = url; }



    public String getOriginalTitle(){
        return originalTitle;
    }
    public void setOriginalTitle(String title){ this.originalTitle = title; }

    public String getOriginalLanguage(){
        return originalLanguage;
    }
    public void setOriginalLanguage(String language){ this.originalTitle = language; }




    public float getPopularity(){
        return popularity;
    }
    public void setPopularity(float popularity){ this.popularity = popularity; }

    public Integer getVoteCount(){
        return voteCount;
    }
    public void setVoteCount(Integer count){this.voteCount = count; }

    public float getVoteAverage(){
        return voteAverage;
    }
    public void setVoteAverage(float average){this.voteAverage = average; }



    public String getPosterPath(){ return posterPath; }
    public void setPosterPath(String path){ this.posterPath = path; }

    public String getBackdropPath(){
        return backdropPath;
    }
    public void setBackdropPath(String path){ this.backdropPath = path; }

    public Boolean getVideo(){
        return video;
    }
    public void setVideo(boolean hasVideo){this.video = hasVideo; }



    public Boolean getAdult(){
        return adult;
    }
    public void setAdult(boolean isAdult){this.adult = isAdult; }

    public Integer[] getGenreIds(){
        return genreIds;
    }
    public void setGenreIds(Integer[] ids){ this.genreIds = ids; }

    public ArrayList<GenreItem> getGenres(){
        return genres;
    }
    public void setGenres(ArrayList<GenreItem> genres){ this.genres = genres; }

    public ArrayList<CastItem> getCast(){ return cast; }
    public void setCast(ArrayList<CastItem> cast){ this.cast = cast; }
}
