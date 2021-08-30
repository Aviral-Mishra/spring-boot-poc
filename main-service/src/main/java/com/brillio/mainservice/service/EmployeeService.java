package com.brillio.mainservice.service;

import com.brillio.mainservice.entity.employee.EmployeeEntity;
import com.brillio.mainservice.exception.EmployeeAlreadyExistsException;
import com.brillio.mainservice.exception.EmployeeNotFoundException;
import com.brillio.mainservice.model.EmployeeModel;

public interface EmployeeService {

    boolean addNewEmployee(EmployeeModel employeeModel,String addedBy) throws EmployeeAlreadyExistsException;
    EmployeeEntity findEmployeeByEmail(String email) throws EmployeeNotFoundException;
    boolean deleteEmployeeByEmail(String email) throws EmployeeNotFoundException;
    boolean updateEmployeeByEmail(EmployeeModel employeeModel) throws EmployeeNotFoundException;
}
