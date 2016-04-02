package me.makeachoice.movies.model.json;

import java.util.ArrayList;

/**
 * MovieJSON models the json data from TMDb (TheMovieDB) API.
 *
 * The MovieModel models:
 *      original_title      title               overview
 *      release_date        original_language
 *      popularity          vote_count          vote_average
 *      genre_ids           adult
 *      poster_path         backdrop_path       video
 *
 *
 *
 *
 */
public class MovieJSON {
    private ArrayList<MovieDetail> mList = new ArrayList<MovieDetail>();

    public class MovieDetail{

        private String mOriginalTitle;
        private String mTitle;
        private String mOverview;

        private String mReleaseDate;
        private String mOriginalLanguage;

        private String mPopularity;
        private String mVoteCount;
        private String mVoteAverage;

        private String mGenreId;
        private Boolean mAdult;

        private String mPosterPath;
        private String mBackdropPath;
        private String mVideo;

        public MovieDetail(){}

        public String getOriginalTitle(){return mOriginalTitle;}
        public String getTitle(){return mTitle;}
        public String getOverview(){return mOverview;}

        public String getReleaseDate(){return mReleaseDate;}
        public String getOriginalLanguage(){return mOriginalLanguage;}

        public String getPopularity(){return mPopularity;}
        public String getVoteCount(){return mVoteCount;}
        public String getVoteAverage(){return mVoteAverage;}

        public String getGenreId(){return mGenreId;}
        public Boolean getAdult(){return mAdult;}

        public String getPosterPath(){return mPosterPath;}
        public String getBackdropPath(){return mBackdropPath;}
        public String getVideo(){return mVideo;}


        public void setOriginalTitle(String title){mOriginalTitle = title;}
        public void setTitle(String title){mTitle = title;}
        public void setOverview(String overview){mOverview = overview;}

        public void setReleaseDate(String date){mReleaseDate = date;}
        public void setOriginalLanguage(String language){mOriginalLanguage = language;}

        public void setPopularity(String popularity){mPopularity = popularity;}
        public void setVoteCount(String count){mVoteCount = count;}
        public void setVoteAverage(String average){mVoteAverage = average;}

        public void setGenreId(String id){mGenreId = id;}
        public void setAdult(Boolean isAdult){mAdult = isAdult;}

        public void setPosterPath(String path){mPosterPath = path;}
        public void setBackdropPath(String path){mBackdropPath = path;}
        public void setVideo(String path){mVideo = path;}

    }

    public MovieJSON(){
        //MovieModel constructor
    }

    public MovieDetail getMovie(int index){
        return mList.get(index);
    }

    public void addMovie(MovieDetail detail){
        //this inserts app at the end of the list
        mList.add(detail);
    }

    public void addMovie(MovieDetail detail, int index){
        //this inserts app at the index and moves everything ahead of it one position
        mList.add(index, detail);
    }

    public void updateMovie(int index, MovieDetail detail){
        //this replaces the old app at the index with an updated version
        mList.set(index, detail);
    }

    public void removeMovie(MovieDetail detail){
        //removes app object from list
        mList.remove(detail);
    }

    public void removeMovie(int index){
        //removes app object from list using the index value
        mList.remove(index);
    }

    public boolean checkForMovie(MovieDetail detail){
        return mList.contains(detail);
    }

    public int getMovieCount(){
        return mList.size();
    }
}
