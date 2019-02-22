package com.tscloud.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author yang.liu1
 * @date 2017/7/28
 */
public class ViewUtil<K, V> {

    public List<Object> mapToTree(Map<K, V> map) {
        List<Object> result = Lists.newArrayList();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            Object value = entry.getValue();
            Object children;
            if (value instanceof Map) {
                Map<K, V> childMap = (Map<K, V>) value;
                children = mapToTree(childMap);
            } else {
                children = value;
            }
            Map<String, Object> resMap = Maps.newHashMap();
            resMap.put("name", entry.getKey());
            resMap.put("children", children);
            resMap.put("isParent", true);
            resMap.put("open", true);
            result.add(resMap);
        }

        return result;
    }
}
