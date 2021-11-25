package com.rehab.service;

import com.rehab.repository.EmployeeCrudRepository;
import com.rehab.security.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeCrudRepository employeeCrudRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeCrudRepository employeeCrudRepository) {
        this.employeeCrudRepository = employeeCrudRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new EmployeeDetails(employeeCrudRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new NoSuchElementException("Employee with " + username + " not found.")));
    }
}
