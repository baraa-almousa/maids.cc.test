package com.java.springBoot.app.Exception;

public class NoDataFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoDataFoundException(String param) {

        super(param);
    }
}