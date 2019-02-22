package com.tscloud.common.framework.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author daowan.hu
 */
public class ServiceBeanContext {

    private static volatile ServiceBeanContext context = null;

    private static volatile ApplicationContext ctx = null;

    private ServiceBeanContext() {
    }

    public static ServiceBeanContext getInstance() {
        if (context == null) {
            synchronized (ServiceBeanContext.class) {
                if (context == null) {
                    context = new ServiceBeanContext();
                }
            }
        }
        return context;
    }

    public void loadContext(String path) {
        ctx = new ClassPathXmlApplicationContext(path);
    }

    public <T> T getBean(String name) {
        Object object = ctx.getBean(name);
        if (object != null) {
            return (T) object;
        }
        return null;
    }
}
