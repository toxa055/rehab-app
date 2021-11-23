package com.rehab.service;

import com.rehab.data.PrescriptionTestData;
import com.rehab.dto.PrescriptionDtoOut;
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
class PrescriptionServiceTest {

    private static PrescriptionDtoOut expected1;
    private static PrescriptionDtoOut expected2;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private EventService eventService;

    @BeforeEach
    public void before1() {
        var saved1 = prescriptionService.save(PrescriptionTestData.getPrescriptionDto1());
        expected1 = PrescriptionTestData.getPrescriptionDtoOut1();
        expected1.setId(saved1.getId());
    }

    @BeforeEach
    @WithUserDetails("doctor2@doc.ru")
    public void before2() {
        var saved2 = prescriptionService.save(PrescriptionTestData.getPrescriptionDto2());
        expected2 = PrescriptionTestData.getPrescriptionDtoOut2();
        expected2.setId(saved2.getId());
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getDtoOutById() {
        var actual = prescriptionService.getPrescriptionDtoOutById(expected1.getId());
        assertEquals(expected1, actual);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getDtoOutByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> prescriptionService.getPrescriptionDtoOutById(100));
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getDtoById() {
        var expected = PrescriptionTestData.getPrescriptionDto1();
        expected.setId(expected1.getId());
        var actual = prescriptionService.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getByTreatmentId() {
        var expected = List.of(expected1);
        var actual = prescriptionService.getByTreatmentId(expected1.getTreatmentId(),
                PageRequest.of(0, 15)).getContent();
        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void cancel() {
        var cancelled = prescriptionService.cancel(expected2.getId());
        expected2.setActive(false);
        assertEquals(expected2, cancelled);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void cancelWhenAlreadyCancelled() {
        var cancelled = prescriptionService.cancel(expected2.getId());
        assertThrows(ApplicationException.class, () -> prescriptionService.cancel(cancelled.getId()));
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void cancelWithDifferentDoctor() {
        assertThrows(ApplicationException.class, () -> prescriptionService.cancel(expected1.getId()));
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void update() {
        var forUpdate = PrescriptionTestData.getPrescriptionDto3();
        forUpdate.setId(expected1.getId());
        var actual = prescriptionService.update(forUpdate);
        var expected = PrescriptionTestData.getPrescriptionDtoOut3();
        expected.setId(actual.getId());
        expected.setPeriodId(actual.getPeriodId());
        expected.setPatternId(actual.getPatternId());
        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getAll() {
        var expected = List.of(expected1, expected2);
        var actual = prescriptionService.filter(null, null,
                false, false, PageRequest.of(0, 15)).getContent();
        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails("doctor2@doc.ru")
    public void getWithAllFilters() {
        var expected = List.of(expected2);
        var actual = prescriptionService.filter(LocalDate.now(),
                        expected2.getPatientInsuranceNumber()
                        , true, true, PageRequest.of(0, 15))
                .getContent();
        assertEquals(expected, actual);
    }
}
