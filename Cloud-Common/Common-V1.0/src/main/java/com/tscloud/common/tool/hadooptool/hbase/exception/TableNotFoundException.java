package com.tscloud.common.tool.hadooptool.hbase.exception;

/**
 * Created by lei.yang1 on 2017/4/18.
 */
public class TableNotFoundException extends Exception {

    public TableNotFoundException(){
        super("table exist.");
    }

    public TableNotFoundException(String msg ){
        super( msg );
    }
}
