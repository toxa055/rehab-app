package com.rehab.service;

import com.rehab.dto.EventDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.type.CureType;
import com.rehab.model.type.EventState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:test_init_db.sql", "classpath:test_pop_db.sql"})
class EventServiceTest {

    private static final String PERFORMED = "performed";
    private static EventDto expected1;
    private static EventDto expected2;
    private static EventDto expected3;
    private static EventDto expected4;
    private static EventDto expected5;

    @Autowired
    private EventService eventService;

    @BeforeEach
    public void before() {

//        VALUES (6, 0, '2021-11-20', '09:00', 'PLANNED', 1, '1 pill');
        expected1 = new EventDto();
        expected1.setId(10);
        expected1.setPatientId(6);
        expected1.setPatientInsuranceNumber(123400);
        expected1.setPatientName("test patient1");
        expected1.setPrescriptionId(0);
        expected1.setPlannedDate(LocalDate.parse("2021-11-20"));
        expected1.setPlannedTime(LocalTime.parse("09:00"));
        expected1.setEventState(EventState.PLANNED);
        expected1.setCureId(1);
        expected1.setCureName("test-cure1");
        expected1.setCureType(CureType.MEDICINE);
        expected1.setDose("1 pill");

//        VALUES (6, 5, 0, '2021-11-20', '17:00', 'PLANNED', 1, '1 pill');
        expected2 = new EventDto();
        expected2.setId(11);
        expected2.setPatientId(6);
        expected2.setPatientInsuranceNumber(123400);
        expected2.setPatientName("test patient1");
        expected2.setNurseId(5);
        expected2.setNurseName("nurse1 name");
        expected2.setPrescriptionId(0);
        expected2.setPlannedDate(LocalDate.parse("2021-11-20"));
        expected2.setPlannedTime(LocalTime.parse("17:00"));
        expected2.setEventState(EventState.PLANNED);
        expected2.setCureId(1);
        expected2.setCureName("test-cure1");
        expected2.setCureType(CureType.MEDICINE);
        expected2.setDose("1 pill");

//        VALUES (7, 5, 0, '2021-11-21', '17:00', 'PERFORMED', 1, '1 pill', '2021-11-21', '17:30');
        expected3 = new EventDto();
        expected3.setId(12);
        expected3.setPatientId(7);
        expected3.setPatientInsuranceNumber(567800);
        expected3.setPatientName("test patient2");
        expected3.setNurseId(5);
        expected3.setNurseName("nurse1 name");
        expected3.setPrescriptionId(0);
        expected3.setPlannedDate(LocalDate.parse("2021-11-21"));
        expected3.setPlannedTime(LocalTime.parse("17:00"));
        expected3.setEventState(EventState.PERFORMED);
        expected3.setCureId(1);
        expected3.setCureName("test-cure1");
        expected3.setCureType(CureType.MEDICINE);
        expected3.setDose("1 pill");
        expected3.setEndDate(LocalDate.parse("2021-11-21"));
        expected3.setEndTime(LocalTime.parse("17:30"));

//        VALUES (7, 0, '2021-11-21', '17:00', 'CANCELLED', 1, '1 pill', '2021-11-21', '15:30', 'cancelled comment');
        expected4 = new EventDto();
        expected4.setId(13);
        expected4.setPatientId(7);
        expected4.setPatientInsuranceNumber(567800);
        expected4.setPatientName("test patient2");
        expected4.setPrescriptionId(0);
        expected4.setPlannedDate(LocalDate.parse("2021-11-21"));
        expected4.setPlannedTime(LocalTime.parse("17:00"));
        expected4.setEventState(EventState.CANCELLED);
        expected4.setCureId(1);
        expected4.setCureName("test-cure1");
        expected4.setCureType(CureType.MEDICINE);
        expected4.setDose("1 pill");
        expected4.setEndDate(LocalDate.parse("2021-11-21"));
        expected4.setEndTime(LocalTime.parse("15:30"));
        expected4.setComment("cancelled comment");

//        VALUES (6, 14, 0, '2021-11-20', '09:00', 'PLANNED', 2);
        expected5 = new EventDto();
        expected5.setId(15);
        expected5.setPatientId(6);
        expected5.setPatientInsuranceNumber(123400);
        expected5.setPatientName("test patient1");
        expected5.setNurseId(14);
        expected5.setNurseName("nurse2 name");
        expected5.setPrescriptionId(0);
        expected5.setPlannedDate(LocalDate.parse("2021-11-20"));
        expected5.setPlannedTime(LocalTime.parse("09:00"));
        expected5.setEventState(EventState.PLANNED);
        expected5.setCureId(2);
        expected5.setCureName("test-cure2");
        expected5.setCureType(CureType.PROCEDURE);
    }

    @Test
    public void getById() {
        var actual = eventService.getById(expected1.getId());
        assertEquals(expected1, actual);
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void setNurse() {
        var set = eventService.setNurse(expected1.getId());
        expected1.setNurseId(5);
        expected1.setNurseName("nurse1 name");
        assertEquals(expected1, set);
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void setNurseWhenAlreadySet() {
        assertThrows(ApplicationException.class, () -> eventService.setNurse(expected3.getId()));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void setNurseWhenHasNotPlannedState() {
        assertThrows(ApplicationException.class, () -> eventService.setNurse(expected4.getId()));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void unSetNurse() {
        var unSet = eventService.unSetNurse(expected2.getId());
        expected2.setNurseId(0);
        expected2.setNurseName(null);
        assertEquals(expected2, unSet);
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void unSetNurseWhenHasNotNurse() {
        assertThrows(ApplicationException.class, () -> eventService.unSetNurse(expected1.getId()));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void unSetNurseWhenHasDifferentNurse() {
        assertThrows(ApplicationException.class, () -> eventService.unSetNurse(expected5.getId()));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void unSetNurseWhenHasNotPlannedState() {
        assertThrows(ApplicationException.class, () -> eventService.unSetNurse(expected3.getId()));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void perform() {
        var performed = eventService.changeState(expected2.getId(), PERFORMED, null);
        expected2.setEventState(EventState.PERFORMED);
        expected2.setEndDate(performed.getEndDate());
        expected2.setEndTime(performed.getEndTime());
        assertEquals(expected2, performed);
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void performWhenHasNotNurse() {
        assertThrows(ApplicationException.class, () -> eventService.changeState(expected1.getId(), PERFORMED, null));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void performWhenHasDifferentNurse() {
        assertThrows(ApplicationException.class, () -> eventService.changeState(expected5.getId(), PERFORMED, null));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void performWhenHasNotPlannedState() {
        assertThrows(ApplicationException.class, () -> eventService.changeState(expected3.getId(), PERFORMED, null));
    }

    @Test
    @WithUserDetails("nurse1@nurse.ru")
    public void cancel() {
        var cancelled = eventService.changeState(expected2.getId(), "cancelled", "cancel comment");
        expected2.setEventState(EventState.CANCELLED);
        expected2.setEndDate(cancelled.getEndDate());
        expected2.setEndTime(cancelled.getEndTime());
        expected2.setComment(cancelled.getComment());
        assertEquals(expected2, cancelled);
    }
}
