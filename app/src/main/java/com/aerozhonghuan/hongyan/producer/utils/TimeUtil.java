package com.aerozhonghuan.hongyan.producer.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dell on 2017/7/4.
 */

public class TimeUtil {
    public static String getDate_HHmm(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }

    public static String getDate_MMddHHmm(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }

    public static String getDate_yyyyMMddHHmmss(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }
    public static String getDate_yyyyMMddTHHmmss(long time) {
        String date = "";
        String date1 = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = sdf.format(time);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        date1 = sdf1.format(time);
        return date+"T"+date1;
    }
    public static String getDate_yyyyMMddHHmm(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }

    public static String getDate_yyyyMMdd(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }

    public static String getDate_yyyyMMdd1(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }

    public static String getDate_yyyyMMdd2(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = sdf.format(time);
        return date;
    }

    /**
     * 当前时间
     *
     * @return
     */
    public static long now() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 字符串转时间Date
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    //string-->long
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    //string-->date
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    //date-->long
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
