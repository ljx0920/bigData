package com.iov.common.tool.impala;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iov.common.tool.dbtool.DSPool;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * impala客户端工具
 *
 * @author lei.yang1
 * @date 2017/4/20
 */
public class ImpalaClient {

    private DruidDataSource dataSource;

    private String database;
    private static final String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";

    private DruidDataSource getDataSource() {
        return this.dataSource;
    }

    protected void setDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected String getDatabase() {
        return database;
    }

    protected void setDatabase(String database) {
        this.database = database;
    }

    /**
     * impala客户端构造方法
     *
     * @param host     impala所在的主机名或者IP
     * @param port     impala的监听端口
     * @param database impala的数据库
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ImpalaClient(String host, int port, String database) throws ClassNotFoundException, SQLException {
        String url = "jdbc:impala://" + host + ":" + port + "/" + database + ";auth=noSasl";
        setDatabase(database);
        setDataSource(DSPool.createDataSource(JDBC_DRIVER, url, null, null));
    }

    public ImpalaClient(String host, int port, String database, String principal) throws ClassNotFoundException, SQLException {
        String url = "jdbc:impala://" + host + ":" + port + "/" + database + ";principal=" + principal;
        setDatabase(database);
        setDataSource(DSPool.createDataSource(JDBC_DRIVER, url, null, null));
    }

    public ImpalaClient(String host, int port, String database, String user, String pwd) throws ClassNotFoundException, SQLException {
        String url = "jdbc:impala://" + host + ":" + port + "/" + database + ";user=" + user + ";password=" + pwd;
        setDatabase(database);
        setDataSource(DSPool.createDataSource(JDBC_DRIVER, url, null, null));
    }

    /**
     * 关闭客户端
     */
    public void close() {
        dataSource.close();
    }

    public void createDatabase(String database) throws SQLException {
        executeUpdateSql("create database " + database);
    }

    public void deleteDatabase(String database) throws SQLException {
        executeUpdateSql("drop database " + database);
    }

