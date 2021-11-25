package com.rehab.dto;

import com.rehab.model.type.CureType;
import com.rehab.model.type.EventState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class EventDto {
    private int id;
    private int patientId;
    private int patientInsuranceNumber;
    private String patientName;
    private int nurseId;
    private String nurseName;
    private int prescriptionId;
    private LocalDate plannedDate;
    private LocalTime plannedTime;
    private EventState eventState;
    private int cureId;
    private String cureName;
    private CureType cureType;
    private String dose;
    private LocalDate endDate;
    private LocalTime endTime;
    private String comment;

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

    public int getNurseId() {
        return nurseId;
    }

    public String getNurseName() {
        return nurseName;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public LocalTime getPlannedTime() {
        return plannedTime;
    }

    public EventState getEventState() {
        return eventState;
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

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public void setPlannedTime(LocalTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    public void setEventState(EventState eventState) {
        this.eventState = eventState;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDto eventDto = (EventDto) o;
        return id == eventDto.id
                && patientId == eventDto.patientId
                && patientInsuranceNumber == eventDto.patientInsuranceNumber
                && nurseId == eventDto.nurseId
                && prescriptionId == eventDto.prescriptionId
                && cureId == eventDto.cureId
                && patientName.equals(eventDto.patientName)
                && Objects.equals(nurseName, eventDto.nurseName)
                && plannedDate.equals(eventDto.plannedDate)
                && plannedTime.equals(eventDto.plannedTime)
                && eventState == eventDto.eventState
                && cureName.equals(eventDto.cureName)
                && cureType == eventDto.cureType
                && dose.equals(eventDto.dose)
                && Objects.equals(endDate, eventDto.endDate)
                && Objects.equals(endTime, eventDto.endTime)
                && Objects.equals(comment, eventDto.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, patientInsuranceNumber, patientName, nurseId, nurseName, prescriptionId,
                plannedDate, plannedTime, eventState, cureId, cureName, cureType, dose, endDate, endTime, comment);
    }
}
