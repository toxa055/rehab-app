package com.rehab.model;

import com.rehab.model.type.TimeUnit;

import javax.persistence.*;

@Entity
@Table(name = "periods", uniqueConstraints = {@UniqueConstraint(name = "count_unique_unit_idx",
        columnNames = {"count", "unit"})})
public class Period extends AbstractIdEntity {

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    public Period() {
    }

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
