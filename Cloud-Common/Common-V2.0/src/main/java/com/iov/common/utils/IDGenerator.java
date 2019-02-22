package com.iov.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author daowan.hu
 */
public class IDGenerator {

    private static int COUNT = 0;

    public static String getID() {
        UUID uid = UUID.randomUUID();
        String id = uid.toString();
        id = id.toUpperCase();
        id = id.replace("-", "");
        return id;
    }

    /**
     * 生成随机字母
     */
    public static String getRandomChar() {
        int randomInt;
        int pwdLength = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};
        final int maxNum = str.length;

        StringBuilder pwd = new StringBuilder();
        Random rand = new Random();
        while (pwdLength < 32) {
            randomInt = Math.abs(rand.nextInt(maxNum));
            if (randomInt >= 0 && randomInt < str.length) {
                pwd.append(str[randomInt]);
                pwdLength++;
            }
        }
        return pwd.toString();
    }

    /**
     * 获取26位唯一字符串,前15位距离19700101的时间毫秒数，后11单独计数值
     *
     * @return
     */
    public static synchronized String getUniqueID() {
        long millis = System.currentTimeMillis();
        return num2Str(millis, 15) + num2Str(++COUNT, 11);
    }

    /**
     * 数字按照指定长度
     *
     * @param number
     * @param width
     * @return
     */
    private static String num2Str(long number, int width) {
        String numStr = String.valueOf(number);

        int len = numStr.length() - width;
        if (len > 0) {
            numStr = numStr.substring(len);
        }

        width -= numStr.length();
        StringBuilder zeroBuff = new StringBuilder();
        while (zeroBuff.length() < width) {
            zeroBuff.append("0");
        }
        return zeroBuff.toString() + numStr;
    }

    public static synchronized String getDateUniqueID() {
        return num2Str(17) + num2Str(++COUNT, 2);
    }

    /**
     * 当前时间制定长度
     *
     * @param width
     * @return
     */
    public static String num2Str(int width) {
        String numStr = DateFormatUtils.format(new Date(), "HHmmssS");
        width -= numStr.length();
        StringBuilder zeroBuff = new StringBuilder();
        while (zeroBuff.length() < width) {
            zeroBuff.append("0");
        }
        return zeroBuff.toString() + numStr;
    }
}
