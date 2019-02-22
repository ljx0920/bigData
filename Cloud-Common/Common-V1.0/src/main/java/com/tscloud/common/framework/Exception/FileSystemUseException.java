package com.tscloud.common.framework.Exception;

/**
 *
 * Created by Administrator on 2015/12/23.
 */
public class FileSystemUseException extends ServiceException {
    public FileSystemUseException(Exception e){
        super(e);
    }

    public FileSystemUseException(String message, Exception e){
        super( message, e );
    }

    public FileSystemUseException(String message){
        super( message);
    }
}
