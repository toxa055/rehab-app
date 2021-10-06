package com.rehab.repository;


import com.rehab.model.Employee;

import java.util.List;

public class EmployeeDataJpaRepository {

    private EmployeeCrudRepository repository;

    public Employee get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }
}
