package com.rehab.service;

import com.rehab.repository.EmployeeCrudRepository;
import com.rehab.security.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service for EmployeeDetails. It contains logic to authenticate users.
 */
@Service
public class EmployeeDetailsService implements UserDetailsService {

    /**
     * EmployeeCrudRepository bean.
     */
    private final EmployeeCrudRepository employeeCrudRepository;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param employeeCrudRepository description of cureCrudRepository is in field declaration.
     */
    @Autowired
    public EmployeeDetailsService(EmployeeCrudRepository employeeCrudRepository) {
        this.employeeCrudRepository = employeeCrudRepository;
    }

    /**
     * Method finds employee by given username during user authentication.
     *
     * @param username employee email address.
     * @return instance of EmployeeDetails found by given username.
     * @throws UsernameNotFoundException if employee with given username not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new EmployeeDetails(employeeCrudRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new NoSuchElementException("Employee with " + username + " not found.")));
    }
}
