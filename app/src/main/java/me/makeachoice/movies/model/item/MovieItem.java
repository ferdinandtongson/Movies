package me.makeachoice.movies.model.item;


import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * MovieItem presents movie data ready to be consumed by the View
 */
public class MovieItem {

/**************************************************************************************************/
/**
 * Class Variables:
 *      int mTMDBId - movie id number used by TheMovieDB
 *      String mTitle - title of the movie
 *      String mOverview - overview of the movie
 *      String mReleaseDate - date the movie was released to the public
 *      String mIMDBId - movie id number used by InternetMovieDb
 *      String mHomepage - internet homepage of the movie
 *
 *      String mOriginalTitle - original title of the movie
 *      String mOriginalLanguage - original language of the movie
 *
 *      float mPopularity - popularity rating of the movie
 *      Integer mVoteCount - number of rating votes the movie has received
 *      float mVoteAverage - average rating the movie has received
 *
 *      String mPosterPath - the full API path to the poster image in TMDB
 *      Bitmap mPoster - bitmap of the movie poster
 *      byte[] mPosterByte - holds the byte array for the poster image
 *
 *      String mBackdropPath - url path to the movie backdrop image
 *      boolean mVideo - if movie contains a video
 *      boolean mAdult - if the movie is an X-rated movie
 *
 *      Integer[] mGenreIds - id number of genre types the movie is in
 *      ArrayList<GenreItem> mGenres - array of genre item data, genre types the movie is in
 *
 *      ArrayList<CastItem> mCast - array of cast item data, cast members in the movie
 *      ArrayList<ReviewItem> mReviews - array of review item data, reviews of the movie
 *      ArrayList<VideoItem> mVideos - array of video item data, video trailers of the movie
 */
/**************************************************************************************************/

    //mTMDBId - movie id number used by TheMovieDB
    private int mTMDBId;
    //mTitle - title of the movie
    private String mTitle;
    //mOverview - overview of the movie
    private String mOverview;
    //mReleaseDate - date the movie was released to the public
    private String mReleaseDate;
    //mIMDBId - movie id number used by InternetMovieDb
    private String mIMDBId;
    //mHomepage - internet homepage of the movie
    private String mHomepage;                   //called during a request for a specific movie

    //mOriginalTitle - original title of the movie
    private String mOriginalTitle;
    //mOriginalLanguage - original language of the movie
    private String mOriginalLanguage;

    //mPopularity - popularity rating of the movie
    private float mPopularity;
    //mVoteCount - number of rating votes the movie has received
    private Integer mVoteCount;
    //mVoteAverage - average rating the movie has received
    private float mVoteAverage;

    //mPosterPath - the full API path to the poster image in TMDB
    private String mPosterPath;
    //mPoster - bitmap of the movie poster
    private Bitmap mPoster;
    //mPosterByte - holds the byte array for the poster image
    private byte[] mPosterByte;


    //mBackdropPath - url path to the movie backdrop image
    private String mBackdropPath;
    //mVideo - if movie contains a video
    private Boolean mVideo;
    //mAdult - if the movie is an X-rated movie
    private Boolean mAdult;

    //mGenreIds - id number of genre types the movie is in
    private Integer[] mGenreIds;
    //mGenres - array of genre item data, genre types the movie is in
    private ArrayList<GenreItem> mGenres;        //called during a request for a specific movie

    //mCast - array of cast item data, cast members in the movie
    private ArrayList<CastItem> mCast;           //called during a request for a specific movie
    //mReviews - array of review item data, reviews of the movie
    private ArrayList<ReviewItem> mReviews;      //called during a request for a specific movie
    //mVideos - array of video item data, video trailers of the movie
    private ArrayList<VideoItem> mVideos;        //called during a request for a specific movie


/**************************************************************************************************/
/**
 * Getters:
 *      int getTMDBId() - gets TheMovieDB id number of the movie
 *      String getTitle() - gets title of the movie
 *      String getOverview() - get overview of the movie
 *      String getReleaseDate() - get the release date of the movie
 *      String getIMDBId() - get the InternetMovieDb id number of the movie
 *      String getHomepage() - get the homepage of the movie
 *
 *      String getOriginalTitle() - get the original title of the movie
 *      String getOriginalLanguage() - get the original language of the movie
 *
 *      float getPopularity() - get popularity rating of the movie
 *      Integer getVoteCount() - get number of rating votes the movie has received
 *      float getVoteAverage() - get average rating the movie has received
 *
 *      String getPosterPath() - gets the full API path to the poster image in TMDB
 *      Bitmap getPoster() - gets the poster bitmap
 *
 *      String getBackdropPath() - get url path to the movie backdrop image
 *      boolean getVideo() - get status if movie contains a video
 *      boolean getAdult() - get status if the movie is an X-rated movie
 *
 *      Integer[] getGenreIds() - get id number of genre types the movie is in
 *      ArrayList<GenreItem> getGenres() - get array of genre item data, genre types the movie is in
 *
 *      ArrayList<CastItem> getCast() - get array of cast item data, cast members in the movie
 *      ArrayList<ReviewItem> getReviews() - get array of review item data, reviews of the movie
 *      ArrayList<VideoItem> getVideos() - gets array of video item data, video trailers of the
 *          movie
 */
/**************************************************************************************************/

