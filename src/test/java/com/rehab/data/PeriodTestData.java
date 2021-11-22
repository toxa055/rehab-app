package com.rehab.data;

import com.rehab.model.Period;
import com.rehab.model.type.TimeUnit;

public class PeriodTestData {

    private PeriodTestData() {
    }

    public static Period getPeriod1() {
        return new Period(17, 4, TimeUnit.DAY);
    }

    public static Period getPeriod2() {
        return new Period(18, 1, TimeUnit.WEEK);
    }
}
