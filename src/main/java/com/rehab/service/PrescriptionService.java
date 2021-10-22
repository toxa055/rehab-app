package com.rehab.service;

import com.rehab.dto.PrescriptionDto;
import com.rehab.model.Prescription;
import com.rehab.repository.*;
import com.rehab.util.EventUtil;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private final PrescriptionCrudRepository prescriptionCrudRepository;
    private final PatternCrudRepository patternCrudRepository;
    private final PeriodCrudRepository periodCrudRepository;
    private final EventCrudRepository eventCrudRepository;
    private final ModelMapper modelMapper;
    private final TypeMap<PrescriptionDto, Prescription> typeMap;

    @Autowired
    public PrescriptionService(PrescriptionCrudRepository prescriptionCrudRepository,
                               PatternCrudRepository patternCrudRepository, PeriodCrudRepository periodCrudRepository,
                               EventCrudRepository eventCrudRepository, ModelMapper modelMapper) {
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.patternCrudRepository = patternCrudRepository;
        this.periodCrudRepository = periodCrudRepository;
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
        typeMap = modelMapper.createTypeMap(PrescriptionDto.class, Prescription.class);
    }

    @Transactional
    public PrescriptionDto save(PrescriptionDto prescriptionDto) {
        var prescriptionFromDto = toEntity(prescriptionDto);
        prescriptionFromDto.setPattern(patternCrudRepository.save(prescriptionFromDto.getPattern()));
        prescriptionFromDto.setPeriod(periodCrudRepository.save(prescriptionFromDto.getPeriod()));
        var savedPrescription = prescriptionCrudRepository.save(prescriptionFromDto);
        var plannedEvents = EventUtil.createEvents(savedPrescription);
        plannedEvents.forEach(e -> e.setPrescription(savedPrescription));
        eventCrudRepository.saveAll(plannedEvents);
        return toDto(savedPrescription);
    }

    @Transactional
    public PrescriptionDto cancel(int id) {
        var authDoctor = SecurityUtil.getAuthEmployee();
        var cancellingPrescription = prescriptionCrudRepository.findAllById(List.of(id)).get(0);
        if (!authDoctor.getId().equals(cancellingPrescription.getDoctor().getId())) {
            throw new IllegalStateException();
        }
        var eventsByPrescriptionId = eventCrudRepository.findAllByPrescriptionId(id);
        var eventsForCancelling = EventUtil.getEventsForCancelling(eventsByPrescriptionId);
        eventCrudRepository.saveAll(eventsForCancelling);
        cancellingPrescription.setActive(false);
        return toDto(prescriptionCrudRepository.save(cancellingPrescription));
    }

    public PrescriptionDto getById(int id) {
        return toDto(prescriptionCrudRepository.findAllById(List.of(id)).get(0));
    }

    public List<PrescriptionDto> getAllByPatientId(int patientId) {
        return prescriptionCrudRepository
                .findAllByPatientId(patientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PrescriptionDto> getAllByDoctorId(int doctorId) {
        return prescriptionCrudRepository
                .findAllByDoctorId(doctorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PrescriptionDto> getAll() {
        return prescriptionCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PrescriptionDto toDto(Prescription prescription) {
        return modelMapper.map(prescription, PrescriptionDto.class);
    }

    private Prescription toEntity(PrescriptionDto prescriptionDto) {
        var authDoctor = SecurityUtil.getAuthEmployee();
        typeMap.addMappings(modelMapper -> modelMapper.map(src -> authDoctor, Prescription::setDoctor));
        return modelMapper.map(prescriptionDto, Prescription.class);
    }
}
