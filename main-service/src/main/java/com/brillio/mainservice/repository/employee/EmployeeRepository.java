package com.brillio.mainservice.repository.employee;

import com.brillio.mainservice.entity.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,String> {

	boolean existsByEmployeeEmail(String employeeEmail);
	Optional<EmployeeEntity> findByEmployeeEmail(String employeeEmail);

	@Modifying
	@Transactional
	@Query(value = "update employee_entity set employee_name=?2,employee_designation=?3,employee_address=?4 where employee_email=?1", nativeQuery = true)
	int updateEmployeeEmail(String employeeEmail,String employeeName, String employeeDesignation,String employeeAddress);

}
