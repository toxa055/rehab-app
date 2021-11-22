package com.rehab.service;

import com.rehab.data.CureTestData;
import com.rehab.dto.CureDto;
import com.rehab.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql"})
class CureServiceTest {

    private static CureDto expected1;
    private static CureDto expected2;
    private static CureDto newCure;

    @Autowired
    private CureService cureService;

    @BeforeEach
    public void before() {
        expected1 = CureTestData.getCureDto1();
        expected2 = CureTestData.getCureDto2();
        newCure = CureTestData.getNewCureDto();
    }

    @Test
    public void getById() {
        var actual = cureService.getById(expected1.getId());
        assertEquals(expected1, actual);
    }

    @Test
    public void getByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> cureService.getById(20));
    }

    @Test
    public void getByName() {
        var actual = cureService.getByName(expected2.getName());
        assertEquals(expected2, actual);
    }

    @Test
    public void getByNameNotFound() {
        assertThrows(NoSuchElementException.class, () -> cureService.getByName("notFound"));
    }

    @Test
    public void save() {
        var savedCure = cureService.save(newCure);
        newCure.setId(savedCure.getId());
        assertEquals(newCure, savedCure);
    }

    @Test
    public void saveWithExistingName() {
        newCure.setName(expected2.getName());
        assertThrows(ApplicationException.class, () -> cureService.save(newCure));
    }

    @Test
    public void getAll() {
        var expected = List.of(expected1, expected2);
        var actual = cureService.getAll(PageRequest.of(0, 15)).getContent();
        assertEquals(expected, actual);
    }
}
