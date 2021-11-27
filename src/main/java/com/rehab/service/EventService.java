package com.rehab.service;

import com.rehab.config.BeansConfig;
import com.rehab.config.MQConfig;
import com.rehab.dto.EventDto;
import com.rehab.dto.EventMessage;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Employee;
import com.rehab.model.Event;
import com.rehab.model.type.EventState;
import com.rehab.repository.EventCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

/**
 * Service class for Event. It operates with Event, EventDto
 * and contains methods that are considered as business logic.
 */
@Service
public class EventService {

    /**
     * EventCrudRepository bean.
     */
    private final EventCrudRepository eventCrudRepository;

    /**
     * ModelMapper bean.
     *
     * @see BeansConfig#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * RabbitTemplate bean. It's used for sending messages to queue.
     *
     * @see MQConfig#template(ConnectionFactory)
     */
    private final RabbitTemplate template;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param eventCrudRepository description of eventCrudRepository is in field declaration.
     * @param template            description of template is in field declaration.
     * @param modelMapper         description of modelMapper is in field declaration.
     */
    @Autowired
    public EventService(EventCrudRepository eventCrudRepository, RabbitTemplate template, ModelMapper modelMapper) {
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
        this.template = template;
    }

    /**
     * Method executes after current bean initialization and sends message to message queue.
     * It takes some time to configure connection to message broker,
     * so thread is sleeping before sending the message.
     *
     * @throws InterruptedException if thread will be interrupted while sleeping.
     */
    @PostConstruct
    public void init() throws InterruptedException {
        Thread.sleep(1000);
        sendMessage();
    }

    /**
     * Method returns only eventDto by given event id.
     *
     * @param id event id.
     * @return found event mapped to eventDto.
     */
    public EventDto getById(int id) {
        return toDto(getEventById(id));
    }

    /**
     * Method finds events for particular prescription and maps them to page of eventDto.
     *
     * @param prescriptionId prescription id that events will be found for.
     * @param pageable       interface that provides pagination.
     * @return page of events for prescription mapped to treatmentDto.
     */
    public Page<EventDto> getByPrescriptionId(int prescriptionId, Pageable pageable) {
        return eventCrudRepository.findAllByPrescriptionId(prescriptionId, pageable).map(this::toDto);
    }

    /**
     * Method sets nurse as a performer of the event. It's impossible to set nurse for event,
     * if event already has a performer or event state is not 'PLANNED'.
     * Method sends message to message queue if today event was changed.
     *
     * @param eventId event id that nurse will be set for.
     * @return event that nurse was set for, mapped to eventDto.
     */
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

    /**
     * Method removes nurse as a performer of the event. It's impossible to remove nurse for event,
     * if event does not have a performer, has a different nurse as a performer or event state is not 'PLANNED'.
     * Method sends message to message queue if today event was changed.
     *
     * @param eventId event id that nurse will be removed from.
     * @return event that nurse was removed from, mapped to eventDto.
     */
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

    /**
     * Method changes event state from 'PLANNED' to another. It's impossible to change state for event,
     * if event does not have a performer, has a different nurse as a performer or event state is not 'PLANNED'.
     * When event state was successfully changed, method sets current date/time as finished date/time for event.
     * If state to change is 'CANCELLED', method sets comment.
     * Method sends message to message queue if today event was changed.
     *
     * @param eventId    event id that state will be changed for.
     * @param eventState state that will be set for event.
     * @param comment    string comment that will be added to event.
     * @return event that state was changed, mapped to eventDto.
     */
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

    /**
     * Method finds events by given parameters and maps them to page of eventDto.
     *
     * @param plannedDate     particular date when events are planned for.
     * @param insuranceNumber patient insurance number.
     * @param authNurse       only events that authenticated nurse is a performer or any.
     * @param onlyPlanned     only planned events or any.
     * @param pageable        interface that provides pagination.
     * @return page of events (found by given parameters) mapped to eventDto.
     */
    public Page<EventDto> filter(LocalDate plannedDate, Integer insuranceNumber, boolean authNurse,
                                 boolean onlyPlanned, Pageable pageable) {
        return eventCrudRepository.filter(plannedDate, insuranceNumber,
                authNurse ? SecurityUtil.getAuthEmployee().getId() : null,
                onlyPlanned ? EventState.PLANNED : null,
                pageable).map(this::toDto);
    }

    /**
     * Method finds only planned events for today date and maps them to list of eventMessage.
     *
     * @return list of found events mapped to eventMessage.
     */
    public List<EventMessage> getTodayPlannedEventsMessage() {
        return eventCrudRepository.findPlannedByPlannedDate(LocalDate.now())
                .stream()
                .map(this::toMessage)
                .collect(Collectors.toList());
    }

    /**
     * Method checks whether event has a nurse as a performer of the event.
     *
     * @param event that will be checked.
     */
    private void checkEventHasNotNurse(Event event) {
        if (event.getNurse() == null) {
            throw new ApplicationException("Event has no nurse.");
        }
    }

    /**
     * Method checks whether event nurse id and authenticated nurse id are the same.
     *
     * @param authNurse    authenticated nurse.
     * @param anotherNurse nurse as a performer of the event.
     */
    private void checkEventHasDifferentNurse(Employee authNurse, Employee anotherNurse) {
        if (!authNurse.getId().equals(anotherNurse.getId())) {
            throw new ApplicationException("Event has a different nurse.");
        }
    }

    /**
     * Method checks whether given event state is not 'PLANNED'.
     *
     * @param eventState event state that will be checked.
     * @param action     action that is being tried to perform on event.
     */
    private void checkEventHasNotPlannedState(EventState eventState, String action) {
        if (eventState != EventState.PLANNED) {
            throw new ApplicationException("Cannot " + action + " event with " + eventState + " state.");
        }
    }

    /**
     * Method returns only event by given event id.
     *
     * @param id event id.
     * @return found event.
     */
    private Event getEventById(int id) {
        return eventCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Event with id " + id + " not found."));
    }

    /**
     * Method checks whether date of changed event is today date and sends message to message queue.
     *
     * @param changedEvent that date will be checked.
     */
    private void sendMessage(Event changedEvent) {
        if (changedEvent.getPlannedDate().equals(LocalDate.now())) {
            sendMessage();
        }
    }

    /**
     * Method sends message to message queue.
     */
    private void sendMessage() {
        template.convertAndSend("events_queue", "updated");
    }

    /**
     * Method maps (converts) given object of Event class to object of EventDto class.
     *
     * @param event object to map from Event to EventDto.
     * @return mapped instance of EventDto class.
     */
    private EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    /**
     * Method maps (converts) given object of Event class to object of EventMessage class.
     *
     * @param event object to map from Event to EventMessage.
     * @return mapped instance of EventMessage class.
     */
    private EventMessage toMessage(Event event) {
        return modelMapper.map(event, EventMessage.class);
    }
}
