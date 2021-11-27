package com.rehab.util;

import com.rehab.model.Event;
import com.rehab.model.Pattern;
import com.rehab.model.Prescription;
import com.rehab.model.type.EventState;
import com.rehab.model.type.PatternUnit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains static utility methods for manipulating with events.
 * Instantiation of this class is prohibited.
 */
public class EventUtil {

    /**
     * Message that is used as parameter when IllegalArgumentException will be thrown.
     */
    private static final String ILLEGAL_PATTERN_UNIT_VALUE = "Illegal PatternUnit value.";

    /**
     * Particular time for time of day 'MORNING'.
     */
    private static final LocalTime MORNING_TIME = LocalTime.of(9, 0);

    /**
     * Particular time for time of day 'AFTERNOON'.
     */
    private static final LocalTime AFTERNOON_TIME = LocalTime.of(13, 0);

    /**
     * Particular time for time of day 'EVENING'.
     */
    private static final LocalTime EVENING_TIME = LocalTime.of(17, 0);

    /**
     * Particular time for time of day 'NIGHT'.
     */
    private static final LocalTime NIGHT_TIME = LocalTime.of(21, 0);

    /**
     * Private empty constructor that indicates prohibition for creating instance of current class.
     */
    private EventUtil() {
    }

    /**
     * Method creates new planned events for given prescription, based on which Pattern and Period it has.
     * At first, any period is converted to days.
     * There is check if it's possible to create new events for today date (if Pattern - several times a day).
     * If number of created today events is greater than pattern count divided into 2,
     * then today date is considered to cover all events for today date and today date is the beginning of period.
     * Otherwise, period starts from tomorrow date.
     *
     * @param prescription that events will be created for.
     * @return list of all created events.
     */
    public static List<Event> createEvents(Prescription prescription) {
        var period = prescription.getPeriod();
        var periodCount = period.getCount();
        var daysCount = switch (period.getUnit()) {
            case DAY -> periodCount;
            case WEEK -> periodCount * 7L;
            case MONTH -> periodCount * 30L;
        };

        var events = new ArrayList<Event>();
        var date = prescription.getDate();
        var pattern = prescription.getPattern();
        switch (pattern.getUnit()) {
            case DAY -> {
                var todayEvents = todayEvents(prescription);
                if (!todayEvents.isEmpty()) {
                    events.addAll(todayEvents);
                    if (todayEvents.size() > (pattern.getCount() / 2)) {
                        daysCount--;
                    }
                }
                for (int i = 0; i < daysCount; i++) {
                    createAndAddEventsForPartsOfDay(prescription, date.plusDays(i + 1), events);
                }
            }
            case WEEK -> {
                for (int i = 0; i < daysCount; i++) {
                    createAndAddEventForDaysOfWeek(prescription, date.plusDays(i + 1), events);
                }
            }
            default -> throw new IllegalArgumentException("Illegal TimeUnit value.");
        }

        return events;
    }

    /**
     * Method changes event state from 'PLANNED' to 'CANCELLED',
     * sets current day and time, and adds comment to them.
     *
     * @param events     that will be cancelled.
     * @param doctorName that will be added to comment.
     * @return list of cancelled events.
     */
    public static List<Event> getEventsForCancelling(List<Event> events, String doctorName) {
        return events
                .stream()
                .filter(e -> e.getEventState() == EventState.PLANNED)
                .peek(e -> {
                    e.setEventState(EventState.CANCELLED);
                    e.setEndDate(LocalDate.now());
                    e.setEndTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
                    e.setComment("Cancelled by " + doctorName);
                })
                .collect(Collectors.toList());
    }

