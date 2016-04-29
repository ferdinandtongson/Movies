package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * VideoResponse is used by Gson to parse Video data information from TheMovieDB api
 *
 * API Call to TMDB:
 *      http://api.themoviedb.org/3/movie/movieID/videos?api_key=xxx (movieID = id number of movie)
 *      http://api.themoviedb.org/3/movie/157336/videos?api_key=xxx
 * JSON Response:
 *      root{1}
 *          array{2}
 *              id:
 *              results[]
 *
 */
public class VideosResponse {

/**************************************************************************************************/
/**
 * Class Variables:
 *      int id - TMDB id number of the movie
 *      ArrayList<VideoModel> - list of VideoModel data
 *
 * NOTE: Variable names need to match the JSON response for GSON to properly map the data into
 * VideoResponse. If using a different variable name, use @SerializedName("")
 */
/**************************************************************************************************/

    //id - TMDB id number of movie
    private int id;

    //videos - VideoModel
    @SerializedName("results")
    private ArrayList<VideoModel> videos;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * VideoResponse - constructor, initialize ArrayLists (potential bug if not initialized)
 */
    public VideosResponse(){
        videos = new ArrayList<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      int getId() - get movie id
 *      ArrayList<VideoModel> getVideos() - get Video data of a given movie
 */
/**************************************************************************************************/
/**
 * int getId() - get movie id
 * @return - id number of movie
 */
    public int getId(){ return id; }

/**
 * ArrayList<VideoModel> getVideos() - get Video data of a given movie
 * @return - video data of a given movie
 */
    public ArrayList<VideoModel> getVideos(){ return videos; }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      - None -
 */
/**************************************************************************************************/

    //setter methods

/**************************************************************************************************/

}
