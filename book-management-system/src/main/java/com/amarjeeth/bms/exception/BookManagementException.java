package com.amarjeeth.bms.exception;

//Base exception class
public class BookManagementException extends RuntimeException {
	public BookManagementException(String message) {
		super(message);
	}

	public BookManagementException(String message, Throwable cause) {
		super(message, cause);
	}
}



