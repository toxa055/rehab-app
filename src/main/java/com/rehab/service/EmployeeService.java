package com.rehab.service;

import com.rehab.dto.EmployeeDto;
import com.rehab.dto.UserDto;
import com.rehab.model.Employee;
import com.rehab.repository.EmployeeCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return toDto(employeeCrudRepository.findById(id).get());
    }

    public EmployeeDto getAuth() {
        return getById(SecurityUtil.getAuthEmployee().getId());
    }

    public EmployeeDto changePassword(UserDto userDto) {
        var employee = employeeCrudRepository.findById(userDto.getId()).get();
        if (!employee.getId().equals(userDto.getId())
                || !userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException();
        }
        employee.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return toDto(employeeCrudRepository.save(employee));
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
}
