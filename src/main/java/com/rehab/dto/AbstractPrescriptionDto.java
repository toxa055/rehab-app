package com.rehab.dto;

import com.rehab.model.type.CureType;
import com.rehab.model.type.TimeUnit;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Base abstract class for Data Transfer Object for {@link com.rehab.model.Prescription}.
 * It contains the majority of Prescription model data.
 */
public abstract class AbstractPrescriptionDto {

    /**
     * Prescription id.
     */
    private Integer id;

    /**
     * Patient id.
     */
    private int patientId;

    /**
     * Patient insurance number.
     */
    private int patientInsuranceNumber;

    /**
     * Patient name, second name.
     */
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
     * Treatment id.
     */
    private int treatmentId;

    /**
     * Cure id.
     */
    private Integer cureId;

    /**
     * Cure name.
     */
    private String cureName;

    /**
     * Cure type.
     */
    private CureType cureType;

    /**
     * Pattern id.
     */
    private int patternId;

    /**
     * Pattern count.
     */
    private int patternCount;

    /**
     * Pattern time unit.
     */
    private TimeUnit patternUnit;

    /**
     * Period id.
     */
    private int periodId;

    /**
     * Period count.
     */
    @NotNull(message = "Count cannot be empty")
    @Range(min = 1, max = 90, message = "Period count must be from 1 to 90")
    private Integer periodCount;

    /**
     * Period time unit.
     */
    private TimeUnit periodUnit;

    /**
     * Cure dose.
     */
    @NotBlank(message = "Dose cannot be empty")
    @Size(min = 3, max = 30, message = "Length must be from 3 to 30 symbols")
    private String dose;

    public Integer getId() {
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

    public int getTreatmentId() {
        return treatmentId;
    }

    public Integer getCureId() {
        return cureId;
    }

    public String getCureName() {
        return cureName;
    }

    public CureType getCureType() {
        return cureType;
    }

    public int getPatternId() {
        return patternId;
    }

    public int getPatternCount() {
        return patternCount;
    }

    public TimeUnit getPatternUnit() {
        return patternUnit;
    }

    public int getPeriodId() {
        return periodId;
    }

    public Integer getPeriodCount() {
        return periodCount;
    }

    public TimeUnit getPeriodUnit() {
        return periodUnit;
    }

    public String getDose() {
        return dose;
    }

    public void setId(Integer id) {
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

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public void setCureId(Integer cureId) {
        this.cureId = cureId;
    }

    public void setCureName(String cureName) {
        this.cureName = cureName;
    }

    public void setCureType(CureType cureType) {
        this.cureType = cureType;
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public void setPatternCount(int patternCount) {
        this.patternCount = patternCount;
    }

    public void setPatternUnit(TimeUnit patternUnit) {
        this.patternUnit = patternUnit;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public void setPeriodCount(Integer periodCount) {
        this.periodCount = periodCount;
    }

    public void setPeriodUnit(TimeUnit periodUnit) {
        this.periodUnit = periodUnit;
    }

    public void setDose(String dose) {
        this.dose = dose;
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
        AbstractPrescriptionDto that = (AbstractPrescriptionDto) o;
        return patientId == that.patientId
                && patientInsuranceNumber == that.patientInsuranceNumber
                && doctorId == that.doctorId
                && treatmentId == that.treatmentId
                && patternId == that.patternId
                && patternCount == that.patternCount
                && periodId == that.periodId
                && id.equals(that.id)
                && patientName.equals(that.patientName)
                && doctorName.equals(that.doctorName)
                && cureId.equals(that.cureId)
                && cureName.equals(that.cureName)
                && cureType == that.cureType
                && patternUnit == that.patternUnit
                && periodCount.equals(that.periodCount)
                && periodUnit == that.periodUnit
                && dose.equals(that.dose);
    }

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, patientInsuranceNumber, patientName, doctorId, doctorName,
                treatmentId, cureId, cureName, cureType, patternId, patternCount, patternUnit, periodId,
                periodCount, periodUnit, dose);
    }
}
