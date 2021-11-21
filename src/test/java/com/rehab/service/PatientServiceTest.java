package com.rehab.service;

import com.rehab.data.PatientTestData;
import com.rehab.dto.PatientDto;
import com.rehab.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql"})
class PatientServiceTest {

    private static PatientDto expected;

    @Autowired
    private PatientService patientService;

    @BeforeEach
    public void before() {
        expected = PatientTestData.getPatientDto1();
    }

    @Test
    public void getById() {
        var actual = patientService.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void getByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> patientService.getById(20));
    }

    @Test
    public void getByInsNum() {
        var expected = PatientTestData.getPatientDto2();
        var actual = patientService.getByInsuranceNumber(expected.getInsuranceNumber());
        assertEquals(expected, actual);
    }

    @Test
    public void getByInsNumNotFound() {
        assertThrows(NoSuchElementException.class, () -> patientService.getByInsuranceNumber(1000));
    }

    @Test
    public void save() {
        var newPatient = PatientTestData.getNewPatientDto();
        var saved = patientService.save(newPatient);
        newPatient.setId(saved.getId());
        assertEquals(newPatient, saved);
    }

    @Test
    public void saveWithExistingInsNum() {
        var newPatient = PatientTestData.getNewPatientDto();
        newPatient.setInsuranceNumber(expected.getInsuranceNumber());
        assertThrows(ApplicationException.class, () -> patientService.save(newPatient));
    }

    @Test
    public void update() {
        expected.setInsuranceNumber(556600);
        expected.setName("updated patient1");
        expected.setAddress("updated patient1 address");
        var updated = patientService.update(expected);
        var afterUpdate = patientService.getById(updated.getId());
        assertEquals(afterUpdate, updated);
    }

    @Test
    public void updateWithExistingInsNum() {
        var diff = PatientTestData.getPatientDto2();
        expected.setInsuranceNumber(diff.getInsuranceNumber());
        expected.setName("updated patient1");
        expected.setBirthDate(LocalDate.parse("1985-10-05"));
        assertThrows(ApplicationException.class, () -> patientService.update(expected));
    }
}
