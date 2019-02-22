package com.iov.common.utils;

/**
 *
 * @author jicheng.cui
 * @date 2018/7/2
 */
public class Constants {

    /**
     * mysql 的字段类型
     */
    public final static class MysqlColumn {

        public final static String YEAR = "YEAR";
        public final static String DATETIME = "DATETIME";
        public final static String BINARY = "BINARY";
        public final static String VARBINARY = "VARBINARY";
        public final static String TINYINT = "TINYINT";
        public final static String SMALLINT = "SMALLINT";
        public final static String MEDIUMINT = "MEDIUMINT";
        public final static String INT = "INT";
        public final static String BIGINT = "BIGINT";
        public final static String BIT = "BIT";
        public final static String CHAR = "CHAR";
        public final static String VARCHAR = "VARCHAR";

        public final static String DECIMAL = "DECIMAL";
        public final static String DOUBLE = "DOUBLE";
        public final static String FLOAT = "FLOAT";
    }

    /**
     * 数据表相关
     */
    public final static class TableInfo {

        public static final String IS_PK = "isPk";
        public static final String IS_NULL = "isNull";
        public static final String COLUMN_DEF = "COLUMN_DEF";
        public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
        public static final String COLUMN_NAME = "COLUMN_NAME";
        public static final String TYPE_NAME = "TYPE_NAME";
        public static final String DATA_TYPE = "DATA_TYPE";
        public static final String COLUMN_SIZE = "COLUMN_SIZE";
        public static final String NULLABLE = "NULLABLE";
        public static final String DATA_DEFAULT = "DATA_DEFAULT";
        public static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";
        public static final String TYPE = "type";
        public static final String COLUMN_TYPE = "COLUMN_TYPE";
        public static final String IS_NULLABLE = "IS_NULLABLE";
        public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
        public static final String COLUMN_KEY = "COLUMN_KEY";

        public final static String LEFT_BRACKET = "(";
        public final static String RIGHT_BRACKET = ")";
    }
}
