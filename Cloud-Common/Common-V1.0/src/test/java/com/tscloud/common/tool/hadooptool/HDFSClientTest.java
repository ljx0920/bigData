package com.tscloud.common.tool.hadooptool;

import com.alibaba.fastjson.JSON;
import com.tscloud.common.tool.hadooptool.hdfs.HDFSClient;
import com.tscloud.common.tool.hadooptool.hdfs.IFileSystemClient;
import com.tscloud.common.tool.hadooptool.hdfs.IOVFile;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lei.yang1 on 2017/4/14.
 */
public class HDFSClientTest {

    private static String uri = "hdfs://192.168.56.101:9000";

    private static String xml = "E:/hirain/work/IOV/源码/trunk/IOV-Common/src/test/resource/core-site.xml";

    private IFileSystemClient client;

    @Before
    public void init() throws IOException, InterruptedException {
        client = new HDFSClient( uri,null, "hdfs" );
    }

    @Test
    public void uploadSpark() throws IOException {
//        client.mkdir( "/test001/demo");
        client.copyFromLocal( "D:\\home\\track_20171001.csv", "/hudw_test/car_data/");
//        client.delete( "/spark/demo/wordcount.jar", false );
//        client.copyFromLocal( "C:\\Users\\lei.yang1.HIRAIN\\Desktop\\wordcount.jar", "/spark/demo/wordcount.jar");
/*        client.copyFromLocal( "C:\\Users\\lei.yang1.HIRAIN\\Desktop\\spark-examples-1.6.0-cdh5.10.1-hadoop2.6.0-cdh5.10.1.jar",
                                    "/spark/spark-examples-1.6.0-cdh5.10.1-hadoop2.6.0-cdh5.10.1.jar");
        client.copyFromLocal( "C:\\Users\\lei.yang1.HIRAIN\\Desktop\\python.tar.gz","/spark/python.tar.gz");*/
    }

    @Test
    public void mkdir() throws IOException {
        client.mkdir( "/yltest" );
        IOVFile file = client.getFile( "/yltest" );
        assertTrue( file.getName().equals( "yltest" ) );
    }

    @Test
    public void getFile() throws IOException {
        client.createNewFile("/getFile" );
        IOVFile file = client.getFile( "/getFile" );
        assertTrue( file.getName().equals( "getFile" ) );
        try {
            client.getFile( "/12321321321" );
        } catch ( Exception e ){
            assertTrue( e instanceof  FileNotFoundException );
        }
    }

    @Test
    public void listFile( ) throws IOException {
        List<IOVFile> list = client.listFile( "/" );
        System.out.println( JSON.toJSON( list ) );
        assertTrue( list.size() > 0 );
    }

    @Test
    public void delete() throws IOException {
        assertTrue( client.delete("/yltest" ) );
    }

    @Test
    public void deleteRecursive() throws IOException {
        client.mkdir("hdfs://192.168.56.101:9000/delete/path" );
        assertTrue( client.delete( "hdfs://192.168.56.101:9000/delete/path", true ) );
    }

    @Test
    public void deleteOnExit() throws IOException {
        assertFalse( client.deleteOnExit("/deleteOnExit" ) );
        client.mkdir( "/deleteOnExit" );
        assertTrue( client.deleteOnExit("/deleteOnExit" ) );
    }

    @Test
    public void exists() throws IOException {
        client.mkdir( "/exists" );
        assertTrue( client.exists( "/exists" ) );
        client.delete( "/exists" );
        assertFalse( client.exists( "/exists" ) );
    }

    @Test
    public void create() throws IOException {
        OutputStream outputStream = client.create( "/a" );
        outputStream.write( "123456".getBytes() );
        outputStream.close();
    }

    @Test
    public void createOverwride() throws IOException {
        OutputStream outputStream = client.create( "/aa" );
        outputStream.write( "123456".getBytes() );
        outputStream.close();
        outputStream = client.create( "/aa" );
        outputStream.write( "234567".getBytes() );
        outputStream.close();
    }

    @Test
    public void createNewFile() throws IOException {
        assertTrue( client.createNewFile( "/newFile" ) );
        assertFalse( client.createNewFile( "/newFile" ) );
        client.delete( "/newFile" );
    }

    @Test
    public void getOutputStream() throws IOException {
        client.createNewFile("/nf2" );
        OutputStream outputStream = client.getOutputStream( "/nf2" );
        outputStream.write( "new11111".getBytes() );
        outputStream.close();
    }

    @Test
    public void getInputStream() throws IOException {
        InputStream in = client.getInputStream("/nf" );
        byte[] bs = new byte[1024];
        int n = 0;
        while (  ( n = in.read( bs ) ) > 0 ){
            System.out.print( new String( bs, 0, n ) );
        }
        in.close();
    }
}
