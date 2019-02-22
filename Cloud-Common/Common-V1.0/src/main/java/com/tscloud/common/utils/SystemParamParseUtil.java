package com.tscloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tscloud.common.framework.Constants;

import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author yang.liu1
 * @date 2017/7/25
 */
public class SystemParamParseUtil {

    /**
     * 将类型解析成map
     *
     * @param type
     * @return
     */
    public static Map<String, String> parseTypeToMap(String type) {
        Map<String, String> map = Maps.newLinkedHashMap();
        String[] types = type.split(Constants.SystemParam.COMMA_SPLIT);
        Arrays.stream(types).forEach(t -> {
            String[] data = t.split(Constants.SystemParam.COLON_SPLIT);
            map.put(data[0], data[1]);
        });
        return map;
    }

    /**
     * 将类型解析成json
     *
     * @param type
     * @return
     */
    public static JSONObject parseTypeToJson(String type) {
        JSONObject json = new JSONObject();
        Map<String, String> map = parseTypeToMap(type);
        map.forEach(json::put);
        return json;
    }

    /**
     * 字符串 过滤基础类型"00",返回单一数据类型
     *
     * @param sourceType
     * @return
     */
    public static String filterBasicType(String sourceType) {
        String retSourceType = "";
        StringTokenizer st = new StringTokenizer(sourceType, Constants.SystemParam.COMMA_SPLIT);
        //获取除了基础的其他单一数据类型  sourceType
        while (st.hasMoreTokens()) {
            String dbType = st.nextToken();
            //过滤基本信息
            if (!"00".equals(dbType)) {
                retSourceType = dbType;
            }
        }
        return retSourceType;
    }
}
