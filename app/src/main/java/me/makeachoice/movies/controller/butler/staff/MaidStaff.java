package me.makeachoice.movies.controller.butler.staff;

import java.util.HashMap;

import me.makeachoice.movies.controller.Boss;
import me.makeachoice.movies.controller.viewside.maid.MyMaid;

/**
 * MaidStaff maintains the buffer objects holding MyMaid classes.
 *
 * NOTE: Maid Objects are Activity context sensitive.
 */
public class MaidStaff {

/**************************************************************************************************/
/**
 * Class Variables:
 *      Boss mBoss - Boss object
 *      HashMap<Integer,MyMaid> mRegistry - buffer holding MyMaid classes
 */
/**************************************************************************************************/

    //mBoss - Boss object
    Boss mBoss;

    //mRegistry - buffer holding MyMaid classes
    private HashMap<Integer, MyMaid> mRegistry = new HashMap<>();


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * MaidStaff - constructor, initialize refresh buffer
 * @param boss - Boss class
 */
    public MaidStaff(Boss boss){
        //Boss object
        mBoss = boss;

        //buffer holding MyMaid classes
        mRegistry = new HashMap<>();
    }

/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Getters:
 *      MyMaid getMaid(int) - get Maid from buffer
 *
 * Setters:
 *      void setMaid(MyMaid,int) - put Maid into buffer
 */
/**************************************************************************************************/
/**
 * MyMaid getMaid(int) - get Maid from buffer
 * @param id - id number of Maid
 * @return - Maid object requested
 */
    public MyMaid getMaid(int id){
        //check if maid with id number is in buffer
        if(mRegistry.containsKey(id)){
            //return maid requested
            return mRegistry.get(id);
        }

        //invalid maid requested
        return null;
    }

/**
 * void setMaid(MyMaid,int) - put MyMaid into buffer.
 * @param maid - maid object to be registered
 * @param id - id number of Maid
 */
    public void setMaid(MyMaid maid, int id){
        mRegistry.put(id, maid);
    }


/**************************************************************************************************/

/**************************************************************************************************/
/**
 * Class methods:
 *      void onFinish() - nulls all of the data in the buffer
 */
/**************************************************************************************************/
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
