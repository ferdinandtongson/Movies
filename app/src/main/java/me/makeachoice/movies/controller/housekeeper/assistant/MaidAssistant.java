package me.makeachoice.movies.controller.housekeeper.assistant;

import java.util.ArrayList;

import me.makeachoice.movies.adapter.item.PosterItem;
import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.MovieKeeper;
import me.makeachoice.movies.controller.housekeeper.SwipeKeeper;
import me.makeachoice.movies.controller.housekeeper.helper.InfoHelper;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.controller.housekeeper.maid.InfoMaid;
import me.makeachoice.movies.controller.housekeeper.maid.PosterMaid;

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

        ArrayList<PosterItem> posters = boss.getPosters(PosterHelper.NAME_ID_EMPTY);

        //update posters
        popularMaid.updatePosters(posters);
        topRatedMaid.updatePosters(posters);
        nowPlayingMaid.updatePosters(posters);
        upcomingMaid.updatePosters(posters);
        favoriteMaid.updatePosters(posters);


        boss.registerMaid(PosterHelper.NAME_ID_MOST_POPULAR, popularMaid);
        boss.registerMaid(PosterHelper.NAME_ID_TOP_RATED, topRatedMaid);
        boss.registerMaid(PosterHelper.NAME_ID_NOW_PLAYING, nowPlayingMaid);
        boss.registerMaid(PosterHelper.NAME_ID_UPCOMING, upcomingMaid);
        boss.registerMaid(PosterHelper.NAME_ID_FAVORITE, favoriteMaid);
    }

    public void hireDetailMaids(Boss boss, MovieKeeper keeper){
        InfoMaid infoMaid = new InfoMaid(keeper, InfoHelper.NAME_ID);

        boss.registerMaid(InfoHelper.NAME_ID, infoMaid);

    }

}
