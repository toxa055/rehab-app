package com.rehab.data;

import com.rehab.dto.PatientDto;
import com.rehab.model.type.PatientState;

import java.time.LocalDate;

public class PatientTestData {

    private PatientTestData() {
    }

    public static PatientDto getPatientDto1() {
        var patientDto = new PatientDto();
        patientDto.setId(6);
        patientDto.setInsuranceNumber(123400);
        patientDto.setName("test patient1");
        patientDto.setBirthDate(LocalDate.parse("1980-10-05"));
        patientDto.setAddress("test patient1 address");
        patientDto.setPatientState(PatientState.TREATING);
        return patientDto;
    }

    public static PatientDto getPatientDto2() {
        var patientDto = new PatientDto();
        patientDto.setId(7);
        patientDto.setInsuranceNumber(567800);
        patientDto.setName("test patient2");
        patientDto.setBirthDate(LocalDate.parse("1995-02-17"));
        patientDto.setAddress("test patient2 address");
        patientDto.setPatientState(PatientState.TREATING);
        return patientDto;
    }

    public static PatientDto getPatientDto3() {
        var patientDto = new PatientDto();
        patientDto.setId(16);
        patientDto.setInsuranceNumber(9988);
        patientDto.setName("test patient3");
        patientDto.setBirthDate(LocalDate.parse("1990-12-31"));
        patientDto.setAddress("test patient3 address");
        patientDto.setPatientState(PatientState.DISCHARGED);
        return patientDto;
    }

    public static PatientDto getNewPatientDto() {
        var patientDto = new PatientDto();
        patientDto.setInsuranceNumber(9900);
        patientDto.setName("new patient");
        patientDto.setBirthDate(LocalDate.parse("1960-05-30"));
        patientDto.setAddress("test new patient address");
        return patientDto;
    }
}
