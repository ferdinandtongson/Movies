package me.makeachoice.movies.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Usuario on 5/11/2016.
 */
public class DateManager {
    public static Date currentDate(){
        return new Date(System.currentTimeMillis());
    }

    public static Date addDaysToDate(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day + 1);

        return cal.getTime();
    }

}
