package com.rehab.service;

import com.rehab.config.BeansConfig;
import com.rehab.config.MQConfig;
import com.rehab.dto.PrescriptionDto;
import com.rehab.dto.PrescriptionDtoOut;
import com.rehab.exception.ApplicationException;
import com.rehab.model.*;
import com.rehab.model.type.EventState;
import com.rehab.repository.*;
import com.rehab.util.EventUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.rehab.util.SecurityUtil.getAuthEmployee;

/**
 * Service class for Prescription. It operates with Prescription, PrescriptionDto,
 * PrescriptionDtoOut and contains methods that are considered as business logic.
 */
@Service
public class PrescriptionService {

    /**
     * PrescriptionCrudRepository bean.
     */
    private final PrescriptionCrudRepository prescriptionCrudRepository;

    /**
     * PatternCrudRepository bean.
     */
    private final PatternCrudRepository patternCrudRepository;

    /**
     * PeriodCrudRepository bean.
     */
    private final PeriodCrudRepository periodCrudRepository;

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
     * Contains mapping configuration from PrescriptionDto to Prescription for modelMapper.
     */
    private final TypeMap<PrescriptionDto, Prescription> typeMapToEntity;

    /**
     * Contains mapping configuration from Prescription to PrescriptionDTO for modelMapper.
     */
    private final TypeMap<Prescription, PrescriptionDto> typeMapToDto;

    /**
     * RabbitTemplate bean. It's used for sending messages to queue.
     *
     * @see MQConfig#template(ConnectionFactory)
     */
    private final RabbitTemplate template;

    /**
     * Constructs new instance and initializes following fields. Sets mapping configuration for modelMapper.
     *
     * @param prescriptionCrudRepository description of prescriptionCrudRepository is in field declaration.
     * @param patternCrudRepository      description of patternCrudRepository is in field declaration.
     * @param periodCrudRepository       description of periodCrudRepository is in field declaration.
     * @param eventCrudRepository        description of eventCrudRepository is in field declaration.
     * @param modelMapper                description of modelMapper is in field declaration.
     * @param template                   description of template is in field declaration.
     */
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

    /**
     * Method returns only prescriptionDto by given prescription id.
     *
     * @param id prescription id.
     * @return found prescription mapped to prescriptionDto.
     */
    public PrescriptionDto getById(int id) {
        return toDto(getPrescriptionById(id));
    }

    /**
     * Method returns only prescriptionDtoOut by given prescription id.
     *
     * @param id prescription id.
     * @return found prescription mapped to prescriptionDtoOut.
     */
    public PrescriptionDtoOut getPrescriptionDtoOutById(int id) {
        return toDtoOut(getPrescriptionById(id));
    }

    /**
     * Method finds prescriptions for particular treatment and maps them to page of prescriptionDtoOut.
     *
     * @param treatmentId treatment id that prescriptions will be found for.
     * @param pageable    interface that provides pagination.
     * @return page of prescriptions for treatment mapped to prescriptionDtoOut.
     */
    public Page<PrescriptionDtoOut> getByTreatmentId(int treatmentId, Pageable pageable) {
        return prescriptionCrudRepository.findAllByTreatmentId(treatmentId, pageable).map(this::toDtoOut);
    }

    /**
     * Method saves given prescriptionDto.
     *
     * @param prescriptionDto that will be saved.
     * @return saved prescription mapped to prescriptionDtoOut.
     */
    @Transactional
    public PrescriptionDtoOut save(PrescriptionDto prescriptionDto) {
        return toDtoOut(savePrescription(prescriptionDto));
    }

    /**
     * Method saves given prescriptionDto and cancels the previous one (that is considered to be updated).
     *
     * @param prescriptionDto that will be saved.
     * @return saved prescription mapped to prescriptionDtoOut.
     */
    @Transactional
    public PrescriptionDtoOut update(PrescriptionDto prescriptionDto) {
        var cancellingPrescriptionId = prescriptionDto.getId();
        prescriptionDto.setId(null);
        var newPrescription = savePrescription(prescriptionDto);
        cancelPrescription(cancellingPrescriptionId);
        return toDtoOut(newPrescription);
    }

    /**
     * Method cancels prescription.
     *
     * @param id prescription id.
     * @return cancelled prescription mapped to prescriptionDtoOut.
     */
    @Transactional
    public PrescriptionDtoOut cancel(int id) {
        return toDtoOut(cancelPrescription(id));
    }

    /**
     * Method closes prescription, i.e., sets its state as inactive.
     * It's impossible to close prescription if it is already closed, was created by different doctor
     * or has at least one planned event (it means all events relating to the prescription
     * must be either performed or cancelled).
     *
     * @param id prescription id.
     * @return closed prescription mapped to prescriptionDtoOut.
     */
    @Transactional
    public PrescriptionDtoOut close(int id) {
        var closingPrescription = getPrescriptionById(id);
        if (!closingPrescription.isActive()) {
            throw new ApplicationException("Prescription is already closed.");
        }
        var authDoctor = getAuthEmployee();
        if (!authDoctor.getId().equals(closingPrescription.getDoctor().getId())) {
            throw new ApplicationException("Cannot close prescription which was created by a different doctor.");
        }
        var hasPlannedEvents = eventCrudRepository.findAllByPrescriptionId(id)
                .stream()
                .anyMatch(e -> e.getEventState() == EventState.PLANNED);
        if (hasPlannedEvents) {
            throw new ApplicationException("Cannot close prescription which has planned events.");
        }
        closingPrescription.setActive(false);
        return toDtoOut(prescriptionCrudRepository.save(closingPrescription));
    }

