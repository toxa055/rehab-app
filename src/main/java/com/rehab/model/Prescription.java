package com.rehab.model;

public class Prescription extends AbstractIdEntity {
    private Patient patient;
    private Cure cure;
    private String pattern; // e.g. "2xDAY:MORNING;EVENING" or e.g. "3xWEEK:MON;WED;FRI"
    private String period;  // e.g. "3xWEEK"                or e.g. "1xMONTH"
    private String dose;

    public Prescription() {
    }

    public Prescription(Integer id, Patient patient, Cure cure, String pattern, String period, String dose) {
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

    public String getPattern() {
        return pattern;
    }

    public String getPeriod() {
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

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
