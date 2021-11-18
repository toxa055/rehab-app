package com.rehab.service;

import com.rehab.dto.PrescriptionDto;
import com.rehab.dto.PrescriptionDtoOut;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Event;
import com.rehab.model.Pattern;
import com.rehab.model.Prescription;
import com.rehab.repository.*;
import com.rehab.util.EventUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
    private final RabbitTemplate template;

    @Autowired
    public PrescriptionService(PrescriptionCrudRepository prescriptionCrudRepository,
                               PatternCrudRepository patternCrudRepository, PeriodCrudRepository periodCrudRepository,
                               EventCrudRepository eventCrudRepository, ModelMapper modelMapper,
                               RabbitTemplate template) {
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.patternCrudRepository = patternCrudRepository;
        this.periodCrudRepository = periodCrudRepository;
        this.eventCrudRepository = eventCrudRepository;
        this.modelMapper = modelMapper;
        this.template = template;
        typeMapToEntity = modelMapper.createTypeMap(PrescriptionDto.class, Prescription.class);
        typeMapToDto = modelMapper.createTypeMap(Prescription.class, PrescriptionDto.class);
    }

    public PrescriptionDto getById(int id) {
        return toDto(getPrescriptionById(id));
    }

    public PrescriptionDtoOut getPrescriptionDtoOutById(int id) {
        return toDtoOut(getPrescriptionById(id));
    }

    public Page<PrescriptionDtoOut> getByTreatmentId(int treatmentId, Pageable pageable) {
        return prescriptionCrudRepository.findAllByTreatmentId(treatmentId, pageable).map(this::toDtoOut);
    }

    @Transactional
    public PrescriptionDtoOut save(PrescriptionDto prescriptionDto) {
        return toDtoOut(savePrescription(prescriptionDto));
    }

    @Transactional
    public PrescriptionDtoOut update(PrescriptionDto prescriptionDto) {
        var cancellingPrescriptionId = prescriptionDto.getId();
        prescriptionDto.setId(null);
        var newPrescription = savePrescription(prescriptionDto);
        cancelPrescription(cancellingPrescriptionId);
        return toDtoOut(newPrescription);
    }

    @Transactional
    public PrescriptionDtoOut cancel(int id) {
        return toDtoOut(cancelPrescription(id));
    }

    public Page<PrescriptionDtoOut> filter(LocalDate pDate, Integer insuranceNumber, boolean authDoctor,
                                        boolean onlyActive, Pageable pageable) {
        return prescriptionCrudRepository.filter(pDate, insuranceNumber,
                authDoctor ? getAuthEmployee().getId() : null,
                onlyActive ? true : null,
                pageable).map(this::toDtoOut);
    }

    private Prescription getPrescriptionById(int id) {
        return prescriptionCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Prescription with id " + id + " not found."));
    }

    private Prescription savePrescription(PrescriptionDto dto) {
        var prescriptionFromDto = toEntity(dto);
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
        sendMessage(plannedEvents);
        return savedPrescription;
    }

    private Prescription cancelPrescription(int id) {
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
        var savedPrescription = prescriptionCrudRepository.save(cancellingPrescription);
        sendMessage(eventsForCancelling);
        return savedPrescription;
    }

    private void sendMessage(List<Event> changedEvents) {
        var today = LocalDate.now();
        var hasTodayEvents = changedEvents
                .stream()
                .anyMatch(e -> e.getPlannedDate().equals(today));
        if (hasTodayEvents) {
            template.convertAndSend("events_queue", "updated");
        }
    }

    private PrescriptionDto toDto(Prescription prescription) {
        var units = EventUtil.patternUnitsAsList(prescription.getPattern());
        typeMapToDto.addMappings(m -> m.map(src -> units, PrescriptionDto::setPatternUnits));
        return modelMapper.map(prescription, PrescriptionDto.class);
    }

    private PrescriptionDtoOut toDtoOut(Prescription prescription) {
        return modelMapper.map(prescription, PrescriptionDtoOut.class);
    }

    private Prescription toEntity(PrescriptionDto dto) {
        typeMapToEntity.addMappings(m -> m.map(src -> getAuthEmployee(), Prescription::setDoctor));
        var mappedPrescription = modelMapper.map(dto, Prescription.class);
        mappedPrescription.setPattern(new Pattern(dto.getPatternId(), dto.getPatternCount(), dto.getPatternUnit(),
                dto.getPatternUnits().stream().map(Enum::name).collect(Collectors.joining(", "))));
        return mappedPrescription;
    }
}
