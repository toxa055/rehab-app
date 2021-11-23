package com.rehab.service;

import com.rehab.data.EventTestData2;
import com.rehab.data.PrescriptionTestData;
import com.rehab.dto.EventDto;
import com.rehab.dto.PrescriptionDtoOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql", "classpath:test_init_events.sql"})
class EventService2Test {

    private static PrescriptionDtoOut p1;
    private static PrescriptionDtoOut p2;

    private static EventDto event1_1;
    private static EventDto event1_2;
    private static EventDto event1_3;
    private static EventDto event1_4;
    private static EventDto event1_5;

    private static EventDto event2_1;
    private static EventDto event2_2;
    private static EventDto event2_3;

    @Autowired
    private EventService eventService;

    @Autowired
    private PrescriptionService prescriptionService;

    @BeforeEach
    @WithUserDetails("doctor1@doc.ru")
    public void before1() {
        var saved1 = prescriptionService.save(PrescriptionTestData.getPrescriptionDto1());
        p1 = PrescriptionTestData.getPrescriptionDtoOut1();
        p1.setId(saved1.getId());
        event1_1 = EventTestData2.getEventDto1_1();
        event1_2 = EventTestData2.getEventDto1_2();
        event1_3 = EventTestData2.getEventDto1_3();
        event1_4 = EventTestData2.getEventDto1_4();
        event1_5 = EventTestData2.getEventDto1_5();
    }

    @BeforeEach
    @WithUserDetails("doctor2@doc.ru")
    public void before2() {
        var saved2 = prescriptionService.save(PrescriptionTestData.getPrescriptionDto2());
        p2 = PrescriptionTestData.getPrescriptionDtoOut2();
        p2.setId(saved2.getId());
        event2_1 = EventTestData2.getEventDto2_1();
        event2_2 = EventTestData2.getEventDto2_2();
        event2_3 = EventTestData2.getEventDto2_3();
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getByPrescriptionId() {
        var expected = List.of(event1_1, event1_2, event1_3, event1_4, event1_5);
        expected.forEach(e -> e.setPrescriptionId(p1.getId()));
        var actual = eventService.getByPrescriptionId(p1.getId(),
                PageRequest.of(0, 25)).getContent();
        for (int i = 0; i < actual.size(); i++) {
            expected.get(i).setId(actual.get(i).getId());
        }
        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails("doctor2@doc.ru")
    public void getAll() {
        var expected1 = new ArrayList<>(List.of(event1_1, event1_2, event1_3, event1_4, event1_5));
        var expected2 = List.of(event2_1, event2_2, event2_3);
        expected1.forEach(e -> e.setPrescriptionId(p1.getId()));
        expected2.forEach(e -> e.setPrescriptionId(p2.getId()));
        expected1.addAll(expected2);
        var actual = eventService.filter(null, null,
                false, true, PageRequest.of(0, 25)).getContent();
        for (int i = 0; i < expected1.size(); i++) {
            expected1.get(i).setId(actual.get(i).getId());
        }
        assertEquals(expected1, actual);
    }

    @Test
    @WithUserDetails("doctor1@doc.ru")
    public void getWithAllFilters() {
        var expected = List.of(event1_2, event1_3);
        expected.forEach(e -> e.setPrescriptionId(p1.getId()));
        var actual = eventService.filter(LocalDate.now().plusDays(1), p1.getPatientInsuranceNumber(),
                false, true, PageRequest.of(0, 25)).getContent();
        for (int i = 0; i < expected.size(); i++) {
            expected.get(i).setId(actual.get(i).getId());
        }
        assertEquals(expected, actual);
    }
}
