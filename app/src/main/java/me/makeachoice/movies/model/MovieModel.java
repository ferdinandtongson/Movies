package me.makeachoice.movies.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * MovieModel models the information data from TMDb (TheMovieDB) API.
 *
 * The MovieModel models:
 *      original title
 *      plot synopsis (called overview in api)
 *      movie poster image thumbnail
 *      user rating (called vote_average in api)
 *      release date
 */
public class MovieModel {
    private static String DEBUG = "MovieModel";
    private ArrayList<MovieDetail> mList = new ArrayList<MovieDetail>();

    public class MovieDetail{

        private String mTitle;
        private String mPlot;
        private int mPosterId;
        private String mUserRating;
        private String mReleaseDate;

        public MovieDetail(String title, String plot, int posterId,
                           String rating, String releaseDate){
            mTitle = title;
            mPlot = plot;
            mPosterId = posterId;
            mUserRating = rating;
            mReleaseDate = releaseDate;
        }

        public String getTitle(){return mTitle;}
        public String getPlot(){return mPlot;}
        public int getPoster(){return mPosterId;}
        public String getUserRating(){return mUserRating;}
        public String getReleaseDate(){return mReleaseDate;}

        public void setTitle(String title){mTitle = title;}
        public void setPlot(String plot){mPlot = plot;};
        public void setPoster(int posterId){mPosterId = posterId;}
        public void setUserRating(String rating){mUserRating = rating;}
        public void setReleaseDate(String date){mReleaseDate = date;}

        public ArrayList getMovieList(){return mList;}
    }

    public MovieModel(){
        //MovieModel constructor
    }

    public MovieDetail getMovie(int index){
        return mList.get(index);
    }

    public void addMovie(String title, String plot, int posterId, String rating, String releaseDate){
        mList.add(new MovieDetail(title, plot, posterId, rating, releaseDate));
    }

    public void addMovie(MovieDetail detail){
        //this inserts app at the end of the list
        mList.add(detail);
    }

    public void addMovie(MovieDetail detail, int index){
        //this inserts app at the index and moves everything ahead of it one position
        mList.add(index, detail);
    }

    public void updateApp(int index, MovieDetail detail){
        //this replaces the old app at the index with an updated version
        mList.set(index, detail);
    }

    public void removeApp(MovieDetail detail){
        //removes app object from list
        mList.remove(detail);
    }

    public void removeApp(int index){
        //removes app object from list using the index value
        mList.remove(index);
    }

    public boolean checkForApp(MovieDetail detail){
        return mList.contains(detail);
    }

    public int getAppCount(){
        return mList.size();
    }
}
