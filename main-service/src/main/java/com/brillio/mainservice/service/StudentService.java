package com.brillio.mainservice.service;

import com.brillio.mainservice.entity.student.StudentEntity;
import com.brillio.mainservice.exception.StudentAlreadyExistsException;
import com.brillio.mainservice.exception.StudentNotFoundException;
import com.brillio.mainservice.model.StudentModel;

public interface StudentService {
	boolean addNewStudent(StudentModel studentModel, String addedBy) throws StudentAlreadyExistsException;
	StudentEntity findStudentByEmail(String email) throws StudentNotFoundException;
	boolean deleteStudentByEmail(String email) throws StudentNotFoundException;
	boolean updateStudentByEmail(StudentModel studentModel) throws StudentNotFoundException;
}
