package com.rehab.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event extends AbstractIdEntity {
    private Patient patient;
    private Nurse nurse;
    private LocalDate plannedDate;
    private LocalTime plannedTime;
    private EventState eventState = EventState.PLANNED;
    private Cure cure;
    private LocalDate endDate;
    private LocalTime endTime;
    private String comment;

    public Event() {
    }

    public Event(Integer id, Patient patient, Nurse nurse, LocalDate plannedDate, LocalTime plannedTime,
                 EventState eventState, Cure cure, LocalDate endDate, LocalTime endTime, String comment) {
        super(id);
        this.patient = patient;
        this.nurse = nurse;
        this.plannedDate = plannedDate;
        this.plannedTime = plannedTime;
        this.eventState = eventState;
        this.cure = cure;
        this.endDate = endDate;
        this.endTime = endTime;
        this.comment = comment;
    }

    public Patient getPatient() {
        return patient;
    }

    public Nurse getNurse() {
        return nurse;
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

    public Cure getCure() {
        return cure;
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

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
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

    public void setCure(Cure cure) {
        this.cure = cure;
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