    /**
     * Method converts string representation of pattern time units to list of pattern time units.
     *
     * @param pattern that pattern units have to be converted.
     * @return list of pattern units.
     */
    public static List<PatternUnit> patternUnitsAsList(Pattern pattern) {
        return Arrays.stream(pattern.getPatternUnits().split(", "))
                .map(PatternUnit::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Method creates new events only for today date (if it's possible) and returns list of them.
     *
     * @param prescription that today events is being created for.
     * @return list of created events planned only for today.
     */
    private static List<Event> todayEvents(Prescription prescription) {
        var events = new ArrayList<Event>();
        patternUnitsAsList(prescription.getPattern()).forEach(unit -> {
            var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(), LocalDate.now());
            switch (unit) {
                case MORNING -> addEventIfUnitIsBeforeCurrentTime(MORNING_TIME, event, events);
                case AFTERNOON -> addEventIfUnitIsBeforeCurrentTime(AFTERNOON_TIME, event, events);
                case EVENING -> addEventIfUnitIsBeforeCurrentTime(EVENING_TIME, event, events);
                case NIGHT -> addEventIfUnitIsBeforeCurrentTime(NIGHT_TIME, event, events);
                default -> throw new IllegalArgumentException(ILLEGAL_PATTERN_UNIT_VALUE);
            }
        });
        return events;
    }

    /**
     * Method checks if current time is before planned time for today event,
     * then current event is added to list of today events.
     *
     * @param time   planned time of today event.
     * @param event  that is checked for ability to be added to list of today events.
     * @param events list of today events.
     */
    private static void addEventIfUnitIsBeforeCurrentTime(LocalTime time, Event event, List<Event> events) {
        if (LocalTime.now().isBefore(time)) {
            event.setPlannedTime(time);
            events.add(event);
        }
    }

    /**
     * Method creates new events for times of day (morning, afternoon, evening, night)
     * for particular date and adds them to resulting list.
     *
     * @param prescription that new events is being created for.
     * @param plannedDate  particular date, new events will be created for.
     * @param events       that new events will be added to.
     */
    private static void createAndAddEventsForPartsOfDay(Prescription prescription, LocalDate plannedDate,
                                                        List<Event> events) {
        patternUnitsAsList(prescription.getPattern()).forEach(unit -> {
                    var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(),
                            plannedDate);
                    event.setPlannedTime(
                            switch (unit) {
                                case MORNING -> MORNING_TIME;
                                case AFTERNOON -> AFTERNOON_TIME;
                                case EVENING -> EVENING_TIME;
                                case NIGHT -> NIGHT_TIME;
                                default -> throw new IllegalArgumentException(ILLEGAL_PATTERN_UNIT_VALUE);
                            }
                    );
                    events.add(event);
                }
        );
    }

    /**
     * Method creates new event for days of week (Monday, Tuesday, Wednesday, etc.)
     * for particular date and adds it to resulting list.
     * Method checks if given parameter possiblePlannedDate is suitable for creating new planned event
     * (i.e. given day of week is equal to day of week from pattern).
     * Default time for this event is {@link #MORNING_TIME}.
     *
     * @param prescription        that new event is being created for.
     * @param possiblePlannedDate possible date, new event will be created for.
     * @param events              that new events will be added to.
     */
    private static void createAndAddEventForDaysOfWeek(Prescription prescription, LocalDate possiblePlannedDate,
                                                       List<Event> events) {
        var patternUnits = Arrays.stream(prescription.getPattern().getPatternUnits().split(", "))
                .map(PatternUnit::valueOf).collect(Collectors.toList());
        patternUnits.forEach(unit -> {
                    var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(),
                            MORNING_TIME);
                    var isGivenDateSuitable = switch (unit) {
                        case MONDAY,
                                TUESDAY,
                                WEDNESDAY,
                                THURSDAY,
                                FRIDAY,
                                SATURDAY,
                                SUNDAY -> possiblePlannedDate.getDayOfWeek().toString().equals(unit.toString());
                        default -> throw new IllegalArgumentException(ILLEGAL_PATTERN_UNIT_VALUE);
                    };
                    if (isGivenDateSuitable) {
                        event.setPlannedDate(possiblePlannedDate);
                        events.add(event);
                    }
                }
        );
    }
}
