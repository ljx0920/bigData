package com.tscloud.common.tool.hadooptool.hdfs;

import com.google.common.collect.ImmutableList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

/**
 * HDFS 客户端工具类
 * 通过初始化的客户端可以完成文件或者目录的查询以及读写等功能。
 *
 * @author lei.yang1
 * @date 2017/4/17
 */
public class HDFSClient implements IFileSystemClient {
    private static final Logger log = LogManager.getLogger(HDFSClient.class);

    private FileSystem fileSystem;

    /**
     * 获取HDFS客户端，加载classpath下配置文件core-site.xml进行客户端的初始化，
     * 如果classpath下边没有此文件则加载hadoop-common里边的core-default.xml默认配置进行初始化。
     *
     * @param url HDFS的URI地址，格式为：hdfs://192.168.56.103:8020
     * @throws IOException
     */
    public HDFSClient(String url) throws IOException {
        Configuration configuration = new Configuration();
        this.fileSystem = FileSystem.get(URI.create(url), configuration);
    }

    /**
     * 获取HDFS客户端，加载指定目录下的xml配置文件进行客户端的初始化。
     *
     * @param url      HDFS的URI地址，格式为：hdfs://192.168.56.103:8020
     * @param resource 配置文件路径
     * @throws IOException
     */
    public HDFSClient(String url, String resource) throws IOException {
        Configuration configuration = new Configuration();
        configuration.addResource(new File(resource).toURI().toURL());
        this.fileSystem = FileSystem.get(URI.create(url), configuration);
    }

    /**
     * 获取HDFS客户端，加载指定目录下的xml配置文件进行客户端的初始化。
     *
     * @param url      HDFS的URI地址，格式为：hdfs://192.168.56.103:8020
     * @param resource 配置文件路径
     * @param user     hdfs使用用户
     * @throws IOException
     * @throws InterruptedException
     */
    public HDFSClient(String url, String resource, String user) throws IOException, InterruptedException {
        Configuration configuration = new Configuration();
        if (resource != null) {
            configuration.addResource(new File(resource).toURI().toURL());
        }
        this.fileSystem = FileSystem.get(URI.create(url), configuration, user);
    }

    @Override
    public boolean mkdir(String path) throws IOException {
        return this.getFileSystem().mkdirs(new Path(path));
    }

    @Override
    public IOVFile getFile(String path) throws IOException {
        return new IOVFile(fileSystem.getFileStatus(new Path(path)));
    }

    @Override
    public List<IOVFile> listFile(String path) throws IOException {
        Path p = new Path(path);
        FileStatus[] files = this.getFileSystem().listStatus(p);
        ImmutableList.Builder<IOVFile> builder = ImmutableList.builder();
        for (FileStatus file : files) {
            builder.add(new IOVFile(file));
        }
        return builder.build();
    }

    @Override
    public boolean delete(String path) throws IOException {
        Path p = new Path(path);
        if (fileSystem.exists(p)) {
            return fileSystem.delete(p, true);
        }
        return false;
    }

    @Override
    public boolean delete(String path, boolean recursive) throws IOException {
        return fileSystem.delete(new Path(path), recursive);
    }

    @Override
    public boolean deleteOnExit(String path) throws IOException {
        return fileSystem.deleteOnExit(new Path(path));
    }

    @Override
    public boolean exists(String path) throws IOException {
        return fileSystem.exists(new Path(path));
    }

    @Override
    public OutputStream create(String path) throws IOException {
        return fileSystem.create(new Path(path));
    }

    @Override
    public OutputStream create(String path, boolean overwrite) throws IOException {
        return fileSystem.create(new Path(path), overwrite);
    }

    @Override
    public boolean createNewFile(String path) throws IOException {
        return fileSystem.createNewFile(new Path(path));
    }

    @Override
    public OutputStream getOutputStream(String path) throws IOException {
        return fileSystem.append(new Path(path));
    }

    @Override
    public InputStream getInputStream(String path) throws IOException {
        return fileSystem.open(new Path(path));
    }

    @Override
    public void copyFromLocal(String src, String dst) throws IOException {
        fileSystem.copyFromLocalFile(new Path(src), new Path(dst));
    }

    @Override
    public void copyToLocal(String src, String dst) throws IOException {
        fileSystem.copyToLocalFile(new Path(src), new Path(dst));
    }

    @Override
    public void saveFile(InputStream in, String dst) throws Exception {
        if (exists(dst)) {
            throw new FileExistsException(" file (" + dst + ") exists.");
        }
        createNewFile(dst);
        try (OutputStream out = getOutputStream(dst)) {
            write(in, out);
        }
    }

    @Override
    public void saveFile(String path, List<String> dataList) throws Exception {
        FSDataOutputStream os = fileSystem.create(new Path(path));
        for (String str : dataList) {
            byte[] buff = str.getBytes();
            os.write(buff, 0, buff.length);
        }
        os.flush();
        os.close();
    }

    @Override
    public boolean fetchFile(String path, OutputStream out) throws Exception {
        if (!exists(path)) {
            return false;
        }
        try (InputStream in = getInputStream(path)) {
            write(in, out);
        }
        return true;
    }

    @Override
    public void rename(String path, String name) throws Exception {
        String[] paths = FileUtil.splitParentPath(path);
        String newPath = paths[0] + IOVFile.SEPARATOR + name;
        fileSystem.rename(new Path(path), new Path(newPath));
    }

    @Override
    public void setOwner(String path, String owner) throws Exception {
        fileSystem.setOwner(new Path(path), owner, owner);
    }

    private void write(InputStream in, OutputStream os) throws IOException {
        byte[] bs = new byte[1024];
        int len;
        while ((len = in.read(bs)) > 0) {
            os.write(bs, 0, len);
        }
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    @Override
    public void close() {
        try {
            this.getFileSystem().close();
        } catch (Exception e) {
            log.error("HDFS conn close error!", e);
        }
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
}
