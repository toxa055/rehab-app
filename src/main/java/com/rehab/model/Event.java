package com.rehab.model;

import com.rehab.model.type.EventState;
import com.rehab.model.type.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
public class Event extends AbstractIdEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nurse_id")
    private Employee nurse;

    @Column(name = "planned_date", nullable = false)
    private LocalDate plannedDate;

    @Column(name = "planned_time", nullable = false)
    private LocalTime plannedTime;

    @Column(name = "event_state")
    @Enumerated(EnumType.STRING)
    private EventState eventState = EventState.PLANNED;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cure_id", nullable = false)
    private Cure cure;

    @Column(name = "dose", nullable = false)
    private String dose = "According to instruction.";

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "comment")
    private String comment;

    public Event() {
    }

    public Event(Integer id, Patient patient, Employee nurse, LocalDate plannedDate, LocalTime plannedTime,
                 Cure cure, String dose, LocalDate endDate, LocalTime endTime, String comment) {
        super(id);
        if (!nurse.getRoles().contains(Role.NURSE)) {
            throw new IllegalArgumentException();
        }
        this.patient = patient;
        this.nurse = nurse;
        this.plannedDate = plannedDate;
        this.plannedTime = plannedTime;
        this.cure = cure;
        this.dose = dose;
        this.endDate = endDate;
        this.endTime = endTime;
        this.comment = comment;
    }

    public Event(Patient patient, Cure cure, String dose, LocalDate plannedDate) {
        this.patient = patient;
        this.cure = cure;
        this.dose = dose;
        this.plannedDate = plannedDate;
    }

    public Event(Patient patient, Cure cure, String dose, LocalTime plannedTime) {
        this.patient = patient;
        this.cure = cure;
        this.dose = dose;
        this.plannedTime = plannedTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Employee getNurse() {
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

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setNurse(Employee nurse) {
        if (!nurse.getRoles().contains(Role.NURSE)) {
            throw new IllegalArgumentException();
        }
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
