package com.rehab.repository;

import com.rehab.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeCrudRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmailIgnoreCase(String email);
}
