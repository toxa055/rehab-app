package com.rehab.model;

import com.rehab.model.type.PatientState;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity describing patient which is a client of rehabilitation hospital.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'patients' in database.
 */
@Entity
@Table(name = "patients")
public class Patient extends AbstractIdEntity {

    /**
     * Patient's unique insurance number.
     * It maps to column 'insurance_number' to table 'patients' in database.
     * Value cannot be null.
     */
    @Column(name = "insurance_number", nullable = false, unique = true)
    private int insuranceNumber;

    /**
     * Patient's name, second name.
     * It maps to column 'name' to table 'patients' in database.
     * Value cannot be null.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Patient's date of birth.
     * It maps to column 'birth_date' to table 'patients' in database.
     * Value cannot be null.
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    /**
     * Patient's home address.
     * It maps to column 'address' to table 'patients' in database.
     * Value cannot be null.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * Patient's current state.
     * It maps to column 'patient_state' to table 'patients' in database.
     * Value cannot be null.
     *
     * @see PatientState
     */
    @Column(name = "patient_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PatientState patientState = PatientState.TREATING;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Patient() {
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param id              description of id is in field declaration.
     * @param insuranceNumber description of insuranceNumber is in field declaration.
     * @param name            description of name is in field declaration.
     * @param birthDate       description of birthDate is in field declaration.
     * @param address         description of address is in field declaration.
     */
    public Patient(Integer id, int insuranceNumber, String name, LocalDate birthDate, String address) {
        super(id);
        this.insuranceNumber = insuranceNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

    public int getInsuranceNumber() {
        return insuranceNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public PatientState getPatientState() {
        return patientState;
    }

    public void setInsuranceNumber(int insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPatientState(PatientState patientState) {
        this.patientState = patientState;
    }
}
