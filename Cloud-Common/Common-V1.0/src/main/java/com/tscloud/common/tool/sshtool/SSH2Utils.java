package com.tscloud.common.tool.sshtool;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author huwanshan
 * @date 2015/3/6
 */
public class SSH2Utils {

    private Logger log = LogManager.getLogger(SSH2Utils.class);

    private static SSH2Utils instance;
    private static IlinuxSSH2 ssh2 = new LinuxSSH2Impl();

    public synchronized static SSH2Utils getInstance() {
        if (instance == null) {
            instance = new SSH2Utils();
        }
        return instance;
    }

    /**
     * 检查连接是否正确
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    public boolean checkConn(String host, int port, String username, String password) {
        return ssh2.checkConn(host, port, username, password);
    }

    /**
     * 获取SSH连接
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    public Session getJSchSession(String host, int port, String username, String password) throws Exception {
        return ssh2.getJSchSession(host, port, username, password);
    }

    /**
     * 关闭
     *
     * @param sshSession
     * @return
     */
    public boolean closeJSchSession(Session sshSession) {
        return ssh2.closeJSchSession(sshSession);
    }

    /**
     * 权限设置
     *
     * @param sshSession
     * @param shell
     * @return
     */
    public boolean chmodShellFile(Session sshSession, String shell) {
        log.info("-------" + shell);
        return ssh2.chmodShellFile(sshSession, shell);
    }

    /**
     * 执行shell
     *
     * @param sshSession
     * @param shell
     * @return
     */
    public boolean actionShell(Session sshSession, String shell) {
        log.info("------- " + shell);
        return ssh2.actionShell(sshSession, shell);
    }

    /**
     * 执行shell返回结果
     *
     * @param sshSession
     * @param shell
     * @return
     */
    public String actionShellStr(Session sshSession, String shell) {
        log.info("------- " + shell);
        return ssh2.actionShellStr(sshSession, shell);
    }

    /**
     * 连接sftp服务器
     *
     * @return
     */
    public ChannelSftp getFtpConnect(Session sshSession) {
        return ssh2.getFtpConnect(sshSession);
    }

    /**
     * 上传文件
     *
     * @param list
     * @param sshSession
     * @return
     */
    public boolean uploadFile(List<Map<String, String>> list, Session sshSession) {
        return ssh2.uploadFile(list, sshSession);
    }

    /**
     * 上传文件
     *
     * @param directory
     * @param in
     * @param sftp
     * @param fileName
     * @return
     */
    public boolean upload(String directory, InputStream in, ChannelSftp sftp, String fileName) {
        return ssh2.upload(directory, in, sftp, fileName);
    }

    /**
     * 获取文件
     *
     * @param sftp
     * @param filePath
     * @return
     */
    public InputStream getFile(ChannelSftp sftp, String filePath) {
        return ssh2.getFile(sftp, filePath);
    }

    /**
     * 下载
     *
     * @param directory         下载目录
     * @param remoteFileName    下载的文件
     * @param localFullFilePath 存在本地的路径
     * @param sftp
     * @param sftp
     * @return
     */
    public boolean downloadFile(String directory, String remoteFileName, String localFullFilePath, ChannelSftp sftp) {
        return ssh2.downloadFile(directory, remoteFileName, localFullFilePath, sftp);
    }

    /**
     * 创建文件目录
     *
     * @param sftpDirPath
     * @param sftp
     * @return
     */
    public boolean createDir(String sftpDirPath, ChannelSftp sftp) {
        return ssh2.createDir(sftpDirPath, sftp);
    }

    /**
     * 判断文件或目录是否存在
     *
     * @param directory
     * @param sftp
     * @return
     */
    public boolean isDirOrFileExist(String directory, ChannelSftp sftp) {
        return ssh2.isDirOrFileExist(directory, sftp);
    }

    public static void main(String[] org) {
        try {

            SSH2Utils ssh2Utils = SSH2Utils.getInstance();
            Session sshSession = ssh2Utils.getJSchSession("10.10.3.191" +
                    "", 22, "root", "datacloud321");
//            ChannelSftp sftp = ssh2Utils.getFtpConnect(sshSession);
            SSH2Utils.getInstance().actionShell(sshSession, "ls /");
//            // git file
//            String path = "/test0117/liuyang/job/CS_ROLE.sql";
//            String pathTemp = "/home/iovCloudTemps/vrds/data_2015-01-08_12-03-15_temp.vrd";
//            InputStream inputStream = ssh2Utils.getFile(sftp, path);
//            int size = inputStream.available();
//            System.out.print("size : " + size);
//            sftp.put(inputStream, pathTemp);

        } catch (Exception e) {
//
        }
    }
}
