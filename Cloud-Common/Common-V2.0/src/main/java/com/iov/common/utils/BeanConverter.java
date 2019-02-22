package com.iov.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 将实体Bean转换成Map
 *
 * @author huwanshan
 */
public class BeanConverter {

    public static Map<String, Object> convertBean(Object bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (bean != null) {
            Class type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, null);
                    }
                }
            }
        }
        return returnMap;
    }

    public static List<Map<String, Object>> convertBeanToList(List beans) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<Map<String, Object>> returnList = Lists.newArrayList();
        for (Object o : beans) {
            returnList.add(convertBean(o));
        }
        return returnList;
    }
}
