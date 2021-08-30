package com.brillio.mainservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeModel {
	@Override
	public String toString() {
		return "EmployeeModel{" +
				"employeeEmail='" + employeeEmail + '\'' +
				", employeeName='" + employeeName + '\'' +
				", employeeDesignation='" + employeeDesignation + '\'' +
				", employeeAddress='" + employeeAddress + '\'' +
				'}';
	}

	private String employeeEmail;
	private String employeeName;
	private String employeeDesignation;
	private String employeeAddress;
}
