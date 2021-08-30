package com.brillio.mainservice.controller;

import com.brillio.mainservice.entity.employee.EmployeeEntity;
import com.brillio.mainservice.exception.EmployeeNotFoundException;
import com.brillio.mainservice.model.EmployeeModel;
import com.brillio.mainservice.service.EmployeeService;
import com.brillio.mainservice.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = Controller.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@InjectMocks
	private Controller controller;

	@MockBean
	private StudentService studentService;

	@Autowired
	private ObjectMapper mapper;

	private EmployeeModel employeeModel;
	private EmployeeEntity employeeEntity;

	String addedBy;

	public static final String EMPLOYEE_OPERATIONS_URL = "/api/v1/employee";

	@BeforeEach
	void setUp(){
		employeeModel=new EmployeeModel("tony@gmail.com","Tony Stark","Manager","New york");
		employeeEntity=new EmployeeEntity("tony@gmail.com","Tony Stark","Manager","New york","nikfury@gmail.com");
		addedBy="nikfury@gmail.com";
	}

	@Test
	void givenEmployeeDetailsWhenEmployeeExistsThenUpdateEmployeeDetailsAndShouldReturnOkStatus() throws Exception {
		when(employeeService.updateEmployeeByEmail(employeeModel)).thenReturn(true);

		mockMvc.perform(put(EMPLOYEE_OPERATIONS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(employeeModel)))
				.andExpect(status().isOk());
	}

	@Test
	void givenEmployeeDetailsIsNullThenShouldReturnBadRequestStatus() throws Exception {

		mockMvc.perform(put(EMPLOYEE_OPERATIONS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(null)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void givenNewEmployeeDetailsWithNullValuesThenShouldReturnBadRequestStatus() throws Exception{
		mockMvc.perform(post(EMPLOYEE_OPERATIONS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(employeeModel)
				)).andExpect(status().isBadRequest());
	}

	@Test
	void givenNewEmployeeDetailsWhenItIsNotSavedThenShouldReturnBadRequestStatus() throws Exception{
		when(employeeService.addNewEmployee(any(),any())).thenReturn(false);
		mockMvc.perform(post(EMPLOYEE_OPERATIONS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(employeeModel)
				)).andExpect(status().isBadRequest());
	}

	@Test
	void givenEmployeeIdWhenEmployeeExistsThenShouldReturnFoundStatus() throws Exception {
		when(employeeService.findEmployeeByEmail("tony@gmail.com")).thenReturn(employeeEntity);
		mockMvc.perform(get(EMPLOYEE_OPERATIONS_URL)
				.param("email","tony@gmail.com"))
				.andExpect(status().isFound());
	}

	@Test
	void givenEmployeeIdWhenEmployeeNotExistsThenShouldReturnFoundStatus() throws Exception {
		when(employeeService.findEmployeeByEmail(any())).thenThrow(EmployeeNotFoundException.class);
		mockMvc.perform(get(EMPLOYEE_OPERATIONS_URL)
				.param("email","tony@gmail.com"))
				.andExpect(status().isNotFound());
	}
}
