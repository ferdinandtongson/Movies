package me.makeachoice.movies.model;

import java.util.ArrayList;

/**
 * MovieJSON models the json data from TMDb (TheMovieDB) API. Creates an array list that contains
 * the data received the TheMovieDB API call.
 *
 * Contains an inner class MovieDetail to hold individual detailed information of Movies
 *
 */
public class MovieJSON {

/**************************************************************************************************/
/**
 * Class Variables:
 *      ArrayList<MovieDetail> mList - list of MovieDetail data
 */
/**************************************************************************************************/

    //mList - list of MovieDetail data
    private ArrayList<MovieDetail> mList = new ArrayList<>();

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      MovieDetail getMovie(int) - get MovieDetial in given list position
 *      int getMovieCount() - get number of items in list
 *
 * Setters:
 *      - None -
 */
/**************************************************************************************************/
/**
 * MovieDetail getMovie(int) - get the movie detail data in the current index position
 * @param index - position of data in the list
 * @return - MovieDetail data of movie
 */
    public MovieDetail getMovie(int index){
        //return MovieDetail in the current index position of list
        return mList.get(index);
    }

/**
 * int getMovieCount() - get number of MovieDetail data items in the list
 * @return - number of items in the list
 */
    public int getMovieCount(){
        //get size of MovieDetail list
        return mList.size();
    }

/**************************************************************************************************/
/**
 * Class Methods:
 *      void addMovie(MovieDetail) - adds MovieDetail to list
 *      void clearMovies() - clear
 */
/**************************************************************************************************/
/**
 * void addMovie(MovieDetail) - add MovieDetail to array list
 * @param detail - MovieDetail object to add to list
 */
    public void addMovie(MovieDetail detail){

        //check if array list has been created
        if(mList == null){
            //create array list
            mList = new ArrayList<>();
        }
        //this inserts app at the end of the list
        mList.add(detail);
    }

/**
 * void clearMovies() - clear array list of movies and null reference
 */
    public void clearMovies(){
        if(mList != null){
            mList.clear();
            mList = null;
        }
    }

/**************************************************************************************************/


/**************************************************************************************************/
/**
 * Inner Class MovieDetail - holds detailed information received through the API call
 */
/**************************************************************************************************/

    public class MovieDetail{

/**************************************************************************************************/
/**
 * Class Variables:
 *      String mOriginalTitle - original title of movie (may be in a foreign language)
 *      String mTitle - title of movie (English title, if original title was in different langague)
 *      String mOverview - plot of movie
 *
 *      String mReleaseDate - date movie was released
 *      String mOriginalLanguage - original language of movie
 *
 *      Double mPopularity - popularity of movie
 *      Double mVoteCount - number of votes from users that the movie has received
 *      Double mVoteAverage - average vote score the movie has received
 *
 *      Integer[] mGenreIds - id numbers for genres the movie fits into
 *      Boolean mAdult - is the movie in the Adult category
 *
 *      String mPosterPath - url path to the poster image of the movie
 *      String mBackdropPath - url path to the backdeop image of the movie
 *      Boolean mVideo - if the movie has a video link to it or not
 */
/**************************************************************************************************/

        private String mOriginalTitle;
        private String mTitle;
        private String mOverview;

        private String mReleaseDate;
        private String mOriginalLanguage;

        private Double mPopularity;
        private Double mVoteCount;
        private Double mVoteAverage;

        private Integer[] mGenreIds;
        private Boolean mAdult;

        private String mPosterPath;
        private String mBackdropPath;
        private Boolean mVideo;

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters and Setters Methods
 */
/**************************************************************************************************/

        public String getOriginalTitle(){return mOriginalTitle;}
        public String getTitle(){return mTitle;}
        public String getOverview(){return mOverview;}

        public String getReleaseDate(){return mReleaseDate;}
        public String getOriginalLanguage(){return mOriginalLanguage;}

        public Double getPopularity(){return mPopularity;}
        public Double getVoteCount(){return mVoteCount;}
        public Double getVoteAverage(){return mVoteAverage;}

        public Integer[] getGenreIds(){return mGenreIds;}
        public Boolean getAdult(){return mAdult;}

        public String getPosterPath(){return mPosterPath;}
        public String getBackdropPath(){return mBackdropPath;}
        public Boolean getVideo(){return mVideo;}


        public void setOriginalTitle(String title){mOriginalTitle = title;}
        public void setTitle(String title){mTitle = title;}
        public void setOverview(String overview){mOverview = overview;}

        public void setReleaseDate(String date){mReleaseDate = date;}
        public void setOriginalLanguage(String language){mOriginalLanguage = language;}

        public void setPopularity(Double popularity){mPopularity = popularity;}
        public void setVoteCount(Double count){mVoteCount = count;}
        public void setVoteAverage(Double average){mVoteAverage = average;}

        public void setGenreId(Integer[] ids){mGenreIds = ids;}
        public void setAdult(Boolean isAdult){mAdult = isAdult;}

        public void setPosterPath(String path){mPosterPath = path;}
        public void setBackdropPath(String path){mBackdropPath = path;}
        public void setVideo(Boolean hasVideo){mVideo = hasVideo;}

/**************************************************************************************************/

    }

/**************************************************************************************************/

}
