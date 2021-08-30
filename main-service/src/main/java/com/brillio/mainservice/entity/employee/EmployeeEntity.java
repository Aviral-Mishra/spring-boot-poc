package com.brillio.mainservice.entity.employee;

import com.brillio.mainservice.model.EmployeeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "Employee_entity")
public class EmployeeEntity {

	@Id
	@Column(name = "employee_email")
	private String employeeEmail;
	@Column(name = "employee_name")
	private String employeeName;
	@Column(name="employee_designation")
	private String employeeDesignation;
	@Column(name = "employee_address")
	private String employeeAddress;
	@Column(name = "employee_addedBy")
	private String addedBy;

	public EmployeeEntity(EmployeeModel employeeModel,String addedBy){
		this.employeeEmail=employeeModel.getEmployeeEmail();
		this.employeeName=employeeModel.getEmployeeName();
		this.employeeDesignation=employeeModel.getEmployeeDesignation();
		this.employeeAddress=employeeModel.getEmployeeAddress();
		this.addedBy=addedBy;
	}

}
