package com.tscloud.common.tool.excel.exception;

/**
 * Created by jiancai.wang on 2017/5/31.
 */
public class ExportException extends Exception {

    public ExportException(String message) {
        super(message);
    }

    public ExportException(String message, Throwable throwable){
        super(message, throwable);
    }
}
