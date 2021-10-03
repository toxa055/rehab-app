package com.rehab.model;

import com.rehab.model.type.Role;

public class Treatment extends AbstractIdEntity {
    private Patient patient;
    private Employee doctor;
    private String diagnosis;
    private boolean isClosed = false;

    public Treatment() {
    }

    public Treatment(Integer id, Patient patient, Employee doctor, String diagnosis) {
        super(id);
        if (doctor.getRole() != Role.DOCTOR) {
            throw new IllegalArgumentException();
        }
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Employee doctor) {
        if (doctor.getRole() != Role.DOCTOR) {
            throw new IllegalArgumentException();
        }
        this.doctor = doctor;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
