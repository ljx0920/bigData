package com.tscloud.common.tool.datetool;

import com.google.common.collect.Lists;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiancai.wang
 * @date 2016/8/30
 */
public class CalendarUtil {

    /**
     * 根据需求计算开始时间和结束时间之间的时间区间
     *
     * @param mode   Month/Week/Day
     * @param period 周期
     * @return
     */
    public static List<Date> getTimeRange(String mode, Integer period) {

        // 时间倒退的集合
        List<Date> timeRanges = Lists.newLinkedList();
        switch (mode) {
            case "Month":
                for (int i = 0; i < period; i++) {
                    timeRanges.add(getStartDateOfMonth(i));
                    timeRanges.add(getEndDateOfMonth(i));
                }
                break;
            case "Week":
                for (int i = 0; i < period; i++) {
                    timeRanges.add(getStartDateOfWeek(i));
                    timeRanges.add(getEndDateOfWeek(i));
                }
                break;
            case "Day":
                for (int i = 0; i < period; i++) {
                    timeRanges.add(getStartDateOfDay(i));
                    timeRanges.add(getEndDateOfDay(i));
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        // 重新排序  由小到大 [start,end,start,end ....]
        timeRanges = timeRanges.stream()
                .sorted((d1, d2) -> {
                    if (d1.getTime() >= d2.getTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .collect(Collectors.toList());
        return timeRanges;
    }

    /**
     * 根据需求计算开始时间和结束时间之间的时间区间
     *
     * @param mode   Month/Week/Day
     * @param period 周期
     * @return
     */
    public static List<Date> getStartTimeList(String mode, Integer period) {
        // 时间倒退的集合
        List<Date> timeRanges = Lists.newLinkedList();
        switch (mode) {
            case "Month":
                for (int i = 0; i < period; i++) {
                    //  timeRanges.add(getStartDateOfMonth(i));
                    timeRanges.add(getEndDateOfMonth(i));
                }
                break;
            case "Week":
                for (int i = 0; i < period; i++) {
                    // timeRanges.add(getStartDateOfWeek(i));
                    timeRanges.add(getEndDateOfWeek(i));
                }
                break;
            case "Day":
                for (int i = 0; i < period; i++) {
                    //  timeRanges.add(getStartDateOfDay(i));
                    timeRanges.add(getEndDateOfDay(i));
                }
                break;
            case "Hour":
                for (int i = 0; i < period * 24; i++) {
                    //  timeRanges.add(getStartDateOfDay(i));
                    timeRanges.add(getEndDateOfHour(i));
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        // 重新排序  由小到大 [start,end,start,end ....]
        timeRanges = timeRanges.stream()
                .sorted((d1, d2) -> {
                    if (d1.getTime() >= d2.getTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .collect(Collectors.toList());
        return timeRanges;
    }

    public static Date getStartTime(String mode, Integer period) {
        Date date = null;
        switch (mode) {
            case "Month":
                date = getEndDateOfMonth(period - 1);
                break;

            case "Week":
                date = getEndDateOfWeek(period - 1);
                break;
            case "Day":
                date = getEndDateOfDay(period - 1);
                break;
            case "Hour":
                date = getEndDateOfHour(period - 1);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return date;
    }

    /**
     * 获取指统计借宿时间
     *
     * @return
     */
    public static Date getEndTime() {
        return getEndDateOfDay(-1);
    }

    /**
     * 获取当天的结束时间
     *
     * @return
     */
    public static Date getEndDateOfHour(Integer period) {

        /*
         * **时间倒退** 结束时间也就是 00:00:00
         * 昨天 00:00:00
         */
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR, -period - 1);// 往前推period小时
        return (Date) calendar.getTime().clone();
    }

    /**
     * 获取指定月的开始时间
     *
     * @param period
     * @return
     */
    public static Date getStartDateOfMonth(Integer period) {

        /*
         * **时间倒退**
         * period = 0 ：开始时间就是结束昨天的23:59:59
         * default ： 每个月最后一天的 23:59:59
         *
         */
        if (period == 0) {
            return getStartDateOfDay(period);
        }
        Calendar calendar = Calendar.getInstance();
        // 本月的第一天减去一天的最后时刻
        calendar.add(Calendar.MONTH, -(period - 1));
        calendar.set(Calendar.DATE, 1);
        // 减去一天
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取指定月前的结束时间
     *
     * @param period
     * @return
     */
    public static Date getEndDateOfMonth(Integer period) {

        /*
         * **时间倒退**
         * 上月的开始时刻 01-00:00:00
         */
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -period);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当周的开始时间
     *
     * @return
     */
    public static Date getStartDateOfWeek(Integer period) {

        /*
         * 时间倒退： 这周的开始时间也就是昨天的 23:59:59
         * period = 0 ： 是昨天的 23:59:59
         * default: 是每周末的 23:59:59
         */
        if (period == 0) {
            return getStartDateOfDay(period);
        }
        Calendar calendar = new GregorianCalendar();

        // 倒退一周
        calendar.add(Calendar.WEEK_OF_YEAR, -period);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return (Date) calendar.getTime().clone();
    }

    /**
     * 获取当周的开始时间
     *
     * @return
     */
    public static Date getEndDateOfWeek(Integer period) {

        /*
         * 时间倒退： 结束时间是周末00:00:00
         * period = 0 ： 是本周的周日开始时间
         * default: 是每周末的 00:00:00
         */
        Calendar calendar = new GregorianCalendar();

        calendar.add(Calendar.WEEK_OF_YEAR, -period);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return (Date) calendar.getTime().clone();
    }

    /**
     * 获取当天的开始时间
     *
     * @return
     */
    public static Date getStartDateOfDay(Integer period) {

        /*
         * 记住是**倒退** 开始时间都是 23:59:59
         * 昨天 23:59:59
         *
         */
        Calendar calendar = new GregorianCalendar();

        calendar.add(Calendar.DATE, -period - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return (Date) calendar.getTime().clone();
    }

    /**
     * 获取当天的结束时间
     *
     * @return
     */
    public static Date getEndDateOfDay(Integer period) {

        /*
         * **时间倒退** 结束时间也就是 00:00:00
         * 昨天 00:00:00
         */
        Calendar calendar = new GregorianCalendar();

        // 往前推period几天
        calendar.add(Calendar.DATE, -period - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (Date) calendar.getTime().clone();
    }

    /**
     * 获取指定天的开始时间
     *
     * @param date
     * @param period
     * @return
     */
    public static Date getStartDateOfDay(Date date, int period) {

        /*
         * 记住是**倒退** 开始时间都是 23:59:59
         * 昨天 23:59:59
         *
         */
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        calendar.add(Calendar.DATE, -period - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return (Date) calendar.getTime().clone();
    }

    /**
     * 获取指定天的结束时间
     *
     * @param date
     * @param period
     * @return
     */
    public static Date getEndDateOfDay(Date date, int period) {

        /*
         * **时间倒退** 结束时间也就是 00:00:00
         * 昨天 00:00:00
         */
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        // 往前推period几天
        calendar.add(Calendar.DATE, -period - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (Date) calendar.getTime().clone();
    }

    /**
     * 判断是否为同一天
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean areSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }
}
