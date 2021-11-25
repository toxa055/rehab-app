package com.rehab.dto;

import com.rehab.model.type.PatientState;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for {@link com.rehab.model.Patient}, i.e. it's a view representation of Patient model.
 * It's used for transferring data between services and controllers.
 */
public class PatientDto {

    /**
     * Patient id.
     */
    private Integer id;

    /**
     * Patient insurance number.
     */
    @NotNull(message = "Insurance number cannot be empty")
    @Range(min = 1_000, max = 99_999_999, message = "Insurance number must contain from 4 to 8 digits")
    private Integer insuranceNumber;

    /**
     * Patient name, second name.
     */
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 8, max = 50, message = "Length must be from 8 to 50 symbols")
    private String name;

    /**
     * Patient date of birth.
     */
    @Past(message = "Birth date must be a past date")
    @NotNull(message = "Birth date cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * Patient home address.
     */
    @NotBlank(message = "Address cannot be empty")
    @Size(min = 8, max = 50, message = "Length must be from 8 to 50 symbols")
    private String address;

    /**
     * Patient state.
     */
    private PatientState patientState = PatientState.TREATING;

    public Integer getId() {
        return id;
    }

    public Integer getInsuranceNumber() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInsuranceNumber(Integer insuranceNumber) {
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

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDto that = (PatientDto) o;
        return id.equals(that.id)
                && insuranceNumber.equals(that.insuranceNumber)
                && name.equals(that.name)
                && birthDate.equals(that.birthDate)
                && address.equals(that.address)
                && patientState == that.patientState;
    }

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, insuranceNumber, name, birthDate, address, patientState);
    }
}
