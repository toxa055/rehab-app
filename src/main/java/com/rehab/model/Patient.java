package com.rehab.model;

import com.rehab.model.type.PatientState;

import java.util.ArrayList;
import java.util.List;

public class Patient extends AbstractIdEntity {
    private int insuranceNumber;
    private String name;
    private String address;
    private PatientState patientState = PatientState.TREATING;
    private List<Treatment> treatments = new ArrayList<>();

    public Patient() {
    }

    public Patient(Integer id, int insuranceNumber, String name, String address) {
        super(id);
        this.insuranceNumber = insuranceNumber;
        this.name = name;
        this.address = address;
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

    public List<Treatment> getTreatments() {
        return treatments;
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

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }
}
