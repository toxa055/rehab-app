package com.rehab.dto;

import java.time.LocalDate;
import java.util.Objects;

public class PrescriptionDtoOut extends AbstractPrescriptionDto {
    private LocalDate date;
    private String patternUnits;
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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, patternUnits, active);
    }
}
