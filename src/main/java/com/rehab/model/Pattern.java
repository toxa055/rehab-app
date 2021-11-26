package com.rehab.model;

import com.rehab.model.type.PatternUnit;
import com.rehab.model.type.TimeUnit;

import javax.persistence.*;

/**
 * Entity describing particular behavior (frequency), how often
 * patient has to take medicines or to be treated with procedure.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'patterns' in database.
 * Combination of count, unit and patternUnits is unique.
 * <p>There are two types of patterns: several times a day and several times a week.</p>
 * <p>For example, if count = 2, unit = 'DAY' and patternUnits = 'MORNING, EVENING',
 * it means patient has to take medicine (or to be treated with procedure)
 * twice a day: in the morning and evening.</p>
 * <p>For example, if count = 3, unit = 'WEEK' and patternUnits = 'MONDAY, TUESDAY, WEDNESDAY',
 * it means patient has to take medicine (or to be treated with procedure)
 * three times a week: on Monday, Tuesday, Wednesday.</p>
 */
@Entity
@Table(name = "patterns", uniqueConstraints = {@UniqueConstraint(name = "count_unit_unique_units_idx",
        columnNames = {"count", "unit", "pattern_units"})})
public class Pattern extends AbstractIdEntity {

    /**
     * Particular count of time units.
     * It maps to column 'count' to table 'patterns' in database.
     * Value cannot be null.
     */
    @Column(name = "count", nullable = false)
    private int count;

    /**
     * Particular value which defines time unit.
     * It maps to column 'unit' to table 'patterns' in database.
     * Value cannot be null.
     *
     * @see TimeUnit
     */
    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    /**
     * String representation of particular time units
     * which defines what time a day or day of week
     * patient has to take medicine (or to be treated with procedure).
     * It maps to column 'pattern_units' to table 'periods' in database.
     * Value cannot be null.
     *
     * @see PatternUnit
     */
    @Column(name = "pattern_units", nullable = false)
    private String patternUnits;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Pattern() {
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param id           description of id is in field declaration.
     * @param count        description of count is in field declaration.
     * @param unit         description of unit is in field declaration.
     * @param patternUnits description of patternUnits is in field declaration.
     */
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
