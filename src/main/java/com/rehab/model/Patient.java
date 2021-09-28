package com.rehab.model;

public class Patient extends AbstractNamedEntity {
    private int insuranceNumber;
    private Doctor doctor;
    private PatientState patientState;
    private String diagnosis;

    public Patient() {
    }

    public Patient(int id, String name, int insuranceNumber, Doctor doctor, PatientState patientState, String diagnosis) {
        super(id, name);
        this.insuranceNumber = insuranceNumber;
        this.doctor = doctor;
        this.patientState = patientState;
        this.diagnosis = diagnosis;
    }

    public int getInsuranceNumber() {
        return insuranceNumber;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public PatientState getPatientState() {
        return patientState;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatientState(PatientState patientState) {
        this.patientState = patientState;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
