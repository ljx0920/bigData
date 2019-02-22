package com.tscloud.common.tool.hadooptool.hdfs;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

/**
 * @author lei.yang1
 * @date 2017/4/17
 */
public class IOVFile {
    public static String SEPARATOR = "/";
    private FileStatus fileStatus;
    private Path path;

    private IOVFile(String path) {
        this.path = new Path(path);
    }

    IOVFile(FileStatus fileStatus) {
        this.fileStatus = fileStatus;
        this.path = fileStatus.getPath();
    }

    public FileStatus fileStatus() {
        return fileStatus;
    }

    public void setFileStatus(FileStatus fileStatus) {
        this.fileStatus = fileStatus;
    }

    /**
     * 获取文件路径
     *
     * @return 此文件全路径的字符串形式
     */
    public String getPath() {
        return path.toUri().getPath();
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    public String getName() {
        return path.getName();
    }

    /**
     * 获取文件大小
     *
     * @return 文件大小，单位为字节
     */
    public long getLength() {
        return fileStatus.getLen();
    }

    /**
     * 获取文件父级目录
     *
     * @return 文件父级目录路径的字符串形式
     */
    public String getParent() {
        return path.getParent().toUri().getPath();
    }

    /**
     * 此路径表示的是否为目录
     *
     * @return 如果为目录则返回true，否则返回false
     */
    public boolean isDirectory() {
        return fileStatus.isDirectory();
    }

    /**
     * 获取文件最新更新时间
     *
     * @return 文件最新更新时间
     */
    public long getModificationTime() {
        return fileStatus.getModificationTime();
    }
}
