package com.tscloud.common.tool.remotefiletool;

import java.io.InputStream;

/**
 * 远程文件工具
 *
 * @author jiancai.wang
 * @date 2017/1/11
 */
public interface IRemoteFileTool {

    /**
     * 登录
     *
     * @param ip
     * @param port
     * @param domainName
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    boolean connect(String ip, Integer port, String domainName, String username, String password) throws Exception;

    /**
     * 是否连接
     *
     * @return
     */
    boolean isConnect();

    /**
     * 创建远程文件夹（支持多级目录创建）
     *
     * @param dirPath
     * @return
     * @throws Exception
     */
    boolean createDir(String dirPath) throws Exception;

    /**
     * 判断远程文件是否存在
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    boolean exists(String filePath) throws Exception;

    /**
     * 读取远程文件
     *
     * @param remoteFilePath
     * @return
     * @throws Exception
     */
    InputStream readFileToInputStream(String remoteFilePath) throws Exception;

    /**
     * 下载远程目录到本地
     *
     * @param localDir
     * @param localFileName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    boolean downloadFile(String localDir, String localFileName, String remoteFileName) throws Exception;

    /**
     * 将字节数组写本地文件
     *
     * @param bytes
     * @param remoteDir
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    boolean writeByteArrayToLocal(byte[] bytes, String remoteDir, String remoteFileName) throws Exception;

    /**
     * 将字节流写远程文件
     *
     * @param bytes
     * @param remoteDir
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    boolean writeByteArrayToRemote(byte[] bytes, String remoteDir, String remoteFileName) throws Exception;

    /**
     * 将数据流写远程文件
     *
     * @param in
     * @param remoteDir
     * @param remoteFilename
     * @return
     * @throws Exception
     */
    boolean writeInputStreamToFile(InputStream in, String remoteDir, String remoteFilename) throws Exception;

    /**
     * 删除远程文件
     *
     * @param remoteFilePath
     * @return
     * @throws Exception
     */
    boolean deleteRemoteFile(String remoteFilePath) throws Exception;

    /**
     * 拷贝本地文件到远程目录
     *
     * @param localFileFullPath
     * @param remoteDir
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    boolean uploadFile(String localFileFullPath, String remoteDir, String remoteFileName) throws Exception;

    /**
     * 删除文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    boolean deleteFolder(String path) throws Exception;
}
