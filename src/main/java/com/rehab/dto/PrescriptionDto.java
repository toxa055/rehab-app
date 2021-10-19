package com.rehab.dto;

import com.rehab.model.type.CureType;
import com.rehab.model.type.PatternUnit;
import com.rehab.model.type.TimeUnit;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionDto {
    private Integer id;
    private int patientId;
    private int patientInsuranceNumber;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private int treatmentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    private int cureId;
    private String cureName;
    private CureType cureType;
    private int patternId;
    private int patternCount;
    private TimeUnit patternUnit;
    private List<PatternUnit> patternUnits;
    private int periodId;
    private int periodCount;
    private TimeUnit periodUnit;
    private String dose;

    public PrescriptionDto() {
    }

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

    public int getCureId() {
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

    public List<PatternUnit> getPatternUnits() {
        return patternUnits;
    }

    public int getPeriodId() {
        return periodId;
    }

    public int getPeriodCount() {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCureId(int cureId) {
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

    public void setPatternUnits(List<PatternUnit> patternUnits) {
        this.patternUnits = patternUnits;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public void setPeriodCount(int periodCount) {
        this.periodCount = periodCount;
    }

    public void setPeriodUnit(TimeUnit periodUnit) {
        this.periodUnit = periodUnit;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
