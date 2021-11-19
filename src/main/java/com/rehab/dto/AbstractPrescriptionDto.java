package com.rehab.dto;

import com.rehab.model.type.CureType;
import com.rehab.model.type.TimeUnit;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class AbstractPrescriptionDto {
    private Integer id;
    private int patientId;
    private int patientInsuranceNumber;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private int treatmentId;
    private Integer cureId;
    private String cureName;
    private CureType cureType;
    private int patternId;
    private int patternCount;
    private TimeUnit patternUnit;
    private int periodId;
    @NotNull(message = "Count cannot be empty")
    @Range(min = 1, max = 90, message = "Period count must be from 1 to 90")
    private Integer periodCount;
    private TimeUnit periodUnit;
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
}
