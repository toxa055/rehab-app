package com.rehab.service;

import com.rehab.data.PrescriptionTestData;
import com.rehab.data.TreatmentTestData;
import com.rehab.dto.TreatmentDto;
import com.rehab.exception.ApplicationException;
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
class TreatmentServiceTest {

    private static TreatmentDto expected1;
    private static TreatmentDto expected2;
    private static TreatmentDto newTreatment;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private PrescriptionService prescriptionService;

    @BeforeEach
    public void before() {
        expected1 = TreatmentTestData.getTreatmentDto1();
        expected2 = TreatmentTestData.getTreatmentDto2();
        newTreatment = TreatmentTestData.getNewTreatmentDto1();
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
        var saved = treatmentService.save(newTreatment);
        newTreatment.setId(saved.getId());
        assertEquals(newTreatment, saved);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void saveWithPatientNotFound() {
        newTreatment.setPatientId(600);
        assertThrows(NoSuchElementException.class, () -> treatmentService.save(newTreatment));
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void saveWithDischargedState() {
        var newTreatment = TreatmentTestData.getNewTreatmentDto2();
        var saved = treatmentService.save(newTreatment);
        newTreatment.setId(saved.getId());
        assertEquals(newTreatment, saved);
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
    public void closeWhenHasActivePrescription() {
        var active = prescriptionService.save(PrescriptionTestData.getPrescriptionDto1());
        assertThrows(ApplicationException.class, () -> treatmentService.close(active.getTreatmentId()));
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

    @Test
    @WithUserDetails("doctor2@doc.ru")
    public void getAll() {
        var expected = List.of(expected1, expected2, TreatmentTestData.getTreatmentDto3());
        var actual = treatmentService.filter(null, null, false,
                false, PageRequest.of(0, 15)).getContent();
        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getWithAllFilters() {
        var expected = List.of(expected1);
        var actual = treatmentService.filter(LocalDate.now(), expected1.getPatientInsuranceNumber(),
                true, true, PageRequest.of(0, 15)).getContent();
        assertEquals(expected, actual);
    }
}
