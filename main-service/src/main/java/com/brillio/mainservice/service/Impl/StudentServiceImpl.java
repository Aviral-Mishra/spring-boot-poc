package com.brillio.mainservice.service.Impl;

import com.brillio.mainservice.entity.employee.EmployeeEntity;
import com.brillio.mainservice.entity.student.StudentEntity;
import com.brillio.mainservice.exception.EmployeeNotFoundException;
import com.brillio.mainservice.exception.StudentAlreadyExistsException;
import com.brillio.mainservice.exception.StudentNotFoundException;
import com.brillio.mainservice.model.StudentModel;
import com.brillio.mainservice.repository.student.StudentRepository;
import com.brillio.mainservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Override
	public boolean addNewStudent(StudentModel studentModel, String addedBy) throws StudentAlreadyExistsException {
		StudentEntity studentEntity=new StudentEntity(studentModel,addedBy);
		if(studentRepository.existsByStudentEmail(studentModel.getStudentEmail())){
			throw new StudentAlreadyExistsException("Student already exists with this mail id");
		}
		else {
			studentRepository.save(studentEntity);
			return true;
		}
	}

	@Override
	public StudentEntity findStudentByEmail(String email) throws StudentNotFoundException {
		Optional<StudentEntity> studentEntity =studentRepository.findByStudentEmail(email);
		return studentEntity.orElseThrow(()->new StudentNotFoundException("No Student exists with this email"));
	}

	@Override
	public boolean deleteStudentByEmail(String email) throws StudentNotFoundException {
		Optional<StudentEntity> studentEntity=studentRepository.findByStudentEmail(email);
		if(studentEntity.isPresent()){
			studentRepository.delete(studentEntity.get());
			return true;
		}
		else {
			throw new StudentNotFoundException("no Student exists with this email id");
		}	}

	@Override
	public boolean updateStudentByEmail(StudentModel studentModel) throws StudentNotFoundException {
		Optional<StudentEntity> studentEntity=studentRepository.findByStudentEmail(studentModel.getStudentEmail());
		if(studentEntity.isPresent()){
			studentRepository.updateEmployeeEmail(studentModel.getStudentEmail(),studentModel.getStudentName(),studentModel.getStudentAge(),studentModel.getStudentDepartment());
			return true;
		}
		else {
			throw new StudentNotFoundException("no Student exists with this email id");
		}
	}
}
