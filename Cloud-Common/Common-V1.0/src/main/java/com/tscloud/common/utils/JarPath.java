package com.tscloud.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;

/**
 * 获取当类所在的jar名称和jar路径
 *
 * @author huwanshan
 */
public class JarPath {
    private String jarName;
    private String jarPath;

    public JarPath(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource().getLocation()
                .getFile();
        try {
            path = decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File jarFile = new File(path);
        this.jarName = jarFile.getName();
        File parent = jarFile.getParentFile();
        if (parent != null) {
            this.jarPath = parent.getAbsolutePath();
        }
    }

    /**
     * 获取Class类所在Jar包的名称
     *
     * @return Jar包名 (例如：C:\temp\demo.jar 则返回 demo.jar )
     */
    public String getJarName() {
        try {
            return decode(this.jarName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得Class类所在的Jar包路径
     *
     * @return 返回一个路径 (例如：C:\temp\demo.jar 则返回 C:\temp )
     */
    public String getJarPath() {
        try {
            return decode(this.jarPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
