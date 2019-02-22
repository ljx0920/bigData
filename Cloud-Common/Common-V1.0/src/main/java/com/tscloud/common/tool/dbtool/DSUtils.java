package com.tscloud.common.tool.dbtool;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tscloud.common.framework.Constants.TableInfo;
import com.tscloud.common.framework.Exception.DubboProviderException;
import com.tscloud.common.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static com.tscloud.common.framework.Constants.DataBase.MYSQL;
import static com.tscloud.common.framework.Constants.DataBase.ORACLE;
import static com.tscloud.common.framework.Constants.MysqlColumn.*;
import static com.tscloud.common.framework.Constants.OracleColumn.*;
import static com.tscloud.common.framework.Constants.TableInfo.*;

/**
 * @author daowan.hu
 */
public class DSUtils {

    private static final String COLUMN_NAME = "column_name";
    /**
     * ******************************请不要在此类中做业务处理方法**********************************
     */
    protected static Logger log = LogManager.getLogger(DSUtils.class);

    public static void createDatabase(Connection connection, String database) {
        executeUpdateSql(connection, "create database " + database
                + " DEFAULT CHARSET utf8 COLLATE utf8_general_ci");
    }

    public static void deleteDatabase(Connection connection, String database) {
        executeUpdateSql(connection, "drop database " + database);
    }

    /**
     * get the connection
     */
    public static Connection getConn(String driveName, String url, String userName, String password) {
        Connection con = null;
        try {
            DruidDataSource dataSource = DSPool.getDataSource(driveName, url, userName, password);
            if (dataSource != null) {
                con = dataSource.getConnection();
            }
        } catch (Exception e) {
            log.error("get connection error,{driveName:{}, url:{}, userName:{}, password:{}}", driveName, url, userName, password, e);
        }
        return con;
    }

