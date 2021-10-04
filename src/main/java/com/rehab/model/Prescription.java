package com.rehab.model;

import com.rehab.model.type.Role;

public class Prescription extends AbstractIdEntity {
    private Employee doctor;
    private Patient patient;
    private Cure cure;
    private Pattern pattern;
    private Period period;
    private String dose;

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
