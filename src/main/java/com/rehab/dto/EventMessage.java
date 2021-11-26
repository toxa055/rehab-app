package com.rehab.dto;

import com.rehab.controller.RestEventController;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Data Transfer Object for {@link com.rehab.model.Event}, i.e. it's a simplified view representation of Event model.
 * It's used for transferring data between services and controllers.
 * It's used for getting information about today planned events from another application through REST.
 *
 * @see RestEventController#getTodayPlannedEvents()
 */
public class EventMessage {

    /**
     * Patient insurance number.
     */
    private Integer patientInsuranceNumber;

    /**
     * Patient name, second name.
     */
    private String patientName;

    /**
     * Nurse name, second name.
     */
    private String nurseName;

    /**
     * Event planned date.
     */
    private LocalTime plannedTime;

    /**
     * Cure name.
     */
    private String cureName;

    /**
     * Cure type.
     */
    private String cureType;

    /**
     * Cure dose.
     */
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
        EventMessage that = (EventMessage) o;
        return patientInsuranceNumber.equals(that.patientInsuranceNumber)
                && patientName.equals(that.patientName)
                && Objects.equals(nurseName, that.nurseName)
                && plannedTime.equals(that.plannedTime)
                && cureName.equals(that.cureName)
                && cureType.equals(that.cureType)
                && dose.equals(that.dose);
    }

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(patientInsuranceNumber, patientName, nurseName, plannedTime, cureName, cureType, dose);
    }
}
