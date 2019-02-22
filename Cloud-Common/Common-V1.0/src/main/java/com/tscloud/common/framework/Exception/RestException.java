package com.tscloud.common.framework.Exception;


public class RestException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public RestException() {
		super();
	}

	public RestException(String msg) {
		super(msg);
	}

	public RestException(Throwable throwable) {
		super(throwable);
	}

	public RestException(String msg, Throwable throwable) {
		super(msg,throwable);
	}

    @Override
    public void printStackTrace() {
		super.printStackTrace();
	}

}
