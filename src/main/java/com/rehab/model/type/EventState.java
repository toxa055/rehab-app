package com.rehab.model.type;

/**
 * Enumeration which stores event states.
 */
public enum EventState {

    /**
     * State for event that has to be performed by nurse at a particular date and time.
     */
    PLANNED,

    /**
     * State for event that was performed by nurse.
     */
    PERFORMED,

    /**
     * State for event that cancelled by nurse or doctor.
     */
    CANCELLED
}
