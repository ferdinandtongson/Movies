package me.makeachoice.movies.controller.butler.staff;

import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.housekeeper.DetailKeeper;
import me.makeachoice.movies.controller.viewside.housekeeper.MyHouseKeeper;
import me.makeachoice.movies.controller.viewside.housekeeper.SwipeKeeper;
import me.makeachoice.movies.controller.viewside.helper.DetailHelper;
import me.makeachoice.movies.controller.viewside.helper.SwipeHelper;

/**
 * HouseKeeperStaff maintains the buffer objects holding MyHouseKeeper classes.
 *
 * It uses other classes to assist in the upkeep of the buffers:
 *      SwipeHelper - holds all static resources (layout id, view ids, etc)
 *      DetailHelper - holds all static resources (layout id, view ids, etc)
 *
 * NOTE: HouseKeeper Objects are Activity context sensitive.
 */
public class HouseKeeperStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      Boss mBoss - Boss object
 *      HashMap<Integer,MyHouseKeeper> mRegistry - buffer holding MyHouseKeeper classes
 */
/**************************************************************************************************/

    //mBoss - Boss object
    Boss mBoss;

    //mRegistry - buffer holding MyHouseKeeper classes
    private HashMap<Integer, MyHouseKeeper> mRegistry = new HashMap<>();


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * HouseKeeperStaff - constructor, initialize refresh buffer
 * @param boss - Boss class
 */
    public HouseKeeperStaff(Boss boss){
        //Boss object
        mBoss = boss;

        //buffer holding MyHouseKeeper classes
        mRegistry = new HashMap<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      MyHouseKeeper getHouseKeeper(int) - get HouseKeeper from buffer
 *
 * Setters:
 *      void setHouseKeeper(MyHouseKeeper,int) - put HouseKeeper into buffer
 */
/**************************************************************************************************/
/**
 * MyHouseKeeper getHouseKeeper(int) - get HouseKeeper from buffer
 * @param id - id number of HouseKeeper
 * @return - HouseKeeper object requested
 */
    public MyHouseKeeper getHouseKeeper(int id){
        //check if houseKeeper with id number is in buffer
        if(mRegistry.containsKey(id)){
            //return requested houseKeeper
            return mRegistry.get(id);
        }

        //wake houseKeeper requested
        return startHouseKeeper(id);
    }

/**
 * void setHouseKeeper(MyHouseKeeper,int) - put HouseKeeper into buffer.
 * @param keeper - HouseKeeper object to be registered
 * @param id - id number of HouseKeeper
 */
    public void setHouseKeeper(MyHouseKeeper keeper, int id){
        mRegistry.put(id, keeper);
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods:
 *      MyHouseKeeper startHouseKeeper(int) - wakes up requested HouseKeeper and stores in buffer.
 *      void onFinish() - nulls all of the data in the buffer
 */
/**************************************************************************************************/
/**
 * MyHouseKeeper startHouseKeeper(int) - wakes up requested HouseKeeper and stores in buffer
 * @param id - id number of HouseKeeper
 * @return - requested HouseKeeper
 */
    public MyHouseKeeper startHouseKeeper(int id){
        //create MyHouseKeeper object
        MyHouseKeeper keeper;

        //id of requested HouseKeeper
        switch(id){
            case SwipeHelper.NAME_ID:
                //SwipeKeeper requested
                keeper = new SwipeKeeper(mBoss);
                break;
            case DetailHelper.NAME_ID:
                //DetailKeeper requested
                keeper = new DetailKeeper(mBoss);
                break;
            default:
                //invalid id number
                keeper = null;
        }

        if(keeper != null){
            //valid HouseKeeper id, add HouseKeeper to buffer
            setHouseKeeper(keeper, id);
        }

        return keeper;
    }

/**
 * void onFinish() - nulls all of the data in the buffer
 */
    public void onFinish(){
        //clear and null refresh buffer
        mRegistry.clear();
        mRegistry = null;
    }

/**************************************************************************************************/

}
