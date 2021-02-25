package by.legan.android.firealert.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {

    public static BestDate getCurrentBestDate(){
        Date currentTime = Calendar.getInstance().getTime();
        return toBestDate(currentTime);
    }

    public static BestDate toBestDate(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        if (date == null) date = new Date();
        calendar.setTime(date);
        int day_week = calendar.get(Calendar.DAY_OF_WEEK);
        int day_number = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        long testCalOne = calendar.getTimeInMillis();
        Date resultDate = new Date(testCalOne);
        return new BestDate(day_week,day_number,month,year,hour,minute,second,resultDate);
    }

    public static Date firstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) date = new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date lastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) date = new Date();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


}
