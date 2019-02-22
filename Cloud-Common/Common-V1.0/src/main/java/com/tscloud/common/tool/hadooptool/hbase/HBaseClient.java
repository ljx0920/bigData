package com.tscloud.common.tool.hadooptool.hbase;

import com.tscloud.common.tool.hadooptool.hbase.exception.NamespaceExistException;
import com.tscloud.common.tool.hadooptool.hbase.exception.TableExistException;
import com.tscloud.common.tool.hadooptool.hbase.exception.TableNotFoundException;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lei.yang1 on 2017/4/17.
 */
public class HBaseClient {

    private Configuration config;
//    private Connection connection;
    private String namespace;

    private static final String COLUMN_FAMILY = "COLUMN_FAMILY";
    private static final String COLUMN = "COLUMN";
    private static final String VALUE = "VALUE";
    private static final String TIMESTAMP = "TIMESTAMP";

    public static final String TIME_START = "start_time";
    public static final String TIME_END = "end_time";
    public static final String VERSIONS = "version_number";

    public static final String FAMILYS = "familys";
    public static final String COLUMNS = "columns";
    /**
     * 初始化HBase客户端
     * @throws IOException
     */
    public HBaseClient( String zookeepers ) throws IOException {
//        config = HBaseConfiguration.create();
//        config.set( "hbase.zookeeper.quorum", zookeepers );
//        connection = ConnectionFactory.createConnection( config );
    }

    /**
     * 初始化Hbase客户端
     * @param resource
     * @throws IOException
     */
    public HBaseClient( String zookeepers, String resource, String namespace ) throws IOException{
//        config = HBaseConfiguration.create();
//        if ( resource != null ){
//            config.addResource( new File( resource ).toURI().toURL() );
//        }
//        config.set( "hbase.zookeeper.quorum", zookeepers );
//        connection = ConnectionFactory.createConnection( config );
//        this.namespace = namespace;
    }

    /**
     * 初始化Hbase客户端
     * @param resource
     * @throws IOException
     */
    public HBaseClient( String zookeepers, String resource ) throws IOException{
//        config = HBaseConfiguration.create();
//        if ( resource != null ){
//            config.addResource( new File( resource ).toURI().toURL() );
//        }
//        config.set( "hbase.zookeeper.quorum", zookeepers );
//        connection = ConnectionFactory.createConnection( config );
    }

    /**
     * 关闭连接池
     * @throws IOException
     */
    public void close() throws IOException {
       // connection.close();
    }

//    private Connection getConnection() {
//        return connection;
//    }

    /**
     * 命名空间是否存在
     * @param namespace 命名空间,类似于关系型数据库中库的概念
     * @return          如果存在则返回true，否则返回false
     * @throws IOException
     */
    public boolean namespaceExists( String namespace ) throws IOException {
//        try ( Admin admin = getConnection().getAdmin() ) {
//            try{
//                admin.getNamespaceDescriptor( namespace );
//            } catch ( Exception e ){
//                if ( e instanceof  NamespaceNotFoundException ){
//                    return false;
//                }
//            }
//            return true;
//        }
        return true;
    }

    /**
     * 创建命名空间
     * @param namespace     命名空间名
     * @throws IOException
     * @throws NamespaceExistException
     */
    public void createNamespace( String namespace ) throws IOException, NamespaceExistException {
//        try ( Admin admin = getConnection().getAdmin() ) {
//            if (  namespaceExists( namespace ) ) {
//                throw new NamespaceExistException( "namespace (" + namespace + ") exists." );
//            }
//            admin.createNamespace( NamespaceDescriptor.create( namespace ).build() );
//        }
    }

    /**
     * 删除命名空间
     * @param namespace  命名空间名
     * @throws IOException
     */
    public void deleteNamespace( String namespace ) throws IOException {
//        try ( Admin admin = getConnection().getAdmin() ) {
//            admin.deleteNamespace( namespace );
//        }
    }

    /**
     * 查询所有的命名空间列表
     * @return
     * @throws IOException
     */
    public List<String> listNamespace() throws IOException {
//        try ( Admin admin = getConnection().getAdmin() ) {
//            NamespaceDescriptor[] nsds = admin.listNamespaceDescriptors();
//            ImmutableList.Builder<String> nsBuilder = ImmutableList.builder();
//            for ( NamespaceDescriptor nsd : nsds ){
//                nsBuilder.add( nsd.getName() );
//            }
//            return nsBuilder.build();
//        }
        return null;
    }

