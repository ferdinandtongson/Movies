package me.makeachoice.movies.controller.butler.uri;

import android.net.Uri;

/**
 * Created by Usuario on 4/22/2016.
 */
public class TMDBUri {

    private final String SCHEME = "http";
    private final String AUTHORITY = "api.themoviedb.org";
    private final String API_VERSION = "3";

    private final String PATH_MOVIE = "movie";
    private final String PATH_MOVIES = "movies";
    private final String PATH_GENRE = "genre";
    private final String PATH_SEARCH = "search";
    private final String PATH_LIST = "list";

    public final static String MOVIES_NOW_PLAYING = "now_playing";
    public final static String MOVIES_POPULAR = "popular";
    public final static String MOVIES_TOP_RATED = "top_rated";
    public final static String MOVIES_UPCOMING = "upcoming";

    public final static String MOVIE_VIDEOS = "videos";
    public final static String MOVIE_SIMILAR = "similar";
    public final static String MOVIE_REVIEWS = "review";
    public final static String MOVIE_CREDITS = "credits";


    private final String QUERY_API_KEY = "api_key";
    final String GENRE_MOVIE_LIST = "genre/movie/list";

    private Uri.Builder createUriBase(){
        //http://api.themoviedb.org/3/
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(API_VERSION);

        return builder;
    }

    public String getMovieList(String listType, String apiKey){
        // movie/listType?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(PATH_MOVIE)
                .appendPath(listType)
                .appendQueryParameter(QUERY_API_KEY, apiKey);

        return builder.build().toString();
    }

    public String getMovieListByGenre(String genreId, String apiKey){
        // genre/genreId/movies?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(PATH_GENRE)
                .appendPath(genreId)
                .appendPath(PATH_MOVIES)
                .appendQueryParameter(QUERY_API_KEY, apiKey);

        return builder.build().toString();
    }


    public String getMovieDetail(String movieId, String apiKey){
        // movie/movieId?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(PATH_MOVIE)
                .appendPath(movieId)
                .appendQueryParameter(QUERY_API_KEY, apiKey);

        return builder.build().toString();
    }

    public String getMovieDetail(String movieId, String detailType, String apiKey){
        // movie/movieId/detailType?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(PATH_MOVIE)
                .appendPath(movieId)
                .appendPath(detailType)
                .appendQueryParameter(QUERY_API_KEY, apiKey);

        return builder.build().toString();
    }

    public String getGenreList(String apiKey){
        // genre/movie/list?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(PATH_GENRE)
                .appendPath(PATH_MOVIE)
                .appendPath(PATH_LIST)
                .appendQueryParameter(QUERY_API_KEY, apiKey);

        return builder.build().toString();

    }

    /*





    http://api.themoviedb.org/3/movie/popular?api_key=ec1c9e77ea098584409c2b2309c4f287
    http://api.themoviedb.org/3/movie/id?api_key=ec1c9e77ea098584409c2b2309c4f287

    http://api.themoviedb.org/3/search/movie (by movie title)


    */

}
