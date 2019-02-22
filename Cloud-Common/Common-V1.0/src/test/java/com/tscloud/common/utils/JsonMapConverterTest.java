package com.tscloud.common.utils;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static com.tscloud.common.utils.JsonMapConverter.map2JsonStr;
import static com.tscloud.common.utils.JsonMapConverter.simpleMapToJsonStr;

public class JsonMapConverterTest {

    @Test
    public void test() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("a", "aaaa");
        map.put("b", 123);
        map.put("c", true);
        System.out.println(map2JsonStr(map));
    }

    public static void main(String args[]) {
        Map<String, String> map = Maps.newHashMap();
        map.put("name", "12342");
        map.put("pass", "4355");
        map.put("jos", "999");
        map.put("wang", "999");
        JsonMapConverter jsonType = new JsonMapConverter();
        String mm = simpleMapToJsonStr(map);
//        System.out.println(mm);

        Map<String, Object> mp = jsonType.getData(mm);
        System.out.println(mp.get("wang"));
    }
}
