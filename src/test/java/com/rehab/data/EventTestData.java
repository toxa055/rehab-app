package com.rehab.data;

import com.rehab.dto.CureDto;
import com.rehab.dto.EmployeeDto;
import com.rehab.dto.EventDto;
import com.rehab.dto.PatientDto;
import com.rehab.model.type.EventState;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventTestData {

    private static final PatientDto patient1 = PatientTestData.getPatientDto1();
    private static final PatientDto patient2 = PatientTestData.getPatientDto2();

    private static final EmployeeDto nurse1 = EmployeeTestData.getEmployeeDto3();
    private static final EmployeeDto nurse2 = EmployeeTestData.getEmployeeDto4();

    private static final CureDto cure1 = CureTestData.getCureDto1();
    private static final CureDto cure2 = CureTestData.getCureDto2();

    private static final LocalDate date1 = LocalDate.parse("2021-11-20");
    private static final LocalDate date2 = LocalDate.parse("2021-11-21");

    private static final LocalTime time1 = LocalTime.parse("09:00");
    private static final LocalTime time2 = LocalTime.parse("17:00");

    private static final String DOSE_1 = "1 pill";

    private EventTestData() {
    }

    public static EventDto getEventDto1() {
        var eventDto = new EventDto();
        eventDto.setId(10);
        eventDto.setPatientId(patient1.getId());
        eventDto.setPatientInsuranceNumber(patient1.getInsuranceNumber());
        eventDto.setPatientName(patient1.getName());
        eventDto.setPrescriptionId(0);
        eventDto.setPlannedDate(date1);
        eventDto.setPlannedTime(time1);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(cure1.getId());
        eventDto.setCureName(cure1.getName());
        eventDto.setCureType(cure1.getCureType());
        eventDto.setDose(DOSE_1);
        return eventDto;
    }

    public static EventDto getEventDto2() {
        var eventDto = new EventDto();
        eventDto.setId(11);
        eventDto.setPatientId(patient1.getId());
        eventDto.setPatientInsuranceNumber(patient1.getInsuranceNumber());
        eventDto.setPatientName(patient1.getName());
        eventDto.setNurseId(nurse1.getId());
        eventDto.setNurseName(nurse1.getName());
        eventDto.setPrescriptionId(0);
        eventDto.setPlannedDate(date1);
        eventDto.setPlannedTime(time2);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(cure1.getId());
        eventDto.setCureName(cure1.getName());
        eventDto.setCureType(cure1.getCureType());
        eventDto.setDose(DOSE_1);
        return eventDto;
    }

    public static EventDto getEventDto3() {
        var eventDto = new EventDto();
        eventDto.setId(12);
        eventDto.setPatientId(patient2.getId());
        eventDto.setPatientInsuranceNumber(patient2.getInsuranceNumber());
        eventDto.setPatientName(patient2.getName());
        eventDto.setNurseId(nurse1.getId());
        eventDto.setNurseName(nurse1.getName());
        eventDto.setPrescriptionId(0);
        eventDto.setPlannedDate(date2);
        eventDto.setPlannedTime(time2);
        eventDto.setEventState(EventState.PERFORMED);
        eventDto.setCureId(cure1.getId());
        eventDto.setCureName(cure1.getName());
        eventDto.setCureType(cure1.getCureType());
        eventDto.setDose(DOSE_1);
        eventDto.setEndDate(date2);
        eventDto.setEndTime(LocalTime.parse("17:30"));
        return eventDto;
    }

    public static EventDto getEventDto4() {
        var eventDto = new EventDto();
        eventDto.setId(13);
        eventDto.setPatientId(patient2.getId());
        eventDto.setPatientInsuranceNumber(patient2.getInsuranceNumber());
        eventDto.setPatientName(patient2.getName());
        eventDto.setPrescriptionId(0);
        eventDto.setPlannedDate(date2);
        eventDto.setPlannedTime(time2);
        eventDto.setEventState(EventState.CANCELLED);
        eventDto.setCureId(cure1.getId());
        eventDto.setCureName(cure1.getName());
        eventDto.setCureType(cure1.getCureType());
        eventDto.setDose(DOSE_1);
        eventDto.setEndDate(date2);
        eventDto.setEndTime(LocalTime.parse("15:30"));
        eventDto.setComment("cancelled comment");
        return eventDto;
    }

    public static EventDto getEventDto5() {
        var eventDto = new EventDto();
        eventDto.setId(15);
        eventDto.setPatientId(patient1.getId());
        eventDto.setPatientInsuranceNumber(patient1.getInsuranceNumber());
        eventDto.setPatientName(patient1.getName());
        eventDto.setNurseId(nurse2.getId());
        eventDto.setNurseName(nurse2.getName());
        eventDto.setPrescriptionId(0);
        eventDto.setPlannedDate(date1);
        eventDto.setPlannedTime(time1);
        eventDto.setEventState(EventState.PLANNED);
        eventDto.setCureId(cure2.getId());
        eventDto.setCureName(cure2.getName());
        eventDto.setCureType(cure2.getCureType());
        return eventDto;
    }
}
