package com.tscloud.common.tool.hadooptool;

import com.tscloud.common.tool.dbtool.DSUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lei.yang1 on 2017/4/23.
 */
public class DSUtilsTest {

    private static final String DRIVE_NAME = "com.mysql.jdbc.Driver";

    private Connection con = null;

    @Before
    public void getConnection() {
        String url = "jdbc:mysql://192.168.5.12:3306/hive";
        String userName = "root";
        String password = "123456";
        con = DSUtils.getConn(DRIVE_NAME, url, userName, password);
    }

    @Test
    public void getAllTableName() {
        List<String> allTableName = DSUtils.getAllTableName(con);
        System.out.println(allTableName);
    }

    @Test
    public void getAllTableNameBySchema() {
        List<String> tableNameList = DSUtils.getAllTableName(con, "yl_test");
        System.out.println(tableNameList);
    }

    /**
     * 错误
     * tinyint返回了bit  id的长度为11返回了10
     */
    @Test
    public void findTableColumnList() {
        List<Map<String, String>> tableColumnList = DSUtils.findTableColumnList(con, "TYPE_FIELDS");
        System.out.println(tableColumnList);
    }

    /**
     * 错误
     * tinyint返回了bit  id的长度为11返回了10
     */
    @Test
    public void findTableColumnListBySchema() {
        List<Map<String, String>> tableColumnList = DSUtils.findTableColumnList(con, "yl_test", "TYPE_FIELDS");
        System.out.println(tableColumnList);
    }

    @After
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
