package com.tscloud.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guideshiny
 */
public class ResourceUtils {

    private static Logger logger = LogManager.getLogger(ResourceUtils.class);

    public static String getFilePath(String filePath) {
        int startIdx = filePath.lastIndexOf('/');
        return filePath.substring(0, startIdx);
    }

    public static String getSecurityPath() {
        return getClassPath() + "/security";
    }

    /**
     * Who call this, and then use the caller's class path.
     *
     * @return
     */
    private static String getClassPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return path.substring(0, path.length() - 1);
    }
}