    public boolean containDatabase(String database) throws SQLException {
        boolean flag = false;
        Connection connection = this.getDataSource().getConnection();
        ResultSet rs = connection.getMetaData().getSchemas();
        while (rs.next()) {
            if (database.equals(rs.getString(1))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 执行更新sql
     *
     * @param sql sql语句
     * @return
     * @throws SQLException
     */
    public boolean executeUpdateSql(String sql) throws SQLException {
        try (Connection connection = this.getDataSource().getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                return stmt.executeUpdate(sql) >= 0;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean executeUpdateSql(String sql, List<Object> param) throws SQLException {
        try (Connection connection = this.getDataSource().getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (int i = 1; i <= param.size(); i++) {
                    stmt.setObject(i, param.get(i - 1));
                }
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public boolean batchUpdateSql(String sql, List<List<Object>> params) throws SQLException {
        try (Connection connection = this.getDataSource().getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (List<Object> param : params) {
                    for (int i = 1; i <= param.size(); i++) {
                        stmt.setObject(i, param.get(i - 1));
                    }
                }
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public void load(String path, String table, boolean override, Map<String, Object> partitions) throws SQLException {
        addPartition(table, partitions);
        load(false, path, table, override, partitions);
    }

    public void load(boolean isLocal, String path, String table, boolean override, Map<String, Object> partitions) throws SQLException {
        StringBuffer sb = new StringBuffer("load data ");
        sb.append("inpath ").append("'").append(path).append("' ");
        appendLocal(isLocal);
        sb.append("into table ").append(table);
        if (partitions != null && partitions.size() > 0) {
            appendPartitions(sb, partitions);
        }
        this.executeUpdateSql(sb.toString());
    }

    private void appendPartitions(StringBuffer sb, Map<String, Object> partitions) {
        sb.append(" partition ( ");
        Iterator<Map.Entry<String, Object>> iterator = partitions.entrySet().iterator();
        boolean hasPartitions = false;
        while (iterator.hasNext()) {
            if (hasPartitions) {
                sb.append(",");
            }
            Map.Entry<String, Object> entry = iterator.next();
            sb.append(entry.getKey()).append("=");
            if (entry.getValue() instanceof String) {
                sb.append("'").append(entry.getValue()).append("'");
            }
            hasPartitions = true;
        }
        sb.append(")");
    }

    private void addPartition(String table, Map<String, Object> partitions) throws SQLException {
        StringBuffer sb = new StringBuffer("ALTER TABLE ");
        sb.append(table).append(" ADD IF NOT EXISTS");
        appendPartitions(sb, partitions);
        executeUpdateSql(sb.toString());
    }

    private void appendLocal(boolean isLocal) {
        if (isLocal) {
            throw new RuntimeException("impala canot load local file.");
        }
    }

    /**
     * 判断表是否存在
     *
     * @param tableName
     * @return 存在则返回true，否则返回false
     * @throws SQLException
     */
    public boolean existTable(String tableName) throws SQLException {
        List<String> tables = getAllTableName();
        return tables.contains(tableName);
    }

    /**
     * 获取所有表名的列表
     *
     * @return
     * @throws SQLException
     */
    public List<String> getAllTableName() throws SQLException {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        try (Connection connection = this.getDataSource().getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            try (ResultSet rsTables = meta.getTables(null, database, "%", null)) {
                while (rsTables.next()) {
                    builder.add(rsTables.getString("TABLE_NAME"));
                }
            }
        }
        return builder.build();
    }

    /**
     * 获取一个表的所有列信息
     *
     * @param tableName 表名
     * @return 列信息，格式为
     * [{
     * "COLUMN_NAME":列名,
     * "TYPE_NAME":列的类型
     * },{
     * "COLUMN_NAME":列名,
     * "TYPE_NAME":列的类型
     * },]
     * @throws SQLException
     */
    public List<Map<String, String>> getTableColumns(String tableName) throws SQLException {
        ImmutableList.Builder<Map<String, String>> columnsList = ImmutableList.builder();
        if (StringUtils.isBlank(tableName)) {
            throw new NullPointerException("the table null is null.");
        }
        try (Connection conn = this.getDataSource().getConnection()) {
            DatabaseMetaData dmd = conn.getMetaData();
            if (dmd == null) {
                return columnsList.build();
            }
            try (ResultSet rs = dmd.getColumns(null, null, tableName, "%")) {
                while (rs.next()) {
                    Map<String, String> map = Maps.newHashMap();
                    String type = rs.getString("TYPE_NAME");

                    map.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
                    map.put("TYPE_NAME", type);
                    columnsList.add(map);
                }
            }
        }
        return columnsList.build();
    }

    /**
     * 执行count类型的sql
     *
     * @param sql SQL语句
     * @return
     * @throws SQLException
     */
    public Integer count(String sql) throws SQLException {
        int count = 0;
        try (Connection conn = this.getDataSource().getConnection();
             Statement smt = conn.createStatement();
             ResultSet rs = smt.executeQuery(sql)) {

            while (rs.next()) {
                count = (int) rs.getLong(1);
                break;
            }
        }
        return count;
    }

    public String addFunction(String functionName, String jarPath, String classPath, String returnType, String argTypes) throws SQLException {
        StringBuilder sb = new StringBuilder().append("CREATE FUNCTION ").append(functionName).append("(");
        String[] argTypeList = argTypes.split(",");
        for (String argType : argTypeList) {
            sb.append(argType.trim()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 2).deleteCharAt(sb.length() - 1).append(")");
        sb.append(" RETURNS ").append(returnType);
        sb.append(" LOCATION '").append(jarPath).append("'");
        sb.append(" SYMBOL='").append(classPath).append("'");
        executeUpdateSql(sb.toString());
        return sb.toString();
    }

    public void deleteFunction(String functionName, String argTypes) throws SQLException {
        StringBuilder sb = new StringBuilder().append("DROP FUNCTION ").append(functionName).append("(");
        String[] argTypeList = argTypes.split(",");
        for (String argType : argTypeList) {
            sb.append(argType.trim()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 2).deleteCharAt(sb.length() - 1).append(")");
        executeUpdateSql(sb.toString());
    }

    /**
     * 执行插入语句
     *
     * @param sql  插入SQL语句
     * @param vals 参数
     * @return
     * @throws SQLException
     */
    public boolean insertData(String sql, Object[] vals) throws SQLException {
        boolean flag = false;
        try (Connection conn = this.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            for (int i = 1; i <= vals.length; i++) {
                ps.setObject(i, vals[i - 1]);
            }
            ps.executeUpdate();
            flag = true;
        }
        return flag;
    }

    /**
     * 执行查询语句
     *
     * @param sql    查询SQL
     * @param values 参数
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> searchData(String sql, Object[] values) throws SQLException {
        ImmutableList.Builder<Map<String, Object>> builder = ImmutableList.builder();
        try (Connection conn = this.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (values != null) {
                for (int i = 1; i <= values.length; i++) {
                    ps.setObject(i, values[i - 1]);
                }
            }
            ResultSet rs = ps.executeQuery();
            //获取所有列名
            ResultSetMetaData rsmd = rs.getMetaData();
            ImmutableList.Builder<String> columns = ImmutableList.builder();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                String columnName = rsmd.getColumnName(i);
                if (columnName.endsWith(")")) {
                    columns.add(columnName);
                } else {
                    String[] cn = rsmd.getColumnName(i).split("\\.", 2);
                    columns.add(cn.length == 2 ? cn[1] : cn[0]);
                }
            }
            //取得所有数据放入Map
            while (rs.next()) {
                builder.add(toMap(columns.build(), rs));
            }
        }
        return builder.build();
    }

    private Map<String, Object> toMap(List<String> columns, ResultSet rs) throws SQLException {
        Map<String, Object> map = Maps.newLinkedHashMap();
        for (String column : columns) {
            map.put(column, rs.getObject(column));
        }
        return map;
    }

    /**
     * 执行查询SLQ
     *
     * @param sql 查询SQL
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> searchData(String sql) throws SQLException {
        ImmutableList.Builder<Map<String, Object>> builder = ImmutableList.builder();
        try (Connection conn = this.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            //获取所有列名
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> columns = Lists.newLinkedList();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                String columnName = rsmd.getColumnName(i);
                if (columnName.endsWith(")")) {
                    columns.add(columnName);
                } else {
                    String[] cn = rsmd.getColumnName(i).split("\\.", 2);
                    columns.add(cn.length == 2 ? cn[1] : cn[0]);
                }
            }
            //取得所有数据放入Map
            while (rs.next()) {
                builder.add(toMap(columns, rs));
            }
        }
        return builder.build();
    }

    /**
     * 执行查询SLQ
     *
     * @param sql 查询SQL
     * @return
     * @throws SQLException
     */
    public List<List<Object>> searchDataToList(String sql) throws SQLException {
        ImmutableList.Builder<List<Object>> builder = ImmutableList.builder();
        try (Connection conn = this.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            //获取所有列名
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> columns = Lists.newLinkedList();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                String columnName = rsmd.getColumnName(i);
                if (columnName.endsWith(")")) {
                    columns.add(columnName);
                } else {
                    String[] cn = rsmd.getColumnName(i).split("\\.", 2);
                    columns.add(cn.length == 2 ? cn[1] : cn[0]);
                }
            }
            //取得所有数据放入Map
            while (rs.next()) {
                toList(builder, rs, columns);
            }
        }
        return builder.build();
    }

    private void toList(ImmutableList.Builder<List<Object>> builder, ResultSet rs, List<String> columns) throws SQLException {
        List<Object> rsRow = Lists.newArrayList();
        for (int i = 0; i < columns.size(); i++) {
            rsRow.add(rs.getObject(i + 1));
        }
        builder.add(rsRow);
    }
}
