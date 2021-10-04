package com.rehab.model;

import com.rehab.model.type.PatternUnit;
import com.rehab.model.type.TimeUnit;

import java.util.List;

public class Pattern extends AbstractIdEntity {
    private int count;
    private TimeUnit unit;
    private List<PatternUnit> patternUnits;

    public Pattern() {
    }

    public Pattern(Integer id, int count, TimeUnit unit, List<PatternUnit> patternUnits) {
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

    public List<PatternUnit> getPatternUnits() {
        return patternUnits;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public void setPatternUnits(List<PatternUnit> patternUnits) {
        this.patternUnits = patternUnits;
    }
}
