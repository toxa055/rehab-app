package com.rehab.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for {@link com.rehab.model.Prescription}. i.e. it's a view representation of Prescription model.
 * It's used for transferring data between services and controllers.
 * It's only used for getting prescription data.
 */
public class PrescriptionDtoOut extends AbstractPrescriptionDto {

    /**
     * Prescription date.
     */
    private LocalDate date;

    /**
     * String representation of pattern time units.
     */
    private String patternUnits;

    /**
     * Whether prescription active.
     */
    private boolean active;

    public LocalDate getDate() {
        return date;
    }

    public String getPatternUnits() {
        return patternUnits;
    }

    public boolean isActive() {
        return active;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPatternUnits(String patternUnits) {
        this.patternUnits = patternUnits;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        if (!super.equals(o)) return false;
        PrescriptionDtoOut that = (PrescriptionDtoOut) o;
        return active == that.active
                && date.equals(that.date)
                && patternUnits.equals(that.patternUnits);
    }

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, patternUnits, active);
    }
}
