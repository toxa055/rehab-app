package com.rehab.dto;

import java.time.LocalTime;
import java.util.Objects;

public class EventMessage {
    private Integer patientInsuranceNumber;
    private String patientName;
    private String nurseName;
    private LocalTime plannedTime;
    private String cureName;
    private String cureType;
    private String dose;

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

    public String getCureName() {
        return cureName;
    }

    public String getCureType() {
        return cureType;
    }

    public String getDose() {
        return dose;
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

    public void setCureName(String cureName) {
        this.cureName = cureName;
    }

    public void setCureType(String cureType) {
        this.cureType = cureType;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventMessage that = (EventMessage) o;
        return patientInsuranceNumber.equals(that.patientInsuranceNumber)
                && patientName.equals(that.patientName)
                && Objects.equals(nurseName, that.nurseName)
                && plannedTime.equals(that.plannedTime)
                && cureName.equals(that.cureName)
                && cureType.equals(that.cureType)
                && dose.equals(that.dose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientInsuranceNumber, patientName, nurseName, plannedTime, cureName, cureType, dose);
    }
}
