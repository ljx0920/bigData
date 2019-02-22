package com.tscloud.common.tool.excel.exception;

/**
 * Created by jiancai.wang on 2017/5/31.
 */
public class ImportException extends Exception {

    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable throwable){
        super(message, throwable);
    }
}
