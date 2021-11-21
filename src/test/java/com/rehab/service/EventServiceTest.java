package com.rehab.service;

import com.rehab.data.EventTestData;
import com.rehab.dto.EventDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.type.EventState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

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
        expected1 = EventTestData.getEventDto1();

//        VALUES (6, 5, 0, '2021-11-20', '17:00', 'PLANNED', 1, '1 pill');
        expected2 = EventTestData.getEventDto2();

//        VALUES (7, 5, 0, '2021-11-21', '17:00', 'PERFORMED', 1, '1 pill', '2021-11-21', '17:30');
        expected3 = EventTestData.getEventDto3();

//        VALUES (7, 0, '2021-11-21', '17:00', 'CANCELLED', 1, '1 pill', '2021-11-21', '15:30', 'cancelled comment');
        expected4 = EventTestData.getEventDto4();

//        VALUES (6, 14, 0, '2021-11-20', '09:00', 'PLANNED', 2);
        expected5 = EventTestData.getEventDto5();
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