    /**
     * close DB connection
     *
     * @param con DB connection
     */
    public static void closeConn(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
            con = null;
        } catch (Exception ignore) {
            log.error("[DSUtils] 数据库关闭连接失败！");
        }
    }

    /**
     * 判断是否为主键
     *
     * @param conn
     * @param tableName
     * @param columnName
     * @return
     */
    public static boolean isPK(Connection conn, String tableName, String columnName) {
        boolean isPK = false;
        try {
            DatabaseMetaData dsData = conn.getMetaData();
            ResultSet rs = dsData.getPrimaryKeys(null, null, tableName);

            try {
                while (rs.next()) {
                    if (columnName.equals(rs.getString(COLUMN_NAME))) {
                        isPK = true;
                        break;
                    }
                }
            } catch (SQLException e) {
                log.error("query table structure error,{tableName:{}, columnName:{}}", tableName, columnName, e);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return isPK;
    }

    /**
     * 获取数据库中的所有表名
     *
     * @param conn
     * @return
     * @author cai_cf@126.com
     */
    public static List<String> getAllTableName(Connection conn) {
        return getAllTableName(conn, null);
    }

    public static List<String> getAllTableName(Connection conn, String schema) {
        List<String> tables = Lists.newLinkedList();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rsTables = meta.getTables(conn.getCatalog(), schema,
                    "%", new String[]{"TABLE"});
            while (rsTables.next()) {
                tables.add(rsTables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("获取数据库中的所有表的表名", e);
        }
        return tables;
    }

    /**
     * 判断数据库表是否存在
     *
     * @param conn
     * @param schema
     *@param tableName  @return
     */
    public static boolean existsTable(Connection conn, String schema, String tableName) {
        boolean flag = false;
        try {
            DatabaseMetaData meta = conn.getMetaData();
            tableName = upperCaseTableNameForOracle(conn, tableName);

            ResultSet rsTables = meta.getTables(null, schema, tableName, null);
            if (rsTables.next()) {
                flag = true;
                log.info(tableName + "已经存在");
            } else {
                log.info(tableName + "不存在");
            }
        } catch (SQLException e) {
            log.error("判断数据库表是否存在错误", e);
        }
        return flag;
    }

    private static String upperCaseTableNameForOracle(Connection conn, String tableName) throws SQLException {
        if (ORACLE.equals(conn.getMetaData().getDatabaseProductName().toLowerCase())) {
            tableName = tableName.toUpperCase();
        }
        return tableName;
    }

    /**
     * 获取表字段属性
     *
     * @param conn
     * @param tableName
     * @return
     * @throws NullPointerException
     */
    public static List<Map<String, String>> findTableColumnList(Connection conn, String tableName) throws NullPointerException {
        return findTableColumnList(conn, null, tableName);
    }

    public static List<Map<String, String>> findTableColumnList(Connection conn, String schema, String tableName) throws NullPointerException {
        verify(tableName, conn);
        List<Map<String, String>> columnsList = Lists.newArrayList();
        try {
            tableName = upperCaseTableNameForOracle(conn, tableName);
            DatabaseMetaData dmd = conn.getMetaData();
            if (dmd == null) {
                log.error("The DatabaseMetaData Object was null.");
                return columnsList;
            }
            ResultSet rs = dmd.getColumns(conn.getCatalog(), schema, tableName, "%");
            rs2List(rs, columnsList, conn, tableName);
        } catch (SQLException e) {
            log.error("数据库连接失败！", e);
        }
        return columnsList;
    }

    private static void verify(String tableName, Connection conn) throws NullPointerException {
        if (StringUtils.isBlank(tableName)) {
            throw new NullPointerException("the table name is null.");
        }
        if (conn == null) {
            throw new NullPointerException("the connection is null");
        }
    }

    private static void rs2List(ResultSet rs, List<Map<String, String>> columnsList, Connection conn, String tableName) {
        try {
            while (rs.next()) {
                Map<String, String> map = Maps.newHashMap();
                String type = rs.getString(TYPE_NAME);
                String isNull = rs.getString(NULLABLE);

                map.put(TableInfo.COLUMN_NAME, rs.getString(TableInfo.COLUMN_NAME));
                map.put(TYPE_NAME, type);
                map.put(COLUMN_DEF, rs.getString(COLUMN_DEF));

                String dataBaseType = conn.getMetaData().getDatabaseProductName().toLowerCase();
                map.put(COLUMN_SIZE, getColumnSize(dataBaseType, rs));
                map.put(ISNULL, isNull);
                map.put(IS_PK, isPK(conn, tableName, rs.getString(TableInfo.COLUMN_NAME)) ? "1" : "0");
                columnsList.add(map);
            }
        } catch (Exception e) {
            log.error("query table structure error," + e.getMessage(), e);
        }
    }

    private static String getColumnSize(String dataBaseType, ResultSet rs) {
        String size = null;
        try {
            String type = rs.getString(TYPE_NAME);
            //判断数据库类型,来进行判断类型
            switch (dataBaseType) {
                case MYSQL:
                    switch (type) {
                        case YEAR:
                        case DATETIME:
                        case BINARY:
                        case VARBINARY:
                        case TINYINT:
                        case SMALLINT:
                        case MEDIUMINT:
                        case INT:
                        case BIGINT:
                        case BIT:
                        case CHAR:
                        case VARCHAR:
                            size = rs.getString(COLUMN_SIZE);
                            break;
                        case DECIMAL:
                        case DOUBLE:
                        case FLOAT:
                            size = rs.getString(COLUMN_SIZE) + "," + rs.getString(DECIMAL_DIGITS);
                            break;
                        default:
                            break;
                    }
                    break;
                case ORACLE:
                    switch (type) {
                        case VARCHAR2:
                        case ORACLE_CHAR:
                        case RAW:
                        case NVARCHAR2:
                        case TIMESTAMP:
                        case TIMESTAMP_WITH_LOCAL_TIME_ZONE:
                        case TIMESTAMP_WITH_TIME_ZONE:
                            size = rs.getString(COLUMN_SIZE);
                            break;

                        case INTERVAL_DAY_TO_SECOND:
                        case INTERVAL_YEAR_TO_MONTH:
                        case NUMBER:
                            size = "0".equals(rs.getString(DECIMAL_DIGITS))
                                    ? rs.getString(COLUMN_SIZE)
                                    : rs.getString(COLUMN_SIZE) + "," + rs.getString(DECIMAL_DIGITS);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    throw new DubboProviderException("not support type ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static List<Map<String, String>> findGPTableColumnList(Connection conn, String tableName) {
        verify(tableName, conn);
        List<Map<String, String>> columnsList = Lists.newArrayList();
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            if (dmd == null) {
                log.error("The DatabaseMetaData Object was null .");
                return columnsList;
            }

            ResultSet rs = dmd.getColumns(null, null, tableName, "%");
            try {
                while (rs.next()) {
                    Map<String, String> map = Maps.newHashMap();
                    String type = rs.getString(TYPE_NAME);
                    String name = rs.getString(TableInfo.COLUMN_NAME);
                    if ("UNKNOWN".equals(type)) {
                        type = getColumnType(tableName, name, conn);
                    }
                    String isNull = rs.getString(NULLABLE);

                    map.put(TableInfo.COLUMN_NAME, name);
                    map.put(TYPE_NAME, type);
                    map.put(COLUMN_DEF, rs.getString(COLUMN_DEF));
                    assert type != null;
                    if (!type.toLowerCase().contains("date") || !type.toLowerCase().contains("time")) {
                        map.put(COLUMN_SIZE, rs.getString(COLUMN_SIZE));
                    }
                    map.put(ISNULL, isNull);
                    map.put(IS_PK, isPK(conn, tableName, rs.getString(TableInfo.COLUMN_NAME)) ? "1" : "0");
                    columnsList.add(map);
                }
            } catch (Exception e) {
                log.error("query table structure error," + e.getMessage(), e);
            }
        } catch (SQLException e) {
            log.error("数据库连接失败！", e);
        }
        return columnsList;
    }

    private static String getColumnType(String table, String col, Connection con) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT t.typname FROM pg_attribute a,pg_class c,pg_type t where a.attrelid=c.oid and t.oid=a.atttypid and c.relname=? and a.attname=?;";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, table);
            pstmt.setString(2, col);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    rs = null;
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    pstmt = null;
                }
            }
        }
        return null;
    }

    /**
     * 获取总条数
     *
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static int getCount(Connection conn, String sql) throws SQLException {
        int count;
        try {
            QueryRunner queryRunner = new QueryRunner();
            count = Integer.valueOf(queryRunner.query(conn, sql, new ScalarHandler(1)).toString());
        } catch (SQLException e) {
            log.error("Count data of db fail.{sql:" + sql + "}", e);
            throw e;
        }
        return count;
    }

    public static void main(String[] arg) throws SQLException {
        String driveName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://10.10.1.74:3307/jyjtest?useUnicode=true&characterEncoding=UTF-8";
        String userName = "root";
        String password = "123456";
        Connection connection = DSUtils.getConn(driveName, url, userName, password);
        String BLANK = " ";
        String tableName = "people";
        String key = "id";
        StringBuilder setKeyIncrementSql = new StringBuilder().append("ALTER TABLE").append(BLANK).append(tableName).append(BLANK).append("CHANGE").append(BLANK).append(key).append(BLANK).append(key).append(BLANK).append("int auto_increment");
        System.out.println(setKeyIncrementSql);
    }


    /**
     * execute sql to db (Create Table && Drop Table)
     *
     * @param conn connection of db
     * @param sql  sql to execute
     * @return execute success?
     * @author cai_cf@126.com
     */
    public static boolean executeUpdateSql(Connection conn, String sql) {
        boolean flag = true;

        try {
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            flag = false;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.error("executeUpdateSql rollBack error.", e1);
            }
            log.error("executeUpdateSql error.{ sql:{}", sql, e);
        }
        return flag;
    }

    /**
     * 查询数据
     *
     * @param conn
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> search(Connection conn, String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> result;
        QueryRunner runner = new QueryRunner();
        try {
            if (params != null && params.size() > 0) {
                Object[] objects = params.toArray();
                result = runner.query(conn, sql, new MapListHandler(), objects);
            } else {
                result = runner.query(conn, sql, new MapListHandler());
            }
        } catch (SQLException e) {
            log.error("数据查询失败！", e);
            throw e;
        }
        return result;
    }

    /**
     * 查所有数据   clob  和Blob需要单独处理
     */
    public static List<Map<String, Object>> getTableData(Connection conn, String sql) throws SQLException {
        List<Map<String, Object>> result;
        QueryRunner runner = new QueryRunner();
        try {
            result = runner.query(conn, sql, new MapListHandler());
        } catch (SQLException e) {
            log.error("getTableData error", e);
            throw e;
        }
        return result;
    }
}
