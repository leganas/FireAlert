package by.legan.android.firealert.utils;

import android.content.Context;

import java.util.Date;

import by.legan.android.firealert.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class BestDate {
    int day_week;
    int day_number;
    int month;
    int year;
    int hour;
    int minute;
    int second;
    Date date;

    public static String fullDayName(Context context, int day){
        switch (day)
        {
            case 1: return context.getResources().getString(R.string.name_day1);
            case 2: return context.getResources().getString(R.string.name_day2);
            case 3: return context.getResources().getString(R.string.name_day3);
            case 4: return context.getResources().getString(R.string.name_day4);
            case 5: return context.getResources().getString(R.string.name_day5);
            case 6: return context.getResources().getString(R.string.name_day6);
            case 7: return context.getResources().getString(R.string.name_day7);
        }
        return "";
    }

    public static String fullMonthName(Context context, int month){
        switch (month)
        {
            case 1: return context.getResources().getString(R.string.name_month_1);
            case 2: return context.getResources().getString(R.string.name_month_2);
            case 3: return context.getResources().getString(R.string.name_month_3);
            case 4: return context.getResources().getString(R.string.name_month_4);
            case 5: return context.getResources().getString(R.string.name_month_5);
            case 6: return context.getResources().getString(R.string.name_month_6);
            case 7: return context.getResources().getString(R.string.name_month_7);
            case 8: return context.getResources().getString(R.string.name_month_8);
            case 9: return context.getResources().getString(R.string.name_month_9);
            case 10: return context.getResources().getString(R.string.name_month_10);
            case 11: return context.getResources().getString(R.string.name_month_11);
            case 12: return context.getResources().getString(R.string.name_month_12);
        }
        return "";
    }
    @Override
    public String toString() {
        return " " + day_number+":"+month+":"+year + " | " + hour + "-" + minute + "-" + second;
    }
}
