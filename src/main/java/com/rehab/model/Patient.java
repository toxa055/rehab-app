package com.rehab.model;

import com.rehab.model.type.PatientState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient extends AbstractIdEntity {

    @Column(name = "insurance_number", nullable = false, unique = true)
    private int insuranceNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "patient_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PatientState patientState = PatientState.TREATING;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
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