    /**
     * 查询表是否在指定的命名空间中存在
     * @param tableName     表名
     * @return              存在则返回true，否则返回false
     * @throws IOException
     */
    public boolean tableExists( String tableName ) throws IOException {
//        try ( Admin admin = getConnection().getAdmin() ) {
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            return admin.tableExists( TableName.valueOf( tableName ) );
//        }
        return true;
    }

    /**
     * 查询表是否在指定的命名空间中存在
     * @param namespace     命名空间，如果在默认的库中查询则为null
     * @param tableName     表名
     * @return              存在则返回true，否则返回false
     * @throws IOException
     */
    public boolean tableExists( String namespace, String tableName ) throws IOException {
//        try ( Admin admin = getConnection().getAdmin() ) {
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            return admin.tableExists( TableName.valueOf( tableName ) );
//        }
        return true;
    }

    /**
     * 在默认命名空间下创建表
     * @param tableName 表名
     * @param fcs       列族名列表
     * @throws IOException
     */
    public void createTable( String tableName, List<String> fcs ) throws IOException, TableExistException {
        createTable( namespace, tableName, fcs );
    }

    /**
     * 在指定命名空间下创建表
     * @param namespace 命名空间
     * @param tableName 表名
     * @param fcs       列族名列表
     * @throws IOException
     */
    public void createTable( String namespace, String tableName, List<String> fcs ) throws IOException, TableExistException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( admin.tableExists( tn ) ){
//                throw new TableExistException( "table (" + tableName + ") exists." );
//            }
//            HTableDescriptor tableDescriptor = new HTableDescriptor( tn );
//            for ( String fc : fcs ) {
//                tableDescriptor.addFamily( new HColumnDescriptor( fc ) );
//            }
//            admin.createTable( tableDescriptor );
//        }

    }

    /**
     * 在指定命名空间下创建表，并且按照指定规则进行region的split
     * @param namespace 命名空间
     * @param tableName 表名
     * @param fcs       列族名列表
     * @param startKey  开始key
     * @param endKey    结束key
     * @param numRegions    region数量
     * @throws IOException
     * @throws TableExistException
     */
    public void createTable( String namespace, String tableName, List<String> fcs,
                             byte[] startKey,byte[] endKey, int numRegions ) throws IOException, TableExistException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( admin.tableExists( tn ) ){
//                throw new TableExistException( "table (" + tableName + ") exists." );
//            }
//            HTableDescriptor tableDescriptor = new HTableDescriptor( tn );
//            tableDescriptor.setRegionSplitPolicyClassName( "org.apache.hadoop.hbase.regionserver.ConstantSizeRegionSplitPolicy" );
//            tableDescriptor.setCompactionEnabled( false );
//            tableDescriptor.setMaxFileSize( 1024 * 1024 * 1024 * 100 );
//            for ( String fc : fcs ) {
//                tableDescriptor.addFamily( new HColumnDescriptor( fc ) );
//            }
//            admin.createTable( tableDescriptor, startKey, endKey, numRegions );
//        }
    }

    /**
     * 在指定命名空间下创建表，并且按照指定规则进行region的split
     * @param namespace 命名空间
     * @param tableName 表名
     * @param fcs       列族名列表
     * @param splitKeys  key的列表
     * @throws IOException
     * @throws TableExistException
     */
    public void createTable( String namespace, String tableName, List<String> fcs,
                             byte[][] splitKeys ) throws IOException, TableExistException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( admin.tableExists( tn ) ){
//                throw new TableExistException( "table (" + tableName + ") exists." );
//            }
//            HTableDescriptor tableDescriptor = new HTableDescriptor( tn );
//            tableDescriptor.setRegionSplitPolicyClassName( "org.apache.hadoop.hbase.regionserver.ConstantSizeRegionSplitPolicy" );
//            tableDescriptor.setCompactionEnabled( false );
//            tableDescriptor.setMaxFileSize( 1024 * 1024 * 1024 * 100 );
//            for ( String fc : fcs ) {
//                tableDescriptor.addFamily( new HColumnDescriptor( fc ) );
//            }
//            admin.createTable( tableDescriptor, splitKeys );
//        }
    }

