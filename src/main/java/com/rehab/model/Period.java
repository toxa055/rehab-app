package com.rehab.model;

import com.rehab.model.type.TimeUnit;

public class Period extends AbstractIdEntity {
    private int count;
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
