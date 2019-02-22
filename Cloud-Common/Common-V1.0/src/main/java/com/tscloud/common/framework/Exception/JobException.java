package com.tscloud.common.framework.Exception;

/**
 * job exception
 *
 * @author sticver
 * @date 14-10-30
 */
public class JobException extends Exception {
    private static final long serialVersionUID = 1L;

    public JobException(){}

    public JobException(String msg) {
        super(msg);
    }

    public JobException(Throwable throwable) {
        super(throwable);
    }

    public JobException(String msg, Throwable throwable) {
        super(msg,throwable);
    }
}
