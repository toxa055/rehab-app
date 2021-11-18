package com.rehab.dto;

import com.rehab.model.type.PatternUnit;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class PrescriptionDto extends AbstractPrescriptionDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    @NotEmpty(message = "Times of day must be chosen")
    private List<PatternUnit> patternUnits;
    private boolean active = true;

    public LocalDate getDate() {
        return date;
    }

    public List<PatternUnit> getPatternUnits() {
        return patternUnits;
    }

    public boolean isActive() {
        return active;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPatternUnits(List<PatternUnit> patternUnits) {
        this.patternUnits = patternUnits;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
