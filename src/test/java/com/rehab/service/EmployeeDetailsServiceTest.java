package com.rehab.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeDetailsServiceTest {

    @Autowired
    private EmployeeDetailsService service;

    @Test
    public void loadByUserNameNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.loadUserByUsername("notfound@name.ru"));
    }
}
