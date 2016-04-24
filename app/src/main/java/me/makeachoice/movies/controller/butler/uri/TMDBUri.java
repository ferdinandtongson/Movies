package me.makeachoice.movies.controller.butler.uri;

import android.net.Uri;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.butler.MyButler;

/**
 * Created by Usuario on 4/22/2016.
 */
public class TMDBUri {

    private final int SCHEME = R.string.tmdb_scheme;
    private final int AUTHORITY = R.string.tmdb_authority;
    private final int API_VERSION = R.string.tmdb_api_version;

    private final int PATH_MOVIE = R.string.tmdb_path_movie;
    private final int PATH_MOVIES = R.string.tmdb_path_movies;
    private final int PATH_GENRE = R.string.tmdb_path_genre;
    private final int PATH_SEARCH = R.string.tmdb_path_search;
    private final int PATH_LIST = R.string.tmdb_path_list;

    public final static int PATH_NOW_PLAYING = R.string.tmdb_path_now_playing;
    public final static int PATH_POPULAR = R.string.tmdb_path_popular;
    public final static int PATH_TOP_RATED = R.string.tmdb_path_top_rated;
    public final static int PATH_UPCOMING = R.string.tmdb_path_upcoming;

    public final static int PATH_VIDEOS = R.string.tmdb_path_videos;
    public final static int PATH_SIMILAR = R.string.tmdb_path_similar;
    public final static int PATH_REVIEWS = R.string.tmdb_path_reviews;
    public final static int PATH_CREDITS = R.string.tmdb_path_credits;


    private final static int QUERY_API_KEY = R.string.tmdb_query_api_key;

    MyButler mButler;
    public TMDBUri(MyButler butler){
        mButler = butler;
    }

    private Uri.Builder createUriBase(){
        //http://api.themoviedb.org/3/
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mButler.getActivityContext().getString(SCHEME))
                .authority(mButler.getActivityContext().getString(AUTHORITY))
                .appendPath(mButler.getActivityContext().getString(API_VERSION));

        return builder;
    }

    public String getMovieList(int listType, String apiKey){
        // movie/listType?api_key=apiKey
        //use MoviesResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mButler.getActivityContext().getString(PATH_MOVIE))
                .appendPath(mButler.getActivityContext().getString(listType))
                .appendQueryParameter(
                        mButler.getActivityContext().getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getMovieListByGenre(String genreId, String apiKey){
        // genre/genreId/movies?api_key=apiKey
        //use MoviesResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mButler.getActivityContext().getString(PATH_GENRE))
                .appendPath(genreId)
                .appendPath(mButler.getActivityContext().getString(PATH_MOVIES))
                .appendQueryParameter(
                        mButler.getActivityContext().getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }


    public String getMovieDetail(String movieId, String apiKey){
        // movie/movieId?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(mButler.getActivityContext().getString(PATH_MOVIE))
                .appendPath(movieId)
                .appendQueryParameter(
                        mButler.getActivityContext().getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getMovieDetail(String movieId, int detailType, String apiKey){
        // movie/movieId/detailType?api_key=apiKey
        //credits: use CreditsResponse
        //reviews: use ReviewsResponse
        //videos: use VideosResponse
        //similar: use MoviesResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mButler.getActivityContext().getString(PATH_MOVIE))
                .appendPath(movieId)
                .appendPath(mButler.getActivityContext().getString(detailType))
                .appendQueryParameter(
                        mButler.getActivityContext().getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getGenreList(String apiKey){
        // genre/movie/list?api_key=apiKey
        //use GenreResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mButler.getActivityContext().getString(PATH_GENRE))
                .appendPath(mButler.getActivityContext().getString(PATH_MOVIE))
                .appendPath(mButler.getActivityContext().getString(PATH_LIST))
                .appendQueryParameter(
                        mButler.getActivityContext().getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();

    }

    /*



    http://api.themoviedb.org/3/genre/movie/list?api_key=ec1c9e77ea098584409c2b2309c4f287

    http://api.themoviedb.org/3/movie/popular?api_key=ec1c9e77ea098584409c2b2309c4f287
    http://api.themoviedb.org/3/movie/id?api_key=ec1c9e77ea098584409c2b2309c4f287

    http://api.themoviedb.org/3/search/movie (by movie title)

    http://api.themoviedb.org/3/genre/28/movies?api_key=ec1c9e77ea098584409c2b2309c4f287
    http://api.themoviedb.org/3/movie/209112?api_key=ec1c9e77ea098584409c2b2309c4f287

    //get movie details plus videos
    http://api.themoviedb.org/3/movie/157336?api_key=ec1c9e77ea098584409c2b2309c4f287&append_to_response=videos
    //same as above plus videos, similar movies, reviews and credits
    http://......?api_key=[id]&append_to_response=videos,similar,reviews,credits


    //get video with key
    https://www.youtube.com/watch?v=SUXWAEX2jlg
    */

}
