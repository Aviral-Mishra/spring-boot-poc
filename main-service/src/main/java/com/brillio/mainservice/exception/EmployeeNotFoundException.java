package com.brillio.mainservice.exception;

public class EmployeeNotFoundException extends RuntimeException{
	public EmployeeNotFoundException(String message){
		super(message);
	}
}
