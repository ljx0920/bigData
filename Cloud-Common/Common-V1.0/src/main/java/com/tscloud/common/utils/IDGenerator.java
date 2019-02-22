package com.tscloud.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author daowan.hu
 */
public class IDGenerator {

    private static IDGenerator generator = new IDGenerator();

    private IDGenerator() {
    }

    public static IDGenerator getGenerator() {
        return generator;
    }

    private static int COUNT = 0;

    public static String getID() {
        UUID uid = UUID.randomUUID();
        String id = uid.toString();
        id = id.toUpperCase();
        id = id.replace("-", "");
        return id;
    }

    public static String getSixteenID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2];
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

    public static synchronized String getDateUniqueID() {
        return num2Str(17) + num2Str(++COUNT, 2);
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

    /**
     * 当前时间制定长度
     *
     * @param width
     * @return
     */
    public static String num2Str(int width) {
        String numStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        width -= numStr.length();
        StringBuilder zeroBuff = new StringBuilder();
        while (zeroBuff.length() < width) {
            zeroBuff.append("0");
        }
        return zeroBuff.toString() + numStr;
    }
}
