package com.tscloud.common.tool.hadooptool;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tscloud.common.tool.hadooptool.hbase.HBaseClient;
import com.tscloud.common.tool.hadooptool.hbase.HBaseWhere;
import com.tscloud.common.tool.hadooptool.hbase.exception.NamespaceExistException;
import com.tscloud.common.tool.hadooptool.hbase.exception.TableExistException;
import com.tscloud.common.tool.hadooptool.hbase.exception.TableNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by lei.yang1 on 2017/4/18.
 */
public class HBaseClientTest {

    private HBaseClient client;

    @Before
    public void init() throws IOException {
        client = new HBaseClient("slave4,slave5,slave6");
    }

    @Test
    public void createNamespace() throws IOException, NamespaceExistException {
        try {
            client.createNamespace("cjc");
        } catch (Exception e) {
            assertTrue(e instanceof NamespaceExistException);
        }
        assertTrue(client.namespaceExists("cjc"));

        System.out.println(client.namespaceExists("cjc"));
    }

    @Test
    public void createTable() throws IOException, TableExistException {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("cjc1");
        builder.add("cjc2");
        builder.add("cjc3");
        List<String> fsc = builder.build();
        client.createTable("cjc", "cjcTable2", fsc);

        try {
            //client.modifyTable( "cjc","cjcTable2", fsc );
        } catch (Exception e) {
            assertTrue(e instanceof TableExistException);
        }
        //    client.createTable( "ns", "createTable", fsc );
        assertTrue(client.tableExists("cjc", "cjcTable2"));
    }

    //将原来的字段除去,替换成新的Column Family,但是!!不能将旧的全部都删除掉,需要新列至少有一个与原Column Family名字相同
    @Test
    public void modifyTable() throws IOException, TableExistException, TableNotFoundException {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
/*        builder.add( "fa" );
        builder.add( "fb" );
        builder.add( "fc" );
        builder.add( "fd" );
        builder.add( "fe" );
        builder.add( "fg" );
        client.createTable("create", "test", builder.build() );*/
        builder = ImmutableList.builder();
        builder.add("cjc3");
        builder.add("fields2");
        builder.add("fields3");
        builder.add("fields4");
        builder.add("fields5");
        builder.add("fields6");
        client.modifyTable("cjc", "cjcTable2", builder.build());
    }

    @Test
    public void getTable() throws IOException {
        System.out.println(JSON.toJSON(client.getTable("cjc", "cjcTable2")));
    }

    @Test
    public void deleteTable() throws IOException {
        client.deleteTable("cjc", "cjcTable2");
    }

    @Test
    public void ListTable() throws IOException {
        System.out.print(client.listTables("default"));
    }

    @Test
    public void listTablesWithColumns() throws IOException {
        System.out.print(client.listTablesWithColumns("cjc"));
    }

    @Test
    public void getColums() throws IOException {
        System.out.print(client.getTable("cjc", "cjcTable2").entrySet());
    }

    @Test
    public void put() throws IOException {
        Map<String, Map<String, Object>> vs = Maps.newHashMap();
        Map<String, Object> d1 = Maps.newHashMap();
        d1.put("c3", "aaa");
        d1.put("c4", "dde");
        vs.put("cjc1", d1);
        /*Map<String, Object> dd = Maps.newHashMap();
        d1.put("dd1","test1234");
        d1.put("dd2",new Date());
        vs.put( "dd",dd );*/
        System.out.println(JSON.toJSON(vs));
        client.put("cjc", "cjcTable2", "lailalaia", vs);
    }

    @Test
    public void addColumnFamily() throws IOException, TableNotFoundException {
        String[] a = {"cjc4"};
        client.addColumnFamily("cjc", "cjcTable2", Arrays.asList(a));
    }

