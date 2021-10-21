package com.rehab.service;

import com.rehab.dto.EventDto;
import com.rehab.model.Event;
import com.rehab.repository.EventCrudRepository;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public EventDto getById(int id) {
        return toDto(eventCrudRepository.findById(id).get());
    }

    public List<EventDto> getAll() {
        return sortByPlannedDateTime(eventCrudRepository.findAll());
    }

    public List<EventDto> getAllByPatientId(int patientId) {
        return sortByPlannedDateTime(eventCrudRepository.findAllByPatientId(patientId));
    }

    public List<EventDto> getAllByInsuranceNumber(int insNumber) {
        return sortByPlannedDateTime(eventCrudRepository.findAllByInsuranceNumber(insNumber));
    }

    public List<EventDto> getAllByNurseId(int nurseId) {
        return sortByPlannedDateTime(eventCrudRepository.findAllByNurseId(nurseId));
    }

    public List<EventDto> getAllByInsuranceNumberAndPlannedDate(int insNumber, LocalDate plannedDate) {
        return sortByPlannedTime(eventCrudRepository.findAllByInsuranceNumberAndPlannedDate(insNumber, plannedDate));
    }

    public List<EventDto> getAllByPlannedDate(LocalDate plannedDate) {
        return sortByPlannedTime(eventCrudRepository.findAllByPlannedDate((plannedDate)));
    }

    private EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    private List<EventDto> sortByPlannedDateTime(List<Event> events) {
        return events
                .stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(EventDto::getPlannedDate)
                        .thenComparing(EventDto::getPlannedTime))
                .collect(Collectors.toList());
    }

    private List<EventDto> sortByPlannedTime(List<Event> events) {
        return events
                .stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(EventDto::getPlannedTime))
                .collect(Collectors.toList());
    }
}
