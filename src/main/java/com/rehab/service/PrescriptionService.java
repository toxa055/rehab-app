package com.rehab.service;

import com.rehab.dto.PrescriptionDto;
import com.rehab.model.Employee;
import com.rehab.model.Prescription;
import com.rehab.model.type.Role;
import com.rehab.repository.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private final PrescriptionCrudRepository prescriptionCrudRepository;
    private final PatternCrudRepository patternCrudRepository;
    private final PeriodCrudRepository periodCrudRepository;
    private final ModelMapper modelMapper;
    private final TypeMap<PrescriptionDto, Prescription> typeMap;

    @Autowired
    public PrescriptionService(PrescriptionCrudRepository prescriptionCrudRepository,
                               PatternCrudRepository patternCrudRepository, PeriodCrudRepository periodCrudRepository,
                               ModelMapper modelMapper) {
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.patternCrudRepository = patternCrudRepository;
        this.periodCrudRepository = periodCrudRepository;
        this.modelMapper = modelMapper;
        typeMap = modelMapper.createTypeMap(PrescriptionDto.class, Prescription.class);
    }

    @Transactional
    public Prescription save(PrescriptionDto prescriptionDto) {
        var prescriptionFromDto = toEntity(prescriptionDto);
        prescriptionFromDto.setPattern(patternCrudRepository.save(prescriptionFromDto.getPattern()));
        prescriptionFromDto.setPeriod(periodCrudRepository.save(prescriptionFromDto.getPeriod()));
        return prescriptionCrudRepository.save(prescriptionFromDto);
    }

    public PrescriptionDto getById(int id) {
        return toDto(prescriptionCrudRepository.findAllById(List.of(id)).get(0));
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

    private Prescription toEntity(PrescriptionDto prescriptionDto) {
        prescriptionDto.setDoctorId(1);
        var doctor = new Employee();
        doctor.setRoles(Set.of(Role.DOCTOR));
        typeMap.addMappings(modelMapper -> modelMapper.map(src -> doctor, Prescription::setDoctor));
        return modelMapper.map(prescriptionDto, Prescription.class);
    }
}
