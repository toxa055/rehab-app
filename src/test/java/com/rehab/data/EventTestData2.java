package com.rehab.data;

import com.rehab.dto.EventDto;
import com.rehab.dto.PrescriptionDtoOut;
import com.rehab.model.type.EventState;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventTestData2 {

    private static final LocalDate today = LocalDate.now();
    private static final LocalTime morning = LocalTime.parse("09:00");
    private static final LocalTime night = LocalTime.parse("21:00");

    private static final PrescriptionDtoOut p1 = PrescriptionTestData.getPrescriptionDtoOut1();
    private static final PrescriptionDtoOut p2 = PrescriptionTestData.getPrescriptionDtoOut2();

    private EventTestData2() {
    }

    public static EventDto getEventDto1_1() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p1.getPatientId());
        eventDto.setPatientInsuranceNumber(p1.getPatientInsuranceNumber());
        eventDto.setPatientName(p1.getPatientName());
        eventDto.setPlannedDate(today);
        eventDto.setPlannedTime(night);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p1.getCureId());
        eventDto.setCureName(p1.getCureName());
        eventDto.setCureType(p1.getCureType());
        eventDto.setDose(p1.getDose());
        return eventDto;
    }

    public static EventDto getEventDto1_2() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p1.getPatientId());
        eventDto.setPatientInsuranceNumber(p1.getPatientInsuranceNumber());
        eventDto.setPatientName(p1.getPatientName());
        eventDto.setPlannedDate(today.plusDays(1));
        eventDto.setPlannedTime(morning);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p1.getCureId());
        eventDto.setCureName(p1.getCureName());
        eventDto.setCureType(p1.getCureType());
        eventDto.setDose(p1.getDose());
        return eventDto;
    }

    public static EventDto getEventDto1_3() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p1.getPatientId());
        eventDto.setPatientInsuranceNumber(p1.getPatientInsuranceNumber());
        eventDto.setPatientName(p1.getPatientName());
        eventDto.setPlannedDate(today.plusDays(1));
        eventDto.setPlannedTime(night);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p1.getCureId());
        eventDto.setCureName(p1.getCureName());
        eventDto.setCureType(p1.getCureType());
        eventDto.setDose(p1.getDose());
        return eventDto;
    }

    public static EventDto getEventDto1_4() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p1.getPatientId());
        eventDto.setPatientInsuranceNumber(p1.getPatientInsuranceNumber());
        eventDto.setPatientName(p1.getPatientName());
        eventDto.setPlannedDate(today.plusDays(2));
        eventDto.setPlannedTime(morning);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p1.getCureId());
        eventDto.setCureName(p1.getCureName());
        eventDto.setCureType(p1.getCureType());
        eventDto.setDose(p1.getDose());
        return eventDto;
    }

    public static EventDto getEventDto1_5() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p1.getPatientId());
        eventDto.setPatientInsuranceNumber(p1.getPatientInsuranceNumber());
        eventDto.setPatientName(p1.getPatientName());
        eventDto.setPlannedDate(today.plusDays(2));
        eventDto.setPlannedTime(night);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p1.getCureId());
        eventDto.setCureName(p1.getCureName());
        eventDto.setCureType(p1.getCureType());
        eventDto.setDose(p1.getDose());
        return eventDto;
    }

    public static EventDto getEventDto2_1() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p2.getPatientId());
        eventDto.setPatientInsuranceNumber(p2.getPatientInsuranceNumber());
        eventDto.setPatientName(p2.getPatientName());
        eventDto.setPlannedDate(today.plusDays(1));
        eventDto.setPlannedTime(morning);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p2.getCureId());
        eventDto.setCureName(p2.getCureName());
        eventDto.setCureType(p2.getCureType());
        eventDto.setDose(p2.getDose());
        return eventDto;
    }

    public static EventDto getEventDto2_2() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p2.getPatientId());
        eventDto.setPatientInsuranceNumber(p2.getPatientInsuranceNumber());
        eventDto.setPatientName(p2.getPatientName());
        eventDto.setPlannedDate(today.plusDays(3));
        eventDto.setPlannedTime(morning);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p2.getCureId());
        eventDto.setCureName(p2.getCureName());
        eventDto.setCureType(p2.getCureType());
        eventDto.setDose(p2.getDose());
        return eventDto;
    }

    public static EventDto getEventDto2_3() {
        var eventDto = new EventDto();
        eventDto.setPatientId(p2.getPatientId());
        eventDto.setPatientInsuranceNumber(p2.getPatientInsuranceNumber());
        eventDto.setPatientName(p2.getPatientName());
        eventDto.setPlannedDate(today.plusDays(6));
        eventDto.setPlannedTime(morning);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(p2.getCureId());
        eventDto.setCureName(p2.getCureName());
        eventDto.setCureType(p2.getCureType());
        eventDto.setDose(p2.getDose());
        return eventDto;
    }
}
