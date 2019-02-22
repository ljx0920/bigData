package com.tscloud.common.tool.remotefiletool;

/**
 * Created by jiancai.wang on 2017/1/13.
 */


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * 负责文件的写和文件上传
 * 对象为 文件 FileInfo
 */
class FileWriter {
    // 线程池 用来异步存储文件的
    private final ExecutorService executorService;
    private final IRemoteFileTool remoteFileTool;
    private static FileWriter instance;

    private FileWriter(ExecutorService executorService, IRemoteFileTool remoteFileTool) {
        this.executorService = executorService;
        this.remoteFileTool = remoteFileTool;
    }

    public static FileWriter getInstance(ExecutorService executorService, IRemoteFileTool remoteFileTool) {
        if (instance == null) {
            synchronized (FileWriter.class) {
                if (instance == null) {
                    instance = new FileWriter(executorService, remoteFileTool);
                }
            }
        }
        return instance;
    }

    public boolean writeByteArray(byte[] bufferBlock, String localDir, String remoteDir, String fileFormat) throws IOException {
        boolean result = true;
        FileInfo fileInfo = new FileInfo(allocateFileName(), localDir, new Date(), remoteDir, fileFormat);
        if (!fileInfo.exists()) {
            FileUtils.writeByteArrayToFile(fileInfo.file(), bufferBlock);
            if (remoteDir != null) {
                if (remoteFileTool.isConnect()) {
                    upload(fileInfo);
                } else {
                    result = false;
                    throw new IllegalArgumentException("remoteFileTool is disconnected.");
                }
            }
        } else {
            result = false;
            throw new IllegalArgumentException("文件已存在" + fileInfo.getAbsolutePath());
        }
        return result;
    }

    public boolean appendByteArray(byte[] bufferBlock, String localDir, String fileName, String fileFormat) throws IOException {
        boolean result = true;
        FileInfo fileInfo = new FileInfo(fileName, localDir, new Date(), null, fileFormat);
        if (!fileInfo.exists()) {
            FileUtils.writeByteArrayToFile(fileInfo.file(), bufferBlock);
        } else {
            byte[] existingByteData = FileUtils.readFileToByteArray(fileInfo.file());
            byte[] writingByteData = new byte[existingByteData.length + bufferBlock.length];
            System.arraycopy(existingByteData, 0, writingByteData, 0, existingByteData.length);
            System.arraycopy(bufferBlock, 0, writingByteData, existingByteData.length, bufferBlock.length);
            FileUtils.writeByteArrayToFile(fileInfo.file(), writingByteData);
        }
        return result;
    }

    private void upload(FileInfo fileInfo) {
        Callable<Boolean> uploadTask = () -> {
            boolean result = false;
            if (remoteFileTool.isConnect()) {
                try {
                    remoteFileTool.uploadFile(fileInfo.getAbsolutePath(), fileInfo.getSendDir(), fileInfo.getName());
                    result = delete(fileInfo);
                } catch (Exception e) {
                    // 上传失败！！
                }
            }
            return result;
        };
        executorService.submit(uploadTask);
    }

    private boolean delete(FileInfo fileInfo) {
        boolean flag = false;
        if (!new File(fileInfo.getAbsolutePath() + "_del").exists()) {
            File file = new File(fileInfo.getAbsolutePath());
            flag = file.renameTo(new File(fileInfo.getAbsolutePath() + "_del"));
        }
        return flag;
    }

    public String allocateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return "data_" + sdf.format(new Date());
    }

    private String allocateFileParent() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "data_" + sdf.format(new Date());
    }
}

