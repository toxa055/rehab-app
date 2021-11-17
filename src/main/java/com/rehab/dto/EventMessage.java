package com.rehab.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventMessage {
    private Integer patientInsuranceNumber;
    private String patientName;
    private String nurseName;
    private LocalTime plannedTime;
    private String eventState;
    private String cureName;
    private String cureType;
    private String dose;
    private LocalDate endDate;
    private LocalTime endTime;
    private String comment;

    public Integer getPatientInsuranceNumber() {
        return patientInsuranceNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public LocalTime getPlannedTime() {
        return plannedTime;
    }

    public String getEventState() {
        return eventState;
    }

    public String getCureName() {
        return cureName;
    }

    public String getCureType() {
        return cureType;
    }

    public String getDose() {
        return dose;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setPatientInsuranceNumber(Integer patientInsuranceNumber) {
        this.patientInsuranceNumber = patientInsuranceNumber;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public void setPlannedTime(LocalTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public void setCureName(String cureName) {
        this.cureName = cureName;
    }

    public void setCureType(String cureType) {
        this.cureType = cureType;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
