package com.tscloud.common.framework.Exception;

public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String msg, Exception e) {
		super(msg, e);
	} 
	
	public ServiceException(String msg) {
		super(msg);
	}
}
