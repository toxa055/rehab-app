package com.rehab.service;

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

    private static final EmployeeDto expected1 = new EmployeeDto();
    private static final EmployeeDto expected2 = new EmployeeDto();
    private static final EmployeeDto expected3 = new EmployeeDto();
    private static EmployeeDto newEmployee;
    private static UserDto newUser;

    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    public void before() {
        expected1.setId(3);
        expected1.setName("doctor1 name");
        expected1.setPosition("doctor1 position");
        expected1.setEmail("doctor1@doc.ru");

        expected2.setId(4);
        expected2.setName("doctor2 name");
        expected2.setPosition("doctor2 position");
        expected2.setEmail("doctor1@doc.ru");

        expected3.setId(5);
        expected3.setName("nurse1 name");
        expected3.setPosition("nurse1 position");
        expected3.setEmail("nurse1@nurse.ru");

        newUser = new UserDto();
        newUser.setName("new name");
        newUser.setPosition("new position");
        newUser.setEmail("new@user.ru");
        newUser.setPassword("new password");
        newUser.setConfirmPassword("new password");
        newUser.setRoles(Set.of(Role.DOCTOR));

        newEmployee = new EmployeeDto();
        newEmployee.setName(newUser.getName());
        newEmployee.setPosition(newUser.getPosition());
        newEmployee.setEmail(newUser.getEmail());
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
