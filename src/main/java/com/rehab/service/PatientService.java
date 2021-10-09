package com.rehab.service;


import com.rehab.dto.PatientDto;
import com.rehab.model.Patient;
import com.rehab.repository.PatientCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientCrudRepository patientCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientService(PatientCrudRepository patientCrudRepository, ModelMapper modelMapper) {
        this.patientCrudRepository = patientCrudRepository;
        this.modelMapper = modelMapper;
    }

    public Patient save(PatientDto patientDto) {
        return patientCrudRepository.save(toEntity(patientDto));
    }

    public List<PatientDto> getAll() {
        return patientCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PatientDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    private Patient toEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }
}
