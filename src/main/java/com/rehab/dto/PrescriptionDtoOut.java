package com.rehab.dto;

import com.rehab.model.type.CureType;
import com.rehab.model.type.TimeUnit;

import java.time.LocalDate;

public class PrescriptionDtoOut {
    private Integer id;
    private int patientId;
    private int patientInsuranceNumber;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private int treatmentId;
    private LocalDate date;
    private Integer cureId;
    private String cureName;
    private CureType cureType;
    private int patternId;
    private int patternCount;
    private TimeUnit patternUnit;
    private String patternUnits;
    private int periodId;
    private Integer periodCount;
    private TimeUnit periodUnit;
    private String dose;
    private boolean active;

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

    public LocalDate getDate() {
        return date;
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

    public String getPatternUnits() {
        return patternUnits;
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

    public boolean isActive() {
        return active;
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

    public void setDate(LocalDate date) {
        this.date = date;
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

    public void setPatternUnits(String patternUnits) {
        this.patternUnits = patternUnits;
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

    public void setActive(boolean active) {
        this.active = active;
    }
}
