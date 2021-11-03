package com.rehab.service;

import com.rehab.dto.EventDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Employee;
import com.rehab.model.Event;
import com.rehab.model.type.EventState;
import com.rehab.repository.EventCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

@Service
public class EventService {

    private final EventCrudRepository eventCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EventService(EventCrudRepository eventCrudRepository, ModelMapper modelMapper) {
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public EventDto setNurse(int eventId) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForSettingNurse = getEvent(eventId);
        if (eventForSettingNurse.getNurse() != null) {
            throw new ApplicationException("Event already has a nurse.");
        }
        checkEventHasNotPlannedState(eventForSettingNurse.getEventState(), "choose");
        eventForSettingNurse.setNurse(authNurse);
        return toDto(eventCrudRepository.save(eventForSettingNurse));
    }

    @Transactional
    public EventDto unSetNurse(int eventId) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForUnsettingNurse = getEvent(eventId);
        checkEventHasNotNurse(eventForUnsettingNurse);
        checkEventHasDifferentNurse(authNurse, eventForUnsettingNurse.getNurse());
        checkEventHasNotPlannedState(eventForUnsettingNurse.getEventState(), "discard");
        eventForUnsettingNurse.setNurse(null);
        return toDto(eventCrudRepository.save(eventForUnsettingNurse));
    }

    @Transactional
    public EventDto changeState(int eventId, String eventState, String comment) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForChangingState = getEvent(eventId);
        checkEventHasNotNurse(eventForChangingState);
        checkEventHasDifferentNurse(authNurse, eventForChangingState.getNurse());
        var currentEventState = eventForChangingState.getEventState();
        var newEventState = EventState.valueOf(eventState.toUpperCase());
        if (currentEventState != EventState.PLANNED) {
            throw new ApplicationException("Cannot change state from " + currentEventState + " to " + newEventState);
        }
        eventForChangingState.setEventState(newEventState);
        eventForChangingState.setEndDate(LocalDate.now());
        eventForChangingState.setEndTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        eventForChangingState.setComment(comment);
        return toDto(eventCrudRepository.save(eventForChangingState));
    }

    public EventDto getById(int eventId) {
        return toDto(getEvent(eventId));
    }

    public Page<EventDto> getByPrescriptionId(int prescriptionId, Pageable pageable) {
        return eventCrudRepository.findAllByPrescriptionId(prescriptionId, pageable).map(this::toDto);
    }

    public Page<EventDto> filter(LocalDate plannedDate, Integer insuranceNumber, boolean authNurse,
                                 boolean onlyPlanned, Pageable pageable) {
        return eventCrudRepository.filter(plannedDate, insuranceNumber,
                authNurse ? SecurityUtil.getAuthEmployee().getId() : null,
                onlyPlanned ? EventState.PLANNED : null,
                pageable).map(this::toDto);
    }

    private void checkEventHasNotNurse(Event event) {
        if (event.getNurse() == null) {
            throw new ApplicationException("Event has no nurse.");
        }
    }

    private void checkEventHasDifferentNurse(Employee authNurse, Employee anotherNurse) {
        if (!authNurse.getId().equals(anotherNurse.getId())) {
            throw new ApplicationException("Event has a different nurse.");
        }
    }

    private void checkEventHasNotPlannedState(EventState eventState, String action) {
        if (eventState != EventState.PLANNED) {
            throw new ApplicationException("Cannot " + action + " event with " + eventState + " state.");
        }
    }

    private Event getEvent(int id) {
        return eventCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Event with id " + id + " not found."));
    }

    private EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }
}
