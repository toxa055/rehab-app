package com.rehab.model;

import com.rehab.model.type.PatientState;

import java.util.ArrayList;
import java.util.List;

public class Patient extends AbstractNamedEntity {
    private int insuranceNumber;
    private String address;
    private PatientState patientState = PatientState.TREATING;
    private List<Treatment> treatments = new ArrayList<>();

    public Patient() {
    }

    public Patient(Integer id, String name, int insuranceNumber, String address) {
        super(id, name);
        this.address = address;
        this.insuranceNumber = insuranceNumber;
    }

    public int getInsuranceNumber() {
        return insuranceNumber;
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
