package com.rehab.service;

import com.rehab.config.BeansConfig;
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

/**
 * Service class for Employee. It operates with Employee, EmployeeDto, UserDto
 * and contains methods that are considered as business logic.
 */
@Service
public class EmployeeService {

    /**
     * EmployeeCrudRepository bean.
     */
    private final EmployeeCrudRepository employeeCrudRepository;

    /**
     * PasswordEncoder bean.
     *
     * @see com.rehab.config.SecurityConfig
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * ModelMapper bean.
     *
     * @see BeansConfig#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param employeeCrudRepository description of cureCrudRepository is in field declaration.
     * @param passwordEncoder        description of passwordEncoder is in field declaration.
     * @param modelMapper            description of modelMapper is in field declaration.
     */
    @Autowired
    public EmployeeService(EmployeeCrudRepository employeeCrudRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.employeeCrudRepository = employeeCrudRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Method returns only employeeDto by given employee id.
     *
     * @param id employee id.
     * @return found employee mapped to employeeDto.
     */
    public EmployeeDto getById(int id) {
        return toDto(getEmployeeById(id));
    }

    /**
     * Method returns authenticated employeeDto.
     *
     * @return authenticated employee mapped to employeeDto.
     */
    public EmployeeDto getAuth() {
        return getById(SecurityUtil.getAuthEmployee().getId());
    }

    /**
     * Method maps given userDto to employee and saves it.
     * It's impossible to save new employee with email, if another employee has the same one.
     *
     * @param userDto that will be saved as employee.
     * @return saved employee mapped to employeeDto.
     */
    public EmployeeDto save(UserDto userDto) {
        var userDtoEmail = userDto.getEmail();
        if (employeeCrudRepository.findByEmailIgnoreCase(userDtoEmail).isPresent()) {
            throw new ApplicationException("Employee with " + userDtoEmail + " already exists.");
        }
        var employeeFromUserDto = toEntity(userDto);
        employeeFromUserDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return toDto(employeeCrudRepository.save(employeeFromUserDto));
    }

    /**
     * Method changes password for employee.
     *
     * @param userDto stores new password for employee.
     * @return employee, that password was changed, mapped to employeeDto.
     */
    @Transactional
    public EmployeeDto changePassword(UserDto userDto) {
        var employee = getEmployeeById(userDto.getId());
        employee.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return toDto(employeeCrudRepository.save(employee));
    }

    /**
     * Method returns page of all employees mapped to employeeDto.
     *
     * @param pageable interface that provides pagination.
     * @return page of all employees mapped to employeeDto.
     */
    public Page<EmployeeDto> getAll(Pageable pageable) {
        return employeeCrudRepository.findAll(pageable).map(this::toDto);
    }

    /**
     * Method returns only employee by given employee id.
     *
     * @param id employee id.
     * @return found employee.
     */
    private Employee getEmployeeById(int id) {
        return employeeCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Employee with id " + id + " not found."));
    }

    /**
     * Method maps (converts) given object of Employee class to object of EmployeeDto class.
     *
     * @param employee object to map from Employee to EmployeeDto.
     * @return mapped instance of EmployeeDto class.
     */
    private EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    /**
     * Method maps (converts) given object of UserDto class to object of Employee class.
     *
     * @param userDto object to map from UserDto to Employee.
     * @return mapped instance of Employee class.
     */
    private Employee toEntity(UserDto userDto) {
        return modelMapper.map(userDto, Employee.class);
    }
}
