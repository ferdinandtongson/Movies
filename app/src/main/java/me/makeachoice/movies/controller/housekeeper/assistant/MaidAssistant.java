package me.makeachoice.movies.controller.housekeeper.assistant;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.housekeeper.MainKeeper;
import me.makeachoice.movies.controller.housekeeper.helper.EmptyHelper;
import me.makeachoice.movies.controller.housekeeper.helper.InfoHelper;
import me.makeachoice.movies.controller.housekeeper.helper.PosterHelper;
import me.makeachoice.movies.controller.housekeeper.maid.EmptyMaid;
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
    public void hireMainMaids(Boss boss, MainKeeper keeper){
        PosterMaid posterMaid = new PosterMaid(keeper);
        EmptyMaid emptyMaid = new EmptyMaid(keeper);
        InfoMaid infoMaid = new InfoMaid(keeper);

        boss.registerMaid(PosterHelper.NAME_ID, posterMaid);
        boss.registerMaid(EmptyHelper.NAME_ID, emptyMaid);
        boss.registerMaid(InfoHelper.NAME_ID, infoMaid);
    }



}
