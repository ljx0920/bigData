package com.tscloud.common.utils;

import com.tscloud.common.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.tscloud.common.Constants.DATE_FORMAT.*;

/**
 * 对日期的处理
 *
 * @author daowan.hu
 */
public class DateUtil {

    private static Date parse(String text, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(text);
    }

    /**
     * long转换为时间
     *
     * @param millSec
     * @return
     */
    public static String longToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(E_DATE_FORMAT_SECOND.getStrFormat());
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 时间转换为long时间戳
     *
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 将代表时间格式的文本转换为日期类型;
     *
     * @param text       待解析的文本，如"2017-12-08 13:48:29.123"
     * @param dateFormat 时间戳格式
     */
    public static Date parse(String text, Constants.DATE_FORMAT dateFormat) throws ParseException {
        return parse(text, dateFormat.getStrFormat());
    }

    public static String format(Date date, Constants.DATE_FORMAT dateFormat) {
        return new SimpleDateFormat(dateFormat.getStrFormat()).format(date);
    }

    /**
     * 将指定的时间戳转换为代表时间格式的文本；
     *
     * @param unixTime   unix时间戳，单位为ms；
     * @param dateFormat 时间戳格式，需要为DATE_FORMAT枚举
     */
    public static String format(long unixTime, Constants.DATE_FORMAT dateFormat) {
        return format(new Date(unixTime), dateFormat);
    }

    /**
     * 判断给定时间是否位于指定区间内
     *
     * @param baseDate  待判断的时间点
     * @param startDate 时间区间的开始时间点
     * @param endDate   时间区间的结束时间点
     * @return 如果给定的时间点位于区间内，则返回true，否则返回false.
     */
    public static boolean isBetween(Date baseDate, Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            throw new RuntimeException("Datetime exception: 结束时间不能早于开始时间!");
        }

        if (baseDate.before(startDate)) {
            return false;
        }

        if (baseDate.after(endDate)) {
            return false;
        }

