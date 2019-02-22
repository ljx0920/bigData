package com.tscloud.common.tool.hadooptool.hdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Administrator
 * @date 2015/12/10
 */
public class FileUtil {

    public static String[] splitFilePath(String path) {
        if (path.endsWith(IOVFile.SEPARATOR)) {
            throw new RuntimeException("dir is invaild.");
        }
        int lindex = path.lastIndexOf(IOVFile.SEPARATOR);
        if (lindex == -1) {
            throw new RuntimeException("dir is invaild.");
        }
        String dir = path.substring(0, lindex);
        String name = path.substring(lindex + 1, path.length());
        String[] fp = {dir, name};
        return fp;
    }

    public static String[] splitParentPath(String path) {
        if (path.endsWith(IOVFile.SEPARATOR)) {
            throw new RuntimeException("dir is invaild.");
        }
        int lindex = -1;
        if (path.startsWith(IOVFile.SEPARATOR)) {
            lindex = path.substring(1).indexOf(IOVFile.SEPARATOR);
            lindex++;
        } else {
            lindex = path.indexOf(IOVFile.SEPARATOR);
        }
        if (lindex == -1) {
            throw new RuntimeException("dir is invaild.");
        }
        String parent = path.substring(0, lindex);
        String sub = path.substring(lindex + 1, path.length());
        String[] fp = {parent, sub};
        return fp;
    }

    public static long appendRandomFile(String filename, byte[] bytes) throws IOException {
        long fileLen = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
            fileLen = randomAccessFile.length();
            randomAccessFile.seek(fileLen);
            randomAccessFile.write(bytes);
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            System.out.print("File : " + filename + " not found.");
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            System.out.print("File : " + filename + " io error.");
            e.printStackTrace();
            throw e;
        }

        return fileLen;
    }
}
