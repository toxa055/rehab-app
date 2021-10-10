package com.rehab.service;

import com.rehab.dto.EventDto;
import com.rehab.model.Event;
import com.rehab.repository.EventCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public EventDto getById(int id) {
        return toDto(eventCrudRepository.findById(id).get());
    }

    public List<EventDto> getAll() {
        return eventCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getAllByPatientId(int patientId) {
        return eventCrudRepository
                .findAllByPatientId(patientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getAllByNurseId(int nurseId) {
        return eventCrudRepository
                .findAllByNurseId(nurseId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getAllToday() {
        return eventCrudRepository
                .findAllToday(LocalDate.now())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }
}
