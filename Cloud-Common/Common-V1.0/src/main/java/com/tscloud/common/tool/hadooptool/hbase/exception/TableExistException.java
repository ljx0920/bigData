package com.tscloud.common.tool.hadooptool.hbase.exception;

/**
 * Created by lei.yang1 on 2017/4/18.
 */
public class TableExistException extends Exception {

    public TableExistException(){
        super("table exist.");
    }

    public TableExistException( String msg ){
        super( msg );
    }
}
