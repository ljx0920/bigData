package com.tscloud.common.tool.remotefiletool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tscloud.common.utils.DataConvert;
import com.tscloud.common.utils.FileUtil;
import jcifs.UniAddress;
import jcifs.smb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * window 远程共享文件操作
 *
 * @author huwanshan
 * @date 2015/9/2
 */
public class WinRemoteFileTool implements IRemoteFileTool {
    private static final Logger log = LogManager.getLogger(WinRemoteFileTool.class);

    /**
     * ip
     */
    private String ip;
    /**
     * port
     */
    private Integer port;

    private NtlmPasswordAuthentication auth;

    /**
     * 如果需要登录局域网内部服务器，请将ip换成域名
     *
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public boolean connect(String ip, Integer port, String domainName, String username, String password) {
        boolean flag = false;
        try {
            if ("".equals(domainName)) {
                domainName = ip;
            }
            this.ip = ip;
            this.port = port;
            // 先登录验证
            this.auth = new NtlmPasswordAuthentication(domainName, username, password);
            UniAddress address = UniAddress.getByName(ip);
            SmbSession.logon(address, auth);
            flag = true;
        } catch (Exception e) {
            log.error("Windows共享目录登录失败," + e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean isConnect() {
        return auth != null;
    }

    /**
     * 在远程共享文件系统中创建文件夹（支持多级目录创建）
     *
     * @param filePath
     */
    @Override
    public boolean createDir(String filePath) {
        boolean flag = true;
        try {
            SmbFile file = new SmbFile(filePath, auth);
            if (!file.exists()) {
                file.mkdirs();
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            log.error("创建目录失败！", e);
        }
        return flag;
    }

    /**
     * 判断文件或目录是否存在
     *
     * @param filePath
     * @return
     */
    @Override
    public boolean exists(String filePath) {
        boolean flag = false;
        try {
            SmbFile file = new SmbFile(filePath, auth);
            if (file.exists()) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            log.error("exists fails！", e);
        }
        return flag;
    }

    /**
     * 读取远程文件
     *
     * @param remoteFilePath
     * @return
     */
    @Override
    public InputStream readFileToInputStream(String remoteFilePath) throws Exception {
        try {
            SmbFile smbFile = new SmbFile(remoteFilePath, auth);
            return new SmbFileInputStream(smbFile);
        } catch (Exception e) {
            log.error("Win共享文件读取失败," + e.getMessage(), e);
            throw new Exception("create SmbFile Object exception by remoteFilePath(" + remoteFilePath + ")", e);
        }
    }

    /**
     * 下载文件到本地
     *
     * @param remoteFilePath
     */
    @Override
    public boolean downloadFile(String localDir, String localFileName, String remoteFilePath) {
        boolean flag = false;
        try {
            InputStream is = this.readFileToInputStream(remoteFilePath);
            log.info("正在下载远程目录文件：" + remoteFilePath + " ......");
            FileUtil.newFolder(localDir);
            // 打开一个已存在文件的输出流
            String path = localDir + "/" + localFileName;
            // 文件输出流fos
            FileOutputStream fos = new FileOutputStream(path);
            // 将输入流is写入文件输出流fos中
            byte[] buffer = new byte[4 * 1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            //关闭输入流等（略）
            fos.close();
            is.close();
            flag = true;
            log.info("远程目录文件：" + remoteFilePath + "下载成功！");
        } catch (Exception e) {
            log.error("win远程文件下载失败！", e);
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param remoteFilePath
     */
    public boolean downloadFile(OutputStream fos, String remoteFilePath) {
        boolean flag = false;
        try {
            InputStream is = this.readFileToInputStream(remoteFilePath);
            log.info("正在下载远程目录文件：" + remoteFilePath + " ......");
            // 将输入流is写入文件输出流fos中
            byte[] buffer = new byte[4 * 1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            //关闭输入流等（略）
            fos.close();
            is.close();
            flag = true;
            log.info("远程目录文件：" + remoteFilePath + "下载成功！");
        } catch (Exception e) {
            log.error("win远程文件下载失败！", e);
        }
        return flag;
    }

    @Override
    public boolean writeByteArrayToLocal(byte[] bytes, String remoteDir, String remoteFileName) {
        return false;
    }

    /**
     * 将字节流数据写入到远程文件系统中
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    @Override
    public boolean writeByteArrayToRemote(byte[] bytes, String remoteDir, String remoteFileName) throws Exception {
        InputStream in = DataConvert.byteToInputStream(bytes);
        return writeInputStreamToFile(in, remoteDir, remoteFileName);
    }

    /**
     * 将数据流写入到远程文件系统中
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean writeInputStreamToFile(InputStream in, String remoteDir, String remoteFilename) throws Exception {
        boolean isWriteSuccess = false;
        String fileFullName = remoteDir + "/" + remoteFilename;
        try {
            createDir(remoteDir);
            SmbFile file = new SmbFile(fileFullName, auth);
            OutputStream out = new BufferedOutputStream(new SmbFileOutputStream(file));
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            isWriteSuccess = true;
        } catch (Exception e) {
            throw new Exception("create SmbFile exception,{fileFullName:" + fileFullName + "}," + e.getMessage(), e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return isWriteSuccess;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     * @throws Exception
     */
    @Override
    public boolean deleteFolder(String folderPath) throws Exception {
        boolean flag = false;
        try {
            SmbFile myFilePath = new SmbFile(folderPath);
            if (myFilePath.exists() && myFilePath.isDirectory() && myFilePath.listFiles().length == 0) {
                myFilePath.delete();
                flag = true;
            }
        } catch (Exception e) {
            throw new Exception("delete directory exception," + e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param remoteFilePath
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteRemoteFile(String remoteFilePath) throws Exception {
        boolean flag;
        try {
            SmbFile smbFile = new SmbFile(remoteFilePath, auth);
            if (smbFile.isFile()) {
                smbFile.delete();
            } else {
                smbFile.delete(); // 删除空文件夹
            }
            flag = true;
        } catch (SmbException e) {
            throw new Exception("deleted remote exception by fileName(" + remoteFilePath + ")," + e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 拷贝本地文件到远程目录
     *
     * @param localFileFullPath
     * @param remoteDir
     * @throws Exception
     */
    @Override
    public boolean uploadFile(String localFileFullPath, String remoteDir, String remoteFileName) throws Exception {
        boolean flag;
        try {
            InputStream in = new FileInputStream(localFileFullPath);
            if (!exists(remoteDir)) {
                createDir(remoteDir);
            }
            flag = writeInputStreamToFile(in, remoteDir, remoteFileName);
            in.close();
            log.debug("本地文件：" + localFileFullPath + " upload 成功！");
        } catch (Exception e) {
            log.error("本地文件：" + localFileFullPath + " upload 失败！");
            throw new Exception("copy remote(" + localFileFullPath + ") to remote path exception," + e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 循环获取文件夹内所有匹配的目录
     * 不包含子目录下文件和目录
     *
     * @param strPath
     * @return
     * @throws Exception
     */
    public List<FileInfo> getFileDirList(String strPath) throws Exception {
        SmbFile dir;
        List<FileInfo> list = Lists.newArrayList();
        try {
            dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            if (files == null) {
                return null;
            }
            for (SmbFile file : files) {
                FileInfo fileInfo;
                Map<String, String> map = Maps.newHashMap();
                if (file.isDirectory()) {
                    fileInfo = cpProperties(file);
                    list.add(fileInfo);
                }
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 循环获取文件夹内所有匹配的文件与文件夹
     * 不包含子目录下文件和目录
     *
     * @param strPath
     * @return
     * @throws Exception
     */
    public List<FileInfo> getFileOrFolderList(String strPath) throws Exception {
        SmbFile dir;
        List<FileInfo> list = Lists.newArrayList();
        try {
            dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            if (files == null) {
                return null;
            }
            for (SmbFile file : files) {
                FileInfo fileInfo;
                fileInfo = cpProperties(file);
                list.add(fileInfo);
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 循环获取文件夹内所有匹配的文件
     * 不包含子目录下文件和目录
     *
     * @param strPath
     * @param formatFile
     * @return
     * @throws Exception
     */
    public List<FileInfo> getFileNameList(String strPath, String[] formatFile) throws Exception {
        SmbFile dir;
        List<FileInfo> list = Lists.newArrayList();
        try {
            dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            if (files == null) {
                return null;
            }
            for (SmbFile file : files) {
                FileInfo fileInfo;
                Map<String, String> map = Maps.newHashMap();
                if (!file.isDirectory()) {
                    if (formatFile != null) {
                        for (String one : formatFile) {
                            if (file.getName().contains(one)) {
                                fileInfo = cpProperties(file);
                                list.add(fileInfo);
                            }
                        }
                    } else {
                        fileInfo = cpProperties(file);
                        list.add(fileInfo);
                    }
                } else {
                    fileInfo = cpProperties(file);
                    list.add(fileInfo);
                }
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取所有的文件和目录（包含子目录）
     * 只读取文件 不包含目录
     *
     * @param strPath
     * @param formatFile 过滤  文件名、扩展名
     * @return
     * @throws Exception
     */
    public List<FileInfo> getFileNameListAll(String strPath, String[] formatFile) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        this.getChildFile(strPath, formatFile, list);
        log.info("文件和目录个数：" + list.size() + "远程目录：" + strPath);
        return list;
    }

    /**
     * 获取当前目录下所有的文件
     *
     * @param strPath
     * @return
     * @throws Exception
     */
    public List<FileInfo> getCurrentDirFile(String strPath) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        try {
            SmbFile dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            for (SmbFile file : files) {
                if (!file.isDirectory()) {
                    FileInfo fileInfo = cpProperties(file);
                    list.add(fileInfo);
                }
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取所有的文件和目录（包含子目录）
     *
     * @param path
     * @param filterArr 只取  文件名、扩展名
     * @return
     * @throws MalformedURLException
     * @throws SmbException
     */
    public List<FileInfo> findFileAndDirAllForJustTake(String path, String[] filterArr) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        getChildFileForJustTake(path, filterArr, list);
        log.info("文件和目录个数：" + list.size() + "远程目录：" + path);
        return list;
    }

    /**
     * 获取所有文件目录（包含子目录）
     * 只查询目录
     *
     * @param strPath
     * @return
     * @throws Exception
     */
    public List<FileInfo> getFileDirListAll(String strPath) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        getChildFileDirList(strPath, list);
        log.info("远程目录：" + strPath + "文件个数：" + list.size());
        return list;
    }

    public boolean renameFileOrFolder(String absolutePath, String name) throws Exception {
        boolean flag = false;
        try {
            SmbFile file = new SmbFile(absolutePath, auth);
            if (file.exists() && (file.isFile() || file.isDirectory())) {
                String targetPath = absolutePath.substring(0, absolutePath.lastIndexOf("\\") + 1) + name;
                SmbFile tragetFile = new SmbFile(targetPath, auth);
                file.renameTo(tragetFile);
                flag = true;
            }
        } catch (Exception e) {
            throw new Exception("rename a file or folder fail. ," + e.getMessage(), e);
        }
        return flag;
    }

    private void getChildFileDirList(String strPath, List<FileInfo> list) throws Exception {
        SmbFile dir;
        try {
            dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            if (files != null) {
                for (SmbFile file : files) {
                    if (file.isDirectory()) {
                        FileInfo fileInfo = cpProperties(file);
                        list.add(fileInfo);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
    }

    /**
     * @param strPath
     * @param formatFile
     * @param list
     * @throws Exception
     */
    private void getChildFile(String strPath, String[] formatFile, List<FileInfo> list) throws Exception {
        SmbFile dir;
        try {
            dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            if (files != null) {
                for (SmbFile file : files) {
                    if (!file.isDirectory()) {
                        if (formatFile == null) {
                            FileInfo fileInfo = cpProperties(file);
                            list.add(fileInfo);
                        }
                    } else {
                        getChildFile(file.getPath(), formatFile, list);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
    }

    private void getChildFileForJustTake(String strPath, String[] formatFile, List<FileInfo> list) throws Exception {
        SmbFile dir;
        try {
            dir = new SmbFile(strPath, auth);
            SmbFile[] files = dir.listFiles();
            if (files != null) {
                for (SmbFile file : files) {
                    if (!file.isDirectory()) {
                        if (formatFile != null) {
                            if (isIgnore(file.getName(), formatFile)) {
                                FileInfo fileInfo = cpProperties(file);
                                list.add(fileInfo);
                            }
                        }
                    } else {
                        getChildFile(file.getPath(), formatFile, list);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("illegal url(" + strPath + ")," + e.getMessage(), e);
        }
    }

    /**
     * 查询指定目录下的文件及文件夹数据
     *
     * @param path  文件路径
     * @param types 过滤文件类型列表
     * @author chenlu@ccuu.me
     */
    public List<FileInfo> findFileAndDir(String path, String[] types) throws MalformedURLException, SmbException {
        List<FileInfo> list = Lists.newArrayList();
        types = types == null ? new String[0] : types;
        findFiles(path, types, true, list);
        return list;
    }

    /**
     * 查询指定目录下的文件
     *
     * @param path  文件路径
     * @param types 过滤文件类型列表
     * @author chenlu@ccuu.me
     */
    public List<FileInfo> findFiles(String path, String[] types) throws MalformedURLException, SmbException {
        List<FileInfo> list = Lists.newArrayList();
        types = types == null ? new String[0] : types;
        findFiles(path, types, false, list);
        return list;
    }

    /**
     * 查询指定目录下文件
     * 1.types 过滤指定列表的类型文件
     * 2.flag true 查询文件夹,flase 不查询文件夹
     *
     * @param path  文佳路径
     * @param types 过滤文件类型
     * @param flag  是否查询文件夹
     */
    private void findFiles(String path, String[] types, boolean flag, List<FileInfo> list) throws MalformedURLException, SmbException {

        SmbFile file = new SmbFile(path, auth);
        if (!file.exists() || file.isFile()) {
            return;
        }
        SmbFile[] files = file.listFiles();
        for (SmbFile f : files) {
            if (f.isDirectory()) {
                findFiles(f.getPath(), types, flag, list);
            }
            FileInfo fileInfo = cpProperties(f);
            if (!flag && f.isDirectory()) {
                continue;
            }
            if (f.isFile() && isIgnore(f.getName(), types)) {
                continue;
            }
            list.add(fileInfo);
        }
    }

    private FileInfo cpProperties(SmbFile file) throws SmbException {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(file.getName());
        fileInfo.setPath(file.getPath());
        fileInfo.setParentPath(file.getParent());
        fileInfo.setSize(file.length());
        fileInfo.setIsDirectory(file.isDirectory());
        fileInfo.setIsFile(file.isFile());
        fileInfo.setModificationTime(new Date(file.lastModified()));

        return fileInfo;
    }

    private static boolean isIgnore(String fileName, String[] types) {
        boolean flag = false;
        if (types == null || types.length == 0) {
            return false;
        }
        for (String type : types) {
            if (flag = fileName.toLowerCase().endsWith(type.toLowerCase())) {
                break;
            }
        }
        return flag;
    }
}
