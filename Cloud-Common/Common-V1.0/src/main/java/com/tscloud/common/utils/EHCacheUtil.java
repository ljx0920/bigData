package com.tscloud.common.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * EHCache工具类
 *
 * @author wanshan.hu
 */
public class EHCacheUtil {
    private static Logger log = LogManager.getLogger(EHCacheUtil.class);

    /**
     * 元素最大数量
     */
    private static final int MAX_ELEMENTS_IN_MEMORY = 50000;
    /**
     * 是否把溢出数据持久化到硬盘
     */
    private static final boolean OVERFLOW_TO_DISK = false;
    /**
     * 是否会死亡
     */
    private static final boolean ETERNAL = false;
    /**
     * 缓存的间歇时间
     */
    private static final int TIME_TO_IDLE_SECONDS = 0;
    /**
     * 存活时间(默认一天86400)
     */
    private static final int TIME_TO_LIVE_SECONDS = 0;
    /**
     * 需要持久化到硬盘否
     */
    public static final boolean DISK_PERSISTENT = false;
    /**
     * 内存存取策略
     */
    public static final String MEMORY_STORE_EVICTION_POLICY = "LFU";

    private static CacheManager cacheManager = null;

    static {
        EHCacheUtil.initCacheManager();
    }

    /**
     * 初始化缓存管理容器
     */
    private static void initCacheManager() {
        try {
            if (cacheManager == null) {
                cacheManager = CacheManager.getInstance();
            }
        } catch (Exception e) {
            log.error("EHCacheUtil initCacheManager error.", e);
        }
    }

    /**
     * 初始化cache
     */
    public static Cache initCache(String cacheName) throws Exception {
        return initCache(cacheName, 0);
    }

    /**
     * 初始化cache
     *
     * @param cacheName         cache的名字
     * @param timeToLiveSeconds 有效时间
     * @return cache 缓存
     * @throws Exception
     */
    public static Cache initCache(String cacheName, long timeToLiveSeconds) throws Exception {
        return initCache(cacheName, MAX_ELEMENTS_IN_MEMORY, OVERFLOW_TO_DISK, ETERNAL, timeToLiveSeconds, TIME_TO_IDLE_SECONDS);
    }

    /**
     * 初始化缓存
     *
     * @param cacheName           缓存名称
     * @param maxElementsInMemory 元素最大数量
     * @param overflowToDisk      是否持久化到硬盘
     * @param eternal             是否会死亡
     * @param timeToLiveSeconds   缓存存活时间
     * @param timeToIdleSeconds   缓存的间隔时间
     * @return 缓存
     * @throws Exception
     */
    private static Cache initCache(String cacheName, int maxElementsInMemory, boolean overflowToDisk, boolean eternal,
                                   long timeToLiveSeconds, long timeToIdleSeconds) throws Exception {
        checkCacheManager();
        Cache cache;
        try {
            cache = getCache(cacheName);
            if (cache == null) {
                Cache memoryOnlyCache = new Cache(cacheName, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds, timeToIdleSeconds);
                cacheManager.addCache(memoryOnlyCache);
                cache = getCache(cacheName);
            } else {
                CacheConfiguration config = cache.getCacheConfiguration();
                config.setTimeToLiveSeconds(timeToLiveSeconds);
                config.setMaxEntriesLocalHeap(maxElementsInMemory);
                config.setOverflowToDisk(overflowToDisk);
                config.setEternal(eternal);
                config.setTimeToIdleSeconds(timeToIdleSeconds);
            }
        } catch (Exception e) {
            log.error("EHCacheUtil initCache {} error.", cacheName, e);
            throw new Exception("CacheName:" + cacheName + "创建失败！");
        }
        return cache;
    }

    /**
     * 释放CacheManager
     */
    public static void shutdown() {
        cacheManager.shutdown();
    }