    @Test
    public void get() throws IOException {
        List list = client.get("cjc", "cjcTable2", "24kshuai");
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void delete() throws IOException {

        client.delete("create", "signal_upload_f_hb", "99");
    }

    @Test
    public void scan() throws IOException, NamespaceExistException, TableExistException {

/*        client.createNamespace("hbasetest");

        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add( "fc1" );
        builder.add( "fc2" );

        client.createTable("hbasetest", "test", builder.build() );*/

        for (int i = 10000; i < 20000; i++) {
            Map<String, Map<String, Object>> map = Maps.newHashMap();
            Map<String, Object> d1 = Maps.newHashMap();
            d1.put("id", "id" + i);
            d1.put("name", "name" + i);
            Map<String, Object> d2 = Maps.newHashMap();
            d2.put("age", i);
            d2.put("sex", 0);
            map.put("fc1", d1);
            map.put("fc2", d2);
            client.put("hbasetest", "test2", i + "", map);
        }

        HBaseWhere where1 = new HBaseWhere();
//        where1.equals("cjc1","c3","ccc");
        Map<String, List<Map<String, Object>>> result1 = client.search("cjc", "cjcTable2", where1, String.class);
        System.out.println("1---------------------------------");
        System.out.println(JSON.toJSON(result1));
        System.out.println("1---------------------------------");

/**
 HBaseWhere where2 = new HBaseWhere();
 where2.greater("cjc1","c4","bbc" );
 Map<Integer, List<Map<String, Object>>> result2 = client.search( "cjc", "cjcTable2", where2, Integer.class );
 System.out.println( result2.size() );
 System.out.println( JSON.toJSON( result2 ) );
 System.out.println("---------------------------------");

 HBaseWhere where3 = new HBaseWhere();
 where3.subString("cjc1","c4", "bbc" );
 /*       Map<Integer, List<Map<String, Object>>> result3 = client.search( "ns", "test", where3, Integer.class );
 System.out.println( result3.size() );
 System.out.println( JSON.toJSON( result3 ) );


 HBaseWhere where4 = new HBaseWhere();
 where4.regex( "d1", "d11", "test.*" );
 Map<Integer, List<Map<String, Object>>> result4 = client.search( "ns", "test", where4, Integer.class );
 System.out.println( result4.size() );
 System.out.println( JSON.toJSON( result4 ) );*//*

        System.out.println( client.count( "cjc", "cjcTable2", where1 ) );
        System.out.println("---------------------------------");
        System.out.println( client.count( "cjc", "cjcTable2", where2 ) );
        //System.out.println( client.count( "ns", "test", where3 ) );
        //System.out.println( client.count( "ns", "test", where4 ) );*/

/*
        HBaseWhere where5 = new HBaseWhere();
        where5.regex( "cjc1", "c3", "c.*" );
        where5.and( where3 );
        Map<Integer, List<Map<String, Object>>> result5 = client.search( "cjc", "cjcTable2", where5, Integer.class );
        System.out.println( result5.size() );
        System.out.println( JSON.toJSON( result5 ) );

        HBaseWhere where6 = new HBaseWhere();
        where6.greater("d1","d12",50 );
        where6.less("d1","d12",60 );

        HBaseWhere where7 = new HBaseWhere();
        where7.greater("d1","d12",10 );
        where7.less("d1","d12",40 );

        where6.or( where7 );
        Map<Integer, List<Map<String, Object>>> result6 = client.search( "ns", "test", where6, Integer.class );
        System.out.println( result6.size() );
        System.out.println( JSON.toJSON( result6 ) );

        HBaseWhere where8 = new HBaseWhere();
        where8.subString( "d1", "d11", "5" );
        Map<Integer, List<Map<String, Object>>> result8 = client.search( "ns", "test",
                                                                where8, 10,10, Integer.class );
        System.out.println( result8.size() );
        System.out.println( JSON.toJSON( result8 ) );*/
    }

    @Test
    public void getMuti() throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put(HBaseClient.VERSIONS, 100000);
        map.put(HBaseClient.TIME_START, 1483417207000L);
        map.put(HBaseClient.TIME_END, 1483418221000L);
        Collection<List<Map<String, Object>>> list = client.get("default", "signal_upload_f_hb3", "103", map);
        System.out.println("list size : " + list.size());
    }

    @Test
    public void getMuti2() throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put(HBaseClient.VERSIONS, 100000);
        map.put(HBaseClient.TIME_START, 1495784858000L);
        map.put(HBaseClient.TIME_END, 1495792601000L);
        List<String> familys = Lists.newArrayList();
        familys.add("fc1");
        map.put(HBaseClient.FAMILYS, familys);
        Collection<List<Map<String, Object>>> list = client.get("hbasetest", "test2", "10000", map);
        System.out.println("list size : " + list.size());
        System.out.println(JSON.toJSONString(list));
    }
}