        return true;
    }

    // --------------------------
    // 以下为过时方法，不建议使用
    // --------------------------

    /**
     * @param millSec 单位为毫秒
     * @return
     * @deprecated 将unix时间戳转换为单位到秒的时间
     */
    public static String format(long millSec) {
        return format(millSec, E_DATE_FORMAT_SECOND);
    }

    /**
     * 判断给定时间点是否处于指定区间内
     *
     * @param startTime
     * @param endTime
     * @return [-1: 给定时间点早于开始时间点, 0: 给定时间点处于区间内, 1: 给定时间点晚于结束时间点]
     */
    private static int timeLocation(long baseTime, long startTime, long endTime) {
        return baseTime < startTime ? -1 : (baseTime > endTime ? 1 : 0);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate 较小的时间
     * @param endDate   较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    private static int daysBetween(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        startDate = sdf.parse(sdf.format(startDate));
        endDate = sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 获得当前系统日期
     *
     * @return
     * @author hudaowan Sep 29, 2006 10:43:22 AM
     */
    public static String getDate() {
        return new SimpleDateFormat(E_DATE_FORMAT_DAY.getStrFormat()).format(new Date());
    }

    /**
     * 得到当前系统时间和日期
     *
     * @return
     * @author hudaowan 2006-10-13 上午09:55:24
     */
    public static String getDateTimeInSecond() {
        return new SimpleDateFormat(E_DATE_FORMAT_SECOND.getStrFormat()).format(new Date());
    }

    /**
     * 将当前系统时间转换成 Millisecond 的样式
     */
    public static String getDateTimeInMillis() {
        return new SimpleDateFormat(E_DATE_FORMAT_MILLIS.getStrFormat()).format(new Date());
    }

    /**
     * 获得当前系统时间
     *
     * @return
     * @author hudaowan Sep 29, 2006 11:06:57 AM
     */
    public static String getTime() {
        return new SimpleDateFormat(E_DATE_FORMAT_TIME.getStrFormat()).format(new Date());
    }

    /**
     * 将给定日期转换为至秒的时间
     */
    public static String getDate(Date dDate) {
        return new SimpleDateFormat(E_DATE_FORMAT_DAY.getStrFormat()).format(dDate);
    }

    /**
     * 将给定日期转换为至秒的时间
     *
     * @param date
     * @return
     */
    public static String getDateTimeInSecond(Date date) {
        return new SimpleDateFormat(E_DATE_FORMAT_SECOND.getStrFormat()).format(date);
    }

    /**
     * 将给定日期转换为至毫秒的时间
     *
     * @param date
     * @return
     */
    public static String getDateTimeInMillis(Date date) {
        return new SimpleDateFormat(E_DATE_FORMAT_MILLIS.getStrFormat()).format(date);
    }

    /**
     * 根据指定的日期格式显示日期
     *
     * @param date
     * @param format
     * @return
     * @author hudaowan Sep 29, 2006 10:21:46 AM
     */
    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 添加秒
     */
    public static Date addSecond(String date, long amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        cal.add(Calendar.SECOND, (int) amount);
        return cal.getTime();
    }

    /**
     * 添加分
     */
    public static Date addMinute(String date, long amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        cal.add(Calendar.MINUTE, (int) amount);
        return cal.getTime();
    }

    /**
     * 添加小时
     *
     * @param date
     * @param amount
     * @return
     * @throws ParseException
     */
    public static Date addHour(String date, long amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        cal.add(Calendar.HOUR_OF_DAY, (int) amount);
        return cal.getTime();
    }

    /**
     * 添加天数
     */
    public static Date addDay(String date, long amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        cal.add(Calendar.DAY_OF_MONTH, (int) amount);
        return cal.getTime();
    }

    /**
     * 添加 周
     */
    public static Date addWeek(String date, long amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        cal.add(Calendar.WEEK_OF_YEAR, (int) amount);
        return cal.getTime();
    }

    /**
     * 添加 月
     */
    public static Date addMonth(String date, int amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        int month = cal.get(Calendar.MONTH);
        month += amount;
        int year = month / 12;
        month %= 12;
        cal.set(Calendar.MONTH, month);
        if (year != 0) {
            int oldYear = cal.get(Calendar.YEAR);
            cal.set(Calendar.YEAR, year + oldYear);
        }
        return cal.getTime();
    }

    /**
     * 添加 年
     *
     * @param date
     * @param amount
     * @return
     * @author hudaowan Sep 29, 2006 10:17:14 AM
     */
    public static Date addYear(String date, int amount) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date, E_DATE_FORMAT_MINUTE.getStrFormat()));
        int oldYear = cal.get(Calendar.YEAR);
        cal.set(Calendar.YEAR, amount + oldYear);
        return cal.getTime();
    }

    /**
     * 得到当前年
     *
     * @author liuxd
     * @date 2011-04-20
     */
    public static int getYear(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 得到当前月
     *
     * @param dDate
     * @return
     */
    public static int getMonth(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 得到当前日期是星期几
     *
     * @param dDate
     * @return
     * @author hudaowan 2006-10-27 下午01:26:32
     */
    public static int getWeek(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param date 指定的日期
     * @return ["星期一","星期二",...]
     */
    public static String getWeekInChn(Date date) {
        return new SimpleDateFormat(E_DATE_FORMAT_WEEKDAY.getStrFormat()).format(date);
    }

    /**
     * 得到当前日期
     *
     * @param dDate
     * @return
     * @author hudaowan 2006-10-27 下午01:34:37
     */
    public static int getDayOfMonth(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前小时
     *
     * @param timeStamp
     * @return
     */
    public static int getHourOfDay(Date timeStamp) {
        if (timeStamp == null) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStamp);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到当前时间的分钟
     *
     * @param timeStamp
     * @return
     */
    public static int getMinuteOfHour(Date timeStamp) {
        if (timeStamp == null) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStamp);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 得到当前季度 1 第一季度  2 第二季度 3 第三季度 4 第四季度
     *
     * @author liuxd
     * @date 2011-04-20
     */
    public static int getSeason() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                return 1;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                return 2;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return 3;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                return 4;
            default:
                return 0;
        }
    }
}
