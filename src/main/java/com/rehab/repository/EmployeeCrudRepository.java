package com.rehab.repository;

import com.rehab.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface which allows working with employees as entities in database.
 *
 * @see Employee
 */
@Repository
public interface EmployeeCrudRepository extends JpaRepository<Employee, Integer> {

    /**
     * Method to get only employee by given email address ignoring case.
     *
     * @param email particular employee email address, must not be null.
     * @return optional value of found employee.
     */
    Optional<Employee> findByEmailIgnoreCase(String email);
}
