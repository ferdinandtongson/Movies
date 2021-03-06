package me.makeachoice.movies.controller.viewside.assistant;

import java.util.ArrayList;

import me.makeachoice.movies.model.item.MovieItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.housekeeper.DetailKeeper;
import me.makeachoice.movies.controller.viewside.housekeeper.SwipeKeeper;
import me.makeachoice.movies.controller.viewside.helper.InfoHelper;
import me.makeachoice.movies.controller.viewside.helper.PosterHelper;
import me.makeachoice.movies.controller.viewside.helper.ReviewHelper;
import me.makeachoice.movies.controller.viewside.helper.VideoHelper;
import me.makeachoice.movies.controller.viewside.maid.InfoMaid;
import me.makeachoice.movies.controller.viewside.maid.PosterMaid;
import me.makeachoice.movies.controller.viewside.maid.ReviewMaid;
import me.makeachoice.movies.controller.viewside.maid.VideoMaid;

/**
 * MaidAssistant will assist the HouseKeeper class with initializing and registering the Maids.
 */
public class MaidAssistant{

/**************************************************************************************************/
/**
 * PosterMaid is in charge of taking care of displaying thumbnail icon images of movies in a
 * grid fragment. It will maintain all events or requests called by the fragment and will push
 * these events or requests up to the MyHouseKeeper if the MyMaid cannot handle it.
 *
 * EmptyMaid is in charge of displaying and "Empty" message if the gridView in the PosterFragment
 * is empty.
 *
 * MovieInfoMaid is in charge of displaying information about a particular movie selected by the
 * user. It will maintain all events or requests called by the fragment and will push these events
 * or requests up to the MyHouseKeeper if the MyMaid cannot handle it.
 */
/**************************************************************************************************/
    public void hireSwipeMaids(Boss boss, SwipeKeeper keeper){
        PosterMaid popularMaid = new PosterMaid(keeper, PosterHelper.NAME_ID_MOST_POPULAR);
        PosterMaid topRatedMaid = new PosterMaid(keeper, PosterHelper.NAME_ID_TOP_RATED);
        PosterMaid nowPlayingMaid = new PosterMaid(keeper, PosterHelper.NAME_ID_NOW_PLAYING);
        PosterMaid upcomingMaid = new PosterMaid(keeper, PosterHelper.NAME_ID_UPCOMING);
        PosterMaid favoriteMaid = new PosterMaid(keeper, PosterHelper.NAME_ID_FAVORITE);

        ArrayList<MovieItem> movies = new ArrayList<>();

        //update posters
        popularMaid.updateMovies(movies);
        topRatedMaid.updateMovies(movies);
        nowPlayingMaid.updateMovies(movies);
        upcomingMaid.updateMovies(movies);
        favoriteMaid.updateMovies(movies);


        boss.registerMaid(PosterHelper.NAME_ID_MOST_POPULAR, popularMaid);
        boss.registerMaid(PosterHelper.NAME_ID_TOP_RATED, topRatedMaid);
        boss.registerMaid(PosterHelper.NAME_ID_NOW_PLAYING, nowPlayingMaid);
        boss.registerMaid(PosterHelper.NAME_ID_UPCOMING, upcomingMaid);
        boss.registerMaid(PosterHelper.NAME_ID_FAVORITE, favoriteMaid);
    }

    public void hireDetailMaids(Boss boss, DetailKeeper keeper){
        InfoMaid infoMaid = new InfoMaid(keeper, InfoHelper.NAME_ID);
        ReviewMaid reviewMaid = new ReviewMaid(keeper, ReviewHelper.NAME_ID);
        VideoMaid videoMaid = new VideoMaid(keeper, VideoHelper.NAME_ID);

        boss.registerMaid(InfoHelper.NAME_ID, infoMaid);
        boss.registerMaid(ReviewHelper.NAME_ID, reviewMaid);
        boss.registerMaid(VideoHelper.NAME_ID, videoMaid);
    }

}
