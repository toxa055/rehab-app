package com.rehab.service;

import com.rehab.data.EmployeeTestData;
import com.rehab.dto.EmployeeDto;
import com.rehab.dto.UserDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.type.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql"})
class EmployeeServiceTest {

    private static EmployeeDto expected1;
    //private static EmployeeDto expected2;
    private static EmployeeDto expected3;
    private static UserDto newUser;

    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    public void before() {
        expected1 = EmployeeTestData.getEmployeeDto1();
        //expected2 = EmployeeTestData.getEmployeeDto2();
        expected3 = EmployeeTestData.getEmployeeDto3();
        newUser = EmployeeTestData.getNewUserDto();
    }

    @Test
    public void getById() {
        var actual = employeeService.getById(expected1.getId());
        assertEquals(expected1, actual);
    }

    @Test
    public void getByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> employeeService.getById(20));
    }

    @Test
    public void save() {
        var actual = employeeService.save(newUser);
        var newEmployee = EmployeeTestData.getNewEmployeeDto();
        newEmployee.setId(actual.getId());
        assertEquals(newEmployee, actual);
    }

    @Test
    public void saveWithExistingEmail() {
        newUser.setEmail(expected3.getEmail());
        assertThrows(ApplicationException.class, () -> employeeService.save(newUser));
    }

    @Test
    public void changePassword() {
        var forUpdate = new UserDto();
        forUpdate.setId(expected1.getId());
        forUpdate.setName(expected1.getName());
        forUpdate.setPosition(expected1.getPosition());
        forUpdate.setEmail(expected1.getEmail());
        forUpdate.setPassword("changed password");
        forUpdate.setConfirmPassword("changed password");
        forUpdate.setRoles(Set.of(Role.ADMIN, Role.DOCTOR));
        var updated = employeeService.changePassword(forUpdate);
        var afterUpdate = employeeService.getById(updated.getId());
        assertEquals(afterUpdate, updated);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getAuth() {
        var auth = employeeService.getAuth();
        assertEquals(expected1, auth);
    }
}
