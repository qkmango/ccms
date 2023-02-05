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
    public static final SimpleDateFormat SIMPLE_DATE_WITH_MILLISECOND_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

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
        return addDay(calendar, day, false);
    }

    /**
     * @param calendar 日期
     * @param day      前/后几天，正数为后，负数为前
     * @return 日期
     */
    public static Calendar addDay(Calendar calendar, int day) {
        return addDay(calendar, day, false);
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
        return addDay(calendar, day, start);
    }

    /**
     * 转换
     *
     * @param calendar 日期
     * @param day      前/后几天，正数为后，负数为前
     * @param start    是否为那天的开始时间
     * @return 日期
     */
    public static Calendar addDay(Calendar calendar, int day, boolean start) {
        calendar.add(Calendar.DATE, day);
        //设置为那天的开始时间
        if (start) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        }
        return calendar;
    }

    /**
     * 获取当前月份起始时间
     */
    public static Calendar getMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取当前月份结束时间
     */
    public static Calendar getMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }


    /**
     * 获取当前周起始时间
     *
     * @param calendar 日期
     * @return 日期
     */
    public static Calendar getWeekStart(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取当前周结束时间
     *
     * @param calendar 日期
     * @return 日期
     */
    public static Calendar getWeekEnd(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    /**
     * 获取当前天起始时间
     *
     * @param calendar 日期 如果传入的日期为null，则默认当前时间入参
     * @return 日期，如果有入参，则返回入参对象
     */
    public static Calendar getDayStart(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取当前天结束时间
     *
     * @param calendar 日期
     * @return 日期
     */
    public static Calendar getDayEnd(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }
}
