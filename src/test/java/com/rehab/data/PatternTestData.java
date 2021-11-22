package com.rehab.data;

import com.rehab.model.Pattern;
import com.rehab.model.type.TimeUnit;

public class PatternTestData {

    private PatternTestData() {
    }

    public static Pattern getPattern1() {
        return new Pattern(19, 2, TimeUnit.DAY, "MORNING, EVENING");
    }

    public static Pattern getPattern2() {
        return new Pattern(20, 3, TimeUnit.WEEK, "MONDAY, WEDNESDAY, FRIDAY");
    }
}
