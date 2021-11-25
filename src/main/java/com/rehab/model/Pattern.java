package com.rehab.model;

import com.rehab.model.type.TimeUnit;

import javax.persistence.*;

@Entity
@Table(name = "patterns", uniqueConstraints = {@UniqueConstraint(name = "count_unit_unique_units_idx",
        columnNames = {"count", "unit", "pattern_units"})})
public class Pattern extends AbstractIdEntity {

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    @Column(name = "pattern_units", nullable = false)
    private String patternUnits;

    public Pattern() {
    }

    public Pattern(Integer id, int count, TimeUnit unit, String patternUnits) {
        super(id);
        this.count = count;
        this.unit = unit;
        this.patternUnits = patternUnits;
    }

    public int getCount() {
        return count;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public String getPatternUnits() {
        return patternUnits;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public void setPatternUnits(String patternUnits) {
        this.patternUnits = patternUnits;
    }
}