    /**
     * 在指定命名空间下创建表，并且按照指定规则进行region的split
     * @param namespace 命名空间
     * @param tableName 表名
     * @param fcs       列族名列表
     * @param clazz     org.apache.hadoop.hbase.regionserver.ConstantSizeRegionSplitPolicy
     *                   org.apache.hadoop.hbase.regionserver.DisabledRegionSplitPolicy
     *                   org.apache.hadoop.hbase.regionserver.DelimitedKeyPrefixRegionSplitPolicy
     *                   org.apache.hadoop.hbase.regionserver.KeyPrefixRegionSplitPolicy
     * @throws IOException
     * @throws TableExistException
     */
    public void createTable( String namespace, String tableName, List<String> fcs,
                             String clazz ) throws IOException, TableExistException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( admin.tableExists( tn ) ){
//                throw new TableExistException( "table (" + tableName + ") exists." );
//            }
//            HTableDescriptor tableDescriptor = new HTableDescriptor( tn );
//            tableDescriptor.setRegionSplitPolicyClassName( clazz );
//            for ( String fc : fcs ) {
//                tableDescriptor.addFamily( new HColumnDescriptor( fc ) );
//            }
//            admin.createTable( tableDescriptor );
//        }
    }

    /**
     * 在指定命名空间下按照配置创建表
     * @param namespace 命名空间
     * @param tableName 表名
     * @param fcs       列族名列表
     * @param configs   表的配置，包含split规则、compact规则以及其他的配置
     * @throws IOException
     * @throws TableExistException
     */
    public void createTable( String namespace, String tableName, List<String> fcs,
                             Map<String,String> configs ) throws IOException, TableExistException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( admin.tableExists( tn ) ){
//                throw new TableExistException( "table (" + tableName + ") exists." );
//            }
//            HTableDescriptor tableDescriptor = new HTableDescriptor( tn );
//            Iterator<String> iterator = configs.keySet().iterator();
//            while ( iterator.hasNext() ){
//                String key = iterator.next();
//                tableDescriptor.setValue( key, configs.get( key ) );
//            }
//            for ( String fc : fcs ) {
//                tableDescriptor.addFamily( new HColumnDescriptor( fc ) );
//            }
//            admin.createTable( tableDescriptor );
//        }
    }
    /**
     * 修改表结构
     * @param namespace 命名空间
     * @param tableName 表名
     * @param cfs       列族列表
     * @throws IOException
     * @throws TableNotFoundException
     */
    public void modifyTable( String namespace, String tableName, List<String> cfs ) throws IOException, TableNotFoundException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( !admin.tableExists( tn ) ){
//                throw new TableNotFoundException("table (" + tableName +") not found." );
//            }
//            HTableDescriptor tableDescriptor = admin.getTableDescriptor( tn );
//            HColumnDescriptor[] cds = tableDescriptor.getColumnFamilies();
//            Map<String,List<String>> map = compare( cds, cfs );
//            try {
//                admin.disableTable( tn );
//                List<String> delCols = map.get( "delete" );
//                if ( delCols != null && delCols.size() > 0 ) {
//                    for (String cf : delCols) {
//                        admin.deleteColumn( tn, cf.getBytes( "UTF-8" ) );
//                    }
//                }
//                List<String> addCols = map.get( "add" );
//                if ( addCols != null && addCols.size() > 0 ) {
//                    for (String cf : addCols) {
//                        admin.addColumn( tn, new HColumnDescriptor( cf ) );
//                    }
//                }
//            } finally {
//                admin.enableTable( tn );
//            }
//        }
    }

    public void modifyTable( String tableName, List<String> cfs ) throws IOException, TableNotFoundException {
        modifyTable( namespace, tableName, cfs );
    }

