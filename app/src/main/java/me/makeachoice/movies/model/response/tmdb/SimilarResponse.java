package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * SimilarResponse is used by Gson to parse Movie data similar to the given movie
 *
 * API Call to TMDB:
 *      http://api.themoviedb.org/3/movie/movieID/similar?api_key=xxx (movieID = id number of movie)
 *      http://api.themoviedb.org/3/movie/157336/similar?api_key=xxx
 * JSON Response:
 *      root{1}
 *          array{4}
 *              page:
 *              results[]
 *              total_pages:
 *              total_results:
 *
 */
public class SimilarResponse {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<MovieModel> - list of MovieModels that are similar to the given movie
 *
 * NOTE: Variable names need to match the JSON response for GSON to properly map the data into
 * VideoResponse. If using a different variable name, use @SerializedName("")
 */
/**************************************************************************************************/

    //movies - list of movies that are similar to the given movie
    @SerializedName("results")
    private ArrayList<MovieModel> movies;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * SimilarResponse - constructor, initialize ArrayLists (potential bug if not initialized)
 */
    public SimilarResponse(){
        movies = new ArrayList<>();
    }

/**************************************************************************************************/
/**
 * Getters:
 *      ArrayList<MovieModel> getMovies() - get list of movies similar to a given movie
 */
/**************************************************************************************************/
/**
 * ArrayList<MovieModel> getMovies() - get list of movies similar to a given movie
 * @return - list of movie data similar to a given movie
 */
    public ArrayList<MovieModel> getMovies(){ return movies; }

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
