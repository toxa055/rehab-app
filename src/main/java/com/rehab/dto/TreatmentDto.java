package com.rehab.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class TreatmentDto {
    private int id;
    private int patientId;
    @NotNull(message = "Insurance number cannot be empty")
    @Range(min = 1_000, max = 99_999_999, message = "Insurance number must contain from 4 to 8 digits")
    private Integer patientInsuranceNumber;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 8, max = 50, message = "Length must be from 8 to 50 symbols")
    private String patientName;
    private int doctorId;
    private String doctorName;
    private LocalDate date = LocalDate.now();
    @NotBlank(message = "Diagnosis cannot be empty")
    @Size(min = 3, max = 50, message = "Length must be from 3 to 50 symbols")
    private String diagnosis;
    private LocalDate closeDate;
    private boolean isClosed = false;

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public Integer getPatientInsuranceNumber() {
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

    public LocalDate getCloseDate() {
        return closeDate;
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

    public void setPatientInsuranceNumber(Integer patientInsuranceNumber) {
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

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentDto that = (TreatmentDto) o;
        return id == that.id
                && patientId == that.patientId
                && doctorId == that.doctorId
                && isClosed == that.isClosed
                && patientInsuranceNumber.equals(that.patientInsuranceNumber)
                && patientName.equals(that.patientName)
                && doctorName.equals(that.doctorName)
                && date.equals(that.date)
                && diagnosis.equals(that.diagnosis)
                && Objects.equals(closeDate, that.closeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, patientInsuranceNumber, patientName, doctorId, doctorName,
                date, diagnosis, closeDate, isClosed);
    }
}
