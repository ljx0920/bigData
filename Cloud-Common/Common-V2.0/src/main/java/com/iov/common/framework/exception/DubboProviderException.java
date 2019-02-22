package com.iov.common.framework.exception;

/**
 * @author daowan.hu
 * @date 2016/10/27
 */
public class DubboProviderException extends Exception {

    private static final long serialVersionUID = 1L;

    public DubboProviderException(Exception e) {
        super(e);
    }

    public DubboProviderException(String msg, Exception e) {
        super(msg, e);
    }

    public DubboProviderException(String msg) {
        super(msg);
    }
}
