package com.rehab.model;

import com.rehab.model.type.Role;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity describing patient's treatment.
 * Treatment is created by doctor when patient comes to rehabilitation hospital
 * in order to get medical help.
 * Patient can have several treatments either the same time or different times.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'treatments' in database.
 */
@Entity
@Table(name = "treatments")
public class Treatment extends AbstractIdEntity {

    /**
     * Patient which getting medical help.
     * It maps to column 'patient_id' to table 'treatments' in database.
     * It connects to table 'patients' with 'patient_id'.
     * Value cannot be null.
     *
     * @see Patient
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /**
     * Doctor which creating the treatment.
     * It maps to column 'doctor_id' to table 'treatments' in database.
     * It connects to table 'employees' with 'doctor_id'.
     * Value cannot be null.
     *
     * @see Employee
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    /**
     * Date when treatment was created.
     * It maps to column 'treatment_date' to table 'treatments' in database.
     * Default value is today, since treatment is created the same day
     * when patient comes to rehabilitation hospital, i.e. today.
     * Value cannot be null.
     */
    @Column(name = "treatment_date", nullable = false)
    private LocalDate date = LocalDate.now();

    /**
     * Diagnosis of disease which doctor makes after examining patient.
     * It maps to column 'diagnosis' to table 'treatments' in database.
     * Value cannot be null.
     */
    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    /**
     * Date when treatment was closed.
     * It maps to column 'close_date' to table 'treatments' in database.
     */
    @Column(name = "close_date")
    private LocalDate closeDate;

    /**
     * Value defining whether treatment is closed or not.
     * All prescriptions, depending on this treatment, have to be closed.
     * Only doctor, created treatment, is able to close current treatment.
     * It maps to column 'closed' to table 'treatments' in database.
     * Default value is false, since new treatment cannot be closed.
     * Value cannot be null.
     *
     * @see Prescription
     */
    @Column(name = "closed", nullable = false)
    private boolean isClosed = false;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Treatment() {
    }

    /**
     * Constructor to initialize following fields.
     * Only employee with role 'DOCTOR' can be a doctor for new treatment.
     *
     * @param id        description of id is in field declaration.
     * @param patient   description of patient is in field declaration.
     * @param doctor    description of doctor is in field declaration.
     * @param diagnosis description of diagnosis is in field declaration.
     * @throws IllegalArgumentException if employee does not have role 'DOCTOR'.
     */
    public Treatment(Integer id, Patient patient, Employee doctor, String diagnosis) {
        super(id);
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException("Cannot create new treatment with no role DOCTOR.");
        }
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Method to set value for doctor.
     * Only employee with role 'DOCTOR' can be doctor for treatment.
     *
     * @param doctor incoming value for doctor.
     */
    public void setDoctor(Employee doctor) {
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException("Cannot set employee for treatment with no role DOCTOR.");
        }
        this.doctor = doctor;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
