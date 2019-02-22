package com.tscloud.common.utils;

/**
 * @author wss
 * @date 15-5-28
 */
public class PageUtil {
    /**
     * 根据数据库类型，生成特定的分页sql
     *
     * @param sql
     * @param dialect
     * @param pageNum
     * @param perPage
     * @return
     */
    public static String buildPageSql(String sql, String dialect, int pageNum, int perPage) {
        StringBuilder pageSql = new StringBuilder();
        if ("mysql".equalsIgnoreCase(dialect)) {
            pageSql = buildPageSqlForMysql(sql, pageNum, perPage);
        } else if ("oracle".equalsIgnoreCase(dialect)) {
            pageSql = buildPageSqlForOracle(sql, pageNum, perPage);
        } else {
            return sql;
        }
        return pageSql.toString();
    }

    /**
     * mysql的分页语句
     *
     * @param sql
     * @param pageNum
     * @param perPage
     * @return
     */
    private static StringBuilder buildPageSqlForMysql(String sql, int pageNum, int perPage) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginRow = String.valueOf((pageNum - 1) * perPage);
        pageSql.append(sql);
        pageSql.append(" limit ").append(beginRow).append(",").append(perPage);
        return pageSql;
    }

    /**
     * oracle的分页语句
     *
     * @param sql
     * @param pageNum
     * @param perPage
     * @return
     */
    private static StringBuilder buildPageSqlForOracle(String sql, int pageNum, int perPage) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginRow = String.valueOf((pageNum - 1) * perPage);
        String endRow = String.valueOf(pageNum * perPage);
        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endRow);
        pageSql.append(") where row_id > ").append(beginRow);
        return pageSql;
    }
}
