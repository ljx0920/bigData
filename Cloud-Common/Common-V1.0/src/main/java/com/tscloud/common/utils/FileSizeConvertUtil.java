package com.tscloud.common.utils;

/**
 * @author caicf
 * @date 2016/11/7
 */
public class FileSizeConvertUtil {

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    public static void main(String[] args) {
        long size = 8405478;
        System.out.println(convertFileSize(size));
    }
}
