package com.brillio.mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(EmployeeAlreadyExistsException.class)
	public ResponseEntity<?> handleEmployeeExists(EmployeeAlreadyExistsException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<?> handleEmployeeNotFound(EmployeeNotFoundException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<?> handleStudentExists(StudentAlreadyExistsException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<?> handleStudentNotFound(StudentNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PrimaryKeyNotPresentInPayloadException.class)
	public ResponseEntity<?> handlePrimaryKeyNotPresentInPayload(PrimaryKeyNotPresentInPayloadException exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
