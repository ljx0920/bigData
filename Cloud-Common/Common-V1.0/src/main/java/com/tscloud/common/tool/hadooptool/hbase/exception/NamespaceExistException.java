package com.tscloud.common.tool.hadooptool.hbase.exception;

/**
 * Created by lei.yang1 on 2017/4/18.
 */
public class NamespaceExistException extends Exception {
    public NamespaceExistException( String msg ){
        super( msg );
    }
}
