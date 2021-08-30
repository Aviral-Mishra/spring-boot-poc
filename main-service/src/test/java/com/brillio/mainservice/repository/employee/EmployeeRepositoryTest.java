package com.brillio.mainservice.repository.employee;

import com.brillio.mainservice.entity.employee.EmployeeEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;

	EmployeeEntity employeeEntity;

	@BeforeEach
	void setUp(){
		employeeEntity=new EmployeeEntity("check@gmail.com","check kumar","engineer","hell","AK");
	}

	@AfterEach
	void tearDown(){
		employeeEntity=null;
	}

//	@Test
//	void givenEmployeeToSaveThenShouldReturnSavedEmployee(){
//		EmployeeEntity employeeEntity1=employeeRepository.save(employeeEntity);
//		assertEquals(employeeEntity.getEmployeeEmail(),employeeEntity1.getEmployeeEmail());
//	}
}
