package com.brillio.mainservice.service.Impl;

import com.brillio.mainservice.entity.employee.EmployeeEntity;
import com.brillio.mainservice.exception.EmployeeAlreadyExistsException;
import com.brillio.mainservice.exception.EmployeeNotFoundException;
import com.brillio.mainservice.model.EmployeeModel;
import com.brillio.mainservice.repository.employee.EmployeeRepository;
import com.brillio.mainservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public boolean addNewEmployee(EmployeeModel employeeModel, String addedBy) throws EmployeeAlreadyExistsException {

		if(employeeRepository.existsByEmployeeEmail(employeeModel.getEmployeeEmail())) {
			throw new EmployeeAlreadyExistsException("Employee already exists with this email Id");
		}
		EmployeeEntity employeeEntity=new EmployeeEntity(employeeModel,addedBy);
		employeeRepository.save(employeeEntity);
		return true;
	}

	@Override
	public EmployeeEntity findEmployeeByEmail(String email) throws EmployeeNotFoundException {
		Optional<EmployeeEntity> employeeEntity =employeeRepository.findByEmployeeEmail(email);
		return employeeEntity.orElseThrow(()->new EmployeeNotFoundException("No Employee exists with this email"));
	}

	@Override
	public boolean deleteEmployeeByEmail(String email) throws EmployeeNotFoundException {
		Optional<EmployeeEntity> employeeEntity=employeeRepository.findByEmployeeEmail(email);
		if(employeeEntity.isPresent()){
			employeeRepository.delete(employeeEntity.get());
			return true;
		}
		else {
			throw new EmployeeNotFoundException("no Employee exists with this email id");
		}
	}

	@Override
	public boolean updateEmployeeByEmail(EmployeeModel employeeModel) throws EmployeeNotFoundException {
		Optional<EmployeeEntity> employeeEntity=employeeRepository.findByEmployeeEmail(employeeModel.getEmployeeEmail());
		if(employeeEntity.isPresent()){
			employeeRepository.updateEmployeeEmail(employeeModel.getEmployeeEmail(),employeeModel.getEmployeeName(),employeeModel.getEmployeeDesignation(),employeeModel.getEmployeeAddress());
			return true;
		}
		else {
			throw new EmployeeNotFoundException("no Employee exists with this email id");
		}
	}
}
