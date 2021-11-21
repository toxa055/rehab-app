package com.rehab.data;

import com.rehab.dto.EmployeeDto;
import com.rehab.dto.UserDto;
import com.rehab.model.type.Role;

import java.util.Set;

public class EmployeeTestData {

    public static EmployeeDto getEmployeeDto1() {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(3);
        employeeDto.setName("doctor1 name");
        employeeDto.setPosition("doctor1 position");
        employeeDto.setEmail("doctor1@doc.ru");
        return employeeDto;
    }

    public static EmployeeDto getEmployeeDto2() {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(4);
        employeeDto.setName("doctor2 name");
        employeeDto.setPosition("doctor2 position");
        employeeDto.setEmail("doctor1@doc.ru");
        return employeeDto;
    }

    public static EmployeeDto getEmployeeDto3() {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(5);
        employeeDto.setName("nurse1 name");
        employeeDto.setPosition("nurse1 position");
        employeeDto.setEmail("nurse1@nurse.ru");
        return employeeDto;
    }

    public static EmployeeDto getEmployeeDto4() {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(14);
        employeeDto.setName("nurse2 name");
        employeeDto.setPosition("nurse2 position");
        employeeDto.setEmail("nurse2@nurse.ru");
        return employeeDto;
    }

    public static EmployeeDto getNewEmployeeDto() {
        var newUser = getNewUserDto();
        var employeeDto = new EmployeeDto();
        employeeDto.setName(newUser.getName());
        employeeDto.setPosition(newUser.getPosition());
        employeeDto.setEmail(newUser.getEmail());
        return employeeDto;
    }

    public static UserDto getNewUserDto() {
        var userDto = new UserDto();
        userDto.setName("new name");
        userDto.setPosition("new position");
        userDto.setEmail("new@user.ru");
        userDto.setPassword("new password");
        userDto.setConfirmPassword("new password");
        userDto.setRoles(Set.of(Role.DOCTOR));
        return userDto;
    }
}
