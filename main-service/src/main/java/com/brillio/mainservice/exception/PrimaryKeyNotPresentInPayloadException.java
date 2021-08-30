package com.brillio.mainservice.exception;

public class PrimaryKeyNotPresentInPayloadException extends RuntimeException{

	public PrimaryKeyNotPresentInPayloadException(String message){
		super(message);
	}
}
