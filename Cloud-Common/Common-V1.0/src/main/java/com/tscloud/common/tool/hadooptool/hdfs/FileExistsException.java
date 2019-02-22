package com.tscloud.common.tool.hadooptool.hdfs;

/**
 * Created by lei.yang1 on 2017/4/22.
 */
public class FileExistsException extends Exception {
    public FileExistsException( String msg ){
        super( msg );
    }
}
