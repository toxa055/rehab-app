package com.rehab.dto;

import com.rehab.model.type.PatientState;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class PatientDto {
    private int id;
    @NotNull(message = "Insurance number cannot be empty")
    @Range(min = 1_000, max = 99_999_999, message = "Insurance number must contain from 4 to 8 digits")
    private Integer insuranceNumber;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 8, max = 50, message = "Length must be from 8 to 50 symbols")
    private String name;
    @NotBlank(message = "Address cannot be empty")
    @Size(min = 8, max = 30, message = "Length must be from 8 to 50 symbols")
    private String address;
    private PatientState patientState = PatientState.TREATING;

    public PatientDto() {
    }

    public int getId() {
        return id;
    }

    public Integer getInsuranceNumber() {
        return insuranceNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public PatientState getPatientState() {
        return patientState;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInsuranceNumber(Integer insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPatientState(PatientState patientState) {
        this.patientState = patientState;
    }
}
