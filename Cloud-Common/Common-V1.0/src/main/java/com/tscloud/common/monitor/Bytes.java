package com.tscloud.common.monitor;

/**
 * @author daowan.hu
 * @date 2016/10/27
 */
public class Bytes {
    public static String substring(String src, int startIdx, int endIdx) {
        byte[] b = src.getBytes();
        StringBuilder tgt = new StringBuilder();
        for (int i = startIdx; i <= endIdx; i++) {
            tgt.append((char) b[i]);
        }
        return tgt.toString();
    }
}
