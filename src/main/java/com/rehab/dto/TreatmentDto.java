package com.rehab.dto;

import java.time.LocalDate;

public class TreatmentDto {
    private int id;
    private int patientId;
    private int patientInsuranceNumber;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private LocalDate date = LocalDate.now();
    private String diagnosis;
    private boolean isClosed = false;

    public TreatmentDto() {
    }

    public TreatmentDto(int id, int patientId, int patientInsuranceNumber, String patientName, int doctorId,
                        String doctorName, String diagnosis) {
        this.id = id;
        this.patientId = patientId;
        this.patientInsuranceNumber = patientInsuranceNumber;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getPatientInsuranceNumber() {
        return patientInsuranceNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setPatientInsuranceNumber(int patientInsuranceNumber) {
        this.patientInsuranceNumber = patientInsuranceNumber;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
