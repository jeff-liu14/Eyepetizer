package com.moment.eyepetizer.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by moment on 2018/1/5.
 */

public class TimeUtils {
    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time) {
        return timeFormat(time, "yyyy-MM-dd hh:mm:ss");
    }

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01 00:00:00";
    }

    /**
     * 1s==1000ms
     */
    private final static int TIME_MILLISECONDS = 1000;
    /**
     * 时间中的分、秒最大值均为60
     */
    private final static int TIME_NUMBERS = 60;
    /**
     * 时间中的小时最大值
     */
    private final static int TIME_HOURSES = 24;
    /**
     * 一个月按30天计算
     */
    private final static int TIME_DAYS = 30;

    public static String dateformat = "yyyy-MM-dd";
    public static String dateTimeformat = "yyyy-MM-dd HH:mm:ss";
    public static String dateTimeMillisformat = "yyyy-MM-dd HH:mm:ss:sss";

    public static String toSimpleDateString(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeformat, Locale.US);
        return sdf.format(dt);
    }

    public static String getNowStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeformat, Locale.US);
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formartDate(Date date) {
        return new SimpleDateFormat(dateformat).format(date);
    }

    /**
     * 毫秒数格式化日期
     *
     * @param millistimes
     * @return
     */
    public static String formartDate(long millistimes) {
        return new SimpleDateFormat(dateformat).format(millistimes);
    }

    public static long formartStringToMillinstimes(String time) {
        Date date = parseDate(time);
        return date.getTime();
    }

    /**
     * 转换日期
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat(dateformat).parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 格式化时间日期
     *
     * @param date
     * @return
     */
    public static String formartDateTime(Date date) {
        return new SimpleDateFormat(dateTimeformat).format(date);
    }

    /**
     * 转换时间日期
     *
     * @param dateTime
     * @return
     */
    public static Date parseDateTime(String dateTime) {
        try {
            return new SimpleDateFormat(dateTimeformat).parse(dateTime);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 格式化时间日期
     *
     * @param millistimes
     * @return
     */
    public static String formartDateTime(long millistimes) {
        return new SimpleDateFormat(dateTimeformat).format(millistimes);
    }

    /**
     * 格式化时间日期毫秒
     *
     * @param date
     * @return
     */
    public static String formartDateTimeMillis(Date date) {
        return new SimpleDateFormat(dateTimeMillisformat).format(date);
    }

    /**
     * 转换时间日期毫秒
     *
     * @param dateTime
     * @return
     */
    public static Date parseDateTimeMillis(String dateTime) {
        try {
            return new SimpleDateFormat(dateTimeformat).parse(dateTime);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取当前时间距离指定日期时差的大致表达形式
     *
     * @param millistimes 日期
     * @return 时差的大致表达形式
     */
    public static String getDiffTime(long millistimes) {
        String strTime;
        long diffTime = Math.abs(new Date().getTime() - millistimes);
        if (formartDate(millistimes).compareTo(formartDate(new Date())) >= 0) {
            if (diffTime < TIME_NUMBERS * TIME_MILLISECONDS) {
                strTime = "刚刚";
            } else {
                int diffMin = (int) (diffTime / (TIME_MILLISECONDS * TIME_NUMBERS));
                if (diffMin < TIME_NUMBERS) {
                    strTime = diffMin + "分钟前";
                } else {
                    strTime = (diffMin / TIME_NUMBERS) + "小时前";
                }
            }
        } else {
            int diffDay = (int) (diffTime / (TIME_MILLISECONDS * TIME_NUMBERS * TIME_NUMBERS * TIME_HOURSES));
            if (diffDay <= 1) {
                strTime = "昨天";
            } else if (diffDay < TIME_DAYS) {
                strTime = diffDay + "天前";
            } else {
                int diffMon = diffDay / TIME_DAYS;
                if (diffMon < 3) {
                    strTime = diffMon + "月前";
                } else {
                    strTime = new SimpleDateFormat(dateformat).format(millistimes);
                }
            }
        }

        return strTime;
    }

    /**
     * 比较两个日期相差的天数
     */
    public static String day(String d1, String d2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        long date1 = format.parse(d1).getTime();
        long date2 = format.parse(d2).getTime();
        long dayOfMills = date2 - date1;
        return dayOfMills / (24 * 60 * 60 * 1000) + "";
    }
}
