package com.tscloud.common.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author caicf
 * @date 2016/11/3
 */
public class JsonMapConverter {

    public static String simpleMapToJsonStr(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "null";
        }
        StringBuilder jsonStr = new StringBuilder("{");
        for (Map.Entry<String, String> stringEntry : map.entrySet()) {
            jsonStr.append("\"").append(stringEntry.getKey()).append("\":\"").append(stringEntry.getValue()).append("\",");
        }
        jsonStr = new StringBuilder(jsonStr.substring(0, jsonStr.length() - 1));
        jsonStr.append("}");
        return jsonStr.toString();
    }

    public static String map2JsonStr(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "null";
        }
        StringBuilder jsonStr = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                jsonStr.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
            } else {
                jsonStr.append("\"").append(entry.getKey()).append("\":").append(entry.getValue()).append(",");
            }
        }
        jsonStr = new StringBuilder(jsonStr.substring(0, jsonStr.length() - 1));
        jsonStr.append("}");
        return jsonStr.toString();
    }

    /**
     * {"pass":"4355","name":"12342","wang":"fsf"}
     */
    public Map<String, Object> getData(String str) {
        String sb = str.substring(1, str.length() - 1);
        String[] name = sb.split("\\\",\\\"");
        String[] nn;
        Map<String, Object> map = Maps.newHashMap();
        for (String aName : name) {
            nn = aName.split("\\\":\\\"");
            map.put(nn[0], nn[1]);
        }
        return map;
    }
}
