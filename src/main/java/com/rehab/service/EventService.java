package com.rehab.service;

import com.rehab.dto.EventDto;
import com.rehab.model.Event;
import com.rehab.model.type.EventState;
import com.rehab.repository.EventCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
public class EventService {

    private final EventCrudRepository eventCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EventService(EventCrudRepository eventCrudRepository, ModelMapper modelMapper) {
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
    }

    public EventDto setNurse(int eventId) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForSettingNurse = eventCrudRepository.findById(eventId).get();
        if (eventForSettingNurse.getNurse() != null) {
            throw new IllegalArgumentException();
        }
        eventForSettingNurse.setNurse(authNurse);
        return toDto(eventCrudRepository.save(eventForSettingNurse));
    }

    public EventDto unSetNurse(int eventId) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForUnsettingSetNurse = eventCrudRepository.findById(eventId).get();
        if ((eventForUnsettingSetNurse.getNurse() == null)
                || (!authNurse.getId().equals(eventForUnsettingSetNurse.getNurse().getId()))) {
            throw new IllegalArgumentException();
        }
        eventForUnsettingSetNurse.setNurse(null);
        return toDto(eventCrudRepository.save(eventForUnsettingSetNurse));
    }

    public EventDto changeStatus(int eventId, String eventState, String comment) {
        var authNurse = SecurityUtil.getAuthEmployee();
        var eventForChangingState = eventCrudRepository.findById(eventId).get();
        if ((eventForChangingState.getNurse() == null)
                || (!authNurse.getId().equals(eventForChangingState.getNurse().getId()))
                || (eventForChangingState.getEventState() != EventState.PLANNED)) {
            throw new IllegalArgumentException();
        }
        eventForChangingState.setEventState(EventState.valueOf(eventState.toUpperCase()));
        eventForChangingState.setEndDate(LocalDate.now());
        eventForChangingState.setEndTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        eventForChangingState.setComment(comment);
        return toDto(eventCrudRepository.save(eventForChangingState));
    }

    public EventDto getById(int id) {
        return toDto(eventCrudRepository.findById(id).get());
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

    private EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }
}
