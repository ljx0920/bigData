package com.iov.common.tool.dbtool;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.utils.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author daowan.hu
 */
public class DSUtils {

    private static final String COLUMN_NAME = "column_name";

    private static final String MYSQL = "mysql";
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

    /**
     * 查所有数据
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
            //ignore
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

    private static String getColumnSize(String dataBaseType, ResultSet rs) {
        String size = null;
        try {
            String type = rs.getString(Constants.TableInfo.TYPE_NAME);
            //判断数据库类型,来进行判断类型
            switch (dataBaseType) {
                case MYSQL:
                    switch (type) {
                        case Constants.MysqlColumn.YEAR:
                        case Constants.MysqlColumn.DATETIME:
                        case Constants.MysqlColumn.BINARY:
                        case Constants.MysqlColumn.VARBINARY:
                        case Constants.MysqlColumn.TINYINT:
                        case Constants.MysqlColumn.SMALLINT:
                        case Constants.MysqlColumn.MEDIUMINT:
                        case Constants.MysqlColumn.INT:
                        case Constants.MysqlColumn.BIGINT:
                        case Constants.MysqlColumn.BIT:
                        case Constants.MysqlColumn.CHAR:
                        case Constants.MysqlColumn.VARCHAR:
                            size = rs.getString(Constants.TableInfo.COLUMN_SIZE);
                            break;
                        case Constants.MysqlColumn.DECIMAL:
                        case Constants.MysqlColumn.DOUBLE:
                        case Constants.MysqlColumn.FLOAT:
                            size = rs.getString(Constants.TableInfo.COLUMN_SIZE) + "," + rs.getString(Constants.TableInfo.DECIMAL_DIGITS);
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

}
