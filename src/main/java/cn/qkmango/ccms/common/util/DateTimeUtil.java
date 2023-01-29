package cn.qkmango.ccms.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 18:53
 */
public class DateTimeUtil {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @param timestamp 时间戳
     * @param day       前/后几天，正数为后，负数为前
     * @return 日期
     */
    public static Calendar addDay(long timestamp, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return transition(calendar, day, false);
    }

    /**
     * @param date 日期
     * @param day  前/后几天，正数为后，负数为前
     * @return 日期
     */
    public static Calendar addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return transition(calendar, day, false);
    }

    /**
     * @param timestamp 时间戳
     * @param day       前/后几天，正数为后，负数为前
     * @param start     是否为当天的开始时间
     * @return 日期
     */
    public static Calendar addDay(long timestamp, int day, boolean start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return transition(calendar, day, start);
    }

    /**
     * @param date  日期
     * @param day   前/后几天，正数为后，负数为前
     * @param start 是否为当天的开始时间
     * @return 日期
     */
    public static Calendar addDay(Date date, int day, boolean start) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return transition(calendar, day, start);
    }


    /**
     * 转换
     *
     * @param calendar 日期
     * @param day      前/后几天，正数为后，负数为前
     * @param start    是否为那天的开始时间
     * @return 日期
     */
    private static Calendar transition(Calendar calendar, int day, boolean start) {
        calendar.add(Calendar.DATE, day);
        //设置为那天的开始时间
        if (start) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        }
        return calendar;
    }
}
