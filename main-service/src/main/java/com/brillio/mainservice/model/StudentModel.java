package com.brillio.mainservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentModel {

	private String studentEmail;
	private String studentName;
	private int studentAge;
	private String studentDepartment;


}
