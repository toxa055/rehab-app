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
import java.util.List;
import java.util.stream.Collectors;

public class EventUtil {

    private static final long DAYS_IN_WEEK = 7L;
    private static final long DAYS_IN_MONTH = 30L;

    private EventUtil() {
    }

    public static List<Event> createEvents(Prescription prescription) {
        var events = new ArrayList<Event>();
        var pattern = prescription.getPattern();
        var period = prescription.getPeriod();
        var date = prescription.getDate();

        if (pattern.getUnit() == TimeUnit.DAY) {
            for (int i = 0; i < period.getCount(); i++) {
                switch (period.getUnit()) {
                    case DAY -> addEventsForPartsOfDay(prescription, pattern, date.plusDays(i + 1), events);
                    case WEEK -> {
                        for (int j = 1; j <= DAYS_IN_WEEK; j++) {
                            addEventsForPartsOfDay(prescription, pattern, date.plusDays(i * DAYS_IN_WEEK + j), events);
                        }
                    }
                    case MONTH -> {
                        for (int j = 1; j <= DAYS_IN_MONTH; j++) {
                            addEventsForPartsOfDay(prescription, pattern, date.plusDays(i * DAYS_IN_MONTH + j), events);
                        }
                    }
                }
            }
        }

        if (pattern.getUnit() == TimeUnit.WEEK) {
            for (int i = 0; i < period.getCount(); i++) {
                switch (period.getUnit()) {
                    case DAY -> addEventsForDaysOfWeek(prescription, pattern, date.plusDays(i + 1), events);
                    case WEEK -> {
                        for (int j = 1; j <= DAYS_IN_WEEK; j++) {
                            addEventsForDaysOfWeek(prescription, pattern, date.plusDays(i * DAYS_IN_WEEK + j), events);
                        }
                    }
                    case MONTH -> {
                        for (int j = 1; j <= DAYS_IN_MONTH; j++) {
                            addEventsForDaysOfWeek(prescription, pattern, date.plusDays(i * DAYS_IN_MONTH + j), events);
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

    private static void addEventsForPartsOfDay(Prescription prescription, Pattern pattern, LocalDate plannedDate,
                                               List<Event> events) {
        pattern.getPatternUnits().forEach(unit -> {
                    var event = new Event(prescription.getPatient(), prescription.getCure(), prescription.getDose(),
                            plannedDate);
                    event.setPlannedTime(LocalTime.of(
                            switch (unit) {
                                case MORNING -> 9;
                                case AFTERNOON -> 13;
                                case EVENING -> 17;
                                case NIGHT -> 22;
                                default -> throw new IllegalArgumentException();
                            }, 0)
                    );
                    events.add(event);
                }
        );
    }

    private static void addEventsForDaysOfWeek(Prescription prescription, Pattern pattern, LocalDate plannedDate,
                                               List<Event> events) {
        var plannedDayOfWeek = plannedDate.getDayOfWeek();
        pattern.getPatternUnits().forEach(unit -> {
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
