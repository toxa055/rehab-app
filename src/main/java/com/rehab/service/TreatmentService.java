package com.rehab.service;

import com.rehab.config.BeansConfig;
import com.rehab.dto.TreatmentDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Prescription;
import com.rehab.model.Treatment;
import com.rehab.model.type.PatientState;
import com.rehab.repository.*;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * Service class for Treatment. It operates with Treatment, TreatmentDto
 * and contains methods that are considered as business logic.
 */
@Service
public class TreatmentService {

    /**
     * TreatmentCrudRepository bean.
     */
    private final TreatmentCrudRepository treatmentCrudRepository;

    /**
     * PatientCrudRepository bean.
     */
    private final PatientCrudRepository patientCrudRepository;

    /**
     * PrescriptionCrudRepository bean.
     */
    private final PrescriptionCrudRepository prescriptionCrudRepository;

    /**
     * ModelMapper bean.
     *
     * @see BeansConfig#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * Contains specific mapping configuration for modelMapper.
     */
    private final TypeMap<TreatmentDto, Treatment> typeMap;

    /**
     * Constructs new instance and initializes following fields. Sets mapping configuration for modelMapper.
     *
     * @param treatmentCrudRepository    description of treatmentCrudRepository is in field declaration.
     * @param patientCrudRepository      description of patientCrudRepository is in field declaration.
     * @param prescriptionCrudRepository description of prescriptionCrudRepository is in field declaration.
     * @param modelMapper                description of modelMapper is in field declaration.
     */
    @Autowired
    public TreatmentService(TreatmentCrudRepository treatmentCrudRepository, PatientCrudRepository patientCrudRepository,
                            PrescriptionCrudRepository prescriptionCrudRepository, ModelMapper modelMapper) {
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.patientCrudRepository = patientCrudRepository;
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.modelMapper = modelMapper;
        typeMap = modelMapper.createTypeMap(TreatmentDto.class, Treatment.class);
    }

    /**
     * Method returns only treatmentDto by given treatment id.
     *
     * @param id treatment id.
     * @return found treatment mapped to treatmentDto.
     */
    public TreatmentDto getById(int id) {
        return toDto(getTreatmentById(id));
    }

    /**
     * Method maps given treatmentDto to treatment and saves it.
     * If patient state is 'DISCHARGED', it will be changed to 'TREATING'.
     *
     * @param treatmentDto that will be saved.
     * @return saved treatment mapped to treatmentDto.
     */
    @Transactional
    public TreatmentDto save(TreatmentDto treatmentDto) {
        var patientId = treatmentDto.getPatientId();
        var patient = patientCrudRepository.findById(patientId).orElseThrow(() ->
                new NoSuchElementException("Patient with id " + patientId + " not found."));
        if (patient.getPatientState() == PatientState.DISCHARGED) {
            patient.setPatientState(PatientState.TREATING);
            patientCrudRepository.save(patient);
        }
        return toDto(treatmentCrudRepository.save(toEntity(treatmentDto)));
    }

    /**
     * Method sets treatment as closed. Sets close date - today date. It's impossible to close treatment,
     * if it is already closed, was created by different doctor or has active prescriptions.
     *
     * @param id treatment id.
     * @return closed treatment mapped to treatmentDto.
     */
    @Transactional
    public TreatmentDto close(int id) {
        var treatmentForClosing = getTreatmentById(id);
        if (treatmentForClosing.isClosed()) {
            throw new ApplicationException("Treatment is already closed.");
        }
        var authDoctor = SecurityUtil.getAuthEmployee();
        if (!treatmentForClosing.getDoctor().getId().equals(authDoctor.getId())) {
            throw new ApplicationException("Cannot close treatment which was created by a different doctor.");
        }
        var hasActivePrescriptions = prescriptionCrudRepository.findAllByTreatmentId(id)
                .stream()
                .anyMatch(Prescription::isActive);
        if (hasActivePrescriptions) {
            throw new ApplicationException("Cannot close treatment which has active prescriptions.");
        }
        treatmentForClosing.setClosed(true);
        treatmentForClosing.setCloseDate(LocalDate.now());
        return toDto(treatmentCrudRepository.save(treatmentForClosing));
    }

    /**
     * Method finds treatments by given parameters and maps them to page of treatmentDto.
     *
     * @param date            particular date when treatments were created.
     * @param insuranceNumber patient insurance number.
     * @param authDoctor      only treatments that were created by authenticated doctor or any.
     * @param onlyOpen        only open treatments or any.
     * @param pageable        interface that provides pagination.
     * @return page of treatments (found by given parameters) mapped to treatmentDto.
     */
    public Page<TreatmentDto> filter(LocalDate date, Integer insuranceNumber, boolean authDoctor, boolean onlyOpen,
                                     Pageable pageable) {
        return treatmentCrudRepository.filter(date, insuranceNumber,
                authDoctor ? SecurityUtil.getAuthEmployee().getId() : null,
                onlyOpen ? false : null,
                pageable).map(this::toDto);
    }

    /**
     * Method returns only treatment by given treatment id.
     *
     * @param id treatment id.
     * @return found treatment.
     */
    private Treatment getTreatmentById(int id) {
        return treatmentCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Treatment with id " + id + " not found."));
    }

    /**
     * Method maps (converts) given object of Treatment class to object of TreatmentDto class.
     *
     * @param treatment object to map from Treatment to TreatmentDto.
     * @return mapped instance of TreatmentDto class.
     */
    private TreatmentDto toDto(Treatment treatment) {
        return modelMapper.map(treatment, TreatmentDto.class);
    }

    /**
     * Method maps (converts) given object of TreatmentDto class to object of Treatment class.
     * It sets authenticated employee (i.e. doctor) as doctor that created this treatment.
     *
     * @param treatmentDto object to map from TreatmentDto to Treatment.
     * @return mapped instance of Treatment class.
     */
    private Treatment toEntity(TreatmentDto treatmentDto) {
        var authDoctor = SecurityUtil.getAuthEmployee();
        treatmentDto.setDoctorId(authDoctor.getId());
        treatmentDto.setDoctorName(authDoctor.getName());
        typeMap.addMappings(modelMapper -> modelMapper.map(src -> authDoctor, Treatment::setDoctor));
        return modelMapper.map(treatmentDto, Treatment.class);
    }
}
