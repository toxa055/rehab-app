package com.rehab.model;

public class Prescription extends AbstractIdEntity {
    private Patient patient;
    private Cure cure;
    private Pattern pattern;
    private Period period;
    private String dose;

    public Prescription() {
    }

    public Prescription(Integer id, Patient patient, Cure cure, Pattern pattern, Period period, String dose) {
        super(id);
        this.patient = patient;
        this.cure = cure;
        this.pattern = pattern;
        this.period = period;
        this.dose = dose;
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
