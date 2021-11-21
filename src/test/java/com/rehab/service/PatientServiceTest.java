package com.rehab.service;

import com.rehab.dto.PatientDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.type.PatientState;
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

    private static final PatientDto expected1 = new PatientDto();
    private static final PatientDto expected2 = new PatientDto();
    private static PatientDto newPatient;

    @Autowired
    private PatientService patientService;

    @BeforeEach
    public void before() {
        expected1.setId(6);
        expected1.setInsuranceNumber(123400);
        expected1.setName("test patient1");
        expected1.setBirthDate(LocalDate.parse("1980-10-05"));
        expected1.setAddress("test patient1 address");
        expected1.setPatientState(PatientState.TREATING);

        expected2.setId(7);
        expected2.setInsuranceNumber(567800);
        expected2.setName("test patient2");
        expected2.setBirthDate(LocalDate.parse("1995-02-17"));
        expected2.setAddress("test patient2 address");
        expected2.setPatientState(PatientState.TREATING);

        newPatient = new PatientDto();
        newPatient.setInsuranceNumber(9900);
        newPatient.setName("new patient");
        newPatient.setBirthDate(LocalDate.parse("1960-05-30"));
        newPatient.setAddress("test new patient address");
    }

    @Test
    public void getById() {
        var actual = patientService.getById(expected1.getId());
        assertEquals(expected1, actual);
    }

    @Test
    public void getByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> patientService.getById(20));
    }

    @Test
    public void getByInsNum() {
        var actual = patientService.getByInsuranceNumber(expected2.getInsuranceNumber());
        assertEquals(expected2, actual);
    }

    @Test
    public void getByInsNumNotFound() {
        assertThrows(NoSuchElementException.class, () -> patientService.getByInsuranceNumber(1000));
    }

    @Test
    public void save() {
        var saved = patientService.save(newPatient);
        newPatient.setId(saved.getId());
        assertEquals(newPatient, saved);
    }

    @Test
    public void saveWithExistingInsNum() {
        newPatient.setInsuranceNumber(expected1.getInsuranceNumber());
        assertThrows(ApplicationException.class, () -> patientService.save(newPatient));
    }

    @Test
    public void update() {
        expected1.setInsuranceNumber(556600);
        expected1.setName("updated patient1");
        expected1.setAddress("updated patient1 address");
        var updated = patientService.update(expected1);
        var afterUpdate = patientService.getById(updated.getId());
        assertEquals(afterUpdate, updated);
    }

    @Test
    public void updateWithExistingInsNum() {
        expected1.setInsuranceNumber(expected2.getInsuranceNumber());
        expected1.setName("updated patient1");
        expected1.setBirthDate(LocalDate.parse("1985-10-05"));
        assertThrows(ApplicationException.class, () -> patientService.update(expected1));
    }
}
