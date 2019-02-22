package com.tscloud.common.tool.remotefiletool;


import java.io.File;
import java.io.Serializable;
import java.util.Date;


/**
 * create by shm on 2015/9/14
 */
public class FileInfo implements Serializable {

    private static final long serialVersionUID = -6985747450226543585L;

    // 文件名称
    private String name;
    // 文件路径
    private String path;
    // 文件大小
    private Long size;
    // 文件扩展名
    private String fileFormat;
    // 是否是目录
    private boolean isDirectory;
    // 是否是文件
    private boolean isFile;
    // 父文件文件目录
    private String parentPath;
    // 创建时间
    private Date createTime;
    // 是否下发
    private boolean isSend;
    // 是否已执行下发任务
    private boolean hasSend;
    // 文件最后更新时间
    private Date modificationTime;
    // 下发任务ID
    private String sendTaskId;
    // 下发文件需存放的目录
    private String sendDir;

    public FileInfo() {
    }

    public FileInfo(String name, String path, Date createTime, String fileFormat) {
        this.name = name;
        this.path = path;
        this.createTime = createTime;
        this.fileFormat = fileFormat;
    }

    public FileInfo(String name, String path, Date createTime, String sendDir, String fileFormat) {
        this.name = name;
        this.path = path;
        this.createTime = createTime;
        this.sendDir = sendDir;
        this.fileFormat = fileFormat;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }


    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isHasSend() {
        return hasSend;
    }

    public void setHasSend(boolean hasSend) {
        this.hasSend = hasSend;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getSendTaskId() {
        return sendTaskId;
    }

    public void setSendTaskId(String sendTaskId) {
        this.sendTaskId = sendTaskId;
    }

    public String getSendDir() {
        return sendDir;
    }

    public void setSendDir(String sendDir) {
        this.sendDir = sendDir;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean isFile) {
        this.isFile = isFile;
    }

    public boolean exists() {
        return new File(this.path + "/" + this.name + "." + this.fileFormat).exists();
    }

    public File file() {
        return new File(this.path + "/" + this.name + "." + this.fileFormat);
    }

    public String getAbsolutePath() {
        return this.path + "/" + this.name + "." + this.fileFormat;
    }
}
