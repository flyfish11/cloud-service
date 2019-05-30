package com.cloud.common.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;



public class DateUtils {
    public static final String TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY = "yyyy";
    public static final String HHMMSS = "HH:mm:ss";
    public static final String HHMM = "HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMdd HH:mm:ss";
    /**
     * 中文时间格式（2015年01月02日）
     **/
    public static final String FORMAT_ZH = "yyyy年MM月dd日";
    public static final long SECONDS_IN_DAY = 1000 * 3600 * 24;

    public static void main(String[] args) {
        String str = "1][2][3";
        str = str.replace("][", ",");
        String[] aa = str.split(",");
        System.out.println(aa.toString());
    }

    /**
     * 获得当前时间，格式自定义
     *
     * @param pattern 传入格式(yyyy-MM-dd HH:mm:ss|yyyy-MM-dd|yyyyMMdd|HH:mm:ss|yyyyMMdd HH:mm:ss|yyyy年MM月dd日)
     * @return date 针对相应格式返回时间字符串
     */
    public static String getCurrentDate(String pattern) {
        Calendar day = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        formatTimeForAsia(format);
        String date = format.format(day.getTime());
        return date;
    }

    /**
     * 获得当前时间:yyyy-MM-dd HH:mm:ss
     *
     * @return date
     */
    public static Date getCurrentDate() {
        String date = getCurrentDate(TIME_PATTON_DEFAULT);
        return parse(date);
    }

    /**
     * 取当月的第一天
     *
     * @return
     */
    public static String firstDayOfMonth() {
        return format(new Date(), "yyyy-MM-01");
    }

    /**
     * 取当月的最后一天
     *
     * @return
     */
    public static String lastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return format(new Date(), "yyyy-MM-" + maxDay);
    }

    /**
     * 将字符串转换成Date 格式可以是以下几种：yyyy-MM-dd；yyyy-MM-dd HH:mm:ss； yyyyMMdd
     * HH:mm:ss；yyyyMMdd；HH:mm:ss
     *
     * @param strDate 字符串
     * @return date
     */
    public static Date parse(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        String pattern = DATE_PATTON_DEFAULT;
        if (strDate.indexOf("-") > 0 && strDate.indexOf(":") > 0) {
            pattern = TIME_PATTON_DEFAULT;
        } else if (strDate.indexOf("-") < 0 && strDate.indexOf(" ") > 0 && strDate.indexOf(":") > 0) {
            pattern = "yyyyMMdd HH:mm:ss";
        } else if (strDate.indexOf("-") > 0 && strDate.indexOf(":") < 0) {
            pattern = DATE_PATTON_DEFAULT;
        } else if (strDate.indexOf("-") < 0 && strDate.indexOf(":") < 0) {
            pattern = YYYYMMDD;
        } else if (strDate.indexOf("-") < 0 && strDate.indexOf(":") > 0) {
            pattern = HHMMSS;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(strDate);
        } catch (ParseException pe) {
            newDate = null;
        }
        return newDate;
    }

    /**
     * 将字符串转换成Timestamp
     *
     * @param dateStr 格式化的字符串
     * @return Timestamp
     */
    public static Timestamp parse2Timestamp(String dateStr) {
        Date date = parse(dateStr);
        if (date == null) {
            return null;
        } else {
            return new Timestamp(date.getTime());
        }
    }

    /**
     * 将date格式化成字符串，格式2012-09-12
     *
     * @param date 需要格式化的日期
     * @return 字符串，格式yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        return format(date, DATE_PATTON_DEFAULT);
    }

    /**
     * 将date格式化成字符串，格式2012-09-12 14:24:23
     *
     * @param date 需要格式化的日期
     * @return 字符串，格式yyyy-MM-dd HH:mm:ss
     */
    public static String formatTime(Date date) {
        return format(date, TIME_PATTON_DEFAULT);
    }

    /**
     * 将Date转化成指定格式的字符串
     *
     * @param date    需要格式化的日期
     * @param pattern 格式
     *                TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss"
     *                DATE_PATTON_DEFAULT = "yyyy-MM-dd"
     *                YYYYMMDD = "yyyyMMdd"
     *                HHMMSS = "HH:mm:ss"
     *                YYYYMMDDHHMMSS = "yyyyMMdd HH:mm:ss"
     * @return 字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }

        SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(pattern);

        return dateFromat.format(date);
    }

    /**
     * 取得指定日期N天后的日期
     *
     * @param date 指定日期
     * @param days 增加的天数，负数表示减
     * @return date
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 date1
     * @param date2 date2
     * @return int 相差的天数
     */
    public static int daysBetween(Date date1, Date date2) {
        long between_days = (date2.getTime() - date1.getTime()) / SECONDS_IN_DAY;
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 设置中国时区时间
     *
     * @param format
     * @author huangqx
     * 2015-5-11
     */
    public static void formatTimeForAsia(SimpleDateFormat format) {
        TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// 获取中国的时区
        format.setTimeZone(timeZoneChina);// 设置系统时区
    }

    /**
     * 使用指定的模式字符串解析日期字符串
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateStr);
        } catch (ParseException pe) {
            newDate = null;
        }
        return newDate;
    }
}
