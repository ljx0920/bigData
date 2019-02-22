package com.tscloud.common.tool.localfiletool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tscloud.common.tool.ftptool.FileInfo;
import com.tscloud.common.utils.DataConvert;
import com.tscloud.common.utils.DateUtil;
import com.tscloud.common.utils.FileSorterUtil;
import com.tscloud.common.utils.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.tscloud.common.utils.FileSorterUtil.SortType.TYPE_NAME;

/**
 * @author huwanshan
 * @date 2015/9/4
 */
public class LocalFileUtils {
    private static Logger log = Logger.getLogger(LocalFileUtils.class);

    /**
     * 创建目录
     * 支持创建多级目录
     *
     * @param folderPath
     */
    public static boolean mkdir(String folderPath) throws Exception {
        boolean flag = false;
        try {
            log.info("新建目录" + folderPath);
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
                log.info("新建目录myFilePath.mkdirs" + myFilePath.getPath());
            }

            if (myFilePath.exists()) {
                log.info("新建目录myFilePath.exists()" + myFilePath.getPath());
                flag = true;
            }
        } catch (Exception e) {
            log.error("新建目录操作出错", e);
            e.printStackTrace();
            throw e;
        }
        return flag;
    }

    /**
     * 判断文件目录是否存在
     *
     * @param dir
     * @return
     */
    public static boolean exists(String dir) throws Exception {
        boolean flag = false;
        File file = new File(dir);
        if (!file.exists()) {
            log.info("文件名称不存在!");
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     */
    public static InputStream readFile(String filePath) throws Exception {
        File file = new File(filePath);
        InputStream in = null;
        try {
            if (file.exists()) {
                in = new FileInputStream(file);
            }
        } catch (Exception e) {
            log.error("读取文件失败", e);
            throw e;
        }
        return in;
    }

    public static boolean saveFile(InputStream input, String dir, String fileName) throws Exception {
        boolean flag = false;
        try {
            mkdir(dir);
            FileOutputStream output = new FileOutputStream(dir + "/" + fileName);
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            log.error("saveFile文件失败", e);
            throw e;
        }
        return flag;
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     */
    public static byte[] readFileToBytes(String filePath) throws Exception {
        File file = new File(filePath);
        InputStream in = null;
        byte[] bytes = null;
        try {
            if (file.exists()) {
                in = new FileInputStream(file);
                bytes = DataConvert.inputStreamToBytes(in);
            }
        } catch (Exception e) {
            log.error("读取文件失败", e);
            throw e;
        }
        return bytes;
    }

    /**
     * 获取目录下所有文件和目录（不包含子目录）
     *
     * @param dir
     * @param extArr
     * @return
     */
    public static List<FileInfo> listFile(String dir, String[] extArr) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        File file = new File(dir);
        if (!file.exists()) {
            log.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (file.isFile()) {
                if (extArr != null) {
                    for (String anExtArr : extArr) {
                        if (file.getName().toLowerCase().endsWith(anExtArr)) {
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setFileName(file.getName());
                            fileInfo.setDir(false);
                            fileInfo.setFilePath(file.getPath());
                            fileInfo.setFileSize(DataConvert.convertSize(file.length()));
                            fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                            fileInfo.setFileDir(file.getParent());
                            list.add(fileInfo);
                        }
                    }
                } else {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(file.getName());
                    fileInfo.setDir(false);
                    fileInfo.setFilePath(file.getPath());
                    fileInfo.setFileSize(file.length() + "");
                    fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                    fileInfo.setFileDir(file.getParent());
                    list.add(fileInfo);
                }
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        if (extArr != null) {
                            if (file.getName().toLowerCase().endsWith(extArr[i])) {
                                FileInfo fileInfo = new FileInfo();
                                fileInfo.setFileName(files[i].getName());
                                fileInfo.setDir(false);
                                fileInfo.setFilePath(files[i].getPath());
                                fileInfo.setFileSize(files[i].length() + "");
                                fileInfo.setDate(DateUtil.longToDate(files[i].lastModified()));
                                fileInfo.setFileDir(files[i].getParent());
                                list.add(fileInfo);
                            }
                        } else {
                            Map<String, String> map = Maps.newHashMap();
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setFileName(files[i].getName());
                            fileInfo.setDir(false);
                            fileInfo.setFilePath(files[i].getPath());
                            fileInfo.setFileSize(files[i].length() + "");
                            fileInfo.setDate(DateUtil.longToDate(files[i].lastModified()));
                            fileInfo.setFileDir(files[i].getParent());
                            list.add(fileInfo);
                        }
                    } else {
                        Map<String, String> map = Maps.newHashMap();
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(files[i].getName());
                        fileInfo.setDir(true);
                        fileInfo.setFilePath(files[i].getPath());
                        fileInfo.setFileSize(files[i].length() + "");
                        fileInfo.setDate(DateUtil.longToDate(files[i].lastModified()));
                        fileInfo.setFileDir(files[i].getParent());
                        list.add(fileInfo);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下文件和目录（包含子目录）
     * 每级目录为一个fileinfo对象
     *
     * @param path
     * @param extArr
     * @return
     */
    public static List<FileInfo> listFileAll(String path, String[] extArr) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        try {
            File file = new File(path);
            if (file.exists()) {
                File[] files = file.listFiles();
                Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
                for (File file1 : files) {
                    listChild(file1, extArr, list);
                }
            }
        } catch (Exception e) {
            log.error("文件目录不存在，读取文件失败！", e);
            throw e;
        }
        return list;
    }

    /**
     * 获取最末端文件和目录的全路径
     *
     * @param path
     * @param extArr
     * @return
     */
    public static List<FileInfo> listFilePathAll(String path, String[] extArr) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        try {
            File file = new File(path);
            if (file.exists()) {
                File[] files = file.listFiles();
                Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
                for (File file1 : files) {
                    listFilePathChild(file1, extArr, list);
                }
            }
        } catch (Exception e) {
            log.error("文件目录不存在，读取文件失败！", e);
            throw e;
        }
        return list;
    }

    private static void listFilePathChild(File file, String[] extArr, List<FileInfo> list) throws Exception {
        if (!file.exists()) {
            log.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (file.isFile()) {
                if (extArr != null) {
                    for (String anExtArr : extArr) {
                        if (file.getName().toLowerCase().endsWith(anExtArr)) {
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setFileName(file.getName());
                            fileInfo.setDir(false);
                            fileInfo.setFilePath(file.getPath());
                            fileInfo.setFileSize(file.length() + "");
                            fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                            fileInfo.setFileDir(file.getParent());
                            list.add(fileInfo);
                        }
                    }
                } else {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(file.getName());
                    fileInfo.setDir(false);
                    fileInfo.setFilePath(file.getPath());
                    fileInfo.setFileSize(file.length() + "");
                    fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                    fileInfo.setFileDir(file.getParent());
                    list.add(fileInfo);
                }
            } else {
                File[] files = file.listFiles();
                if (files.length > 0) {
                    for (File file1 : files) {
                        listFilePathChild(file1, extArr, list);
                    }
                } else {
                    Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(file.getName());
                    fileInfo.setDir(true);
                    fileInfo.setFilePath(file.getPath());
                    fileInfo.setFileSize(file.length() + "");
                    fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                    fileInfo.setFileDir(file.getParent());
                    list.add(fileInfo);
                }
            }
        }
    }

    /**
     * 获取目录下文件和目录（包含子目录）
     *
     * @param path
     * @param extDir 过滤文件目录
     * @param extArr 过滤文件
     * @return
     */
    public static List<FileInfo> listFileAll(String path, String extDir, String[] extArr) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
            for (File file1 : files) {
                if (StringUtils.isNotBlank(extDir)) {
                    //过滤目录
                    if (!file1.getName().equals(extDir)) {
                        listChild(file1, extDir, extArr, list);
                    }
                } else {
                    listChild(file1, extDir, extArr, list);
                }
            }
        }
        return list;
    }

    /**
     * 获取所有目录（包含子目录）
     * 只查询目录
     *
     * @param path
     * @return
     */
    public static List<FileInfo> listFileDirAll(String path) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(file1.getName());
                    fileInfo.setDir(true);
                    fileInfo.setFilePath(file1.getPath());
                    fileInfo.setFileSize(file1.length() + "");
                    fileInfo.setDate(DateUtil.longToDate(file1.lastModified()));
                    fileInfo.setFileDir(file1.getParent());
                    list.add(fileInfo);
                    listFielDirChild(file1, list);
                }
            }
        }
        return list;
    }

    /**
     * 复制单个文件
     *
     * @param oldFullFilePath 原文件路径 如：c:/fqf.txt
     * @param newDir          复制后路径 如：f:/fqf/test/
     * @param fileName        文件名称
     * @return
     */
    public static boolean copyFile(String oldFullFilePath, String newDir, String fileName) throws Exception {
        boolean flag = false;
        try {
            File myFilePath = new File(newDir);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldFullFilePath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldFullFilePath); //读入原文件
                String path = newDir + "/" + fileName;
                FileOutputStream fs = new FileOutputStream(path);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                flag = true;
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            throw e;
        }
        return flag;
    }

    private static void listChild(File file, String[] extArr, List<FileInfo> list) throws Exception {
        if (!file.exists()) {
            log.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (file.isFile()) {
                if (extArr != null) {
                    for (String anExtArr : extArr) {
                        if (file.getName().toLowerCase().endsWith(anExtArr)) {// 文件格式
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setFileName(file.getName());
                            fileInfo.setDir(false);
                            fileInfo.setFilePath(file.getPath());
                            fileInfo.setFileSize(file.length() + "");
                            fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                            fileInfo.setFileDir(file.getParent());
                            list.add(fileInfo);
                        }
                    }
                } else {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(file.getName());
                    fileInfo.setDir(false);
                    fileInfo.setFilePath(file.getPath());
                    fileInfo.setFileSize(file.length() + "");
                    fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                    fileInfo.setFileDir(file.getParent());
                    list.add(fileInfo);
                }
            } else {
                File[] files = file.listFiles();
                Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(file.getName());
                fileInfo.setDir(true);
                fileInfo.setFilePath(file.getPath());
                fileInfo.setFileSize(file.length() + "");
                fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                fileInfo.setFileDir(file.getParent());
                list.add(fileInfo);
                for (File file1 : files) {
                    listChild(file1, extArr, list);
                }
            }
        }
//        return list;
    }

    private static void listChild(File file, String extDir, String[] extArr, List<FileInfo> list) throws Exception {
        if (!file.exists()) {
            log.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (file.isFile()) {
                if (extArr != null) {
                    for (String anExtArr : extArr) {
                        if (file.getName().toLowerCase().endsWith(anExtArr)) {// 文件格式
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setFileName(file.getName());
                            fileInfo.setDir(false);
                            fileInfo.setFilePath(file.getPath());
                            fileInfo.setFileSize(file.length() + "");
                            fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                            fileInfo.setFileDir(file.getParent());
                            list.add(fileInfo);
                        }
                    }
                } else {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(file.getName());
                    fileInfo.setDir(false);
                    fileInfo.setFilePath(file.getPath());
                    fileInfo.setFileSize(file.length() + "");
                    fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                    fileInfo.setFileDir(file.getParent());
                    list.add(fileInfo);
                }
            } else {
                File[] files = file.listFiles();
                Arrays.sort(files, new FileSorterUtil(TYPE_NAME.getType()));
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(file.getName());
                fileInfo.setDir(true);
                fileInfo.setFilePath(file.getPath());
                fileInfo.setFileSize(file.length() + "");
                fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                fileInfo.setFileDir(file.getParent());
                list.add(fileInfo);
                for (File file1 : files) {
                    if (StringUtils.isNotBlank(extDir)) {
                        //过滤目录
                        if (!file1.getName().equals(extDir)) {
                            listChild(file1, extArr, list);
                        }
                    } else {
                        listChild(file1, extArr, list);
                    }
                }
            }
        }
    }

    public static void listFielDirChild(File file, List<FileInfo> list) throws Exception {
        if (!file.exists()) {
            log.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (!file.isFile()) {
                File[] files = file.listFiles();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(file.getName());
                fileInfo.setDir(true);
                fileInfo.setFilePath(file.getPath());
                fileInfo.setFileSize(file.length() + "");
                fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
                fileInfo.setFileDir(file.getParent());
                list.add(fileInfo);
                for (File file1 : files) {
                    listFielDirChild(file1, list);
                }
            }
        }
    }

    /**
     * 获取指定本地目录下的文件及文件夹（包含子文件），同时按指定类型过滤文件
     *
     * @param localPath  本地文件路径
     * @param filterType 过滤类型数组  只过滤扩展名 和 全文件名
     * @return list
     * @author chenlu@ccuu.me
     */
    public static List<FileInfo> findFileAndDir(String localPath, String[] filterType) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        findFiles(localPath, filterType, list, true);
        return list;
    }

    /**
     * 获取指定本地目录下的文件及文件夹（包含子文件），同时按指定类型过滤文件
     *
     * @param localPath  本地文件路径
     * @param filterType 过滤类型数组  只取
     * @return list
     * @author chenlu@ccuu.me
     */
    public static List<FileInfo> findFileAndDirForJustTake(String localPath, String[] filterType) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        findFilesJustTake(localPath, filterType, list, false);
        return list;
    }

    /**
     * 查询指定目录及子目录下的文件，同时按指定类型过滤文件
     *
     * @param localPath  本地路径
     * @param filterType 过滤类型
     * @return list
     * @author chenlu@ccuu.me
     */
    public static List<FileInfo> findFileList(String localPath, String[] filterType) throws Exception {
        List<FileInfo> list = Lists.newArrayList();
        findFiles(localPath, filterType, list, false);
        return list;
    }

    /**
     * 查询指定目录下的文件
     * 1.如果filterType 不为空，则按其列表过滤指定类型的文件
     * 2.如果flag 为true，查询的文件包含目录，否则只查询文件
     *
     * @param localPath  文件路径
     * @param filterType 过滤列表
     * @param list       lis
     * @param flag       true 结果包含目录，flase 结果不包含目录
     * @author chenlu@ccuu.me
     */
    public static void findFiles(String localPath, String[] filterType, List<FileInfo> list, boolean flag) throws Exception {
        File file = new File(localPath);
        if (!file.exists() || file.isFile()) {
            return;
        }

        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                findFiles(f.getPath(), filterType, list, flag);
            }
            FileInfo fileInfo = cpProperty(f);
            if (!flag && f.isDirectory()) {
                continue;
            }
            if (!f.isDirectory() && isIgnore(f.getName(), filterType)) {
                continue;
            }
            list.add(fileInfo);
        }
    }

    /**
     * 查询指定目录下的文件
     * 1.如果filterType 不为空，则按其列表过滤指定类型的文件
     * 2.如果flag 为true，查询的文件包含目录，否则只查询文件
     *
     * @param localPath  文件路径
     * @param filterType 过滤列表   只取扩展名
     * @param list       lis
     * @param flag       true 结果包含目录，flase 结果不包含目录
     * @author chenlu@ccuu.me
     */
    public static void findFilesJustTake(String localPath, String[] filterType, List<FileInfo> list, boolean flag) throws Exception {
        File file = new File(localPath);
        if (!file.exists() || file.isFile()) {
            return;
        }

        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                findFilesJustTake(f.getPath(), filterType, list, flag);
            }
            FileInfo fileInfo = cpProperty(f);
            if (!flag && f.isDirectory()) {
                continue;
            }
            if (!f.isDirectory() && !isIgnore(f.getName(), filterType)) {
                continue;
            }
            list.add(fileInfo);
        }
    }

    private static boolean isIgnore(String fileName, String[] types) throws Exception {
        if (types == null || types.length == 0) {
            return false;
        }
        for (String type : types) {
            if (fileName.toLowerCase().endsWith(type.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * file to fileInfo
     *
     * @param file
     * @return
     */
    private static FileInfo cpProperty(File file) throws Exception {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getName());
        fileInfo.setDir(file.isDirectory());
        fileInfo.setFilePath(file.getPath());
        fileInfo.setFileSize(file.length() + "");
        fileInfo.setDate(DateUtil.longToDate(file.lastModified()));
        fileInfo.setFileDir(file.getParent());
        return fileInfo;
    }

    public static void main(String[] args) throws Exception {
        String localPath = "C:\\test";
//        String[] extArr = {".conf"};
        String[] extArr = {"nginx"};
        List<FileInfo> list = LocalFileUtils.findFileAndDirForJustTake(localPath, extArr);
        for (FileInfo fileInfo : list) {
            System.out.println(fileInfo.getFilePath());
        }
    }
}
