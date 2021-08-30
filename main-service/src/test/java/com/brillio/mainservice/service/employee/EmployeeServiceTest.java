package com.brillio.mainservice.service.employee;


import com.brillio.mainservice.entity.employee.EmployeeEntity;
import com.brillio.mainservice.exception.EmployeeAlreadyExistsException;
import com.brillio.mainservice.exception.EmployeeNotFoundException;
import com.brillio.mainservice.model.EmployeeModel;
import com.brillio.mainservice.repository.employee.EmployeeRepository;
import com.brillio.mainservice.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private EmployeeEntity employeeEntity;
	private EmployeeModel employeeModel;


	@BeforeEach
	void setUp(){
		employeeEntity=new EmployeeEntity("tony@gmail.com","Tony Stark","Manager","New york","nikfury@gmail.com");
		employeeModel=new EmployeeModel("tony@gmail.com","Tony Stark","Manager","New york");

	}

	@AfterEach
	void tearDown(){
		employeeEntity=null;
		employeeModel=null;
	}

	@Test
	void givenEmployeeToSaveThenShouldReturnSavedEmployee() {
		when(employeeRepository.save(any())).thenReturn(employeeEntity);
		assertEquals(true,employeeService.addNewEmployee(employeeModel,"nickfury@gmail.com"));
		verify(employeeRepository,times(1)).save(any());
	}

	@Test
	void givenEmployeeToSaveIfEmployeeExistsAlreadyThenThrowException(){
		when(employeeRepository.existsByEmployeeEmail(any())).thenReturn(true);
		Assertions.assertThrows(EmployeeAlreadyExistsException.class,()->employeeService.addNewEmployee(employeeModel,"any@gmail.com"));
		verify(employeeRepository,times(0)).save(any());

	}

	@Test
	void givenEmployeeIdThenShouldReturnEmployee(){
		when(employeeRepository.findByEmployeeEmail("tony@gmail.com")).thenReturn(Optional.of(employeeEntity));
		assertEquals(employeeEntity.toString(),employeeService.findEmployeeByEmail("tony@gmail.com").toString());
	}

	@Test
	void givenEmployeeIdToFindIfEmployeeNotPesentThenThrowException(){
		when(employeeRepository.findByEmployeeEmail(any())).thenReturn(Optional.empty());
		Assertions.assertThrows(EmployeeNotFoundException.class,()->employeeService.findEmployeeByEmail("any@gmail.com"));
	}

	@Test
	void givenEmployeeIdToDeleteEmployeeDetailsIfEmployeeNotPesentThenThrowException(){
		when(employeeRepository.findByEmployeeEmail(any())).thenReturn(Optional.empty());
			Assertions.assertThrows(EmployeeNotFoundException.class,()->employeeService.deleteEmployeeByEmail("any@gmail.com"));
		verify(employeeRepository,times(1)).findByEmployeeEmail(any());
	}

	@Test
	void givenEmployeeIdToDeleteEmployeeDetailsThenShoulDeleteEmployeeDetails(){
		when(employeeRepository.findByEmployeeEmail("tony@gmail.com")).thenReturn(Optional.of(employeeEntity));
		assertEquals(true,employeeService.deleteEmployeeByEmail("tony@gmail.com"));
		verify(employeeRepository,times(1)).findByEmployeeEmail(any());
		verify(employeeRepository,times(1)).delete(any());
	}

	@Test
	void givenEmployeeDetailToUpdateEmployeeDetailsIfEmployeeNotPesentThenThrowException(){
		when(employeeRepository.findByEmployeeEmail(any())).thenReturn(Optional.empty());
		Assertions.assertThrows(EmployeeNotFoundException.class,()->employeeService.updateEmployeeByEmail(employeeModel));
		verify(employeeRepository,times(1)).findByEmployeeEmail(any());
	}

	@Test
	void givenEmployeeDetailToUpdateEmployeeDetailsthenShouldUpdate(){
		when(employeeRepository.findByEmployeeEmail("tony@gmail.com")).thenReturn(Optional.of(employeeEntity));
		assertEquals(true,employeeService.updateEmployeeByEmail(employeeModel));
		verify(employeeRepository,times(1)).findByEmployeeEmail(any());
		verify(employeeRepository,times(1)).updateEmployeeEmail(employeeModel.getEmployeeEmail(),employeeModel.getEmployeeName(),employeeModel.getEmployeeDesignation(),employeeModel.getEmployeeAddress());
	}

}
