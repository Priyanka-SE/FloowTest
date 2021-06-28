package com.floow.driverdetails.exception;

public class InvalidRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String errorMessage) {
	    super(errorMessage);
	  }
}
