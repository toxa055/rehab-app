package com.rehab.service;

import com.rehab.dto.PrescriptionDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Prescription;
import com.rehab.repository.*;
import com.rehab.util.EventUtil;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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

    public PrescriptionDto getById(int id) {
        return toDto(getPrescriptionById(id));
    }

    public Page<PrescriptionDto> getByTreatmentId(int treatmentId, Pageable pageable) {
        return prescriptionCrudRepository.findAllByTreatmentId(treatmentId, pageable).map(this::toDto);
    }

    @Transactional
    public PrescriptionDto save(PrescriptionDto prescriptionDto) {
        var prescriptionFromDto = toEntity(prescriptionDto);
        var periodFromDto = prescriptionFromDto.getPeriod();
        var period = periodCrudRepository.findByCountAndUnit(periodFromDto.getCount(), periodFromDto.getUnit());
        if (period.isEmpty()) {
            prescriptionFromDto.setPeriod(periodCrudRepository.save(prescriptionFromDto.getPeriod()));
        } else {
            prescriptionFromDto.setPeriod(period.get());
        }
        prescriptionFromDto.setPattern(patternCrudRepository.save(prescriptionFromDto.getPattern()));
        var savedPrescription = prescriptionCrudRepository.save(prescriptionFromDto);
        var plannedEvents = EventUtil.createEvents(savedPrescription);
        plannedEvents.forEach(e -> e.setPrescription(savedPrescription));
        eventCrudRepository.saveAll(plannedEvents);
        return toDto(savedPrescription);
    }

    @Transactional
    public PrescriptionDto cancel(int id) {
        var authDoctor = SecurityUtil.getAuthEmployee();
        var cancellingPrescription = getPrescriptionById(id);
        if (!cancellingPrescription.isActive()) {
            throw new ApplicationException("Prescription is already cancelled.");
        }
        if (!authDoctor.getId().equals(cancellingPrescription.getDoctor().getId())) {
            throw new ApplicationException("Cannot cancel prescription which was created by a different doctor.");
        }
        var eventsByPrescriptionId = eventCrudRepository.findAllByPrescriptionId(id);
        var eventsForCancelling = EventUtil.getEventsForCancelling(eventsByPrescriptionId,
                authDoctor.getName());
        eventCrudRepository.saveAll(eventsForCancelling);
        cancellingPrescription.setActive(false);
        return toDto(prescriptionCrudRepository.save(cancellingPrescription));
    }

    public Page<PrescriptionDto> filter(LocalDate pDate, Integer insuranceNumber, boolean authDoctor,
                                        boolean onlyActive, Pageable pageable) {
        return prescriptionCrudRepository.filter(pDate, insuranceNumber,
                authDoctor ? SecurityUtil.getAuthEmployee().getId() : null,
                onlyActive ? true : null,
                pageable).map(this::toDto);
    }

    private Prescription getPrescriptionById(int id) {
        var prescriptions = prescriptionCrudRepository.findAllById(List.of(id));
        if (prescriptions.isEmpty()) {
            throw new NoSuchElementException("Prescription with id " + id + " not found.");
        }
        return prescriptions.get(0);
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
