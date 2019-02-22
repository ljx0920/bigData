package com.tscloud.common.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.tscloud.common.Constants.DATE_FORMAT.*;

/**
 * @author wanshan.hu
 */
public class ConvertUtil {

    private static final String DATE_FORMAT_DAY = E_DATE_FORMAT_DAY.getStrFormat();
    private static final String DATE_FORMAT_MILLIS = E_DATE_FORMAT_MILLIS.getStrFormat();
    private static final String DEFAULT_DATE_FORMAT = E_DATE_FORMAT_MINUTE.getStrFormat();

    /**
     * 将对象转化为long型
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author huwanshan
     * @date 2010-12-11 下午07:53:52
     */
    public static long asLong(Object obj, long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(obj));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为int型
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author huwanshan
     * @date 2010-12-11 下午07:54:03
     */
    public static int asInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为double型
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author huwanshan
     * @date 2010-12-11 下午07:54:12
     */
    public static double asDouble(Object obj, double defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为float型
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author huwanshan
     * @date 2010-12-11 下午07:54:20
     */
    public static float asFloat(Object obj, float defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        try {
            return Float.parseFloat(String.valueOf(obj));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将对象转化为String型
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author huwanshan
     * @date 2010-12-11 下午07:54:28
     */
    public static String asString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }

    /**
     * 根据str内容转换成TimeStamp
     *
     * @param dateStr 时间字符串
     * @return sql timeStamp
     * @throws ParseException
     */
    public static Timestamp asSqlTimestamp(String dateStr) throws ParseException {
        SimpleDateFormat format;
        int len = dateStr.trim().length();

        if (dateStr.contains("-") && len > 10) {
            if (len == DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            } else if (len > DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat(DATE_FORMAT_MILLIS);
            } else {
                format = new SimpleDateFormat(DATE_FORMAT_DAY);
            }
            java.util.Date date = format.parse(dateStr.trim());
            return new Timestamp(date.getTime());
        } else if (dateStr.contains("年")) {
            format = new SimpleDateFormat("yyyy年MM月dd日");
            java.util.Date date = format.parse(dateStr.trim());
            return new Timestamp(date.getTime());
        } else if (dateStr.contains("/")) {
            java.util.Date date;
            if (len > 10) {
                format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                date = format.parse(dateStr.trim());
            } else {
                format = new SimpleDateFormat(DATE_FORMAT_DAY);
                date = format.parse(dateStr.trim());
            }
            return new Timestamp(date.getTime());
        }
        return null;
    }

    /**
     * 根据str内容转换成Date
     *
     * @param dateStr 时间字符串
     * @return sql date
     * @throws ParseException
     */
    public static Date asSqlDate(String dateStr) throws ParseException {
        SimpleDateFormat format;
        int len = dateStr.trim().length();
        if (dateStr.contains("-") && len > 10) {
            if (len == DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            } else if (len > DEFAULT_DATE_FORMAT.length()) {
                format = new SimpleDateFormat(DATE_FORMAT_MILLIS);
            } else {
                format = new SimpleDateFormat(DATE_FORMAT_DAY);
            }
            java.util.Date date = format.parse(dateStr.trim());
            return new Date(date.getTime());
        } else if (dateStr.contains("年")) {
            format = new SimpleDateFormat("yyyy年MM月dd日");
            java.util.Date date = format.parse(dateStr.trim());
            date = DateUtil.parse(DateUtil.formatDate(date, DATE_FORMAT_DAY), E_DATE_FORMAT_DAY);
            return new Date(date.getTime());
        } else if (dateStr.contains("/")) {
            java.util.Date date;
            if (len > 10) {
                format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                date = format.parse(dateStr.trim());
                date = DateUtil.parse(DateUtil.formatDate(date, DEFAULT_DATE_FORMAT), E_DATE_FORMAT_SECOND);
            } else {
                format = new SimpleDateFormat(DATE_FORMAT_DAY);
                date = format.parse(dateStr.trim());
                date = DateUtil.parse(DateUtil.formatDate(date, DATE_FORMAT_DAY), E_DATE_FORMAT_DAY);
            }
            return new Date(date.getTime());
        }
        return null;
    }
}
