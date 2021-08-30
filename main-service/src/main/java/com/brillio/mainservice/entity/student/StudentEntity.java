package com.brillio.mainservice.entity.student;

import com.brillio.mainservice.model.StudentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StudentEntity {
	@Id
	private String studentEmail;
	private String studentName;
	private int studentAge;
	private String studentDepartment;
	private String studentAddedBy;

	public StudentEntity(StudentModel studentModel,String addedBy){
		this.studentEmail=studentModel.getStudentEmail();
		this.studentName=studentModel.getStudentName();
		this.studentAge=studentModel.getStudentAge();
		this.studentDepartment=studentModel.getStudentDepartment();
		this.studentAddedBy=addedBy;
	}
}
