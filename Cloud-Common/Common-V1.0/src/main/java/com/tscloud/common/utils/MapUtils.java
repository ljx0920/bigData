package com.tscloud.common.utils;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author daowan.hu
 */
public class MapUtils {
    /**
     * 将Map<String, String[]>转为Map<String, String>
     *
     * @param src
     * @param dest
     */
    public static void populate(Map<String, String[]> src, Map<String, String> dest) {
        for (Entry<String, String[]> e : src.entrySet()) {
            dest.put(e.getKey(), e.getValue()[0]);
        }
    }

    /**
     * 将a=2&b=3这种String转换为map
     *
     * @param str
     * @return
     */
    public static Map<String, String> string2Map(String str) {
        Map<String, String> params = Maps.newHashMap();
        if (str != null && str.length() > 0) {
            String[] pairs = str.split("&");
            for (String s : pairs) {
                String[] kv = s.split("=");
                if (kv.length == 2) {
                    params.put(kv[0], kv[1]);
                } else {
                    params.put(kv[0], null);
                }
            }
        }
        return params;
    }
}
