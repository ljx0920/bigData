package com.tscloud.common;

/**
 * @author weijun.yu
 */
public class Constants {

    private static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    private static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH";
    private static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
    private static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_FORMAT_TIME = "HH:mm:ss";
    private static final String DATE_FORMAT_WEEKDAY = "EEEE";

    public enum DATE_FORMAT {
        // 平台指定的时间格式字符串
        E_DATE_FORMAT_DAY(DATE_FORMAT_DAY),
        E_DATE_FORMAT_HOUR(DATE_FORMAT_HOUR),
        E_DATE_FORMAT_MINUTE(DATE_FORMAT_MINUTE),
        E_DATE_FORMAT_SECOND(DATE_FORMAT_SECOND),
        E_DATE_FORMAT_MILLIS(DATE_FORMAT_MILLIS),
        E_DATE_FORMAT_TIME(DATE_FORMAT_TIME),
        E_DATE_FORMAT_WEEKDAY(DATE_FORMAT_WEEKDAY);

        private final String strFormat;

        DATE_FORMAT(String strFormat) {
            this.strFormat = strFormat;
        }

        public String getStrFormat() {
            return strFormat;
        }
    }
}
