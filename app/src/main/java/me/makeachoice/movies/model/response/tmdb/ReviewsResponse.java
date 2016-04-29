package me.makeachoice.movies.model.response.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * ReviewsResponse is used by Gson to parse Review data for a given movie
 *
 * API Call to TMDB:
 *      http://api.themoviedb.org/3/movie/movieID/reviews?api_key=xxx (movieID = id number of movie)
 *      http://api.themoviedb.org/3/movie/157336/reviews?api_key=xxx
 * JSON Response:
 *      root{1}
 *          array{5}
 *              id:
 *              page:
 *              results[]
 *              total_pages:
 *              total_results:
 *
 */
public class ReviewsResponse {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<ReviewModel> - list of ReviewModels that are for the given movie
 *
 * NOTE: Variable names need to match the JSON response for GSON to properly map the data into
 * VideoResponse. If using a different variable name, use @SerializedName("")
 */
/**************************************************************************************************/

    //reviews - review models that are for the given movie
    @SerializedName("results")
    private ArrayList<ReviewModel> reviews;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * ReviewsResponse - constructor, initialize ArrayLists (potential bug if not initialized)
 */

    public ReviewsResponse(){
        reviews = new ArrayList<>();
    }

/**************************************************************************************************/
/**
 * Getters:
 *      ArrayList<ReviewModel> getReviews() - get list of reviews for a given movie
 */
/**************************************************************************************************/
/**
 * ArrayList<ReviewModel> getReviews() - get list of reviews for a given movie
 * @return - list of review data for a given movie
 */

    public ArrayList<ReviewModel> getReviews(){ return reviews; }

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
