package com.rehab.service;

import com.rehab.config.BeansConfig;
import com.rehab.dto.PatientDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Patient;
import com.rehab.model.type.PatientState;
import com.rehab.repository.PatientCrudRepository;
import com.rehab.repository.TreatmentCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Service class for Patient. It operates with Patient, PatientDto
 * and contains methods that are considered as business logic.
 */
@Service
public class PatientService {

    /**
     * PatientCrudRepository bean.
     */
    private final PatientCrudRepository patientCrudRepository;

    /**
     * TreatmentCrudRepository bean.
     */
    private final TreatmentCrudRepository treatmentCrudRepository;

    /**
     * ModelMapper bean.
     *
     * @see BeansConfig#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param patientCrudRepository   description of patientCrudRepository is in field declaration.
     * @param treatmentCrudRepository description of treatmentCrudRepository is in field declaration.
     * @param modelMapper             description of modelMapper is in field declaration.
     */
    @Autowired
    public PatientService(PatientCrudRepository patientCrudRepository,
                          TreatmentCrudRepository treatmentCrudRepository, ModelMapper modelMapper) {
        this.patientCrudRepository = patientCrudRepository;
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Method returns only patientDto by given patient id.
     *
     * @param id patient id.
     * @return found patient mapped to patientDto.
     */
    public PatientDto getById(int id) {
        return toDto(getPatientById(id));
    }

    /**
     * Method returns only patientDto by given patient insurance number.
     *
     * @param insuranceNumber patient insurance number.
     * @return found patient mapped to patientDto.
     */
    public PatientDto getByInsuranceNumber(int insuranceNumber) {
        return toDto(patientCrudRepository.findByInsuranceNumber(insuranceNumber).orElseThrow(() ->
                new NoSuchElementException("Patient with Insurance Number " + insuranceNumber + " not found.")));
    }

    /**
     * Method maps given patientDto to patient and saves it.
     * It's impossible to save new patient with insurance number, if another patient has the same one.
     *
     * @param patientDto that will be saved.
     * @return saved patient mapped to patientDto.
     */
    public PatientDto save(PatientDto patientDto) {
        var insuranceNumber = patientDto.getInsuranceNumber();
        if (patientCrudRepository.findByInsuranceNumber(insuranceNumber).isPresent()) {
            throw new ApplicationException("Patient with Insurance Number " + insuranceNumber + " already exists.");
        }
        return toDto(patientCrudRepository.save(toEntity(patientDto)));
    }

    /**
     * Method updates patient data.
     * It's impossible to set new insurance number for current patient, if another patient has the same one.
     *
     * @param patientDto stores editing data for patient.
     * @return updated patient mapped to patientDto.
     */
    @Transactional
    public PatientDto update(PatientDto patientDto) {
        var patientForUpdateById = getPatientById(patientDto.getId());
        var patientForUpdateByInsNumber = patientCrudRepository
                .findByInsuranceNumber(patientDto.getInsuranceNumber())
                .orElse(null);
        if (patientForUpdateByInsNumber != null) {
            if (!patientForUpdateById.getId().equals(patientForUpdateByInsNumber.getId())) {
                throw new ApplicationException("Patient with Insurance Number "
                        + patientForUpdateByInsNumber.getInsuranceNumber() + " already exists.");
            }
            return toDto(patientCrudRepository.save(toEntity(patientDto)));
        }
        return toDto(patientCrudRepository.save(toEntity(patientDto)));
    }

    /**
     * Method changes patient state from 'TREATING' to 'DISCHARGED'.
     * It's impossible to discharge patient, if they have already discharged or have open treatments.
     *
     * @param id patient id.
     * @return discharged patient mapped to patientDto.
     */
    @Transactional
    public PatientDto discharge(int id) {
        var dischargingPatient = getPatientById(id);
        if (dischargingPatient.getPatientState() == PatientState.DISCHARGED) {
            throw new ApplicationException("Patient is already discharged.");
        }
        var hasOpenTreatments = treatmentCrudRepository.findAllByPatientId(id)
                .stream()
                .anyMatch(t -> !t.isClosed());
        if (hasOpenTreatments) {
            throw new ApplicationException("Cannot discharge patient which has active treatments.");
        }
        dischargingPatient.setPatientState(PatientState.DISCHARGED);
        return toDto(patientCrudRepository.save(dischargingPatient));
    }

    /**
     * Method finds patients by given parameters and maps them to page of patientDto.
     *
     * @param insuranceNumber patient insurance number.
     * @param nameLike        not a particular patient name, but char sequence which patient names have to contain.
     * @param onlyTreating    patient state: only 'TREATING' or any.
     * @param pageable        interface that provides pagination.
     * @return page of patients (found by given parameters) mapped to patientDto.
     */
    public Page<PatientDto> filter(Integer insuranceNumber, String nameLike, boolean onlyTreating, Pageable pageable) {
        return patientCrudRepository.filter(insuranceNumber,
                nameLike == null ? null : nameLike.strip().toLowerCase(),
                onlyTreating ? PatientState.TREATING : null,
                pageable).map(this::toDto);
    }

    /**
     * Method returns only patient by given patient id.
     *
     * @param id patient id.
     * @return found patient.
     */
    private Patient getPatientById(int id) {
        return patientCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Patient with id " + id + " not found."));
    }

    /**
     * Method maps (converts) given object of Patient class to object of PatientDto class.
     *
     * @param patient object to map from Patient to PatientDto.
     * @return mapped instance of PatientDto class.
     */
    private PatientDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    /**
     * Method maps (converts) given object of PatientDto class to object of Patient class.
     *
     * @param patientDto object to map from PatientDto to Patient.
     * @return mapped instance of Patient class.
     */
    private Patient toEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }
}
