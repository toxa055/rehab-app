package com.rehab.service;

import com.rehab.dto.PrescriptionDto;
import com.rehab.model.Prescription;
import com.rehab.repository.PrescriptionCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private final PrescriptionCrudRepository prescriptionCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PrescriptionService(PrescriptionCrudRepository prescriptionCrudRepository, ModelMapper modelMapper) {
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.modelMapper = modelMapper;
    }

    public PrescriptionDto getById(int id) {
        return toDto(prescriptionCrudRepository.findById(id).get());
    }

    public List<PrescriptionDto> getAllByPatientId(int patientId) {
        return prescriptionCrudRepository
                .findAllByPatientId(patientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PrescriptionDto> getAllByDoctorId(int doctorId) {
        return prescriptionCrudRepository
                .findAllByDoctorId(doctorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PrescriptionDto> getAll() {
        return prescriptionCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PrescriptionDto toDto(Prescription prescription) {
        return modelMapper.map(prescription, PrescriptionDto.class);
    }
}
