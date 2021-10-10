package com.rehab.service;

import com.rehab.dto.TreatmentDto;
import com.rehab.model.Treatment;
import com.rehab.repository.TreatmentCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentService {

    private final TreatmentCrudRepository treatmentCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TreatmentService(TreatmentCrudRepository treatmentCrudRepository, ModelMapper modelMapper) {
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.modelMapper = modelMapper;
    }

    public TreatmentDto getById(int id) {
        return toDto(treatmentCrudRepository.findById(id).get());
    }

    public List<TreatmentDto> getAllByPatientId(int patientId) {
        return treatmentCrudRepository
                .findAllByPatientId(patientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TreatmentDto> getAllByDoctorId(int doctorId) {
        return treatmentCrudRepository
                .findAllByDoctorId(doctorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TreatmentDto> getAll() {
        return treatmentCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TreatmentDto toDto(Treatment treatment) {
        return modelMapper.map(treatment, TreatmentDto.class);
    }
}
