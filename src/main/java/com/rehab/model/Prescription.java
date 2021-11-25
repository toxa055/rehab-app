package com.rehab.model;

import com.rehab.model.type.Role;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity describing prescription for patient.
 * Prescription is created by doctor based on patient's treatment.
 * Patient can have several prescriptions at the same time within one treatment.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'prescriptions' in database.
 */
@Entity
@Table(name = "prescriptions")
public class Prescription extends AbstractIdEntity {

    /**
     * Doctor which prescribing the prescription.
     * It maps to column 'doctor_id' to table 'prescriptions' in database.
     * It connects to table 'employees' with 'doctor_id'.
     * Value cannot be null.
     *
     * @see Employee
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    /**
     * Patient which getting current prescription.
     * It maps to column 'patient_id' to table 'prescriptions' in database.
     * It connects to table 'patients' with 'patient_id'.
     * Value cannot be null.
     *
     * @see Patient
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /**
     * Treatment which current prescription belongs to.
     * It maps to column 'treatment_id' to table 'prescriptions' in database.
     * It connects to table 'treatments' with 'treatment_id'.
     * Value cannot be null.
     *
     * @see Treatment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    /**
     * Date when prescription was created.
     * It maps to column 'prescription_date' to table 'prescriptions' in database.
     * Default value is today, since prescription is created today.
     * Value cannot be null.
     */
    @Column(name = "prescription_date", nullable = false)
    private LocalDate date = LocalDate.now();

    /**
     * Cure which is used for patient's treating.
     * It maps to column 'cure_id' to table 'prescriptions' in database.
     * It connects to table 'cures' with 'cure_id'.
     * Value cannot be null.
     *
     * @see Treatment
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cure_id", nullable = false)
    private Cure cure;

    /**
     * Frequency of how often patient has to take medicines or to be treated with procedure.
     * It maps to column 'pattern_id' to table 'prescriptions' in database.
     * It connects to table 'patterns' with 'pattern_id'.
     * Value cannot be null.
     *
     * @see Pattern
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

    /**
     * Period of time, during of that patient has to take medicines or to be treated with procedure.
     * It maps to column 'period_id' to table 'prescriptions' in database.
     * It connects to table 'periods' with 'period_id'.
     * Value cannot be null.
     *
     * @see Period
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    /**
     * Particular amount of current cure.
     * It maps to column 'dose' to table 'prescriptions' in database.
     * Default value is 'According to instruction.'.
     * Value cannot be null.
     */
    @Column(name = "dose", nullable = false)
    private String dose = "According to instruction.";

    /**
     * Value defining whether prescription is active or not.
     * All events, depending on this prescription, have to be performed or cancelled.
     * Only doctor, created prescription, is able to close current prescription.
     * It maps to column 'active' to table 'prescriptions' in database.
     * Default value is true, since new prescription is active.
     * Value cannot be null.
     *
     * @see Event
     */
    @Column(name = "active", nullable = false)
    private boolean isActive = true;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Prescription() {
    }

    /**
     * Constructor to initialize following fields.
     * Only employee with role 'DOCTOR' can be a doctor for new prescription.
     *
     * @param id        description of id is in field declaration.
     * @param doctor    description of doctor is in field declaration.
     * @param patient   description of patient is in field declaration.
     * @param treatment description of treatment is in field declaration.
     * @param cure      description of cure is in field declaration.
     * @param pattern   description of pattern is in field declaration.
     * @param period    description of period is in field declaration.
     * @param dose      description of dose is in field declaration.
     * @throws IllegalArgumentException if employee does not have role 'DOCTOR'.
     */
    public Prescription(Integer id, Employee doctor, Patient patient, Treatment treatment, Cure cure, Pattern pattern,
                        Period period, String dose) {
        super(id);
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException("Cannot create new prescription with no role DOCTOR.");
        }
        this.patient = patient;
        this.treatment = treatment;
        this.cure = cure;
        this.pattern = pattern;
        this.period = period;
        this.dose = dose;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public LocalDate getDate() {
        return date;
    }

    public Cure getCure() {
        return cure;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Period getPeriod() {
        return period;
    }

    public String getDose() {
        return dose;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Method to set value for doctor.
     * Only employee with role 'DOCTOR' can be doctor for prescription.
     *
     * @param doctor incoming value for doctor.
     */
    public void setDoctor(Employee doctor) {
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException("Cannot set employee for prescription with no role DOCTOR.");
        }
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCure(Cure cure) {
        this.cure = cure;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
