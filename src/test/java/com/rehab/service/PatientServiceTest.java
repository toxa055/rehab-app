package com.rehab.service;

import com.rehab.data.PatientTestData;
import com.rehab.data.TreatmentTestData;
import com.rehab.dto.PatientDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.type.PatientState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql"})
class PatientServiceTest {

    private static PatientDto expected;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TreatmentService treatmentService;

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

    @Test
    public void updateWithNotExistingInsNum() {
        expected.setName("updated patient1");
        expected.setAddress("updated patient1 address");
        var updated = patientService.update(expected);
        var afterUpdate = patientService.getById(updated.getId());
        assertEquals(afterUpdate, updated);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void discharge() {
        var saved = treatmentService.save(TreatmentTestData.getNewTreatmentDto2());
        treatmentService.close(saved.getId());
        var expected = PatientTestData.getPatientDto3();
        var discharged = patientService.discharge(expected.getId());
        expected.setPatientState(PatientState.DISCHARGED);
        assertEquals(expected, discharged);
    }

    @Test
    public void dischargeWhenAlreadyDischarged() {
        var discharged = PatientTestData.getPatientDto3();
        assertThrows(ApplicationException.class, () -> patientService.discharge(discharged.getId()));
    }

    @Test
    public void dischargeWhenHasOpenTreatments() {
        assertThrows(ApplicationException.class, () -> patientService.discharge(expected.getId()));
    }

    @Test
    public void getAll() {
        var expected2 = PatientTestData.getPatientDto2();
        var expected3 = PatientTestData.getPatientDto3();
        var expectedList = List.of(expected, expected2, expected3);
        var actual = patientService.filter(null, null, false,
                PageRequest.of(0, 15)).getContent();
        assertEquals(expectedList, actual);
    }

    @Test
    public void getWithNameOnlyTreating() {
        var expectedList = List.of(expected, PatientTestData.getPatientDto2());
        var actual = patientService.filter(null, "pat", true,
                PageRequest.of(0, 15)).getContent();
        assertEquals(expectedList, actual);
    }
}
