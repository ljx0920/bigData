package com.tscloud.common.utils;

import java.io.*;
import java.text.NumberFormat;

/**
 * 数据转换
 *
 * @author huwanshan
 * @date 2015/4/28
 */
public class DataConvert {

    private static final String HEX_DECIMAL_LIST = "0123456789ABCDEF";

    /**
     * 字节转换InputStream
     *
     * @param in
     * @return
     */
    public static InputStream byteToInputStream(byte[] in) throws Exception {
        return new ByteArrayInputStream(in);
    }

    /**
     * file 转换  InputStream
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static InputStream fileToInputStream(File file) throws Exception {
        return byteToInputStream(fileToBytes(file));
    }

    /**
     * inStream 转换 byte
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] inputStreamToBytes(InputStream inStream) throws Exception {
        byte[] bytes;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            bytes = swapStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return bytes;
    }

    /**
     * file 转换 byte
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] fileToBytes(File file) throws Exception {
        byte[] bytes;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            bytes = inputStreamToBytes(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return bytes;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @return HexString
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray) {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制字符转字节
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] charArray = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(charArray[pos]) << 4 | toByte(charArray[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        return (byte) HEX_DECIMAL_LIST.indexOf(c);
    }

    /**
     * @param size
     * @return
     */
    public static String convertSize(long size) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String result = String.valueOf(size);
        if (size < 1024) {
            result = result + " B";
        } else if (size < 1024 * 1024) {
            result = nf.format(size / 1024.0) + " KB";
        } else if (size < 1024 * 1024 * 1024) {
            result = nf.format(size / 1024.0 / 1024.0) + " MB";
        } else {
            result = nf.format(size / 1024.0 / 1024.0 / 1024.0) + " GB";
        }
        return result;
    }

    public static long convertToGB(long size) {
        return size / 1024 / 1024 / 1024;
    }
}
