package com.rehab.data;

import com.rehab.dto.EmployeeDto;
import com.rehab.dto.PatientDto;
import com.rehab.dto.TreatmentDto;

public class TreatmentTestData {

    private static final PatientDto patient1 = PatientTestData.getPatientDto1();
    private static final PatientDto patient2 = PatientTestData.getPatientDto2();
    private static final EmployeeDto doctor1 = EmployeeTestData.getEmployeeDto1();

    private TreatmentTestData() {
    }

    public static TreatmentDto getTreatmentDto1() {
        var treatmentDto = new TreatmentDto();
        treatmentDto.setId(8);
        treatmentDto.setPatientId(patient1.getId());
        treatmentDto.setPatientInsuranceNumber(patient1.getInsuranceNumber());
        treatmentDto.setPatientName(patient1.getName());
        treatmentDto.setDoctorId(doctor1.getId());
        treatmentDto.setDoctorName(doctor1.getName());
        treatmentDto.setDiagnosis("test diagnosis1");
        return treatmentDto;
    }

    public static TreatmentDto getTreatmentDto2() {
        var treatmentDto = new TreatmentDto();
        treatmentDto.setId(9);
        treatmentDto.setPatientId(patient2.getId());
        treatmentDto.setPatientInsuranceNumber(patient2.getInsuranceNumber());
        treatmentDto.setPatientName(patient2.getName());
        treatmentDto.setDoctorId(doctor1.getId());
        treatmentDto.setDoctorName(doctor1.getName());
        treatmentDto.setDiagnosis("test diagnosis2");
        return treatmentDto;
    }

    public static TreatmentDto getNewTreatmentDto() {
        var treatmentDto = new TreatmentDto();
        treatmentDto.setPatientId(patient2.getId());
        treatmentDto.setPatientInsuranceNumber(patient2.getInsuranceNumber());
        treatmentDto.setPatientName(patient2.getName());
        treatmentDto.setDoctorId(doctor1.getId());
        treatmentDto.setDoctorName(doctor1.getName());
        treatmentDto.setDiagnosis("new diagnosis");
        return treatmentDto;
    }
}
