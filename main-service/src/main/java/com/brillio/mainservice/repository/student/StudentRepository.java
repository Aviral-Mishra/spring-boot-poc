package com.brillio.mainservice.repository.student;


import com.brillio.mainservice.entity.employee.EmployeeEntity;
import com.brillio.mainservice.entity.student.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,String> {

	boolean existsByStudentEmail(String studentEmail);
	Optional<StudentEntity> findByStudentEmail(String employeeEmail);

	@Modifying
	@Transactional
	@Query(value = "update studententity set studentname=?2,studentage=?3,studentdepartment=?4 where studentemail=?1", nativeQuery = true)
	int updateEmployeeEmail(String studentEmail,String StudentName, int studentAge,String studentDepartment);

}
