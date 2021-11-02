package com.rehab.service;

import com.rehab.dto.PatientDto;
import com.rehab.model.Patient;
import com.rehab.model.type.PatientState;
import com.rehab.repository.PatientCrudRepository;
import com.rehab.repository.TreatmentCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return toDto(patientCrudRepository.findById(id).get());
    }

    public PatientDto getByInsuranceNumber(int insuranceNumber) {
        return toDto(patientCrudRepository.getByInsuranceNumber(insuranceNumber));
    }

    public Patient save(PatientDto patientDto) {
        return patientCrudRepository.save(toEntity(patientDto));
    }

    public Patient discharge(int id) {
        long openTreatmentsCount = treatmentCrudRepository.findAllByPatientId(id)
                .stream()
                .filter(t -> !t.isClosed())
                .count();
        if (openTreatmentsCount > 0) {
            throw new IllegalStateException();
        }
        var dischargingPatient = patientCrudRepository.findById(id).get();
        dischargingPatient.setPatientState(PatientState.DISCHARGED);
        return patientCrudRepository.save(dischargingPatient);
    }

    public Page<PatientDto> filter(Integer insuranceNumber, String nameLike, boolean onlyTreating, Pageable pageable) {
        return patientCrudRepository.filter(insuranceNumber,
                nameLike == null ? null : nameLike.strip().toLowerCase(),
                onlyTreating ? PatientState.TREATING : null,
                pageable).map(this::toDto);
    }

    private PatientDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    private Patient toEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }
}
