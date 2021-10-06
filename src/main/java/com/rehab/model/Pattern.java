package com.rehab.model;

import com.rehab.model.type.PatternUnit;
import com.rehab.model.type.TimeUnit;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patterns")
public class Pattern extends AbstractIdEntity {

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit unit;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "pattern_units", joinColumns = @JoinColumn(name = "pattern_id"))
    @JoinColumn(name = "pattern_id")
    @ElementCollection(fetch = FetchType.EAGER)
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
