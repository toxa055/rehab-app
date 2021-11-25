package com.rehab.model.type;

/**
 * Enumeration which stores pattern units.
 * They are used in {@link com.rehab.model.Pattern} to define what time of day or day of week
 * patient has to take medicine (or to be treated with procedure).
 *
 * @see com.rehab.util.EventUtil
 */
public enum PatternUnit {

    /**
     * Unit which defines time of day as morning.
     */
    MORNING,

    /**
     * Unit which defines time of day as afternoon.
     */
    AFTERNOON,

    /**
     * Unit which defines time of day as evening.
     */
    EVENING,

    /**
     * Unit which defines time of day as night.
     */
    NIGHT,

    /**
     * Unit which defines day of week as Monday.
     */
    MONDAY,

    /**
     * Unit which defines day of week as Tuesday.
     */
    TUESDAY,

    /**
     * Unit which defines day of week as Wednesday.
     */
    WEDNESDAY,

    /**
     * Unit which defines day of week as Thursday.
     */
    THURSDAY,

    /**
     * Unit which defines day of week as Friday.
     */
    FRIDAY,

    /**
     * Unit which defines day of week as Saturday.
     */
    SATURDAY,

    /**
     * Unit which defines day of week as Sunday.
     */
    SUNDAY
}
