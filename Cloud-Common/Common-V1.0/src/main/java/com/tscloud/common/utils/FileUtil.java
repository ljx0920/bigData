package com.tscloud.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 文件操作帮助类
 *
 * @author yanglei
 */
public class FileUtil {
    private static Logger LOG = LogManager.getLogger(FileUtil.class);

    /**
     * 创建文件
     *
     * @param pathStr  路径
     * @param fileName 文件名字
     * @return File实例
     */
    public static File createFile(String pathStr, String fileName) {
        File path = new File(pathStr);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(pathStr + "/" + fileName);
        if (file.exists()) {
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1) {
                file = new File(pathStr + "/" + fileName.substring(0, dotIndex) + System.currentTimeMillis()
                        + fileName.substring(dotIndex, fileName.length()));
            } else {
                file = new File(pathStr + "/" + fileName + System.currentTimeMillis());
            }
        }
        return file;
    }

    /**
     * 创建目录
     * 支持创建多级目录
     *
     * @param folderPath
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹
     *
     * @return boolean
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            java.io.File myFilePath = new java.io.File(folderPath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径 如 c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (String aTempList : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + aTempList);
            } else {
                temp = new File(path + File.separator + aTempList);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + aTempList);//先删除文件夹里面的文件
                delFolder(path + "/" + aTempList);//再删除空文件夹
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean flag = false;
        try {
            int byteSum = 0;
            int byteRead = 0;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead; //字节数 文件大小
                    System.out.println(byteSum);
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
                flag = true;
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        boolean flag = false;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (String aFile : file) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + aFile);
                } else {
                    temp = new File(oldPath + File.separator + aFile);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()));
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + aFile, newPath + "/" + aFile);
                }
            }
            flag = true;
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath   String 原文件路径 如：c:/fqf
     * @param newPath   String 复制后路径 如：f:/fqf/ff
     * @param filterDir String    过滤目录名称
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath, String filterDir) {
        boolean flag = false;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (String aFile : file) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + aFile);
                } else {
                    temp = new File(oldPath + File.separator + aFile);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()));
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    if (!aFile.equals(filterDir)) {
                        copyFolder(oldPath + "/" + aFile, newPath + "/" + aFile, filterDir);
                    }
                }
            }
            flag = true;
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static boolean moveFile(String oldPath, String newPath) {
        boolean flag = copyFile(oldPath, newPath);
        if (flag) {
            delFile(oldPath);
        }
        return flag;
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath, null);
        delFolder(oldPath);
    }

    public static void delFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    delFile(f);
                }
                file.delete();
            }
        }
    }

    public static void delFile(String path) {
        delFile(new File(path));
    }

    /**
     * copy 文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
        }
    }

    /**
     * @return
     * @throws Exception
     */
    public static File getFile(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if (propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }
        File file = null;

        if (filePath == null || "".equals(filePath)) {
            URL url = FileUtil.class.getClassLoader().getResource(propertyName + fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }
            file = new File(url.getPath());
        } else {
            filePath = filePath.endsWith("/") ? filePath.concat(fileName)
                    : filePath.concat("/").concat(fileName);
            file = new File(filePath);
        }
        return file;
    }

    public static String getFilePath(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if (propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }

        if (filePath == null || "".equals(filePath)) {

            URL url = FileUtil.class.getClassLoader().getResource(propertyName + fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }
            filePath = url.getPath();
        } else {
            filePath = filePath.endsWith("/") ? filePath.concat(fileName)
                    : filePath.concat("/").concat(fileName);
        }
        return filePath;
    }

    public static String getFileDir(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if (propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }

        if (filePath == null || "".equals(filePath)) {

            URL url = FileUtil.class.getClassLoader().getResource(propertyName + fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }
            filePath = url.getPath();
            filePath = filePath.replace(fileName, "");
        } else {
            filePath = filePath.endsWith("/") ? filePath.concat(fileName)
                    : filePath.concat("/").concat(fileName);
        }
        return filePath;
    }

    /**
     * @param file
     */
    public static String read(File file, String charset) {
        final byte[] content = read(file);
        return content == null ? "" : new String(content);
    }

    public static byte[] read(File file) {
        if (!(file.exists() && file.isFile())) {
            throw new IllegalArgumentException("The remote not exist or not a remote");
        }
        FileInputStream fis = null;
        byte[] content = null;
        try {
            fis = new FileInputStream(file);
            content = new byte[fis.available()];
            fis.read(content);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
                fis = null;
            }
        }
        return content;
    }

    /**
     * 将saveProperties保存为文件
     *
     * @param filePath
     * @param parameterName
     * @param parameterValue
     */
    public static void saveProperties(String filePath, String parameterName, String parameterValue) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            OutputStream fos = new FileOutputStream(filePath);
            prop.setProperty(parameterName, parameterValue);
            prop.store(fos, "Update '" + parameterName + "' value");
            fis.close();
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
        }
    }

    /**
     * 获取目录下所有文件和目录（不包含子目录）
     *
     * @param extArr
     * @return
     */
    public static List<Map<String, String>> listFile(String dir, String[] extArr) {
        List<Map<String, String>> mapList = Lists.newArrayList();
        File file = new File(dir);
        if (!file.exists()) {
            LOG.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (file.isFile()) {
                if (extArr != null) {
                    for (String anExtArr : extArr) {
                        if (file.getName().toLowerCase().endsWith(anExtArr)) {// 文件格式
                            hm.put("fileName", file.getName());
                            hm.put("isDir", "false");
                            hm.put("path", file.getPath());
                            hm.put("size", file.length() + "");
                            hm.put("modified", DateUtil.format(file.lastModified()));
                            hm.put("fileDir", file.getParent());
                            mapList.add(hm);
                        }
                    }
                } else {
                    hm.put("fileName", file.getName());
                    hm.put("isDir", "false");
                    hm.put("path", file.getPath());
                    hm.put("size", file.length() + "");
                    hm.put("modified", DateUtil.format(file.lastModified()));
                    hm.put("fileDir", file.getParent());
                    mapList.add(hm);
                }
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {

                    }
                    if (extArr != null) {
                        if (file.getName().toLowerCase().endsWith(extArr[i])) {// 文件格式
                            Map<String, String> map = Maps.newHashMap();
                            map.put("fileName", files[i].getName());
                            //为目录true;
                            map.put("isDir", "true");
                            map.put("path", files[i].getPath());
                            map.put("size", files[i].length() + "");
                            map.put("modified", DateUtil.format(files[i].lastModified()));
                            map.put("count", files[i].length() + "");
                            map.put("fileDir", files[i].getParent());
                            mapList.add(map);
                        }
                    } else {
                        Map<String, String> map = Maps.newHashMap();
                        map.put("fileName", files[i].getName());
                        //为目录true;
                        map.put("isDir", "true");
                        map.put("path", files[i].getPath());
                        map.put("size", files[i].length() + "");
                        map.put("modified", DateUtil.format(files[i].lastModified()));
                        map.put("count", files[i].length() + "");
                        map.put("fileDir", files[i].getParent());
                        mapList.add(map);
                    }
                }
            }
        }
        return mapList;
    }

    /**
     * 获取目录下文件和目录（包含子目录）
     *
     * @param path
     * @param extArr
     * @return
     */
    public static List<Map<String, String>> listFileAll(String path, String[] extArr) {
        List<Map<String, String>> mapList = Lists.newArrayList();
        File file = new File(path);
        if (file.exists()) {
            mapList = listChild(file, extArr, mapList);
        }
        return mapList;
    }

    private static List<Map<String, String>> listChild(File file, String[] extArr, List<Map<String, String>> mapList) {
        if (!file.exists()) {
            LOG.info("文件名称不存在!");
        } else {
            Map<String, String> hm = Maps.newHashMap();
            if (file.isFile()) {
                if (extArr != null) {
                    for (String anExtArr : extArr) {
                        if (file.getName().toLowerCase().endsWith(anExtArr)) {// 文件格式
                            hm.put("fileName", file.getName());
                            hm.put("isDir", "false");
                            hm.put("path", file.getPath());
                            hm.put("size", file.length() + "");
                            hm.put("modified", DateUtil.format(file.lastModified()));
                            hm.put("fileDir", file.getParent());
                            mapList.add(hm);
                        }
                    }
                } else {
                    hm.put("fileName", file.getName());
                    hm.put("isDir", "false");
                    hm.put("path", file.getPath());
                    hm.put("size", file.length() + "");
                    hm.put("modified", DateUtil.format(file.lastModified()));
                    hm.put("fileDir", file.getParent());
                    mapList.add(hm);
                }
            } else {
                File[] files = file.listFiles();
                hm.put("fileName", file.getName());
                //为目录true;
                hm.put("isDir", "true");
                hm.put("path", file.getPath());
                hm.put("size", file.length() + "");
                hm.put("modified", DateUtil.format(file.lastModified()));
                hm.put("count", files.length + "");
                hm.put("fileDir", file.getParent());
                mapList.add(hm);
                for (File file1 : files) {
                    listChild(file1, extArr, mapList);
                }
            }
        }
        return mapList;
    }

    /**
     *    * 删除单个文件
     *    * @param filePath
     *    * 文件目录路径
     *    * @param fileName
     *    * 文件名称
     *    
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isFile()) {
                    if (file1.getName().equals(fileName)) {
                        file1.delete();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 流转byte数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static void main(String[] arr) {
        FileUtil.copyFolder("C:\\test\\source_data\\ptgui\\0100002\\2015\\10-02\\ptgui_project", "E:\\outputfile\\result_data\\ptgui\\0100002\\2015\\10-02\\ptgui_project", "task");

//		String[] arr_ = new String[1];
//		arr_[0] = "exe";
//		String path = "D:\\opt\\share";
//		List<Map<String,String>> mapList = FileUtil.listFileAll(path, null);
//		for(Map<String,String> one:mapList){
//            System.out.println(one.get("fileName") + "--" + one.get("path") + "---" + one.get("size")+ "---" + one.get("count")+ "---" + one.get("modified")+ "---" + one.get("fileDir"));
//
//		}
//
//		FileUtil.newFolder("e:/rr1/ee/qw/www/wwq/qqq");
//		FileUtil.moveFile("e:/rr1/1111.txt","e:/rr1/ee/qw/www/wwq/qqq/1111.txt");
//
//		String shareDir = "e:/ed/ed/r/d/";
//		String filepath = "e:/ed/ed/r/d//frfr";
//
//		shareDir = shareDir.replace("\\","/");
//		filepath = filepath.replace("\\","/");
//		StringBuffer tmp = new StringBuffer(filepath);
//		tmp.insert(shareDir.length(),"/backupDir");
//		String backupDir=tmp.toString();
//		backupDir = backupDir.replace("//","/");
//		System.out.println(backupDir);

    }

    /**
     * 数据文件，返回文件内容
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {
        BufferedReader reader = null;
        StringBuilder lastStr = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                lastStr.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lastStr.toString();
    }

    public static List<File> listFiles(File path) {
        List<File> result = Lists.newLinkedList();
        List<File> root = Lists.newLinkedList();
        if (path.isDirectory()) {
            root.addAll(Arrays.asList(path.listFiles()));
        }
        while (root.size() > 0) {
            File file = root.remove(0);
            if (file.isFile()) {
                result.add(file);
            } else {
                root.addAll(Arrays.asList(file.listFiles()));
            }
        }
        return result;
    }
}