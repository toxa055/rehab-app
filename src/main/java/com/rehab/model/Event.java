package com.rehab.model;

import com.rehab.model.type.EventState;
import com.rehab.model.type.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity describing event which is supposed to perform by nurse.
 * It contains information about patient, what day/time and which cure they have to be given.
 * When doctor creates prescription, it generates events (using {@link com.rehab.util.EventUtil}),
 * depending on which time pattern and period were chosen.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'events' in database.
 */
@Entity
@Table(name = "events")
public class Event extends AbstractIdEntity {

    /**
     * Patient which has to get particular cure.
     * It maps to column 'patient_id' to table 'events' in database.
     * It connects to table 'patients' with 'patient_id'.
     * Value cannot be null.
     *
     * @see Patient
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /**
     * Nurse who is performer of current event.
     * It maps to column 'nurse_id' to table 'events' in database.
     * It connects to table 'employees' with 'nurse_id'.
     *
     * @see Employee
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nurse_id")
    private Employee nurse;

    /**
     * Prescription which current event was created based on.
     * It maps to column 'prescription_id' to table 'events' in database.
     * It connects to table 'prescription' with 'prescription_id'.
     * Value cannot be null.
     *
     * @see Prescription
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    /**
     * Planned date when current event has to be performed.
     * It maps to column 'planned_date' to table 'events' in database.
     * Value cannot be null.
     */
    @Column(name = "planned_date", nullable = false)
    private LocalDate plannedDate;

    /**
     * Planned time when current event has to be performed.
     * It maps to column 'planned_time' to table 'events' in database.
     * Value cannot be null.
     */
    @Column(name = "planned_time", nullable = false)
    private LocalTime plannedTime;

    /**
     * Event's current state.
     * It maps to column 'event_state' to table 'events' in database.
     * Value cannot be null.
     *
     * @see EventState
     */
    @Column(name = "event_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState eventState = EventState.PLANNED;

    /**
     * Cure which is used for patient's treating.
     * It maps to column 'cure_id' to table 'events' in database.
     * It connects to table 'cures' with 'cure_id'.
     * Value cannot be null.
     *
     * @see Cure
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cure_id", nullable = false)
    private Cure cure;

    /**
     * Particular amount of current cure.
     * It maps to column 'dose' to table 'events' in database.
     * Default value is 'According to instruction.'.
     * Value cannot be null.
     */
    @Column(name = "dose", nullable = false)
    private String dose = "According to instruction.";

    /**
     * Date when current event was performed or cancelled.
     * It maps to column 'end_date' to table 'events' in database.
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Time when current event was performed or cancelled.
     * It maps to column 'end_time' to table 'events' in database.
     */
    @Column(name = "end_time")
    private LocalTime endTime;

    /**
     * When nurse cancels event, they write reason why they do it.
     * It maps to column 'comment' to table 'events' in database.
     */
    @Column(name = "comment")
    private String comment;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Event() {
    }

    /**
     * Constructor to initialize following fields.
     * Only employee with role 'NURSE' can be a nurse for new event.
     *
     * @param id           description of id is in field declaration.
     * @param patient      description of patient is in field declaration.
     * @param nurse        description of nurse is in field declaration.
     * @param prescription description of prescription is in field declaration.
     * @param plannedDate  description of plannedDate is in field declaration.
     * @param plannedTime  description of plannedTime is in field declaration.
     * @param cure         description of cure is in field declaration.
     * @param dose         description of dose is in field declaration.
     * @param endDate      description of endDate is in field declaration.
     * @param endTime      description of endTime is in field declaration.
     * @param comment      description of comment is in field declaration.
     * @throws IllegalArgumentException if employee does not have role 'NURSE'.
     */
    public Event(Integer id, Patient patient, Employee nurse, Prescription prescription, LocalDate plannedDate,
                 LocalTime plannedTime, Cure cure, String dose, LocalDate endDate, LocalTime endTime, String comment) {
        super(id);
        if (!nurse.getRoles().contains(Role.NURSE)) {
            throw new IllegalArgumentException("Cannot create new event with no role NURSE.");
        }
        this.patient = patient;
        this.nurse = nurse;
        this.prescription = prescription;
        this.plannedDate = plannedDate;
        this.plannedTime = plannedTime;
        this.cure = cure;
        this.dose = dose;
        this.endDate = endDate;
        this.endTime = endTime;
        this.comment = comment;
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param patient description of patient is in field declaration.
     * @param cure    description of cure is in field declaration.
     * @param dose    description of dose is in field declaration.
     */
    public Event(Patient patient, Cure cure, String dose) {
        this.patient = patient;
        this.cure = cure;
        this.dose = dose;
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param patient     description of patient is in field declaration.
     * @param cure        description of cure is in field declaration.
     * @param dose        description of dose is in field declaration.
     * @param plannedDate description of plannedDate is in field declaration.
     */
    public Event(Patient patient, Cure cure, String dose, LocalDate plannedDate) {
        this(patient, cure, dose);
        this.plannedDate = plannedDate;
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param patient     description of patient is in field declaration.
     * @param cure        description of cure is in field declaration.
     * @param dose        description of dose is in field declaration.
     * @param plannedTime description of plannedTime is in field declaration.
     */
    public Event(Patient patient, Cure cure, String dose, LocalTime plannedTime) {
        this(patient, cure, dose);
        this.plannedTime = plannedTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Employee getNurse() {
        return nurse;
    }

    public Prescription getPrescription() {
        return prescription;
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

    /**
     * Method to set value for nurse.
     * Only employee with role 'NURSE' can be nurse for event.
     *
     * @param nurse incoming value for nurse.
     */
    public void setNurse(Employee nurse) {
        if ((nurse != null) && (!nurse.getRoles().contains(Role.NURSE))) {
            throw new IllegalArgumentException("Cannot set employee for event with no role NURSE.");
        }
        this.nurse = nurse;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
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
