package com.amarjeeth.bms.exception;

//Resource not found exception
public class ResourceNotFoundException extends BookManagementException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}