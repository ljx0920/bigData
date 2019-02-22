package com.iov.common.framework.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author weijun.yu
 */
public class ServiceBeanContext {

    private static volatile ApplicationContext ctx = null;

    private ServiceBeanContext() {
    }

    public static void loadContext(ApplicationContext context) {
        ctx = context;
    }

    public static <T> T getBean(String name) {
        return (T) ctx.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }
}
