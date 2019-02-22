package com.tscloud.common.framework.Exception;


public class DaoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(Throwable throwable) {
		super(throwable);
	}

	public DaoException(String msg, Throwable throwable) {
		super(msg,throwable);
	}

    @Override
    public void printStackTrace() {
		super.printStackTrace();
	}

}
