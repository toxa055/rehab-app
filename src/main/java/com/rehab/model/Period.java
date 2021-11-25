package com.rehab.model;

import com.rehab.model.type.TimeUnit;

import javax.persistence.*;

/**
 * Entity describing particular period of time,
 * during of that patient has to take medicines or to be treated with procedure.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'periods' in database.
 * Combination of count and unit is unique.
 * <p>For example, if count = 2 and unit = 'WEEK', it means period lasts two weeks.</p>
 */
@Entity
@Table(name = "periods", uniqueConstraints = {@UniqueConstraint(name = "count_unique_unit_idx",
        columnNames = {"count", "unit"})})
public class Period extends AbstractIdEntity {

    /**
     * Particular count of time units.
     * It maps to column 'count' to table 'periods' in database.
     * Value cannot be null.
     */
    @Column(name = "count", nullable = false)
    private int count;

    /**
     * Particular value which defines time unit.
     * It maps to column 'unit' to table 'periods' in database.
     * Value cannot be null.
     *
     * @see TimeUnit
     */
    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Period() {
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param id    description of id is in field declaration.
     * @param count description of count is in field declaration.
     * @param unit  description of unit is in field declaration.
     */
    public Period(Integer id, int count, TimeUnit unit) {
        super(id);
        this.count = count;
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
}
