package com.tscloud.common.tool.hadooptool.hbase;


/**
 * Created by lei.yang1 on 2017/4/19.
 */
public class HBaseWhere {

//    private Filter filter;
//
//    private FilterList.Operator operator = FilterList.Operator.MUST_PASS_ALL;
//
//    /**
//     * 查询条件内关系设定为 and
//     * @return
//     */
//    public HBaseWhere and(){
//        operator = FilterList.Operator.MUST_PASS_ALL;
//        return this;
//    }
//
//    /**
//     * 查询条件内关系设定为 or
//     * @return
//     */
//    public HBaseWhere or(){
//        operator = FilterList.Operator.MUST_PASS_ALL;
//        return this;
//    }
//
//    /**
//     * 某一列族的列值等于设定值
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere equals( String cf, String col, Object value ){
//        join( cf, col, CompareFilter.CompareOp.EQUAL, value );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值不等于设定值
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere notEquals( String cf, String col, Object value ){
//        join( cf, col, CompareFilter.CompareOp.NOT_EQUAL, value );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值大于设定值
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere greater( String cf, String col, Object value ){
//        join( cf, col, CompareFilter.CompareOp.GREATER, value );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值大于等于设定值
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere greaterOrEqual( String cf, String col, Object value ){
//        join( cf, col, CompareFilter.CompareOp.GREATER_OR_EQUAL, value );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值小于设定值
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere less( String cf, String col, Object value ){
//        join( cf, col, CompareFilter.CompareOp.LESS, value );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值小于等于设定值
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere lessOrEqual( String cf, String col, Object value ){
//        join( cf, col, CompareFilter.CompareOp.LESS_OR_EQUAL, value );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值按照正则表达式进行匹配
//     * @param cf        列族
//     * @param col       列名
//     * @param reg       正则表达式
//     * @return
//     */
//    public HBaseWhere regex( String cf, String col, String reg ){
//        RegexStringComparator comp = new RegexStringComparator( reg );
//        join( cf, col, CompareFilter.CompareOp.EQUAL, comp );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值是否包含指定子字符串
//     * @param cf        列族
//     * @param col       列名
//     * @param subString 子字符串
//     * @return
//     */
//    public HBaseWhere subString( String cf, String col, String subString ){
//        SubstringComparator comp = new SubstringComparator( subString );
//        join( cf, col, CompareFilter.CompareOp.EQUAL, comp );
//        return this;
//    }
//
//    /**
//     * 某一列族的列值按照byte[]方式匹配
//     * @param cf        列族
//     * @param col       列名
//     * @param value     值
//     * @return
//     */
//    public HBaseWhere binaryPrefix( String cf, String col, byte[] value ){
//        join( cf, col, CompareFilter.CompareOp.EQUAL, new BinaryPrefixComparator( value ) );
//        return this;
//    }
//
//    /**
//     * 查询条件与查询条件为and关系
//     * @param where 查询条件
//     * @return
//     */
//    public HBaseWhere and( HBaseWhere where ) {
//        operator = FilterList.Operator.MUST_PASS_ALL;
//        join( where.getFilter() );
//        return this;
//    }
//
//    /**
//     * 查询条件与查询条件为or关系
//     * @param where
//     * @return
//     */
//    public HBaseWhere or( HBaseWhere where ) {
//        operator = FilterList.Operator.MUST_PASS_ONE;
//        join( where.getFilter() );
//        return this;
//    }
//
//    public Filter getFilter(){
//        return this.filter;
//    }
//
//    private void join( Filter f ){
//        if ( this.filter == null ){
//            this.filter = f;
//        } else if ( filter instanceof FilterList ) {
//            ((FilterList) filter).addFilter( f );
//        } else {
//            FilterList fl = new FilterList( this.operator );
//            fl.addFilter( filter );
//            fl.addFilter( f );
//            filter = f;
//        }
//    }
//
//    private void join(String cf, String col, CompareFilter.CompareOp op, Object value){
//        if ( value instanceof ByteArrayComparable ){
//            join( new SingleColumnValueFilter( Bytes.toBytes( cf ),
//                    Bytes.toBytes( col ),
//                    op,
//                    (ByteArrayComparable)value ) );
//        } else {
//            join( new SingleColumnValueFilter( Bytes.toBytes( cf ),
//                    Bytes.toBytes( col ),
//                    op,
//                    BytesUtil.getBytes( value ) ) );
//        }
//    }
}
