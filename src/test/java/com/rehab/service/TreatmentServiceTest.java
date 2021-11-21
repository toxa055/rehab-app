package com.rehab.service;

import com.rehab.dto.TreatmentDto;
import com.rehab.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql"})
class TreatmentServiceTest {

    private static final TreatmentDto expected1 = new TreatmentDto();
    private static final TreatmentDto expected2 = new TreatmentDto();
    private static TreatmentDto newTreatment;

    @Autowired
    private TreatmentService treatmentService;

    @BeforeEach
    public void before() {
        expected1.setId(8);
        expected1.setPatientId(6);
        expected1.setPatientInsuranceNumber(123400);
        expected1.setPatientName("test patient1");
        expected1.setDoctorId(3);
        expected1.setDoctorName("doctor1 name");
        expected1.setDiagnosis("test diagnosis1");

        expected2.setId(9);
        expected2.setPatientId(7);
        expected2.setPatientInsuranceNumber(567800);
        expected2.setPatientName("test patient2");
        expected2.setDoctorId(3);
        expected2.setDoctorName("doctor1 name");
        expected2.setDiagnosis("test diagnosis2");

        newTreatment = new TreatmentDto();
    }

    @Test
    public void getById() {
        var actual = treatmentService.getById(8);
        assertEquals(expected1, actual);
    }

    @Test
    public void getByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> treatmentService.getById(100));
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void save() {
        newTreatment.setPatientId(7);
        newTreatment.setPatientInsuranceNumber(567800);
        newTreatment.setPatientName("test patient2");
        newTreatment.setDoctorId(3);
        newTreatment.setDoctorName("doctor1 name");
        newTreatment.setDiagnosis("new diagnosis");
        var saved = treatmentService.save(newTreatment);
        newTreatment.setId(saved.getId());
        assertEquals(newTreatment, saved);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void saveWithPatientNotFound() {
        newTreatment.setPatientId(600);
        newTreatment.setPatientInsuranceNumber(567800);
        newTreatment.setPatientName("test patient2");
        newTreatment.setDoctorId(3);
        newTreatment.setDoctorName("doctor1 name");
        newTreatment.setDiagnosis("new diagnosis");
        assertThrows(NoSuchElementException.class, () -> treatmentService.save(newTreatment));
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void close() {
        var closed = treatmentService.close(expected1.getId());
        expected1.setCloseDate(LocalDate.now());
        expected1.setClosed(true);
        assertEquals(expected1, closed);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void closeWhenClosed() {
        expected1.setCloseDate(LocalDate.now());
        expected1.setClosed(true);
        treatmentService.save(expected1);
        assertThrows(ApplicationException.class, () -> treatmentService.close(expected1.getId()));
    }

    @Test
    @WithUserDetails("doctor2@doc.ru")
    public void closeWithDifferentDoctor() {
        assertThrows(ApplicationException.class, () -> treatmentService.close(expected2.getId()));
    }
}
