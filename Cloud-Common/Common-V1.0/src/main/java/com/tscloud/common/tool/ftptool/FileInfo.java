package com.tscloud.common.tool.ftptool;

import java.io.Serializable;

/**
 * Created by huwanshan on 2015/3/6.
 */
public class FileInfo implements Serializable {


    private String id;

    private String name;

    private String parentId;

    private String path;

    private Boolean isParent;
    /**
     * 文件目录
     */
    private String fileDir;

    /**
     * 当前目录下文件个数（不包含子目录）
     */
    private String fileCount;

    /**
     * 是否文件目录
     * true:目录；false：文件
     */
    private boolean isDir;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件格式
     */
    private String fileFormat;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小（单位字节）
     */
    private String fileSize;

    /**
     * 文件时间
     */
    private String date;

    /**
     * 审批状态
     */
    private String applyStatus;

    /**
     * 是否选中
     */
    private String checked;

    /**
     * 是否禁用选中，供ztree使用
     */
    private String chkDisabled;

    /**
     * 设置节点是否隐藏 checkbox / radio ，供ztree使用
     */
    private boolean nocheck;

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean isDir) {
        this.isDir = isDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(String chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }
}
