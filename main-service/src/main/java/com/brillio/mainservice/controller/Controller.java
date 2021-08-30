package com.brillio.mainservice.controller;


import com.brillio.mainservice.exception.*;
import com.brillio.mainservice.model.EmployeeModel;
import com.brillio.mainservice.model.StudentModel;
import com.brillio.mainservice.service.EmployeeService;
import com.brillio.mainservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class Controller {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	StudentService studentService;

	@GetMapping("/health")
	public String check(){
		return "API is up and running";
	}

	@PostMapping("/employee")
	public ResponseEntity addNewEmployee(@RequestBody EmployeeModel employeeModel, @RequestParam String addedBy) throws EmployeeAlreadyExistsException {
		if (employeeModel == null) {
			return new ResponseEntity<>("Insuffecient Data", HttpStatus.BAD_REQUEST);
		}
		else if(employeeModel.getEmployeeEmail()==null || employeeModel.getEmployeeEmail().isEmpty()){
			throw new PrimaryKeyNotPresentInPayloadException("Primary Key is not present in payload");
		}
		else{
			boolean flag=employeeService.addNewEmployee(employeeModel,addedBy);
			if(!flag) {
				return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity("Employee Successfully Added", HttpStatus.CREATED);
		}
	}

	@GetMapping("/employee")
	public ResponseEntity getEmployeeByEmail(@RequestParam String email) throws EmployeeNotFoundException {
		return new ResponseEntity(employeeService.findEmployeeByEmail(email), HttpStatus.FOUND);
	}

	@DeleteMapping("/employee")
	public ResponseEntity deleteEmployeeByEmail(@RequestParam String email) throws EmployeeNotFoundException {
		if (employeeService.deleteEmployeeByEmail(email)) {
			return new ResponseEntity("Employee removed successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/employee")
	public ResponseEntity updateEmployeeByEmail(@RequestBody EmployeeModel employeeModel) throws EmployeeNotFoundException {
		if (employeeService.updateEmployeeByEmail(employeeModel)) {
			return new ResponseEntity("Employee Details updated successfully", HttpStatus.OK);
		}
		else if(employeeModel.getEmployeeEmail()==null || employeeModel.getEmployeeEmail().isEmpty()){
			throw new PrimaryKeyNotPresentInPayloadException("Primary Key is not present in payload");
		}
		else {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/student")
	public ResponseEntity addNewStudent(@RequestBody StudentModel studentModel, @RequestParam String addedBy) throws StudentAlreadyExistsException {
		if (studentModel == null) {
			return new ResponseEntity<>("Insuffecient Data", HttpStatus.BAD_REQUEST);
		}
		else if(studentModel.getStudentEmail()==null || studentModel.getStudentEmail().isEmpty()){
			throw new PrimaryKeyNotPresentInPayloadException("Primary Key is not present in payload");
		}
		else{
			boolean flag=studentService.addNewStudent(studentModel,addedBy);
			if(!flag)
				return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
			return new ResponseEntity("Student Successfully Added", HttpStatus.CREATED);
		}
	}

	@GetMapping("/student")
	public ResponseEntity getStudentByEmail(@RequestParam String email) throws StudentNotFoundException {
		return new ResponseEntity(studentService.findStudentByEmail(email), HttpStatus.FOUND);
	}

	@DeleteMapping("/student")
	public ResponseEntity deleteStudentByEmail(@RequestParam String email) throws StudentNotFoundException {
		if (studentService.deleteStudentByEmail(email)) {
			return new ResponseEntity("Student removed successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/student")
	public ResponseEntity updateStudentByEmail(@RequestBody StudentModel studentModel) throws StudentNotFoundException {
		if (studentService.updateStudentByEmail(studentModel)) {
			return new ResponseEntity("Student Details updated successfully", HttpStatus.OK);
		}
		else if(studentModel.getStudentEmail()==null || studentModel.getStudentEmail().isEmpty()){
			throw new PrimaryKeyNotPresentInPayloadException("Primary Key is not present in payload");
		}
		else {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
	}
}