    /**
     * 增加列族
     * @param namespace
     * @param tableName
     * @param cfs
     * @throws IOException
     * @throws TableNotFoundException
     */
    public void addColumnFamily( String namespace, String tableName, List<String> cfs ) throws IOException, TableNotFoundException {
//        try( Admin admin = getConnection().getAdmin() ){
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            if ( !admin.tableExists( tn ) ){
//                throw new TableNotFoundException("table (" + tableName +") not found." );
//            }
//            HTableDescriptor tableDescriptor = admin.getTableDescriptor( tn );
//            try {
//                admin.disableTable( tn );
//                cfs.forEach( (String cf) ->{
//                    try {
//                        admin.addColumn( tn, new HColumnDescriptor( cf ) );
//                    } catch (IOException e) {
//                        throw new RuntimeException( e );
//                    }
//                });
//            } finally {
//                admin.enableTable( tn );
//            }
//        }
    }

    /**
     * 比较已存在的列族和新增的列族
     * @param cds    已存在的列族
     * @param cfs    修改后的列族
     * @return
//     */
//    private Map<String,List<String>> compare( HColumnDescriptor[] cds, List<String> cfs ){
//        ImmutableMap.Builder<String,List<String>> mapBuilder = ImmutableMap.builder();
//        if ( cds == null || cds.length == 0 ){
//            mapBuilder.put( "add", cfs );
//            return mapBuilder.build();
//        }
//        ImmutableList.Builder<String> delete = ImmutableList.builder();
//        if ( cfs == null || cfs.size() == 0 ) {
//            for ( HColumnDescriptor cd : cds ) {
//                delete.add( cd.getNameAsString() );
//            }
//            mapBuilder.put( "delete", delete.build() );
//            return mapBuilder.build();
//        }
//        ImmutableList.Builder<String> update = ImmutableList.builder();
//        for ( HColumnDescriptor cd : cds ){
//            boolean exist = false;
//            for ( String fc : cfs ){
//                if ( cd.getNameAsString().equals( fc ) ){
//                    exist = true;
//                    break;
//                }
//            }
//            if ( exist ){
//                update.add( cd.getNameAsString() );
//            } else {
//                delete.add( cd.getNameAsString() );
//            }
//        }
//        List<String> upList = update.build();
//        List<String> add = Lists.newLinkedList();
//        add.addAll( cfs );
//        add.removeAll( upList );
//        mapBuilder.put( "delete", delete.build() );
//        mapBuilder.put( "add", add );
//        mapBuilder.put( "update", upList );
//        return mapBuilder.build();
//    }

    /**
     * 删除表
     * @param namespace 命名空间
     * @param tableName 表名
     * @throws IOException
     */
    public void deleteTable( String namespace, String tableName ) throws IOException {
//        try ( Admin admin = this.getConnection().getAdmin() ) {
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            admin.disableTable( tn );
//            admin.deleteTable( tn );
//        }
    }

    public void deleteTable( String tableName ) throws IOException {
        this.deleteTable( namespace, tableName );
    }

    /**
     * 获取所有表
     * @return              所有表名
     * @throws IOException
     */
    public List<String> listTables( ) throws IOException {
        return listTables( namespace );
    }

    /**
     * 获取所有表
     * @param namespace     命名空间
     * @return              所有表名
     * @throws IOException
     */
    public List<String> listTables( String namespace ) throws IOException {
//        try ( Admin admin = this.getConnection().getAdmin() ){
//            ImmutableList.Builder<String> builder = ImmutableList.builder();
//            HTableDescriptor[] tds = admin.listTables( namespace+":.*" );
//            for ( HTableDescriptor td : tds ) {
//                String table = td.getNameAsString();
//                String[] tn = table.split(":", 2 );
//                builder.add( tn.length == 2 ? tn[1] : tn[0] );
//            }
//            return builder.build();
//        }
        return  null;
    }

    /**
     * 获取所有表，包含列信息
     * @param namespace     命名空间
     * @return              所有表以及表的列信息
     * @throws IOException
     */
    public Map<String,Map<String,Object>> listTablesWithColumns( String namespace ) throws IOException {
//        try ( Admin admin = this.getConnection().getAdmin() ){
//            ImmutableMap.Builder<String,Map<String,Object>> builder = ImmutableMap.builder();
//            HTableDescriptor[] tds = admin.listTables( namespace+":.*" );
//            for ( HTableDescriptor td : tds ) {
//                String[] tableName = td.getNameAsString().split( ":", 2 );
//                builder.put( tableName.length == 2 ? tableName[1] : tableName[0], getColumns( td ) );
//            }
//            return builder.build();
//        }
        return  null;
    }

    public Map<String,Map<String,Object>> listTablesWithColumns( ) throws IOException {
        return listTablesWithColumns( namespace );
    }

    /**
     * 获取表详情
     * @param namespace 命名空间
     * @param tableName 表名
     * @return          key为列名，value为列族的配置信息（map格式）
     *                   value的内容如：
     *                   {NAME => 'cf', BLOOMFILTER => 'ROW', VERSIONS => '1', IN_MEMORY => 'false',
     *                   KEEP_DELETED_CELLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER',
     *                   COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCACHE => 'true',
     *                   BLOCKSIZE => '65536', REPLICATION_SCOPE => '0'}
     * @throws IOException
     */
    public Map<String,Object> getTable( String namespace, String tableName ) throws IOException {
//        try ( Admin admin = this.getConnection().getAdmin() ){
//
//            if ( namespace != null && namespace.length() > 0 ){
//                tableName = namespace + ":" + tableName;
//            }
//            TableName tn = TableName.valueOf( tableName );
//            HTableDescriptor td = admin.getTableDescriptor( tn );
//            return getColumns( td );
//        }
        return  null;
    }

    public Map<String,Object> getTable( String tableName ) throws IOException {
        return getTable( namespace, tableName );
    }

//    private Map<String,Object> getColumns( HTableDescriptor td ){
//        ImmutableMap.Builder<String,Object> builder = ImmutableMap.builder();
//        HColumnDescriptor[] cds = td.getColumnFamilies();
//        for ( HColumnDescriptor cd : cds ) {
//            builder.put( cd.getNameAsString(), cd.getConfiguration() );
//        }
//        return builder.build();
//        return  null;
//    }

    /**
     * 插入数据
     * @param namespace     命名空间
     * @param tableName     表名
     * @param rowkey        行健
     * @param map           需要保持的值，参数为Map<String,Map<String,Object>>，
     *                       具体格式为{ 列族1:{列1:值,列2:值},列族2:{列3:值,列4:值} }
     * @throws IOException
     */
    public void put( String namespace, String tableName, Object rowkey, Map<String,Map<String,Object>> map ) throws IOException {
        this.put( namespace, tableName, rowkey, -1, map );
    }

    public void put( String namespace, String tableName, Object rowkey, long ts,Map<String,Map<String,Object>> map ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        Put put = null;
//        if ( ts <= 0 ) {
//            put = new Put( BytesUtil.getBytes( rowkey ) );
//        } else {
//            put = new Put( BytesUtil.getBytes( rowkey ), ts );
//        }
//        Iterator<String> iterator = map.keySet().iterator();
//        while ( iterator.hasNext() ) {
//            String cf = iterator.next();
//            Map<String,Object> values = map.get( cf );
//            Iterator<String> vIterator = values.keySet().iterator();
//            while ( vIterator.hasNext() ){
//                String column = vIterator.next();
//                Object value = values.get( column );
//                put.addColumn( Bytes.toBytes( cf ), Bytes.toBytes( column ), BytesUtil.getBytes( value ) );
//            }
//        }
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            table.put( put );
//        }
    }

    public void put( String namespace, String tableName, Object rowkey, long ts, List<String[]> cfs ,List<Object> data ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        Put put = null;
//        if ( ts <= 0 ) {
//            put = new Put( BytesUtil.getBytes( rowkey ) );
//        } else {
//            put = new Put( BytesUtil.getBytes( rowkey ), ts );
//        }
//        for ( int i = 0 ; i < cfs.size() ; i++ ) {
//            String[] cf = cfs.get( i );
//            Object value = data.get( i );
//            put.addColumn( Bytes.toBytes( cf[0] ), Bytes.toBytes( cf[1] ), BytesUtil.getBytes( value ) );
//        }
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            table.put( put );
//        }
    }

    public void put( String tableName, Object rowkey, Map<String,Map<String,Object>> map ) throws IOException {
        this.put( namespace, tableName, rowkey, map );
    }

    /**
     * 根据行健查询
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKey    行健
     * @return          查询结果格式为：
     *                  [{
     *                      "VALUE":[116,101,115,116,49,50,51,52,53],
     *                      "COLUMN_FAMILY":"d1",
     *                      "COLUMN":"d11",
     *                      "TIMESTAMP":1492516780054
     *                     },{
     *                      "VALUE":[116,101,115,116,49,50,51,52,53],
     *                      "COLUMN_FAMILY":"d12",
     *                      "COLUMN":"d112",
     *                      "TIMESTAMP":1492516780054
     *                   }]
     *                   VALUE          列存储的值，类型为byte[]
     *                   COLUMN_FAMILY  列族名称
     *                   COLUMN         列名称
     *                   TIMESTAMP      时间戳
     */
    public List<Map<String,Object>> get( String namespace, String tableName, String rowKey ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        Get get = new Get( Bytes.toBytes( rowKey ) );
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            return resultToList( table.get( get ) );
//        }
        return  null;
    }

    public Collection<List<Map<String,Object>>> get( String namespace, String tableName, String rowKey, Map<String,Object> map ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        Get get = new Get( Bytes.toBytes( rowKey ) );
//        if ( map.get( TIME_START ) != null && map.get( TIME_END ) != null ){
//            get.setTimeRange( (Long)map.get( TIME_START ), (Long)map.get( TIME_END ) );
//        }
//        if ( map.get( VERSIONS ) != null ){
//            get.setMaxVersions( (Integer)map.get( VERSIONS ) );
//        } else {
//            get.setMaxVersions();
//        }
//        if ( map.get( FAMILYS ) != null ){
//            List<String> familys = (List<String>) map.get( FAMILYS );
//            for ( String family : familys ) {
//                get.addFamily( family.getBytes() );
//            }
//        }
//        if ( map.get( COLUMNS ) != null ){
//            List<String> fcs = (List<String>) map.get( COLUMNS );
//            for ( String fc : fcs ) {
//                String[] column = fc.split(":" );
//                get.addColumn( column[0].getBytes(), column[1].getBytes() );
//            }
//        }
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            return resultToLists( table.get( get ) );
//        }
        return  null;
    }

    public List<Map<String,Object>> get( String tableName, String rowKey ) throws IOException {
//        if ( namespace != null && namespace.length() > 0 ){
//            tableName = namespace + ":" + tableName;
//        }
//        TableName tn = TableName.valueOf( tableName );
//        Get get = new Get( Bytes.toBytes( rowKey ) );
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            return resultToList( table.get( get ) );
//        }
        return  null;
    }

//    private List<Map<String,Object>> resultToList( Result result ) {
//        if (result.isEmpty()) {
//            return null;
//        }
//        ImmutableList.Builder<Map<String,Object>> listBuilder = ImmutableList.builder();
//        result.listCells().forEach( cell -> {
//            ImmutableMap.Builder<String,Object> builder = ImmutableMap.builder();
//            builder.put( COLUMN_FAMILY, getColumnFamily( cell ) );
//            builder.put( COLUMN, getQualifier( cell ) );
//            builder.put( VALUE, getValue( cell ) );
//            builder.put( TIMESTAMP, new Date( cell.getTimestamp() ) );
//            listBuilder.add(builder.build());
//        } );
//        return listBuilder.build();
//    }

//    private Collection<List<Map<String,Object>>> resultToLists( Result result ) {
//        if (result.isEmpty()) {
//            return null;
//        }
//        TreeMap<Long,List<Map<String,Object>>> tree = Maps.newTreeMap();
//        result.listCells().forEach( cell -> {
//            List<Map<String,Object>> list = null;
//            if ( tree.get( cell.getTimestamp() ) != null ){
//                list = tree.get( cell.getTimestamp() );
//            } else {
//                list = Lists.newArrayList();
//                tree.put( cell.getTimestamp(), list );
//            }
//            ImmutableMap.Builder<String,Object> builder = ImmutableMap.builder();
//            builder.put( COLUMN_FAMILY, getColumnFamily( cell ) );
//            builder.put( COLUMN, getQualifier( cell ) );
//            builder.put( VALUE, getValue( cell ) );
//            builder.put( TIMESTAMP, new Date( cell.getTimestamp() ) );
//            list.add( builder.build() );
//        } );
//        return tree.values();
//    }

//    private String getColumnFamily( Cell cell ){
//        return Bytes.toString( cell.getFamilyArray(),cell.getFamilyOffset(), cell.getFamilyLength() );
//    }
//
//    private String getQualifier( Cell cell ){
//        return Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(), cell.getQualifierLength() );
//    }
//
//    private byte[] getValue( Cell cell ){
//        ByteBuffer byteBuffer = ByteBuffer.allocate( cell.getValueLength() );
//        byteBuffer.put( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength() );
//        return byteBuffer.array();
//    }

    /**
     * 删除指定行
     * @param tableName  表名
     * @param rowKey     行健
     * @throws IOException
     */
    public void delete( String tableName, String rowKey ) throws IOException {
        delete( namespace, tableName, rowKey, null, null );
    }

    /**
     * 删除指定行
     * @param namespace  命名空间
     * @param tableName  表名
     * @param rowKey     行健
     * @throws IOException
     */
    public void delete( String namespace, String tableName, String rowKey ) throws IOException {
        delete( namespace, tableName, rowKey, null, null );
    }

    /**
     * 删除指定行或者列族
     * @param namespace     命名空间
     * @param tableName     表名
     * @param rowKey        行健
     * @param columnFamily  列族
     * @throws IOException
     */
    public void delete( String namespace, String tableName, String rowKey, String[] columnFamily ) throws IOException {
        delete( namespace, tableName, rowKey, columnFamily, null );
    }

    /**
     * 删除指定行、列族、列
     * @param namespace     命名空间
     * @param tableName     表名
     * @param rowKey        行健
     * @param columnFamilys 列族
     * @param columns       列
     * @throws IOException
     */
    public void delete( String namespace, String tableName, String rowKey,
                                String[] columnFamilys, Map<String,String[]> columns ) throws IOException {
//        if ( namespace != null && namespace.length() > 0 ){
//            tableName = namespace + ":" + tableName;
//        }
//        TableName tn = TableName.valueOf( tableName );
//        Delete del = new Delete( Bytes.toBytes( rowKey ) );
//        if ( columnFamilys != null && columnFamilys.length > 0 ){
//            for ( String columnFamily : columnFamilys ) {
//                del.addFamily( Bytes.toBytes( columnFamily ) );
//            }
//        }
//        if ( columns != null && columns.size() > 0 ){
//            columns.keySet().forEach( cf -> {
//                String[] columnNames = columns.get( cf );
//                for ( String columnName : columnNames ){
//                    del.addColumns( Bytes.toBytes( cf ), Bytes.toBytes( columnName ) );
//                }
//            });
//        }
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            table.delete( del );
//        }
    }

    /**
     * 批量删除指定行
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKeys   行健列表
     * @throws IOException
     */
    public void delete( String namespace, String tableName, String[] rowKeys ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        ImmutableList.Builder<Delete> builder = ImmutableList.builder();
//        for ( String rowKey : rowKeys ){
//            builder.add( new Delete( Bytes.toBytes( rowKey ) ) );
//        }
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            table.delete( builder.build() );
//        }
    }


    /**
     * 删除指定行
     * @param namespace     命名空间
     * @param tableName     表名
     * @param rowKeys       行健信息
     * @param cfs           列族信息，格式为{行健1:[列族1,列族2],行健2:[列族3,列族4]}
     * @param cols          列信息，格式为{行健1:{列族1:[列1,列2]},行健2:{列族2:[列3,列4]}}
     * @throws IOException
     */
    public void delete( String namespace, String tableName, String[] rowKeys,
                        Map<String,String[]> cfs, Map<String,Map<String,String[]>> cols ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        ImmutableList.Builder<Delete> builder = ImmutableList.builder();
//        if ( rowKeys != null && rowKeys.length > 0 ) {
//            for ( String rowKey : rowKeys ){
//                builder.add( new Delete( Bytes.toBytes( rowKey ) ) );
//            }
//        }
//
//        if ( cfs != null && cfs.size() > 0 ) {
//            cfs.keySet().forEach( rowKey -> {
//                String[] columnFamilys = cfs.get( rowKey );
//                for ( String cf : columnFamilys ){
//                    builder.add( new Delete( Bytes.toBytes( rowKey ) ).addFamily( Bytes.toBytes( cf ) ) );
//                }
//            });
//        }
//
//        if ( cols != null && cols.size() > 0 ){
//            cols.keySet().forEach( rowKey -> {
//                Map<String,String[]> columnFamilys = cols.get( rowKey );
//                columnFamilys.keySet().forEach( columnFamily -> {
//                    String[] columns = columnFamilys.get( columnFamily );
//                    for ( String column : columns ) {
//                        builder.add( new Delete( Bytes.toBytes( rowKey ) )
//                                    .addColumn( Bytes.toBytes( columnFamily ), Bytes.toBytes( column ) ) );
//                    }
//                });
//            });
//        }
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            table.delete( builder.build() );
//        }
    }

    /**
     * 按照条件查询
     * @param namespace     命名空间
     * @param tableName     表名
     * @param where         查询条件
     * @param t             rowkey的class
     * @param <T>
     * @return              查询结果格式为：
     *                  { ROWKEY:[{
     *                      "VALUE":[116,101,115,116,49,50,51,52,53],
     *                      "COLUMN_FAMILY":"d1",
     *                      "COLUMN":"d11",
     *                      "TIMESTAMP":1492516780054
     *                     },{
     *                      "VALUE":[116,101,115,116,49,50,51,52,53],
     *                      "COLUMN_FAMILY":"d12",
     *                      "COLUMN":"d112",
     *                      "TIMESTAMP":1492516780054
     *                   }]
     *                   }
     *                   ROWKEY         行健
     *                   VALUE          列存储的值，类型为byte[]
     *                   COLUMN_FAMILY  列族名称
     *                   COLUMN         列名称
     *                   TIMESTAMP      时间戳
     * @throws IOException
     */
    public <T> Map<T,List<Map<String,Object>>> search( String namespace, String tableName, HBaseWhere where, Class<T> t ) throws IOException {
//        if ( namespace != null && namespace.length() > 0 ){
//            tableName = namespace + ":" + tableName;
//        }
//        TableName tn = TableName.valueOf( tableName );
//        Scan scan = new Scan();
//        scan.setFilter( where.getFilter() );
//        return search( tn, scan, t );
        return  null;
    }

    public <T> Map<T,List<Map<String,Object>>> search( String tableName, HBaseWhere where, Class<T> t ) throws IOException {
        return this.search( namespace, tableName, where, t );
    }

    /**
     * 按照条件分页查询
     * @param namespace     命名空间
     * @param tableName     表名
     * @param where         查询条件
     * @param startRowKey   开始行健
     * @param pageSize      每页条数
     * @param t             行健class
     * @param <T>
     * @return              查询结果格式为：
     *                  { ROWKEY:[{
     *                      "VALUE":[116,101,115,116,49,50,51,52,53],
     *                      "COLUMN_FAMILY":"d1",
     *                      "COLUMN":"d11",
     *                      "TIMESTAMP":1492516780054
     *                     },{
     *                      "VALUE":[116,101,115,116,49,50,51,52,53],
     *                      "COLUMN_FAMILY":"d12",
     *                      "COLUMN":"d112",
     *                      "TIMESTAMP":1492516780054
     *                   }]
     *                   }
     *                   ROWKEY         行健
     *                   VALUE          列存储的值，类型为byte[]
     *                   COLUMN_FAMILY  列族名称
     *                   COLUMN         列名称
     *                   TIMESTAMP      时间戳
     * @throws IOException
     */
    public <T> Map<T,List<Map<String,Object>>> search( String namespace, String tableName, HBaseWhere where,
                                                       Object startRowKey, int pageSize,Class<T> t ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        Scan scan = new Scan();
//        if (pageSize <= 0) {
//            pageSize = 10;
//        }
//        // 计算起始页和结束页
//        int nowPageSize=pageSize+1;
//        PageFilter pageFilter = new PageFilter( nowPageSize );
//        FilterList filterList = new FilterList();
//        filterList.addFilter( where.getFilter() );
//        filterList.addFilter( pageFilter );
//        scan.setFilter( filterList );
//        scan.setMaxResultSize( nowPageSize );
//        scan.setStartRow( BytesUtil.getBytes( startRowKey ) );
//        return search( tn, scan, t );
        return  null;
    }

//    private <T> Map<T,List<Map<String,Object>>> search( TableName tn, Scan scan, Class<T> t ) throws IOException {
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            ResultScanner rs = table.getScanner( scan );
//            ImmutableMap.Builder<T,List<Map<String,Object>>> builder = ImmutableMap.builder();
//            rs.forEach( result -> {
//                builder.put( BytesUtil.toObject( result.getRow(), t ), resultToList( result ) );
//            });
//            return builder.build();
//        }
//    }

    public <T> Map<T,List<Map<String,Object>>> search( String tableName, HBaseWhere where,
                                                       Object startRowKey, int pageSize,Class<T> t ) throws IOException {
        return this.search( namespace, tableName, where, startRowKey, pageSize, t );
    }

    /**
     * 按照条件统计条数
     * @param namespace     命名空间
     * @param tableName     表名
     * @param where         查询条件
     * @return              统计符合条件row的数量
     * @throws IOException
     */
    public long count( String namespace, String tableName, HBaseWhere where ) throws IOException {
        if ( namespace != null && namespace.length() > 0 ){
            tableName = namespace + ":" + tableName;
        }
//        TableName tn = TableName.valueOf( tableName );
//        Scan scan = new Scan();
//        FilterList filterList = new FilterList();
//        filterList.addFilter( where.getFilter() );
//        filterList.addFilter( new FirstKeyOnlyFilter() );
//        scan.setFilter( filterList );
//        try ( Table table = this.getConnection().getTable( tn ) ) {
//            ResultScanner rs = table.getScanner( scan );
//            long rowCount = 0;
//            for ( Result result : rs ) {
//                rowCount += result.size();
//            }
//            return rowCount;
//        }
        return  0;
    }

    public boolean existSpace(String space) throws IOException {
        boolean flag=false;
        List<String> spaceArray = listNamespace();
        for (String s:spaceArray){
            if (space.equals(s)){
                flag=true;
                break;
            }
        }
        return flag;
    }
}
