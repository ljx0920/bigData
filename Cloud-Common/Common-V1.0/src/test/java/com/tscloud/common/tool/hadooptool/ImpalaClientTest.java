package com.tscloud.common.tool.hadooptool;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tscloud.common.tool.hadooptool.impala.ImpalaClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by lei.yang1 on 2017/5/12.
 */
public class ImpalaClientTest {
    private ImpalaClient client = null;

    @Before
    public void init() throws SQLException, ClassNotFoundException {
//        client = new ImpalaClient("192.168.5.10", 21050, "default");
        client = new ImpalaClient("10.10.3.192", 21050, "it_select");
    }

    @Test
    public void query() throws SQLException {
//        List<Map<String, Object>> list = client.searchData("select * from test007");
//        List<Map<String, Object>> list = client.searchData("select sum(age), max(fv), name, city from test007 group by city, name order by city, name limit 100");
//        List<Map<String, Object>> list = client.searchData("select sum(driver_id), date_time from event group by date_time order by date_time limit 100");
        List<Map<String, Object>> list = client.searchData("select * from emp_select limit 100");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void queryToList() throws SQLException {
        List<List<Object>> list = client.searchDataToList("select * from track_test03 limit 10");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void getTableColumns() throws SQLException {
        List<Map<String, String>> list = client.getTableColumns("gf2");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void load() throws SQLException {
        Map<String, Object> partitions = Maps.newHashMap();
        partitions.put("year_month", "2017_01");
        client.load("/car/year=2016/0.csv", "SIGNAL_UPLOAD_F", false, partitions);
    }

    @Test
    public void addFunction() throws SQLException {
        String functionName = "level";
        String jarPath = "/impala-udf.test-1.0-SNAPSHOT.jar";
        String classPath = "LevelUdf";
        String returnType = "int";
        String argTypes = "int";
        String sql = client.addFunction(functionName, jarPath, classPath, returnType, argTypes);
        System.out.println(sql);
    }

    @Test
    public void deleteFunction() throws SQLException {
        String functionName = "level";
        String argTypes = "int";
        client.deleteFunction(functionName, argTypes);
    }

    @Test
    public void executeFunction() throws SQLException {
        List<Map<String, Object>> maps = client.searchData("SELECT level2(9);");
        System.out.println(maps);
    }

    @Test
    public void insert() throws SQLException {
        String sql = "insert into test007 values(?,?,?,?)";
        Object[] values = {"abc", 34, 20, "sh"};
        assertTrue(client.insertData(sql, values));
    }

    @Ignore
    @Test
    public void insertRep() throws SQLException {
        String sql = "insert into test007 values(?,?,?,?)";
        for (int i = 0; i < 100; i++) {
            Object[] values = {"abc", i * 4 + 2, i * 5 + 3, "bj"};
            client.insertData(sql, values);

            Object[] values1 = {"def", i * 3 + 1, i * 3 + 2, "bj"};
            client.insertData(sql, values1);

            Object[] values2 = {"abc", i * 6 + 2, i * 7 + 1, "bj"};
            client.insertData(sql, values2);
        }
    }

    @After
    public void close() {
        client.close();
    }
}
