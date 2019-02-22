package com.tscloud.common.tool.dbtool;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Properties;

/**
 * @author daowan.hu
 */
public class DSPool {

    protected static Logger log = LogManager.getLogger(DSPool.class);

    private static Map<String, DruidDataSource> dsPools;

    private static final String MYSQL_QUERY_SQL = "SELECT 'X' ";
    private static final String ORACLE_QUERY_SQL = "SELECT 'X' FROM DUAL";
    private static final String MYSQL = "mysql";
    private static final String ORACLE = "oracle";

    static {
        dsPools = Maps.newConcurrentMap();
    }

    public static DruidDataSource getDataSource(String driveName, String url, String userName, String password) {
        String key = getKey(url, userName, password);
        return getDataSource(key, driveName, url, userName, password);
    }

    private static String getKey(String url, String userName, String password) {
        return url + "_" + userName + "_" + password;
    }

    private static DruidDataSource getDataSource(String id, String driveName, String url, String userName, String password) {
        if (!dsPools.containsKey(id)) {
            synchronized (DSPool.class) {
                if (!dsPools.containsKey(id)) {
                    DruidDataSource dataSource = createDataSource(driveName, url, userName, password);
                    if (dataSource != null) {
                        dsPools.put(id, dataSource);
                    }
                }
            }
        }
        return dsPools.get(id);
    }

    public static DruidDataSource createDataSource(String driveName, String url, String userName, String password) {
        DruidDataSource dataSource;
        try {
            dataSource = new DruidDataSource();

            dataSource.setDriverClassName(driveName);
            dataSource.setUrl(url);

            dataSource.setUsername(userName);
            dataSource.setPassword(password);

            //参数设置
            dataSource.setInitialSize(10);
            dataSource.setMaxActive(500);

            dataSource.setMinIdle(10);
            dataSource.setMaxWait(60 * 1000);

            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(false);
            dataSource.setTestOnReturn(false);

            dataSource.setRemoveAbandoned(true);
            //关闭长时间不使用的连接超时时间，单位秒
            dataSource.setRemoveAbandonedTimeout(180);

            //关闭abanded连接时输出错误日志
            dataSource.setLogAbandoned(true);

            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            dataSource.setTimeBetweenEvictionRunsMillis(60000);

            dataSource.setValidationQueryTimeout(20);
            Properties properties = new Properties();
            properties.setProperty("characterEncoding", "UTF-8");
            dataSource.setConnectProperties(properties);

            if (driveName.contains(MYSQL)) {
                dataSource.setValidationQuery(MYSQL_QUERY_SQL);
            } else if (driveName.contains(ORACLE)) {
                dataSource.setValidationQuery(ORACLE_QUERY_SQL);
            }
            //timeBetweenEvictionRunsMillis
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            dataSource.setTimeBetweenEvictionRunsMillis(1000 * 60 * 3);
            //配置一个连接在池中最小生存的时间，单位是毫秒
            dataSource.setMinEvictableIdleTimeMillis(1000 * 60 * 10);

            dataSource.init();
            log.info("init dataSource pool success,{userName:" + userName + ",password:" + password + ",url:" + url + "}");
        } catch (Exception e) {
            log.error("init dataSource pool fail,{userName:" + userName + ",password:" + password + ",url:" + url + "}," + e.getMessage(), e);
            dataSource = null;
        }

        return dataSource;
    }

    public static DruidDataSource removeDataSource(String url, String userName, String password) {
        String key = getKey(url, userName, password);
        return removeDataSource(key);
    }

    private static DruidDataSource removeDataSource(String id) {
        return dsPools.remove(id);
    }
}
