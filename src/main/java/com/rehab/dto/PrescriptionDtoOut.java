package com.rehab.dto;

import java.time.LocalDate;

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
}
