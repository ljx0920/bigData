package com.tscloud.common.tool.sshtool;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ssh操作linux
 *
 * @author huwanshan
 */
public interface IlinuxSSH2 {

    /**
     * 检查连接是否正确
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    boolean checkConn(String host, int port, String username, String password);

    /**
     * 获取SSH连接
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    Session getJSchSession(String host, int port, String username, String password) throws Exception;

    /**
     * 关闭
     *
     * @param sshSession
     * @return
     */
    boolean closeJSchSession(Session sshSession);

    /**
     * 权限设置
     *
     * @param sshSession
     * @param shell
     * @return
     */
    boolean chmodShellFile(Session sshSession, String shell);

    /**
     * 执行shell
     *
     * @param sshSession
     * @param shell
     * @return
     */
    boolean actionShell(Session sshSession, String shell);

    /**
     * 执行shell返回结果
     *
     * @param sshSession
     * @param command
     * @return
     */
    String actionShellStr(Session sshSession, String command);

    /**
     * 连接sftp服务器
     *
     * @param sshSession
     * @return
     */
    ChannelSftp getFtpConnect(Session sshSession);

    /**
     * 创建文件目录
     *
     * @param sftpDirPath
     * @return
     */
    boolean createDir(String sftpDirPath, ChannelSftp sftp);

    /**
     * 上传文件
     *
     * @param list
     * @param sshSession
     * @return
     */
    boolean uploadFile(List<Map<String, String>> list, Session sshSession);

    /**
     * 上传文件
     *
     * @param directory
     * @param in
     * @param sftp
     * @param fileName
     * @return
     */
    boolean upload(String directory, InputStream in, ChannelSftp sftp, String fileName);

    /**
     * 读取文件流
     *
     * @param sftp
     * @param filePath
     * @return
     */
    InputStream getFile(ChannelSftp sftp, String filePath);

    /**
     * 判断文件目录是否存在
     *
     * @param directory
     * @param sftp
     * @return
     */
    boolean isDirOrFileExist(String directory, ChannelSftp sftp);

    /**
     * 下载
     *
     * @param directory         下载目录
     * @param remoteFileName    下载的文件
     * @param localFullFilePath 存在本地的路径
     * @param sftp
     * @return
     */
    boolean downloadFile(String directory, String remoteFileName, String localFullFilePath, ChannelSftp sftp);
}
