package com.tscloud.common.monitor;

/**
 * 用来提供Agent的常用数据
 *
 * @author aihua.sun
 * @date 2015/4/7
 * @since V1.0
 */

public class AgentConstants {
    public static final String PART_KEY = "custom.partition.key";
    public static final String ENCODING = "custom.encoding";
    public static final String TOPIC_NAME = "custom.topic.name";
    public static final String SLEEP_INTERVAL = "custom.interval";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DEFAULT_TOPIC = "flume_monitor_sink";
    public static final String DEFAULT_SLEEP_TIME = "1000";
    //服务角色
    public static final String HDFS_PRIMARY_NAMENODE = "primary_namenode";
    public static final String HDFS_BACKUP_NAMENODE = "backup_namenode";
    public static final String HDFS_JOURNALNODE = "journalnode";
    public static final String HDFS_DATANODE = "datanode";
    public static final String HBASE_MASTER = "hmaster";
    public static final String HBASE_REGION = "regionserver";
    public static final String HADOOP = "hadoop";
    public static final String HBASE = "hbase";
    public static final String ZOOKEEPER = "zookeeper";
    public static final String FLUME_AGENT = "flume";
    public static final String KAFKA = "kafka";
    public static final String REDIS = "redis";
    public static final String HOST = "HOST";
    public static final String SERVICE_ROLE = "ROLE";
    public static final String DISKINFO = "DISKINFO";
    public static final String CPUINFO = "CPUINFO";
    public static final String MEMINFO = "MEMINFO";
    public static final String NETINFO = "NETINFO";
    public static final String NETSUMMARYINFO = "NETSUMMARYINFO";
    public static final String ZKFC = "zkfc";
    public static final String SPARK = "spark";
    public static final String SPARK_MASTER = "master";
    public static final String SPARK_SLAVER = "slaver";

    //监控指标
    public static final String VALUE_METRIC = "VALUE";
    public static final String PERCENT_METRIC = "PERCENT";
    public static final String UNIT_MB = "MB";
    public static final String UNIT_KB = "KB";
    public static final String UNIT_GB = "GB";
    public static final String UNIT_B = "B";
    public static final String UNIT_PERCENT = "%";
    public static final String UNIT_NULL = "";
    public static final String UNIT_SECOND = "Seconds";
    public static final String UNIT_MILL_SECOND = "milliseconds";
    public static final String METRIC_SEPRATE = ":";
    public static final String ELASTIC_SEARCH_CLUSTER = "ELASTIC_SEARCH_CLUSTER";
    public static final String ELASTIC_SEARCH_NODE = "ELASTIC_SEARCH_NODE";
    public static final String ELASTIC_SEARCH = "es";
    public static final String TAB = "\t";
    public static final String BREAK_LINE = "\n";
    public static final String SPACE = " ";
    public static final String HDFS_SERVICE_URL = "HDFS_URL";
    //优良
    public static final String RUNTIME_STATUS_EXCELLENT = "EXCELLENT";
    public static final String STATUS_EXCELLENT_NUM = "3";
    //存在隐患
    public static final String RUNTIME_STATUS_TROUBLE = "TROUBLE";
    public static final String STATUS_TROUBLE_NUM = "1";
    //良好
    public static final String RUNTIME_STATUS_FINE = "FINE";
    public static final String STATUS_FINE_NUM = "2";
    //良好
    public static final String RUNTIME_STATUS_UNDEFINED = "UNDEFINED";
    public static final String STATUS_UNDEFINED_NUM = "4";

    //服务运行
    public static final String SERVICE_STATUS_ACTIVE = "ACTIVE";
    public static final String SERVICE_STATUS_ACTIVE_V = "1";

    //服务停止
    public static final String SERVICE_STATUS_STOP = "STOP";
    public static final String SERVICE_STATUS_STOP_V = "2";
    //MONITOR_RUNTIME_SUMMARY表中区分状态类型 0表示服务运行状态 1表示服务运行状况
    public static final String SUMMARY_TYPE_STATUS = "0";
    public static final String SUMMARY_TYPE_RUNINFO = "1";


    //服务异常
    public static final String SERVICE_EXCEPTION = "Service role is unavailable!";

    //消息类型
    public static final String ROLE_RUNTIME_INFO = "#ROLE_RUNTIMEINFO";
    public static final String LOG_IDENFITY = "#ROLELOG";

    public static final String ROLE_SERVICE_URL_PARAMETER = "consoleManagerUrl";

    public static final String FLUME_LOG_UPLOAD = "flumeLogUpload";

    public static final String ZOOKEEPER_SERVICE_IP = "zookeeperServiceIp";
//  系统字典服务配置
    public static final String SYSCONFIG_ANSIBLE = "ansible";
    public static final String SYSCONFIG_FTP = "ftp";

    //  集群服务配置
    public static final String[] HBASE_DATA_DIRS = {"hbase_tmp_dir"};

    public static final String[] HADOOP_DATA_DIRS = {"dfs_journalnode_edits_dir", "dfs_namenode_checkpoint_edits_dir",
            "dfs_namenode_name_dir", "dfs_namenode_edits_dir", "dfs_datanode_data_dir", "dfs_namenode_checkpoint_dir"};

    public static final String[] ZOOKEEPER_DATA_DIRS = {"zookeeper_data_dir"};
//    sleep延迟time
    public static final long SHEEP_TIME = 5000;
    public static final int ZOOKEEPER_SESSION_TIMEOUT = 5000;
    public static final int ZOOKEEPER_CONNECT_TIMEOUT = 5000;

    public static final String INTER_SYS_CONSOLE_MANAGER ="consolemanager";
    public static final String INTER_SYS_CLOUD_UI ="cloudui";
    public static final String INTER_SYS_CONSOLE_MONITOR ="consolemonitor";
    public static final String INTER_SYS_CONSOLE_SYSTEM ="consolesystem";
    public static final String INTER_SYS_DATA_GATHER ="datagather";
    public static final String INTER_SYS_ESB ="esb";
    public static final String INTER_SYS_ETL ="etl";
    public static final String INTER_SYS_WORKFLOW_MASTER ="workflowmaster";
    public static final String INTER_SYS_WORKFLOW_SLAVE ="workflowslave";
    public static final String INTER_SYS_DATABASE_MANAGER="databasemanager";
    public static final String INTER_SYS_FILE_GATHER="filegather";
    public static final String INTER_SYS_MANAGE_SERVER="manageserver";
    public static final String LOG_COLLECT="#LOG_COLLECT";
    public static final String SERVICE_LOG_COLLECT="#SERVICE_LOG_COLLECT";
}
