package com.tscloud.common.framework.config;

import com.tscloud.common.framework.Constants;
import com.tscloud.common.utils.DateUtil;
import com.tscloud.common.utils.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 资源文件帮助类，加载配置信息
 *
 * @author daowan.hu
 * @date 2016/10/27
 */
public class ConfigHelper {
    private static final Logger log = LogManager.getLogger(ConfigHelper.class);

    private static Properties properties;

    /**
     * 容器参数
     */
    private static Properties serverProperties;

    private static String filePath;

    static {
        System.out.println("[" + DateUtil.getDateTimeInSecond() + "] Loading config.properties");
        try {
            filePath = FileUtil.getFilePath("config.properties", Constants.Env.BASE_HOME);
            if (filePath != null) {
                //系统参数配置
                properties = loadProperties("config.properties");
            }
            String jettyServerFilePath = FileUtil.getFilePath("jettyServer.properties", Constants.Env.BASE_HOME);
            if (jettyServerFilePath != null) {
                //容器参数配置
                serverProperties = loadProperties("jettyServer.properties");
            }
        } catch (Exception e) {
            //ignore
        }
    }

    private static Properties loadProperties(String fileName) {
        Properties prop = new Properties();
        try {
            File file = FileUtil.getFile(fileName, Constants.Env.BASE_HOME);
            if (file != null) {
                InputStream input = new FileInputStream(file);
                prop.load(input);
            }
        } catch (Exception e) {
            log.error("Loading config.properties fails", e);
        }

        return prop;
    }

    /**
     * 加载系统参数
     *
     * @param key
     * @return
     */
    public static String getJettyParameter(String key) {
        return serverProperties.getProperty(key);
    }

    /**
     * 根据Key  得到config文件中key对应的数据
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        String value = null;
        try {
            value = properties.getProperty(key);
        } catch (Exception e) {
            log.error("key:" + key + " 资源参数加载失败！", e);
        }
        return value;
    }

    /**
     * @param key
     * @param value
     */
    public static void setProperties(String key, String value) {
        try {
            FileInputStream input = new FileInputStream(filePath);
            SafeProperties safeProp = new SafeProperties();
            safeProp.load(input);
            input.close();
            if (!"".equals(value) && value != null) {
                safeProp.put(key, value);
            }
            if (key != null) {
                if (value == null || "".equals(value)) {
                    safeProp.remove(key);
                }
            }
            FileOutputStream output = new FileOutputStream(filePath);
            safeProp.store(output, null);
            output.close();
        } catch (Exception e) {
            log.error("Visit " + filePath + " for updating " + key + " value error", e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void removeProperties(String key) {
        try {
            FileInputStream input = new FileInputStream(filePath);
            SafeProperties safeProp = new SafeProperties();
            safeProp.load(input);
            input.close();
            if (key != null) {
                safeProp.remove(key);
            }
            FileOutputStream output = new FileOutputStream(filePath);
            safeProp.store(output, null);
            output.close();
        } catch (Exception e) {
            log.error("Visit " + filePath + " for updating " + key + " value error", e.getMessage());
        }
    }
}
