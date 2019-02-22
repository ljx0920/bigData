package com.tscloud.common.tool.ftptool;

import com.google.common.collect.Lists;
import com.tscloud.common.utils.DateUtil;
import com.tscloud.common.utils.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 1
 * @date 2016/5/23
 */
public class FtpFileUtils {

    private static final Logger log = LogManager.getLogger(FtpFileUtils.class);

    /**
     * 1：主动模式，2：被动模式   默认为主动模式
     */
    private static final String ACTIVE_MODE = "1";
    private static final String PASSIVE_MODE = "2";

    private static FtpFileUtils instance;

    public synchronized static FtpFileUtils getInstance() {
        if (instance == null) {
            instance = new FtpFileUtils();
        }
        return instance;
    }

    /**
     * 创建多级目录
     *
     * @param dir
     * @return
     */
    public boolean mkdirs(FTPClient ftpClient, String dir) throws Exception {
        boolean flag = true;
        try {
            dir = dir.replace("\\", "/");
            String[] arr = dir.split("/");
            String dirName = "";
            for (String one : arr) {
                if (StringUtils.isNotBlank(one)) {
                    dirName = dirName + "/" + one;
                    if (!isDirExist(ftpClient, dirName)) {
                        this.makeDirectory(ftpClient, dirName);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return flag;
    }

    /**
     * 判断目录是否存在
     *
     * @param dir
     * @return
     */
    public boolean isDirExist(FTPClient ftpClient, String dir) throws Exception {
        boolean flag;
        try {
            flag = ftpClient.changeWorkingDirectory(dir);
            log.info("【" + dir + "】已存在");
        } catch (IOException e) {
            log.info("【" + dir + "】不存在");
            throw e;
        }
        return flag;
    }

    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public boolean makeDirectory(FTPClient ftpClient, String dir) throws Exception {
        boolean flag;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.info("创建目录【" + dir + "】成功");
            }
        } catch (Exception e) {
            log.info("创建目录【" + dir + "】失败" + e.getMessage(), e);
            throw e;
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param inputStream
     * @param distFolder
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean uploadFile(FTPClient ftpClient, InputStream inputStream, final String distFolder, String fileName) throws Exception {
        boolean success = true;
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            if (!isDirExist(ftpClient, distFolder)) {
                mkdirs(ftpClient, distFolder);
            }
            ftpClient.changeWorkingDirectory(distFolder);
            ftpClient.storeFile(fileName, inputStream);
            log.info("【" + fileName + "】上传文件成功！");
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return success;
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     */
    public InputStream readFile(FTPClient ftpClient, String filePath) throws Exception {
        InputStream inputStream = null;
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            String[] nameArr = ftpClient.listNames(filePath);
            if (nameArr.length > 0) {
                inputStream = ftpClient.retrieveFileStream(filePath);
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
        return inputStream;
    }

    /**
     * 删除一个文件
     */
    public boolean deleteFile(FTPClient ftpClient, String filename) throws Exception {
        boolean flag = true;
        try {
            ftpClient.deleteFile(filename);
            log.info("删除文件成功！");
        } catch (IOException e) {
            log.error("删除文件失败" + e.getMessage(), e);
            throw e;
        }
        return flag;
    }

    /**
     * 获取所有的文件和目录  包括子目录
     *
     * @param ftpPath
     * @param includeArr 找出符合条件的  文件扩展名和文件名
     * @return
     * @throws IOException
     */
    public List<FileInfo> listFileAndDirAll(FTPClient ftpClient, String ftpPath, String[] includeArr, boolean flag) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        try {
            FTPFile[] ftpFileArray = ftpClient.listFiles(ftpPath);

            for (FTPFile file : ftpFileArray) {
                String fileName = file.getName();
                if (flag) {
                    for (String str : includeArr) {
                        if (!file.isDirectory() && fileName.contains(str)) {
                            addList(file, ftpPath, list);
                        }
                    }
                } else {
                    if(!file.isDirectory()){
                        addList(file, ftpPath, list);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return list;
    }

    /**
     * 生成FileInfo 对象
     *
     * @param file
     * @param ftpPath
     * @param list
     * @throws Exception
     */
    private void addList(FTPFile file, String ftpPath, List<FileInfo> list) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getName());
        fileInfo.setDir(file.isDirectory());
        String dir = ftpPath + "/" + file.getName();
        dir = dir.replace("//", "/");
        fileInfo.setFilePath(dir);
        fileInfo.setFileSize(file.getSize() + "");
        fileInfo.setDate(DateUtil.longToDate(file.getTimestamp().getTimeInMillis()));
        fileInfo.setFileDir(ftpPath);
        list.add(fileInfo);
    }

    /**
     * 关闭连接
     */
    public void closeConnect(FTPClient ftpClient) throws Exception {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            throw new Exception("FTP连接关闭失败！", e);
        }
    }

    /**
     * 连接到服务器
     *
     * @return true 连接服务器成功，false 连接服务器失败
     */
    public FTPClient getFTPClient(String ip, int port, String userName, String password, String ftpMode) throws Exception {
        int reply = 0;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setDefaultPort(port);
            ftpClient.setConnectTimeout(3600);
            ftpClient.connect(ip);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            boolean flag = ftpClient.login(userName, password);
            String localCharset = "GBK";
            // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                localCharset = "UTF-8";
            }
            ftpClient.setControlEncoding(localCharset);
            reply = ftpClient.getReplyCode();
            ftpClient.setDataTimeout(120000);
            if (PASSIVE_MODE.equals(ftpMode)) {
                ftpClient.enterLocalPassiveMode();
            } else {
                ftpClient.enterLocalActiveMode();
            }
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.info(" reply value :" + reply);
                log.info("FTP server refused connection.");
            }
            if (!flag) {
                ftpClient = null;
            }
        } catch (Exception e) {
            log.error("登录ftp服务器 " + ip + " 失败,连接超时！" + e.getMessage(), e);
            throw e;
        }
        return ftpClient;
    }

    public static void main(String[] args) throws Exception {
//        FtpFileUtils ftp1 = new FtpFileUtils("gisftp", "gisftp", "126.33.9.26", "30");
//        FtpFileUtils ftp2 = new FtpFileUtils("gisftp", "gisftp", "126.33.9.26", "30");
//        FtpFileUtils ftp = new FtpFileUtils("ftp51", "ftp51", "192.168.10.51", "21");
//        ftp.mkdirs("/1/2/2/3/4/5/6");
//        ftp.mkdirs("/测试2");
        System.out.println("1111111111111111");

        //FtpFileUtils.deleteDirectory("aa");
        //ftp.uploadFile(new File("C:\\wlan\\Atheros\\readme.txt"),"aa");
        //deleteFile("\\aa\\readme.txt");
//        String[] listNames = ftp.getFtpClient().listNames("/");//.listFiles("/1");
//        System.out.println(listNames.length);

//        List<FileInfo> qq =ftp.listDirAll("/1/2/");
        //不包含子目录
//        List<FileInfo> qq =ftp.listFileAndDir("/");
//        List<FileInfo> qq =ftp.listFileAndDirAll("/");
//        String[] arr = {".CSV"};
//        List<FileInfo> qq =ftp.listFileAndDirAll("/",arr);
//        List<FileInfo> qq =ftp.listFileAndDirAll("/",null);
//        List<FileInfo> qq = ftp.listFileAndDirAllForJustTake("/", arr);
//        for (FileInfo info : qq) {
//            System.out.println(info.getFilePath());
//        }
        //ftp.listDirAll();
        //boolean flag=ftp.changeWorkingDirectory("/aa/a");
        //System.output.print(flag);

//        InputStream inputStream = ftp.readFile("E:\\3.0\\05217-201411110351250020\\image\\2014\\11\\11\\0X\\03\\005217-X-201411110351250020-0000002.jpg");
//        File file = new File("c");
//        FtpFileUtils ftp1 = new FtpFileUtils("huwanshan", "123456", "127.0.0.1", "21");
//        boolean flag = ftp1.uploadFile(file,"/2");
//        InputStream inputStream = LocalFileUtils.readFile("E:\\3.0\\05217-201411110351250020\\image\\2014\\11\\11\\0X\\03\\005217-X-201411110351250020-0000002.jpg");
//        boolean flag = ftp1.uploadFile(inputStream, "/2", "005217-X-201411110351250020-00000031.jpg");
//        System.out.println(flag);
//        File file = new File("d:\\用户目录\\我的文档\\downloads\\测试BaiduPinyinSetup_3.4.1805.0.exe");
//        ftp.uploadFile(file,"/");
    }
}
