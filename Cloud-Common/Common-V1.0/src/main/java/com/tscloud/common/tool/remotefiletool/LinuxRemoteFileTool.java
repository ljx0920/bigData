package com.tscloud.common.tool.remotefiletool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.tscloud.common.tool.sshtool.SSH2Utils;
import com.tscloud.common.utils.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.tscloud.common.Constants.DATE_FORMAT.E_DATE_FORMAT_MINUTE;

/**
 * @author jiancai.wang
 * @date 2017/1/11
 */
public class LinuxRemoteFileTool implements IRemoteFileTool {

    private final Logger log = LogManager.getLogger(LinuxRemoteFileTool.class);

    private volatile static LinuxRemoteFileTool instance;

    private SSH2Utils ssh2Utils;
    private Session session;
    private ChannelSftp channelSftp;
    private volatile boolean isConnect = false;

    public static LinuxRemoteFileTool getInstance() {
        if (instance == null) {
            synchronized (LinuxRemoteFileTool.class) {
                if (instance == null) {
                    instance = new LinuxRemoteFileTool();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean connect(String ip, Integer port, String domainName, String username, String password) throws Exception {
        boolean flag = false;
        try {
            ssh2Utils = SSH2Utils.getInstance();
            session = ssh2Utils.getJSchSession(ip, port, username, password);
            channelSftp = ssh2Utils.getFtpConnect(session);
            flag = true;
            isConnect = true;
        } catch (Exception e) {
            log.error("Linux共享目录连接失败," + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean isConnect() {
        return isConnect;
    }

    @Override
    public boolean createDir(String dirPath) throws Exception {
        boolean flag = false;
        try {
            flag = ssh2Utils.createDir(dirPath, channelSftp);
        } catch (Exception e) {
            log.error("Linux共享目录创建失败," + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean exists(String filePath) throws Exception {
        boolean flag = false;
        try {
            flag = ssh2Utils.isDirOrFileExist(filePath, channelSftp);
        } catch (Exception e) {
            log.error("exists fails！" + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public InputStream readFileToInputStream(String remoteFilePath) throws Exception {
        try {
            return ssh2Utils.getFile(channelSftp, remoteFilePath);
        } catch (Exception e) {
            log.error("Linux共享文件读取失败," + e.getMessage(), e);
            throw new Exception("create SmbFile Object exception by remoteFilePath(" + remoteFilePath + ")", e);
        }
    }

    @Override
    public boolean downloadFile(String localDir, String localFileName, String remoteFilePath) throws Exception {
        boolean flag = false;
        try {
            // directory, remoteFileName, localFullFilePath, sftp
            String[] pathArray = remoteFilePath.replace("\\", "/").split("/");
            String directory = null, remoteFileName = null;
            if (pathArray.length > 2) {
                remoteFileName = pathArray[pathArray.length - 1];
                directory = remoteFilePath.replace("/" + remoteFileName, "");
            }
            flag = ssh2Utils.downloadFile(directory, remoteFileName, localDir + "/" + localFileName, channelSftp);
        } catch (Exception e) {
            log.error("Linux共享文件下载失败！" + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean writeByteArrayToLocal(byte[] bytes, String remoteDir, String remoteFileName) throws Exception {
        return false;
    }

    @Override
    public boolean writeByteArrayToRemote(byte[] bytes, String remoteDir, String remoteFileName) throws Exception {
        boolean flag = false;
        try {
            InputStream in = new ByteArrayInputStream(bytes);
            channelSftp.put(in, remoteDir + "/" + remoteFileName);
            if (in.available() > -1) {
                in.close();
            }
            flag = true;
        } catch (Exception e) {
            log.error("Linux共享文件写失败！" + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean writeInputStreamToFile(InputStream in, String remoteDir, String remoteFilename) throws Exception {
        boolean flag = false;
        try {
            channelSftp.put(in, remoteDir + "/" + remoteFilename);
            if (in.available() > -1) {
                in.close();
            }
            flag = true;
        } catch (Exception e) {
            log.error("Linux共享文件写失败！" + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean deleteRemoteFile(String remoteFilePath) throws Exception {
        boolean flag = false;
        try {
            channelSftp.rm(remoteFilePath);
            flag = true;
        } catch (Exception e) {
            log.error("Linux共享文件删除失败" + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean uploadFile(String localFileFullPath, String remoteDir, String remoteFileName) throws Exception {
        boolean flag = false;
        try {
            InputStream in = new FileInputStream(localFileFullPath);
            if (!exists(remoteDir)) {
                createDir(remoteDir);
            }
            flag = ssh2Utils.upload(remoteDir, in, channelSftp, remoteFileName);
            in.close();
            log.debug("本地文件：" + localFileFullPath + " upload 成功！");
        } catch (Exception e) {
            log.error("本地文件：" + localFileFullPath + " upload 失败！");
            throw new Exception("copy remote(" + localFileFullPath + ") to remote path exception," + e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 查询路径下的一级文件夹
     */
    public String getDir(String path) throws Exception {
        StringBuilder result = new StringBuilder(path);
        Vector dir = channelSftp.ls(path);
        for (Object aDir : dir) {
            String dirOne = aDir + "";
            dirOne = dirOne.substring(dirOne.lastIndexOf(" ") + 1);
            if (!dirOne.contains(".")) {
                result.append(",").append(path).append("/").append(dirOne);
            }
        }
        return result.toString();
    }

    /**
     * 循环查询路径下的所有文件夹
     */
    public String getDirs(String path) throws Exception {
        StringBuilder result = new StringBuilder();
        Vector dir = channelSftp.ls(path);
        for (Object aDir : dir) {
            String dirOne = aDir + "";
            dirOne = dirOne.substring(dirOne.lastIndexOf(" ") + 1);
            if (!dirOne.contains(".")) {
                result.append(",").append(path).append("/").append(dirOne);
                result.append(getDirs(path + "/" + dirOne));
            }
        }
        return result.substring(1);
    }

    /**
     * path为绝对路径 name为替换的名称
     * 例如 /test/a.txt  b.txt
     * /test/ll/yy   xx
     *
     * @param path
     * @param name
     * @return
     */
    public boolean editDirOrFile(String path, String name) throws Exception {
        boolean res = false;
        try {
            channelSftp.rename(path, path.substring(0, path.lastIndexOf("/") + 1) + name);
            res = true;
        } catch (Exception e) {
            log.error("Linux编辑文件或文件夹失败" + e.getMessage(), e);
        }
        return res;
    }

    public List<Map<String, Object>> getFilesName(String path) throws Exception {
        List<Map<String, Object>> list = Lists.newArrayList();
        Vector<ChannelSftp.LsEntry> dir = channelSftp.ls(path);
        for (ChannelSftp.LsEntry aDir : dir) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("path", aDir.getFilename());
            map.put("size", aDir.getAttrs().getSize());
            map.put("time", DateUtil.format(aDir.getAttrs().getMTime() * 1000L, E_DATE_FORMAT_MINUTE));
            map.put("type", aDir.getAttrs().isDir() ? 0 : 1);
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean deleteFolder(String path) throws Exception {
        boolean flag = false;
        try {
            channelSftp.rmdir(path);
            flag = true;
        } catch (Exception e) {
            log.error("Linux共享文件夹删除失败" + e.getMessage(), e);
        }
        return flag;
    }
}
