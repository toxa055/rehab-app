package com.rehab.model;

import com.rehab.model.type.Role;

import javax.persistence.*;

@Entity
@Table(name = "treatments")
public class Treatment extends AbstractIdEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Column(name = "closed", nullable = false)
    private boolean isClosed = false;

    public Treatment() {
    }

    public Treatment(Integer id, Patient patient, Employee doctor, String diagnosis) {
        super(id);
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException();
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Employee doctor) {
        if (!doctor.getRoles().contains(Role.DOCTOR)) {
            throw new IllegalArgumentException();
        }
        this.doctor = doctor;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
