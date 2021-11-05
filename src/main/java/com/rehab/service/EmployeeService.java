package com.rehab.service;

import com.rehab.dto.EmployeeDto;
import com.rehab.dto.UserDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Employee;
import com.rehab.repository.EmployeeCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    private final EmployeeCrudRepository employeeCrudRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeCrudRepository employeeCrudRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.employeeCrudRepository = employeeCrudRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto getById(int id) {
        return toDto(employeeCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id " + id + " not found.")));
    }

    public EmployeeDto getAuth() {
        return getById(SecurityUtil.getAuthEmployee().getId());
    }

    public EmployeeDto save(UserDto userDto) {
        var userDtoEmail = userDto.getEmail();
        if (employeeCrudRepository.findByEmailIgnoreCase(userDtoEmail).isPresent()) {
            throw new ApplicationException("Employee with " + userDtoEmail + " already exists.");
        }
        var employeeFromUserDto = toEntity(userDto);
        employeeFromUserDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return toDto(employeeCrudRepository.save(employeeFromUserDto));
    }

    @Transactional
    public EmployeeDto changePassword(UserDto userDto) {
        var userDtoId = userDto.getId();
        var employee = employeeCrudRepository.findById(userDtoId).orElseThrow(() ->
                new NoSuchElementException("Employee with id " + userDtoId + " not found."));
        employee.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return toDto(employeeCrudRepository.save(employee));
    }

    public Page<EmployeeDto> getAll(Pageable pageable) {
        return employeeCrudRepository.findAll(pageable).map(this::toDto);
    }

    private EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee toEntity(UserDto userDto) {
        return modelMapper.map(userDto, Employee.class);
    }
}
