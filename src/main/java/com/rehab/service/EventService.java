package com.rehab.service;

import com.rehab.dto.EventDto;
import com.rehab.dto.EventMessage;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Employee;
import com.rehab.model.Event;
import com.rehab.model.type.EventState;
import com.rehab.repository.EventCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventCrudRepository eventCrudRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate template;

    @Autowired
    public EventService(EventCrudRepository eventCrudRepository, RabbitTemplate template, ModelMapper modelMapper) {
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
        this.template = template;
    }

    @PostConstruct
    public void init() {
        sendMessage();
    }

    public EventDto getById(int eventId) {
        return toDto(getEventById(eventId));
    }

    public Page<EventDto> getByPrescriptionId(int prescriptionId, Pageable pageable) {
        return eventCrudRepository.findAllByPrescriptionId(prescriptionId, pageable).map(this::toDto);
    }

    @Transactional
    public EventDto setNurse(int eventId) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForSettingNurse = getEventById(eventId);
        if (eventForSettingNurse.getNurse() != null) {
            throw new ApplicationException("Event already has a nurse.");
        }
        checkEventHasNotPlannedState(eventForSettingNurse.getEventState(), "choose");
        eventForSettingNurse.setNurse(authNurse);
        var savedEvent = eventCrudRepository.save(eventForSettingNurse);
        sendMessage(savedEvent);
        return toDto(savedEvent);
    }

    @Transactional
    public EventDto unSetNurse(int eventId) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForUnsettingNurse = getEventById(eventId);
        checkEventHasNotNurse(eventForUnsettingNurse);
        checkEventHasDifferentNurse(authNurse, eventForUnsettingNurse.getNurse());
        checkEventHasNotPlannedState(eventForUnsettingNurse.getEventState(), "discard");
        eventForUnsettingNurse.setNurse(null);
        var savedEvent = eventCrudRepository.save(eventForUnsettingNurse);
        sendMessage(savedEvent);
        return toDto(savedEvent);
    }

    @Transactional
    public EventDto changeState(int eventId, String eventState, String comment) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForChangingState = getEventById(eventId);
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
        var savedEvent = eventCrudRepository.save(eventForChangingState);
        sendMessage(savedEvent);
        return toDto(savedEvent);
    }

    public Page<EventDto> filter(LocalDate plannedDate, Integer insuranceNumber, boolean authNurse,
                                 boolean onlyPlanned, Pageable pageable) {
        return eventCrudRepository.filter(plannedDate, insuranceNumber,
                authNurse ? SecurityUtil.getAuthEmployee().getId() : null,
                onlyPlanned ? EventState.PLANNED : null,
                pageable).map(this::toDto);
    }

    public List<EventMessage> getTodayPlannedEventsMessage() {
        return eventCrudRepository.findAllTodayPlanned(LocalDate.now())
                .stream()
                .map(this::toMessage)
                .collect(Collectors.toList());
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

    private Event getEventById(int id) {
        return eventCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Event with id " + id + " not found."));
    }

    private void sendMessage(Event changedEvent) {
        if (changedEvent.getPlannedDate().equals(LocalDate.now())) {
            sendMessage();
        }
    }

    private void sendMessage() {
        template.convertAndSend("events_queue", "updated");
    }

    private EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    private EventMessage toMessage(Event event) {
        return modelMapper.map(event, EventMessage.class);
    }
}
