package com.rehab.service;

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

@Service
public class PatientService {

    private final PatientCrudRepository patientCrudRepository;
    private final TreatmentCrudRepository treatmentCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientService(PatientCrudRepository patientCrudRepository,
                          TreatmentCrudRepository treatmentCrudRepository, ModelMapper modelMapper) {
        this.patientCrudRepository = patientCrudRepository;
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.modelMapper = modelMapper;
    }

    public PatientDto getById(int id) {
        return toDto(getPatientById(id));
    }

    public PatientDto getByInsuranceNumber(int insuranceNumber) {
        return toDto(patientCrudRepository.findByInsuranceNumber(insuranceNumber).orElseThrow(() ->
                new NoSuchElementException("Patient with Insurance Number " + insuranceNumber + " not found.")));
    }

    public PatientDto save(PatientDto patientDto) {
        var insuranceNumber = patientDto.getInsuranceNumber();
        if (patientCrudRepository.findByInsuranceNumber(insuranceNumber).isPresent()) {
            throw new ApplicationException("Patient with Insurance Number " + insuranceNumber + " already exists.");
        }
        return toDto(patientCrudRepository.save(toEntity(patientDto)));
    }

    @Transactional
    public PatientDto update(PatientDto patientDto) {
        var patientForUpdateById = getPatientById(patientDto.getId());
        if (patientForUpdateById != null) {
            var patientForUpdateByInsNumber = patientCrudRepository
                    .findByInsuranceNumber(patientDto.getInsuranceNumber()).orElse(null);
            if (patientForUpdateByInsNumber != null) {
                if (!patientForUpdateById.getId().equals(patientForUpdateByInsNumber.getId())) {
                    throw new ApplicationException("Patient with Insurance Number "
                            + patientForUpdateByInsNumber.getInsuranceNumber() + " already exists.");
                } else {
                    return toDto(patientCrudRepository.save(toEntity(patientDto)));
                }
            } else {
                return toDto(patientCrudRepository.save(toEntity(patientDto)));
            }
        }
        return toDto(patientCrudRepository.save(toEntity(patientDto)));
    }

    @Transactional
    public PatientDto discharge(int id) {
        var dischargingPatient = getPatientById(id);
        if (dischargingPatient.getPatientState() == PatientState.DISCHARGED) {
            throw new ApplicationException("Patient is already discharged.");
        }
        long openTreatmentsCount = treatmentCrudRepository.findAllByPatientId(id)
                .stream()
                .filter(t -> !t.isClosed())
                .count();
        if (openTreatmentsCount > 0) {
            throw new ApplicationException("Cannot discharge patient which has active treatments.");
        }
        dischargingPatient.setPatientState(PatientState.DISCHARGED);
        return toDto(patientCrudRepository.save(dischargingPatient));
    }

    public Page<PatientDto> filter(Integer insuranceNumber, String nameLike, boolean onlyTreating, Pageable pageable) {
        return patientCrudRepository.filter(insuranceNumber,
                nameLike == null ? null : nameLike.strip().toLowerCase(),
                onlyTreating ? PatientState.TREATING : null,
                pageable).map(this::toDto);
    }

    private Patient getPatientById(int id) {
        return patientCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Patient with id " + id + " not found."));
    }

    private PatientDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    private Patient toEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }
}
