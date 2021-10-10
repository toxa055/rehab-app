package com.rehab.dto;

import com.rehab.model.type.PatientState;

public class PatientDto {
    private int id;
    private int insuranceNumber;
    private String name;
    private String address;
    private PatientState patientState = PatientState.TREATING;

    public PatientDto() {
    }

    public int getId() {
        return id;
    }

    public int getInsuranceNumber() {
        return insuranceNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public PatientState getPatientState() {
        return patientState;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPatientState(PatientState patientState) {
        this.patientState = patientState;
    }
}
