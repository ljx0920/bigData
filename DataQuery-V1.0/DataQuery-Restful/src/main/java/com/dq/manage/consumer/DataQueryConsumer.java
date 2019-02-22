package com.dq.manage.consumer;

import com.dq.manage.provider.*;
import com.iov.common.framework.spring.ServiceBeanContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author yaju.jiao
 * @date 2018/9/3
 */
public class DataQueryConsumer {

    private static final Logger log = LogManager.getLogger(DataQueryConsumer.class);


    public static UserLoginProvider getUserLoginProvider() {
        try {
            return ServiceBeanContext.getBean(UserLoginProvider.class);
        } catch (Exception e) {
            log.error("获取 getUserLoginRestServer ServiceBean 失败！", e);
            return null;
        }
    }

    public static DataManagerConfigureProvider getDataManagerConfigureProvider() {
        try {
            return ServiceBeanContext.getBean(DataManagerConfigureProvider.class);
        } catch (Exception e) {
            log.error("获取 getDataManagerConfigureProvider ServiceBean 失败！", e);
            return null;
        }
    }

    public static UpLoadClassIfyProvider getUpLoadClassIfyProvider() {
        try {
            return ServiceBeanContext.getBean(UpLoadClassIfyProvider.class);
        } catch (Exception e) {
            log.error("获取 getUpLoadClassIfyProvider ServiceBean 失败！", e);
            return null;
        }
    }

    public static UploadFileItemProvider getUploadFileItemProvider() {
        try {
            return ServiceBeanContext.getBean(UploadFileItemProvider.class);
        } catch (Exception e) {
            log.error("获取 getUploadFileItemProvider ServiceBean 失败！", e);
            return null;
        }
    }
}
