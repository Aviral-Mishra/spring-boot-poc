package com.brillio.mainservice.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{

	public EmployeeAlreadyExistsException(String message){
		super(message);
	}
}
