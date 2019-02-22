package com.tscloud.common.tool.hadooptool.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by lei.yang1 on 2017/4/17.
 */
public interface IFileSystemClient {

    /**
     * 创建目录
     * @param path 目录路径
     * @return 成功创建目录，返回 true；否则返回 false
     */
    boolean mkdir(String path) throws IOException;

    /**
     * 获取文件描述
     * @param path  文件路径
     * @return      文件描述
     * @throws IOException
     */
    IOVFile getFile(String path) throws IOException;

    /**
     * 列出指定当前目录下所有的文件及目录
     * @param path      指定目录
     * @return          所有的文件及目录列表
     * @throws IOException
     */
    List<IOVFile> listFile(String path) throws IOException;

    /**
     * 删除指定目录
     * @param path   需要被删除的目录
     * @return       当且仅当成功删除文件或目录时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean delete(String path) throws IOException;

    /**
     * 删除指定目录
     * @param path          需要被删除的目录
     * @param recursive     是否需要递归删除
     * @return       当且仅当成功删除文件或目录时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean delete(String path, boolean recursive) throws IOException;

    /**
     * 删除指定目录
     * @param path      需要被删除的目录
     * @return          当且仅当成功删除文件或目录时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean deleteOnExit(String path) throws IOException;

    /**
     * 此文件或者目录是否存在
     * @param path      目录或者文件的路径
     * @return          当且仅当此抽象路径名表示的文件或目录存在时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean exists(String path) throws IOException;

    /**
     * 创建一个文件输出流，如果文件存在则覆盖
     * @param path  文件路径
     * @return      文件输出流
     * @throws IOException
     */
    OutputStream create(String path) throws IOException;

    /**
     * 创建一个文件输出流
     * @param path      文件路径
     * @param overwrite 是否覆盖
     * @return          文件输出流
     * @throws IOException
     */
    OutputStream create(String path, boolean overwrite) throws IOException;

    /**
     * 按照指定路径创建一个大小为0的文件
     * @param path  文件路径
     * @return      是否创建成功
     * @throws IOException
     */
    boolean createNewFile(String path) throws IOException;
    /**
     * 打开一个文件的输出流，向此输出流写入的内容将追加到指定的文件中。
     * @param path      需要追加内容的文件路径
     * @return          文件输出流
     * @throws IOException
     */
    OutputStream getOutputStream(String path) throws IOException;

    /**
     * 打开一个文件的输入流
     * @param path      需要读取的文件路径
     * @return          文件输入流
     * @throws IOException
     */
    InputStream getInputStream(String path) throws IOException;

    /**
     * 把本地文件拷贝至文件系统
     * @param src   本地目录
     * @param dst   文件系统目录
     * @throws IOException
     */
    void copyFromLocal(String src, String dst) throws IOException;

    /**
     * 把文件系统的文件拷贝至本地
     * @param src   文件系统目录
     * @param dst   本地目录
     * @throws IOException
     */
    void copyToLocal(String src, String dst) throws IOException;

    /**
     * 从输入流中读取数据并保存到文件系统中的指定文件中
     * @param in     输入流
     * @param dst    目标文件路径
     * @throws Exception
     */
    void saveFile(InputStream in, String dst) throws Exception;

    /**
     * 写csv文件
     * @param path
     * @param dataList
     * @throws Exception
     */
     void saveFile(String path,List<String> dataList) throws Exception;

    /**
     * 从指定目录下读取文件内容到输出流中
     * @param path
     * @param out
     * @return
     * @throws Exception
     */
    boolean fetchFile(String path, OutputStream out) throws Exception;

    /**
     * 重命名文件或者目录
     * @param path      指定文件或者目录路径
     * @param name      修改后的名称
     * @throws Exception
     */
    void rename(String path, String name) throws Exception;

    /**
     * 修改文件所有者
     * @throws Exception
     */
    void setOwner( String path, String owner ) throws Exception;

    /**
     * close conn
     */
    void close();
}
