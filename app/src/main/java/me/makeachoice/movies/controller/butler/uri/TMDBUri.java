package me.makeachoice.movies.controller.butler.uri;

import android.net.Uri;

import me.makeachoice.movies.R;
import me.makeachoice.movies.controller.Boss;

/**
 * TMDBUri builds uri values to call TheMovieDB api and YouTube video links
 */
public class TMDBUri {

    private final int SCHEME = R.string.tmdb_scheme;
    private final int SCHEME_SECURE = R.string.tmdb_scheme_secure;
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
    private final static int QUERY_APPEND_RESPONSE = R.string.tmdb_query_append_response;

    private final static int APPEND_RESPONSE_ALL = R.string.tmdb_append_response_all;

    private final int AUTHORITY_IMAGE = R.string.tmdb_authority_image;
    private final int IMAGE_FOLDER_T = R.string.tmdb_image_folder_t;
    private final int IMAGE_FOLDER_P = R.string.tmdb_image_folder_p;
    private final int IMAGE_FOLDER_W500 = R.string.tmdb_image_folder_w500;

    private final int AUTHORITY_YOUTUBE_IMAGE = R.string.tmdb_authority_youtube_image;
    private final int YOUTUBE_FOLDER_VI = R.string.tmdb_youtube_folder_vi;
    private final int YOUTUBE_PATH_THUMBNAIL = R.string.tmdb_youtube_path_thumbnail;

    private final int AUTHORITY_YOUTUBE = R.string.tmdb_authority_youtube;
    private final int YOUTUBE_PATH_WATCH = R.string.tmdb_youtube_path_watch;
    private final int YOUTUBE_QUERY_VIDEO = R.string.tmdb_youtube_query_video;


    Boss mBoss;
    public TMDBUri(Boss boss){
        mBoss = boss;
    }

    private Uri.Builder createUriBase(){
        //http://api.themoviedb.org/3/
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mBoss.getString(SCHEME))
                .authority(mBoss.getString(AUTHORITY))
                .appendPath(mBoss.getString(API_VERSION));

        return builder;
    }

    public String getMovieList(int listType, String apiKey){
        // movie/listType?api_key=apiKey
        //use MoviesResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mBoss.getString(PATH_MOVIE))
                .appendPath(mBoss.getString(listType))
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getMovieListByGenre(String genreId, String apiKey){
        // genre/genreId/movies?api_key=apiKey
        //use MoviesResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mBoss.getString(PATH_GENRE))
                .appendPath(genreId)
                .appendPath(mBoss.getString(PATH_MOVIES))
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }


    public String getMovieDetail(String movieId, String apiKey){
        // movie/movieId?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(mBoss.getString(PATH_MOVIE))
                .appendPath(movieId)
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getMovieDetailAll(String movieId, String apiKey){
        // movie/movieId?api_key=apiKey
        Uri.Builder builder = createUriBase();
        builder.appendPath(mBoss.getString(PATH_MOVIE))
                .appendPath(movieId)
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey)
                .appendQueryParameter(mBoss.getString(QUERY_APPEND_RESPONSE),
                        mBoss.getString(APPEND_RESPONSE_ALL));

        return builder.build().toString();
    }

    public String getMovieDetail(String movieId, int detailType, String apiKey){
        // movie/movieId/detailType?api_key=apiKey
        //credits: use CreditsResponse
        //reviews: use ReviewsResponse
        //videos: use VideosResponse
        //similar: use MoviesResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mBoss.getString(PATH_MOVIE))
                .appendPath(movieId)
                .appendPath(mBoss.getString(detailType))
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getGenreList(String apiKey){
        // genre/movie/list?api_key=apiKey
        //use GenreResponse
        Uri.Builder builder = createUriBase();
        builder.appendPath(mBoss.getString(PATH_GENRE))
                .appendPath(mBoss.getString(PATH_MOVIE))
                .appendPath(mBoss.getString(PATH_LIST))
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();

    }

    public String getImagePath(String imageId, String apiKey){
        imageId = makeValidUriPath(imageId);

        // http://image.tmdb.org/t/p/w500/poster?api_key=apiKey
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mBoss.getString(SCHEME))
                .authority(mBoss.getString(AUTHORITY_IMAGE))
                .appendPath(mBoss.getString(IMAGE_FOLDER_T))
                .appendPath(mBoss.getString(IMAGE_FOLDER_P))
                .appendPath(mBoss.getString(IMAGE_FOLDER_W500))
                .appendPath(imageId)
                .appendQueryParameter(mBoss.getString(QUERY_API_KEY), apiKey);

        return builder.build().toString();
    }

    public String getYouTubeThumbnailPath(String videoId){
        videoId = makeValidUriPath(videoId);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mBoss.getString(SCHEME))
                .authority(mBoss.getString(AUTHORITY_YOUTUBE_IMAGE))
                .appendPath(mBoss.getString(YOUTUBE_FOLDER_VI))
                .appendPath(videoId)
                .appendPath(mBoss.getString(YOUTUBE_PATH_THUMBNAIL));

        return builder.build().toString();

    }

    public String getYouTubeVideoPath(String videoId){
        videoId = makeValidUriPath(videoId);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mBoss.getString(SCHEME_SECURE))
                .authority(mBoss.getString(AUTHORITY_YOUTUBE))
                .appendPath(mBoss.getString(YOUTUBE_PATH_WATCH))
                .appendQueryParameter(mBoss.getString(YOUTUBE_QUERY_VIDEO), videoId);

        return builder.build().toString();

    }

    private String makeValidUriPath(String path){
        if(path != null){
            String str = String.valueOf(path.charAt(0));
            if(str.equals("/")){
                int len = path.length();
                path = path.substring(1, len);
            }
        }

        return path;
    }


}
