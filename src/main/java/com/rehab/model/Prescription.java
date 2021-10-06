package com.rehab.model;

import com.rehab.model.type.Role;

import javax.persistence.*;

@Entity
@Table(name = "prescriptions")
public class Prescription extends AbstractIdEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cure_id", nullable = false)
    private Cure cure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @Column(name = "dose", nullable = false)
    private String dose = "According to instruction.";

    public Prescription() {
    }

    public Prescription(Integer id, Employee doctor, Patient patient, Cure cure, Pattern pattern, Period period, String dose) {
        super(id);
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException();
        }
        this.patient = patient;
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

    public void setDoctor(Employee doctor) {
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException();
        }
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
}
