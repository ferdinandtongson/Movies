package me.makeachoice.movies.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DateManager - handles date operations
 */
public class DateManager {

/**************************************************************************************************/
/**
 * Static Methods:
 *      Date currentDate() - gets the current system date
 *      Date addDaysToDate(int) - calculates the future date given number of days added
 */
/**************************************************************************************************/
/**
 * Date currentDate() - gets teh current system date
 * @return - current system date
 */
    public static Date currentDate(){
        return new Date(System.currentTimeMillis());
    }

/**
 * Date addDaysToDate(int) - calculates the future date given number of days added
 * @param addDays - number of days to add to current system date
 * @return - future date with given number of days added
 */
    public static Date addDaysToDate(int addDays){
        //get calendar instance
        Calendar cal = Calendar.getInstance();

        //convert current system date to calendar object
        cal.setTime(currentDate());

        //get year of current date
        int year = cal.get(Calendar.YEAR);

        //get month of current date
        int month = cal.get(Calendar.MONTH);

        //get day of current date
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //set calendar date with added days
        cal.set(year, month, day + addDays);

        //return date
        return cal.getTime();
    }

/**************************************************************************************************/

}