    /**
     * Method finds number of prescriptions (active and inactive separately)
     * for particular treatment and collects them to map.
     *
     * @param tId treatment id.
     * @return map where key is prescription state (active and inactive)
     * and value is number of prescriptions (for this state).
     */
    public Map<String, Long> getPrescriptionsCountByTreatmentId(int tId) {
        Map<String, Long> map = new HashMap<>();
        map.put("active", prescriptionCrudRepository.getPrescriptionsCountByStateAndTreatmentId(true, tId));
        map.put("inactive", prescriptionCrudRepository.getPrescriptionsCountByStateAndTreatmentId(false, tId));
        return map;
    }

    /**
     * Method finds prescriptions by given parameters and maps them to page of prescriptionDtoOut.
     *
     * @param date            particular date when prescriptions were created.
     * @param insuranceNumber patient insurance number.
     * @param authDoctor      only prescriptions that were created by authenticated doctor or any.
     * @param onlyActive      only active prescriptions or any.
     * @param pageable        interface that provides pagination.
     * @return page of prescriptions (found by given parameters) mapped to prescriptionDtoOut.
     */
    public Page<PrescriptionDtoOut> filter(LocalDate date, Integer insuranceNumber, boolean authDoctor,
                                           boolean onlyActive, Pageable pageable) {
        return prescriptionCrudRepository.filter(date, insuranceNumber,
                authDoctor ? getAuthEmployee().getId() : null,
                onlyActive ? true : null,
                pageable).map(this::toDtoOut);
    }

    /**
     * Method returns only prescription by given prescription id.
     *
     * @param id prescription id.
     * @return found prescription.
     */
    private Prescription getPrescriptionById(int id) {
        return prescriptionCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Prescription with id " + id + " not found."));
    }

    /**
     * Method maps given dto to prescription and saves it.
     * At first, method check whether period and pattern from given dto are existed in database,
     * if they are not, method saves them to database. Then method saves prescription.
     * After that, method calls utility method to crate new planned events based on given dto and saves them.
     * Finally, method sends message to message queue if there are new events planned for today date were created.
     *
     * @param dto that will be saved.
     * @return saved prescription.
     * @see EventUtil#createEvents(Prescription)
     */
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

    /**
     * Method sets prescription as inactive and cancels all events that were created for it.
     * It's impossible to cancel prescription if it is already cancelled or was created by different doctor.
     * Method calls utility method to change state for events (associated to current prescription)
     * from 'PLANNED' to 'CANCELLED'.
     * Finally, method sends message to message queue if there are events planned for today date that were cancelled.
     *
     * @param id prescription id that will be cancelled.
     * @return cancelled prescription.
     * @see EventUtil#getEventsForCancelling(List, String)
     */
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

    /**
     * Method checks whether date of any changed events is today date and sends message to message queue.
     *
     * @param changedEvents list of events that date will be checked.
     */
    private void sendMessage(List<Event> changedEvents) {
        var today = LocalDate.now();
        var hasTodayEvents = changedEvents
                .stream()
                .anyMatch(e -> e.getPlannedDate().equals(today));
        if (hasTodayEvents) {
            template.convertAndSend("events_queue", "updated");
        }
    }

    /**
     * Method maps (converts) given object of Prescription class to object of PrescriptionDto class.
     * It transforms string representation of pattern units to list of them.
     *
     * @param prescription object to map from Prescription to PrescriptionDto.
     * @return mapped instance of PrescriptionDto class.
     */
    private PrescriptionDto toDto(Prescription prescription) {
        var units = Arrays.stream(prescription.getPattern().getPatternUnits().split(", "))
                .collect(Collectors.toList());
        typeMapToDto.addMappings(m -> m.map(src -> units, PrescriptionDto::setPatternUnits));
        return modelMapper.map(prescription, PrescriptionDto.class);
    }

    /**
     * Method maps (converts) given object of Prescription class to object of PrescriptionDtoOut class.
     *
     * @param prescription object to map from Prescription to PrescriptionDtoOut.
     * @return mapped instance of PrescriptionDtoOut class.
     */
    private PrescriptionDtoOut toDtoOut(Prescription prescription) {
        return modelMapper.map(prescription, PrescriptionDtoOut.class);
    }

    /**
     * Method maps (converts) given object of PrescriptionDto class to object of Prescription class.
     * It transforms list of pattern units to their string representation.
     *
     * @param dto object to map from PrescriptionDto to Prescription.
     * @return mapped instance of Prescription class.
     */
    private Prescription toEntity(PrescriptionDto dto) {
        typeMapToEntity.addMappings(m -> m.map(src -> getAuthEmployee(), Prescription::setDoctor));
        var mappedPrescription = modelMapper.map(dto, Prescription.class);
        mappedPrescription.setPattern(new Pattern(dto.getPatternId(), dto.getPatternCount(), dto.getPatternUnit(),
                String.join(", ", dto.getPatternUnits())));
        return mappedPrescription;
    }
}
