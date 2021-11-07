package com.rehab.service;

import com.rehab.dto.PrescriptionDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Pattern;
import com.rehab.model.Prescription;
import com.rehab.repository.*;
import com.rehab.util.EventUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.rehab.util.SecurityUtil.getAuthEmployee;

@Service
public class PrescriptionService {

    private final PrescriptionCrudRepository prescriptionCrudRepository;
    private final PatternCrudRepository patternCrudRepository;
    private final PeriodCrudRepository periodCrudRepository;
    private final EventCrudRepository eventCrudRepository;
    private final ModelMapper modelMapper;
    private final TypeMap<PrescriptionDto, Prescription> typeMapToEntity;
    private final TypeMap<Prescription, PrescriptionDto> typeMapToDto;

    @Autowired
    public PrescriptionService(PrescriptionCrudRepository prescriptionCrudRepository,
                               PatternCrudRepository patternCrudRepository, PeriodCrudRepository periodCrudRepository,
                               EventCrudRepository eventCrudRepository, ModelMapper modelMapper) {
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.patternCrudRepository = patternCrudRepository;
        this.periodCrudRepository = periodCrudRepository;
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
        typeMapToEntity = modelMapper.createTypeMap(PrescriptionDto.class, Prescription.class);
        typeMapToDto = modelMapper.createTypeMap(Prescription.class, PrescriptionDto.class);
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
        prescriptionFromDto.setPeriod(periodCrudRepository.save(period.isEmpty() ? periodFromDto : period.get()));
        var patternFromDto = prescriptionFromDto.getPattern();
        var pattern = patternCrudRepository.findByCountAndUnitAndPatternUnits(
                patternFromDto.getCount(), patternFromDto.getUnit(), patternFromDto.getPatternUnits());
        prescriptionFromDto.setPattern(patternCrudRepository.save(pattern.isEmpty() ? patternFromDto : pattern.get()));
        var savedPrescription = prescriptionCrudRepository.save(prescriptionFromDto);
        var plannedEvents = EventUtil.createEvents(savedPrescription);
        plannedEvents.forEach(e -> e.setPrescription(savedPrescription));
        eventCrudRepository.saveAll(plannedEvents);
        return toDto(savedPrescription);
    }

    @Transactional
    public PrescriptionDto cancel(int id) {
        var cancellingPrescription = getPrescriptionById(id);
        if (!cancellingPrescription.isActive()) {
            throw new ApplicationException("Prescription is already cancelled.");
        }
        var authDoctor = getAuthEmployee();
        if (!authDoctor.getId().equals(cancellingPrescription.getDoctor().getId())) {
            throw new ApplicationException("Cannot cancel prescription which was created by a different doctor.");
        }
        var eventsByPrescriptionId = eventCrudRepository.findAllByPrescriptionId(id);
        var eventsForCancelling = EventUtil.getEventsForCancelling(eventsByPrescriptionId, authDoctor.getName());
        eventCrudRepository.saveAll(eventsForCancelling);
        cancellingPrescription.setActive(false);
        return toDto(prescriptionCrudRepository.save(cancellingPrescription));
    }

    public Page<PrescriptionDto> filter(LocalDate pDate, Integer insuranceNumber, boolean authDoctor,
                                        boolean onlyActive, Pageable pageable) {
        return prescriptionCrudRepository.filter(pDate, insuranceNumber,
                authDoctor ? getAuthEmployee().getId() : null,
                onlyActive ? true : null,
                pageable).map(this::toDto);
    }

    private Prescription getPrescriptionById(int id) {
        return prescriptionCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Prescription with id " + id + " not found."));
    }

    private PrescriptionDto toDto(Prescription prescription) {
        var units = EventUtil.patternUnitsAsList(prescription.getPattern());
        typeMapToDto.addMappings(m -> m.map(src -> units, PrescriptionDto::setPatternUnits));
        return modelMapper.map(prescription, PrescriptionDto.class);
    }

    private Prescription toEntity(PrescriptionDto dto) {
        typeMapToEntity.addMappings(m -> m.map(src -> getAuthEmployee(), Prescription::setDoctor));
        var mappedPrescription = modelMapper.map(dto, Prescription.class);
        mappedPrescription.setPattern(new Pattern(dto.getPatternId(), dto.getPatternCount(), dto.getPatternUnit(),
                dto.getPatternUnits().stream().map(Enum::name).collect(Collectors.joining(", "))));
        return mappedPrescription;
    }
}
