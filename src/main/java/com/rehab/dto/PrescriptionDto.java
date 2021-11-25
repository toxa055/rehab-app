package com.rehab.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PrescriptionDto extends AbstractPrescriptionDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    @NotEmpty(message = "Times of day must be chosen")
    private List<String> patternUnits;
    private boolean active = true;

    public LocalDate getDate() {
        return date;
    }

    public List<String> getPatternUnits() {
        return patternUnits;
    }

    public boolean isActive() {
        return active;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPatternUnits(List<String> patternUnits) {
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
        PrescriptionDto that = (PrescriptionDto) o;
        return active == that.active
                && date.equals(that.date)
                && patternUnits.equals(that.patternUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, patternUnits, active);
    }
}
