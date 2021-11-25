package com.rehab.util;

import com.rehab.model.Event;
import com.rehab.model.Pattern;
import com.rehab.model.Prescription;
import com.rehab.model.type.EventState;
import com.rehab.model.type.PatternUnit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventUtil {

    private static final String ILLEGAL_PATTERN_UNIT_VALUE = "Illegal PatternUnit value.";
    private static final LocalTime morning = LocalTime.of(9, 0);
    private static final LocalTime afternoon = LocalTime.of(13, 0);
    private static final LocalTime evening = LocalTime.of(17, 0);
    private static final LocalTime night = LocalTime.of(21, 0);

    private EventUtil() {
    }

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
                    addEventsForPartsOfDay(prescription, date.plusDays(i + 1), events);
                }
            }
            case WEEK -> {
                for (int i = 0; i < daysCount; i++) {
                    addEventsForDaysOfWeek(prescription, date.plusDays(i + 1), events);
                }
            }
            default -> throw new IllegalArgumentException("Illegal TimeUnit value.");
        }

        return events;
    }

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

    public static List<PatternUnit> patternUnitsAsList(Pattern pattern) {
        return Arrays.stream(pattern.getPatternUnits().split(", "))
                .map(PatternUnit::valueOf)
                .collect(Collectors.toList());
    }

    private static List<Event> todayEvents(Prescription prescription) {
        var events = new ArrayList<Event>();
        patternUnitsAsList(prescription.getPattern()).forEach(unit -> {
            var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(), LocalDate.now());
            switch (unit) {
                case MORNING -> addEventIfUnitIsBeforeCurrentTime(morning, event, events);
                case AFTERNOON -> addEventIfUnitIsBeforeCurrentTime(afternoon, event, events);
                case EVENING -> addEventIfUnitIsBeforeCurrentTime(evening, event, events);
                case NIGHT -> addEventIfUnitIsBeforeCurrentTime(night, event, events);
                default -> throw new IllegalArgumentException(ILLEGAL_PATTERN_UNIT_VALUE);
            }
        });
        return events;
    }

    private static void addEventIfUnitIsBeforeCurrentTime(LocalTime time, Event event, List<Event> events) {
        if (LocalTime.now().isBefore(time)) {
            event.setPlannedTime(time);
            events.add(event);
        }
    }

    private static void addEventsForPartsOfDay(Prescription prescription, LocalDate plannedDate, List<Event> events) {
        patternUnitsAsList(prescription.getPattern()).forEach(unit -> {
                    var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(),
                            plannedDate);
                    event.setPlannedTime(
                            switch (unit) {
                                case MORNING -> morning;
                                case AFTERNOON -> afternoon;
                                case EVENING -> evening;
                                case NIGHT -> night;
                                default -> throw new IllegalArgumentException(ILLEGAL_PATTERN_UNIT_VALUE);
                            }
                    );
                    events.add(event);
                }
        );
    }

    private static void addEventsForDaysOfWeek(Prescription prescription, LocalDate plannedDate, List<Event> events) {
        var plannedDayOfWeek = plannedDate.getDayOfWeek();
        Arrays.stream(prescription.getPattern().getPatternUnits().split(", "))
                .map(PatternUnit::valueOf).collect(Collectors.toList()).forEach(unit -> {
                    var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(),
                            LocalTime.of(9, 0));
                    var daysToNextPlannedDay = switch (unit) {
                        case MONDAY -> daysToNextPlannedDay(0, plannedDayOfWeek, unit);
                        case TUESDAY -> daysToNextPlannedDay(-1, plannedDayOfWeek, unit);
                        case WEDNESDAY -> daysToNextPlannedDay(-2, plannedDayOfWeek, unit);
                        case THURSDAY -> daysToNextPlannedDay(-3, plannedDayOfWeek, unit);
                        case FRIDAY -> daysToNextPlannedDay(-4, plannedDayOfWeek, unit);
                        case SATURDAY -> daysToNextPlannedDay(-5, plannedDayOfWeek, unit);
                        case SUNDAY -> daysToNextPlannedDay(-6, plannedDayOfWeek, unit);
                        default -> throw new IllegalArgumentException();
                    };
                    if (daysToNextPlannedDay >= 0) {
                        event.setPlannedDate(plannedDate.plusDays(daysToNextPlannedDay));
                        events.add(event);
                    }
                }
        );
    }

    private static int daysToNextPlannedDay(int start, DayOfWeek plannedDayOfWeek, PatternUnit unit) {
        if (plannedDayOfWeek.toString().equals(unit.toString())) {
            return switch (plannedDayOfWeek) {
                case MONDAY -> start;
                case TUESDAY -> start + 1;
                case WEDNESDAY -> start + 2;
                case THURSDAY -> start + 3;
                case FRIDAY -> start + 4;
                case SATURDAY -> start + 5;
                case SUNDAY -> start + 6;
            };
        }
        return -1;
    }
}
