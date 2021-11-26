package com.rehab.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for {@link com.rehab.model.Treatment}, i.e. it's a view representation of Treatment model.
 * It's used for transferring data between services and controllers.
 */
public class TreatmentDto {

    /**
     * Treatment id.
     */
    private int id;

    /**
     * Patient id.
     */
    private int patientId;

    /**
     * Patient insurance number.
     */
    @NotNull(message = "Insurance number cannot be empty")
    @Range(min = 1_000, max = 99_999_999, message = "Insurance number must contain from 4 to 8 digits")
    private Integer patientInsuranceNumber;

    /**
     * Patient name, second name.
     */
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 8, max = 50, message = "Length must be from 8 to 50 symbols")
    private String patientName;

    /**
     * Doctor id.
     */
    private int doctorId;

    /**
     * Doctor name, second name.
     */
    private String doctorName;

    /**
     * Treatment date.
     */
    private LocalDate date = LocalDate.now();

    /**
     * Patient diagnosis.
     */
    @NotBlank(message = "Diagnosis cannot be empty")
    @Size(min = 3, max = 50, message = "Length must be from 3 to 50 symbols")
    private String diagnosis;

    /**
     * Treatment closed date.
     */
    private LocalDate closeDate;

    /**
     * Whether treatment closed.
     */
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


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
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

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, patientInsuranceNumber, patientName, doctorId, doctorName,
                date, diagnosis, closeDate, isClosed);
    }
}
