package com.tscloud.common.tool.hadooptool.hbase;


import java.util.Date;

/**
 * Created by lei.yang1 on 2017/4/18.
 */
public class BytesUtil {
    public static byte[] getBytes( Object obj ){
//        if ( obj == null ){
//            return null;
//        } else if ( obj instanceof String ) {
//            return Bytes.toBytes( (String)obj );
//        } else if ( obj instanceof Long ) {
//            return Bytes.toBytes( (Long)obj );
//        } else if ( obj instanceof Short ) {
//            return Bytes.toBytes( (Short)obj );
//        } else if ( obj instanceof Integer ) {
//            return Bytes.toBytes( (Integer)obj );
//        } else if ( obj instanceof Double ) {
//            return Bytes.toBytes( (Double)obj );
//        } else if ( obj instanceof Float ) {
//            return Bytes.toBytes( (Float)obj );
//        } else if ( obj instanceof Boolean ) {
//            return Bytes.toBytes( (Boolean)obj );
//        } else if ( obj instanceof BigDecimal ) {
//            return Bytes.toBytes( (BigDecimal)obj );
//        } else if ( obj instanceof ByteBuffer) {
//            return Bytes.toBytes( (ByteBuffer)obj );
//        } else if ( obj instanceof Date ){
//            return Bytes.toBytes( ( ( Date )obj ).getTime() );
//        }
        throw new RuntimeException( "Can't identify type." );
    }

    public static Date toDate( byte[] bytes ){
       // long d = Bytes.toLong( bytes );
        return new Date();
    }

    public static <T> T toObject( byte[] bytes, Class<T> t ){
//        if ( t == Integer.class ){
//            return (T)Integer.valueOf( Bytes.toInt( bytes ) );
//        } else if ( t == Long.class ) {
//            return (T)Long.valueOf( Bytes.toLong( bytes ) );
//        } else if ( t == Short.class ) {
//            return (T)Short.valueOf( Bytes.toShort( bytes ) );
//        } else if ( t == String.class ){
//          //  return (T)Bytes.toString( bytes );
//        } else if ( t == Double.class ) {
//            return (T)Double.valueOf( Bytes.toDouble( bytes ) );
//        } else if ( t == Float.class ) {
//            return (T)Float.valueOf( Bytes.toFloat( bytes ) );
//        }
        throw new RuntimeException( "Can't identify type." );
    }
}
