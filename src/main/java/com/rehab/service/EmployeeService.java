package com.rehab.service;

import com.rehab.dto.EmployeeDto;
import com.rehab.model.Employee;
import com.rehab.repository.EmployeeCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeCrudRepository employeeCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeCrudRepository employeeCrudRepository, ModelMapper modelMapper) {
        this.employeeCrudRepository = employeeCrudRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto getById(int id) {
        return toDto(employeeCrudRepository.findById(id).get());
    }

    public EmployeeDto getAuth() {
        return getById(SecurityUtil.getAuthEmployee().getId());
    }

    public List<EmployeeDto> getAll() {
        return employeeCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee toEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

}
