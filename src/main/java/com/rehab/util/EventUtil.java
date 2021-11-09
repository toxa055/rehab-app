package com.rehab.util;

import com.rehab.model.Event;
import com.rehab.model.Pattern;
import com.rehab.model.Prescription;
import com.rehab.model.type.EventState;
import com.rehab.model.type.PatternUnit;
import com.rehab.model.type.TimeUnit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventUtil {

    private static final long DAYS_IN_WEEK = 7L;
    private static final long DAYS_IN_MONTH = 30L;
    private static final LocalTime morning = LocalTime.of(9, 0);
    private static final LocalTime afternoon = LocalTime.of(13, 0);
    private static final LocalTime evening = LocalTime.of(17, 0);
    private static final LocalTime night = LocalTime.of(21, 0);

    private EventUtil() {
    }

    public static List<Event> createEvents(Prescription prescription) {
        var events = new ArrayList<Event>();
        var patternUnit = prescription.getPattern().getUnit();
        var period = prescription.getPeriod();
        var date = prescription.getDate();

        if (patternUnit == TimeUnit.DAY) {
            var todayEvents = todayEvents(prescription);
            int count = period.getCount();
            if (!todayEvents.isEmpty()) {
                events.addAll(todayEvents);
                if ((period.getUnit() == TimeUnit.DAY)
                        && (todayEvents.size() > (prescription.getPattern().getCount() / 2))) {
                    count--;
                }
            }

            for (int i = 0; i < count; i++) {
                switch (period.getUnit()) {
                    case DAY -> addEventsForPartsOfDay(prescription, date.plusDays(i + 1), events);
                    case WEEK -> {
                        for (int j = 1; j <= DAYS_IN_WEEK; j++) {
                            addEventsForPartsOfDay(prescription, date.plusDays(i * DAYS_IN_WEEK + j), events);
                        }
                    }
                    case MONTH -> {
                        for (int j = 1; j <= DAYS_IN_MONTH; j++) {
                            addEventsForPartsOfDay(prescription, date.plusDays(i * DAYS_IN_MONTH + j), events);
                        }
                    }
                }
            }
        }

        if (patternUnit == TimeUnit.WEEK) {
            for (int i = 0; i < period.getCount(); i++) {
                switch (period.getUnit()) {
                    case DAY -> addEventsForDaysOfWeek(prescription, date.plusDays(i + 1), events);
                    case WEEK -> {
                        for (int j = 1; j <= DAYS_IN_WEEK; j++) {
                            addEventsForDaysOfWeek(prescription, date.plusDays(i * DAYS_IN_WEEK + j), events);
                        }
                    }
                    case MONTH -> {
                        for (int j = 1; j <= DAYS_IN_MONTH; j++) {
                            addEventsForDaysOfWeek(prescription, date.plusDays(i * DAYS_IN_MONTH + j), events);
                        }
                    }
                }
            }
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
                default -> throw new IllegalArgumentException();
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
                                default -> throw new IllegalArgumentException();
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