    public int getTMDBId(){ return mTMDBId; }
    public String getTitle(){ return mTitle; }
    public String getOverview(){ return mOverview; }
    public String getReleaseDate(){ return mReleaseDate; }
    public String getIMDBId() {return mIMDBId; }
    public String getHomepage() { return mHomepage; }

    public String getOriginalTitle(){ return mOriginalTitle; }
    public String getOriginalLanguage(){ return mOriginalLanguage; }

    public float getPopularity(){ return mPopularity; }
    public Integer getVoteCount(){ return mVoteCount; }
    public float getVoteAverage(){ return mVoteAverage; }

    public String getPosterPath(){ return mPosterPath; }
    public Bitmap getPoster(){ return mPoster; }
    public byte[] getPosterBytes(){ return mPosterByte; }

    public String getBackdropPath(){ return mBackdropPath; }
    public Boolean getVideo(){ return mVideo; }
    public Boolean getAdult(){ return mAdult; }


    public Integer[] getGenreIds(){ return mGenreIds; }
    public ArrayList<GenreItem> getGenres(){ return mGenres; }
    public ArrayList<CastItem> getCast(){ return mCast; }
    public ArrayList<ReviewItem> getReviews(){ return mReviews; }
    public ArrayList<VideoItem> getVideos(){ return mVideos; }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Setters:
 *      void setTMDBId(int) - sets TheMovieDB id number of the movie
 *      void setTitle(String) - sets title of the movie
 *      void setOverview(String) - sets overview of the movie
 *      void setReleaseDate(String) - sets the release date of the movie
 *      void setIMDBId(String) - sets the InternetMovieDb id number of the movie
 *      void setHomepage(String) - sets the homepage of the movie
 *
 *      void setOriginalTitle(String) - sets the original title of the movie
 *      void setOriginalLanguage(String) - sets the original language of the movie
 *
 *      void setPopularity(float) - sets popularity rating of the movie
 *      void setVoteCount(Integer) - sets number of rating votes the movie has received
 *      void setVoteAverage(float) - sets average rating the movie has received
 *
 *      void setPosterPath(String) - sets the full API path to the poster image in TMDB
 *      void setPoster(Bitmap) - sets the poster bitmap
 *      void setPosterBytes(byte[]) - set the byte array value of the poster bitmap
 *
 *      void setBackdropPath(String) - sets url path to the movie backdrop image
 *      void setVideo(boolean) - sets status if movie contains a video
 *      void setAdult(boolean) - sets status if the movie is an X-rated movie
 *
 *      void setGenreIds(Integer[]) - sets id number of genre types the movie is in
 *      void setGenres(ArrayList<GenreItem>) - sets array of genre item data
 *
 *      void setCast(ArrayList<CastItem>) - sets array of cast item data
 *      void setReviews(ArrayList<ReviewItem>) - sets array of review item data
 *      void setVideos(ArrayList<VideoItem>) - sets array of video item data
 */
/**************************************************************************************************/

    public void setTMDBId(int id){ mTMDBId = id; }
    public void setTitle(String title){ mTitle = title; }
    public void setOverview(String overview){ mOverview = overview; }
    public void setReleaseDate(String date){ mReleaseDate = date; }
    public void setIMDBId(String id){ mIMDBId = id; }
    public void setHomepage(String url){ mHomepage = url; }

    public void setOriginalTitle(String title){ mOriginalTitle = title; }
    public void setOriginalLanguage(String language){ mOriginalLanguage = language; }

    public void setPopularity(float popularity){ mPopularity = popularity; }
    public void setVoteCount(Integer count){ mVoteCount = count; }
    public void setVoteAverage(float average){ mVoteAverage = average; }

    public void setPosterPath(String path){ mPosterPath = path; }
    public void setPoster(Bitmap poster){ mPoster = poster; }
    public void setPosterBytes(byte[] bmpByte){ mPosterByte = bmpByte;}

    public void setBackdropPath(String path){ mBackdropPath = path; }
    public void setVideo(boolean hasVideo){mVideo = hasVideo; }
    public void setAdult(boolean isAdult){mAdult = isAdult; }
    public void setGenreIds(Integer[] ids){ mGenreIds = ids; }
    public void setGenres(ArrayList<GenreItem> genres){ mGenres = genres; }
    public void setCast(ArrayList<CastItem> cast){ mCast = cast; }
    public void setReviews(ArrayList<ReviewItem> reviews){ mReviews = reviews; }
    public void setVideos(ArrayList<VideoItem> videos){ mVideos = videos; }

/**************************************************************************************************/

}
