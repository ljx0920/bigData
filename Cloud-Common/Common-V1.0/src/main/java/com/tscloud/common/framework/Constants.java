
package com.tscloud.common.framework;

/**
 * EnvConstants.java
 *
 * @author daowan.hu
 */
public final class Constants {

    public final class Env {

        public static final String BASE_HOME = "configs/";

        public static final String WORKFLOW_HOME = "configs/workflow/";

        public static final String ECHARTS_HOME = "configs/echarts/";

        public static final String FUNCTION_HOME = "configs/function/";
    }

    public final class RestPathPrefix {
        /**
         * 系统基础服务路径
         */
        public static final String BASE = "base/";

        /**
         * 平台每个模块前缀
         */
        public static final String MODEL_CONSOLE = "console/";
        public static final String MODEL_DATAACCESS = "access/";
        public static final String MODEL_DATAFILLING = "datafilling/";
        public static final String MODEL_DATAMANANAGE = "datamanage/";
        public static final String MODEL_FILESTORAGE = "filestorage/";
        public static final String MODEL_JOBMANAGE = "job/";
        public static final String MODEL_RECEIVE = "revceive/";
        public static final String MODEL_DATA_ANALYSIS = "da/";
        public static final String MODEL_ETL = "etl/";
        public static final String MODEL_MONITORING = "monitoring/";
        public static final String MODEL_PLATFORMOPERATION = "op/";
        public static final String MODEL_DATASERVICE = "dataservice/";

        /**
         * 对外接口前缀
         */
        public static final String API_BASE = "apibase/";
    }

    /**
     * Restful 对外的静态变量
     */
    public final class JsonView {

        public static final String STATUS_SUCCESS = "success";

        public static final String STATUS_FAIL = "fail";
    }

    /**
     * 缓存名称
     */
    public final class Cache {
        /**
         * 系统参数
         */
        public static final String SYSTEM_PARAM = "system_param";
        public static final String SYSTEM_SESSION = "system_session";
        public static final String ETL_CACHE = "etl_workflow_cache";
        public static final String RE_CACHE = "re_workflow_cache";
        public static final String RE_CACHE_TOPIC = "re_workflow_cache_topic";
        public static final String LOGIN_INFO = "login_info";
        public static final String USER = "user";
        public static final String USER_INFO = "user_info";
        public static final String TOKENNAME = "token";
    }

    public final static class SystemParam {
        /**
         * 逗号分隔符
         */
        public static final String COMMA_SPLIT = ",";
        /**
         * 冒号分隔符
         */
        public static final String COLON_SPLIT = ":";
        /**
         * 空Json串
         */
        public static final String BLANK_JSON_STRING = "{}";
    }

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
     * mysql 的字段类型
     */
    public final static class OracleColumn {

        public final static String VARCHAR2 = "VARCHAR2";
        public final static String TIMESTAMP = "TIMESTAMP";
        public final static String ORACLE_CHAR = "CHAR";
        public final static String RAW = "RAW";
        public final static String NVARCHAR2 = "NVARCHAR2";
        public final static String TIMESTAMP_WITH_LOCAL_TIME_ZONE = "TIMESTAMP_WITH_LOCAL_TIME_ZONE";
        public final static String TIMESTAMP_WITH_TIME_ZONE = "TIMESTAMP_WITH_TIME_ZONE";

        public final static String INTERVAL_DAY_TO_SECOND = "INTERVAL_DAY_TO_SECOND";
        public final static String INTERVAL_YEAR_TO_MONTH = "INTERVAL_YEAR_TO_MONTH";
        public final static String NUMBER = "NUMBER";
    }

    /**
     * 数据表相关
     */
    public final static class TableInfo {

        public static final String COLUMN_NAME = "COLUMN_NAME";
        public static final String TYPE_NAME = "TYPE_NAME";
        public static final String COLUMN_SIZE = "COLUMN_SIZE";
        public static final String IS_PK = "isPk";
        public static final String ISNULL = "isNull";
        public static final String NULLABLE = "NULLABLE";
        public static final String COLUMN_DEF = "COLUMN_DEF";
        public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
    }

    /**
     * 数据库
     */
    public final static class DataBase {
        public static final String MYSQL = "mysql";
        public static final String ORACLE = "oracle";
    }
}
