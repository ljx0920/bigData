package com.tscloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tscloud.common.framework.rest.view.JsonViewObject;

import java.util.List;

/**
 * JsonViewObject utils class
 *
 * @author aihua.sun
 * @date 2015/2/10
 */
public class JsonViewObjectHelper {

    /**
     * 根据jsonViewObjectResultStr以及entity的class,生成对应的entity列表
     */
    public static List extractObjectList(String jsonViewObjectResultStr, Class cls) {
        List objectList = Lists.newArrayList();
        if (StringUtils.isNotBlank(jsonViewObjectResultStr)) {
            JsonViewObject viewObject = JSON.parseObject(jsonViewObjectResultStr, JsonViewObject.class);
            objectList = JSON.parseArray(viewObject.getContent().toString(), cls);
        }
        return objectList;
    }

    public static Object extractObject(String result, Class cls) {
        Object obj = new Object();
        if (StringUtils.isNotBlank(result)) {
            JsonViewObject viewObject = JSON.parseObject(result, JsonViewObject.class);
            obj = JSON.parseObject(viewObject.getContent().toString(), cls);
        }
        return obj;
    }
}
