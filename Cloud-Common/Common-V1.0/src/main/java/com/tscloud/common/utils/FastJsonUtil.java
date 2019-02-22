package com.tscloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * @author LiuWangChenG
 * Description: 提供fastjson数据格式转换公用类
 * Date: 2015/4/16
 * Mail: liuwangcheng@leador.com.cn
 */
public class FastJsonUtil {
    private static final SerializeConfig CONFIG;
    private static final SerializerFeature[] FEATURES = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullStringAsEmpty,
            /*
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty,
            // 默认输出日期格式yyyy-MM-dd HH:mm:ss"
            SerializerFeature.WriteDateUseDateFormat,
            */
            // List数组转换格式不通过"$ref"来表示
            SerializerFeature.DisableCircularReferenceDetect
    };

    static {
        CONFIG = new SerializeConfig();
        // 使用和json-lib兼容的日期输出格式
        CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        // 输出日期格式
        CONFIG.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, CONFIG, FEATURES);
    }
}