    /**
     * 修改缓存容器配置
     *
     * @param cacheName           缓存名
     * @param timeToLiveSeconds   有效时间
     * @param maxElementsInMemory 最大数量
     * @throws Exception
     */
    public static boolean modifyCache(String cacheName, long timeToLiveSeconds, int maxElementsInMemory) throws Exception {
        try {
            if (StringUtils.isNotBlank(cacheName) && timeToLiveSeconds != 0L && maxElementsInMemory != 0) {
                CacheManager myManager = CacheManager.create();
                Cache myCache = myManager.getCache(cacheName);
                CacheConfiguration config = myCache.getCacheConfiguration();
                config.setTimeToLiveSeconds(timeToLiveSeconds);
                config.setMaxEntriesLocalHeap(maxElementsInMemory);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("EHCacheUtil modifyCache {} error.", cacheName, e);
            throw new Exception("modify cache " + cacheName + " failed!!!");
        }
    }

    /**
     * 向指定容器中设置值
     *
     * @param cacheName 容器名
     * @param key       键
     * @param value     值
     * @return 返回真
     * @throws Exception 异常
     */
    public static boolean setValue(String cacheName, String key, Object value) throws Exception {
        checkCacheManager();
        try {
            Cache cache = getCache(cacheName);
            if (cache == null) {
                cache = initCache(cacheName);
            }
            cache.put(new Element(key, value));
            return true;
        } catch (Exception e) {
            log.error("EHCacheUtil setValue error, cacheName = {}, key = {}, value = {}.", cacheName, key, value, e);
            throw new Exception("set cache " + cacheName + " failed!!!");
        }
    }

    /**
     * 向指定容器中设置值
     *
     * @param cacheName         容器名
     * @param key               键
     * @param value             值
     * @param timeToLiveSeconds 存活时间
     * @return 真
     * @throws Exception 抛出异常
     */
    public static boolean setValue(String cacheName, String key, Object value, Integer timeToLiveSeconds) throws Exception {
        checkCacheManager();
        try {
            Cache cache = getCache(cacheName);
            if (cache == null) {
                cache = initCache(cacheName);
            }
            cache.put(new Element(key, value, TIME_TO_IDLE_SECONDS, timeToLiveSeconds));
            return true;
        } catch (Exception e) {
            log.error("EHCacheUtil setValue error, cacheName = {}, key = {}, value = {}.", cacheName, key, value, e);
            throw new Exception("EHCacheUtil setValue error, cacheName = " + cacheName + ", key = " + key + ", value = " + value, e);
        }
    }

    /**
     * 从ehcache的指定容器中取值
     *
     * @param key 键
     * @return 返回Object类型的值
     */
    public static Object getValue(String cacheName, String key) throws Exception {
        checkCacheManager();
        Object object = null;
        try {
            Cache cache = getCache(cacheName);
            if (cache == null) {
                cache = initMyCache(cacheName);
            }
            if (cache.get(key) != null) {
                object = cache.get(key).getObjectValue();
            }
        } catch (Exception e) {
            log.error("EHCacheUtil setValue error, cacheName = {}, key = {}.", cacheName, key, e);
            throw new Exception("EHCacheUtil setValue error, cacheName = " + cacheName + ", key = " + key, e);
        }
        return object;
    }

    private static Cache getCache(String cacheName) {
        checkCacheManager();
        return cacheManager.getCache(cacheName);
    }

    private static Cache initMyCache(String cacheName) throws Exception {
        return EHCacheUtil.initCache(cacheName, TIME_TO_LIVE_SECONDS);
    }

    /**
     * 删除EHCache容器中的元素
     *
     * @param cacheName 容器名
     * @param key       键
     * @return 真
     * @throws Exception 失败抛出异常
     */
    public static boolean removeElement(String cacheName, String key) throws Exception {
        checkCacheManager();
        try {
            Cache cache = getCache(cacheName);
            cache.remove(key);
            return true;
        } catch (Exception e) {
            log.error("EHCacheUtil removeElement error, cacheName = {}, key = {}.", cacheName, key, e);
            throw new Exception("remove cache " + cacheName + " failed!!!");
        }
    }

    /**
     * 删除指定容器中的所有元素
     *
     * @param cacheName 容器名
     * @return 真
     * @throws Exception 失败抛出异常
     */
    public static boolean removeAllElement(String cacheName) throws Exception {
        checkCacheManager();
        try {
            Cache cache = getCache(cacheName);
            cache.removeAll();
            return true;
        } catch (Exception e) {
            log.error("EHCacheUtil removeAllElement error, cacheName = {}.", cacheName, e);
            throw new Exception("remove cache " + cacheName + " failed!!!");
        }
    }

    /**
     * 移除cache
     *
     * @param cacheName
     */
    public static void removeCache(String cacheName) {
        checkCacheManager();
        Cache cache = getCache(cacheName);
        if (null != cache) {
            cacheManager.removeCache(cacheName);
        }
    }

    /**
     * 移除所有cache
     */
    public static void removeAllCache() {
        checkCacheManager();
        cacheManager.removeAllCaches();
    }

    public static void removeValue(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        checkCache(cache);
        cache.remove(key);
    }

    public static void removeAllValue(String cacheName) {
        removeAllValue(getCache(cacheName));
    }

    /**
     * 移除所有Element
     */
    private static void removeAllValue(Cache cache) {
        checkCache(cache);
        cache.removeAll();
    }

    /**
     * 获取所有的cache名称
     *
     * @return
     */
    public static String[] getAllCaches() {
        checkCacheManager();
        return cacheManager.getCacheNames();
    }

    /**
     * 获取Cache所有的Keys
     *
     * @param cacheName
     * @return
     */
    public static List getKeys(String cacheName) {
        return getKeys(getCache(cacheName));
    }

    private static List getKeys(Cache cache) {
        checkCache(cache);
        return cache.getKeys();
    }

    /**
     * 检测cacheManager
     */
    private static void checkCacheManager() {
        if (null == cacheManager) {
            throw new IllegalArgumentException("调用前请先初始化CacheManager值：EHCacheUtil.initCacheManager");
        }
    }

    private static void checkCache(Cache cache) {
        if (null == cache) {
            throw new IllegalArgumentException("调用前请先初始化Cache值：EHCacheUtil.initCache(参数)");
        }
    }

    public static void main(String[] arg) {
        // 初始化--必须
        try {
            EHCacheUtil.setValue("dataAccessCahe", "name", "12121");
            String nn = EHCacheUtil.getValue("dataAccessCahe", "name").toString();

            System.out.println(nn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}