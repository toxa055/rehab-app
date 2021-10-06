package com.rehab.repository;

import com.rehab.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeCrudRepository extends JpaRepository<Employee, Integer> {

}
